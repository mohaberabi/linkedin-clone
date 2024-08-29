package com.mohaberabi.linkedinclone.post_detail.presetnation.post_details.fragments.list_adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.mohaberabi.linkedin.core.domain.model.PostCommentModel
import com.mohaberabi.linkedin.core.domain.model.ReactionModel
import com.mohaberabi.linkedinclone.post_detail.databinding.CommentorListItemBinding
import com.mohaberabi.linkedinclone.post_detail.databinding.ReactorListItemBinding
import com.mohaberabi.presentation.ui.util.AppListAdapter
import com.mohaberabi.presentation.ui.util.extension.cachedImage
import com.mohaberabi.presentation.ui.util.extension.icon

class CommentorListAdapter :
    AppListAdapter<PostCommentModel, CommentorListAdapter.CommentViewHolder>(
        predicate = { it.id },
        contentPredicate = { old, new -> old == new }
    ) {


    inner class CommentViewHolder(
        private val binding: CommentorListItemBinding,
    ) : ViewHolder(binding.root) {
        fun bind(comment: PostCommentModel) {
            with(binding) {
                commenterAvatar.cachedImage(comment.commentImg)
                commentText.text = comment.comment
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CommentViewHolder {

        val binding = CommentorListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CommentViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: CommentViewHolder,
        position: Int
    ) = holder.bind(getItem(position))

}