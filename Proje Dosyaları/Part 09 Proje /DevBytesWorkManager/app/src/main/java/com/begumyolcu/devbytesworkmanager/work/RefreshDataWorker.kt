package com.begumyolcu.devbytesworkmanager.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.begumyolcu.devbytesworkmanager.database.getDatabase
import com.begumyolcu.devbytesworkmanager.repository.VideosRepository
import retrofit2.HttpException
import timber.log.Timber

class RefreshDataWorker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {

    companion object {
        const val WORK_NAME = "com.begumyolcu.devbytesworkmanager.work.RefreshDataWorker"
    }
    override suspend fun doWork(): Result {
        val database = getDatabase(applicationContext)
        val repository = VideosRepository(database)

        try {
            repository.refreshVideos( )
            Timber.d("WorkManager: Sync için work request çalıştırıldı.")
        } catch (e: HttpException) {
            return Result.retry()
        }

        return Result.success()
    }
}


