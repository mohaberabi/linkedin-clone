package com.mohaberabi.linkedinclone.jobs.domain.repository

import com.mohaberabi.linkedin.core.domain.error.ErrorModel
import com.mohaberabi.linkedin.core.domain.model.JobModel
import com.mohaberabi.linkedin.core.domain.util.AppResult

interface JobRepository {
    suspend fun getJobs(
        lastElementId: String? = null,
        limit: Int = 20
    ): AppResult<List<JobModel>, ErrorModel>
}