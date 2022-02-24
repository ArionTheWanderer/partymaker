package com.example.partymaker.presentation.ui.meals.details

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
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

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getMealById(args.mealId)
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

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

}

private const val TAG = "MealDetailsFragment"