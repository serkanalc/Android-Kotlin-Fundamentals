# <a name="1"></a>Bir Repository Ekleyin

- [Kurulum ve başlangıç kodu kılavuzu](#a)
- [Caching](#b)
- [Çevrimdışı cache ekleyin](#c)
- [Repositoryler](#d)
- [Bir repository oluşturun](#e)
- [Bir refresh stratejisi kullanarak repository'yi entegre edin](#f)

DevBytes uygulaması, [Google Android geliştirici ilişkileri ekibi](https://www.youtube.com/channel/UCVHFbqXqoYvEWM1Ddxl0QDg) tarafından hazırlanan kısa eğitimler olan DevByte videolarının bir listesini görüntüler. Videolar, geliştirici özelliklerini ve Android geliştirme için best practiceleri tanıtır.

DevBytes başlangıç uygulaması, [Retrofit](https://square.github.io/retrofit/) kütüphanesini kullanarak ağdan video URL'lerinin bir listesini getirir ve bir `RecyclerView` kullanarak listeyi görüntüler. Uygulama, verileri tutmak ve UI'yı güncellemek için `ViewModel` ve `LiveData`'yı kullanır. Uygulamanın mimarisi, bu kursta daha önce geliştirdiğiniz uygulamalara benzer.

![app image](https://developer.android.com/codelabs/kotlin-android-training-repository/img/30ee74d946a2f6ca.png)

Başlangıç uygulaması yalnızca çevrimiçidir, bu nedenle kullanıcının onu kullanmak için bir ağ bağlantısına ihtiyacı vardır. Bu dökümanda, sonuçları ağ yerine yerel veritabanından görüntülemek için çevrimdışı caching (önbelleğe alma) uygularsınız. Kullanıcılarınız, cihazları çevrimdışıyken veya ağ bağlantılarının yavaş olması durumunda uygulamayı kullanabilir.

Çevrimdışı cache'i uygulamak için, alınan verileri cihazın yerel belleğinde kalıcı hale getirmek için bir [`Room`](https://developer.android.com/topic/libraries/architecture/room) veritabanı kullanırsınız. Veri kaynaklarını uygulamanın geri kalanından ayıran bir tasarım pattern'ı olan bir _repository pattern_ kullanarak `Room` veritabanına erişir ve onu yönetirsiniz. Bu teknik, uygulamanın geri kalanının verilere erişmek için kullanması için temiz bir API sağlar.

## <a name="a"></a>Aşama 1 : Kurulum ve başlangıç kodu kılavuzu

Bu aşamada, DevBytes uygulamasının başlangıç kodunu indirip inceleyebilirsiniz.

### Adım 1: Başlangıç uygulamasını indirin & çalıştırın

1. DevBytes başlangıç kodunu GitHub'dan indirin.
2. Kodu zip'ten çıkarın ve projeyi Android Studio'da açın.
3. Henüz bağlı değilse, test cihazınızı veya emülatörünüzü internete bağlayın. Uygulamayı build edin ve çalıştırın. Uygulama, ağdan DevByte videolarının bir listesini alır ve görüntüler.
4. Uygulamada, YouTube uygulamasında açmak için herhangi bir videoya tıklayın.
5. Cihazınızda veya emülatör'ünüzde uçak modunu etkinleştirin.
6. Uygulamayı tekrar çalıştırın ve ağ hatası toast mesajına dikkat edin.

![error message](https://developer.android.com/codelabs/kotlin-android-training-repository/img/3388360b2a512f7a.png)

Uçak modu kapalıyken, bu yalnızca çevrimiçi bir uygulama olduğu için internet bağlantınız yavaşsa dönen bir ilerleme çubuğu görebilirsiniz. Dönen ilerleme çubuğunu görmüyorsanız, ağ gecikmesini programlı olarak eklemek için sonraki adımı uygulayın. Bu, yavaş bağlantılara sahip kullanıcılar için uygulama deneyiminin nasıl olduğunu ve bu uygulama için çevrimdışı caching'in neden önemli olduğunu görmenize yardımcı olacaktır.

### Adım 2: (Opsiyonel) Ağ gecikmesini simüle edin

Emülatörünüzün veya cihazınızın internet bağlantısı iyiyse ve dönen ilerleme çubuğunu fark etmiyorsanız, `delay()` fonksiyonunu kullanarak ağ yanıtındaki (network response) gecikmeyi simüle edin. `delay()` fonksiyonu hakkında daha fazla bilgi edinmek için [Kotlin ile ilk coroutine'iniz](https://kotlinlang.org/docs/tutorials/coroutines/coroutines-basic-jvm.html) bölümüne bakın.

1. Cihazınızda veya emülatörünüzde uçak modunun kapalı olduğundan emin olun.
2. `DevByteViewModel`'da, `refreshDataFromNetwork()` içinde, `catch` bloğunun başlangıcında 2 saniyelik bir gecikme ekleyin. Bu gecikme kodu, ağ hatasının ayarlanmasını geciktirecektir.

```

private fun refreshDataFromNetwork() = viewModelScope.launch {

   try {
        ...
   } catch (networkError: IOException) {
       delay(2000)
       // Show a Toast error message and hide the progress bar.
       _eventNetworkError.value = true
   }
}

```

3. Uygulamayı tekrar çalıştırın. Şimdi bir yükleme döndürücü (loading spinner) ve ağ hatası toast mesajı görüyorsunuz. Yükleme döndürücü, ağ bağlantılarının yavaş olması durumunda kullanıcılarınızın görebileceği şeydir. Çevrimdışı caching'i uyguladıktan sonra bu kullanıcı deneyimi iyileşecektir.

![loading spinner](https://developer.android.com/codelabs/kotlin-android-training-repository/img/2cf359e25e5eb8dd.png)

4. Bir önceki adımda eklediğiniz delay ifadesini, `delay(2000)`, kaldırın.

### Adım 3: Kodu keşfedin

Bu başlangıç uygulaması, uygulamanın repository modülüne odaklanabilmeniz için özellikle tüm ağ ve kullanıcı interface modülleri olmak üzere birçok kodla birlikte gelir.

1. Android Studio'da tüm paketleri genişletin.
2. `domain` paketini keşfedin. Bu paket, uygulamanın verilerini temsil eden `data` classlarını içerir. Örneğin, `domain/Models.kt` class'ındaki `DevByteVideo` data class'ı, tek bir DevByte videosunu temsil eder.
3. `network` paketini keşfedin.

`network/DataTransferObjects.kt` class'ı, `NetworkVideo` adlı bir [data transfer object](https://en.wikipedia.org/wiki/Data_transfer_object) (veri aktarım nesnesi) için data class'ını içerir. Data transfer object, ağ sonucunu parse etmek için kullanılır. Bu dosya ayrıca ağ sonuçlarını bir domain nesneleri listesine dönüştürmek için bir kolaylık metodu olan `asDomainModel()` içerir. Data transfer objectleri, ağ sonuçlarını parse etmek için fazladan mantık içerdiklerinden, domain nesnelerinden farklıdır.

>İpucu: Ağ, domain ve veritabanı nesnelerini ayırmak en iyi uygulamadır (best practice). Bu strateji, [separation of concerns](https://en.wikipedia.org/wiki/Separation_of_concerns) ilkesini takip eder. Ağ yanıtı veya veritabanı şeması (schema) değişirse, tüm uygulamanın kodunu güncellemeden uygulama bileşenlerini değiştirebilmek ve yönetebilmek istersiniz.

4. Başlangıç kodunun geri kalanını kendi başınıza keşfetmeyi deneyin.

Uygulamaların mimarisinin geri kalanı, önceki dökümanlarda kullanılan diğer uygulamalara benzer:

- Retrofit servisi, `network/Service.kt`, ağdan `devbytes` çalma listesini getirir.
- `DevByteViewModel`, uygulama verilerini `LiveData` nesneleri olarak tutar.
- UI controller (denetleyicisi) `DevByteFragment`, video listesini ve `LiveData` nesneleri için observerları görüntülemek için bir `RecyclerView` içerir.

## <a name="b"></a>Aşama 2 : Caching
## <a name="c"></a>Aşama 3 : Çevrimdışı cache ekleyin
## <a name="d"></a>Aşama 4 : Repositoryler
## <a name="e"></a>Aşama 5 : Bir repository oluşturun
## <a name="f"></a>Aşama 6 : Bir refresh stratejisi kullanarak repository'yi entegre edin

