package com.mohaberabi.linkedinclone.usermedida.presentation.fragments

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.mohaberabi.linkedinclone.usermedida.presentation.viewmodel.UserMediaActions
import com.mohaberabi.linkedinclone.usermedida.presentation.viewmodel.UserMediaEvents
import com.mohaberabi.linkedinclone.usermedida.presentation.viewmodel.UserMediaViewModel
import com.mohaberabi.presentation.ui.util.extension.asByteArray
import com.mohaberabi.presentation.ui.util.extension.collectLifeCycleFlow
import com.mohaberabi.presentation.ui.util.extension.createLoadingDialog
import com.mohaberabi.presentation.ui.util.extension.showIf
import com.mohaberabi.presentation.ui.util.extension.showSnackBar
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
        setupBinding()
        val loadingDialog = requireContext().createLoadingDialog()
        collectLifeCycleFlow(
            viewmodel.state,
        ) { state ->
            if (state.loading) {
                loadingDialog.show()
            } else {
                loadingDialog.dismiss()
            }
        }

        collectLifeCycleFlow(
            viewmodel.events,
        ) { event ->
            when (event) {
                is UserMediaEvents.Error -> {
                    binding.root.showSnackBar(event.error)
                }

                UserMediaEvents.Uploaded -> {
                    findNavController().popBackStack()
                }
            }
        }
        return binding.root
    }


    private fun imageChanged() {
        val bytes = Uri.parse(args.imgUri)
            .asByteArray(requireContext().contentResolver)
        bytes?.let {
            viewmodel.onAction(
                UserMediaActions.ProfilePictureChanged(
                    it
                )
            )
        }
    }

    private fun setupBinding() {
        val isCover = args.isCover
        val uri = Uri.parse(args.imgUri)
        binding.imageProfilePicture.showIf(!isCover) { setImageURI(uri) }
        binding.landScacpeImg.showIf(isCover) { setImageURI(uri) }
        binding.cancelButton.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.confirmButton.setOnClickListener {
            viewmodel.onAction(UserMediaActions.ConfirmUpload(isCover))
        }
    }
}