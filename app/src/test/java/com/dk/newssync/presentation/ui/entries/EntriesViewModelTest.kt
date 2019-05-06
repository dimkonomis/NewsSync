package com.dk.newssync.presentation.ui.entries

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.dk.newssync.data.Result
import com.dk.newssync.data.entity.Entry
import com.dk.newssync.data.usecase.EntriesUseCase
import com.dk.newssync.presentation.common.State
import com.dk.newssync.presentation.ui.DispatcherRule
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import java.lang.Exception

/**
 * Created by Dimitris Konomis (konomis.dimitris@gmail.com) on 05/03/2019.
 */

@RunWith(MockitoJUnitRunner::class)
class EntriesViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()
    @get:Rule
    val dispatcherRule: TestRule = DispatcherRule()

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
    fun testSelectedConfirmed() = runBlocking {
        val selected = true
        val id = 1L
        val entry = Entry(id = id)
        val observer: Observer<Boolean> = mock()

        entriesViewModel.selectedConfirmed.observeForever(observer)
        entriesViewModel.setSelected(entry)

        verify(entriesUseCase).setSelected(id)
        verify(observer).onChanged(selected)
    }

    @Test
    fun testNewEntryThenStateSuccess() = runBlocking {
        val name = "ABC"
        val id = 1L
        val result = Result.success(id)
        val state = true
        val observer: Observer<State<Boolean>> = mock()

        whenever(entriesUseCase.insertEntry(name)).doReturn(result)

        entriesViewModel.newEntryState.observeForever(observer)
        entriesViewModel.submit(name).join()

        verify(entriesUseCase).insertEntry(name)
        verify(observer).onChanged(State.success(state))
    }

    @Test
    fun testNewEntryThenStateError() = runBlocking {
        val name = "ABC"
        val error = Exception("Unknown Error")
        val result = Result.error(error)
        val observer: Observer<State<Boolean>> = mock()

        whenever(entriesUseCase.insertEntry(name)).doReturn(result)

        entriesViewModel.newEntryState.observeForever(observer)
        entriesViewModel.submit(name).join()

        verify(entriesUseCase).insertEntry(name)
        verify(observer).onChanged(State.error(error.localizedMessage, error))
    }

    @Test
    fun testGetEntriesThenStateSuccess() = runBlocking {
        val entry = Entry()
        val response = listOf(entry)
        val result = Result.success(response)
        val observer: Observer<State<List<Entry>>> = mock()

        whenever(entriesUseCase.getEntries()).doReturn(result)

        entriesViewModel.entries.observeForever(observer)
        entriesViewModel.getEntries().join()

        verify(entriesUseCase).getEntries()
        verify(observer).onChanged(State.success(response))
    }

    @Test
    fun testGetEntriesThenStateError() = runBlocking {
        val error = Exception("Unknown Error")
        val result = Result.error(error)
        val observer: Observer<State<List<Entry>>> = mock()

        whenever(entriesUseCase.getEntries()).doReturn(result)

        entriesViewModel.entries.observeForever(observer)
        entriesViewModel.getEntries().join()

        verify(entriesUseCase).getEntries()
        verify(observer).onChanged(State.error(error.localizedMessage, error))
    }

}