package com.mohaberabi.linkedinclone.posts.data.source.remote

import com.google.firebase.firestore.FirebaseFirestore
import com.mohaberabi.linkedin.core.domain.model.PostModel
import com.mohaberabi.linkedin.core.domain.util.CommonParams
import com.mohaberabi.linkedin.core.domain.util.DispatchersProvider
import com.mohaberabi.linkedin.core.domain.util.EndPoints
import com.mohaberabi.linkedinclone.firestore.paginate
import com.mohaberabi.linkedinclone.firestore.safeCall
import com.mohaberabi.linkedinclone.posts.data.source.dto.PostDto
import com.mohaberabi.linkedinclone.posts.data.source.dto.mapper.toPostModel
import com.mohaberabi.linkedinclone.posts.domain.source.remote.PostsRemoteDataSource
import javax.inject.Inject


class FirebasePostsRemoteDataSource
@Inject constructor(
    private val firestore: FirebaseFirestore,
    private val dispatchers: DispatchersProvider,
) : PostsRemoteDataSource {
    override suspend fun getPosts(
        limit: Int,
        lastDocId: String?,
    ): List<PostModel> {

        return firestore.safeCall {
            paginate<PostDto>(
                limit = limit.toLong(),
                lastDocId = lastDocId,
                coll = EndPoints.Posts,
                orderBy = CommonParams.CREATED_AT_MILLIS
            ).map {
                it.toPostModel()
            }
        }
    }
}