package com.mohaberabi.linkedinclone.register.data.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.mohaberabi.linkedin.core.domain.source.local.UserLocalDataSource
import com.mohaberabi.linkedin.core.domain.util.DispatchersProvider
import com.mohaberabi.linkedinclone.register.data.repository.DefaultRegisterRepository
import com.mohaberabi.linkedinclone.register.data.source.remote.FirebaseRegisterRemoteDataSource
import com.mohaberabi.linkedinclone.register.domain.repository.RegisterRepository
import com.mohaberabi.linkedinclone.register.domain.source.remote.RegisterRemoteDataSource
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
    fun provideRemoteDataSource(
        dispatchers: DispatchersProvider,
        auth: FirebaseAuth,
        firestore: FirebaseFirestore,
    ): RegisterRemoteDataSource = FirebaseRegisterRemoteDataSource(
        dispatchers = dispatchers,
        auth = auth,
        firestore = firestore
    )


    @Provides
    @Singleton
    fun provideRepository(
        remoteDataSource: RegisterRemoteDataSource,
        userLocalDataSource: UserLocalDataSource,
    ): RegisterRepository = DefaultRegisterRepository(
        registerRemoteDataSource = remoteDataSource,
        userLocalDataSource = userLocalDataSource
    )

    @Provides
    @Singleton
    fun provideUseCase(
        registerRepository: RegisterRepository,
    ) = RegisterUsecase(
        registerRepository = registerRepository,
    )

}