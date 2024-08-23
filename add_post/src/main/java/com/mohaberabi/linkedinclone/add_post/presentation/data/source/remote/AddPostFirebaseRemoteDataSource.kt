package com.mohaberabi.linkedinclone.add_post.presentation.data.source.remote

import com.google.firebase.firestore.FirebaseFirestore
import com.mohaberabi.linkedin.core.domain.util.DispatchersProvider
import com.mohaberabi.linkedin.core.domain.util.EndPoints
import com.mohaberabi.linkedinclone.add_post.presentation.data.source.remote.dto.AddPostRequest
import com.mohaberabi.linkedinclone.add_post.presentation.domain.source.remote.AddPostRemoteDataSource
import com.mohaberabi.linkedinclone.firestore.safeCall
import kotlinx.coroutines.withContext
import java.util.UUID
import javax.inject.Inject


class AddPostFirebaseRemoteDataSource @Inject constructor(
    private val dispatchers: DispatchersProvider,
    private val firestore: FirebaseFirestore,
) : AddPostRemoteDataSource {
    override suspend fun addPost(
        issuerBio: String,
        issuerAvatar: String,
        postData: String,
        issuerUid: String,
        issuerName: String,
        postAttachedImg: String?,
        postId: String,
    ) {
        return withContext(dispatchers.io) {
            firestore.safeCall {
                val request = AddPostRequest(
                    id = postId,
                    issuerName = issuerName,
                    issuerBio = issuerBio,
                    issuerUid = issuerUid,
                    issuerAvatar = issuerAvatar,
                    createdAtMillis = System.currentTimeMillis(),
                    postData = postData,
                    postAttachedImg = postAttachedImg ?: "",
                )
                collection(EndPoints.Posts).document(request.id).set(request)
            }
        }
    }

}