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
import com.mohaberabi.linkedinclone.current_user.presentation.CurrentUserViewModel
import com.mohaberabi.linkedinclone.profile.presentation.viewmodel.ProfileActions
import com.mohaberabi.linkedinclone.profile.presentation.viewmodel.MappedProfileState
import com.mohaberabi.profile.databinding.FragmentProfileBinding
import com.mohaberabi.linkedinclone.profile.presentation.viewmodel.ProfileViewModel
import com.mohaberabi.linkedinclone.user_metadata.UserMetaDataViewModel
import com.mohaberabi.presentation.ui.navigation.AppRoutes
import com.mohaberabi.presentation.ui.navigation.goTo
import com.mohaberabi.presentation.ui.util.extension.collectLifeCycleFlow
import com.mohaberabi.presentation.ui.util.extension.show
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.map

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val currentUserViewModel by activityViewModels<CurrentUserViewModel>()
    private val userMetaDataViewModel by activityViewModels<UserMetaDataViewModel>()
    private val profileViewModel by viewModels<ProfileViewModel>()
    private var isPickedCover: Boolean = false
    private val imgPickerLauncher =
        registerForActivityResult(
            ActivityResultContracts.GetContent(),
        ) { uri ->
            uri?.let { goViewImage(it) }
        }

    private lateinit var profileStateMapper: MappedProfileState
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(
            layoutInflater,
            container,
            false
        )
        profileStateMapper = MappedProfileState(
            currentUserMetaDataFlow = userMetaDataViewModel.state,
            currentUserFlow = currentUserViewModel.state.map { it.user },
            otherUserState = profileViewModel.state,
            otherUserUid = arguments?.getString("uid"),
            onSetupCurrentUser = {
                setupCurrentUserBinding()
            },
            onLoadOtherUser = {
                profileViewModel.onAction(ProfileActions.LoadOtherUser(it))
            }
        )
        collectLifeCycleFlow(
            profileStateMapper()
        ) { state ->
            binding.bindFromState(state)
        }
        return binding.root
    }


    private fun setupCurrentUserBinding() {
        with(binding) {
            avatarImage.setOnClickListener {
                pickImage()
            }
            coverImage.setOnClickListener {
                isPickedCover = true
                pickImage()
            }
            profileViews.show()
            postImpressions.show()
        }
    }

    private fun goViewImage(
        imgUri: Uri,
    ) {
        val route = AppRoutes.UserMedia(
            imgUri = imgUri.toString(),
            isCover = isPickedCover
        )
        findNavController().goTo(route)
    }

    private fun pickImage() = imgPickerLauncher.launch("image/*")
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}