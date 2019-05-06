package com.dk.newssync.presentation.sync

import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.dk.newssync.R
import com.dk.newssync.data.usecase.SyncUseCase
import com.dk.newssync.presentation.notification.NotificationChannels
import dagger.android.*
import javax.inject.Inject
import kotlinx.coroutines.*

/**
 * Created by Dimitris Konomis (konomis.dimitris@gmail.com) on 27/02/2019.
 **/

class SyncService: DaggerService(), CoroutineScope {

    @Inject
    lateinit var syncUseCase: SyncUseCase
    @Inject
    lateinit var notificationManager: NotificationManagerCompat

    private lateinit var notificationChannels: NotificationChannels
    private val serviceJob = Job()

    companion object {
        private const val NOTIFICATION_ID = 1000
    }

    override val coroutineContext = serviceJob + Dispatchers.IO

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannels = NotificationChannels(this)
        }

        showNotification()
        launch { startSync() }
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceJob.cancel()
    }

    private suspend fun startSync() = coroutineScope {
        syncUseCase.syncStories()
        dismissNotification()
    }

    private fun showNotification() {
        val notification = createBuilder()
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setOnlyAlertOnce(true)
            .setContentTitle(getString(R.string.sync_message, getString(R.string.app_name)))
            .setOngoing(true)
            .setProgress(100, 0, true)
            .build()

        notificationManager.notify(NOTIFICATION_ID, notification)
        startForeground(NOTIFICATION_ID, notification)
    }

    private fun dismissNotification() {
        notificationManager.cancel(NOTIFICATION_ID)
        stopForeground(true)
        stopSelf()
    }

    private fun createBuilder(): NotificationCompat.Builder {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationCompat.Builder(this, notificationChannels.primaryChannel)
        } else {
            NotificationCompat.Builder(this)
        }
    }

}