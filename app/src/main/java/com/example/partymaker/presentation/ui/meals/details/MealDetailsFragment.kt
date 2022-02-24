package com.example.partymaker.presentation.ui.meals.details

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.RequestManager
import com.example.partymaker.databinding.FragmentMealDetailsBinding
import com.example.partymaker.domain.common.DataState
import com.example.partymaker.domain.entities.MealDomain
import com.example.partymaker.presentation.ui.common.BaseFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import javax.inject.Inject

class MealDetailsFragment : BaseFragment() {

    private var binding: FragmentMealDetailsBinding? = null

    private val args: MealDetailsFragmentArgs by navArgs()

    private lateinit var adapterIngredients: MealIngredientListRecyclerViewAdapter
    private lateinit var adapterDetails: MealDetailListRecyclerViewAdapter

    @Inject
    lateinit var glide: RequestManager

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: MealDetailsViewModel by viewModels {
        viewModelFactory
    }

    override fun onAttach(context: Context) {
        injector.inject(this)
        super.onAttach(context)
        adapterIngredients = MealIngredientListRecyclerViewAdapter(mutableListOf())
        adapterDetails = MealDetailListRecyclerViewAdapter(mutableListOf())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentMealDetailsBinding.inflate(inflater, container, false).root

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentMealDetailsBinding.bind(view)

        val navController = findNavController()
        binding?.toolbarMealDetails?.setupWithNavController(navController)
        binding?.toolbarMealDetails?.title = args.mealName

        binding?.layoutMealDetailsIncluded?.rvMealDetailsIngredients?.adapter = adapterIngredients
        binding?.layoutMealDetailsIncluded?.rvMealDetails?.adapter = adapterDetails

        if (savedInstanceState != null) {
            val isAddButtonVisible = savedInstanceState.getBoolean(IS_ADD_BUTTON_VISIBLE)
            if (isAddButtonVisible) {
                binding?.layoutMealDetailsIncluded?.buttonMealDetailsAdd?.visibility = View.VISIBLE
                binding?.layoutMealDetailsIncluded?.buttonMealDetailsDelete?.visibility = View.INVISIBLE
            } else {
                binding?.layoutMealDetailsIncluded?.buttonMealDetailsAdd?.visibility = View.INVISIBLE
                binding?.layoutMealDetailsIncluded?.buttonMealDetailsDelete?.visibility = View.VISIBLE
            }
        }

        binding?.layoutMealDetailsIncluded?.buttonMealDetailsAdd?.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                whenStarted {
                    Log.d(TAG, "onViewCreated: insertMeal")
                    viewModel.insertMeal(args.mealId, args.partyId)
                }
            }
        }

        binding?.layoutMealDetailsIncluded?.buttonMealDetailsDelete?.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                whenStarted {
                    Log.d(TAG, "onViewCreated: deleteMeal")
                    viewModel.deleteMeal(args.mealId, args.partyId)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getMealById(args.mealId, args.partyId)
                viewModel.meal.collect { meal ->
                    when (meal) {
                        is DataState.Init -> {}
                        is DataState.Loading -> showProgress(true)
                        is DataState.Data -> {
                            showProgress(false)
                            setMealData(meal.data)
                        }
                        is DataState.Error -> {
                            showProgress(false)
                            binding?.root?.let {
                                Snackbar.make(it, meal.error, Snackbar.LENGTH_SHORT).show()
                            }
                            viewModel.resetErrorMessage()
                        }
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.addResponse.collect { addResponse ->
                    when (addResponse) {
                        is DataState.Init -> {}
                        is DataState.Loading -> {
                            showProgressAddButton(true)
                        }
                        is DataState.Data -> {
                            showProgressAddButton(false)
                            binding?.root?.let {
                                Snackbar.make(it, addResponse.data, Snackbar.LENGTH_SHORT).show()
                            }
                            viewModel.resetAddResponse()
                        }
                        is DataState.Error -> {
                            showProgressAddButton(false)
                            binding?.root?.let {
                                Snackbar.make(it, addResponse.error, Snackbar.LENGTH_SHORT).show()
                            }
                            viewModel.resetAddResponse()
                        }
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.deleteResponse.collect { deleteResponse ->
                    when (deleteResponse) {
                        is DataState.Init -> {}
                        is DataState.Loading -> {
                            showProgressDeleteButton(true)
                        }
                        is DataState.Data -> {
                            showProgressDeleteButton(false)
                            binding?.root?.let {
                                Snackbar.make(it, deleteResponse.data, Snackbar.LENGTH_SHORT).show()
                            }
                            viewModel.resetDeleteResponse()
                        }
                        is DataState.Error -> {
                            showProgressDeleteButton(false)
                            binding?.root?.let {
                                Snackbar.make(it, deleteResponse.error, Snackbar.LENGTH_SHORT).show()
                            }
                            viewModel.resetDeleteResponse()
                        }
                    }
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        val addButtonVisibility = binding?.layoutMealDetailsIncluded?.buttonMealDetailsAdd?.visibility
        if (addButtonVisibility == View.VISIBLE)
            outState.putBoolean(IS_ADD_BUTTON_VISIBLE, true)
        else
            outState.putBoolean(IS_ADD_BUTTON_VISIBLE, false)
        super.onSaveInstanceState(outState)
    }

    private fun setMealData(meal: MealDomain) {
        Log.d(TAG, "setMealData: ingredients size ${meal.ingredientsWithMeasures.size}")
        adapterIngredients.setData(meal.ingredientsWithMeasures)
        val detailsList: MutableList<Pair<String, String>> = mutableListOf()
        detailsList.add("Category" to meal.category.name)
        detailsList.add("Area" to meal.area)
        Log.d(TAG, "setMealData: details size ${meal.ingredientsWithMeasures.size}")
        adapterDetails.setData(detailsList)
        glide
            .load(meal.thumbnailLink)
            .into(binding?.layoutMealDetailsIncluded?.ivMealDetails!!)
        binding?.layoutMealDetailsIncluded?.tvImageTitle?.text = meal.name
        binding?.layoutMealDetailsIncluded?.tvMealDetailsInstructionsText?.text =
            meal.instructions
        if (meal.isInCurrentParty) {
            binding?.layoutMealDetailsIncluded?.buttonMealDetailsAdd?.visibility = View.INVISIBLE
            binding?.layoutMealDetailsIncluded?.buttonMealDetailsDelete?.visibility = View.VISIBLE
        } else {
            binding?.layoutMealDetailsIncluded?.buttonMealDetailsAdd?.visibility = View.VISIBLE
            binding?.layoutMealDetailsIncluded?.buttonMealDetailsDelete?.visibility = View.INVISIBLE
        }
    }

    private fun showProgress(isVisible: Boolean) {
        if (isVisible) {
            binding?.layoutMealDetailsIncluded?.groupMealDetailsContent?.visibility = View.INVISIBLE
            binding?.layoutMealDetailsIncluded?.pbMealDetails?.visibility = View.VISIBLE
        } else {
            binding?.layoutMealDetailsIncluded?.groupMealDetailsContent?.visibility = View.VISIBLE
            binding?.layoutMealDetailsIncluded?.pbMealDetails?.visibility = View.INVISIBLE
        }
    }

    private fun showProgressAddButton(isVisible: Boolean) {
        if (isVisible) {
            binding?.layoutMealDetailsIncluded?.buttonMealDetailsAdd?.visibility = View.INVISIBLE
            binding?.layoutMealDetailsIncluded?.buttonMealDetailsDelete?.visibility = View.INVISIBLE
            binding?.layoutMealDetailsIncluded?.pbMealDetailsToggleButton?.visibility = View.VISIBLE
        } else {
            binding?.layoutMealDetailsIncluded?.buttonMealDetailsAdd?.visibility = View.INVISIBLE
            binding?.layoutMealDetailsIncluded?.buttonMealDetailsDelete?.visibility = View.VISIBLE
            binding?.layoutMealDetailsIncluded?.pbMealDetailsToggleButton?.visibility = View.INVISIBLE
        }
    }

    private fun showProgressDeleteButton(isVisible: Boolean) {
        if (isVisible) {
            binding?.layoutMealDetailsIncluded?.buttonMealDetailsAdd?.visibility = View.INVISIBLE
            binding?.layoutMealDetailsIncluded?.buttonMealDetailsDelete?.visibility = View.INVISIBLE
            binding?.layoutMealDetailsIncluded?.pbMealDetailsToggleButton?.visibility = View.VISIBLE
        } else {
            binding?.layoutMealDetailsIncluded?.buttonMealDetailsAdd?.visibility = View.VISIBLE
            binding?.layoutMealDetailsIncluded?.buttonMealDetailsDelete?.visibility = View.INVISIBLE
            binding?.layoutMealDetailsIncluded?.pbMealDetailsToggleButton?.visibility = View.INVISIBLE
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    companion object {
        private const val IS_ADD_BUTTON_VISIBLE = "IS_ADD_BUTTON_VISIBLE"
    }
}

private const val TAG = "MealDetailsFragment"