package com.mohaberabi.linkedinclone.core.data.source.remote

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.snapshots
import com.google.firebase.firestore.toObject
import com.mohaberabi.linkedin.core.domain.model.UserModel
import com.mohaberabi.linkedin.core.domain.source.remote.UserRemoteDataSource
import com.mohaberabi.linkedin.core.domain.util.DispatchersProvider
import com.mohaberabi.linkedin.core.domain.util.EndPoints
import com.mohaberabi.linkedinclone.core.data.dto.UserDto
import com.mohaberabi.linkedinclone.core.data.dto.mapper.toUserDto
import com.mohaberabi.linkedinclone.core.data.dto.mapper.toUserModel
import com.mohaberabi.linkedinclone.core.data.util.safeCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject


class FirebaseUserRemoteDataSource @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val dispatchers: DispatchersProvider
) : UserRemoteDataSource {
    override suspend fun updateUser(userModel: UserModel) {
        return withContext(dispatchers.io) {
            firestore.safeCall {
                collection(EndPoints.USERS).document(
                    userModel.uid,
                ).set(userModel.toUserDto()).await()
            }
        }
    }

    override fun listenToUser(
        uid: String,
    ): Flow<UserModel?> {
        return firestore.collection(EndPoints.USERS)
            .document(uid).snapshots()
            .map { it.toObject<UserDto>()?.toUserModel() }
            .flowOn(dispatchers.io)
    }

}