package com.dk.newssync.presentation.ui.story

import androidx.lifecycle.LiveData
import com.dk.newssync.data.entity.Story
import com.dk.newssync.data.usecase.SearchUseCase
import com.dk.newssync.presentation.ui.base.BaseViewModel
import com.dk.newssync.presentation.common.State
import com.dk.newssync.presentation.common.SingleLiveEvent
import com.dk.newssync.presentation.common.defaultErrorHandler
import com.dk.newssync.presentation.common.toLiveData
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

/**
 * Created by Dimitris Konomis (konomis.dimitris@gmail.com) on 03/01/2019.
 **/

class StoryViewModel @Inject constructor(private val searchUseCase: SearchUseCase): BaseViewModel() {

    var id: Long = 0

    val story: LiveData<Story> by lazy {
        searchUseCase.getStory(id)
            .toLiveData()
    }

    val favoriteAction: SingleLiveEvent<State<Boolean>> =
        SingleLiveEvent()

    fun toggleFavorite(story: Story) {
        searchUseCase.toggleFavorite(story)
            .doOnError { e -> favoriteAction.postValue(State.error(e.message ?: "Unknown Error", e)) }
            .subscribeBy(onComplete = { favoriteAction.postValue(State.success(!story.favorite)) }, onError = defaultErrorHandler())
            .addTo(compositeDisposable)
    }

}