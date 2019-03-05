package com.dk.newssync.di.builder

import com.dk.newssync.di.module.SearchModule
import com.dk.newssync.presentation.ui.search.SearchFavoritesFragment
import com.dk.newssync.presentation.ui.search.SearchFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by Dimitris Konomis (konomis.dimitris@gmail.com) on 13/11/2018.
 **/

@Module
interface SearchFragmentBuilder {
    @ContributesAndroidInjector(modules = [SearchModule::class])
    fun contributeSearchFragment(): SearchFragment
    @ContributesAndroidInjector(modules = [SearchModule::class])
    fun contributeSearchFavoritesFragment(): SearchFavoritesFragment
}