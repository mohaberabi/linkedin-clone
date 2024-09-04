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
        when {
            isTopLevelRoute(destination.id) -> {
                bottomNavigationView.show()
                with(appBar) {
                    show()
                    showSearchField(true)
                    showAvatar(true)
                }
                appDrawerLayout.unlock()
            }

            else -> {
                bottomNavigationView.hide()
                with(appBar) {
                    show()
                    showSearchField(false)
                    showAvatar(false)
                }
                appDrawerLayout.unlock()
            }


        }
    }
    bottomNavigationView.setOnItemSelectedListener { item ->
        when (item.itemId) {
            R.id.nav_item_addPost -> {
                val enterAnim = com.mohaberabi.core.presentation.ui.R.anim.enter_bottom_top
                val exitAnim = com.mohaberabi.core.presentation.ui.R.anim.exit_bottom_to_top
                navController
                    .goTo(AppRoutes.AddPost) {
                        setEnterAnim(enterAnim)
                        setExitAnim(exitAnim)
                        setPopEnterAnim(enterAnim)
                        setPopExitAnim(exitAnim)
                    }
            }

            else -> NavigationUI.onNavDestinationSelected(item, navController)
        }
        true
    }
}


private fun isTopLevelRoute(id: Int) = when (id) {
    com.mohaberabi.posts.R.id.postsFragment,
    com.mohaberabi.jobs.R.id.jobsFragments -> true

    else -> false
}