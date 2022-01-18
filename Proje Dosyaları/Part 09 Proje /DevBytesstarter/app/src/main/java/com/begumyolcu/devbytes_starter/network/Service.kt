package com.begumyolcu.devbytes_starter.network

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

/**
 * Sadece bir servisimiz olduğu için, bunların hepsi tek bir dosyada durabilir.
 * Daha fazla servis eklerseniz, bunu birden çok dosyaya bölün ve retorfit nesnesini
 * servisler arasında paylaştığınızdan emin olun.
 */

/**
 * Bir devbyte playlist'ini almak için bir retrofit servisi.
 */
interface DevbyteService {
    @GET("devbytes")
    suspend fun getPlaylist(): NetworkVideoContainer
}

/**
 * Ağ erişimi için ana giriş noktası. `DevByteNetwork.devbytes.getPlaylist()` gibi çağırın.
 */
object DevByteNetwork {

    // JSON'ı parse etmek ve coroutineleri kullnamak için retrofit'i yapılandırın
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://android-kotlin-fun-mars-server.appspot.com/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    val devbytes = retrofit.create(DevbyteService::class.java)

}