package com.mohaberabi.linkedinclone.job_detail.domain.source.remote

import com.mohaberabi.linkedin.core.domain.model.JobDetailModel

interface JobDetailRemoteDataSource {


    suspend fun getJobDetail(
        id: String,
    ): JobDetailModel
}