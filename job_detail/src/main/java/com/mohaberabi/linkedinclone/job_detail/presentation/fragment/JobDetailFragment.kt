package com.mohaberabi.linkedinclone.job_detail.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.mohaberabi.job_detail.R
import com.mohaberabi.job_detail.databinding.FragmentJobDetailBinding
import com.mohaberabi.linkedinclone.job_detail.presentation.viewmodel.JobDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class JobDetailFragment : Fragment() {
    private val viewmodel by viewModels<JobDetailsViewModel>()
    private var _binding: FragmentJobDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var detailRenderer: JobDetailRenderer
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentJobDetailBinding.inflate(
            layoutInflater,
            container,
            false
        )
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        detailRenderer = JobDetailRenderer(binding)
        viewLifecycleOwner.lifecycleScope.launch {
            viewmodel.state.collect { state ->
                detailRenderer.bind(state)
            }
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}