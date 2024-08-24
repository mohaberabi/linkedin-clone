package com.mohaberabi.linkedinclone.core.data.source.remote

import com.google.firebase.storage.FirebaseStorage
import com.mohaberabi.linkedin.core.domain.error.AppException
import com.mohaberabi.linkedin.core.domain.error.ErrorModel
import com.mohaberabi.linkedin.core.domain.error.RemoteError
import com.mohaberabi.linkedin.core.domain.source.remote.StorageClient
import com.mohaberabi.linkedin.core.domain.util.DispatchersProvider
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject


class FirebaseStorageStorageClient @Inject constructor(
    private val dispatchers: DispatchersProvider,
    private val storage: FirebaseStorage,
) : StorageClient {
    override suspend fun upload(
        bytes: ByteArray,
        reference: String
    ): String {
        return withContext(dispatchers.io) {
            try {
                val storageRef = storage.reference.child(reference)
                storageRef.putBytes(bytes).await()
                val downloadUrl = storageRef.downloadUrl.await()
                downloadUrl.toString()
            } catch (e: Exception) {
                throw AppException.RemoteException(ErrorModel(type = RemoteError.UNKNOWN_ERROR))
            }
        }
    }

}