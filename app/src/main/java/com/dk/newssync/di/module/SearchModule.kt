package com.dk.newssync.di.module

import androidx.lifecycle.ViewModel
import com.dk.newssync.di.ViewModelKey
import com.dk.newssync.presentation.ui.search.SearchViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by Dimitris Konomis (konomis.dimitris@gmail.com) on 13/11/2018.
 **/

@Module
interface SearchModule {

    @Binds @IntoMap
    @ViewModelKey(SearchViewModel::class)
    fun bindSearchViewModel(searchViewModel: SearchViewModel): ViewModel
}