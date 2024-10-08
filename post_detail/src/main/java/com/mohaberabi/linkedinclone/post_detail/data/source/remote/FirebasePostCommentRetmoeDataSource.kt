package com.mohaberabi.linkedinclone.post_detail.data.source.remote

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.snapshots
import com.google.firebase.firestore.toObject
import com.mohaberabi.linkedin.core.domain.model.PostCommentModel
import com.mohaberabi.linkedin.core.domain.util.CommonParams
import com.mohaberabi.linkedin.core.domain.util.DispatchersProvider
import com.mohaberabi.linkedin.core.domain.util.EndPoints
import com.mohaberabi.linkedinclone.core.data.util.paginate
import com.mohaberabi.linkedinclone.core.data.util.safeCall
import com.mohaberabi.linkedinclone.post_detail.data.source.dto.CommentDto
import com.mohaberabi.linkedinclone.post_detail.data.source.dto.mapper.toCommentDto
import com.mohaberabi.linkedinclone.post_detail.data.source.dto.mapper.toCommentModel
import com.mohaberabi.linkedinclone.post_detail.domain.source.remote.PostCommentRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject


class FirebasePostCommentRemoteDataSource @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val dispatchers: DispatchersProvider,
) : PostCommentRemoteDataSource {


    override suspend fun leaveComment(
        comment: PostCommentModel,
    ) {
        withContext(dispatchers.io) {
            firestore.safeCall {
                val updateMap = mapOf(
                    CommonParams.COMMENT_COUNT
                            to FieldValue.increment(1L)
                )
                val postDoc = collection(EndPoints.Posts)
                    .document(comment.postId)

                val commentDoc = postDoc
                    .collection(EndPoints.COMMENTS)
                    .document(comment.id)

                runTransaction { txn ->
                    txn.set(commentDoc, comment.toCommentDto())
                    txn.update(postDoc, updateMap)
                }.await()
            }


        }
    }

    override suspend fun getPostComments(
        postId: String,
        lastDocId: String?,
        limit: Int
    ): List<PostCommentModel> {
        return withContext(dispatchers.io) {

            val comments = firestore.safeCall {
                val postCommentsCollection = collection(EndPoints.Posts)
                    .document(postId)
                    .collection(EndPoints.COMMENTS)
                paginate<CommentDto>(
                    collection = postCommentsCollection,
                    lastDocId = lastDocId,
                    orderBy = CommonParams.COMMENTED_AT_MILLIS
                )
            }
            comments.map { it.toCommentModel() }
        }
    }

    override fun listenToPostComments(
        postId: String,
        limit: Int
    ): Flow<List<PostCommentModel>> {
        return firestore.collection(EndPoints.Posts)
            .document(postId)
            .collection(EndPoints.COMMENTS)
            .limit(limit.toLong())
            .orderBy(CommonParams.COMMENTED_AT_MILLIS, Query.Direction.DESCENDING)
            .snapshots()
            .map { snaps -> snaps.map { it.toObject(CommentDto::class.java).toCommentModel() } }
            .flowOn(dispatchers.io)

    }
}