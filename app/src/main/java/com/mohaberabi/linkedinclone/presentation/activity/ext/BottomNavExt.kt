package com.mohaberabi.linkedinclone.presentation.activity.ext

import androidx.navigation.NavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mohaberabi.in_app_notifications.R.id.nav_graph_app_notifications
import com.mohaberabi.linkedinclone.R
import com.mohaberabi.presentation.ui.navigation.AppRoutes
import com.mohaberabi.presentation.ui.navigation.goTo


fun BottomNavigationView.setupAppBottomNavigation(
    navController: NavController,
    onReadAllNotifications: () -> Unit
) {
    setupWithNavController(navController)
    setOnItemSelectedListener { item ->
        if (item.itemId == R.id.nav_item_addPost) {
            navController.goTo(AppRoutes.AddPost)
        } else {
            if (item.itemId == nav_graph_app_notifications) {
                onReadAllNotifications()
            }
            NavigationUI.onNavDestinationSelected(item, navController)
        }
        true
    }
}

fun BottomNavigationView.addNotificationsBadge(
    unreadNotifications: Int = 100
) {
    if (unreadNotifications > 0) {
        val badge = getOrCreateBadge(nav_graph_app_notifications)
        badge.isVisible = true
        badge.number = unreadNotifications
    } else {
        removeBadge(nav_graph_app_notifications)
    }
}
