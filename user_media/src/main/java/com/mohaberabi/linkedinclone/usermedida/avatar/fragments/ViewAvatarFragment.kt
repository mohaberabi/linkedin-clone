package com.mohaberabi.linkedinclone.usermedida.avatar.fragments

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mohaberabi.linkedinclone.usermedida.avatar.viewmodel.AvatarActions
import com.mohaberabi.linkedinclone.usermedida.avatar.viewmodel.AvatarEvents
import com.mohaberabi.linkedinclone.usermedida.avatar.viewmodel.ViewAvatarViewModel
import com.mohaberabi.presentation.ui.util.asByteArray
import com.mohaberabi.presentation.ui.util.collectLifeCycleFlow
import com.mohaberabi.presentation.ui.util.createLoadingDialog
import com.mohaberabi.presentation.ui.util.eventCollector
import com.mohaberabi.presentation.ui.util.showSnackBar
import com.mohaberabi.user_media.R
import com.mohaberabi.user_media.databinding.FragmentViewAvatarBinding
import com.mohaberabi.user_media.databinding.FragmentViewCoverBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ViewAvatarFragment : Fragment() {
    private val viewmodel by viewModels<ViewAvatarViewModel>()
    private val args by navArgs<ViewAvatarFragmentArgs>()
    private var _binding: FragmentViewAvatarBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentViewAvatarBinding.inflate(
            layoutInflater,
            container,
            false
        )
        imageChanged()
        bind()
        val loadingDialog = requireContext().createLoadingDialog()
//        collectLifeCycleFlow(
//            viewmodel.state,
//        ) { state ->
////            if (state.loading) {
////                loadingDialog.show()
////            } else {
////                loadingDialog.dismiss()
////            }
//        }
//        collectLifeCycleFlow(
//            viewmodel.events,
//        ) { event ->
//            when (event) {
//                is AvatarEvents.Error -> binding.root.showSnackBar(
//                    event.error.asString(
//                        requireContext()
//                    )
//                )
//
//                AvatarEvents.Uploaded -> Unit
//            }
//        }
        return binding.root
    }


    private fun imageChanged() {
        val bytes = Uri.parse(args.imgUri).asByteArray(requireContext().contentResolver)
        bytes?.let {
            viewmodel.onAction(
                AvatarActions.AvatarChanged(
                    it
                )
            )
        }
    }

    private fun bind() {
        binding.imageProfilePicture.setImageURI(
            Uri.parse(args.imgUri),
        )
        binding.cancelButton.setOnClickListener {
//            findNavController().popBackStack()
        }
        binding.confirmButton.setOnClickListener {
            viewmodel.onAction(AvatarActions.ConfirmUpload)
        }
    }
}