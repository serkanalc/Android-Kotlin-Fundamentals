package com.begumyolcu.devbytesrepository.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.begumyolcu.devbytesrepository.database.VideosDatabase
import com.begumyolcu.devbytesrepository.database.asDomainModel
import com.begumyolcu.devbytesrepository.domain.DevByteVideo
import com.begumyolcu.devbytesrepository.network.DevByteNetwork
import com.begumyolcu.devbytesrepository.network.asDatabaseModel
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