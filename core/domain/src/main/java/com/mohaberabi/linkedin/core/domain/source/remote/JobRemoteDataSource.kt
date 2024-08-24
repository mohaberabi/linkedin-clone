package com.mohaberabi.linkedin.core.domain.source.remote

import com.mohaberabi.linkedin.core.domain.model.JobModel


interface JobRemoteDataSource {


    suspend fun getJobs(
        lastElementId: String? = null,
        limit: Int = 20
    ): List<JobModel>
}