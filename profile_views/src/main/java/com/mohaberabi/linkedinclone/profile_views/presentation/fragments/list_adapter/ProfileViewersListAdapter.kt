package com.mohaberabi.linkedinclone.profile_views.presentation.fragments.list_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.transform.CircleCropTransformation
import com.mohaberabi.core.presentation.design_system.R
import com.mohaberabi.linkedin.core.domain.model.ProfileViewerModel
import com.mohaberabi.presentation.ui.util.AppListAdapter
import com.mohaberabi.presentation.ui.util.extension.cachedImage
import com.mohaberabi.presentation.ui.util.toTimeAgo
import com.mohaberabi.profile_views.databinding.ProfileViewerViewBinding


class ProfileViewersListAdapter(
    private val onClick: (String) -> Unit,
) :
    AppListAdapter<ProfileViewerModel, ProfileViewersListAdapter.ProfileViewerViewHolder>(
        predicate = { it.uid },
        contentPredicate = { _, _ -> true }
    ) {
    init {

        setHasStableIds(true)
    }

    inner class ProfileViewerViewHolder(
        private val binding: ProfileViewerViewBinding,
    ) : ViewHolder(binding.root) {
   

        fun bind(
            viewer: ProfileViewerModel,
        ) {

            with(binding) {
                root.setOnClickListener {
                    onClick(viewer.uid)
                }
                viewProfilePic.cachedImage(viewer.img) {
                    transformations(CircleCropTransformation())
                }
                viewerName.text = viewer.name
                viewerBio.text = viewer.bio
                val context = root.context
                val timeAgo = viewer.viewedAtMillis.toTimeAgo(context)
                viewedAt.text = context.getString(R.string.viewed_ago, timeAgo)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProfileViewerViewHolder {

        val binding = ProfileViewerViewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProfileViewerViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ProfileViewerViewHolder,
        position: Int
    ) = holder.bind(getItem(position))

    override fun getItemId(position: Int): Long {
        return getItem(position).uid.hashCode().toLong()
    }

}

