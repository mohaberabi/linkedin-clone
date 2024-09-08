package com.mohaberabi.linkedinclone.core.database.di

import android.content.Context
import androidx.room.Room
import com.mohaberabi.linkedin.core.domain.source.local.posts.PostsLocalDataSource
import com.mohaberabi.linkedin.core.domain.util.DispatchersProvider
import com.mohaberabi.linkedinclone.core.database.dao.PostsDao
import com.mohaberabi.linkedinclone.core.database.database.LinkedInCloneDb
import com.mohaberabi.linkedinclone.core.database.source.RoomPostsLocalDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    private const val DB_NAME = "linkedinclone.db"

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
    ): LinkedInCloneDb {
        return Room.databaseBuilder(
            context,
            LinkedInCloneDb::class.java,
            DB_NAME
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun providePostsDao(
        database: LinkedInCloneDb,
    ): PostsDao = database.postsDao()


    @Provides
    @Singleton
    fun providePostsLocalDataSource(
        dao: PostsDao,
        dispatchers: DispatchersProvider,
    ): PostsLocalDataSource = RoomPostsLocalDataSource(
        dispatchers = dispatchers,
        postsDao = dao
    )

}
