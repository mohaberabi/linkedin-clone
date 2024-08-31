package com.mohaberabi.linkedinclone.post_detail.presetnation.post_details.fragments

import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.mohaberabi.linkedinclone.post_detail.databinding.FragmentPostDetailBinding
import com.mohaberabi.linkedinclone.post_detail.presetnation.post_details.fragments.list_adapters.CommentorListAdapter
import com.mohaberabi.linkedinclone.post_detail.presetnation.post_details.fragments.list_adapters.PostDetailReactorsAdapter
import com.mohaberabi.linkedinclone.post_detail.presetnation.post_details.viewmodel.PostDetailActions
import com.mohaberabi.linkedinclone.post_detail.presetnation.post_details.viewmodel.PostDetailState
import com.mohaberabi.linkedinclone.post_detail.presetnation.post_details.viewmodel.PostDetailStatus
import com.mohaberabi.presentation.ui.util.AppRecyclerViewScrollListener
import com.mohaberabi.presentation.ui.util.extension.hideAll
import com.mohaberabi.presentation.ui.util.extension.showAll
import com.mohaberabi.presentation.ui.util.extension.submitIfDifferent
import com.mohaberabi.presentation.ui.util.extension.submitOnce
import com.mohaberabi.presentation.ui.views.post_item.PostClickCallBacks


fun FragmentPostDetailBinding.setup(
    onAction: (PostDetailActions) -> Unit,
) {
    commentTextField.addTextChangedListener {
        onAction(PostDetailActions.CommentChanged(it.toString()))
    }
    commentOnPostButton.setButtonClickListener {
        onAction(PostDetailActions.SubmitComment)
    }

}

fun FragmentPostDetailBinding.bindWithState(
    state: PostDetailState,
    reactorsAdapter: PostDetailReactorsAdapter,
    commentAdapter: CommentorListAdapter,
    onPostClickCallbacks: PostClickCallBacks = PostClickCallBacks(),
    onLoadMoreComments: () -> Unit
) {

    val populatedViews = arrayOf(
        reactionsArea,
        postData,
        commentsArea,
        leaveCommentArea,
    )

    when (state.state) {
        PostDetailStatus.Loading -> {
            hideAll(
                *populatedViews,
                errorWidget
            )
            loader.show()
        }

        PostDetailStatus.Error -> {
            hideAll(
                *populatedViews,
                loader
            )
            errorWidget.show()
            errorWidget.setErrorTitle(state.error.asString(root.context))
        }

        PostDetailStatus.Populated -> {

            with(
                commentOnPostButton,
            ) {
                setLoading(state.commentLoading)
                setEnable(state.canComment)

            }
            reactorsAdapter.submitIfDifferent(
                state.topPostReactions,
            )
            commentAdapter.submitIfDifferent(
                state.postComments,
            )
            state.currentPost?.let {
                postData.bindFromPost(
                    post = it,
                    onClickCallBacks = onPostClickCallbacks
                )
                reactorsRecyclerView.submitOnce(
                    newLayoutManager = LinearLayoutManager(
                        root.context,
                        LinearLayoutManager.HORIZONTAL,
                        false,
                    ),
                    listAdapter = reactorsAdapter
                )

                commentsRecyclerView.isNestedScrollingEnabled = false
                commentsRecyclerView.submitOnce(
                    scrollListener = AppRecyclerViewScrollListener(
                        onPaginate = { onLoadMoreComments() },
                        isLinear = true,
                        threshold = 5,
                    ),
                    newLayoutManager = LinearLayoutManager(
                        root.context,
                    ),
                    listAdapter = commentAdapter
                )
            }

            showAll(
                *populatedViews,
            )
            hideAll(
                loader,
                errorWidget
            )
        }
    }
}

