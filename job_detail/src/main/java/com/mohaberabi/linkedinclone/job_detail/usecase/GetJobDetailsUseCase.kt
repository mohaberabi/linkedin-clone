package com.mohaberabi.linkedinclone.job_detail.usecase

import com.mohaberabi.linkedin.core.domain.repository.JobDetailRepository
import javax.inject.Inject

class GetJobDetailsUseCase @Inject constructor(
    private val jobDetailRepository: JobDetailRepository
) {

    suspend operator fun invoke(
        id: String
    ) = jobDetailRepository.getJobDetail(id = id)
}