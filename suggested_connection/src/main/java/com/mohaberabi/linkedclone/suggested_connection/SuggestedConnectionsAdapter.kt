package com.mohaberabi.linkedclone.suggested_connection

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.transform.RoundedCornersTransformation
import com.mohaberabi.core.presentation.ui.databinding.PostListItemBinding
import com.mohaberabi.linkedin.core.domain.model.PostModel
import com.mohaberabi.linkedin.core.domain.model.UserModel
import com.mohaberabi.presentation.ui.util.AppListAdapter
import com.mohaberabi.presentation.ui.util.extension.cachedImage
import com.mohaberabi.presentation.ui.views.post_item.bindFromPost
import com.mohaberabi.suggested_connection.databinding.ConnectionUserViewBinding


class SuggestedConnectionsAdapter :
    AppListAdapter<UserModel, SuggestedConnectionsAdapter.ConnectionViewHolder>(
        predicate = { it.uid },
        contentPredicate = { old, new -> old == new }
    ) {
    inner class ConnectionViewHolder(
        private val binding: ConnectionUserViewBinding,
    ) : ViewHolder(binding.root) {
        fun bind(
            user: UserModel,
        ) {
            with(binding) {
                userBio.text = user.bio
                userName.text = user.name
                coverImage.cachedImage(user.cover)
                profileImage.cachedImage(user.img)
            }

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ConnectionViewHolder {

        val binding = ConnectionUserViewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ConnectionViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ConnectionViewHolder,
        position: Int
    ) = holder.bind(getItem(position))

    override fun getItemId(position: Int): Long {
        return getItem(position).uid.hashCode().toLong()
    }
}