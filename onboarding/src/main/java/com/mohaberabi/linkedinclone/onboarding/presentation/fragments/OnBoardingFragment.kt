package com.mohaberabi.linkedinclone.onboarding.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.mohaberabi.linkedinclone.onboarding.presentation.domain.OnBoardingPage
import com.mohaberabi.onboarding.R
import com.mohaberabi.onboarding.databinding.FragmentOnBoardingBinding
import com.mohaberabi.presentation.ui.navigation.AppRoutes
import com.mohaberabi.presentation.ui.navigation.goTo
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class OnBoardingFragment : Fragment() {


    private var _binding: FragmentOnBoardingBinding? = null

    private val binding get() = _binding!!

    private val onBoardingAdapter = OnBoardingAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOnBoardingBinding.inflate(
            layoutInflater,
            container,
            false
        )

        setupBinding()
        return binding.root
    }


    private fun setupBinding() {
        setupPager()
        with(binding) {
            agreeJoinButton.setButtonClickListener {
                goRegister()
            }
            signInTextView.setOnClickListener {
                goLogin()
            }
        }
    }

    private fun setupPager() {
        val pager = binding.viewPager
        pager.adapter = onBoardingAdapter
        val bound = OnBoardingPage.entries.size - 1
        viewLifecycleOwner.lifecycleScope.launch {
            while (true) {
                val currentIndex = pager.currentItem
                delay(2000L)
                viewLifecycleOwner.lifecycleScope.launch {
                    if (currentIndex < bound) {
                        pager.setCurrentItem(currentIndex + 1, true)
                    } else {
                        pager.setCurrentItem(0, false)
                    }
                }
            }
        }
    }

    private fun goLogin() = findNavController().goTo(AppRoutes.Login)
    private fun goRegister() = findNavController().goTo(AppRoutes.Register)

}