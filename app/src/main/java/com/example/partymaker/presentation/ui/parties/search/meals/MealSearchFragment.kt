package com.example.partymaker.presentation.ui.parties.search.meals

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.partymaker.R
import com.example.partymaker.databinding.FragmentMealSearchBinding

class MealSearchFragment : Fragment(), SearchView.OnQueryTextListener {

    private var binding: FragmentMealSearchBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentMealSearchBinding.inflate(inflater, container, false).root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentMealSearchBinding.bind(view)
        val navController = findNavController()

        binding?.toolbarMealSearch?.inflateMenu(R.menu.menu_search)
        val searchItem = binding?.toolbarMealSearch?.menu?.findItem(R.id.action_search)
        val searchView = searchItem?.actionView as SearchView
        searchView.setOnQueryTextListener(this)
        searchView.queryHint = getString(R.string.meal_search_hint_search_view)
        binding?.toolbarMealSearch?.setupWithNavController(navController)
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null && query != "") {

        }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean = true
}
