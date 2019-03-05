package com.dk.newssync.di

import com.dk.newssync.data.repository.StoriesRepository
import com.dk.newssync.data.repository.StoriesRepositoryImpl
import com.dk.newssync.data.repository.EntriesRepository
import com.dk.newssync.data.repository.EntriesRepositoryImpl
import com.dk.newssync.data.source.local.LocalSource
import com.dk.newssync.data.source.network.NetworkSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Dimitris Konomis (konomis.dimitris@gmail.com) on 13/11/2018.
 **/

@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun provideArticlesRepository(networkSource: NetworkSource, localSource: LocalSource): StoriesRepository {
        return StoriesRepositoryImpl(networkSource, localSource)
    }

    @Singleton
    @Provides
    fun provideEntriesRepository(localSource: LocalSource): EntriesRepository {
        return EntriesRepositoryImpl(localSource)
    }

}