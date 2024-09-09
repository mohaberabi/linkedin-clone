package com.mohaberabi.linkedinclone.presentation.activity.ext

import coil.transform.CircleCropTransformation
import com.mohaberabi.linkedinclone.R
import com.mohaberabi.linkedinclone.current_user.presentation.CurrentUserState
import com.mohaberabi.linkedinclone.databinding.ActivityMainBinding
import com.mohaberabi.linkedinclone.databinding.NavHeaderBinding
import com.mohaberabi.linkedinclone.presentation.activity.constants.RouteType
import com.mohaberabi.presentation.ui.util.extension.cachedImage
import com.mohaberabi.presentation.ui.util.extension.showIf
import com.mohaberabi.presentation.ui.util.toggleEnabled

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
            userName.text = root.context.getString(R.string.full_name, it.name, it.lastname)
            avatarImage.cachedImage(it.img) {
                transformations(CircleCropTransformation())
            }
        }
        appBar.loadImgUrl(it.img)

    }
}


fun ActivityMainBinding.bindWithActivityState(
    type: RouteType,
    title: String? = null
) {
    val topRoute = type.isTopRoute
    val showAppBar = !type.isStandAlone
    val enableDrawer = topRoute || showAppBar
    bottomNavigationView.showIf(topRoute)
    appBar.showIf(showAppBar) {
        showAvatar(topRoute)
        showSearchField(topRoute)
        toggleTitleVisiblity(title != null)
        title?.let {
            setRouteTitle(it)
        }
    }
    appDrawerLayout.toggleEnabled(enableDrawer)
}

val ActivityMainBinding.mainDrawer: NavHeaderBinding
    get() {
        val headerView = appDrawerView.getHeaderView(0)
        return NavHeaderBinding.bind(headerView)
    }
