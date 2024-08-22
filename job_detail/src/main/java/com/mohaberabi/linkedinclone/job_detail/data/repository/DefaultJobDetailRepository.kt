package com.mohaberabi.linkedinclone.job_detail.data.repository

import com.mohaberabi.linkedin.core.domain.error.ErrorModel
import com.mohaberabi.linkedin.core.domain.model.JobDetailModel
import com.mohaberabi.linkedin.core.domain.util.AppResult
import com.mohaberabi.linkedinclone.job_detail.data.source.remote.FirebaseJobDetailRemoteDataSource
import com.mohaberabi.linkedinclone.job_detail.domain.repository.JobDetailRepository
import com.mohaberabi.linkedinclone.job_detail.domain.source.remote.JobDetailRemoteDataSource
import javax.inject.Inject

class DefaultJobDetailRepository @Inject constructor(
    private val jobDetailRemoteDataSource: JobDetailRemoteDataSource,
) : JobDetailRepository {
    override suspend fun getJobDetail(
        id: String,
    ): AppResult<JobDetailModel, ErrorModel> {
        return AppResult.handle {
            jobDetailRemoteDataSource.getJobDetail(id)
        }

    }
}