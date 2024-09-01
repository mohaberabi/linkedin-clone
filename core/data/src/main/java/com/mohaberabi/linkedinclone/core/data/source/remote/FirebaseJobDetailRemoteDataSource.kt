package com.mohaberabi.linkedinclone.core.data.source.remote

import com.google.firebase.firestore.FirebaseFirestore
import com.mohaberabi.linkedin.core.domain.model.JobDetailModel
import com.mohaberabi.linkedin.core.domain.model.JobTime
import com.mohaberabi.linkedin.core.domain.model.JobType
import com.mohaberabi.linkedin.core.domain.util.DispatchersProvider
import com.mohaberabi.linkedin.core.domain.util.EndPoints
import com.mohaberabi.linkedin.core.domain.source.remote.JobDetailRemoteDataSource
import com.mohaberabi.linkedinclone.core.data.dto.JobDetailDto
import com.mohaberabi.linkedinclone.core.data.dto.mapper.toJobDetailModel
import com.mohaberabi.linkedinclone.core.data.util.safeCall
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.random.Random

class FirebaseJobDetailRemoteDataSource @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val dispatchers: DispatchersProvider,
) : JobDetailRemoteDataSource {
    override suspend fun getJobDetail(
        id: String,
    ): JobDetailModel {
        return withContext(dispatchers.io) {
            firestore.safeCall {
                collection(EndPoints.JOB_DETAILS).document(id).get().await()
                    .toObject(JobDetailDto::class.java)!!.toJobDetailModel()
            }
        }
    }


}

