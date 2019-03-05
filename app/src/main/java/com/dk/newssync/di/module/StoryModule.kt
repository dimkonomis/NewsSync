package com.dk.newssync.di.module

import androidx.lifecycle.ViewModel
import com.dk.newssync.di.ViewModelKey
import com.dk.newssync.presentation.ui.story.StoryViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by Dimitris Konomis (konomis.dimitris@gmail.com) on 07/12/2018.
 **/

@Module
interface StoryModule {

    @Binds
    @IntoMap
    @ViewModelKey(StoryViewModel::class)
    fun bindStoryViewModel(storyViewModel: StoryViewModel): ViewModel

}