package com.mohaberabi.linkedinclone.register.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.mohaberabi.linkedinclone.register.presentation.viewmodel.RegisterEvents
import com.mohaberabi.linkedinclone.register.presentation.viewmodel.RegisterViewModel
import com.mohaberabi.presentation.ui.navigation.AppRoutes
import com.mohaberabi.presentation.ui.navigation.popAllAndNavigate
import com.mohaberabi.presentation.ui.util.extension.collectLifeCycleFlow
import com.mohaberabi.presentation.ui.util.extension.showSnackBar
import com.mohaberabi.register.databinding.FragmentRegisterBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class RegisterFragment : Fragment() {
    private val viewmodel by viewModels<RegisterViewModel>()
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentRegisterBinding.inflate(
            layoutInflater,
            container,
            false
        )

        binding.setup(
            onAction = viewmodel::onAction
        )
        collectLifeCycleFlow(
            viewmodel.state,
        ) { state ->
            binding.bindWithRegisterState(
                state,
            )
        }


        collectLifeCycleFlow(
            viewmodel.event,
        ) { event ->
            when (event) {
                is RegisterEvents.Error -> binding.root.showSnackBar(event.error)
                RegisterEvents.Registered -> goHome()
            }

        }

        return binding.root
    }


    private fun goHome() {
        findNavController().popAllAndNavigate(AppRoutes.Posts)
    }
}