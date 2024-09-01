package com.mohaberabi.linkedinclone.core.database.source

import com.mohaberabi.linkedin.core.domain.model.PostModel
import com.mohaberabi.linkedin.core.domain.source.local.posts.PostsLocalDataSource
import com.mohaberabi.linkedin.core.domain.util.DispatchersProvider
import com.mohaberabi.linkedinclone.core.database.dao.PostsDao
import com.mohaberabi.linkedinclone.core.database.entity.mappers.toPostEntity
import com.mohaberabi.linkedinclone.core.database.entity.mappers.toPostModel
import com.mohaberabi.linkedinclone.core.database.util.safeCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RoomPostsLocalDataSource @Inject constructor(
    private val postsDao: PostsDao,
    private val dispatchers: DispatchersProvider
) : PostsLocalDataSource {
    override suspend fun upsertPost(post: PostModel) {
        withContext(dispatchers.io) {
            safeCall {
                postsDao.upsertPost(post.toPostEntity())
            }
        }

    }

    override suspend fun upsertPosts(posts: List<PostModel>) {
        withContext(dispatchers.io) {
            safeCall {
                postsDao.upsertPosts(posts.map { it.toPostEntity() })
            }
        }

    }


    override fun getPosts(): Flow<List<PostModel>> {
        return postsDao.getPosts().map { list ->
            list.map { it.toPostModel() }
        }.flowOn(dispatchers.io)
    }

    override fun getPostById(postId: String): Flow<PostModel?> {
        return postsDao.getPostById(postId).map {
            it?.toPostModel()
        }.flowOn(dispatchers.io)
    }

    override suspend fun reactToPost(postId: String, reaction: String) {

        withContext(dispatchers.io) {
            safeCall {
                postsDao.reactToPost(postId = postId, reaction = reaction)
            }
        }
    }

    override suspend fun undoReaction(postId: String) {
        withContext(dispatchers.io) {
            safeCall {
                postsDao.undoReaction(postId = postId)
            }
        }
    }
}