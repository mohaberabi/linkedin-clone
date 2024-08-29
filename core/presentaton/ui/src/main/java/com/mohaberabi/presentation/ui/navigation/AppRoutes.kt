package com.mohaberabi.presentation.ui.navigation

private object NavDeepLinks {
    const val LAYOUT = "layoutFragment"
    const val ADD_POST = "fragment_add_post"
    const val PROFILE = "profile_fragment"
    const val Profile_Pic = "ProfilePictureFragment"
    const val POST_DETAIL = "fragment_post_detail"
    const val POST_REACTIONS = "PostReactionsFragment"

}

sealed class AppRoutes(
    val route: String,
    val args: List<Pair<String, Any>> = listOf(),
) {
    data object Layout : AppRoutes(NavDeepLinks.LAYOUT)
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


    data class PostReactions(
        val postId: String
    ) : AppRoutes(
        NavDeepLinks.POST_REACTIONS,
        args = listOf("postId" to postId)
    )

}