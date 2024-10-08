package com.mohaberabi.presentation.ui.navigation

import com.mohaberabi.presentation.ui.util.NavTransition


private object NavDeepLinks {
    const val ADD_POST = "addPost"
    const val PROFILE = "profile"
    const val User_Media = "userMedia"
    const val POST_DETAIL = "postDetails"
    const val POSTS = "posts"
    const val Login = "login"
    const val Register = "register"
    const val SavedPosts = "savedPosts"
    const val Settings = "settings"
    const val ProfileViews = "profileViews"
    const val OnBoarding = "onboarding"

}

sealed class AppRoutes(
    val route: String,
    val args: List<Pair<String, Any?>> = listOf(),
    var navTransition: NavTransition? = null,
) {
    data object OnBoarding : AppRoutes(NavDeepLinks.OnBoarding)

    data object Settings : AppRoutes(NavDeepLinks.Settings)
    data object ProfileViews : AppRoutes(NavDeepLinks.ProfileViews)
    data object Login : AppRoutes(NavDeepLinks.Login)
    data object Register : AppRoutes(NavDeepLinks.Register)
    data object Posts : AppRoutes(NavDeepLinks.POSTS)
    data object SavedPosts : AppRoutes(NavDeepLinks.SavedPosts)
    data object AddPost :
        AppRoutes(
            NavDeepLinks.ADD_POST,
            navTransition = NavTransition.BottomToTop
        )

    data class Profile(val uid: String? = null) :
        AppRoutes(
            NavDeepLinks.PROFILE,
            args = uid?.let {
                listOf(
                    "uid" to it,
                )
            } ?: emptyList()
        )

    data class UserMedia(
        val imgUri: String,
        val isCover: Boolean = false,
    ) : AppRoutes(
        route = NavDeepLinks.User_Media,
        args = listOf(
            "imgUri" to imgUri,
            "isCover" to isCover,
        )
    )

    data class PostDetail(
        val postId: String
    ) : AppRoutes(
        route = NavDeepLinks.POST_DETAIL,
        args = listOf("postId" to postId),
        navTransition = NavTransition.LeftToRight,
    )
}