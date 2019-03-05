package com.dk.newssync.di.builder

import com.dk.newssync.di.module.StoryModule
import com.dk.newssync.presentation.ui.story.StoryFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created by Dimitris Konomis (konomis.dimitris@gmail.com) on 07/12/2018.
 **/

@Module interface StoryFragmentBuilder {
    @ContributesAndroidInjector(modules = [StoryModule::class])
    fun contributeStoryFragment(): StoryFragment
}