package com.example.partymaker.presentation.ui.parties.details.pager

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.partymaker.databinding.FragmentCocktailListBinding

private const val PARTY_ID = "PARTY_ID"

class CocktailListFragment : Fragment() {

    private var binding: FragmentCocktailListBinding? = null
    private var listener: OnCocktailAddListener? = null
    private var partyId: Long? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = parentFragment as OnCocktailAddListener
        } catch (e: ClassCastException) {
            throw ClassCastException(parentFragment.toString() + " must implement OnCocktailAddListener")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            partyId = it.getLong(PARTY_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentCocktailListBinding.inflate(inflater, container, false).root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentCocktailListBinding.bind(view)

        binding?.fabCocktailsList?.setOnClickListener {
            listener?.addCocktail()
            Log.d(TAG, "onViewCreated: result has been set")
        }
        parentFragment
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    override fun onDestroy() {
        listener = null
        super.onDestroy()
    }

    companion object {
        @JvmStatic
        fun newInstance(partyId: Long) =
            CocktailListFragment().apply {
                arguments = Bundle().apply {
                    putLong(PARTY_ID, partyId)
                }
            }
    }

    interface OnCocktailAddListener {
        fun addCocktail()
    }
}

private const val TAG = "CocktailListFragment"