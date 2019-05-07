package com.dk.newssync.data.repository

import com.dk.newssync.data.Result
import kotlinx.coroutines.*
import timber.log.Timber

/**
 * Created by Dimitris Konomis (konomis.dimitris@gmail.com) on 2019-05-03.
 **/

interface Repository {

    suspend fun <T> requestAwait(
        call: () -> Deferred<T>
    ): Result<T>  {
        return try {
            val result = call().await()
            Result.success(result)
        } catch (exception: Exception) {
            Timber.e(exception)
            Result.error(exception)
        }
    }

    suspend fun <T> request(
        call: suspend () -> T
    ): Result<T>  {
        return try {
            Result.success(call())
        } catch (exception: Exception) {
            Timber.e(exception)
            Result.error(exception)
        }
    }

}
