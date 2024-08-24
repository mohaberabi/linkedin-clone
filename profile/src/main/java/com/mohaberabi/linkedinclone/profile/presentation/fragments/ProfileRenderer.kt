package com.mohaberabi.linkedinclone.profile.presentation.fragments

import android.view.View
import coil.transform.CircleCropTransformation
import com.mohaberabi.linkedin.core.domain.model.UserModel
import com.mohaberabi.presentation.ui.util.UiText
import com.mohaberabi.presentation.ui.util.cached
import com.mohaberabi.profile.databinding.FragmentProfileBinding
import com.mohaberabi.linkedinclone.profile.presentation.viewmodel.ProfileState
import com.mohaberabi.linkedinclone.profile.presentation.viewmodel.ProfileStatus

class ProfileRenderer(
    private val binding: FragmentProfileBinding,

    ) {


    fun bind(state: ProfileState) {
        when (state.state) {
            ProfileStatus.Error -> error(state.error)
            ProfileStatus.Populated -> state.user?.let { populated(it) } ?: error(state.error)
            else -> loading()
        }
    }

    private fun loading() {
        with(binding) {
            loader.visibility = View.VISIBLE
            error.visibility = View.GONE
            userContent.visibility = View.GONE
        }
    }

    private fun error(errorText: UiText) {
        with(binding) {
            loader.visibility = View.GONE
            error.visibility = View.VISIBLE
            error.setErrorTitle(errorText.asString(binding.root.context))
            userContent.visibility = View.GONE
        }
    }

    private fun populated(user: UserModel) {
        with(binding) {
            userContent.visibility = View.VISIBLE
            userBio.text = user.bio
            userName.text = "${user.name} ${user.lastname}"
            avatarImage.cached(user.img) {
                transformations(CircleCropTransformation())
            }
            coverImage.cached(user.cover)
        }
    }
}