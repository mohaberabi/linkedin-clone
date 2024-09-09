package com.mohaberabi.linkedinclone.core.data.source.remote

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.mohaberabi.linkedin.core.domain.error.AppException
import com.mohaberabi.linkedin.core.domain.error.ErrorModel
import com.mohaberabi.linkedin.core.domain.error.RemoteError
import com.mohaberabi.linkedin.core.domain.model.UserModel
import com.mohaberabi.linkedin.core.domain.util.DispatchersProvider
import com.mohaberabi.linkedin.core.domain.util.EndPoints
import com.mohaberabi.linkedin.core.domain.source.remote.AuthRemoteDataSource
import com.mohaberabi.linkedinclone.core.data.dto.RegisterRequest
import com.mohaberabi.linkedinclone.core.data.dto.UserDto
import com.mohaberabi.linkedinclone.core.data.dto.mapper.toUserModel
import com.mohaberabi.linkedinclone.core.data.util.safeCall
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject


class FirebaseAuthRemoteDataSource @Inject constructor(
    private val dispatchers: DispatchersProvider,
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : AuthRemoteDataSource {
    override suspend fun createUserWithEmailAndPassword(
        email: String,
        password: String,
        name: String,
        lastname: String,
        bio: String
    ): String {
        return withContext(dispatchers.io) {
            val registerResponse = auth.safeCall {
                createUserWithEmailAndPassword(email, password).await()
            }
            registerResponse?.let { authed ->
                firestore.safeCall {
                    val request = RegisterRequest(
                        uid = authed.user!!.uid,
                        name = name,
                        lastname = lastname,
                        bio = bio,
                        email = email
                    )
                    collection(EndPoints.USERS)
                        .document(request.uid)
                        .set(request).await()
                    request.uid
                }
            } ?: throw AppException.RemoteException(ErrorModel(type = RemoteError.UNKNOWN_ERROR))

        }

    }

    override suspend fun loginWithEmailAndPassword(
        email: String,
        password: String
    ): UserModel {

        return withContext(dispatchers.io) {
            val uid =
                auth.safeCall {
                    signInWithEmailAndPassword(
                        email,
                        password,
                    ).await()
                }.user?.uid
            uid?.let {
                firestore.safeCall {
                    collection(EndPoints.USERS)
                        .document(it)
                        .get()
                        .await()
                        .toObject(UserDto::class.java)?.toUserModel()
                }
            } ?: throw AppException.RemoteException(ErrorModel(type = RemoteError.UNKNOWN_ERROR))

        }
    }

    override suspend fun signOut() {
        auth.safeCall {
            signOut()
        }
    }

}