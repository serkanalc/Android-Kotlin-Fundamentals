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

Bir uygulama ağdan veri aldıktan sonra, uygulama verileri bir cihazın deposunda depolayarak verileri [cache](https://searchstorage.techtarget.com/definition/cache) edebilir (önbelleğe alabilir). Daha sonra cihaz çevrimdışıyken veya aynı verilere tekrar erişmek istediğinizde erişebilmek için verileri cache edersiniz.

Aşağıdaki tablo, Android'de network (ağ) caching uygulamanın birkaç yolunu gösterir. Bu dökümanda, yapılandırılmış verileri bir aygıt dosya sisteminde depolamanın önerilen yolu olduğu için `Room`'u kullanırsınız.

| Caching tekniği                                                                                                                                                                                                                                                                                            | Kullanımları                                                                                                                                                                                                                                                                        |
|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| [Retrofit](http://square.github.io/retrofit/), Android için type-safe bir REST client uygulamak için kullanılan bir ağ kütüphanesidir. Retrofit'i her ağ sonucunun bir kopyasını yerel olarak depolamak için yapılandırabilirsiniz.                                                                        | Basit request ve responselar, seyrek ağ aramaları veya küçük veri kümeleri için iyi bir çözüm.                                                                                                                                                                                      |
| key-value çiftlerini depolamak için `SharedPreferences`'ı kullanabilirsiniz.                                                                                                                                                                                                                                 | Az sayıda key ve basit valuelar için iyi bir çözüm. Bu tekniği büyük miktarda yapılandırılmış veri depolamak için kullanamazsınız.                                                                                                                                                  |
| Uygulamanın dahili depolama dizinine erişebilir ve buna veri dosyalarını kaydedebilirsiniz. Uygulamanızın paket adı, uygulamanın Android dosya sisteminde özel bir konumda bulunan dahili depolama dizinini belirtir. Bu dizin uygulamanıza özeldir ve uygulamanızın yüklemesi kaldırıldığında temizlenir. | Bir dosya sisteminin çözebileceği belirli ihtiyaçlarınız varsa, örneğin medya dosyalarını veya veri dosyalarını kaydetmeniz gerekiyorsa ve dosyaları kendiniz yönetmeniz gerekiyorsa iyi bir çözüm. Karmaşık ve yapılandırılmış verileri depolamak için bu tekniği kullanamazsınız. |
| SQLite üzerinde bir abstraction katmanı sağlayan bir SQLite object-mapping kütüphanesi olan [Room](https://developer.android.com/topic/libraries/architecture/room)'u kullanarak verileri cache edebilirsiniz.                                                                                             | Yapılandırılmış verileri bir aygıtın dosya sisteminde depolamanın en iyi yolu yerel bir SQLite veritabanında olduğundan, karmaşık ve yapılandırılmış veriler için önerilen çözüm.                                                                                                   |


## <a name="c"></a>Aşama 3 : Çevrimdışı cache ekleyin

Bu aşamada, çevrimdışı cache olarak kullanmak için uygulamanıza bir `Room` veritabanı ekleyeceksiniz.

>Anahtar kavram: Uygulama her başlatıldığında ağdan veri almayın. Bunun yerine, veritabanından aldığınız verileri görüntüleyin. Bu teknik, uygulama yükleme süresini azaltır.

![data fetch](https://developer.android.com/codelabs/kotlin-android-training-repository/img/e23d9d1fb048f343.png)

Uygulama ağdan veri getirdiğinde, verileri hemen görüntülemek yerine verileri veritabanında saklayın.

Yeni bir ağ sonucu alındığında, yerel veri tabanını güncelleyin ve yerel veri tabanından yeni içeriği ekranda görüntüleyin. Bu teknik, çevrimdışı cache'in her zaman güncel olmasını sağlar. Ayrıca, cihaz çevrimdışıysa uygulamanız yerel olarak cache edilmiş verileri yükleyebilir.

### Adım 1: Room dependency'sini ekleyin

1. `build.gradle (Module:app)` dosyasını açın ve projeye `Room` dependency'sini ekleyin.

```

// Room dependency
def room_version = "2.1.0-alpha06"
implementation "androidx.room:room-runtime:$room_version"
kapt "androidx.room:room-compiler:$room_version"

```

### Adım 2: Database nesnesini ekleyin

Bu adımda, veritabanı nesnelerini temsil etmek için `DatabaseVideo` adlı bir veritabanı entity'si oluşturacaksınız. Ayrıca `DatabaseVideo` nesnelerini domain nesnelerine ve ağ nesnelerini `DatabaseVideo` nesnelerine dönüştürmek için kolaylık metotlarını uygulayacaksınız.

1. `database/DatabaseEntities.kt`'i açın ve `DatabaseVideo` adı verilen bir `Room` entity'si oluşturun. Primary key olarak `url`'yi verin. DevBytes server tasarımı, video URL'sinin her zaman benzersiz olmasını sağlar.

```

/**
* DatabaseVideo veritabaınındaki bir video entity'sini temsil eder.
*/
@Entity
data class DatabaseVideo constructor(
       @PrimaryKey
       val url: String,
       val updated: String,
       val title: String,
       val description: String,
       val thumbnail: String)
       
```

2. `database/DatabaseEntities.kt` dosyasında, `asDomainModel()` adlı bir extension fonksiyonu oluşturun. `DatabaseVideo` veritabanı nesnelerini domain nesnelerine dönüştürmek için bu fonksiyonu kullanın.

```

/**
* DatabaseVideolarını domain entitylerine mapleyin.
*/
fun List<DatabaseVideo>.asDomainModel(): List<DevByteVideo> {
   return map {
       DevByteVideo(
               url = it.url,
               title = it.title,
               description = it.description,
               updated = it.updated,
               thumbnail = it.thumbnail)
   }
}

```

Bu örnek uygulamada dönüştürme basittir ve bu kodun bir kısmı gerekli değildir. Ancak gerçek dünyadaki bir uygulamada domain, veritabanı ve ağ nesnelerinin yapısı farklı olacaktır. Karmaşık olabilecek dönüştürme mantığına ihtiyacınız olacak.

3. `network/DataTransferObjects.kt` dosyasını açın ve `asDatabaseModel()` adlı bir extension fonksiyonu oluşturun. Ağ nesnelerini `DatabaseVideo` veritabanı nesnelerine dönüştürmek için bu fonksiyonu kullanın.

```

/**
* Network resultlarını veritabanı nesnelerine dönüştürün
*/
fun NetworkVideoContainer.asDatabaseModel(): List<DatabaseVideo> {
   return videos.map {
       DatabaseVideo(
               title = it.title,
               description = it.description,
               url = it.url,
               updated = it.updated,
               thumbnail = it.thumbnail)
   }
}

```

### Adım 3: VideoDao'yu ekleyin

Bu adımda, VideoDao'yu uygulayacak ve veritabanına erişmek için iki yardımcı metot tanımlayacaksınız. Bir yardımcı metot, videoları veritabanından alır ve diğer metot, videoları veritabanına ekler.

1. `database/Room.kt`'de, bir `VideoDao` interface'i tanımlayın ve `@Dao` ile annotate edin.

```

@Dao
interface VideoDao { 
}

```
2. `VideoDao` interface'inin içinde, veritabanındaki tüm videoları almak için `getVideos()` adlı bir metot oluşturun. Bu metodun return türünü `LiveData` olarak değiştirin, böylece UI'da görüntülenen veriler, veritabanındaki veriler her değiştirildiğinde yenilenecektir.

```

   @Query("select * from databasevideo")
   fun getVideos(): LiveData<List<DatabaseVideo>>
   
```

Android Studio'da bir `Unresolved reference` hatası görünürse, `androidx.room.Query`'yi import edin.

3. `VideoDao` interface'inin içinde, ağdan alınan videoların bir listesini veritabanına eklemek için başka bir `insertAll()` metodu tanımlayın. Basit olması için, video entry'si veritabanında zaten mevcutsa, veritabanı entry'sin overwrite edin. Bunu yapmak için, conflict stratejisini `REPLACE` olarak ayarlamak için `onConflict` argümanını kullanın.

```

@Insert(onConflict = OnConflictStrategy.REPLACE)
fun insertAll( videos: List<DatabaseVideo>)

```

### Adım 4: RoomDatabase'i uygulayın 

Bu adımda, [`RoomDatabase`](https://developer.android.com/reference/android/arch/persistence/room/RoomDatabase)'i uygulayarak çevrimdışı cache'iniz için veritabanını eklersiniz.

1. `database/Room.kt`'de `VideoDao` interface'inden sonra `VideosDatabase` adlı bir `abstract` class oluşturun. `VideosDatabase`'i `RoomDatabase`'den extend edin.
2. `VideosDatabase` sınıfını bir `Room` veritabanı olarak işaretlemek için `@Database` annotation'ını kullanın. Bu veritabanına ait olan `DatabaseVideo` entity'sini bildirin ve versiyon numarasını `1` olarak ayarlayın.
3. `VideosDatabase` içinde, `Dao` metotlarına erişmek için `VideoDao` türünde bir değişken tanımlayın.

```

@Database(entities = [DatabaseVideo::class], version = 1)
abstract class VideosDatabase: RoomDatabase() {
   abstract val videoDao: VideoDao
}

```

4. Singleton nesnesini tutmak için classların dışında `INSTANCE` adlı bir `private` `lateinit` değişkeni oluşturun. Veritabanının birden çok instance'ının aynı anda açılmasını önlemek için `VideosDatabase` [singleton](https://en.wikipedia.org/wiki/Singleton_pattern) olmalıdır.
5. Classların dışında bir `getDatabase()` metodu oluşturun ve tanımlayın. `getDatabase()` içinde, `synchronized` bloğu içindeki `INSTANCE` değişkenini initialize edin ve döndürün.

```

@Dao
interface VideoDao {
...
}
abstract class VideosDatabase: RoomDatabase() {
...
}

private lateinit var INSTANCE: VideosDatabase

fun getDatabase(context: Context): VideosDatabase {
   synchronized(VideosDatabase::class.java) {
       if (!::INSTANCE.isInitialized) {
           INSTANCE = Room.databaseBuilder(context.applicationContext,
                   VideosDatabase::class.java,
                   "videos").build()
       }
   }
   return INSTANCE
}

```
>İpucu: `.isInitialized` Kotlin özelliği, `lateinit` özelliğine (bu örnekte `INSTANCE`) bir değer atanmışsa `true`, aksi takdirde `false` döndürür.

Artık, `Room`'u kullanarak veritabanını uyguladınız. Bir sonraki görevde, bir repository pattern'ı kullanarak bu veritabanını nasıl kullanacağınızı öğreneceksiniz.


## <a name="d"></a>Aşama 4 : Repositoryler
## <a name="e"></a>Aşama 5 : Bir repository oluşturun
## <a name="f"></a>Aşama 6 : Bir refresh stratejisi kullanarak repository'yi entegre edin

