package com.dk.newssync.data.source.local

import com.dk.newssync.data.entity.Entry
import com.dk.newssync.data.entity.Story
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

/**
 * Created by Dimitris Konomis (konomis.dimitris@gmail.com) on 13/11/2018.
 **/

interface LocalSource {

    // STORIES

    fun getStories(entryId: Long?): Flowable<List<Story>>

    fun getStory(id: Long?): Flowable<Story>

    fun insertStories(stories: List<Story>): Completable

    fun getFavorites(): Flowable<List<Story>>

    fun toggleFavorite(id: Long, favorite: Boolean): Single<Int>

    // ENTRIES

    fun getEntries(): Single<List<Entry>>

    fun getEntry(id: Long): Single<Entry>

    fun insertEntry(entry: Entry): Single<Long>

    // PREFS

    val SELECTEDKEY : String get() = "selectedKey"

    fun setSelected(id: Long): Completable

    fun getSelected(): Long

}