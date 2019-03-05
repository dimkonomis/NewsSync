package com.dk.newssync.data.usecase

import com.dk.newssync.data.entity.Story
import com.dk.newssync.data.executor.BaseSchedulerProvider
import com.dk.newssync.data.repository.StoriesRepository
import io.reactivex.Completable
import io.reactivex.Flowable
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Dimitris Konomis (konomis.dimitris@gmail.com) on 13/11/2018.
 **/

@Singleton
class SearchUseCase @Inject constructor(private val storiesRepository: StoriesRepository,
                                        private val schedulerProvider: BaseSchedulerProvider
) {

    fun searchStories(q: String?): Flowable<List<Story>> {
        return storiesRepository.searchStories(q)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
    }

    fun getStory(id: Long?): Flowable<Story> {
        return storiesRepository.getStory(id)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
    }

    fun getFavorites(): Flowable<List<Story>> {
        return storiesRepository.getFavorites()
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
    }

    fun toggleFavorite(story: Story): Completable {
        return storiesRepository.toggleFavorite(story.id, !story.favorite)
            .flatMapCompletable { updated ->
                if(updated == 0) throw Exception("Not Updated") else Completable.complete()
            }
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
    }

}