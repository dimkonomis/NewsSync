package com.dk.newssync.data.repository

import com.dk.newssync.data.Result
import com.dk.newssync.data.entity.Entry
import com.dk.newssync.data.source.local.LocalSource
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Dimitris Konomis (konomis.dimitris@gmail.com) on 08/12/2018.
 **/

@Singleton
class EntriesRepositoryImpl @Inject constructor(private val localSource: LocalSource) : EntriesRepository {

    override suspend fun getEntries(): Result<List<Entry>> {
        return request { localSource.getEntries() }
    }

    override suspend fun getEntry(id: Long): Result<Entry> {
        return request { localSource.getEntry(id) }
    }

    override suspend fun insertEntry(name: String?): Result<Long> {
        return request { localSource.insertEntry(Entry(name = name)) }
    }

    override suspend fun getSelected(): Long {
        return localSource.getSelected()
    }

    override fun setSelected(id: Long) {
        return localSource.setSelected(id)
    }
}