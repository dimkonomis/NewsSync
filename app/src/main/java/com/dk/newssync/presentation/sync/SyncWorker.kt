package com.dk.newssync.presentation.sync

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import androidx.work.Worker
import androidx.work.WorkerParameters

/**
 * Created by Dimitris Konomis (konomis.dimitris@gmail.com) on 28/02/2019.
 **/

class SyncWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {

    override fun doWork(): Result {
        ContextCompat.startForegroundService(
            applicationContext,
            Intent(applicationContext, SyncService::class.java)
        )
        return Result.success()
    }

}
