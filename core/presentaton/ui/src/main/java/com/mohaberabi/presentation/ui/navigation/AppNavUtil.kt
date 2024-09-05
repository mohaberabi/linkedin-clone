package com.mohaberabi.presentation.ui.navigation

import android.net.Uri
import androidx.core.net.toUri
import androidx.navigation.NavController
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.NavOptions

private const val APP_DOMAIN = "android-app://linked.clone.app/"


fun NavController.goTo(
    route: AppRoutes,
    builder: NavOptions.Builder.() -> Unit = {},
) {
    val navOptions = NavOptions.Builder().apply {
        route.navTransition?.let { transition ->
            setEnterAnim(transition.enterAnim)
            setExitAnim(transition.exitAnim)
            setPopEnterAnim(transition.popEnterAnim)
            setPopExitAnim(transition.popExitAnim)
        }
        builder()
    }.build()
    goTo(
        fragmentId = route.route,
        args = route.args,
        navOptions = navOptions,
    )
}


private fun NavController.goTo(
    fragmentId: String,
    args: List<Pair<String, Any>> = listOf(),
    navOptions: NavOptions = NavOptions.Builder().build()
) {
    val uri = buildString {
        append(APP_DOMAIN)
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
    navigate(request, navOptions)
}


fun NavController.popAllAndNavigate(
    route: AppRoutes,
) = popAllAndNavigate(route.route)

fun NavController.popAllAndNavigate(
    fragmentId: String,
) {
    val uri = buildString {
        append(APP_DOMAIN)
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

