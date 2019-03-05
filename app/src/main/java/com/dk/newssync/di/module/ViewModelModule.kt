package com.dk.newssync.di.module

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module

/**
 * Created by Dimitris Konomis (konomis.dimitris@gmail.com) on 13/11/2018.
 **/

@Module
interface ViewModelModule {

    @Binds
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}