package com.dk.newssync.data.source.network

import com.dk.newssync.utils.API_KEY
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * Created by Dimitris Konomis (konomis.dimitris@gmail.com) on 13/11/2018.
 **/

class NetworkHeadersInterceptor() : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        val requestBuilder = original.newBuilder()
            .header("X-Api-Key", API_KEY)

        val request = requestBuilder.method(original.method(), original.body()).build()

        return chain.proceed(request)
    }
}