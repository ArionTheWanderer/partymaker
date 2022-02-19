package com.example.partymaker.presentation.ui.parties.details.pager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.partymaker.databinding.FragmentCocktailListBinding

class CocktailListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentCocktailListBinding.inflate(inflater, container, false).root
}
