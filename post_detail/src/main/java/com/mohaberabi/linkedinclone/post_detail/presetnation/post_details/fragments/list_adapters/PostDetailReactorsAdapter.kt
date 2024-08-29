package com.mohaberabi.linkedinclone.post_detail.presetnation.post_details.fragments.list_adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.mohaberabi.linkedin.core.domain.model.ReactionModel
import com.mohaberabi.linkedinclone.post_detail.databinding.ReactorListItemBinding
import com.mohaberabi.presentation.ui.util.AppListAdapter
import com.mohaberabi.presentation.ui.util.extension.cachedImage
import com.mohaberabi.presentation.ui.util.extension.icon

class PostDetailReactorsAdapter :
    AppListAdapter<ReactionModel, PostDetailReactorsAdapter.ReactorViewHolder>(
        predicate = { it.reactorId },
        contentPredicate = { old, new -> old == new }
    ) {


    inner class ReactorViewHolder(
        private val binding: ReactorListItemBinding,
    ) : ViewHolder(binding.root) {
        fun bind(reactor: ReactionModel) {
            with(binding) {
                reactionImage.setImageDrawable(
                    AppCompatResources.getDrawable(
                        root.context,
                        reactor.reactionType.icon
                    )
                )

                avatarImage.cachedImage(reactor.reactorImg)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ReactorViewHolder {

        val binding = ReactorListItemBinding.inflate(
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