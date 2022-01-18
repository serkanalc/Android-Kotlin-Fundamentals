package com.begumyolcu.devbytesworkmanager

import android.app.Application
import android.os.Build
import androidx.work.*

import com.begumyolcu.devbytesworkmanager.work.RefreshDataWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.TimeUnit

/**
 * WorkManager aracılığıyla arka plan çalışmasını ayarlamak için application'ı override et
 */
class DevByteApplication : Application() {

    private val applicationScope = CoroutineScope(Dispatchers.Default)

    /**
     * onCreate, kullanıcıya ilk ekran gösterilmeden önce çağrılır.
     *
     * Uygulamanın başlatılmasını geciktirmekten kaçınmak için bir arka plan thread'inde
     * pahalı kurulum işlemleri çalıştırarak herhangi bir arka plan görevini ayarlamak için kullanın.
     */
    override fun onCreate() {
        super.onCreate()
        delayedInit()
    }

    private fun delayedInit() {
        applicationScope.launch {
            Timber.plant(Timber.DebugTree())
            setupRecurringWork()
        }
    }

    /**
     * Her gün yeni ağ verilerini 'fetch' etmek için WorkManager arka plan işini kurun.
     */
    private fun setupRecurringWork() {

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiresCharging(true)
            .setRequiresBatteryNotLow(true)
            .apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    setRequiresDeviceIdle(true)
                }
            }
            .build()

        val repeatingRequest = PeriodicWorkRequestBuilder<RefreshDataWorker>(1, TimeUnit.DAYS)
            .setConstraints(constraints)
            .build()

        Timber.d("WorkManager: Sync için Periodic Work schedule edildi.")
        WorkManager.getInstance().enqueueUniquePeriodicWork(
            RefreshDataWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            repeatingRequest)
    }

}



