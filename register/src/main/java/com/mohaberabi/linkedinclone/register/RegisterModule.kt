package com.mohaberabi.linkedinclone.register


import com.mohaberabi.linkedin.core.domain.repository.AuthRepository
import com.mohaberabi.linkedinclone.register.domain.usecase.LoginUseCase
import com.mohaberabi.linkedinclone.register.domain.usecase.RegisterUsecase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RegisterModule {
    @Provides
    @Singleton
    fun provideRegisterUseCase(
        registerRepository: AuthRepository
    ): RegisterUsecase {
        return RegisterUsecase(registerRepository)
    }

    @Provides
    @Singleton
    fun provideLoginUseCase(
        registerRepository: AuthRepository
    ): LoginUseCase {
        return LoginUseCase(registerRepository)
    }


}
