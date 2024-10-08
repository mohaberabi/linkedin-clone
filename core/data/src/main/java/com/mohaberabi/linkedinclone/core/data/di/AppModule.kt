package com.mohaberabi.linkedinclone.core.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.mohaberabi.linkedin.core.domain.source.local.persistence.PersistenceClient
import com.mohaberabi.linkedin.core.domain.util.AppBottomSheetShower
import com.mohaberabi.linkedin.core.domain.util.AppSuperVisorScope
import com.mohaberabi.linkedin.core.domain.util.DispatchersProvider
import com.mohaberabi.linkedinclone.core.data.source.local.persistence.DataStorePersistenceClient
import com.mohaberabi.linkedinclone.core.data.source.remote.FirebaseStorageClient
import com.mohaberabi.linkedinclone.core.data.util.DefaultAppSheetShower
import com.mohaberabi.linkedinclone.core.data.util.DefaultDispatchersProvider
import com.mohaberabi.linkedinclone.core.data.util.GlobalDrawerController
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)


object AppModule {
    @Singleton
    @Provides
    fun provideDisaptcherProvider(
    ): DispatchersProvider = DefaultDispatchersProvider()


    @Singleton
    @Provides
    fun provideAppSuperVisorScope(
        dispatchers: DispatchersProvider
    ): AppSuperVisorScope = object : AppSuperVisorScope {
        override fun invoke(): CoroutineScope {
            return CoroutineScope(SupervisorJob() + dispatchers.main)
        }
    }


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
    fun provideFirebaseStorage() = FirebaseStorage.getInstance()

    @Singleton
    @Provides
    fun provideFirebaseAuth() = FirebaseAuth.getInstance()

    @Singleton
    @Provides
    fun provideFirebase() = FirebaseFirestore.getInstance()


    @Singleton
    @Provides
    fun provideStorageClient(
        dispatchers: DispatchersProvider,
        storage: FirebaseStorage
    ) = FirebaseStorageClient(
        dispatchers = dispatchers,
        storage = storage
    )
}

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = "linkedincloneprefs",
)