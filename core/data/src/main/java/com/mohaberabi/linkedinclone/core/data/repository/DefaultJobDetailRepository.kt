package com.mohaberabi.linkedinclone.core.data.repository

import com.mohaberabi.linkedin.core.domain.error.ErrorModel
import com.mohaberabi.linkedin.core.domain.model.JobDetailModel
import com.mohaberabi.linkedin.core.domain.util.AppResult
import com.mohaberabi.linkedin.core.domain.repository.JobDetailRepository
import com.mohaberabi.linkedin.core.domain.source.remote.JobDetailRemoteDataSource
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