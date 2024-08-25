package com.mohaberabi.presentation.ui.views

import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog


val BottomSheetDialog.bottomSheet
    get() = findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as? FrameLayout

fun FrameLayout.asPrimarySheet() {
    setBackgroundColor(resources.getColor(android.R.color.white, null))
    val coLayoutParams = layoutParams as CoordinatorLayout.LayoutParams
    layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT
    layoutParams = coLayoutParams
    val behavior = BottomSheetBehavior.from(this)
    behavior.state = BottomSheetBehavior.STATE_EXPANDED
    behavior.isDraggable = true
}