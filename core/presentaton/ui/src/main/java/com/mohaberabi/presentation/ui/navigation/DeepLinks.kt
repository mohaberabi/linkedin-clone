package com.mohaberabi.presentation.ui.navigation

import android.net.Uri
import androidx.core.net.toUri
import androidx.navigation.NavController
import androidx.navigation.NavDeepLinkRequest


object NavDeepLinks {
    const val APP_DOMAIN = "android-app://linked.clone.app/"
    const val Job_Detail = "fragment_job_detail"
}


fun NavController.deepLinkNavigate(
    fragmentId: String,
    args: List<Pair<String, Any>> = listOf()
) {
    val uri = buildString {
        append(NavDeepLinks.APP_DOMAIN)
        append(fragmentId)
        if (args.isNotEmpty()) {
            append("?")
            args.joinToString("&") { "${it.first}=${Uri.encode(it.second.toString())}" }
                .also { append(it) }
        }
    }.toUri()
    val request = NavDeepLinkRequest.Builder
        .fromUri(uri)
        .build()
    navigate(request)
}