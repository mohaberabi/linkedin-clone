package com.mohaberabi.linkedinclone.core.data.di

import com.mohaberabi.linkedin.core.domain.error.ErrorModel
import com.mohaberabi.linkedin.core.domain.model.UserModel
import com.mohaberabi.linkedin.core.domain.repository.UserRepository
import com.mohaberabi.linkedin.core.domain.usecase.GetUserUseCase
import com.mohaberabi.linkedin.core.domain.usecase.ListenToCurrentUserUseCase
import com.mohaberabi.linkedin.core.domain.util.AppResult
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {


    @Singleton
    @Provides
    fun provideGetUserUseCase(
        userRepository: UserRepository
    ): GetUserUseCase {
        return object : GetUserUseCase {
            override suspend fun invoke(
                uid: String,
            ): AppResult<UserModel?, ErrorModel> {
                return userRepository.getUser(uid)
            }
        }
    }

    @Singleton
    @Provides
    fun provideListenToUserUseCase(
        userRepository: UserRepository
    ): ListenToCurrentUserUseCase {
        return object : ListenToCurrentUserUseCase {
            override fun invoke(): Flow<UserModel?> {
                return userRepository.listenToCurrentUser()
            }
        }
    }

}