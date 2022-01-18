package com.begumyolcu.devbytes_starter.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.begumyolcu.devbytes_starter.domain.DevByteVideo
import com.begumyolcu.devbytes_starter.network.DevByteNetwork
import com.begumyolcu.devbytes_starter.network.asDomainModel
import kotlinx.coroutines.launch
import java.io.IOException
//TODO 3: util ve altındaki tüm dosyaları ekle
//TODO 4: res  ve altındaki tüm dosyaları ekle ve düzenle
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
     * Ekranda gösterilebilen videoların bir oynatma listesi. Bu, observerlara
     * bu değeri ayarlamanın bir yolunu göstermemek için private'tır.
     */
    private val _playlist = MutableLiveData<List<DevByteVideo>>()

    /**
     * Ekranda gösterilebilen videoların bir oynatma listesi. Viewlar, verilere
     * erişmek için bunu kullanmalıdır.
     */
    val playlist: LiveData<List<DevByteVideo>>
        get() = _playlist



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
        refreshDataFromNetwork()
    }

    /**
     * Ağdaki verileri yenileyin ve LiveData aracılığıyla iletin. Arka plan thread'ine
     * ulaşmak için bir coroutine launch kullanın.
     */
    private fun refreshDataFromNetwork() = viewModelScope.launch {

        try {
            val playlist = DevByteNetwork.devbytes.getPlaylist()
            _playlist.postValue(playlist.asDomainModel())

            _eventNetworkError.value = false
            _isNetworkErrorShown.value = false

        } catch (networkError: IOException) {
            // Bir Toast hata mesajı gösterin ve ilerleme çubuğunu gizleyin.
            _eventNetworkError.value = true
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
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}