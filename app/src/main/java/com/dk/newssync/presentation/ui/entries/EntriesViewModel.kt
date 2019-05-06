package com.dk.newssync.presentation.ui.entries

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dk.newssync.data.Result
import com.dk.newssync.data.entity.Entry
import com.dk.newssync.data.usecase.EntriesUseCase
import com.dk.newssync.presentation.ui.base.BaseViewModel
import com.dk.newssync.presentation.common.State
import com.dk.newssync.presentation.common.SingleLiveEvent
import com.dk.newssync.presentation.common.toState
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by Dimitris Konomis (konomis.dimitris@gmail.com) on 08/12/2018.
 **/

class EntriesViewModel @Inject constructor(private val entriesUseCase: EntriesUseCase) : BaseViewModel() {

    private val _nameValidation: SingleLiveEvent<Boolean> = SingleLiveEvent()
    private val _selectedConfirmed: SingleLiveEvent<Boolean> = SingleLiveEvent()
    private val _entries: MutableLiveData<State<List<Entry>>> = MutableLiveData()

    val nameValidation: LiveData<Boolean>
        get() = _nameValidation

    val selectedConfirmed: LiveData<Boolean>
        get() = _selectedConfirmed

    val newEntryState: MutableLiveData<State<Boolean>> by lazy {
        MutableLiveData<State<Boolean>>()
    }

    val entries: LiveData<State<List<Entry>>>
        get() = _entries

    fun getEntries() = viewModelScope.launch  {
        _entries.postValue(State.loading())
        _entries.postValue(entriesUseCase.getEntries().toState())
    }

    fun validate(input: String?) {
        _nameValidation.postValue(input?.isNotBlank())
    }

    fun submit(name: String?) = viewModelScope.launch {
        newEntryState.postValue(State.loading())
        when(val inserted = entriesUseCase.insertEntry(name)) {
            is Result.Success -> newEntryState.postValue(State.success(true))
            is Result.Error -> newEntryState.postValue(State.error(inserted.exception.message ?: "Unknown Error", inserted.exception))
        }
    }

    fun setSelected(entry: Entry?) {
        entriesUseCase.setSelected(entry?.id ?: 0)
        _selectedConfirmed.postValue(true)
    }

}