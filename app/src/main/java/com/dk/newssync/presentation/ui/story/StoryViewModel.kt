package com.dk.newssync.presentation.ui.story

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dk.newssync.data.Result
import com.dk.newssync.data.entity.Story
import com.dk.newssync.data.usecase.SearchUseCase
import com.dk.newssync.presentation.common.*
import com.dk.newssync.presentation.ui.base.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Dimitris Konomis (konomis.dimitris@gmail.com) on 03/01/2019.
 **/

class StoryViewModel @Inject constructor(private val searchUseCase: SearchUseCase) : BaseViewModel() {

    private val _favoriteAction: SingleLiveEvent<State<Boolean>> = SingleLiveEvent()
    private val _story: MutableLiveData<Story> = MutableLiveData()

    val favoriteAction: LiveData<State<Boolean>>
        get() = _favoriteAction

    val story: LiveData<Story>
        get() = _story

    fun getStory(id: Long = 0) = viewModelScope.launch {
        _story.postValue((searchUseCase.getStory(id) as Result.Success).data)
    }

    fun toggleFavorite(story: Story) = viewModelScope.launch {
        when(val updated = searchUseCase.toggleFavorite(story)) {
            is Result.Success -> _favoriteAction.postValue(State.success(!story.favorite))
            is Result.Error -> _favoriteAction.postValue(State.error(updated.exception.message ?: "Unknown Error", updated.exception))
        }
    }

}