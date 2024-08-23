package com.mohaberabi.linkedinclone.add_post.presentation.fragments

import android.graphics.BitmapFactory
import android.view.View
import androidx.core.widget.addTextChangedListener
import com.mohaberabi.add_posts.databinding.FragmentAddPostBinding
import com.mohaberabi.linkedinclone.add_post.presentation.viewmodel.AddPostActions
import com.mohaberabi.linkedinclone.add_post.presentation.viewmodel.AddPostState


class AddPostRenderer(
    private val binding: FragmentAddPostBinding,
    private val onAction: (AddPostActions) -> Unit,
) {


    fun render(state: AddPostState) {
        if (state.postImgByteArray.isNotEmpty()) {
            binding.imagePreview.visibility = View.VISIBLE
            binding.imagePreview.setImageBitmap(
                BitmapFactory.decodeByteArray(
                    state.postImgByteArray,
                    0,
                    state.postImgByteArray.size,
                )
            )
        }
        binding.postButton.setOnClickListener {
            onAction(AddPostActions.SubmitPost)
        }
        binding.postTextField.addTextChangedListener {
            onAction(AddPostActions.PostDataChanged(it.toString()))
        }
    }
}