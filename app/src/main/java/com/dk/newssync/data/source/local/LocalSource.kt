package com.dk.newssync.data.source.local

import com.dk.newssync.data.entity.Entry
import com.dk.newssync.data.entity.Story

/**
 * Created by Dimitris Konomis (konomis.dimitris@gmail.com) on 13/11/2018.
 **/

interface LocalSource {

    // STORIES

    suspend fun getStories(entryId: Long?): List<Story>

    suspend fun getStory(id: Long?): Story

    suspend fun insertStories(stories: List<Story>)

    suspend fun getFavorites(): List<Story>

    suspend fun toggleFavorite(id: Long, favorite: Boolean): Int

    // ENTRIES

    suspend fun getEntries(): List<Entry>

    suspend fun getEntry(id: Long): Entry

    suspend fun insertEntry(entry: Entry): Long

    // PREFS

    val SELECTEDKEY : String get() = "selectedKey"

    fun setSelected(id: Long)

    fun getSelected(): Long

}