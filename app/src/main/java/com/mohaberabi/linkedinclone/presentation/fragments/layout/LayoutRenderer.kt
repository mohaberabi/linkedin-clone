package com.mohaberabi.linkedinclone.presentation.fragments.layout

import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.mohaberabi.linkedinclone.R
import com.mohaberabi.linkedinclone.databinding.FragmentLayoutBinding
import com.mohaberabi.linkedinclone.presentation.fragments.layout.viewmodel.LayoutState

fun LayoutFragment.layoutNavController(): NavController {
    val navHostFragment =
        childFragmentManager.findFragmentById(R.id.nav_host_layout_fragment) as NavHostFragment
    val navController = navHostFragment.navController
    return navController
}


fun FragmentLayoutBinding.bind(
    state: LayoutState,
    onAvatarClick: () -> Unit,

    ) {
    state.user?.let {
        appBar.loadImgUrl(it.img)
        appBar.setOnAvatarClickListener {
            onAvatarClick()
        }
    }

}

fun FragmentLayoutBinding.setupBottomNav(
    onAddPost: () -> Unit,
    navController: NavController,
) {
    bottomNavigationView.setupWithNavController(navController)
    bottomNavigationView.setOnItemSelectedListener { item ->
        when (item.itemId) {
            R.id.nav_item_addPost -> onAddPost()
            else -> NavigationUI.onNavDestinationSelected(item, navController)
        }
        true
    }

}

