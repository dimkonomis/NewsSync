package com.dk.newssync.data.usecase

import com.dk.newssync.data.Result
import com.dk.newssync.data.executor.BaseSchedulerProvider
import com.dk.newssync.data.repository.EntriesRepository
import com.dk.newssync.data.repository.StoriesRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Dimitris Konomis (konomis.dimitris@gmail.com) on 27/02/2019.
 **/

@Singleton
class SyncUseCase @Inject constructor(
    private val storiesRepository: StoriesRepository,
    private val entriesRepository: EntriesRepository,
    private val schedulerProvider: BaseSchedulerProvider
) {

    suspend fun syncStories() = withContext(schedulerProvider.io) {
        val entries = entriesRepository.getEntries()
        when(entries) {
            is Result.Success -> {
                entries.data.forEach { entry -> storiesRepository.appendStories(entry.name, entry.id) }
            }
        }
    }

}