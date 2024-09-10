package com.mohaberabi.linkedclone.post_saver.presetnation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mohaberabi.linkedclone.post_saver.presetnation.viewmodel.PostSaverActions
import com.mohaberabi.linkedclone.post_saver.presetnation.viewmodel.PostSaverEvents
import com.mohaberabi.linkedclone.post_saver.presetnation.viewmodel.PostSaverViewModel
import com.mohaberabi.post_saver.databinding.PostSaverSheetBinding
import com.mohaberabi.presentation.ui.util.extension.collectLifeCycleFlow
import com.mohaberabi.presentation.ui.util.extension.showSnackBar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class PostSaverSheet : BottomSheetDialogFragment() {


    private val viewmodel by viewModels<PostSaverViewModel>()

    companion object {
        private const val ARG_POST_ID = "postId"
        fun newInstance(postId: String): PostSaverSheet {
            val fragment = PostSaverSheet()
            val args = Bundle()
            args.putString(ARG_POST_ID, postId)
            fragment.arguments = args
            return fragment
        }
    }


    private var _binding: PostSaverSheetBinding? = null

    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = PostSaverSheetBinding.inflate(
            layoutInflater,
            container,
            false
        )

        val postId = arguments?.getString(ARG_POST_ID)

        binding.saveOption.setOnClickListener {

            postId?.let {
                viewmodel.onAction(PostSaverActions.SavePost(it))
            }
        }
        collectLifeCycleFlow(
            viewmodel.event,
        ) { event ->
            when (event) {
                is PostSaverEvents.Error -> binding.root.showSnackBar(event.error)
                PostSaverEvents.PostSaved -> dismiss()

            }
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}