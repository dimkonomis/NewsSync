package com.dk.newssync.data.repository

import com.dk.newssync.data.entity.Entry
import io.reactivex.Completable
import io.reactivex.Single

/**
 * Created by Dimitris Konomis (konomis.dimitris@gmail.com) on 08/12/2018.
 **/

interface EntriesRepository {

    fun getEntries(): Single<List<Entry>>

    fun getEntry(id: Long): Single<Entry>

    fun insertEntry(name: String?): Single<Long>

    fun setSelected(id: Long): Completable

    fun getSelected(): Single<Long>

}