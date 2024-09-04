package com.mohaberbia.linkedinclone.posts.presentation.fragments.posts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import coil.transform.CircleCropTransformation
import com.mohaberabi.core.presentation.design_system.R
import com.mohaberabi.core.presentation.ui.databinding.PostListItemBinding
import com.mohaberabi.linkedin.core.domain.model.PostModel
import com.mohaberabi.presentation.ui.util.AppListAdapter
import com.mohaberabi.presentation.ui.util.extension.cachedImage
import com.mohaberabi.presentation.ui.util.extension.icon
import com.mohaberabi.presentation.ui.util.extension.label
import com.mohaberabi.presentation.ui.util.toTimeAgo
import com.mohaberabi.presentation.ui.views.post_item.PostClickCallBacks
import com.mohaberabi.presentation.ui.views.post_item.bindFromPost
import kotlin.math.max


class PostsListAdapter(
    private val onClickCallBacks: PostClickCallBacks,
) :
    AppListAdapter<PostModel, PostsListAdapter.PostViewHolder>(
        predicate = { it.id },
        contentPredicate = { old, new -> old == new }
    ) {
    init {

        setHasStableIds(true)
    }

    inner class PostViewHolder(
        private val binding: PostListItemBinding,
    ) : ViewHolder(binding.root) {
        fun bind(
            post: PostModel,
        ) {
            binding.bindFromPost(
                post = post,
                onClickCallBacks = onClickCallBacks
            )
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

    override fun getItemId(position: Int): Long {
        return getItem(position).id.hashCode().toLong()
    }
}