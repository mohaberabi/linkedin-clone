package com.mohaberabi.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.transition.Visibility.Mode
import com.mohaberabi.data.source.local.persistence.DataStorePersistenceClient
import com.mohaberabi.data.source.local.user.UserDataStore
import com.mohaberabi.data.util.DefaultDispatchersProvider
import com.mohaberabi.data.util.GlobalDrawerController
import com.mohaberabi.linkedin.core.domain.source.local.PersistenceClient
import com.mohaberabi.linkedin.core.domain.source.local.UserLocalDataSource
import com.mohaberabi.linkedin.core.domain.util.DispatchersProvider
import com.mohaberabi.linkedin.core.domain.util.DrawerController
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)


object CoreDataModule {
    @Singleton
    @Provides
    fun provideDisaptcherProvider(
    ): DispatchersProvider = DefaultDispatchersProvider()

    @Singleton
    @Provides
    fun provideDrawerController(
    ): DrawerController = GlobalDrawerController()


    @Singleton
    @Provides

    fun providePersistenceClient(
        @ApplicationContext context: Context,
        dispatchers: DispatchersProvider,
    ): PersistenceClient = DataStorePersistenceClient(
        dataStore = context.dataStore,
        dispatchers = dispatchers
    )


    @Singleton
    @Provides
    fun provideUserDataStore(
        persistenceClient: PersistenceClient
    ): UserLocalDataSource = UserDataStore(
        persistenceClient = persistenceClient
    )


}

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = "linkedincloneprefs",
)
