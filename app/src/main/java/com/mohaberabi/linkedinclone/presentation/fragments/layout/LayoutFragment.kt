package com.mohaberabi.linkedinclone.presentation.fragments.layout

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.mohaberabi.linkedin.core.domain.util.AppDrawerActions
import com.mohaberabi.linkedin.core.domain.util.DrawerController
import com.mohaberabi.linkedinclone.R
import com.mohaberabi.linkedinclone.databinding.FragmentLayoutBinding
import com.mohaberabi.linkedinclone.presentation.fragments.layout.viewmodel.LayoutViewModel

import com.mohaberabi.presentation.ui.navigation.NavDeepLinks
import com.mohaberabi.presentation.ui.navigation.deepLinkNavigate
import com.mohaberabi.presentation.ui.util.closeDrawer
import com.mohaberabi.presentation.ui.util.collectLifeCycleFlow
import com.mohaberabi.presentation.ui.util.openDrawer
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@AndroidEntryPoint
class LayoutFragment : Fragment() {


    private val viewModel by viewModels<LayoutViewModel>()
    private var _binding: FragmentLayoutBinding? = null
    private val binding get() = _binding!!


    @Inject
    lateinit var drawerController: DrawerController
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLayoutBinding.inflate(
            layoutInflater,
            container,
            false
        )


        binding.setupBottomNav(
            navController = layoutNavController(),
            onAddPost = { goToAddPost() }
        )

        collectLifeCycleFlow(viewModel.state) { state ->
            binding.bind(
                state = state,
                onAvatarClick = {
                    openDrawer()
                },
            )
        }
        return binding.root
    }

    private fun openDrawer() {
        lifecycleScope.launch {
            drawerController.sendDrawerAction(AppDrawerActions.Close)
            drawerController.sendDrawerAction(AppDrawerActions.Open)

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun goToAddPost() {
        findNavController().deepLinkNavigate(NavDeepLinks.ADD_POST)
    }

    private fun goProfile() {
        findNavController().deepLinkNavigate(NavDeepLinks.PROFILE)
    }

}