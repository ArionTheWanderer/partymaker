package com.example.partymaker.presentation.ui.parties.details.pager.meals

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import com.bumptech.glide.RequestManager
import com.example.partymaker.databinding.FragmentMealListBinding
import com.example.partymaker.domain.common.DataState
import com.example.partymaker.presentation.ui.common.BaseFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val PARTY_ID = "PARTY_ID"

class MealListFragment : BaseFragment() {

    @Inject
    lateinit var glide: RequestManager

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: MealListViewModel by viewModels {
        viewModelFactory
    }

    private lateinit var adapter: MealListRecyclerViewAdapter

    private var binding: FragmentMealListBinding? = null
    private var listener: OnMealListListener? = null
    private var partyId: Long? = null

    override fun onAttach(context: Context) {
        injector.inject(this)
        super.onAttach(context)
        try {
            listener = parentFragment as OnMealListListener
        } catch (e: ClassCastException) {
            throw ClassCastException(parentFragment.toString() + " must implement OnMealAddListener")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            partyId = it.getLong(PARTY_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentMealListBinding.inflate(inflater, container, false).root

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentMealListBinding.bind(view)

        binding?.fabMealsList?.setOnClickListener {
            listener?.addMeal()
            Log.d(TAG, "onViewCreated: result has been set")
        }

        val onItemClickListener = object: MealListRecyclerViewAdapter.OnItemClickListener {
            override fun onItemClick(mealId: Long, mealName: String) {
                listener?.navigateMeal(mealId, mealName)
            }

            override fun onDeleteButtonClick(mealId: Long) {
                viewLifecycleOwner.lifecycleScope.launch {
                    whenStarted {
                        viewModel.deleteMeal(mealId, partyId ?: 0)
                    }
                }
            }
        }
        adapter = MealListRecyclerViewAdapter(mutableListOf(), onItemClickListener, glide)

        binding?.layoutMealsIncluded?.rvLayoutRecycler?.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getMealList(partyId ?: 0)
                viewModel.mealList.collect { mealList ->
                    when (mealList) {
                        is DataState.Init -> {}
                        is DataState.Loading -> showProgress(true)
                        is DataState.Data -> {
                            showProgress(false)
                            adapter.setData(mealList.data)
                        }
                        is DataState.Error -> {
                            showProgress(false)
                            binding?.root?.let {
                                Snackbar.make(it, mealList.error, Snackbar.LENGTH_SHORT).show()
                            }
                            viewModel.resetErrorMessage()
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    override fun onDestroy() {
        listener = null
        super.onDestroy()
    }

    private fun showProgress(isVisible: Boolean) {
        if (isVisible) {
            binding?.fabMealsList?.visibility = View.INVISIBLE
            binding?.layoutMealsIncluded?.rvLayoutRecycler?.visibility = View.INVISIBLE
            binding?.layoutMealsIncluded?.pbLayoutRecycler?.visibility = View.VISIBLE
        } else {
            binding?.fabMealsList?.visibility = View.VISIBLE
            binding?.layoutMealsIncluded?.rvLayoutRecycler?.visibility = View.VISIBLE
            binding?.layoutMealsIncluded?.pbLayoutRecycler?.visibility = View.INVISIBLE
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(partyId: Long) =
            MealListFragment().apply {
                arguments = Bundle().apply {
                    putLong(PARTY_ID, partyId)
                }
            }
    }

    interface OnMealListListener {
        fun addMeal()
        fun navigateMeal(mealId: Long, mealName: String)
    }
}

private const val TAG = "MealListFragment"