package com.dk.newssync.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by Dimitris Konomis (konomis.dimitris@gmail.com) on 13/11/2018.
 **/

data class ArticlesResponse(

    @SerializedName("articles")
    @Expose
    val articles: List<Article>?

)

data class Article(
    @SerializedName("source")
    @Expose
    val source: Source?,

    @SerializedName("author")
    @Expose
    val author: String?,

    @SerializedName("title")
    @Expose
    val title: String?,

    @SerializedName("description")
    @Expose
    val description: String?,

    @SerializedName("url")
    @Expose
    val url: String?,

    @SerializedName("urlToImage")
    @Expose
    val urlToImage: String?,

    @SerializedName("publishedAt")
    @Expose
    val publishedAt: String?
)

data class Source(
    @SerializedName("name")
    @Expose
    val name: String?
)