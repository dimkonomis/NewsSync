package com.dk.newssync.data.usecase

import com.dk.newssync.data.Result
import com.dk.newssync.data.entity.Entry
import com.dk.newssync.data.executor.BaseSchedulerProvider
import com.dk.newssync.data.repository.EntriesRepository
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Dimitris Konomis (konomis.dimitris@gmail.com) on 08/12/2018.
 **/

@Singleton
class EntriesUseCase @Inject constructor(
    private val entriesRepository: EntriesRepository,
    private val schedulerProvider: BaseSchedulerProvider
) {

    suspend fun getEntries(): Result<List<Entry>> = withContext(schedulerProvider.ui) {
        val selected = entriesRepository.getSelected()
        val entries = entriesRepository.getEntries()
        if (entries is Result.Success && entries.data.size > 1) {
            return@withContext entries.copy(
                entries.data.toMutableList().also {
                    val position = it.indexOfFirst { entry -> entry.id == selected }
                    val item = it[position]
                    it.removeAt(position)
                    it.add(0, item)
                }
            )
        }
        return@withContext entries
    }

    suspend fun insertEntry(name: String?): Result<Long> = withContext(schedulerProvider.ui) {
        val result = entriesRepository.insertEntry(name)
        if(result is Result.Success) entriesRepository.setSelected(result.data)
        return@withContext result
    }

    suspend fun getSelected(): Result<Entry>  = withContext(schedulerProvider.ui) {
        val id = entriesRepository.getSelected()
        val selected = entriesRepository.getEntry(id)
        return@withContext entriesRepository.getEntry(id)
    }

    fun setSelected(id: Long) {
        entriesRepository.setSelected(id)
    }

}