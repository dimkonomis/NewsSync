package com.dk.newssync.data.repository

import com.dk.newssync.data.entity.Entry
import com.dk.newssync.data.source.local.LocalSource
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Dimitris Konomis (konomis.dimitris@gmail.com) on 08/12/2018.
 **/

@Singleton
class EntriesRepositoryImpl @Inject constructor(private val localSource: LocalSource) : EntriesRepository {

    override fun getEntries(): Single<List<Entry>> {
        return localSource.getEntries()
    }

    override fun getEntry(id: Long): Single<Entry> {
        return localSource.getEntry(id)
    }

    override fun insertEntry(name: String?): Single<Long> {
        return localSource.insertEntry(Entry(name = name))
    }

    override fun setSelected(id: Long): Completable {
        return localSource.setSelected(id)
    }

    override fun getSelected(): Single<Long> {
        return Single.fromCallable { localSource.getSelected() }
    }
}