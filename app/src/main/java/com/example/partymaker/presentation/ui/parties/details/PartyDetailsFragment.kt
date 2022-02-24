package com.example.partymaker.presentation.ui.parties.details

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import com.example.partymaker.NavGraphDirections
import com.example.partymaker.R
import com.example.partymaker.databinding.FragmentPartyDetailsBinding
import com.example.partymaker.domain.common.DataState
import com.example.partymaker.domain.entities.PartyDomain
import com.example.partymaker.presentation.ui.common.BaseFragment
import com.example.partymaker.presentation.ui.parties.details.pager.ViewPagerAdapter
import com.example.partymaker.presentation.ui.parties.details.pager.cocktails.CocktailListFragment
import com.example.partymaker.presentation.ui.parties.details.pager.meals.MealListFragment
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch
import javax.inject.Inject

class PartyDetailsFragment : BaseFragment(), CocktailListFragment.OnCocktailListListener,
    MealListFragment.OnMealListListener {

    private val tabNames: Array<String> = arrayOf(
        "Cocktails",
        "Meals"
    )

    private val args: PartyDetailsFragmentArgs by navArgs()
    private var partyId: Long = 0L
    private var partyName: String = " "
    private var binding: FragmentPartyDetailsBinding? = null

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: PartyDetailsViewModel by viewModels {
        viewModelFactory
    }

    override fun onAttach(context: Context) {
        injector.inject(this)
        super.onAttach(context)
        partyId = args.itemId
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentPartyDetailsBinding.inflate(inflater, container, false).root

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentPartyDetailsBinding.bind(view)
        val navController = findNavController()

        // Toolbar & options menu setup
        binding?.toolbarPartyDetails?.inflateMenu(R.menu.menu_party_details)
        binding?.toolbarPartyDetails?.setOnMenuItemClickListener { item ->
            if (item.itemId == R.id.navigation_party_dialog) {
                if (partyId == 0L || partyName == " ") {
                    Toast.makeText(
                        requireContext(),
                        "Error: cannot resolve party",
                        Toast.LENGTH_LONG
                    ).show()
                    return@setOnMenuItemClickListener true
                }
                val action = NavGraphDirections.actionGlobalPartyDialogFragment(
                    itemId = partyId,
                    partyName = partyName
                )
                navController.navigate(action)
            } else if (item.itemId == R.id.navigation_party_delete_dialog) {
                if (partyId == 0L) {
                    Toast.makeText(
                        requireContext(),
                        "Error: cannot resolve party",
                        Toast.LENGTH_LONG
                    ).show()
                    return@setOnMenuItemClickListener true
                }
                val action = NavGraphDirections.actionGlobalPartyDeleteDialogFragment(
                    itemId = partyId,
                    partyName = partyName
                )
                navController.navigate(action)
            }
            true
        }
        binding?.toolbarPartyDetails?.setupWithNavController(navController)

        // ViewPager setup
        val viewpager = binding?.viewpagerPartyDetails
        val tablayout = binding?.tabsPartyDetails
        val fragmentList = arrayListOf(
            CocktailListFragment.newInstance(partyId) as Fragment,
            MealListFragment.newInstance(partyId) as Fragment
        )
        val adapter = ViewPagerAdapter(
            fragmentList,
            childFragmentManager,
            lifecycle
        )
        viewpager?.adapter = adapter
        if (tablayout != null && viewpager != null) {
            TabLayoutMediator(tablayout, viewpager) { tab, position ->
                tab.text = tabNames[position]
            }.attach()
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getParty(partyId)
                viewModel.party.collect { party ->
                    when (party) {
                        is DataState.Init -> {}
                        is DataState.Loading -> showProgress(true)
                        is DataState.Data -> {
                            showProgress(false)
                            setScreenData(party.data)
                        }
                        is DataState.Error -> {
                            Log.e(TAG, "onViewCreated: party.error")
                            showProgress(false)
                            val action = NavGraphDirections.actionGlobalPartyListFragmentPopup()
                            navController.navigate(action)
                        }
                    }
                }
            }
        }
    }

    private fun setScreenData(party: PartyDomain) {
        binding?.toolbarPartyDetails?.title = party.name
        partyName = party.name
        partyId = party.id
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun showProgress(isVisible: Boolean) {
        if (isVisible) {
            binding?.pbPartyDetails?.visibility = View.VISIBLE
        } else {
            binding?.pbPartyDetails?.visibility = View.INVISIBLE
        }
    }

    override fun addCocktail() {
        val action =
            PartyDetailsFragmentDirections.actionPartyDetailsFragmentToCocktailSearchFragment(
                partyId = partyId
            )
        findNavController().navigate(action)
    }

    override fun navigateCocktails(cocktailId: Long, cocktailName: String) {
        val action =
            PartyDetailsFragmentDirections.actionPartyDetailsFragmentToCocktailDetailsFragment(
                partyId = partyId,
                cocktailId = cocktailId,
                cocktailName = cocktailName
            )
        findNavController().navigate(action)
    }

    override fun addMeal() {
        val action =
            PartyDetailsFragmentDirections.actionPartyDetailsFragmentToMealSearchFragment(
                partyId = partyId
            )
        findNavController().navigate(action)
    }

    override fun navigateMeal(mealId: Long, mealName: String) {
        val action =
            PartyDetailsFragmentDirections.actionPartyDetailsFragmentToMealDetailsFragment(
                partyId = partyId,
                mealId = mealId,
                mealName = mealName
            )
        findNavController().navigate(action)
    }
}

private const val TAG = "PartyDetailsFragment"