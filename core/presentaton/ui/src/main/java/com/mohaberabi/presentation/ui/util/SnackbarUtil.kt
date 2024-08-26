package com.mohaberabi.presentation.ui.util

import android.view.View
import com.google.android.material.snackbar.Snackbar


fun View.showSnackBar(
    message: String,
) {
    Snackbar.make(
        this,
        message,
        Snackbar.LENGTH_LONG
    ).also {
        it.show()
    }
}