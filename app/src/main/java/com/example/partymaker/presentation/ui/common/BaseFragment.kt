package com.example.partymaker.presentation.ui.common

import androidx.fragment.app.Fragment

open class BaseFragment: Fragment() {

    private val presentationComponent by lazy {
        (requireActivity() as BaseActivity).activityComponent.getPresentationComponentFactory().create()
    }

    protected val injector get() = presentationComponent
}
