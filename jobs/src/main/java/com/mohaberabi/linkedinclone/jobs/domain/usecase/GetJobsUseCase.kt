package com.mohaberabi.linkedinclone.jobs.domain.usecase

import com.mohaberabi.linkedinclone.jobs.domain.repository.JobRepository
import javax.inject.Inject


class GetJobsUseCase @Inject constructor(
    private val jobRepository: JobRepository,
) {
    suspend operator fun invoke(
        lastElementId: String? = null,
        limit: Int = 20
    ) = jobRepository.getJobs(
        limit = limit,
        lastElementId = lastElementId
    )
}