package com.mohaberbai.linkedinclone.jobs.presentation.details.fragment

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mohaberabi.jobs.databinding.FragmentJobDetailBinding
import com.mohaberabi.presentation.ui.views.asPrimarySheet
import com.mohaberabi.presentation.ui.util.extension.collectLifeCycleFlow
import com.mohaberabi.presentation.ui.views.bottomSheet
import com.mohaberbai.linkedinclone.jobs.presentation.details.viewmodel.JobDetailActions
import com.mohaberbai.linkedinclone.jobs.presentation.details.viewmodel.JobDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class JobDetailFragment(
) : BottomSheetDialogFragment() {
    private lateinit var jobId: String
    private val viewmodel by viewModels<JobDetailsViewModel>()
    private var _binding: FragmentJobDetailBinding? = null
    private val binding get() = _binding!!

    companion object {
        private const val JOB_ID_ARG = "jobId"

        @JvmStatic
        fun newInstance(jobId: String) =
            JobDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(JOB_ID_ARG, jobId)
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentJobDetailBinding.inflate(
            layoutInflater,
            container,
            false
        )
        binding.toolbar.setNavigationOnClickListener {
            dismiss()
        }
        viewmodel.onAction(JobDetailActions.JobIdChanged(jobId))
        collectLifeCycleFlow(
            viewmodel.state,
        ) { state ->
            binding.bindWithState(state)
        }

        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        dialog.setOnShowListener { dialogInterface ->
            val bottomSheetDialog = dialogInterface as BottomSheetDialog
            val bottomSheet = bottomSheetDialog.bottomSheet
            bottomSheet?.asPrimarySheet()
        }
        return dialog
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            jobId = it.getString(JOB_ID_ARG) ?: ""
        }
    }
}

