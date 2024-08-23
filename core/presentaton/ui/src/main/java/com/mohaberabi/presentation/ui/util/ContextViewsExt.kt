package com.mohaberabi.presentation.ui.util

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import com.mohaberabi.core.presentation.ui.R


fun Context.createLoadingDialog(
): AlertDialog {
    val builder = AlertDialog.Builder(this)
    val inflater = LayoutInflater.from(this)
    val dialogView = inflater.inflate(R.layout.loading_dialog, null)
    builder.setView(dialogView)
    builder.setCancelable(false)
    return builder.create()
}