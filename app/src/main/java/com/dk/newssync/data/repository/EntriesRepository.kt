package com.dk.newssync.data.repository

import com.dk.newssync.data.Result
import com.dk.newssync.data.entity.Entry

/**
 * Created by Dimitris Konomis (konomis.dimitris@gmail.com) on 08/12/2018.
 **/

interface EntriesRepository: Repository {

    suspend fun getEntries(): Result<List<Entry>>

    suspend fun getEntry(id: Long): Result<Entry>

    suspend fun insertEntry(name: String?): Result<Long>

    suspend fun getSelected(): Long

    fun setSelected(id: Long)

}