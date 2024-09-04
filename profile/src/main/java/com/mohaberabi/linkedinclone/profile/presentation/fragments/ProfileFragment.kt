package com.mohaberabi.linkedinclone.profile.presentation.fragments

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.mohaberabi.linkedinclone.current_user.CurrentUserViewModel
import com.mohaberabi.linkedinclone.profile.presentation.viewmodel.ProfileActions
import com.mohaberabi.linkedinclone.profile.presentation.viewmodel.ProfileState
import com.mohaberabi.linkedinclone.profile.presentation.viewmodel.ProfileStatus
import com.mohaberabi.profile.databinding.FragmentProfileBinding
import com.mohaberabi.linkedinclone.profile.presentation.viewmodel.ProfileViewModel
import com.mohaberabi.presentation.ui.navigation.AppRoutes
import com.mohaberabi.presentation.ui.navigation.goTo
import com.mohaberabi.presentation.ui.util.extension.collectLifeCycleFlow
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.combine

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val profileViewModel by viewModels<ProfileViewModel>()
    private val currentUserViewModel by activityViewModels<CurrentUserViewModel>()
    private var isPickedCover: Boolean = false
    private val imgPickerLauncher =
        registerForActivityResult(
            ActivityResultContracts.GetContent(),
        ) { uri ->
            uri?.let { goViewImage(it) }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val uid = arguments?.getString("uid")
        _binding = FragmentProfileBinding.inflate(
            layoutInflater,
            container,
            false
        )

        setupBinding(uid = uid)
        collectLifeCycleFlow(
            combine(
                currentUserViewModel.state,
                profileViewModel.state,
            ) { user, profile -> user to profile }
        ) { (currentUser, profile) ->
            val sameAsCurrentUser = currentUser.user?.uid == uid || uid == null
            val state = if (sameAsCurrentUser) ProfileState(
                user = currentUser.user,
                canEdit = true,
                state = ProfileStatus.Populated
            ) else profile
            binding.bindWithProfileState(state)
        }
        return binding.root
    }

    private fun setupBinding(uid: String?) {
        val currentUser = currentUserViewModel.state.value.user
        if (uid == null || uid == currentUser?.uid) {
            with(binding) {
                coverImage.setOnClickListener {
                    isPickedCover = true
                    pickImage()
                }
                avatarImage.setOnClickListener {
                    pickImage()
                }
            }
        } else {
            profileViewModel.onAction(ProfileActions.GetUser(uid))
        }
    }


    private fun goViewImage(imgUri: Uri) {
        findNavController().goTo(
            AppRoutes.UserMedia(
                imgUri = imgUri.toString(),
                isCover = isPickedCover
            )
        )
    }

    private fun pickImage() = imgPickerLauncher.launch("image/*")
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}