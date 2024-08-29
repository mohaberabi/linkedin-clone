package com.mohaberabi.linkedinclone.post_detail.presetnation.post_details.fragments

import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.mohaberabi.linkedinclone.post_detail.databinding.FragmentPostDetailBinding
import com.mohaberabi.linkedinclone.post_detail.presetnation.post_details.fragments.list_adapters.CommentorListAdapter
import com.mohaberabi.linkedinclone.post_detail.presetnation.post_details.fragments.list_adapters.PostDetailReactorsAdapter
import com.mohaberabi.linkedinclone.post_detail.presetnation.post_details.viewmodel.PostDetailActions
import com.mohaberabi.linkedinclone.post_detail.presetnation.post_details.viewmodel.PostDetailState
import com.mohaberabi.linkedinclone.post_detail.presetnation.post_details.viewmodel.PostDetailStatus
import com.mohaberabi.presentation.ui.util.extension.hideAll
import com.mohaberabi.presentation.ui.util.extension.showAll
import com.mohaberabi.presentation.ui.util.extension.submitIfDifferent
import com.mohaberabi.presentation.ui.util.extension.submitOnce
import com.mohaberabi.presentation.ui.views.post_item.PostClickCallBacks


fun FragmentPostDetailBinding.bind(
    state: PostDetailState,
    reactorsAdapter: PostDetailReactorsAdapter,
    commentAdapter: CommentorListAdapter,
    onPostClickCallbacks: PostClickCallBacks = PostClickCallBacks(),
    onAction: (PostDetailActions) -> Unit,
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
            commentTextField.addTextChangedListener {
                onAction(PostDetailActions.CommentChanged(it.toString()))
            }
            with(
                commentOnPostButton,
            ) {
                setLoading(state.commentLoading)
                setEnable(state.canComment)
                setButtonClickListener {
                    onAction(PostDetailActions.SubmitComment)
                }
            }
            state.details?.let { detail ->
                reactorsAdapter.submitIfDifferent(
                    detail.topReactions,
                )
                commentAdapter.submitIfDifferent(
                    detail.topComments,
                )
                detail.post?.let { post ->
                    postData.bindFromPost(
                        post = post,
                        onClickCallBacks = onPostClickCallbacks
                    )
                }
                reactorsRecyclerView.submitOnce(
                    newLayoutManager = LinearLayoutManager(
                        root.context,
                        LinearLayoutManager.HORIZONTAL,
                        false,
                    ),
                    listAdapter = reactorsAdapter
                )

                commentsRecyclerView.submitOnce(
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

