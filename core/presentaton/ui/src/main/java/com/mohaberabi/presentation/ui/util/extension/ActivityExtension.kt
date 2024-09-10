package com.mohaberabi.presentation.ui.util.extension

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


fun Activity.addDefaultPaddings(rootView: View) {
    ViewCompat.setOnApplyWindowInsetsListener(rootView) { view, insets ->
        val systemBarsInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
        view.setPadding(
            systemBarsInsets.left,
            systemBarsInsets.top,
            systemBarsInsets.right,
            systemBarsInsets.bottom,
        )
        insets
    }
}

