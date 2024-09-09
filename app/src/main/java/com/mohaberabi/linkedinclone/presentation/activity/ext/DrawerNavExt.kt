package com.mohaberabi.linkedinclone.presentation.activity.ext

import com.mohaberabi.linkedinclone.databinding.ActivityMainBinding
import com.mohaberabi.presentation.ui.navigation.AppRoutes


fun ActivityMainBinding.setupDrawerNavigation(
    onDrawerNavigate: (AppRoutes) -> Unit,
) {
    val drawer = mainDrawer
    val drawerRoutes = with(drawer) {
        listOf(
            avatarImage to AppRoutes.Profile(),
            settingsButton to AppRoutes.Settings,
            savedPosts to AppRoutes.SavedPosts,
            profileViews to AppRoutes.ProfileViews
        )
    }
    drawerRoutes.forEach { (view, route) ->
        view.setOnClickListener {
            onDrawerNavigate(route)
        }
    }
}