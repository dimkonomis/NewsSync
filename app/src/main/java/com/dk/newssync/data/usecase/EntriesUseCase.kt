package com.dk.newssync.data.usecase

import com.dk.newssync.data.entity.Entry
import com.dk.newssync.data.executor.BaseSchedulerProvider
import com.dk.newssync.data.repository.EntriesRepository
import io.reactivex.*
import io.reactivex.functions.BiFunction
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Dimitris Konomis (konomis.dimitris@gmail.com) on 08/12/2018.
 **/

@Singleton
class EntriesUseCase @Inject constructor(private val entriesRepository: EntriesRepository,
                                         private val schedulerProvider: BaseSchedulerProvider
) {

    fun getEntries(): Flowable<List<Entry>> {
        return Single.zip(entriesRepository.getEntries(), entriesRepository.getSelected(),
            BiFunction<List<Entry>, Long, List<Entry>> { entries, selected ->
                val position = entries.indexOfFirst { entry -> entry.id == selected }
                entries.toMutableList().apply {
                    removeAt(position)
                    add(0, entries[position])
                }
            })
            .toFlowable()
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
    }

    fun insertEntry(name: String?): Completable {
        return entriesRepository.insertEntry(name)
            .flatMapCompletable { id -> entriesRepository.setSelected(id) }
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
    }

    fun setSelected(id: Long): Completable {
        return entriesRepository.setSelected(id)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
    }

    fun getSelected(): Flowable<Entry> {
        return entriesRepository.getSelected()
            .flatMap { id -> entriesRepository.getEntry(id) }
            .toFlowable()
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
    }

}