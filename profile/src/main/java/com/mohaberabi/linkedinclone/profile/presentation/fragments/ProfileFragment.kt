package com.mohaberabi.linkedinclone.profile.presentation.fragments

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.mohaberabi.profile.R
import com.mohaberabi.profile.databinding.FragmentProfileBinding
import com.mohaberabi.linkedinclone.profile.presentation.viewmodel.ProfileViewModel
import com.mohaberabi.presentation.ui.navigation.NavDeepLinks
import com.mohaberabi.presentation.ui.navigation.deepLinkNavigate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint

class ProfileFragment : Fragment() {

    private val viewModel by viewModels<ProfileViewModel>()

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!


    private val imgPickerLauncher =
        createImagePickerLauncher(handleImageResult(NavDeepLinks.ViewAvatar))
    private val coverPickerLauncher =
        createImagePickerLauncher(handleImageResult(NavDeepLinks.ViewCover))


    private lateinit var renderer: ProfileRenderer
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(
            layoutInflater,
            container,
            false
        )
        renderer = ProfileRenderer(
            binding = binding,
            onImgClicked = { imgPickerLauncher.launch("image/*") },
            onCoverClicked = { coverPickerLauncher.launch("image/*") }
        )
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect { state ->
                renderer.bind(state)
            }
        }
        return binding.root
    }


    private fun handleImageResult(fragId: String): (Uri?) -> Unit = { uri ->
        if (uri != null) {
            findNavController().deepLinkNavigate(
                fragmentId = fragId,
                args = listOf("imgUri" to uri)
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