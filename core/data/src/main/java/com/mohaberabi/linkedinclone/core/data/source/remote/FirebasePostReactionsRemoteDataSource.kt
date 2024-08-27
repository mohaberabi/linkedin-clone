package com.mohaberabi.linkedinclone.core.data.source.remote

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.mohaberabi.linkedin.core.domain.model.ReactionModel
import com.mohaberabi.linkedin.core.domain.source.remote.PostReactionsRemoteDataSource
import com.mohaberabi.linkedin.core.domain.util.CommonParams
import com.mohaberabi.linkedin.core.domain.util.DispatchersProvider
import com.mohaberabi.linkedin.core.domain.util.EndPoints
import com.mohaberabi.linkedinclone.core.data.dto.ReactionDto
import com.mohaberabi.linkedinclone.core.data.dto.mapper.toReactionDto
import com.mohaberabi.linkedinclone.core.data.dto.mapper.toReactionModel
import com.mohaberabi.linkedinclone.core.data.util.paginate
import com.mohaberabi.linkedinclone.core.data.util.safeCall
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject


class FirebasePostReactionsRemoteDataSource @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val dispatchers: DispatchersProvider,
) : PostReactionsRemoteDataSource {
    override suspend fun reactToPost(
        reaction: ReactionModel,
    ) {
        withContext(dispatchers.io) {
            firestore.safeCall {
                val reactionReference = collection(EndPoints.Posts)
                    .document(reaction.postId)
                    .collection(EndPoints.REACTIONS)
                    .document(reaction.reactorId)
                val currentReaction: ReactionDto? = reactionReference.get()
                    .await().toObject(ReactionDto::class.java)
                val existsBefore =
                    currentReaction != null && currentReaction.reactionType == reaction.reactionType.name
                val incrementedValue = when {
                    existsBefore -> -1
                    currentReaction != null -> 0
                    else -> 1
                }
                val updateMap = mapOf(
                    CommonParams.REACT_COUNT to FieldValue.increment(
                        incrementedValue.toLong(),
                    )
                )
                launch {
                    with(reactionReference) {
                        if (existsBefore) {
                            delete()
                        } else {
                            set(reaction.toReactionDto()).await()
                        }
                    }
                }
                launch {
                    collection(EndPoints.Posts)
                        .document(reaction.postId)
                        .update(updateMap).await()
                }
            }

        }
    }


    override suspend fun getUserReactionOnPost(
        postId: String,
        reactorId: String
    ): ReactionModel? {
        return withContext(dispatchers.io) {
            firestore.safeCall {
                collection(EndPoints.Posts)
                    .document(postId)
                    .collection(EndPoints.REACTIONS)
                    .document(reactorId)
                    .get()
                    .await().toObject(ReactionDto::class.java)
                    ?.toReactionModel()
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
                val reactionsCollection = collection(EndPoints.Posts)
                    .document(postId)
                    .collection(EndPoints.REACTIONS)
                val reactions = paginate<ReactionDto>(
                    limit = limit.toLong(),
                    lastDocId = lastDocId,
                    collection = reactionsCollection,
                    orderBy = CommonParams.CREATED_AT_MILLIS
                )
                reactions.map { it.toReactionModel() }
            }

        }
    }

}