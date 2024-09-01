package com.mohaberabi.presentation.ui.views.post_item

import android.view.View
import android.widget.TextView
import coil.load
import coil.transform.CircleCropTransformation
import com.mohaberabi.core.presentation.design_system.R
import com.mohaberabi.core.presentation.ui.databinding.PostListItemBinding
import com.mohaberabi.linkedin.core.domain.model.PostModel
import com.mohaberabi.presentation.ui.util.extension.cachedImage
import com.mohaberabi.presentation.ui.util.extension.hide
import com.mohaberabi.presentation.ui.util.extension.hideAndKeep
import com.mohaberabi.presentation.ui.util.extension.icon
import com.mohaberabi.presentation.ui.util.extension.label
import com.mohaberabi.presentation.ui.util.extension.show
import com.mohaberabi.presentation.ui.util.toTimeAgo


data class PostClickCallBacks(
    val onClick: (PostModel) -> Unit = {},
    val onLongClickLike: (PostModel) -> Unit = {},
    val onLikeClick: (PostModel) -> Unit = {},
)

fun PostListItemBinding.bindFromPost(
    post: PostModel,
    onClickCallBacks: PostClickCallBacks,
) {

    val context = root.context
    val commentCount = post.commentsCount
    val repostsCount = post.repostsCount
    val reactionsCount = post.reactionsCount
    val reactions = arrayOf(
        likeReact,
        loveReact,
        funnyReact,
    )
    rootLayout.setOnClickListener {
        onClickCallBacks.onClick(post)
    }
    reactions.forEach {
        it.showIf(
            post.reactionsCount > 0,
            keepOnTree = true
        )
    }
    repostsCountTextView.showIf(
        repostsCount > 0,
    ) {
        text = context.getString(
            R.string.repost_count,
            repostsCount
        )
    }
    commentsCountTextView.showIf(
        commentCount > 0,
    ) {
        text = context.getString(
            R.string.comment_count,
            commentCount
        )
    }
    reactionsCountTextView.showIf(
        reactionsCount > 0,
    ) {
        text = "$reactionsCount"
    }


    likeButton.setClickListener {
        onClickCallBacks.onLikeClick.invoke(post)
    }
    likeButton.setOnLongClickListener {
        onClickCallBacks.onLongClickLike(post)
        true
    }
    post.currentUserReaction?.let { react ->
        likeButton.setIcon(react.icon)
        likeButton.setText(root.context.getString(react.label))
    } ?: run {
        likeButton.setIcon(R.drawable.ic_react)
    }
    postDataTextView.setText(
        post.postData,
        maxLines = 5
    )
    issuerBioTextView.text = post.issuerBio
    createdAtTextView.text = post.createdAtMillis.toTimeAgo(root.context)
    issuerNameTextView.text = post.issuerName
    issuerAvatarImageView.cachedImage(post.issuerAvatar) {
        transformations(CircleCropTransformation())
    }
    postAttachedImageView.showIf(
        post.postAttachedImg.isNotEmpty(),
    ) { cachedImage(post.postAttachedImg) }
}

inline fun <reified T : View> T.showIf(
    predicate: Boolean,
    keepOnTree: Boolean = false,
    action: T.() -> Unit = {},
): T {
    if (!predicate) {
        if (keepOnTree) {
            hideAndKeep()
        } else {
            hide()
        }
    } else {
        show()
        this.apply(action)
    }
    return this
}

