package com.dk.newssync.presentation.ui.story

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.dk.newssync.data.entity.Story
import com.dk.newssync.data.usecase.SearchUseCase
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
class StoryViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Mock
    lateinit var searchUseCase: SearchUseCase

    private lateinit var storyViewModel: StoryViewModel

    @Before
    fun setup() {
        storyViewModel = StoryViewModel(searchUseCase)
    }

    @Test
    fun testGetStoryThenResponse() {
        val id = 1L
        val story = Story(id = id)
        val observer: Observer<Story> = mock()

        whenever(searchUseCase.getStory(id)).doReturn(Flowable.just(story))

        storyViewModel.id = id
        storyViewModel.story.observeForever(observer)

        verify(searchUseCase).getStory(id)
        verify(observer).onChanged(story)
    }

    @Test
    fun testToggleFavoriteThenStateSuccess() {
        val id = 1L
        val favorite = false
        val story = Story(id = id, favorite = favorite)
        val observer: Observer<State<Boolean>> = mock()

        whenever(searchUseCase.toggleFavorite(story)).doReturn(Completable.complete())

        storyViewModel.favoriteAction.observeForever(observer)
        storyViewModel.toggleFavorite(story)

        verify(searchUseCase).toggleFavorite(story)
        verify(observer).onChanged(State.success(!favorite))
    }

    @Test
    fun testToggleFavoriteThenStateError() {
        val error = Throwable("Unknown Error")
        val story = Story(id = 1, favorite = false)
        val observer: Observer<State<Boolean>> = mock()

        whenever(searchUseCase.toggleFavorite(story)).doReturn(Completable.error(error))

        storyViewModel.favoriteAction.observeForever(observer)
        storyViewModel.toggleFavorite(story)

        verify(searchUseCase).toggleFavorite(story)
        verify(observer).onChanged(State.error(error.localizedMessage, error))
    }

}