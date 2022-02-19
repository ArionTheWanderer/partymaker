package com.example.partymaker.presentation.ui.parties

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
import androidx.navigation.ui.setupWithNavController
import com.example.partymaker.NavGraphDirections
import com.example.partymaker.databinding.FragmentPartyListBinding
import com.example.partymaker.domain.common.DataState
import com.example.partymaker.presentation.di.Injector
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import javax.inject.Inject

class PartyListFragment : Fragment() {

    private val onItemClickListener = object: PartyListRecyclerViewAdapter.OnItemClickListener {
        override fun onItemClick(itemId: Long, partyName: String) {
            val action = PartyListFragmentDirections.actionItemPartyFragmentToPartyDetailsFragment(itemId = itemId)
            findNavController().navigate(action)
        }

        override fun onDeleteButtonClick(itemId: Long, partyName: String) {
            val action = NavGraphDirections.actionGlobalPartyDeleteDialogFragment(itemId = itemId, partyName = partyName)
            findNavController().navigate(action)
        }
    }

    private val adapter = PartyListRecyclerViewAdapter(mutableListOf(), onItemClickListener)

    private var binding: FragmentPartyListBinding? = null

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: PartyListViewModel by viewModels{
        viewModelFactory
    }

    override fun onAttach(context: Context) {
        Injector.partyComponent()?.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentPartyListBinding.inflate(inflater, container, false).root

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentPartyListBinding.bind(view)
        val navController = findNavController()
        binding?.toolbarPartyList?.setupWithNavController(navController)
        binding?.layoutPartyListIncluded?.rvLayoutRecycler?.adapter = adapter
        binding?.fabPartyList?.setOnClickListener {
            val action = NavGraphDirections.actionGlobalPartyDialogFragment()
            findNavController().navigate(action)
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getPartyList()
                viewModel.partyList.collect { partyList ->
                    when (partyList) {
                        is DataState.Init -> {}
                        is DataState.Loading -> showProgress(true)
                        is DataState.Data -> {
                            showProgress(false)
                            adapter.setData(partyList.data)
                        }
                        is DataState.Error -> {
                            showProgress(false)
                            binding?.root?.let {
                                Snackbar.make(it, "Error ${partyList.error}", Snackbar.LENGTH_SHORT).show()
                            }
                            viewModel.resetErrorMessage()
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
            binding?.fabPartyList?.visibility = View.INVISIBLE
            binding?.layoutPartyListIncluded?.rvLayoutRecycler?.visibility = View.INVISIBLE
            binding?.layoutPartyListIncluded?.pbLayoutRecycler?.visibility = View.VISIBLE
        } else {
            binding?.fabPartyList?.visibility = View.VISIBLE
            binding?.layoutPartyListIncluded?.rvLayoutRecycler?.visibility = View.VISIBLE
            binding?.layoutPartyListIncluded?.pbLayoutRecycler?.visibility = View.INVISIBLE
        }
    }

}
