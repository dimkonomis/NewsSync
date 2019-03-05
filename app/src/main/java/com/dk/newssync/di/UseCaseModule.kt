package com.dk.newssync.di

import com.dk.newssync.data.executor.BaseSchedulerProvider
import com.dk.newssync.data.executor.SchedulerProvider
import com.dk.newssync.data.repository.StoriesRepository
import com.dk.newssync.data.repository.EntriesRepository
import com.dk.newssync.data.usecase.EntriesUseCase
import com.dk.newssync.data.usecase.SearchUseCase
import com.dk.newssync.data.usecase.SyncUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Dimitris Konomis (konomis.dimitris@gmail.com) on 13/11/2018.
 **/

@Module
class UseCaseModule {

    @Singleton
    @Provides
    fun provideSchedulerProvider(): BaseSchedulerProvider {
        return SchedulerProvider()
    }

    @Singleton
    @Provides
    fun provideSearchUseCase(storiesRepository: StoriesRepository, schedulerProvider: BaseSchedulerProvider): SearchUseCase {
        return SearchUseCase(storiesRepository, schedulerProvider)
    }

    @Singleton
    @Provides
    fun provideEntriesUseCase(entriesRepository: EntriesRepository, schedulerProvider: BaseSchedulerProvider): EntriesUseCase {
        return EntriesUseCase(entriesRepository, schedulerProvider)
    }

    @Singleton
    @Provides
    fun provideSyncUseCase(storiesRepository: StoriesRepository,
                           entriesRepository: EntriesRepository, schedulerProvider: BaseSchedulerProvider): SyncUseCase {
        return SyncUseCase(storiesRepository, entriesRepository, schedulerProvider)
    }

}