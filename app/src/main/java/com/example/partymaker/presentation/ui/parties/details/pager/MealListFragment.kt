package com.example.partymaker.presentation.ui.parties.details.pager

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.partymaker.databinding.FragmentMealListBinding

class MealListFragment : Fragment() {

    private var binding: FragmentMealListBinding? = null
    private var listener: OnMealAddListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = parentFragment as OnMealAddListener
        } catch (e: ClassCastException) {
            throw ClassCastException(parentFragment.toString() + " must implement OnMealAddListener")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentMealListBinding.inflate(inflater, container, false).root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentMealListBinding.bind(view)

        binding?.fabDishesList?.setOnClickListener {
            listener?.addMeal()
            Log.d(TAG, "onViewCreated: result has been set")
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

    interface OnMealAddListener {
        fun addMeal()
    }
}

private const val TAG = "MealListFragment"