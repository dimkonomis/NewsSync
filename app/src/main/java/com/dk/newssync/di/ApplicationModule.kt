package com.dk.newssync.di

import android.content.Context
import androidx.core.app.NotificationManagerCompat
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Dimitris Konomis (konomis.dimitris@gmail.com) on 13/11/2018.
 **/

@Module
class ApplicationModule(private val context: Context) {

    @Singleton
    @Provides
    fun provideContext(): Context = context

    @Provides
    fun bindNotificationManager(): NotificationManagerCompat = NotificationManagerCompat.from(context)

}