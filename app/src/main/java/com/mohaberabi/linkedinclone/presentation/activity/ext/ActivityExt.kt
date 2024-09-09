package com.mohaberabi.linkedinclone.presentation.activity.ext

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.mohaberabi.linkedinclone.R


val AppCompatActivity.rootNavController: NavController
    get() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        return navController
    }
