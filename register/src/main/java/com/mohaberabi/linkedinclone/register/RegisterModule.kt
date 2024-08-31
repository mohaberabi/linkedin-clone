package com.mohaberabi.linkedinclone.register


import com.mohaberabi.linkedin.core.domain.repository.RegisterRepository
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
    fun provideUseCase(
        registerRepository: RegisterRepository
    ): RegisterUsecase {
        return RegisterUsecase(registerRepository)
    }

}
