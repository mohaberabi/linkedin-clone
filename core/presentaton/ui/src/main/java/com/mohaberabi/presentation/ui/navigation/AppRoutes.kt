package com.mohaberabi.presentation.ui.navigation

private object NavDeepLinks {
    const val ADD_POST = "addPost"
    const val PROFILE = "profile"
    const val Profile_Pic = "profilePic"
    const val POST_DETAIL = "postDetails"
    const val POSTS = "posts"
    const val Login = "login"
    const val Register = "register"

}

sealed class AppRoutes(
    val route: String,
    val args: List<Pair<String, Any>> = listOf(),
) {
    data object Login : AppRoutes(NavDeepLinks.Login)
    data object Register : AppRoutes(NavDeepLinks.Register)
    data object Posts : AppRoutes(NavDeepLinks.POSTS)
    data object AddPost : AppRoutes(NavDeepLinks.ADD_POST)
    data object Profile : AppRoutes(NavDeepLinks.PROFILE)
    data class ProfilePic(val imgUri: String) : AppRoutes(
        route = NavDeepLinks.Profile_Pic,
        args = listOf("imgUri" to imgUri)
    )

    data class PostDetail(
        val postId: String
    ) : AppRoutes(
        NavDeepLinks.POST_DETAIL,
        args = listOf("postId" to postId)
    )


}