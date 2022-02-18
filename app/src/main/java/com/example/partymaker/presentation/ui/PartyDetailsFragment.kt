package com.example.partymaker.presentation.ui

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import com.example.partymaker.presentation.di.Injector
import kotlinx.coroutines.launch
import javax.inject.Inject

class PartyDetailsFragment : Fragment() {

    private val args: PartyDetailsFragmentArgs by navArgs()
    private var partyName: String = " "
    private var binding: FragmentPartyDetailsBinding? = null

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: PartyDetailsViewModel by viewModels{
        viewModelFactory
    }

    override fun onAttach(context: Context) {
        Injector.partyComponent()?.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentPartyDetailsBinding.inflate(inflater, container, false).root

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val receivedId = args.itemId

        binding = FragmentPartyDetailsBinding.bind(view)
        val navController = findNavController()
        binding?.toolbarPartyDetails?.inflateMenu(R.menu.menu_party_details)
        binding?.toolbarPartyDetails?.setOnMenuItemClickListener { item ->
            if (item.itemId == R.id.navigation_party_dialog) {
                val action = NavGraphDirections.actionGlobalPartyDialogFragment(itemId = receivedId, partyName = partyName)
                navController.navigate(action)
            }
            true
        }
        binding?.toolbarPartyDetails?.setupWithNavController(navController)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.party.collect { party ->
                    when (party) {
                        is DataState.Init -> {
                            viewModel.getParty(receivedId)
                        }
                        is DataState.Loading -> showProgress(true)
                        is DataState.Data -> {
                            showProgress(false)
                            binding?.tvPartyDetailsPartyName?.text = party.data.name
                            binding?.toolbarPartyDetails?.title = party.data.name
                            partyName = party.data.name
                        }
                        is DataState.Error -> {
                            showProgress(false)
                            Toast.makeText(requireContext(), "Error ${party.error}", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun showProgress(isVisible: Boolean) {
        if (isVisible) {
            binding?.tvPartyDetailsPartyName?.visibility = View.INVISIBLE
            binding?.pbPartyDetails?.visibility = View.VISIBLE
        } else {
            binding?.tvPartyDetailsPartyName?.visibility = View.VISIBLE
            binding?.pbPartyDetails?.visibility = View.INVISIBLE
        }
    }
}
