package com.example.partymaker.presentation.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.partymaker.R
import com.example.partymaker.databinding.FragmentPartiesListBinding
import com.example.partymaker.presentation.ui.placeholder.PlaceholderContent

/**
 * A fragment representing a list of Items.
 */
class PartiesFragment : Fragment() {

    private val adapter = PartiesRecyclerViewAdapter(PlaceholderContent.ITEMS)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        FragmentPartiesListBinding.inflate(inflater, container, false).root


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentPartiesListBinding.bind(view)
        binding.rvParties.adapter = adapter
    }
}