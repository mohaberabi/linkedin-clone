package com.mohaberabi.presentation.ui.util

import android.view.View
import com.google.android.material.snackbar.Snackbar


fun View.showSnackBar(
    message: String,
) {
    var snackBar: Snackbar? = null
    snackBar?.dismiss()
    snackBar = Snackbar.make(
        this,
        message,
        Snackbar.LENGTH_LONG
    ).also {
        it.show()
    }
}