package com.dk.newssync.data

/**
 * Created by Dimitris Konomis (konomis.dimitris@gmail.com) on 2019-05-03.
 **/

sealed class Result<out T> {

    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()

    companion object {
        fun <T> success(data: T): Result<T> =
            Success(data)

        fun error(exception: Exception) : Result<Nothing> =
            Error(exception)
    }
}