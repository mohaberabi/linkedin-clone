package com.mohaberabi.linkedinclone.presentation.activity

import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import coil.transform.CircleCropTransformation
import com.mohaberabi.linkedinclone.R
import com.mohaberabi.linkedinclone.databinding.ActivityMainBinding
import com.mohaberabi.linkedinclone.databinding.NavHeaderBinding
import com.mohaberabi.linkedinclone.presentation.activity.viewmodel.MainActivityState
import com.mohaberabi.presentation.ui.navigation.AppRoutes
import com.mohaberabi.presentation.ui.navigation.goTo
import com.mohaberabi.presentation.ui.util.extension.cachedImage
import com.mohaberabi.presentation.ui.util.extension.hide
import com.mohaberabi.presentation.ui.util.extension.show
import com.mohaberabi.presentation.ui.util.openDrawer

fun ActivityMainBinding.bind(
    state: MainActivityState,
    onGoProfile: () -> Unit
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
            avatarImage.setOnClickListener {
                onGoProfile()
            }
        }
        appBar.loadImgUrl(it.img)
        appBar.setOnAvatarClickListener {
            appDrawerLayout.openDrawer()
        }
    }
}


fun ActivityMainBinding.listenToNavGraphDestinations(
    navController: NavController,
) {
    navController.addOnDestinationChangedListener { _, destination, _ ->
        when (destination.id) {

            com.mohaberabi.posts.R.id.postsFragment,
            com.mohaberabi.jobs.R.id.jobsFragments -> {
                bottomNavigationView.show()
                appBar.showSearchField(true)
                appBar.showAvatar(true)
            }

            else -> {
                bottomNavigationView.hide()
                appBar.showSearchField(false)
                appBar.showAvatar(false)
            }
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