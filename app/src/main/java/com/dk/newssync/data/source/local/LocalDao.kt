package com.dk.newssync.data.source.local

import androidx.room.*
import com.dk.newssync.data.entity.Entry
import com.dk.newssync.data.entity.Story
import io.reactivex.Flowable
import io.reactivex.Single

/**
 * Created by Dimitris Konomis (konomis.dimitris@gmail.com) on 13/11/2018.
 **/

@Dao
interface LocalDao {

    @Query("SELECT * FROM stories WHERE entryId =:entryId ORDER BY timestamp DESC")
    fun getStories(entryId: Long): Flowable<List<Story>>

    @Query("SELECT * FROM stories WHERE id =:id")
    fun getStory(id: Long?): Flowable<Story>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertStories(stories: List<Story>)

    @Query("SELECT * FROM stories WHERE favorite = 1 ORDER BY timestamp DESC")
    fun getFavorites(): Flowable<List<Story>>

    @Query("UPDATE stories SET favorite = :favorite WHERE id = :id")
    fun toggleFavorite(id: Long, favorite: Int): Int

    @Query("SELECT * FROM entries")
    fun getEntries(): Single<List<Entry>>

    @Query("SELECT * FROM entries WHERE id =:id")
    fun getEntry(id: Long): Single<Entry>

    @Insert(onConflict = OnConflictStrategy.FAIL)
    fun insertEntry(entry: Entry): Long

}