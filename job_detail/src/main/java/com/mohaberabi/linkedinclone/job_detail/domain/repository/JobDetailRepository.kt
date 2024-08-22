package com.mohaberabi.linkedinclone.job_detail.domain.repository

import com.mohaberabi.linkedin.core.domain.error.ErrorModel
import com.mohaberabi.linkedin.core.domain.model.JobDetailModel
import com.mohaberabi.linkedin.core.domain.util.AppResult

interface JobDetailRepository {


    suspend fun getJobDetail(
        id: String,
    ): AppResult<JobDetailModel, ErrorModel>
}