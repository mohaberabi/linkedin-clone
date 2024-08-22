package com.mohaberabi.presentation.ui.util

import android.widget.ImageView
import coil.load
import coil.request.ImageRequest
import com.mohaberabi.core.presentation.ui.R

fun ImageView.cached(
    url: String,
    builder: ImageRequest.Builder.() -> Unit = {}
) {
    load(url) {
        placeholder(R.drawable.img_plcholder)
        error(R.drawable.img_plcholder)
        crossfade(true)
        builder()
    }
}