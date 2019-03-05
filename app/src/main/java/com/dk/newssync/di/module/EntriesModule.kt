package com.dk.newssync.di.module

import androidx.lifecycle.ViewModel
import com.dk.newssync.di.ViewModelKey
import com.dk.newssync.presentation.ui.entries.EntriesViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by Dimitris Konomis (konomis.dimitris@gmail.com) on 08/12/2018.
 **/

@Module
interface EntriesModule {

    @Binds
    @IntoMap
    @ViewModelKey(EntriesViewModel::class)
    fun bindEntriesViewModel(entriesViewModel: EntriesViewModel): ViewModel
}