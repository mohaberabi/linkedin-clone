package com.mohaberabi.linkedinclone.settings.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import coil.transform.CircleCropTransformation
import com.mohaberabi.linkedinclone.current_user.presentation.CurrentUserViewModel
import com.mohaberabi.linkedinclone.settings.presentation.viewmodel.SettingsActions
import com.mohaberabi.linkedinclone.settings.presentation.viewmodel.SettingsEvents
import com.mohaberabi.linkedinclone.settings.presentation.viewmodel.SettingsViewModel
import com.mohaberabi.presentation.ui.navigation.AppRoutes
import com.mohaberabi.presentation.ui.navigation.popAllAndNavigate
import com.mohaberabi.presentation.ui.util.extension.cachedImage
import com.mohaberabi.presentation.ui.util.extension.collectLifeCycleFlow
import com.mohaberabi.presentation.ui.util.extension.showSnackBar
import com.mohaberabi.settings.databinding.FragmentSettingsBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SettingsFragment : Fragment() {


    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private val viewmodel by viewModels<SettingsViewModel>()
    private val currentUserViewModel by activityViewModels<CurrentUserViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsBinding.inflate(
            layoutInflater,
            container,
            false
        )
        collectLifeCycleFlow(
            currentUserViewModel.state,
        ) { state ->
            state.user?.let {
                binding.profilePic.cachedImage(it.img) {
                    transformations(CircleCropTransformation())
                }
            }
        }

        collectLifeCycleFlow(
            viewmodel.event,
        ) { event ->
            when (event) {
                is SettingsEvents.Error -> binding.root.showSnackBar(event.error)
                SettingsEvents.SignedOut -> goOnBoarding()
            }
        }

        binding.signOut.setOnClickListener {
            viewmodel.onAction(SettingsActions.SignOut)
        }
        return binding.root
    }

    private fun goOnBoarding() =
        findNavController().popAllAndNavigate(
            AppRoutes.OnBoarding.route,
            clearWholeStack = true
        )

}