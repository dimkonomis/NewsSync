package com.dk.newssync.data.source.local

import com.dk.newssync.data.entity.Entry
import com.dk.newssync.data.entity.Story
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Singleton

/**
 * Created by Dimitris Konomis (konomis.dimitris@gmail.com) on 13/11/2018.
 **/

@Singleton
class LocalSourceImpl(private val localDatabase: LocalDatabase, private val localSharedPrefs: LocalSharedPrefs) :
    LocalSource {

    override fun getStories(entryId: Long?): Flowable<List<Story>> {
        return localDatabase
            .localDao()
            .getStories(entryId ?: 0)
            .onErrorReturn { listOf() }
    }

    override fun getStory(id: Long?): Flowable<Story> {
        return localDatabase
            .localDao()
            .getStory(id)
    }

    override fun insertStories(stories: List<Story>): Completable {
        return Completable.fromCallable {
            localDatabase
                .localDao()
                .insertStories(stories)
        }
    }

    override fun getFavorites(): Flowable<List<Story>> {
        return localDatabase
            .localDao()
            .getFavorites()
    }

    override fun toggleFavorite(id: Long, favorite: Boolean): Single<Int> {
        return Single.fromCallable {
            localDatabase
                .localDao()
                .toggleFavorite(id, if (favorite) 1 else 0)
        }
    }

    override fun getEntries(): Single<List<Entry>> {
        return localDatabase
            .localDao()
            .getEntries()
    }

    override fun getEntry(id: Long): Single<Entry> {
        return localDatabase
            .localDao()
            .getEntry(id)
    }

    override fun insertEntry(entry: Entry): Single<Long> {
        return Single.fromCallable {
            localDatabase
                .localDao()
                .insertEntry(entry)
        }
    }

    override fun setSelected(id: Long): Completable {
        return Completable.fromCallable {
            localSharedPrefs
                .set(SELECTEDKEY, id)
        }
    }

    override fun getSelected(): Long {
        return localSharedPrefs.get(SELECTEDKEY, 0) ?: 0
    }

}