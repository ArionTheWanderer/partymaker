package com.example.partymaker.presentation.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
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
import com.example.partymaker.presentation.di.Injector
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "PartyDialogFragment"

class PartyDialogFragment : BottomSheetDialogFragment() {

    private enum class EditingState {
        NEW_PARTY,
        EXISTING_PARTY
    }

    private val args: PartyDialogFragmentArgs by navArgs()
    private var binding: FragmentPartyDialogBinding? = null

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: PartyDialogViewModel by viewModels{
        viewModelFactory
    }

    override fun onAttach(context: Context) {
        Injector.partyComponent()?.inject(this)
        super.onAttach(context)
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
        val receivedPartyName = args.partyName

        val editingState =
            if (receivedId > 0 && receivedPartyName != "") EditingState.EXISTING_PARTY
            else EditingState.NEW_PARTY

        if (editingState == EditingState.EXISTING_PARTY) {
            binding?.etPartyDialog?.setText(receivedPartyName)
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
                                        viewModel.addData(0, name)
                                } else {
                                    Toast.makeText(requireContext(), "Empty name is not allowed!", Toast.LENGTH_SHORT).show()
                                }
                            }
                            is DataState.Loading -> showProgress(true)
                            is DataState.Data -> {
                                showProgress(false)
                                Log.d(TAG, "DataState.Data: ${response.data}")
                                when (editingState) {
                                    EditingState.EXISTING_PARTY ->
                                        Toast.makeText(requireContext(), "Successfully edited!", Toast.LENGTH_SHORT).show()
                                    EditingState.NEW_PARTY ->
                                        Toast.makeText(requireContext(), "Successfully created!", Toast.LENGTH_SHORT).show()
                                }
                                dismiss()
                            }
                            is DataState.Error -> {
                                showProgress(false)
                                Log.d(TAG, "DataState.Error: ${response.error}")
                                Toast.makeText(requireContext(), "Error has occurred", Toast.LENGTH_SHORT).show()
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
