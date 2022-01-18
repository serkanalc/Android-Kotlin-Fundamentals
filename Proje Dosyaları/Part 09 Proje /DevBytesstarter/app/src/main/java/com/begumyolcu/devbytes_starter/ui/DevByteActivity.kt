package com.begumyolcu.devbytes_starter.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.begumyolcu.devbytes_starter.R


/**
 * Bu, Navigation kütüphanesini kullanan tek activity uygulamasıdır.
 * İçerik Fragmentlar tarafından görüntülenir.
 */
class DevByteActivity : AppCompatActivity() {

    /**
     *  Activity başladığında çağrılır. Çoğu initialization'ın olması
     *  gereken yer burasıdır.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dev_byte)
    }
}