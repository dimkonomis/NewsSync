package com.dk.newssync.presentation.ui.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.dk.newssync.data.Result
import com.dk.newssync.data.entity.Story
import com.dk.newssync.data.usecase.SearchUseCase
import com.dk.newssync.presentation.common.State
import com.dk.newssync.presentation.ui.DispatcherRule
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by Dimitris Konomis (konomis.dimitris@gmail.com) on 04/03/2019.
 */

@RunWith(MockitoJUnitRunner::class)
class SearchViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()
    @get:Rule
    val dispatcherRule: TestRule = DispatcherRule()

    @Mock
    lateinit var searchUseCase: SearchUseCase

    private lateinit var searchViewModel: SearchViewModel

    @Before
    fun setup() {
        searchViewModel = SearchViewModel(searchUseCase)
    }

    @Test
    fun testSearchStoriesThenStateSuccess() = runBlocking {
        val q = "q"
        val response = listOf(Story(id = 1))
        val result = Result.success(response)
        val observer: Observer<State<List<Story>>> = mock()

        whenever(searchUseCase.searchStories(q)).doReturn(result)

        searchViewModel.q = q
        searchViewModel.stories.observeForever(observer)
        searchViewModel.getStories().join()

        verify(searchUseCase).searchStories(q)
        verify(observer).onChanged(State.success(response))
    }

    @Test
    fun testSearchStoriesThenStateError() = runBlocking {
        val q = "q"
        val error = Exception("Unknown Error")
        val result = Result.error(error)
        val observer: Observer<State<List<Story>>> = mock()

        whenever(searchUseCase.searchStories(q)).doReturn(result)

        searchViewModel.q = q
        searchViewModel.stories.observeForever(observer)
        searchViewModel.getStories().join()

        verify(searchUseCase).searchStories(q)
        verify(observer).onChanged(State.error(error.localizedMessage, error))
    }

    @Test
    fun testFavoritesThenStateSuccess() = runBlocking {
        val response = listOf(Story(id = 1, favorite = true))
        val result = Result.success(response)
        val observer: Observer<State<List<Story>>> = mock()

        whenever(searchUseCase.getFavorites()).doReturn(result)

        searchViewModel.favorites.observeForever(observer)
        searchViewModel.getFavorites().join()

        verify(searchUseCase).getFavorites()
        verify(observer).onChanged(State.success(response))
    }

}