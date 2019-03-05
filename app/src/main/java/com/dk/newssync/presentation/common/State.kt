package com.dk.newssync.presentation.common

/**
 * Created by Dimitris Konomis (konomis.dimitris@gmail.com) on 13/11/2018.
 **/

sealed class State<T> {

    class Loading<T>: State<T>()
    data class Error<T>(val errorMessage: String?, val error: Throwable): State<T>()
    data class Success<T>(var data: T) : State<T>()

    companion object {
        fun <T> loading(): State<T> =
            Loading()

        fun <T> error(errorMessage: String, error: Throwable): State<T> =
            Error(errorMessage, error)

        fun <T> success(data: T): State<T> =
            Success(data)
    }
}