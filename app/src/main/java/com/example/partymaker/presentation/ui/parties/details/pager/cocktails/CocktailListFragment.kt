package com.example.partymaker.presentation.ui.parties.details.pager.cocktails

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import com.bumptech.glide.RequestManager
import com.example.partymaker.databinding.FragmentCocktailListBinding
import com.example.partymaker.domain.common.DataState
import com.example.partymaker.presentation.ui.common.BaseFragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val PARTY_ID = "PARTY_ID"

class CocktailListFragment : BaseFragment() {

    @Inject
    lateinit var glide: RequestManager

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: CocktailListViewModel by viewModels {
        viewModelFactory
    }

    private lateinit var adapter: CocktailListRecyclerViewAdapter

    private var binding: FragmentCocktailListBinding? = null
    private var listener: OnCocktailListListener? = null
    private var partyId: Long? = null


    override fun onAttach(context: Context) {
        injector.inject(this)
        super.onAttach(context)
        try {
            listener = parentFragment as OnCocktailListListener
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

    @SuppressLint("UnsafeRepeatOnLifecycleDetector")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentCocktailListBinding.bind(view)

        binding?.fabCocktailsList?.setOnClickListener {
            listener?.addCocktail()
            Log.d(TAG, "onViewCreated: result has been set")
        }

        val onItemClickListener = object: CocktailListRecyclerViewAdapter.OnItemClickListener {
            override fun onItemClick(cocktailId: Long, cocktailName: String) {
                listener?.navigateCocktails(cocktailId, cocktailName)
            }

            override fun onDeleteButtonClick(cocktailId: Long) {
                viewLifecycleOwner.lifecycleScope.launch {
                    whenStarted {
                        viewModel.deleteCocktail(cocktailId, partyId ?: 0)
                    }
                }
            }
        }
        adapter = CocktailListRecyclerViewAdapter(mutableListOf(), onItemClickListener, glide)
        binding?.layoutCocktailsIncluded?.rvLayoutRecycler?.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getCocktailList(partyId ?: 0)
                viewModel.cocktailList.collect { cocktailList ->
                    when (cocktailList) {
                        is DataState.Init -> {}
                        is DataState.Loading -> {}
                        is DataState.Data -> {
                            adapter.setData(cocktailList.data)
                        }
                        is DataState.Error -> {
                            binding?.root?.let {
                                Snackbar.make(it, cocktailList.error, Snackbar.LENGTH_SHORT).show()
                            }
                            viewModel.resetErrorMessage()
                        }
                    }
                }
            }
        }
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

    interface OnCocktailListListener {
        fun addCocktail()
        fun navigateCocktails(cocktailId: Long, cocktailName: String)
    }
}

private const val TAG = "CocktailListFragment"