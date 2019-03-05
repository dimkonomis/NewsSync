package com.dk.newssync.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dk.newssync.data.entity.Entry
import com.dk.newssync.data.entity.Story

/**
 * Created by Dimitris Konomis (konomis.dimitris@gmail.com) on 13/11/2018.
 **/

@Database(entities = [Story::class, Entry::class], version = 1, exportSchema = true)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun localDao(): LocalDao

}