package com.mohaberabi.presentation.ui.views.post_item


import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.mohaberabi.core.presentation.ui.R
import com.mohaberabi.core.presentation.ui.databinding.PostListItemBinding
import com.mohaberabi.linkedin.core.domain.model.PostModel

class PostListItem @JvmOverloads constructor(
    context: Context,
    private val attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private var onPosterClickListener: (() -> Unit)? = null
    private var onLikeClickListener: (() -> Unit)? = null
    private var onLongClickLikeListener: (() -> Unit)? = null
    private var onCommentClickListener: (() -> Unit)? = null
    private var onRepostClickListener: (() -> Unit)? = null
    private var onSendClickListener: (() -> Unit)? = null
    private val binding: PostListItemBinding =
        PostListItemBinding.inflate(
            LayoutInflater.from(context),
            this,
            true
        )

    init {
        setupListeners()
        setCustomAttrs()
    }


    fun bindFromPost(
        post: PostModel,
        onClickCallBacks: PostClickCallBacks,
    ) {
        binding.bindFromPost(
            onClickCallBacks = onClickCallBacks,
            post = post
        )
    }

    private fun setupListeners() {
        binding.likeButton.setOnClickListener {
            onLikeClickListener?.invoke()
        }

        binding.likeButton.setOnLongClickListener {
            onLongClickLikeListener?.invoke()
            true
        }

        binding.commentButton.setOnClickListener {
            onCommentClickListener?.invoke()
        }

        binding.repostButton.setOnClickListener {
            onRepostClickListener?.invoke()
        }

        binding.sendButton.setOnClickListener {
            onSendClickListener?.invoke()
        }
        binding.issuerAvatarImageView.setOnClickListener {
            onPosterClickListener?.invoke()
        }
        binding.issuerNameTextView.setOnClickListener {
            onPosterClickListener?.invoke()
        }
    }


    fun setPosterName(name: String) {
        binding.issuerNameTextView.text = name
    }

    fun setPosterBio(bio: String) {
        binding.issuerBioTextView.text = bio
    }

    fun setPosterImage(resourceId: Int) {
        binding.issuerAvatarImageView.setImageResource(resourceId)
    }

    fun setAttachedImage(resourceId: Int) {
        binding.postAttachedImageView.setImageResource(resourceId)
    }

    fun setCreatedAt(createdAt: String) {
        binding.createdAtTextView.text = createdAt
    }

    fun setPostData(postData: String) {
        binding.postDataTextView.setText(postData)
    }

    fun setReactionsCount(count: String) {
        binding.commentsCountTextView.text = count
    }

    fun setCommentsCount(count: String) {
        binding.commentsCountTextView.text = count
    }

    fun setRepostsCount(count: String) {
        binding.repostsCountTextView.text = count
    }

    fun showLikeReaction(show: Boolean) {
        binding.likeReact.visibility = if (show) View.VISIBLE else View.GONE
    }

    fun showLoveReaction(show: Boolean) {
        binding.loveReact.visibility = if (show) View.VISIBLE else View.GONE
    }

    fun showFunnyReaction(show: Boolean) {
        binding.funnyReact.visibility = if (show) View.VISIBLE else View.GONE
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
