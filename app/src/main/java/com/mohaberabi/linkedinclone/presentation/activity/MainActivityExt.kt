package com.mohaberabi.linkedinclone.presentation.activity

import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.mohaberabi.linkedinclone.R


fun MainActivity.rootNavController(): NavController {
    val navHostFragment = supportFragmentManager
        .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
    val navController = navHostFragment.navController
    return navController
}