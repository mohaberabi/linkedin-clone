package com.mohaberabi.linkedinclone.post_detail.presetnation.post_reactions.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.mohaberabi.linkedin.core.domain.model.ReactionModel
import com.mohaberabi.linkedinclone.post_detail.databinding.ReactionListItemBinding
import com.mohaberabi.linkedinclone.post_detail.databinding.ReactorListItemBinding
import com.mohaberabi.presentation.ui.util.AppListAdapter
import com.mohaberabi.presentation.ui.util.extension.cachedImage
import com.mohaberabi.presentation.ui.util.extension.icon

class ReactorsListAdapter : AppListAdapter<ReactionModel, ReactorsListAdapter.ReactorViewHolder>(
    predicate = { it.reactorId },
    contentPredicate = { old, new -> old == new }
) {
    inner class ReactorViewHolder(
        private val binding: ReactionListItemBinding,
    ) : ViewHolder(binding.root) {
        fun bind(reactor: ReactionModel) {
            with(binding) {
                reactionIcon.setImageDrawable(
                    ContextCompat.getDrawable(
                        root.context,
                        reactor.reactionType.icon
                    )
                )
                userAvatar.cachedImage(
                    reactor.reactorImg,
                )
                userName.text = "Mohab Erabi"
                userBio.text = reactor.reactorBio
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ReactorViewHolder {

        val binding = ReactionListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ReactorViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ReactorViewHolder,
        position: Int
    ) = holder.bind(getItem(position))

}