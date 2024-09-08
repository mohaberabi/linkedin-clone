package com.mohaberabi.linkedinclone.core.database.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mohaberabi.linkedinclone.core.database.dao.PostsDao
import com.mohaberabi.linkedinclone.core.database.entity.PostEntity


@Database(
    entities = [
        PostEntity::class,
    ],
    version = 3,
)
abstract class LinkedInCloneDb : RoomDatabase() {
    abstract fun postsDao(): PostsDao

}