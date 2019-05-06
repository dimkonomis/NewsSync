package com.dk.newssync.data.source.local

import com.dk.newssync.data.entity.Entry
import com.dk.newssync.data.entity.Story
import javax.inject.Singleton

/**
 * Created by Dimitris Konomis (konomis.dimitris@gmail.com) on 13/11/2018.
 **/

@Singleton
class LocalSourceImpl(private val localDatabase: LocalDatabase, private val localSharedPrefs: LocalSharedPrefs) :
    LocalSource {

    override suspend fun getStories(entryId: Long?): List<Story> {
        return localDatabase
                .localDao()
                .getStories(entryId ?: 0)
    }

    override suspend fun getStory(id: Long?): Story {
        return localDatabase
                .localDao()
                .getStory(id)

    }

    override suspend fun insertStories(stories: List<Story>) {
        return localDatabase
                .localDao()
                .insertStories(stories)

    }

    override suspend fun getFavorites(): List<Story> {
        return localDatabase
                .localDao()
                .getFavorites()
    }

    override suspend fun toggleFavorite(id: Long, favorite: Boolean): Int {
        return localDatabase
                .localDao()
                .toggleFavorite(id, if (favorite) 1 else 0)
    }

    override suspend fun getEntries(): List<Entry> {
        return localDatabase
                .localDao()
                .getEntries()
    }

    override suspend fun getEntry(id: Long): Entry {
        return localDatabase
                .localDao()
                .getEntry(id)

    }

    override suspend fun insertEntry(entry: Entry): Long {
        return localDatabase
                .localDao()
                .insertEntry(entry)
    }

    override fun setSelected(id: Long) {
        localSharedPrefs.set(SELECTEDKEY, id)
    }

    override fun getSelected(): Long {
        return localSharedPrefs.get(SELECTEDKEY, 0) ?: 0
    }

}