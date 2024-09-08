package com.mohaberabi.linkedinclone.core.data.repository

import com.mohaberabi.linkedin.core.domain.error.ErrorModel
import com.mohaberabi.linkedin.core.domain.model.PostModel
import com.mohaberabi.linkedin.core.domain.repository.SavedPostsRepository
import com.mohaberabi.linkedin.core.domain.source.local.posts.PostsLocalDataSource
import com.mohaberabi.linkedin.core.domain.source.local.user.UserLocalDataSource
import com.mohaberabi.linkedin.core.domain.source.remote.PostReactionsRemoteDataSource
import com.mohaberabi.linkedin.core.domain.source.remote.PostsRemoteDataSource
import com.mohaberabi.linkedin.core.domain.source.remote.SavedPostRemoteDataSource
import com.mohaberabi.linkedin.core.domain.util.AppResult
import com.mohaberabi.linkedin.core.domain.util.AppSuperVisorScope
import com.mohaberabi.linkedin.core.domain.util.EmptyDataResult
import com.mohaberabi.linkedinclone.core.data.source.remote.FirebaseSavedPostsRemoteDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import javax.inject.Inject

class DefaultSavedPostsRepository @Inject constructor(
    private val savedPostsRemoteDataSource: SavedPostRemoteDataSource,
    private val postsRemoteDataSource: PostsRemoteDataSource,
    private val userLocalDataSource: UserLocalDataSource,
    private val reactionsRemoteDataSource: PostReactionsRemoteDataSource,
    private val postsLocalDataSource: PostsLocalDataSource,
    private val appScope: AppSuperVisorScope
) : SavedPostsRepository {
    override suspend fun savePost(
        postId: String,
    ): EmptyDataResult<ErrorModel> {
        return AppResult.handle {
            val uid = userLocalDataSource.getUid()
            savedPostsRemoteDataSource.savePost(
                postId = postId,
                uid = uid
            )
        }
    }

    override fun listenToSavedPosts(): Flow<List<PostModel>> {
        return postsLocalDataSource.getSavedPosts()
    }

    override suspend fun getSavedPostsWithReactions(
        limit: Int,
        lastDocId: String?
    ): EmptyDataResult<ErrorModel> {
        return AppResult.handle {
            val uid = userLocalDataSource.getUid()
            val savedPosts = savedPostsRemoteDataSource.getSavedPosts(
                uid = uid,
                limit = limit,
                lastDocId = lastDocId
            )
            val savedPostsIds = savedPosts.map { it.postId }

            coroutineScope {
                val posts =
                    async {
                        postsRemoteDataSource.getPostsByIds(postsIds = savedPostsIds)
                    }.await()

                val reactionsMap = async {
                    reactionsRemoteDataSource.getUsersReactionsOnPosts(
                        uid = uid,
                        postIds = savedPostsIds
                    )
                }.await()

                val upsertPosts = posts.map {
                    it.copy(
                        currentUserReaction = reactionsMap[it.id],
                        isSaved = true
                    )

                }
                val wasDeletedPostsIds = posts.filter { it.id !in savedPostsIds }.map { it.id }
                appScope().launch {
                    val upsertJob = launch {
                        postsLocalDataSource.upsertPosts(upsertPosts)
                    }
                    val deleteJob = launch {

                        postsLocalDataSource.deleteAllPostsByIds(wasDeletedPostsIds)
                    }
                    joinAll(
                        upsertJob,
                        deleteJob
                    )
                }
            }

        }
    }
}