package com.dk.newssync.data.source.network

import com.dk.newssync.data.model.ArticlesResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Dimitris Konomis (konomis.dimitris@gmail.com) on 13/11/2018.
 **/

interface NetworkSource {

    @GET("everything?sortBy=popularity&language=en")
    fun searchArticles(@Query("q") q: String?, @Query("from") from: String?): Single<ArticlesResponse>

}