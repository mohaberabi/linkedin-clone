package com.mohaberabi.linkedinclone.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import coil.transform.CircleCropTransformation
import com.mohaberabi.linkedinclone.current_user.presentation.CurrentUserViewModel
import com.mohaberabi.presentation.ui.util.extension.cachedImage
import com.mohaberabi.presentation.ui.util.extension.collectLifeCycleFlow
import com.mohaberabi.settings.R
import com.mohaberabi.settings.databinding.FragmentSettingsBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SettingsFragment : Fragment() {


    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

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
        return binding.root
    }


}