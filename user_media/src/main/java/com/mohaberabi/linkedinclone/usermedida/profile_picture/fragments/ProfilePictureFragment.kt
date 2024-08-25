package com.mohaberabi.linkedinclone.usermedida.profile_picture.fragments

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.mohaberabi.linkedinclone.usermedida.profile_picture.viewmodel.ProfilePictureActions
import com.mohaberabi.linkedinclone.usermedida.profile_picture.viewmodel.ProfilePictureViewModel
import com.mohaberabi.presentation.ui.util.asByteArray
import com.mohaberabi.presentation.ui.util.createLoadingDialog
import com.mohaberabi.user_media.databinding.FragmentProfilePictureBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ProfilePictureFragment : Fragment() {
    private val viewmodel by viewModels<ProfilePictureViewModel>()
    private val args by navArgs<ProfilePictureFragmentArgs>()
    private var _binding: FragmentProfilePictureBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfilePictureBinding.inflate(
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
                ProfilePictureActions.ProfilePictureChanged(
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
            viewmodel.onAction(ProfilePictureActions.ConfirmUpload)
        }
    }
}