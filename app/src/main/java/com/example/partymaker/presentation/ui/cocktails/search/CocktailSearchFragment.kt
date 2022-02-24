package com.example.partymaker.presentation.ui.cocktails.search

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
import com.example.partymaker.databinding.FragmentCocktailSearchBinding
import com.example.partymaker.domain.common.DataState
import com.example.partymaker.domain.entities.CocktailAlcoholicEnum
import com.example.partymaker.presentation.ui.common.BaseFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class CocktailSearchFragment: BaseFragment(), SearchView.OnQueryTextListener {

    private var binding: FragmentCocktailSearchBinding? = null

    private val args: CocktailSearchFragmentArgs by navArgs()

    private var isChipCheckClearedByParent: Boolean = false

    private lateinit var adapter: CocktailSearchListRecyclerViewAdapter

    private var searchJob: Job? = null
    private var filterJob: Job? = null

    @Inject
    lateinit var glide: RequestManager

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: CocktailSearchViewModel by viewModels {
        viewModelFactory
    }

    private val onItemClickListener = object: CocktailSearchListRecyclerViewAdapter.OnItemClickListener {
        override fun onItemClick(cocktailId: Long, cocktailName: String) {
            val action =
                CocktailSearchFragmentDirections.actionCocktailSearchFragmentToCocktailDetailsFragment(
                    cocktailId = cocktailId,
                    partyId = args.partyId,
                    cocktailName = cocktailName
                )
            findNavController().navigate(action)
        }
    }

    override fun onAttach(context: Context) {
        injector.inject(this)
        super.onAttach(context)
        adapter = CocktailSearchListRecyclerViewAdapter(mutableListOf(), onItemClickListener, glide)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentCocktailSearchBinding.inflate(inflater, container, false).root

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentCocktailSearchBinding.bind(view)
        val navController = findNavController()

        binding?.toolbarCocktailSearch?.inflateMenu(R.menu.menu_search)
        val searchItem = binding?.toolbarCocktailSearch?.menu?.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as SearchView
        searchView.setOnQueryTextListener(this)
        searchView.queryHint = getString(R.string.cocktail_search_hint_search_view)
        binding?.toolbarCocktailSearch?.setupWithNavController(navController)

        binding?.layoutCocktailListIncluded?.rvLayoutRecycler?.adapter = adapter

        binding?.chipsGroupCocktailSearch?.setOnCheckedChangeListener { _, checkedId ->
            filterJob = viewLifecycleOwner.lifecycleScope.launch {
                whenStarted {
                    if (isChipCheckClearedByParent) return@whenStarted
                    var alcoholicFilter: CocktailAlcoholicEnum = CocktailAlcoholicEnum.All
                    when (checkedId) {
                        binding?.chipAlcoholic?.id -> {
                            alcoholicFilter = CocktailAlcoholicEnum.Alcoholic
                        }
                        binding?.chipNonAlcoholic?.id -> {
                            alcoholicFilter = CocktailAlcoholicEnum.NonAlcoholic
                        }
                        binding?.chipOptionalAlcohol?.id -> {
                            alcoholicFilter = CocktailAlcoholicEnum.OptionalAlcohol
                        }
                    }
                    viewModel.filterResultsByAlcoholic(alcoholicFilter)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.cocktailList.collect { cocktailList ->
                    when (cocktailList) {
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
                            if (cocktailList.data.isNotEmpty())
                                Log.d(TAG, "onViewCreated: ${cocktailList.data[0].name}")
                            showProgress(false)
                            showFilters(true)
                            chipGroupClearCheck(false)
                            adapter.setData(cocktailList.data)
                        }
                        is DataState.Error -> {
                            showProgress(false)
                            showFilters(false)
                            chipGroupClearCheck(false)
                            adapter.setData(mutableListOf())
                            binding?.root?.let {
                                Snackbar.make(it, cocktailList.error, Snackbar.LENGTH_SHORT)
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
                    searchJob = viewModel.getCocktailByName(query)
                }
            }
        }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean = true

    private fun showProgress(isVisible: Boolean) {
        if (isVisible) {
            binding?.layoutCocktailListIncluded?.rvLayoutRecycler?.visibility = View.INVISIBLE
            binding?.layoutCocktailListIncluded?.pbLayoutRecycler?.visibility = View.VISIBLE
        } else {
            binding?.layoutCocktailListIncluded?.rvLayoutRecycler?.visibility = View.VISIBLE
            binding?.layoutCocktailListIncluded?.pbLayoutRecycler?.visibility = View.INVISIBLE
        }
    }

    private fun showFilters(isVisible: Boolean) {
        if (isVisible) {
            binding?.hsvCocktailSearchChips?.visibility = View.VISIBLE
        } else {
            binding?.hsvCocktailSearchChips?.visibility = View.INVISIBLE
        }
    }

    private fun chipGroupClearCheck(boolean: Boolean) {
        if (boolean) {
            isChipCheckClearedByParent = true
            binding?.chipsGroupCocktailSearch?.clearCheck()
        } else
            isChipCheckClearedByParent = false
    }
}

private const val TAG = "CocktailSearchFragment"
