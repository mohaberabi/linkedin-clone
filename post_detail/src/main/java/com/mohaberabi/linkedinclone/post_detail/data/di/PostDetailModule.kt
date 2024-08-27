package com.mohaberabi.linkedinclone.post_detail.data.di

import com.google.firebase.firestore.FirebaseFirestore
import com.mohaberabi.linkedin.core.domain.source.local.user.UserLocalDataSource
import com.mohaberabi.linkedin.core.domain.source.remote.PostReactionsRemoteDataSource
import com.mohaberabi.linkedin.core.domain.source.remote.PostsRemoteDataSource
import com.mohaberabi.linkedin.core.domain.util.DispatchersProvider
import com.mohaberabi.linkedinclone.core.data.source.remote.FirebasePostsRemoteDataSource
import com.mohaberabi.linkedinclone.post_detail.data.repository.DefaultPostDetailRepository
import com.mohaberabi.linkedinclone.post_detail.domain.repository.PostDetailRepository
import com.mohaberabi.linkedinclone.post_detail.domain.source.remote.PostCommentRemoteDataSource
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Singleton
@InstallIn(SingletonComponent::class)
object PostDetailModule {


    @Singleton
    @Provides
    fun providePostCommentSource(
        firestore: FirebaseFirestore,
        dispatchers: DispatchersProvider,
    ): PostsRemoteDataSource = FirebasePostsRemoteDataSource(
        firestore = firestore,
        dispatchers = dispatchers
    )

    @Singleton
    @Provides
    fun providePostDetailRepository(
        postCommentRemoteDataSource: PostCommentRemoteDataSource,
        postsRemoteDataSource: PostsRemoteDataSource,
        userLocalDataSource: UserLocalDataSource,
        reactionsRemoteDataSource: PostReactionsRemoteDataSource,
    ): PostDetailRepository = DefaultPostDetailRepository(
        userLocalDataSource = userLocalDataSource,
        reactionsRemoteDataSource = reactionsRemoteDataSource,
        postsRemoteDataSource = postsRemoteDataSource,
        postCommentRemoteDataSource = postCommentRemoteDataSource
    )


}