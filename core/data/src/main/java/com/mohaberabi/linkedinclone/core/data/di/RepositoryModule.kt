package com.mohaberabi.linkedinclone.core.data.di

import com.mohaberabi.linkedin.core.domain.repository.JobDetailRepository
import com.mohaberabi.linkedin.core.domain.repository.JobRepository
import com.mohaberabi.linkedin.core.domain.repository.PostsReactionRepository
import com.mohaberabi.linkedin.core.domain.repository.PostsRepository
import com.mohaberabi.linkedin.core.domain.repository.AuthRepository
import com.mohaberabi.linkedin.core.domain.repository.UserMediaRepository
import com.mohaberabi.linkedin.core.domain.repository.UserRepository
import com.mohaberabi.linkedinclone.core.data.repository.DefaultJobDetailRepository
import com.mohaberabi.linkedinclone.core.data.repository.DefaultJobRepository
import com.mohaberabi.linkedinclone.core.data.repository.DefaultPostReactionRepository
import com.mohaberabi.linkedinclone.core.data.repository.OfflineFirstPostsRepository
import com.mohaberabi.linkedinclone.core.data.repository.DefaultRegisterRepository
import com.mohaberabi.linkedinclone.core.data.repository.DefaultUserMediaRepository
import com.mohaberabi.linkedinclone.core.data.repository.DefaultUserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)


abstract class RepositoryModule {


    @Binds
    @Singleton
    abstract fun bindUserRepository(
        defaultUserRepository: DefaultUserRepository,
    ): UserRepository


    @Binds
    @Singleton
    abstract fun bindJobRepository(
        defaultJobRepository: DefaultJobRepository,
    ): JobRepository


    @Binds
    @Singleton
    abstract fun bindJobDetailRepository(
        defaultJobDetailRepository: DefaultJobDetailRepository,
    ): JobDetailRepository

    @Binds
    @Singleton
    abstract fun bindRegisterRepository(
        defaultRegisterRepository: DefaultRegisterRepository,
    ): AuthRepository


    @Binds
    @Singleton
    abstract fun bindPostRepository(
        defaultPosRepository: OfflineFirstPostsRepository,
    ): PostsRepository

    @Binds
    @Singleton
    abstract fun bindUserMediaRepository(
        defaultUserMediaRepository: DefaultUserMediaRepository,
    ): UserMediaRepository

    @Binds
    @Singleton
    abstract fun bindReactRepository(
        defaultPostReactionRepository: DefaultPostReactionRepository,
    ): PostsReactionRepository


}
