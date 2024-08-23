package com.mohaberabi.linkedinclone.register.data.source.remote

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.mohaberabi.linkedin.core.domain.error.AppException
import com.mohaberabi.linkedin.core.domain.error.ErrorModel
import com.mohaberabi.linkedin.core.domain.error.RemoteError
import com.mohaberabi.linkedin.core.domain.util.DispatchersProvider
import com.mohaberabi.linkedin.core.domain.util.EndPoints
import com.mohaberabi.linkedinclone.firebase_auth.safeCall
import com.mohaberabi.linkedinclone.firestore.safeCall
import com.mohaberabi.linkedinclone.register.data.source.remote.dto.RegisterRequest
import com.mohaberabi.linkedinclone.register.domain.source.remote.RegisterRemoteDataSource
import kotlinx.coroutines.async
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject


class FirebaseRegisterRemoteDataSource @Inject constructor(
    private val dispatchers: DispatchersProvider,
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : RegisterRemoteDataSource {
    override suspend fun createUserWithEmailAndPassword(
        email: String,
        password: String,
        name: String,
        lastname: String,
        bio: String
    ): String {
        return withContext(dispatchers.io) {
            val registerDeferred = async {
                auth.safeCall {
                    createUserWithEmailAndPassword(email, password).await()
                }
            }.await()
            registerDeferred?.let { authed ->
                firestore.safeCall {
                    val request = RegisterRequest(
                        uid = authed.user!!.uid,
                        name = name,
                        lastname = lastname,
                        bio = bio,
                        img = "",
                        cover = "",
                        email = email
                    )
                    collection(EndPoints.USERS).document(request.uid).set(request).await()
                    request.uid
                }
            } ?: throw AppException.RemoteException(ErrorModel(type = RemoteError.UNKNOWN_ERROR))

        }

    }

}