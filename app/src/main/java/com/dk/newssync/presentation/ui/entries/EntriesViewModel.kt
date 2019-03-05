package com.dk.newssync.presentation.ui.entries

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dk.newssync.data.entity.Entry
import com.dk.newssync.data.usecase.EntriesUseCase
import com.dk.newssync.presentation.ui.base.BaseViewModel
import com.dk.newssync.presentation.common.State
import com.dk.newssync.presentation.common.SingleLiveEvent
import com.dk.newssync.presentation.common.defaultErrorHandler
import com.dk.newssync.presentation.common.toLiveData
import com.dk.newssync.presentation.common.toState
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

/**
 * Created by Dimitris Konomis (konomis.dimitris@gmail.com) on 08/12/2018.
 **/

class EntriesViewModel @Inject constructor(private val entriesUseCase: EntriesUseCase) : BaseViewModel() {

    val nameValidation: SingleLiveEvent<Boolean> =
        SingleLiveEvent()

    val selectedConfirmed: SingleLiveEvent<Boolean> =
        SingleLiveEvent()

    val newEntryState: MutableLiveData<State<Boolean>> by lazy {
        MutableLiveData<State<Boolean>>()
    }

    val entries: LiveData<State<List<Entry>>> by lazy {
        entriesUseCase.getEntries()
            .toState()
            .toLiveData()
    }

    fun validate(input: String?) {
        nameValidation.postValue(input?.isNotBlank())
    }

    fun submit(name: String?) {
        entriesUseCase.insertEntry(name)
            .doOnSubscribe { newEntryState.postValue(State.loading()) }
            .doOnError { e -> newEntryState.postValue(State.error(e.message ?: "Unknown Error", e)) }
            .subscribeBy(onComplete = { newEntryState.postValue(State.success(true)) }, onError = defaultErrorHandler())
            .addTo(compositeDisposable)
    }

    fun setSelected(entry: Entry?) {
        entriesUseCase.setSelected(entry?.id ?: 0)
            .doOnError { selectedConfirmed.postValue(false) }
            .subscribeBy(onComplete = { selectedConfirmed.postValue(true) }, onError = defaultErrorHandler())
            .addTo(compositeDisposable)
    }

}