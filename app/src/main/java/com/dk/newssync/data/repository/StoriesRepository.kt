package com.dk.newssync.data.repository

import com.dk.newssync.data.entity.Story
import io.reactivex.Flowable
import io.reactivex.Single

/**
 * Created by Dimitris Konomis (konomis.dimitris@gmail.com) on 13/11/2018.
 **/

interface StoriesRepository {

    fun searchStories(q: String?): Flowable<List<Story>>

    fun appendStories(q: String?, entryId: Long): Flowable<List<Story>>

    fun getStory(id: Long?): Flowable<Story>

    fun getFavorites(): Flowable<List<Story>>

    fun toggleFavorite(id: Long, favorite: Boolean): Single<Int>

}