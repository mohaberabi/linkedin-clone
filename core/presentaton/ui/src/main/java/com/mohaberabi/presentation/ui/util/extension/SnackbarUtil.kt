package com.mohaberabi.presentation.ui.util.extension

import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.mohaberabi.presentation.ui.util.UiText


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

fun View.showSnackBar(
    message: UiText,
) {
    Snackbar.make(
        this,
        message.asString(this.context),
        Snackbar.LENGTH_LONG
    ).also {
        it.show()
    }
}