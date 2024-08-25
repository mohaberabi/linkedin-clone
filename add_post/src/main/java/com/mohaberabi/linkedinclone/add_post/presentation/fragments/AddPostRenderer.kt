package com.mohaberabi.linkedinclone.add_post.presentation.fragments

import android.graphics.BitmapFactory
import android.view.View
import androidx.core.widget.addTextChangedListener
import com.mohaberabi.add_posts.databinding.FragmentAddPostBinding
import com.mohaberabi.linkedinclone.add_post.presentation.viewmodel.AddPostActions
import com.mohaberabi.linkedinclone.add_post.presentation.viewmodel.AddPostState


fun FragmentAddPostBinding.render(
    state: AddPostState,
    onAction: (AddPostActions) -> Unit,
) {
    if (state.postImgByteArray.isNotEmpty()) {
        imagePreview.visibility = View.VISIBLE
        imagePreview.setImageBitmap(
            BitmapFactory.decodeByteArray(
                state.postImgByteArray,
                0,
                state.postImgByteArray.size,
            )
        )
    }
    postButton.setOnClickListener {
        onAction(AddPostActions.SubmitPost)
    }
    postTextField.addTextChangedListener {
        onAction(AddPostActions.PostDataChanged(it.toString()))
    }
}