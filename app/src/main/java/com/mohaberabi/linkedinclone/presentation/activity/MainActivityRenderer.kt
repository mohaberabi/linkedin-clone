package com.mohaberabi.linkedinclone.presentation.activity

import coil.transform.CircleCropTransformation
import com.mohaberabi.linkedinclone.databinding.NavHeaderBinding
import com.mohaberabi.linkedinclone.presentation.viewmodel.MainActivityState
import com.mohaberabi.presentation.ui.util.cached

class MainActivityRenderer(
    private val mainDrawerBinding: NavHeaderBinding
) {


    fun bind(state: MainActivityState) {


        state.user?.let {

            with(mainDrawerBinding) {
                userBio.text = it.bio
                userName.text = "${it.name} ${it.lastname}"
                avatarImage.cached(it.img) {
                    transformations(CircleCropTransformation())
                }
            }

        }
    }
}