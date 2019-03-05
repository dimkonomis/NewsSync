package com.dk.newssync.presentation.ui.search

import androidx.lifecycle.LiveData
import com.dk.newssync.data.entity.Story
import com.dk.newssync.data.usecase.SearchUseCase
import com.dk.newssync.presentation.ui.base.BaseViewModel
import com.dk.newssync.presentation.common.State
import com.dk.newssync.presentation.common.defaultErrorHandler
import com.dk.newssync.presentation.common.toLiveData
import com.dk.newssync.presentation.common.toState
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

/**
 * Created by Dimitris Konomis (konomis.dimitris@gmail.com) on 13/11/2018.
 **/

class SearchViewModel @Inject constructor(private val searchUseCase: SearchUseCase): BaseViewModel() {

    var q: String? = null

    val stories: LiveData<State<List<Story>>> by lazy {
        searchUseCase.searchStories(q)
            .toState()
            .toLiveData()
    }

    val favorites: LiveData<State<List<Story>>> by lazy {
        searchUseCase.getFavorites()
            .toState()
            .toLiveData()
    }

    fun toggleFavorite(story: Story) {
        searchUseCase.toggleFavorite(story)
            .subscribeBy(onError = defaultErrorHandler())
            .addTo(compositeDisposable)
    }

}