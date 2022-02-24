package com.example.partymaker.presentation.ui.cocktails.details

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
import com.example.partymaker.databinding.FragmentCocktailDetailsBinding
import com.example.partymaker.databinding.FragmentMealDetailsBinding
import com.example.partymaker.domain.common.DataState
import com.example.partymaker.domain.entities.CocktailAlcoholicEnum
import com.example.partymaker.domain.entities.CocktailDomain
import com.example.partymaker.domain.entities.MealDomain
import com.example.partymaker.presentation.ui.common.BaseFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import javax.inject.Inject

class CocktailDetailsFragment : BaseFragment() {

    private var binding: FragmentCocktailDetailsBinding? = null

    private val args: CocktailDetailsFragmentArgs by navArgs()

    private lateinit var adapterIngredients: CocktailIngredientListRecyclerViewAdapter
    private lateinit var adapterDetails: CocktailDetailListRecyclerViewAdapter

    @Inject
    lateinit var glide: RequestManager

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: CocktailDetailsViewModel by viewModels {
        viewModelFactory
    }

    override fun onAttach(context: Context) {
        injector.inject(this)
        super.onAttach(context)
        adapterIngredients = CocktailIngredientListRecyclerViewAdapter(mutableListOf())
        adapterDetails = CocktailDetailListRecyclerViewAdapter(mutableListOf())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentCocktailDetailsBinding.inflate(inflater, container, false).root

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentCocktailDetailsBinding.bind(view)

        val navController = findNavController()
        binding?.toolbarCocktailDetails?.setupWithNavController(navController)
        binding?.toolbarCocktailDetails?.title = args.cocktailName

        binding?.layoutCocktailDetailsIncluded?.rvCocktailDetailsIngredients?.adapter =
            adapterIngredients
        binding?.layoutCocktailDetailsIncluded?.rvCocktailDetails?.adapter = adapterDetails

        if (savedInstanceState != null) {
            val isAddButtonVisible = savedInstanceState.getBoolean(IS_ADD_BUTTON_VISIBLE)
            if (isAddButtonVisible) {
                binding?.layoutCocktailDetailsIncluded?.buttonCocktailDetailsAdd?.visibility =
                    View.VISIBLE
                binding?.layoutCocktailDetailsIncluded?.buttonCocktailDetailsDelete?.visibility =
                    View.INVISIBLE
            } else {
                binding?.layoutCocktailDetailsIncluded?.buttonCocktailDetailsAdd?.visibility =
                    View.INVISIBLE
                binding?.layoutCocktailDetailsIncluded?.buttonCocktailDetailsDelete?.visibility =
                    View.VISIBLE
            }
        }

        binding?.layoutCocktailDetailsIncluded?.buttonCocktailDetailsAdd?.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                whenStarted {
                    Log.d(TAG, "onViewCreated: insertCocktail")
                    viewModel.insertCocktail(args.cocktailId, args.partyId)
                }
            }
        }

        binding?.layoutCocktailDetailsIncluded?.buttonCocktailDetailsDelete?.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                whenStarted {
                    Log.d(TAG, "onViewCreated: deleteCocktail")
                    viewModel.deleteCocktail(args.cocktailId, args.partyId)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getCocktailById(args.cocktailId, args.partyId)
                viewModel.cocktail.collect { cocktail ->
                    when (cocktail) {
                        is DataState.Init -> {}
                        is DataState.Loading -> showProgress(true)
                        is DataState.Data -> {
                            showProgress(false)
                            setMealData(cocktail.data)
                        }
                        is DataState.Error -> {
                            showProgress(false)
                            binding?.root?.let {
                                Snackbar.make(it, cocktail.error, Snackbar.LENGTH_SHORT).show()
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
                                Snackbar.make(it, deleteResponse.error, Snackbar.LENGTH_SHORT)
                                    .show()
                            }
                            viewModel.resetDeleteResponse()
                        }
                    }
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        val addButtonVisibility =
            binding?.layoutCocktailDetailsIncluded?.buttonCocktailDetailsAdd?.visibility
        if (addButtonVisibility == View.VISIBLE)
            outState.putBoolean(IS_ADD_BUTTON_VISIBLE, true)
        else
            outState.putBoolean(IS_ADD_BUTTON_VISIBLE, false)
        super.onSaveInstanceState(outState)
    }

    private fun setMealData(cocktail: CocktailDomain) {
        Log.d(TAG, "setCocktailData: ingredients size ${cocktail.ingredientsWithMeasures.size}")
        adapterIngredients.setData(cocktail.ingredientsWithMeasures)
        val detailsList: MutableList<Pair<String, String>> = mutableListOf()
        detailsList.add("Category" to cocktail.category)
        detailsList.add("Alcoholic" to CocktailAlcoholicEnum.enumToString(cocktail.alcoholic))
        detailsList.add("Glass" to cocktail.glass)
        Log.d(TAG, "setCocktailData: details size ${cocktail.ingredientsWithMeasures.size}")
        adapterDetails.setData(detailsList)
        glide
            .load(cocktail.thumbnailLink)
            .into(binding?.layoutCocktailDetailsIncluded?.ivCocktailDetails!!)
        binding?.layoutCocktailDetailsIncluded?.tvCocktailTitle?.text = cocktail.name
        binding?.layoutCocktailDetailsIncluded?.tvCocktailDetailsInstructionsText?.text =
            cocktail.instructions
        if (cocktail.isInCurrentParty) {
            binding?.layoutCocktailDetailsIncluded?.buttonCocktailDetailsAdd?.visibility =
                View.INVISIBLE
            binding?.layoutCocktailDetailsIncluded?.buttonCocktailDetailsDelete?.visibility =
                View.VISIBLE
        } else {
            binding?.layoutCocktailDetailsIncluded?.buttonCocktailDetailsAdd?.visibility =
                View.VISIBLE
            binding?.layoutCocktailDetailsIncluded?.buttonCocktailDetailsDelete?.visibility =
                View.INVISIBLE
        }
    }

    private fun showProgress(isVisible: Boolean) {
        if (isVisible) {
            binding?.layoutCocktailDetailsIncluded?.groupCocktailDetailsContent?.visibility =
                View.INVISIBLE
            binding?.layoutCocktailDetailsIncluded?.pbCocktailDetails?.visibility = View.VISIBLE
        } else {
            binding?.layoutCocktailDetailsIncluded?.groupCocktailDetailsContent?.visibility =
                View.VISIBLE
            binding?.layoutCocktailDetailsIncluded?.pbCocktailDetails?.visibility = View.INVISIBLE
        }
    }

    private fun showProgressAddButton(isVisible: Boolean) {
        if (isVisible) {
            binding?.layoutCocktailDetailsIncluded?.buttonCocktailDetailsAdd?.visibility =
                View.INVISIBLE
            binding?.layoutCocktailDetailsIncluded?.buttonCocktailDetailsDelete?.visibility =
                View.INVISIBLE
            binding?.layoutCocktailDetailsIncluded?.pbCocktailDetailsToggleButton?.visibility =
                View.VISIBLE
        } else {
            binding?.layoutCocktailDetailsIncluded?.buttonCocktailDetailsAdd?.visibility =
                View.INVISIBLE
            binding?.layoutCocktailDetailsIncluded?.buttonCocktailDetailsDelete?.visibility =
                View.VISIBLE
            binding?.layoutCocktailDetailsIncluded?.pbCocktailDetailsToggleButton?.visibility =
                View.INVISIBLE
        }
    }

    private fun showProgressDeleteButton(isVisible: Boolean) {
        if (isVisible) {
            binding?.layoutCocktailDetailsIncluded?.buttonCocktailDetailsAdd?.visibility =
                View.INVISIBLE
            binding?.layoutCocktailDetailsIncluded?.buttonCocktailDetailsDelete?.visibility =
                View.INVISIBLE
            binding?.layoutCocktailDetailsIncluded?.pbCocktailDetailsToggleButton?.visibility =
                View.VISIBLE
        } else {
            binding?.layoutCocktailDetailsIncluded?.buttonCocktailDetailsAdd?.visibility =
                View.VISIBLE
            binding?.layoutCocktailDetailsIncluded?.buttonCocktailDetailsDelete?.visibility =
                View.INVISIBLE
            binding?.layoutCocktailDetailsIncluded?.pbCocktailDetailsToggleButton?.visibility =
                View.INVISIBLE
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

private const val TAG = "CocktailDetailsFragment"