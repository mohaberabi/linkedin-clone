package com.mohaberbia.linkedinclone.posts.presentation.fragments.posts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import coil.transform.CircleCropTransformation
import com.mohaberabi.linkedin.core.domain.model.PostModel
import com.mohaberabi.posts.databinding.PostListItemBinding
import com.mohaberabi.presentation.ui.util.AppListAdapter
import com.mohaberabi.presentation.ui.util.cached
import com.mohaberabi.presentation.ui.util.toTimeAgo


class PostsListAdapter :
    AppListAdapter<PostModel, PostsListAdapter.PostViewHolder>(
        predicate = { it.id },
    ) {

    inner class PostViewHolder(
        private val binding: PostListItemBinding,
    ) : ViewHolder(binding.root) {
        fun bind(post: PostModel) {
            with(binding) {
                issuerBioTextView.text = post.issuerBio
                createdAtTextView.text = post.createdAtMillis.toTimeAgo(binding.root.context)
                issuerNameTextView.text = post.issuerName
                issuerAvatarImageView.cached(post.issuerAvatar) {
                    transformations(CircleCropTransformation())
                }
                if (post.postAttachedImg.isEmpty()) {
                    postAttachedImageView.visibility = View.GONE
                } else {
                    postAttachedImageView.load(post.postAttachedImg)
                }

            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PostViewHolder {
        val binding = PostListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: PostViewHolder,
        position: Int
    ) = holder.bind(getItem(position))
}