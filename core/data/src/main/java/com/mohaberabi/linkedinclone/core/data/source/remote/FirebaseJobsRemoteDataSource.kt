package com.mohaberabi.linkedinclone.core.data.source.remote

import com.google.firebase.firestore.FirebaseFirestore

import com.mohaberabi.linkedin.core.domain.model.JobModel
import com.mohaberabi.linkedin.core.domain.util.CommonParams
import com.mohaberabi.linkedin.core.domain.util.DispatchersProvider
import com.mohaberabi.linkedin.core.domain.util.EndPoints
import com.mohaberabi.linkedin.core.domain.source.remote.JobRemoteDataSource
import com.mohaberabi.linkedinclone.core.data.dto.JobDto
import com.mohaberabi.linkedinclone.core.data.dto.mapper.toJobModel
import com.mohaberabi.linkedinclone.core.data.util.paginate
import com.mohaberabi.linkedinclone.core.data.util.safeCall
import kotlinx.coroutines.withContext
import javax.inject.Inject


class FirebaseJobsRemoteDataSource @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val dispatchers: DispatchersProvider,
) : JobRemoteDataSource {


    override suspend fun getJobs(
        lastElementId: String?,
        limit: Int
    ): List<JobModel> {
        return withContext(dispatchers.io) {
            val jobs = firestore.safeCall {
                paginate<JobDto>(
                    limit = limit.toLong(),
                    lastDocId = lastElementId,
                    coll = EndPoints.JOBS,
                    orderBy = CommonParams.POSTED_AT_MILLIS
                )
            }
            jobs.map { it.toJobModel() }
        }
    }

}

