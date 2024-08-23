package com.mohaberabi.linkedinclone.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.mohaberabi.linkedinclone.R
import com.mohaberabi.linkedinclone.databinding.FragmentTestBinding


class TestFragment : Fragment() {


    private var _binding: FragmentTestBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentTestBinding.inflate(
            layoutInflater,
            container,
            false
        )
        binding.button.setOnClickListener {
            val navController = findNavController()
            val navOptions = NavOptions.Builder()
                .setPopUpTo(
                    navController.graph.startDestinationId,
                    inclusive = true
                )
                .build()
            navController.navigate(
                R.id.action_testFragment_to_layoutFragment,
                null,
                navOptions
            )
        }
        return binding.root
    }

}