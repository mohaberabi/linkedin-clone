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
import com.mohaberabi.presentation.ui.util.createLoadingDialog
import com.mohaberabi.presentation.ui.util.showSnackBar
import com.mohaberabi.user_media.R
import com.mohaberabi.user_media.databinding.FragmentViewCoverBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ViewAvatarFragment : Fragment() {


    private val viewmodel by viewModels<ViewAvatarViewModel>()
    private val args by navArgs<ViewAvatarFragmentArgs>()
    private var _binding: FragmentViewCoverBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentViewCoverBinding.inflate(
            layoutInflater,
            container,
            false
        )

        binding.imageCoverPicture.setImageURI(Uri.parse(args.imgUri))
        imageChanged()
        binding.cancelButton.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.confirmButton.setOnClickListener {
            viewmodel.onAction(AvatarActions.ConfirmUpload)
        }
        val loadingDialog = requireContext().createLoadingDialog()
        viewLifecycleOwner.lifecycleScope.launch {
            viewmodel.state.collect { state ->
                if (state.loading) {
                    loadingDialog.show()
                } else {
                    loadingDialog.dismiss()
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewmodel.events.collect { event ->
                when (event) {
                    is AvatarEvents.Error -> binding.root.showSnackBar(
                        event.error.asString(
                            requireContext()
                        )
                    )

                    AvatarEvents.Uploaded -> findNavController().popBackStack()
                }
            }
        }


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
}