package com.dk.newssync.data.usecase

import com.dk.newssync.data.executor.BaseSchedulerProvider
import com.dk.newssync.data.repository.EntriesRepository
import com.dk.newssync.data.repository.StoriesRepository
import io.reactivex.Completable
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

    fun syncStories(): Completable {
        return entriesRepository.getEntries()
            .subscribeOn(schedulerProvider.io())
            .flattenAsObservable { entries -> entries }
            .flatMap { entry -> storiesRepository.appendStories(entry.name, entry.id).toObservable() }
            .toList()
            .ignoreElement()
            .observeOn(schedulerProvider.ui())
    }

}