package com.mohaberabi.linkedinclone.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.mohaberabi.linkedinclone.R
import com.mohaberabi.linkedinclone.databinding.FragmentLayoutBinding

import com.mohaberabi.presentation.ui.navigation.NavDeepLinks
import com.mohaberabi.presentation.ui.navigation.deepLinkNavigate


class LayoutFragment : Fragment() {
    private var _binding: FragmentLayoutBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLayoutBinding.inflate(
            layoutInflater,
            container,
            false
        )
        setupBottomNav()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun setupBottomNav() {
        val navController = layoutNavController()
        binding.bottomNavigationView.setupWithNavController(navController)
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_item_addPost -> goToAddPost()
                else -> NavigationUI.onNavDestinationSelected(item, navController)
            }
            true
        }
    }

    private fun goToAddPost() {
        findNavController().deepLinkNavigate(NavDeepLinks.ADD_POST)
    }

}