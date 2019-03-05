package com.dk.newssync.presentation.ui.entries

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.dk.newssync.data.entity.Entry
import com.dk.newssync.data.usecase.EntriesUseCase
import com.dk.newssync.presentation.common.State
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Completable
import io.reactivex.Flowable
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by Dimitris Konomis (konomis.dimitris@gmail.com) on 05/03/2019.
 */

@RunWith(MockitoJUnitRunner::class)
class EntriesViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Mock
    lateinit var entriesUseCase: EntriesUseCase

    private lateinit var entriesViewModel: EntriesViewModel

    @Before
    fun setup() {
        entriesViewModel = EntriesViewModel(entriesUseCase)
    }

    @Test
    fun testNameValidationValidInput() {
        val input = "ABC"
        val validation = true
        val observer: Observer<Boolean> = mock()

        entriesViewModel.nameValidation.observeForever(observer)
        entriesViewModel.validate(input)

        verify(observer).onChanged(validation)
    }

    @Test
    fun testSelectedConfirmed() {
        val selected = true
        val id = 1L
        val entry = Entry(id = id)
        val observer: Observer<Boolean> = mock()

        whenever(entriesUseCase.setSelected(id)).doReturn(Completable.complete())

        entriesViewModel.selectedConfirmed.observeForever(observer)
        entriesViewModel.setSelected(entry)

        verify(entriesUseCase).setSelected(id)
        verify(observer).onChanged(selected)
    }

    @Test
    fun testNewEntryThenStateSuccess() {
        val name = "ABC"
        val state = true
        val observer: Observer<State<Boolean>> = mock()

        whenever(entriesUseCase.insertEntry(name)).doReturn(Completable.complete())

        entriesViewModel.newEntryState.observeForever(observer)
        entriesViewModel.submit(name)

        verify(entriesUseCase).insertEntry(name)
        verify(observer).onChanged(State.success(state))
    }

    @Test
    fun testNewEntryThenStateError() {
        val name = "ABC"
        val error = Throwable("Unknown Error")
        val observer: Observer<State<Boolean>> = mock()

        whenever(entriesUseCase.insertEntry(name)).doReturn(Completable.error(error))

        entriesViewModel.newEntryState.observeForever(observer)
        entriesViewModel.submit(name)

        verify(entriesUseCase).insertEntry(name)
        verify(observer).onChanged(State.error(error.localizedMessage, error))
    }

    @Test
    fun testGetEntriesThenStateSuccess() {
        val entry = Entry()
        val response = listOf(entry)
        val observer: Observer<State<List<Entry>>> = mock()

        whenever(entriesUseCase.getEntries()).doReturn(Flowable.just(response))

        entriesViewModel.entries.observeForever(observer)

        verify(entriesUseCase).getEntries()
        verify(observer).onChanged(State.success(response))
    }

    @Test
    fun testGetEntriesThenStateError() {
        val error = Throwable("Unknown Error")
        val observer: Observer<State<List<Entry>>> = mock()

        whenever(entriesUseCase.getEntries()).doReturn(Flowable.error(error))

        entriesViewModel.entries.observeForever(observer)

        verify(entriesUseCase).getEntries()
        verify(observer).onChanged(State.error(error.localizedMessage, error))
    }

}