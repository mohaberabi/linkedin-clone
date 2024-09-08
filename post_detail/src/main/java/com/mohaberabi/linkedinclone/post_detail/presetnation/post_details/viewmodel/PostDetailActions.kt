package com.mohaberabi.linkedinclone.post_detail.presetnation.post_details.viewmodel


sealed interface PostDetailActions {
    data class CommentChanged(
        val comment: String,
    ) : PostDetailActions


    data object LoadMoreComments : PostDetailActions
    data object SubmitComment : PostDetailActions
    data class CurrentUIdChanged(val uid: String) : PostDetailActions

}