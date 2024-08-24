package com.mohaberabi.linkedinclone.core.data.repository

import com.mohaberabi.linkedin.core.domain.error.ErrorModel
import com.mohaberabi.linkedin.core.domain.model.AppFile
import com.mohaberabi.linkedin.core.domain.model.JobModel
import com.mohaberabi.linkedin.core.domain.util.AppResult
import com.mohaberabi.linkedin.core.domain.repository.JobRepository
import com.mohaberabi.linkedin.core.domain.source.remote.JobRemoteDataSource
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class DefaultJobRepository @Inject constructor(
    private val jobRemoteDataSource: JobRemoteDataSource,
) : JobRepository {
    override suspend fun getJobs(
        lastElementId: String?,
        limit: Int
    ): AppResult<List<JobModel>, ErrorModel> = AppResult.handle {
        jobRemoteDataSource.getJobs(
            lastElementId = lastElementId,
            limit = limit
        )
    }

}