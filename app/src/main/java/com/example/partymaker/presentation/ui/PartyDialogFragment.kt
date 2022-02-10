package com.example.partymaker.presentation.ui

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.navArgs
import com.example.partymaker.R
import com.example.partymaker.databinding.FragmentPartyDialogBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class PartyDialogFragment : DialogFragment() {

    private enum class EditingState {
        NEW_PARTY,
        EXISTING_PARTY
    }

    private val args: PartyDialogFragmentArgs by navArgs()

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val editingState =
            if (args.itemId > 0) EditingState.EXISTING_PARTY
            else EditingState.NEW_PARTY

        val view = layoutInflater.inflate(R.layout.fragment_party_dialog, null)
        val binding = FragmentPartyDialogBinding.bind(view)
        binding.etPartyDialog.setText(args.partyName)
        return activity?.let {
            MaterialAlertDialogBuilder(it)
                .setTitle(resources.getString(R.string.party_dialog_title))
                .setView(binding.root)
                .setNegativeButton(resources.getString(R.string.party_dialog_cancel_btn)) { dialog, which ->
                    dismiss()
                }
                .setPositiveButton(resources.getString(R.string.party_dialog_create_btn)) { dialog, which ->
                    // Respond to positive button press
                }
                .create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}