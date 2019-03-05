package com.dk.newssync.di.builder

import com.dk.newssync.presentation.sync.SyncService
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by Dimitris Konomis (konomis.dimitris@gmail.com) on 27/02/2019.
 **/

@Module
interface SyncServiceBuilder {
    @ContributesAndroidInjector()
    fun contributeSyncService(): SyncService
}