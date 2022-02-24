package com.example.partymaker.presentation.ui.meals.search

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
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.RequestManager
import com.example.partymaker.R
import com.example.partymaker.databinding.FragmentMealSearchBinding
import com.example.partymaker.domain.common.DataState
import com.example.partymaker.domain.entities.MealCategoryEnum
import com.example.partymaker.presentation.ui.common.BaseFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class MealSearchFragment : BaseFragment(), SearchView.OnQueryTextListener {

    private var binding: FragmentMealSearchBinding? = null

    private val args: MealSearchFragmentArgs by navArgs()

    private var isChipCheckClearedByParent: Boolean = false

    private lateinit var adapter: MealSearchListRecyclerViewAdapter

    private var searchJob: Job? = null
    private var filterJob: Job? = null

    @Inject
    lateinit var glide: RequestManager

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: MealSearchViewModel by viewModels {
        viewModelFactory
    }

    private val onItemClickListener = object: MealSearchListRecyclerViewAdapter.OnItemClickListener {
        override fun onItemClick(mealId: Long, mealName: String) {
            val action =
                MealSearchFragmentDirections.actionMealSearchFragmentToMealDetailsFragment(
                    mealId = mealId,
                    partyId = args.partyId,
                    mealName = mealName
                )
            findNavController().navigate(action)
        }

    }

    override fun onAttach(context: Context) {
        injector.inject(this)
        super.onAttach(context)
        adapter = MealSearchListRecyclerViewAdapter(mutableListOf(), onItemClickListener, glide)
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

        binding?.layoutMealListIncluded?.rvLayoutRecycler?.adapter = adapter

        binding?.chipsGroupMealSearch?.setOnCheckedChangeListener { _, checkedId ->
            filterJob = viewLifecycleOwner.lifecycleScope.launch {
                whenStarted {
                    if (isChipCheckClearedByParent) return@whenStarted
                    var categoryFilter: MealCategoryEnum = MealCategoryEnum.All
                    when (checkedId) {
                        binding?.chipBeef?.id -> {
                            categoryFilter = MealCategoryEnum.Beef
                        }
                        binding?.chipBreakfast?.id -> {
                            categoryFilter = MealCategoryEnum.Breakfast
                        }
                        binding?.chipChicken?.id -> {
                            categoryFilter = MealCategoryEnum.Chicken
                        }
                        binding?.chipDessert?.id -> {
                            categoryFilter = MealCategoryEnum.Dessert
                        }
                        binding?.chipGoat?.id -> {
                            categoryFilter = MealCategoryEnum.Goat
                        }
                        binding?.chipLamb?.id -> {
                            categoryFilter = MealCategoryEnum.Lamb
                        }
                        binding?.chipMiscellaneous?.id -> {
                            categoryFilter = MealCategoryEnum.Miscellaneous
                        }
                        binding?.chipPasta?.id -> {
                            categoryFilter = MealCategoryEnum.Pasta
                        }
                        binding?.chipPork?.id -> {
                            categoryFilter = MealCategoryEnum.Pork
                        }
                        binding?.chipSeafood?.id -> {
                            categoryFilter = MealCategoryEnum.Seafood
                        }
                        binding?.chipSide?.id -> {
                            categoryFilter = MealCategoryEnum.Side
                        }
                        binding?.chipStarter?.id -> {
                            categoryFilter = MealCategoryEnum.Starter
                        }
                        binding?.chipVegan?.id -> {
                            categoryFilter = MealCategoryEnum.Vegan
                        }
                        binding?.chipVegetarian?.id -> {
                            categoryFilter = MealCategoryEnum.Vegetarian
                        }
                    }
                    viewModel.filterResultsByCategory(categoryFilter)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.mealList.collect { mealList ->
                    when (mealList) {
                        is DataState.Init -> {
                            showFilters(false)
                            showProgress(false)
                            chipGroupClearCheck(false)
                            adapter.setData(mutableListOf())
                        }
                        is DataState.Loading -> {
                            showProgress(true)
                            showFilters(false)
                        }
                        is DataState.Data -> {
                            if (mealList.data.isNotEmpty())
                                Log.d(TAG, "onViewCreated: ${mealList.data[0].name}")
                            showProgress(false)
                            showFilters(true)
                            chipGroupClearCheck(false)
                            adapter.setData(mealList.data)
                        }
                        is DataState.Error -> {
                            showProgress(false)
                            showFilters(false)
                            chipGroupClearCheck(false)
                            adapter.setData(mutableListOf())
                            binding?.root?.let {
                                Snackbar.make(it, mealList.error, Snackbar.LENGTH_SHORT)
                                    .show()
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

    override fun onQueryTextSubmit(query: String?): Boolean {
        searchJob?.cancel()
        filterJob?.cancel()
        chipGroupClearCheck(true)
        if (query != null && query != "") {
            viewLifecycleOwner.lifecycleScope.launch {
                whenStarted {
                    searchJob = viewModel.getMealByName(query)
                }
            }
        }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean = true

    private fun showProgress(isVisible: Boolean) {
        if (isVisible) {
            binding?.layoutMealListIncluded?.rvLayoutRecycler?.visibility = View.INVISIBLE
            binding?.layoutMealListIncluded?.pbLayoutRecycler?.visibility = View.VISIBLE
        } else {
            binding?.layoutMealListIncluded?.rvLayoutRecycler?.visibility = View.VISIBLE
            binding?.layoutMealListIncluded?.pbLayoutRecycler?.visibility = View.INVISIBLE
        }
    }

    private fun showFilters(isVisible: Boolean) {
        if (isVisible) {
            binding?.hsvMealSearchChips?.visibility = View.VISIBLE
        } else {
            binding?.hsvMealSearchChips?.visibility = View.INVISIBLE
        }
    }

    private fun chipGroupClearCheck(boolean: Boolean) {
        if (boolean) {
            isChipCheckClearedByParent = true
            binding?.chipsGroupMealSearch?.clearCheck()
        } else
            isChipCheckClearedByParent = false
    }
}

private const val TAG = "MealSearchFragment"
