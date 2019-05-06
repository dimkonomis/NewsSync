package com.dk.newssync.data.repository

import com.dk.newssync.data.Result
import com.dk.newssync.data.entity.Story

/**
 * Created by Dimitris Konomis (konomis.dimitris@gmail.com) on 13/11/2018.
 **/

interface StoriesRepository: Repository {

    suspend fun searchStories(q: String?): Result<List<Story>>

    suspend fun appendStories(q: String?, entryId: Long): Result<List<Story>>

    suspend fun getStory(id: Long?): Result<Story>

    suspend fun getFavorites(): Result<List<Story>>

    suspend fun toggleFavorite(id: Long, favorite: Boolean): Result<Int>

}