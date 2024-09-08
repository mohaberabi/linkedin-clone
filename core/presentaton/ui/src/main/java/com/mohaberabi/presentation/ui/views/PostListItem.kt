package com.mohaberabi.presentation.ui.views


import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import coil.transform.CircleCropTransformation
import com.mohaberabi.core.presentation.ui.R
import com.mohaberabi.core.presentation.ui.databinding.PostListItemBinding
import com.mohaberabi.linkedin.core.domain.model.PostModel
import com.mohaberabi.presentation.ui.util.extension.cachedImage
import com.mohaberabi.presentation.ui.util.extension.icon
import com.mohaberabi.presentation.ui.util.extension.label
import com.mohaberabi.presentation.ui.util.extension.showIf
import com.mohaberabi.presentation.ui.util.toTimeAgo

class PostListItem @JvmOverloads constructor(
    context: Context,
    private val attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {


    private val binding: PostListItemBinding =
        PostListItemBinding.inflate(
            LayoutInflater.from(context),
            this,
            true
        )

    init {
        setCustomAttrs()
    }


    fun bindFromPost(
        post: PostModel,
        clickCallBacks: PostClickCallBacks,
    ) {
        with(binding) {
            val commentCount = post.commentsCount
            val repostsCount = post.repostsCount
            val reactionsCount = post.reactionsCount
            val reactions = arrayOf(
                likeReact,
                loveReact,
                funnyReact,
            )

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
                    com.mohaberabi.core.presentation.design_system.R.string.repost_count,
                    repostsCount
                )
            }
            commentsCountTextView.showIf(
                commentCount > 0,
            ) {
                text = context.getString(
                    com.mohaberabi.core.presentation.design_system.R.string.comment_count,
                    commentCount
                )
            }
            reactionsCountTextView.showIf(
                reactionsCount > 0,
            ) {
                text = "$reactionsCount"
            }

            post.currentUserReaction?.let { react ->
                likeButton.setIcon(react.icon)
                likeButton.setText(root.context.getString(react.label))
            } ?: run {
                likeButton.setIcon(com.mohaberabi.core.presentation.design_system.R.drawable.ic_react)
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
        setOnClickListeners(
            post = post,
            clickCallBacks = clickCallBacks
        )
    }


    private fun setOnClickListeners(
        post: PostModel,
        clickCallBacks: PostClickCallBacks,
    ) {
        binding.root.setOnClickListener {
            clickCallBacks.onClick(post)
        }
        binding.likeButton.setOnClickListener {
            clickCallBacks.onLikeClick(post)
        }

        binding.likeButton.setOnLongClickListener {
            clickCallBacks.onLongClickLike(post)
            true
        }

        binding.commentButton.setOnClickListener {
            clickCallBacks.onClick(post)
        }
        binding.sendButton.setOnClickListener {
            clickCallBacks.onSendClick(post)
        }

    }


    private fun setPosterName(name: String) {
        binding.issuerNameTextView.text = name
    }

    private fun setPosterBio(bio: String) {
        binding.issuerBioTextView.text = bio
    }

    private fun setPosterImage(resourceId: Int) {
        binding.issuerAvatarImageView.setImageResource(resourceId)
    }

    private fun setAttachedImage(resourceId: Int) {
        binding.postAttachedImageView.setImageResource(resourceId)
    }

    private fun setCreatedAt(createdAt: String) {
        binding.createdAtTextView.text = createdAt
    }

    private fun setPostData(postData: String) {
        binding.postDataTextView.setText(postData)
    }

    private fun setReactionsCount(count: String) {
        binding.commentsCountTextView.text = count
    }

    private fun setCommentsCount(count: String) {
        binding.commentsCountTextView.text = count
    }

    private fun setRepostsCount(count: String) {
        binding.repostsCountTextView.text = count
    }

    private fun showLikeReaction(show: Boolean) {
        binding.likeReact.showIf(show)
    }

    private fun showLoveReaction(show: Boolean) {
        binding.loveReact.showIf(show)
    }

    private fun showFunnyReaction(show: Boolean) {
        binding.funnyReact.showIf(show)
    }


    private fun setCustomAttrs() {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.PostListItem,
            0, 0
        ).apply {
            try {
                setPosterName(getString(R.styleable.PostListItem_posterName) ?: "")
                setPosterBio(getString(R.styleable.PostListItem_posterBio) ?: "")
                setPosterImage(
                    getResourceId(
                        R.styleable.PostListItem_posterImage,
                        R.drawable.img_plcholder
                    )
                )
                setAttachedImage(
                    getResourceId(
                        R.styleable.PostListItem_attachedImage,
                        R.drawable.img_plcholder
                    )
                )
                setCreatedAt(getString(R.styleable.PostListItem_createdAt) ?: "")
                setPostData(getString(R.styleable.PostListItem_postData) ?: "")
                setReactionsCount(getString(R.styleable.PostListItem_reactionsCount) ?: "")
                setCommentsCount(getString(R.styleable.PostListItem_commentsCount) ?: "")
                setRepostsCount(getString(R.styleable.PostListItem_repostsCount) ?: "")
                showLikeReaction(getBoolean(R.styleable.PostListItem_showLikeReaction, true))
                showLoveReaction(getBoolean(R.styleable.PostListItem_showLoveReaction, true))
                showFunnyReaction(getBoolean(R.styleable.PostListItem_showFunnyReaction, true))
            } finally {
                recycle()
            }
        }

    }
}

data class PostClickCallBacks(
    val onClick: (PostModel) -> Unit = {},
    val onLongClickLike: (PostModel) -> Unit = {},
    val onLikeClick: (PostModel) -> Unit = {},
    val onSendClick: (PostModel) -> Unit = {},
)