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
    private val onCoverClicked: () -> Unit,
    private val onImgClicked: () -> Unit,
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
            loader.show()
            error.hide()
            userContent.visibility = View.GONE
        }
    }

    private fun error(errorText: UiText) {
        with(binding) {
            loader.hide()
            error.show()
            error.setErrorTitle(errorText.asString(binding.root.context))
            userContent.visibility = View.GONE
        }
    }

    private fun populated(user: UserModel) {
        with(binding) {
            error.hide()
            loader.hide()
            userContent.visibility = View.VISIBLE
            userBio.text = user.bio
            userName.text = "${user.name} ${user.lastname}"
            avatarImage.apply {
                cached(user.img) {
                    transformations(CircleCropTransformation())
                }
                setOnClickListener {
                    onImgClicked()
                }
            }
            coverImage.apply {
                cached(user.cover)
                setOnClickListener {
                    onCoverClicked()
                }
            }
        }
    }
}