package com.mohaberabi.linkedinclone.presentation.activity.constants

import com.mohaberabi.in_app_notifications.R.id.inAppNotificationsFragment
import com.mohaberabi.jobs.R.id.jobsFragments
import com.mohaberabi.linkedinclone.presentation.activity.constants.AppConstants.bottomNavRoutes
import com.mohaberabi.onboarding.R.id.onBoardingFragment
import com.mohaberabi.posts.R.id.postsFragment
import com.mohaberabi.suggested_connection.R.id.suggestedConnectionFragment
import com.mohaberabi.user_media.R.id.profilePictureFragment
import com.mohaberabi.login.R.id.loginFragment
import com.mohaberabi.register.R.id.registerFragment

object AppConstants {
    val bottomNavRoutes = setOf(
        postsFragment,
        jobsFragments,
        suggestedConnectionFragment,
        inAppNotificationsFragment,
    )

    val appbarRoutesConfig = bottomNavRoutes + onBoardingFragment

}

enum class RouteType {
    TopLevel,
    NonAuthed,
    Normal,
    StandAlone;

    val isTopRoute: Boolean
        get() = this == TopLevel

    val isStandAlone: Boolean
        get() = this == StandAlone
}

val Int.toRouteType: RouteType
    get() = when (this) {
        in bottomNavRoutes -> RouteType.TopLevel
        loginFragment,
        registerFragment,
        onBoardingFragment,
        -> RouteType.NonAuthed

        profilePictureFragment -> RouteType.StandAlone
        else -> RouteType.Normal
    }




