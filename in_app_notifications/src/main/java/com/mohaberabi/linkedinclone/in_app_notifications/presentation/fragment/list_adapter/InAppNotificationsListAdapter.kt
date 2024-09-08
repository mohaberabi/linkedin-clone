package com.mohaberabi.linkedinclone.in_app_notifications.presentation.fragment.list_adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.transform.CircleCropTransformation
import com.mohaberabi.core.presentation.design_system.R
import com.mohaberabi.in_app_notifications.databinding.InAppNotiViewBinding
import com.mohaberabi.linkedin.core.domain.model.InAppNotificationBehaviour
import com.mohaberabi.linkedin.core.domain.model.InAppNotificationModel
import com.mohaberabi.presentation.ui.util.AppListAdapter
import com.mohaberabi.presentation.ui.util.extension.cachedImage
import com.mohaberabi.presentation.ui.util.extension.hide
import com.mohaberabi.presentation.ui.util.extension.icon
import com.mohaberabi.presentation.ui.util.extension.show
import com.mohaberabi.presentation.ui.util.toTimeAgo


class InAppNotificationsListAdapter(
    private val onNotiPostClick: (String) -> Unit,
    private val onNotiUserClick: (String) -> Unit,

    ) :
    AppListAdapter<InAppNotificationModel, InAppNotificationsListAdapter.InAppNotificationsViewHolder>(
        predicate = { it.id },
        contentPredicate = { _, _ -> true }
    ) {
    init {

        setHasStableIds(true)
    }

    inner class InAppNotificationsViewHolder(
        private val binding: InAppNotiViewBinding,
    ) : ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                val noti = getItem(position)
                when (val behaviour = noti.action) {
                    is InAppNotificationBehaviour.Comment -> onNotiPostClick(behaviour.postId)
                    is InAppNotificationBehaviour.Reaction -> onNotiPostClick(behaviour.postId)
                    InAppNotificationBehaviour.ViewProfile -> onNotiUserClick(noti.sender.uid)
                    else -> Unit
                }
            }
        }

        fun bind(
            noti: InAppNotificationModel,
        ) {
            with(binding) {
                senderImg.cachedImage(noti.sender.img) {
                    transformations(CircleCropTransformation())
                }
                val senderName = noti.sender.name
                val context = binding.root.context
                timeAgo.text = noti.createdAtMillis.toTimeAgo(root.context)
                val behaviour = noti.action
                notiHeader.text = context.notificationTitle(behaviour, senderName)
                bindWithBehaviour(behaviour, body = behaviour.notificationBody())
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): InAppNotificationsViewHolder {

        val binding = InAppNotiViewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return InAppNotificationsViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: InAppNotificationsViewHolder,
        position: Int
    ) = holder.bind(getItem(position))

    override fun getItemId(position: Int): Long {
        return getItem(position).id.hashCode().toLong()
    }

}

private fun InAppNotiViewBinding.bindWithBehaviour(
    behavior: InAppNotificationBehaviour,
    body: String,
) = when (behavior) {
    is InAppNotificationBehaviour.Reaction -> {
        reactionLayout.show()
        notiData.hide()
        reactionPostHeader.text = body
        reactionImage.setImageDrawable(
            ContextCompat.getDrawable(
                root.context,
                behavior.reactionType.icon
            )
        )
    }

    is InAppNotificationBehaviour.Comment -> {
        reactionLayout.hide()
        notiData.show()
        notiData.text = body
    }

    else -> {
        reactionLayout.hide()
        notiData.hide()
    }
}

private fun InAppNotificationBehaviour.notificationBody() = when (this) {
    is InAppNotificationBehaviour.Comment -> comment
    is InAppNotificationBehaviour.Reaction -> postHeader
    else -> ""
}

private fun Context.notificationTitle(
    behaviour: InAppNotificationBehaviour,
    senderName: String,
) = when (behaviour) {
    is InAppNotificationBehaviour.Comment -> getString(R.string.notification_comment, senderName)
    is InAppNotificationBehaviour.Reaction -> getString(R.string.notification_reaction, senderName)
    InAppNotificationBehaviour.ViewProfile -> getString(
        R.string.notification_view_profile,
        senderName
    )

    else -> ""
}