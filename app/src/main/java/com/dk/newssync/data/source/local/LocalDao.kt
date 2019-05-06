package com.dk.newssync.data.source.local

import androidx.room.*
import com.dk.newssync.data.entity.Entry
import com.dk.newssync.data.entity.Story

/**
 * Created by Dimitris Konomis (konomis.dimitris@gmail.com) on 13/11/2018.
 **/

@Dao
interface LocalDao {

    @Query("SELECT * FROM stories WHERE entryId =:entryId ORDER BY timestamp DESC")
    suspend fun getStories(entryId: Long): List<Story>

    @Query("SELECT * FROM stories WHERE id =:id")
    suspend fun getStory(id: Long?): Story

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertStories(stories: List<Story>)

    @Query("SELECT * FROM stories WHERE favorite = 1 ORDER BY timestamp DESC")
    suspend fun getFavorites(): List<Story>

    @Query("UPDATE stories SET favorite = :favorite WHERE id = :id")
    suspend fun toggleFavorite(id: Long, favorite: Int): Int

    @Query("SELECT * FROM entries")
    suspend fun getEntries(): List<Entry>

    @Query("SELECT * FROM entries WHERE id =:id")
    suspend fun getEntry(id: Long): Entry

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertEntry(entry: Entry): Long

}