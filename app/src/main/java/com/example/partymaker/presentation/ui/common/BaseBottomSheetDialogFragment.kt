package com.example.partymaker.presentation.ui.common

import com.google.android.material.bottomsheet.BottomSheetDialogFragment

open class BaseBottomSheetDialogFragment: BottomSheetDialogFragment() {

    private val presentationComponent by lazy {
        (requireActivity() as BaseActivity).activityComponent.getPresentationComponentFactory().create()
    }

    protected val injector get() = presentationComponent
}
