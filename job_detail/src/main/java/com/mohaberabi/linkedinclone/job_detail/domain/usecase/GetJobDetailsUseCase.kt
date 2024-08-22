package com.mohaberabi.linkedinclone.job_detail.domain.usecase

import com.mohaberabi.linkedinclone.job_detail.domain.repository.JobDetailRepository
import javax.inject.Inject

class GetJobDetailsUseCase @Inject constructor(
    private val jobDetailRepository: JobDetailRepository
) {

    suspend operator fun invoke(
        id: String
    ) = jobDetailRepository.getJobDetail(id = id)
}