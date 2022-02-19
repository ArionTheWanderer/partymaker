package com.example.partymaker.presentation.ui.dialogs

import android.content.Context
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
import com.example.partymaker.databinding.FragmentPartyDeleteDialogBinding
import com.example.partymaker.domain.common.DataState
import com.example.partymaker.presentation.di.Injector
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.launch
import javax.inject.Inject

class PartyDeleteDialogFragment: BottomSheetDialogFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: PartyDeleteDialogViewModel by viewModels {
        viewModelFactory
    }

    private val args: PartyDeleteDialogFragmentArgs by navArgs()
    private var binding: FragmentPartyDeleteDialogBinding? = null

    override fun onAttach(context: Context) {
        Injector.partyComponent()?.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentPartyDeleteDialogBinding.inflate(inflater, container, false).root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentPartyDeleteDialogBinding.bind(view)
        val receivedId = args.itemId
        val receivedPartyName = args.partyName

        binding?.tvPartyDeleteDialogName?.text = receivedPartyName

        binding?.btnPartyDeleteDialogYes?.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.deleteParty(receivedId)
                    viewModel.response.collect { response ->
                        when (response) {
                            is DataState.Init -> {}
                            is DataState.Loading -> showProgress(true)
                            is DataState.Data -> {
                                showProgress(false)
                                dismiss()
                            }
                            is DataState.Error -> {
                                showProgress(false)
                                Toast.makeText(requireContext(), "Cannot delete", Toast.LENGTH_SHORT).show()
                                viewModel.resetErrorMessage()
                            }
                        }
                    }
                }
            }
        }

        binding?.btnPartyDeleteDialogCancel?.setOnClickListener {
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun showProgress(isVisible: Boolean) {
        if (isVisible) {
            binding?.btnsPartyDeleteDialog?.visibility = View.INVISIBLE
            binding?.pbPartyDeleteDialog?.visibility = View.VISIBLE
        } else {
            binding?.btnsPartyDeleteDialog?.visibility = View.VISIBLE
            binding?.pbPartyDeleteDialog?.visibility = View.INVISIBLE
        }
    }
}
