package com.begumyolcu.devbytesworkmanager.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.begumyolcu.devbytesworkmanager.database.getDatabase
import com.begumyolcu.devbytesworkmanager.repository.VideosRepository
import kotlinx.coroutines.*
import java.io.IOException

/**
 * DevByteViewModel, UI ile ilgili verileri lifecycle bilinçli bir şekilde depolamak ve yönetmek
 * için tasarlanmıştır. Bu, verilerin ekran döndürme gibi konfigürasyon değişikliklerinden
 * kurtulmasını sağlar. Ek olarak, ağ sonuçlarını alma gibi arka plan çalışmaları, konfigürasyon
 * değişiklikleri yoluyla devam edebilir ve sonuçları yeni Fragment veya Activity kullanıma
 * sunulduktan sonra teslim edebilir.
 *
 * @param application Bu viewmodel'ın bağlı olduğu application (uygulama), Application, activity
 * veya fragment lifecycle olayları sırasında hiçbir zaman yeniden oluşturulmadığından,
 * rotasyon boyunca uygulamalara referans tutmak güvenlidir.
 */
class DevByteViewModel(application: Application) : AndroidViewModel(application) {

    /**
     * Bu ViewModel'in sonuçları alacağı veri kaynağı.
     */
    private val videosRepository = VideosRepository(getDatabase(application))

    /**
     * Ekranda gösterilen videoların bir playlist'i.
     */
    val playlist = videosRepository.videos

    /**
     * Ağ hatası için tetiklenen event (olay). Bu, observarlara bu değeri
     * ayarlamanın bir yolunu göstermemek için private'tır.
     */
    private var _eventNetworkError = MutableLiveData<Boolean>(false)

    /**
     * Ağ hatası için tetiklenen event (olay). Viewlar, verilere
     * erişmek için bunu kullanmalıdır.
     */
    val eventNetworkError: LiveData<Boolean>
        get() = _eventNetworkError

    /**
     * Hata mesajını göstermek için flag. Bu, observarlara bu değeri
     * ayarlamanın bir yolunu göstermemek için private'tır.
     */
    private var _isNetworkErrorShown = MutableLiveData<Boolean>(false)

    /**
     * Hata mesajını göstermek için flag. Viewlar, verilere
     * erişmek için bunu kullanmalıdır.
     */
    val isNetworkErrorShown: LiveData<Boolean>
        get() = _isNetworkErrorShown

    /**
     * init{}, bu ViewModel oluşturulduğunda hemen çağrılır.
     */
    init {
        refreshDataFromRepository()
    }

    /**
     * Repository'deki verileri yenileyin. Bir arka plan thread'inde çalıştırmak için bir coroutine launch kullanın.
     */
    private fun refreshDataFromRepository() {
        viewModelScope.launch {
            try {
                videosRepository.refreshVideos()
                _eventNetworkError.value = false
                _isNetworkErrorShown.value = false

            } catch (networkError: IOException) {
                // Bir Toast hata mesajı gösterin ve ilerleme çubuğunu gizleyin.
                if(playlist.value.isNullOrEmpty())
                    _eventNetworkError.value = true
            }
        }
    }

    /**
     * Ağ hatası flag'ini resetler.
     */
    fun onNetworkErrorShown() {
        _isNetworkErrorShown.value = true
    }

    /**
     * parametre ile DevByteViewModel oluşturmak için factory
     */
    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DevByteViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return DevByteViewModel(app) as T
            }
            throw IllegalArgumentException("viewmodel oluşturulamıyor")
        }
    }
}