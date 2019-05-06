package com.dk.newssync.data.usecase

import com.dk.newssync.data.Result
import com.dk.newssync.data.entity.Story
import com.dk.newssync.data.executor.BaseSchedulerProvider
import com.dk.newssync.data.repository.StoriesRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Dimitris Konomis (konomis.dimitris@gmail.com) on 13/11/2018.
 **/

@Singleton
class SearchUseCase @Inject constructor(private val storiesRepository: StoriesRepository,
                                        private val schedulerProvider: BaseSchedulerProvider
) {

    suspend fun searchStories(q: String?): Result<List<Story>> = withContext(schedulerProvider.ui) {
        return@withContext storiesRepository.searchStories(q)
    }

    suspend fun getStory(id: Long?): Result<Story> = withContext(schedulerProvider.ui) {
        return@withContext storiesRepository.getStory(id)
    }

    suspend fun getFavorites(): Result<List<Story>> = withContext(schedulerProvider.ui) {
        return@withContext storiesRepository.getFavorites()
    }

    suspend fun toggleFavorite(story: Story): Result<Int> = withContext(schedulerProvider.computation) {
        return@withContext storiesRepository.toggleFavorite(story.id, !story.favorite)
    }

}