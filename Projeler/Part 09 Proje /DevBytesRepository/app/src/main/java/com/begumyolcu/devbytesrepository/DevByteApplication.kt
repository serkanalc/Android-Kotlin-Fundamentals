package com.begumyolcu.devbytesrepository

import android.app.Application
import timber.log.Timber

/**
 * WorkManager aracılığıyla arka plan çalışmasını ayarlamak için application'ı override et
 */
class DevByteApplication : Application() {

    /**
     * onCreate, kullanıcıya ilk ekran gösterilmeden önce çağrılır.
     *
     * Uygulamanın başlatılmasını geciktirmekten kaçınmak için bir arka plan thread'inde
     * pahalı kurulum işlemleri çalıştırarak herhangi bir arka plan görevini ayarlamak için kullanın.
     */
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}