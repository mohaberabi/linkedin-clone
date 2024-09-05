package com.mohaberabi.linkedinclone.presentation.activity

import androidx.navigation.NavController
import androidx.navigation.ui.NavigationUI
import coil.transform.CircleCropTransformation
import com.mohaberabi.linkedinclone.R
import com.mohaberabi.linkedinclone.current_user.CurrentUserState
import com.mohaberabi.linkedinclone.databinding.ActivityMainBinding
import com.mohaberabi.linkedinclone.databinding.NavHeaderBinding
import com.mohaberabi.presentation.ui.navigation.AppRoutes
import com.mohaberabi.presentation.ui.navigation.goTo
import com.mohaberabi.presentation.ui.util.extension.cachedImage
import com.mohaberabi.presentation.ui.util.extension.hide
import com.mohaberabi.presentation.ui.util.extension.show
import com.mohaberabi.presentation.ui.util.unlock

import com.mohaberabi.posts.R.id.postsFragment
import com.mohaberabi.suggested_connection.R.id.suggestedConnectionFragment
import com.mohaberabi.jobs.R.id.jobsFragments

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


fun ActivityMainBinding.listenToNavGraphDestinations(
    navController: NavController,
) {
    navController.addOnDestinationChangedListener { _, destination, _ ->
        if (destination.id.isTopLevelRoute) {
            showTopLevelUI()
        } else {
            showSecondaryUI()
        }
    }
    bottomNavigationView.setOnItemSelectedListener { item ->
        when (item.itemId) {
            R.id.nav_item_addPost -> navController.goTo(AppRoutes.AddPost)
            else -> NavigationUI.onNavDestinationSelected(item, navController)
        }
        true
    }
}


private val Int.isTopLevelRoute
    get() = when (this) {
        postsFragment,
        suggestedConnectionFragment,
        jobsFragments -> true

        else -> false
    }

private fun ActivityMainBinding.showTopLevelUI() {
    bottomNavigationView.show()
    with(appBar) {
        show()
        showSearchField(true)
        showAvatar(true)
    }
    appDrawerLayout.unlock()
}

private fun ActivityMainBinding.showSecondaryUI() {
    bottomNavigationView.hide()
    with(appBar) {
        show()
        showSearchField(false)
        showAvatar(false)
    }
    appDrawerLayout.unlock()
}
