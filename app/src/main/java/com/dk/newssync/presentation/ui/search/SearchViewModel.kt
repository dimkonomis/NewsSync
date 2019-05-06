package com.dk.newssync.presentation.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dk.newssync.data.entity.Story
import com.dk.newssync.data.usecase.SearchUseCase
import com.dk.newssync.presentation.common.*
import com.dk.newssync.presentation.ui.base.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Dimitris Konomis (konomis.dimitris@gmail.com) on 13/11/2018.
 **/

class SearchViewModel @Inject constructor(private val searchUseCase: SearchUseCase) : BaseViewModel() {

    var q: String? = null

    private val _stories: MutableLiveData<State<List<Story>>> = MutableLiveData()
    private val _favorites: MutableLiveData<State<List<Story>>> = MutableLiveData()

    val stories: LiveData<State<List<Story>>>
            get() = _stories

    val favorites: LiveData<State<List<Story>>>
        get() = _favorites

    fun getStories() = viewModelScope.launch {
        _stories.postValue(State.loading())
        _stories.postValue(searchUseCase.searchStories(q).toState())
    }

    fun getFavorites() = viewModelScope.launch  {
        _favorites.postValue(State.loading())
        _favorites.postValue(searchUseCase.getFavorites().toState())
    }

    fun toggleFavorite(story: Story) = viewModelScope.launch {
        searchUseCase.toggleFavorite(story)
        _stories.postValue(searchUseCase.searchStories(q).toState())
    }

}