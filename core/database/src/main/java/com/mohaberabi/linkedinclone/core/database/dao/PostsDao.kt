package com.mohaberabi.linkedinclone.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mohaberabi.linkedinclone.core.database.entity.PostEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface PostsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertPost(post: PostEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertPosts(posts: List<PostEntity>)

    @Query("SELECT * FROM posts ORDER BY createdAtMillis DESC")
    fun getPosts(): Flow<List<PostEntity>>

  
    @Query(
        """
    UPDATE posts 
    SET 
        currentUserReaction = :reaction, 
        reactionsCount = CASE 
            WHEN currentUserReaction IS NULL THEN reactionsCount + 1 
            ELSE reactionsCount 
        END
    WHERE 
        id = :postId 
        AND (
            currentUserReaction IS NULL 
            OR currentUserReaction != :reaction
        )
    """
    )
    suspend fun reactToPost(postId: String, reaction: String)

    @Query(
        """
    UPDATE posts 
    SET 
        currentUserReaction = NULL, 
        reactionsCount = reactionsCount - 1 
    WHERE 
        id = :postId
    """
    )
    suspend fun undoReaction(postId: String)


    @Query("UPDATE posts SET isSaved = 1 WHERE id = :id")
    suspend fun savePost(id: String)

    @Query("SELECT * FROM posts WHERE isSaved = 1")
    fun getAllSavedPosts(): Flow<List<PostEntity>>

    @Query("DELETE FROM posts WHERE id IN (:ids)")
    suspend fun deleteSavedPostsWhereIn(ids: Set<String>)
}