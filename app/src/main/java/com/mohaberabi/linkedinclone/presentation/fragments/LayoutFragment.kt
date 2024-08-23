package com.mohaberabi.linkedinclone.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.mohaberabi.linkedinclone.R
import com.mohaberabi.linkedinclone.databinding.FragmentLayoutBinding


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
        val navHostFragment =
            childFragmentManager.findFragmentById(R.id.nav_host_fragment_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}