package com.example.partymaker.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.example.partymaker.databinding.FragmentPartyDialogBinding
import com.example.partymaker.domain.common.DataState
import com.example.partymaker.presentation.viewmodels.PartyDialogViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

class PartyDialogFragment : BottomSheetDialogFragment() {

    private enum class EditingState {
        NEW_PARTY,
        EXISTING_PARTY
    }

    private val args: PartyDialogFragmentArgs by navArgs()
    private var binding: FragmentPartyDialogBinding? = null

    @Inject
    @field:Named("partyViewModelFactory")
    lateinit var viewModelFactory: ViewModelProvider.Factory

    val viewModel: PartyDialogViewModel by viewModels{
        viewModelFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentPartyDialogBinding.inflate(inflater, container, false).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentPartyDialogBinding.bind(view)
        val receivedId = args.itemId

        val editingState =
            if (receivedId > 0) EditingState.EXISTING_PARTY
            else EditingState.NEW_PARTY

        // If we arrived here with an itemId of >= 0, then we are editing an existing item
        if (editingState == EditingState.EXISTING_PARTY) {
            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    // Trigger the flow and start listening for values.
                    // Note that this happens when lifecycle is STARTED and stops
                    // collecting when the lifecycle is STOPPED
                    viewModel.uiState.collect { uiState ->
                        // New value received
                        when (uiState) {
                            is DataState.Init -> {
                                viewModel.getParty(receivedId)
                            }
                            is DataState.Loading -> showProgress(true)
                            is DataState.Data -> {
                                showProgress(false)
                                Toast.makeText(requireContext(), "${uiState.data}", Toast.LENGTH_LONG).show()
                            }
                            is DataState.Error -> {
                                showProgress(false)
                                Toast.makeText(requireContext(), uiState.error, Toast.LENGTH_LONG).show()
                                dismiss()
                            }
                        }
                    }
                }

            }
        }

        // When the user clicks the Done button, use the data here to either update
        // an existing item or create a new one
        binding?.btnPartyDialogDone?.setOnClickListener {

            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    // Trigger the flow and start listening for values.
                    // Note that this happens when lifecycle is STARTED and stops
                    // collecting when the lifecycle is STOPPED
                    viewModel.response.collect { response ->
                        when (response) {
                            is DataState.Init -> {
                                val name = binding?.etPartyDialog?.text.toString()
                                if (name.isNotEmpty()) {
                                    if (editingState == EditingState.EXISTING_PARTY)
                                        viewModel.addData(receivedId, name)
                                    else
                                        viewModel.addData(-1, name)
                                } else {
                                    Toast.makeText(requireContext(), "Empty name is not allowed!", Toast.LENGTH_LONG).show()
                                }
                            }
                            is DataState.Loading -> showProgress(true)
                            is DataState.Data -> {
                                showProgress(false)
                                Toast.makeText(requireContext(), "response ${response.data}", Toast.LENGTH_LONG).show()
                                dismiss()
                            }
                            is DataState.Error -> {
                                showProgress(false)
                                Toast.makeText(requireContext(), "response ${response.error}", Toast.LENGTH_LONG).show()
                                dismiss()
                            }
                        }
                    }
                }
            }


        }
        binding?.btnPartyDialogCancel?.setOnClickListener {
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun showProgress(isVisible: Boolean) {
        if (isVisible) {
            binding?.btnsPartyDialog?.visibility = View.INVISIBLE
            binding?.pbPartyDialog?.visibility = View.VISIBLE
        } else {
            binding?.btnsPartyDialog?.visibility = View.VISIBLE
            binding?.pbPartyDialog?.visibility = View.INVISIBLE
        }
    }
}