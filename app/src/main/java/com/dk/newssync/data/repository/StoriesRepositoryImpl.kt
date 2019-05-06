package com.dk.newssync.data.repository

import com.dk.newssync.data.Result
import com.dk.newssync.data.entity.Story
import com.dk.newssync.data.mapper.toStories
import com.dk.newssync.data.source.local.LocalSource
import com.dk.newssync.data.source.network.NetworkSource
import javax.inject.Inject
import javax.inject.Singleton
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Dimitris Konomis (konomis.dimitris@gmail.com) on 13/11/2018.
 **/

@Singleton
class StoriesRepositoryImpl @Inject constructor(private val networkSource: NetworkSource,
                                                private val localSource: LocalSource): StoriesRepository {

    private val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())

    override suspend fun searchStories(q: String?): Result<List<Story>> {
        val entryId = localSource.getSelected()

        val localStories = request { localSource.getStories(entryId) }
        return when {
            localStories is Result.Success && localStories.data.isNotEmpty() -> localStories
            else -> {
                appendStories(q, entryId)
                return request { localSource.getStories(entryId) }
            }
        }
    }

    override suspend fun appendStories(q: String?, entryId: Long): Result<List<Story>> {
        val response = requestAwait { networkSource.searchArticles(q, fromTime()) }
        return when(response) {
            is Result.Success -> {
                with (response.data.articles?.toStories(q, entryId) ?: listOf()) {
                    request { localSource.insertStories(this) }
                    return Result.success(this)
                }
            }
            is Result.Error -> response
        }
    }

    override suspend fun getStory(id: Long?): Result<Story> {
        return request { localSource.getStory(id) }
    }

    override suspend fun getFavorites(): Result<List<Story>> {
        return request { localSource.getFavorites() }
    }

    override suspend fun toggleFavorite(id: Long, favorite: Boolean): Result<Int> {
        return request { localSource.toggleFavorite(id, favorite) }
    }

    private fun fromTime(): String {
        return format.format(Calendar.getInstance().apply {
            add(Calendar.HOUR_OF_DAY, -6)
        }.time)
    }

}