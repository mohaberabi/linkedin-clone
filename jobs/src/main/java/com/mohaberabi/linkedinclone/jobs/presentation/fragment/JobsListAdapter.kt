package com.mohaberabi.linkedinclone.jobs.presentation.fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.mohaberabi.core.presentation.ui.R
import com.mohaberabi.jobs.databinding.FragmentJobsFragmentsBinding
import com.mohaberabi.jobs.databinding.JobListItemBinding
import com.mohaberabi.linkedin.core.domain.model.JobModel
import com.mohaberabi.presentation.ui.util.AppListAdapter
import com.mohaberabi.presentation.ui.util.cached
import com.mohaberabi.presentation.ui.util.toTimeAgo


class JobsListAdapter(
    private val onClick: (JobModel) -> Unit,
) : AppListAdapter<JobModel, JobsListAdapter.JobsViewHolder>({ it.id }) {


    inner class JobsViewHolder(
        private val binding: JobListItemBinding,
    ) : ViewHolder(binding.root) {
        fun bind(job: JobModel) {
            with(binding) {
                jobPlaceTextView.text = job.jobPlace
                jobTitleTextView.text = job.jobTitle
                companyNameTextView.text = job.company
                companyLogoImageView.cached(job.companyLogo)
                postedAtTextView.text = job.postedAtMillis.toTimeAgo(binding.root.context)
                root.setOnClickListener {
                    onClick(job)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): JobsViewHolder {
        val binding = JobListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return JobsViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: JobsViewHolder,
        position: Int
    ) = holder.bind(getItem(position))
}