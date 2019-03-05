package com.dk.newssync.di.builder

import com.dk.newssync.di.module.MainModule
import com.dk.newssync.presentation.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by Dimitris Konomis (konomis.dimitris@gmail.com) on 13/11/2018.
 **/

@Module interface MainActivityBuilder {
    @ContributesAndroidInjector(modules = [MainModule::class])
    fun contributeMainActivity(): MainActivity
}