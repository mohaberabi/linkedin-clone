package com.mohaberabi.linkedinclone.profile.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.mohaberabi.profile.R
import com.mohaberabi.profile.databinding.FragmentProfileBinding
import com.mohaberabi.linkedinclone.profile.presentation.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint

class ProfileFragment : Fragment() {

    private val viewModel by viewModels<ProfileViewModel>()

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var renderer: ProfileRenderer
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(
            layoutInflater,
            container,
            false
        )
        renderer = ProfileRenderer(binding)
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect { state ->
                renderer.bind(state)
            }
        }
        return binding.root
    }

}