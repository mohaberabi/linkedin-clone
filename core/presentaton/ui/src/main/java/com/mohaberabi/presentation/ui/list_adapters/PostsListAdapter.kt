package com.mohaberabi.presentation.ui.list_adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.mohaberabi.linkedin.core.domain.model.PostModel
import com.mohaberabi.presentation.ui.util.AppListAdapter
import com.mohaberabi.presentation.ui.views.PostClickCallBacks
import com.mohaberabi.presentation.ui.views.PostListItem


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
        private val postItem: PostListItem,
    ) : ViewHolder(postItem) {
        fun bind(
            post: PostModel,
        ) {
            postItem.bindFromPost(
                post = post,
                clickCallBacks = onClickCallBacks
            )
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PostViewHolder {
        val postListItem = PostListItem(parent.context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
        return PostViewHolder(postListItem)
    }

    override fun onBindViewHolder(
        holder: PostViewHolder,
        position: Int
    ) = holder.bind(getItem(position))

    override fun getItemId(position: Int): Long {
        return getItem(position).id.hashCode().toLong()
    }
}