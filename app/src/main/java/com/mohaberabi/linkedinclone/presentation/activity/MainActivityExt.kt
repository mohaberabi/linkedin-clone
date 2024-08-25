package com.mohaberabi.linkedinclone.presentation.activity

import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import coil.transform.CircleCropTransformation
import com.mohaberabi.linkedinclone.R
import com.mohaberabi.linkedinclone.databinding.ActivityMainBinding
import com.mohaberabi.linkedinclone.databinding.NavHeaderBinding
import com.mohaberabi.linkedinclone.presentation.activity.viewmodel.MainActivityState
import com.mohaberabi.presentation.ui.util.cachedImage


fun MainActivity.rootNavController(): NavController {
    val navHostFragment = supportFragmentManager
        .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
    val navController = navHostFragment.navController
    return navController
}

fun ActivityMainBinding.bind(
    state: MainActivityState,
    onAvatarClick: () -> Unit,
) {
    val headerView = navView.getHeaderView(0)
    val mainDrawerBinding = NavHeaderBinding.bind(headerView)
    state.user?.let {
        with(mainDrawerBinding) {
            userBio.text = it.bio
            userName.text = "${it.name} ${it.lastname}"
            avatarImage.cachedImage(it.img) {
                transformations(CircleCropTransformation())
            }
            avatarImage.setOnClickListener {
                onAvatarClick()
            }
        }

    }
}