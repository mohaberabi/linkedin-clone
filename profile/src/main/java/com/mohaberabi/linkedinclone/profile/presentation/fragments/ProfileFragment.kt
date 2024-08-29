package com.mohaberabi.linkedinclone.profile.presentation.fragments

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mohaberabi.profile.databinding.FragmentProfileBinding
import com.mohaberabi.linkedinclone.profile.presentation.viewmodel.ProfileViewModel
import com.mohaberabi.presentation.ui.navigation.AppRoutes
import com.mohaberabi.presentation.ui.navigation.goTo
import com.mohaberabi.presentation.ui.util.extension.collectLifeCycleFlow
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class ProfileFragment : Fragment() {

    private val viewModel by viewModels<ProfileViewModel>()

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val imgPickerLauncher =
        createImagePickerLauncher(handleImageResult())
    private val coverPickerLauncher =
        createImagePickerLauncher(handleImageResult())


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(
            layoutInflater,
            container,
            false
        )
        collectLifeCycleFlow(viewModel.state) { state ->
            binding.bind(
                state = state,
                onImgClicked = { imgPickerLauncher.launch("image/*") },
                onCoverClicked = { coverPickerLauncher.launch("image/*") }
            )
        }

        return binding.root
    }


    private fun handleImageResult(
    ): (Uri?) -> Unit = { uri ->
        if (uri != null) {
            findNavController().goTo(
                route = AppRoutes.ProfilePic(imgUri = uri.toString()),
            )
        }
    }

    private fun createImagePickerLauncher(
        onResult: (Uri?) -> Unit
    ) = registerForActivityResult(
        ActivityResultContracts.GetContent(),
        onResult
    )

}