package com.mohaberabi.presentation.ui.navigation

import android.net.Uri
import androidx.core.net.toUri
import androidx.navigation.NavController
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavOptions


object NavDeepLinks {
    const val APP_DOMAIN = "android-app://linked.clone.app/"
    const val Job_Detail = "fragment_job_detail"
    const val LAYOUT = "layoutFragment"
    const val ADD_POST = "fragment_add_post"
    
}


fun NavController.deepLinkNavigate(
    fragmentId: String,
    args: List<Pair<String, Any>> = listOf(),
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

fun NavController.popAllAndNavigate(
    fragmentId: String,
) {
    val uri = buildString {
        append(NavDeepLinks.APP_DOMAIN)
        append(fragmentId)
    }.toUri()

    val request = NavDeepLinkRequest.Builder
        .fromUri(uri)
        .build()

    val navOptions = NavOptions.Builder()
        .setPopUpTo(
            graph.startDestinationId,
            inclusive = true
        )
        .build()

    navigate(request, navOptions)
}

fun NavController.popAllAndNavigate(
    destinationId: Int,
    builder: NavOptions.Builder.() -> Unit = {},
) {
    val navOptions = NavOptions.Builder().apply {
        setPopUpTo(graph.startDestinationId, true)
        builder()
    }.build()

    navigate(destinationId, null, navOptions)
}