package com.mohaberabi.linkedinclone.presentation.activity

import coil.transform.CircleCropTransformation
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mohaberabi.linkedinclone.current_user.presentation.CurrentUserState
import com.mohaberabi.linkedinclone.databinding.ActivityMainBinding
import com.mohaberabi.linkedinclone.databinding.NavHeaderBinding
import com.mohaberabi.presentation.ui.util.extension.cachedImage
import com.mohaberabi.in_app_notifications.R.id.nav_graph_app_notifications
import com.mohaberabi.presentation.ui.navigation.AppRoutes
import com.mohaberabi.presentation.ui.util.openDrawer


fun ActivityMainBinding.bindWithCurrentUserState(
    state: CurrentUserState,
) {
    val headerView = appDrawerView.getHeaderView(0)
    val mainDrawerBinding = NavHeaderBinding.bind(headerView)
    state.user?.let {
        with(
            mainDrawerBinding,
        ) {
            userBio.text = it.bio
            userName.text = "${it.name} ${it.lastname}"
            avatarImage.cachedImage(it.img) {
                transformations(CircleCropTransformation())
            }
        }
        appBar.loadImgUrl(it.img)

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
