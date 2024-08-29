package com.mohaberabi.linkedinclone.post_detail.data.repository

import com.mohaberabi.linkedin.core.domain.error.ErrorModel
import com.mohaberabi.linkedin.core.domain.error.RemoteError
import com.mohaberabi.linkedin.core.domain.model.PostCommentModel
import com.mohaberabi.linkedin.core.domain.model.PostDetailModel
import com.mohaberabi.linkedin.core.domain.model.ReactionModel
import com.mohaberabi.linkedin.core.domain.model.ReactionType
import com.mohaberabi.linkedin.core.domain.source.local.user.UserLocalDataSource
import com.mohaberabi.linkedin.core.domain.source.remote.PostsRemoteDataSource
import com.mohaberabi.linkedin.core.domain.util.AppResult
import com.mohaberabi.linkedin.core.domain.util.EmptyDataResult
import com.mohaberabi.linkedinclone.post_detail.domain.repository.PostDetailRepository
import com.mohaberabi.linkedinclone.post_detail.domain.source.remote.PostCommentRemoteDataSource
import com.mohaberabi.linkedin.core.domain.source.remote.PostReactionsRemoteDataSource
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.first
import java.util.UUID
import javax.inject.Inject
import kotlin.random.Random

class DefaultPostDetailRepository @Inject constructor(
    private val postCommentRemoteDataSource: PostCommentRemoteDataSource,
    private val postsRemoteDataSource: PostsRemoteDataSource,
    private val reactionsRemoteDataSource: PostReactionsRemoteDataSource,
) : PostDetailRepository {
    override suspend fun getPostDetail(
        postId: String,
    ): AppResult<PostDetailModel, ErrorModel> {
        return AppResult.handleResult {
            coroutineScope {
                val post = postsRemoteDataSource.getPost(postId)
                post?.let {
                    val topComments =
                        async {
                            postCommentRemoteDataSource.getPostComments(postId = postId)
                        }.await()
                    val topReactions = async {
                        reactionsRemoteDataSource.getPostReactions(
                            postId = postId,
                        )
                    }.await()

                    val detail = PostDetailModel(
                        topReactions = topReactions.ifEmpty { testReactions },
                        topComments = topComments.ifEmpty { testComments },
                        post = it,
                    )
                    AppResult.Done(detail)
                } ?: AppResult.Error(ErrorModel(type = RemoteError.UNKNOWN_ERROR))

            }
        }
    }


}

private val testComments = buildList {


    repeat(40) {
        add(
            PostCommentModel(
                postId = "",
                comment = "Hey i found this lsoer mohab erbai he says that he is a programmer hahahahaahahah",
                commentedAtMillis = System.currentTimeMillis(),
                commenterId = "",
                commenterBio = "I Am A Big Loser $it",
                commentImg = "https://firebasestorage.googleapis.com/v0/b/linkedinclonedev.appspot.com/o/users%2Fimages%2FcBlQ5Lxj0Zgsl9D2wkQa1P0MSXz1?alt=media&token=9994412f-9fa9-440d-9560-36be31612b30",
                id = "$it"
            )
        )
    }
}
private val testReactions = buildList {


    repeat(40) {
        add(
            ReactionModel(
                postId = "",
                reactionType = ReactionType.entries[Random.nextInt(ReactionType.entries.size)],
                createdAtMillis = System.currentTimeMillis(),
                reactorId = "",
                reactorBio = "I Am A Big Loser $it",
                reactorImg = "https://firebasestorage.googleapis.com/v0/b/linkedinclonedev.appspot.com/o/users%2Fimages%2FcBlQ5Lxj0Zgsl9D2wkQa1P0MSXz1?alt=media&token=9994412f-9fa9-440d-9560-36be31612b30"
            )
        )
    }
}