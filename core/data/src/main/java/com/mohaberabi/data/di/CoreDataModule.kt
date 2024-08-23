package com.mohaberabi.data.di

import androidx.transition.Visibility.Mode
import com.mohaberabi.data.util.DefaultDispatchersProvider
import com.mohaberabi.data.util.GlobalDrawerController
import com.mohaberabi.linkedin.core.domain.util.DispatchersProvider
import com.mohaberabi.linkedin.core.domain.util.DrawerController
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)


abstract class CoreDataModule {
    @Binds
    @Singleton
    abstract fun bindDispatchersProvider(
        defaultDispatchersProvider: DefaultDispatchersProvider
    ): DispatchersProvider

    @Binds
    @Singleton
    abstract fun bindDrawerController(
        globalDrawerController: GlobalDrawerController
    ): DrawerController


}