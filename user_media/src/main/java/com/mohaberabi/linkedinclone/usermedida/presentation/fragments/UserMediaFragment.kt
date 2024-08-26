package com.mohaberabi.linkedinclone.usermedida.presentation.fragments

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.mohaberabi.linkedinclone.usermedida.presentation.viewmodel.UserMediaActions
import com.mohaberabi.linkedinclone.usermedida.presentation.viewmodel.UserMediaViewModel
import com.mohaberabi.presentation.ui.util.asByteArray
import com.mohaberabi.user_media.databinding.FragmentProfilePictureBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class UserMediaFragment : Fragment() {
    private val viewmodel by viewModels<UserMediaViewModel>()
    private val args by navArgs<UserMediaFragmentArgs>()
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

        return binding.root
    }


    private fun imageChanged() {
        val bytes = Uri.parse(args.imgUri).asByteArray(requireContext().contentResolver)
        bytes?.let {
            viewmodel.onAction(
                UserMediaActions.ProfilePictureChanged(
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
        }
        binding.confirmButton.setOnClickListener {
            viewmodel.onAction(UserMediaActions.ConfirmUpload)
        }
    }
}