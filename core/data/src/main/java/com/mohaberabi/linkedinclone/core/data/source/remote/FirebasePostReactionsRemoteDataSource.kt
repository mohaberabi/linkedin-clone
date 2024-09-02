package com.mohaberabi.linkedinclone.core.data.source.remote

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.snapshots
import com.mohaberabi.linkedin.core.domain.model.ReactionModel
import com.mohaberabi.linkedin.core.domain.model.ReactionType
import com.mohaberabi.linkedin.core.domain.source.remote.PostReactionsRemoteDataSource
import com.mohaberabi.linkedin.core.domain.util.CommonParams
import com.mohaberabi.linkedin.core.domain.util.DispatchersProvider
import com.mohaberabi.linkedin.core.domain.util.EndPoints
import com.mohaberabi.linkedinclone.core.data.dto.ReactionDto
import com.mohaberabi.linkedinclone.core.data.dto.mapper.toReactionDto
import com.mohaberabi.linkedinclone.core.data.dto.mapper.toReactionModel
import com.mohaberabi.linkedinclone.core.data.util.paginate
import com.mohaberabi.linkedinclone.core.data.util.safeCall
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject


class FirebasePostReactionsRemoteDataSource @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val dispatchers: DispatchersProvider,
) : PostReactionsRemoteDataSource {


    override suspend fun reactToPost(
        reaction: ReactionModel,
        incrementCount: Int,
    ) {
        return withContext(dispatchers.io) {
            coroutineScope {
                firestore.safeCall {
                    val postReactions = getPostReactionsDoc(
                        reactorId = reaction.reactorId,
                        postId = reaction.postId,
                    )
                    val userReactions =
                        userReactionsCollection(reaction.reactorId).document(reaction.postId)
                    val postDoc = postsCollection.document(reaction.postId)
                    val postUpdatedData = createReactionUpdateMap(
                        value = incrementCount.toLong(),
                    )

                    runTransaction { txn ->
                        val reactionDto = reaction.toReactionDto()
                        txn.set(
                            postReactions,
                            reactionDto,
                        )
                        txn.set(
                            userReactions,
                            reactionDto,
                        )
                        txn.update(
                            postDoc,
                            postUpdatedData
                        )
                    }.await()
                }

            }
        }
    }

    override suspend fun undoReactToPost(
        postId: String,
        reactorId: String,
    ) {

        withContext(dispatchers.io) {
            firestore.safeCall {
                val globalReactionRef = getPostReactionsDoc(
                    reactorId = reactorId,
                    postId = postId,
                )
                val userReactionRef =
                    userReactionsCollection(reactorId).document(postId)
                runTransaction { txn ->
                    val postDoc = postsCollection.document(postId)
                    val postUpdatedData = createReactionUpdateMap(
                        value = -1,
                    )
                    txn.delete(globalReactionRef)
                    txn.delete(userReactionRef)
                    txn.update(
                        postDoc,
                        postUpdatedData
                    )
                }.await()
            }
        }
    }

    override suspend fun getUserReactionOnPost(
        postId: String,
        uid: String
    ): ReactionType? {
        return withContext(dispatchers.io) {
            firestore.safeCall {
                collection(EndPoints.USERS)
                    .document(uid)
                    .collection(EndPoints.REACTIONS)
                    .document(postId).get().await()
                    .toObject(ReactionDto::class.java)?.toReactionModel()?.reactionType
            }
        }
    }

    override suspend fun getUsersReactionsOnPosts(
        uid: String,
        postIds: List<String>
    ): Map<String, ReactionType> {
        return withContext(dispatchers.io) {
            firestore.safeCall {
                val filter = Filter.inArray(
                    FieldPath.documentId(),
                    postIds
                )
                val reactions = userReactionsCollection(
                    uid = uid,
                ).where(
                    filter
                ).get().await().map {
                    it.toObject(ReactionDto::class.java).toReactionModel()
                }
                reactions.associateBy(
                    keySelector = { it.postId },
                    valueTransform = { it.reactionType })
            }
        }

    }


    override suspend fun getPostReactions(
        postId: String,
        reactionType: String?,
        limit: Int,
        lastDocId: String?
    ): List<ReactionModel> {
        return withContext(dispatchers.io) {
            firestore.safeCall {
                val collection = collection(EndPoints.Posts)
                    .document(postId)
                    .collection(EndPoints.REACTIONS)
                val filters = reactionType?.let {
                    listOf(Filter.equalTo(CommonParams.REACTION_TYPE, it))
                } ?: emptyList()

                val reactions = paginate<ReactionDto>(
                    limit = limit.toLong(),
                    lastDocId = lastDocId,
                    collection = collection,
                    filters = filters,
                    orderBy = CommonParams.CREATED_AT_MILLIS
                )
                reactions.map { it.toReactionModel() }
            }

        }
    }

    override fun listenToPostReactions(
        postId: String,
        limit: Int
    ): Flow<List<ReactionModel>> {
        return firestore.collection(EndPoints.Posts)
            .document(postId)
            .collection(EndPoints.REACTIONS)
            .limit(limit.toLong())
            .orderBy(CommonParams.CREATED_AT_MILLIS, Query.Direction.DESCENDING)
            .snapshots()
            .map { docs -> docs.map { it.toObject(ReactionDto::class.java).toReactionModel() } }
            .flowOn(dispatchers.io)
    }


    private fun getPostReactionsDoc(
        reactorId: String,
        postId: String,
    ): DocumentReference {
        return firestore.collection(EndPoints.Posts)
            .document(postId)
            .collection(EndPoints.REACTIONS)
            .document(reactorId)
    }


    private val postsCollection: CollectionReference
        get() = firestore.collection(EndPoints.Posts)

    private fun userReactionsCollection(uid: String) =
        firestore.collection(EndPoints.USERS)
            .document(uid)
            .collection(EndPoints.REACTIONS)

    private fun createReactionUpdateMap(
        value: Long,
    ) = mapOf(
        CommonParams.REACT_COUNT to FieldValue.increment(value),
    )
}
