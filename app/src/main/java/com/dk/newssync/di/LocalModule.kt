package com.dk.newssync.di

import android.content.Context
import androidx.room.Room
import com.dk.newssync.R
import com.dk.newssync.data.source.local.LocalDatabase
import com.dk.newssync.data.source.local.LocalSharedPrefs
import com.dk.newssync.data.source.local.LocalSource
import com.dk.newssync.data.source.local.LocalSourceImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Dimitris Konomis (konomis.dimitris@gmail.com) on 13/11/2018.
 **/

@Module
class LocalModule {

    @Provides
    fun provideDatabase(context: Context): LocalDatabase {
        return Room.databaseBuilder(context, LocalDatabase::class.java, "${context.getString(R.string.app_name)}.db").build()
    }

    @Provides
    fun provideSharedPrefs(context: Context): LocalSharedPrefs {
        return LocalSharedPrefs(context)
    }

    @Singleton
    @Provides
    fun provideLocalSource(localDatabase: LocalDatabase, localSharedPrefs: LocalSharedPrefs): LocalSource {
        return LocalSourceImpl(localDatabase, localSharedPrefs)
    }

}