package com.dk.newssync.presentation.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.dk.newssync.data.entity.Entry
import com.dk.newssync.data.usecase.EntriesUseCase
import com.dk.newssync.presentation.ui.base.BaseViewModel
import com.dk.newssync.presentation.common.State
import com.dk.newssync.presentation.common.SingleLiveEvent
import com.dk.newssync.presentation.common.toState
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Dimitris Konomis (konomis.dimitris@gmail.com) on 03/01/2019.
 **/

class MainViewModel @Inject constructor(private val entriesUseCase: EntriesUseCase) : BaseViewModel() {

    private val _selectedEntry: SingleLiveEvent<State<Entry?>> =
        SingleLiveEvent()

    val selectedEntry: LiveData<State<Entry?>>
        get() = _selectedEntry

    fun findSelected() = viewModelScope.launch {
        _selectedEntry.postValue(State.loading())
        _selectedEntry.postValue(entriesUseCase.getSelected().toState())
    }

}