package com.mohaberabi.linkedinclone.login


import com.mohaberabi.linkedin.core.domain.repository.AuthRepository
import com.mohaberabi.linkedinclone.login.domain.usecase.LoginUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LoginModule {

    @Provides
    @Singleton
    fun provideLoginUseCase(
        registerRepository: AuthRepository
    ): LoginUseCase {
        return LoginUseCase(registerRepository)
    }


}
