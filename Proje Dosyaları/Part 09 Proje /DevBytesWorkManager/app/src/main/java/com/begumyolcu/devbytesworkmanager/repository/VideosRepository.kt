package com.begumyolcu.devbytesworkmanager.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.begumyolcu.devbytesworkmanager.database.VideosDatabase
import com.begumyolcu.devbytesworkmanager.database.asDomainModel
import com.begumyolcu.devbytesworkmanager.domain.DevByteVideo
import com.begumyolcu.devbytesworkmanager.network.DevByteNetwork
import com.begumyolcu.devbytesworkmanager.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

/**
 * Ağdan devbyte videoları almak ve bunları diskte depolamak için bir repository
 */
class VideosRepository(private val database: VideosDatabase) {

    val videos: LiveData<List<DevByteVideo>> = Transformations.map(database.videoDao.getVideos()) {
        it.asDomainModel()
    }
    /**
     * Çevrimdışı cache'te depolanan videoları yenileyin.
     *
     * Bu fonksiyon, veritabanı ekleme veritabanı işleminin IO dispatcher'ında gerçekleşmesini sağlamak için IO dispatcher'ı kullanır. * 'withContext' kullanarak IO dispatcher'ına geçerek, bu işlevin main thread dahil herhangi bir thread'den çağrılması artık * *   * güvenlidir.
     *
     */
    suspend fun refreshVideos() {
        withContext(Dispatchers.IO) {
            Timber.d("refresh videos çağrıldı");
            val playlist = DevByteNetwork.devbytes.getPlaylist()
            database.videoDao.insertAll(playlist.asDatabaseModel())
        }
    }
}