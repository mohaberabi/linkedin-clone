package com.mohaberabi.linkedinclone.presentation.activity

import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.mohaberabi.in_app_notifications.R.id.inAppNotificationsFragment
import com.mohaberabi.jobs.R.id.jobsFragments
import com.mohaberabi.linkedinclone.R
import com.mohaberabi.posts.R.id.postsFragment
import com.mohaberabi.suggested_connection.R.id.suggestedConnectionFragment


val MainActivity.rootNavController: NavController
    get() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        return navController
    }


val Int.isTopLevelRoute
    get() = when (this) {
        postsFragment,
        suggestedConnectionFragment,
        inAppNotificationsFragment,
        jobsFragments -> true

        else -> false
    }


