package com.example.partymaker.presentation.ui.parties.search.meals

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.partymaker.R
import com.example.partymaker.databinding.FragmentMealSearchBinding
import com.example.partymaker.domain.common.DataState
import com.example.partymaker.presentation.ui.common.BaseFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class MealSearchFragment : BaseFragment(), SearchView.OnQueryTextListener {

    private var binding: FragmentMealSearchBinding? = null

    private val adapter = MealSearchListRecyclerViewAdapter(mutableListOf())

    private var searchJob: Job? = null

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: MealSearchViewModel by viewModels{
        viewModelFactory
    }

    override fun onAttach(context: Context) {
        injector.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentMealSearchBinding.inflate(inflater, container, false).root

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentMealSearchBinding.bind(view)
        val navController = findNavController()

        binding?.toolbarMealSearch?.inflateMenu(R.menu.menu_search)
        val searchItem = binding?.toolbarMealSearch?.menu?.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as SearchView
        searchView.setOnQueryTextListener(this)
        searchView.queryHint = getString(R.string.meal_search_hint_search_view)
        binding?.toolbarMealSearch?.setupWithNavController(navController)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.mealList.collect { mealList ->
                    when (mealList) {
                        is DataState.Init -> {}
                        is DataState.Loading -> showProgress(true)
                        is DataState.Data -> {
                            if (mealList.data.isNotEmpty())
                                Log.d(TAG, "onViewCreated: ${mealList.data[0].name}")
                            showProgress(false)
                            adapter.setData(mealList.data)
                        }
                        is DataState.Error -> {
                            showProgress(false)
                            binding?.root?.let {
                                Snackbar.make(it, "Error ${mealList.error}", Snackbar.LENGTH_SHORT).show()
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

    private fun showProgress(isVisible: Boolean) {
        if (isVisible) {
            binding?.hsvMealSearchChips?.visibility = View.INVISIBLE
            binding?.layoutMealListIncluded?.rvLayoutRecycler?.visibility = View.INVISIBLE
            binding?.layoutMealListIncluded?.pbLayoutRecycler?.visibility = View.VISIBLE
        } else {
            binding?.hsvMealSearchChips?.visibility = View.VISIBLE
            binding?.layoutMealListIncluded?.rvLayoutRecycler?.visibility = View.VISIBLE
            binding?.layoutMealListIncluded?.pbLayoutRecycler?.visibility = View.INVISIBLE
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null && query != "") {
            searchJob?.cancel()
            viewLifecycleOwner.lifecycleScope.launch {
                whenStarted {
                    searchJob = viewModel.getMealByName(query)
                }
            }
        }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean = true
}

private const val TAG = "MealSearchFragment"