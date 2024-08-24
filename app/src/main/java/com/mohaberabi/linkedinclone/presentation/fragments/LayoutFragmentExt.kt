package com.mohaberabi.linkedinclone.presentation.fragments

import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.mohaberabi.linkedinclone.R


fun LayoutFragment.layoutNavController(): NavController {
    val navHostFragment =
        childFragmentManager.findFragmentById(R.id.nav_host_layout_fragment) as NavHostFragment
    val navController = navHostFragment.navController
    return navController
}