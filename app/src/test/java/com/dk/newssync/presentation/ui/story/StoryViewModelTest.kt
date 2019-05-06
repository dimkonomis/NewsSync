package com.dk.newssync.presentation.ui.story

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.dk.newssync.data.Result
import com.dk.newssync.data.entity.Story
import com.dk.newssync.data.usecase.SearchUseCase
import com.dk.newssync.presentation.common.State
import com.dk.newssync.presentation.ui.DispatcherRule
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.*
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
class StoryViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()
    @get:Rule
    val dispatcherRule: TestRule = DispatcherRule()

    @Mock
    lateinit var searchUseCase: SearchUseCase

    private lateinit var storyViewModel: StoryViewModel

    @Before
    fun setup() {
        storyViewModel = StoryViewModel(searchUseCase)
    }

    @Test
    fun testGetStoryThenResponse() = runBlocking {
        val id = 1L
        val story = Story(id = id)
        val result = Result.success(story)
        val observer: Observer<Story> = mock()

        whenever(searchUseCase.getStory(id)).doReturn(result)

        storyViewModel.story.observeForever(observer)
        storyViewModel.getStory(id).join()

        verify(searchUseCase).getStory(id)
        verify(observer).onChanged(story)
    }

    @Test
    fun testToggleFavoriteThenStateSuccess() = runBlocking {
        val id = 1L
        val favorite = false
        val result = Result.success(1)
        val story = Story(id = id, favorite = favorite)
        val observer: Observer<State<Boolean>> = mock()

        whenever(searchUseCase.toggleFavorite(story)).doReturn(result)

        storyViewModel.favoriteAction.observeForever(observer)
        storyViewModel.toggleFavorite(story).join()

        verify(searchUseCase).toggleFavorite(story)
        verify(observer).onChanged(State.success(!favorite))
    }

    @Test
    fun testToggleFavoriteThenStateError() = runBlocking {
        val error = Exception("Unknown Error")
        val result = Result.error(error)
        val story = Story(id = 1, favorite = false)
        val observer: Observer<State<Boolean>> = mock()

        whenever(searchUseCase.toggleFavorite(story)).doReturn(result)

        storyViewModel.favoriteAction.observeForever(observer)
        storyViewModel.toggleFavorite(story).join()

        verify(searchUseCase).toggleFavorite(story)
        verify(observer).onChanged(State.error(error.localizedMessage, error))
    }

}