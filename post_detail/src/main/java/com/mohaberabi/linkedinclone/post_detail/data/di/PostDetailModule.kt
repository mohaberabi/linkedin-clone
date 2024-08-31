package com.mohaberabi.linkedinclone.post_detail.data.di

import com.google.firebase.firestore.FirebaseFirestore
import com.mohaberabi.linkedin.core.domain.repository.PostsReactionRepository
import com.mohaberabi.linkedin.core.domain.source.local.user.UserLocalDataSource
import com.mohaberabi.linkedin.core.domain.source.remote.PostReactionsRemoteDataSource
import com.mohaberabi.linkedin.core.domain.source.remote.PostsRemoteDataSource
import com.mohaberabi.linkedin.core.domain.util.DispatchersProvider
import com.mohaberabi.linkedinclone.core.data.source.remote.FirebasePostsRemoteDataSource
import com.mohaberabi.linkedinclone.post_detail.data.repository.DefaultPostCommentRepository
import com.mohaberabi.linkedinclone.post_detail.data.repository.DefaultPostDetailRepository
import com.mohaberabi.linkedinclone.post_detail.data.source.remote.FirebasePostCommentRemoteDataSource
import com.mohaberabi.linkedinclone.post_detail.domain.repository.PostCommentRepository
import com.mohaberabi.linkedinclone.post_detail.domain.repository.PostDetailRepository
import com.mohaberabi.linkedinclone.post_detail.domain.source.remote.PostCommentRemoteDataSource
import com.mohaberabi.linkedinclone.post_detail.domain.usecase.CommentOnPostUseCase
import com.mohaberabi.linkedinclone.post_detail.domain.usecase.GetPostCommentsUseCase
import com.mohaberabi.linkedinclone.post_detail.domain.usecase.GetPostDetailUseCase
import com.mohaberabi.linkedinclone.post_detail.domain.usecase.GetPostReactionsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object PostDetailModule {


    @Singleton
    @Provides
    fun provideCommentsRemoteSource(
        firebase: FirebaseFirestore,
        dispatchers: DispatchersProvider
    ): PostCommentRemoteDataSource = FirebasePostCommentRemoteDataSource(firebase, dispatchers)

    @Singleton
    @Provides
    fun providePostDetailRepository(
        postCommentRemoteDataSource: PostCommentRemoteDataSource,
        postsRemoteDataSource: PostsRemoteDataSource,
        reactionsRemoteDataSource: PostReactionsRemoteDataSource,
    ): PostDetailRepository = DefaultPostDetailRepository(
        reactionsRemoteDataSource = reactionsRemoteDataSource,
        postsRemoteDataSource = postsRemoteDataSource,
        postCommentRemoteDataSource = postCommentRemoteDataSource
    )

    @Singleton
    @Provides
    fun providePostCommentRepository(
        postCommentRemoteDataSource: PostCommentRemoteDataSource,
        userLocalDataSource: UserLocalDataSource,
    ): PostCommentRepository = DefaultPostCommentRepository(
        userLocalDataSource = userLocalDataSource,
        postCommentRemoteDataSource = postCommentRemoteDataSource
    )


    @Singleton
    @Provides
    fun provideCommentOnPostUseCase(
        repo: PostCommentRepository
    ) = CommentOnPostUseCase(repo)

    @Singleton
    @Provides
    fun provideGetPostDetailUserCase(
        repo: PostDetailRepository
    ) = GetPostDetailUseCase(repo)


    @Singleton
    @Provides
    fun provideGetPostReactionsUseCase(
        repo: PostsReactionRepository
    ) = GetPostReactionsUseCase(repo)

    @Singleton
    @Provides
    fun provideGetPostCommentUseCase(
        repo: PostCommentRepository
    ) = GetPostCommentsUseCase(repo)
}