package com.dk.newssync.presentation.ui.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.dk.newssync.data.entity.Story
import com.dk.newssync.data.usecase.SearchUseCase
import com.dk.newssync.presentation.common.State
import com.nhaarman.mockitokotlin2.*
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
 * Created by Dimitris Konomis (konomis.dimitris@gmail.com) on 04/03/2019.
 */

@RunWith(MockitoJUnitRunner::class)
class SearchViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Mock
    lateinit var searchUseCase: SearchUseCase

    private lateinit var searchViewModel: SearchViewModel

    @Before
    fun setup() {
        searchViewModel = SearchViewModel(searchUseCase)
    }

    @Test
    fun testSearchStoriesThenStateSuccess() {
        val q = "q"
        val response = listOf(Story(id = 1))
        val observer: Observer<State<List<Story>>> = mock()

        whenever(searchUseCase.searchStories(q)).doReturn(Flowable.just(response))

        searchViewModel.q = q
        searchViewModel.stories.observeForever(observer)

        verify(searchUseCase).searchStories(q)
        verify(observer).onChanged(State.success(response))
    }

    @Test
    fun testSearchStoriesThenStateError() {
        val q = "q"
        val error = Throwable("Unknown Error")
        val observer: Observer<State<List<Story>>> = mock()

        whenever(searchUseCase.searchStories(q)).doReturn(Flowable.error(error))

        searchViewModel.q = q
        searchViewModel.stories.observeForever(observer)

        verify(searchUseCase).searchStories(q)
        verify(observer).onChanged(State.error(error.localizedMessage, error))
    }

    @Test
    fun testFavoritesThenStateSuccess() {
        val response = listOf(Story(id = 1, favorite = true))
        val observer: Observer<State<List<Story>>> = mock()

        whenever(searchUseCase.getFavorites()).doReturn(Flowable.just(response))

        searchViewModel.favorites.observeForever(observer)

        verify(searchUseCase).getFavorites()
        verify(observer).onChanged(State.success(response))
    }

    @Test
    fun testToggleFavorite() {
        val story = Story(id = 1, favorite = true)

        whenever(searchUseCase.toggleFavorite(story)).doReturn(Completable.complete())

        searchViewModel.toggleFavorite(story)

        verify(searchUseCase).toggleFavorite(story)
    }

}