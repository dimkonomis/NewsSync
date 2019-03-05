package com.dk.newssync.data.repository

import com.dk.newssync.data.entity.Story
import com.dk.newssync.data.mapper.toStories
import com.dk.newssync.data.source.local.LocalSource
import com.dk.newssync.data.source.network.NetworkSource
import io.reactivex.*
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

    override fun searchStories(q: String?): Flowable<List<Story>> {
        val entryId = localSource.getSelected()

        val localStories = localSource.getStories(entryId)
            .takeWhile { stories -> stories.isNotEmpty() }

        val networkStories = appendStories(q, entryId)
            .flatMap { localSource.getStories(entryId) }

        return Flowable
            .concat(localStories, networkStories)
    }

    override fun appendStories(q: String?, entryId: Long): Flowable<List<Story>> {
        return networkSource.searchArticles(q, fromTime())
            .toFlowable()
            .map { stories -> stories.articles?.toStories(q, entryId) ?: listOf() }
            .doOnNext { stories -> localSource.insertStories(stories).subscribe() }
    }

    override fun getStory(id: Long?): Flowable<Story> {
        return localSource.getStory(id)
    }

    override fun getFavorites(): Flowable<List<Story>> {
        return localSource.getFavorites()
    }

    override fun toggleFavorite(id: Long, favorite: Boolean): Single<Int> {
        return localSource.toggleFavorite(id, favorite)
    }

    private fun fromTime(): String {
        return format.format(Calendar.getInstance().apply {
            add(Calendar.HOUR_OF_DAY, -6)
        }.time)
    }

}