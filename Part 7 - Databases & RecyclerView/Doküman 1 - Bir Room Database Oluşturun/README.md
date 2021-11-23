# <a name="1"></a>Bir Room Database Oluşturun

- [Başlangıç uygulamasını indirin & inceleyin](#a)
- [SleepNight entity'sini oluşturun](#b)
- [DAO'yu oluşturun](#c)
- [Bir Room database oluşturun & test edin](#d)

Bu aşamada, bir uygulamanın uyku kalitesini takip eden veritabanı bölümünü oluşturacaksınız. Uygulama, zaman içinde uyku 
verilerini depolamak için bir veritabanı kullanır.

Uygulamanın, aşağıdaki şekilde gösterildiği gibi fragmentlarla temsil edilen iki ekranı vardır:

![image](https://developer.android.com/codelabs/kotlin-android-training-room-database/img/e28eb795b6812ee4.png)

Solda gösterilen ilk ekran, takibi başlatmak ve durdurmak için düğmelere sahiptir. Ekran, kullanıcının tüm uyku verilerini 
gösterir. Temizle düğmesi, uygulamanın kullanıcı için topladığı tüm verileri kalıcı olarak siler.

Sağda gösterilen ikinci ekran, uyku kalitesi derecelendirmesini seçmek içindir. Uygulamada, derecelendirme sayısal olarak temsil 
edilir. Geliştirme amacıyla, uygulama hem yüz simgelerini hem de bunların sayısal eşdeğerlerini gösterir.

Kullanıcı akışı aşağıdaki gibidir:

- Kullanıcı uygulamayı açar ve uyku izleme ekranı ile karşılaşır.
- Kullanıcı Başlat düğmesine dokunur. Bu, başlangıç saatini kaydeder ve görüntüler. Başlat düğmesi devre dışı bırakılır ve 
Durdur düğmesi etkinleştirilir.
- Kullanıcı Durdur düğmesine dokunur. Bu, bitiş saatini kaydeder ve uyku kalitesi ekranını açar.
- Kullanıcı bir uyku kalitesi simgesi seçer. Ekran kapanır ve izleme ekranı uyku bitiş süresini ve uyku kalitesini görüntüler. 
Durdur düğmesi devre dışı bırakılır ve Başlat düğmesi etkinleştirilir. Uygulama yeni bir gece için hazır.
- Veritabanında veri olduğunda Temizle düğmesi etkinleştirilir. Kullanıcı Temizle düğmesine dokunduğunda, tüm verileri onay 
alınmadan silinir; "Emin misiniz?" diye bir soru yoktur.

Bu uygulama, tam mimari bağlamında aşağıda gösterildiği gibi basitleştirilmiş bir mimari kullanır. Uygulama yalnızca 
şu bileşenleri kullanır:

- UI Controller (denetleyici)
- ViewModel & LiveData
- Bir Room database

![image](https://developer.android.com/codelabs/kotlin-android-training-room-database/img/2268f8ae35a8c715.png)

## <a name="a"></a>Aşama 1 : Başlangıç uygulamasını indirin & inceleyin

### Adım 1: Başlangıç uygulamasını indirin & çalıştırın

1. GitHub'dan TrackMySleepQuality-Starter <!-- link ekle --> uygulamasını indirin
2. Uygulamayı build edin ve çalıştırın. Uygulama, SleepTrackerFragment fragmentı için UI gösteriyor, ancak veri yok. 
Düğmeler dokunmalara yanıt vermiyor.

### Adım 2: Başlangıç uygulamasını inceleyin

>İpucu: Başlangıç uygulamanıza aşina olmak, herhangi bir sorunla karşılaşmanız durumunda onları tanımlamayı ve 
çözmeyi kolaylaştıracaktır.

1. Gradle dosyalarına bir göz atın:
  - **Project Gradle dosyası**  Proje düzeyindeki `build.gradle` dosyasında, kütüphane sürümlerini belirten değişkenlere dikkat edin. 
  Başlangıç uygulamasında kullanılan sürümler birlikte iyi çalışır ve bu uygulamayla iyi çalışır. Bu aşamayı bitirdiğinizde, Android 
  Studio sizden bazı sürümleri güncellemenizi isteyebilir. Uygulamada bulunan sürümleri güncellemek veya varolan sürümde kalmak
  size kalmış. "Garip" derleme hatalarıyla karşılaşırsanız, finalize çözüm uygulamasının <!-- linkle --> kullandığı kütüphane sürümlerinin 
  kombinasyonlarını kullanmayı deneyin.
  - **Module Gradle dosyası**   `Room` dahil olmak üzere tüm Android Jetpack kütüphaneleri için sağlanan dependencylere ve coroutines 
  dependencylerine dikkat edin.
2. Project penceresinde farklı paketlere dikkat edin. Uygulama, işlevsellik tarafından yapılandırılmıştır. Paketler, kod ekleyeceğiniz 
yer tutucu dosyaları içerir.
  - `Room` database ilgili tüm kodlar için `database` paketi.
  - `sleepquality` ve `sleeptracker` paketleri, her ekran için fragment, view model ve view model factory içerir.
3. `Util.kt' dosyasına bir göz atın. Bu dosya, uyku kalitesi verilerinin görüntülenmesine yardımcı olacak fonksiyonlar içerir. Bazı kodlar, 
daha sonra oluşturacağınız bir view modela referans verdiği için yoruml satırına alınmıştır.
4. `androidTest` klasörüne bir göz atın ve `SleepDatabaseTest.kt` dosyasına dikkat edin. Veritabanının amaçlandığı gibi çalıştığını doğrulamak 
için bu testi kullanacaksınız.

## <a name="b"></a>Aşama 2 : SleepNight entity'sini oluşturun

Android'de veriler, data classlarda temsil edilir. Bu verilere erişilebilir ve fonksiyon çağrıları kullanılarak değiştirilebilir. Ancak, veritabanı dünyasında, verilere erişmek ve bunları değiştirmek için entitylere ve sorgulara ihtiyacınız vardır.
- Bir *entity*, veritabanında saklanacak özellikleriyle birlikte bir nesneyi veya kavramı temsil eder. Uygulama kodumuzda, bir tablo tanımlayan bir entity sınıfına ihtiyacımız var ve bu sınıfın her instance'ı o tablodaki bir satırı temsil ediyor. Entity sınıfı, Room'a veritabanındaki bilgileri nasıl sunacağını ve bunlarla nasıl etkileşim kurmayı planladığını söyleyen mappinglere (eşlemelere) sahiptir. Uygulamanızda entity, bir uyku hakkında bilgi tutacak.
- Bir *sorgu*, bir veritabanı tablosundan veya tablo kombinasyonundan veri talebi ya da veriler üzerinde bir eylem gerçekleştirme isteğidir. Sık kullanılan sorgular, entityleri oluşturmak, okumak, güncellemek ve silmek içindir. Örneğin, kayıttaki tüm uyku gecelerini başlangıç zamanına göre sıralayarak okumak için bir sorgu yürütebilirsiniz.

Uygulamanızın kullanıcı deneyimi (diğer yaygın kullanım örneklerine benzer şekilde), bazı verilerin yerel olarak kalıcı hale getirilmesinden büyük ölçüde yararlanabilir. İlgili veri parçalarını önbelleğe almak (caching), bir kullanıcının çevrimdışı olsa bile uygulamanızın keyfini çıkarmasına izin verebilir. Uygulamanız bir sunucuya dayanıyorsa, önbelleğe alma, kullanıcıların çevrimdışıyken yerel olarak kalıcı içeriği değiştirmelerine olanak tanır. Uygulama yeniden bağlantı kurduğunda, önbelleğe alınan bu değişiklikler arka planda sorunsuz bir şekilde sunucuyla senkronize edilebilir.

`Room`, Kotlin data classlardan SQLite tablolarında depolanabilen varlıklara ve fonksiyon bildirimlerinden SQL sorgularına kadar tüm zor işleri yapar.

Her entity annotate edilmiş bir data class olarak ve bu entity ile olan etkileşimleri *data access object (veri erişim nesnesi [DAO])* adı verilen annotate edilmiş bir interface olarak tanımlamanız gerekir. `Room`, veritabanında tablolar oluşturmak ve veritabanı üzerinde işlem yapan sorgular oluşturmak için bu annotate sınıfları kullanır.

![image](https://developer.android.com/codelabs/kotlin-android-training-room-database/img/c4a598be115aa77a.png)

### Adım 1: SleepNight entity'sini oluşturun

Bu aşamada, bir veritabanı entity'sini temsil eden annotate edilmiş bir data class olarak bir gecelik uyku tanımlamalısınız.

Bir gecelik uyku için başlangıç saatini, bitiş saatini ve kalite derecelendirmesini kaydetmeniz gerekir.

Ve o geceyi benzersiz bir şekilde tanımlamak için bir ID'ye ihtiyaç vardır.

1. `database` paketindeki `SleepNight.kt` dosyasını bulun ve açın.
2. ID, başlangıç zamanı (milisaniye olarak), bitiş zamanı (milisaniye olarak) ve sayısal uyku kalitesi derecelendirmesi için parametrelerle `SleepNight` data classı oluşturun.
  - `sleepQuality` parametresine bir başlangıç değeri vermelisiniz, bu sebeple `-1` değerini atayın; bu kalite verisinin toplanmadığı anlamına gelecek.
  - Başlangıç saatine geçerli bir başlangıç değeri vermelisiniz. Milisaniye değerinde şimdiki zaman bunun için iyi bir seçenektir.
  - Bitiş saatine de bir başlangıç değer vermeniz gerekiyor. Henüz bir bitiş saatinin kaydedilmediğini belirtmek için başlangıç saatine ayarlayın.

```

data class SleepNight(
       var nightId: Long = 0L,
       val startTimeMilli: Long = System.currentTimeMillis(),
       var endTimeMilli: Long = startTimeMilli,
       var sleepQuality: Int = -1
)

```
3. Sınıf ifadesinden önce, sınıfı `@Entity` ile annotate edin. Bu annotation'ın farklı olası argümanları vardır. Varsayılan olarak (`@Entity` için  argüman yok), tablo adı sınıfla aynı olacaktır. Hadi, `daily_sleep_quality_table` şeklinde bir tablo adı kullanalım. `tableName` için bu argüman isteğe bağlıdır, ancak şiddetle tavsiye edilir. `@Entity` için dokümanlarda araştırabileceğiniz başka argümanlar da vardır. 

Android Studio tarafından istenirse, `Entity` ve diğer tüm annotationları `androidx` kütüphanesinden içe aktarın (import).

```

@Entity(tableName = "daily_sleep_quality_table")
data class SleepNight(...)

```

4. `nightId` özelliğini *primary key* olarak tanımlamak için, `nightId`'yi `@PrimaryKey` ile annotate edin. `autoGenerate` parametresini `true` yapın, böylece `Room` her entity için bir ID oluşturabilsin. Bu her gece için eşsiz bir ID olmasını garantileyecektir.

```

@PrimaryKey(autoGenerate = true)
var nightId: Long = 0L,...

```

5. Kalan özellikleri `@ColumnInfo` ile annotate edin. Özelliklerin isimlerini aşağıda gösterildiği gibi özelleştirebilirsiniz.

```

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "daily_sleep_quality_table")
data class SleepNight(
       @PrimaryKey(autoGenerate = true)
       var nightId: Long = 0L,

       @ColumnInfo(name = "start_time_milli")
       val startTimeMilli: Long = System.currentTimeMillis(),

       @ColumnInfo(name = "end_time_milli")
       var endTimeMilli: Long = startTimeMilli,

       @ColumnInfo(name = "quality_rating")
       var sleepQuality: Int = -1
)

```

6. Hata olmadığından emin olmak için kodunuzu build edin ve çalıştırın.

## <a name="c"></a>Aşama 3 : DAO'yu oluşturun

Bu aşamada, bir *data access object (veri erişim nesnesi [DAO])* tanımlarsınız. Android'de DAO, veritabanını eklemek, silmek ve güncellemek için kolay yöntemler sağlar.

Bir `Room` veritabanı kullandığınızda, kodunuzdaki Kotlin fonksiyonlarını tanımlayıp çağırarak veritabanını sorgularsınız. Bu Kotlin fonksiyonları, SQL sorgularıyla eşlenir (*map*lenir). Bu eşlemeleri, annotationları kullanarak bir DAO'da tanımlarsınız ve `Room` gerekli kodu oluşturur.

DAO'yu veritabanınıza ulaşmak için tanımlanan özel bir interface olarak düşünebilirsiniz.

`Room`, sık kullanılan `@Insert`, `@Delete` ve `@Update` gibi veritabanı operasyonları için kolaylaştırıcı annotationları size sağlar. Diğer her şey için `@Query` annotationı vardır. SQLite tarafından desteklenen herhangi bir sorguyu bu şekilde yazabilirsiniz.

Ek bir avantaj olarak, Android Studio'da sorgularınızı oluştururken, derleyici yazdığınız SQL sorgularınızı syntax hatalarına karşı kontrol eder.

Uyku geceleri için olan sleep-tracker veritabanı için aşağıdaki işlemleri yapabilmeniz gereklidir:

- Yeni gece ekleme (**Insert**)
- Varolan geceyi bitiş saatini ve kalite derecelendirmesini değiştirmek için güncelleme (**Update**)
- Anahtarına göre belirli bir geceyi çekme (**Get**)
- Görüntüleyebilmek için tüm geceleri çekme (**Get**)
- En son geceyi çekme (**Get**)
- Veritabanındaki tüm verileri silme (**Delete**)

### Adım 1: SleepDatabase DAO'yu Oluşturun

1. `database` paketindeki `SleepDatabaseDao.kt`'yi açın
2. `interface` `SleepDatabaseDao`'nun `@Dao` ile annotate edildiğine dikkat edin. Tüm DAOların `@Dao` anahtar kelimesi ile annotate edilmesi gereklidir.

```

@Dao
interface SleepDatabaseDao {}

```

3. Interface gövdesinin içine bir @Insert annotationı ekleyin. @Insert'in altına argüman olarak class SleepNight Entity'sinin instance'ını alan bir insert() fonksiyonu ekleyin.

İşte bu kadar. `Room`, `SleepNight`'ı veritabanına eklemek (insert) için gerekli kodu üretecektir. Kotlin kodunuzdan `insert()`'i çağırdığınızda, `Room`, entity'yi veritabanına eklemek için bir SQL sorgusu yürütür. (Not: Fonksiyona istediğiniz herhangi bir adı verebilirsiniz.)

```

@Insert
fun insert(night: SleepNight)

```

4. Bir `SleepNight` için `@Update` annotationı ile bir `update()` fonksiyonu ekleyin. Güncellenen entity, fonksiyona parametre olarak verilen ile aynı anahtara (primary key) sahip olacaktır. Entity'nin anahtar hariç diğer özelliklerinin bir kısmını veya tamamını güncelleyebilirsiniz.

```

@Update
fun update(night: SleepNight)

```

Kalan işlevler için herhangi bir kolaylaştırıcı annotation yoktur, bu nedenle `@Query` annotationını kullanmanız ve SQLite sorguları sağlamanız gerekir.

5. @Query annotationı ile bir Long key (anahtar) argümanını alan ve null yapılabilir bir SleepNight döndüren get() fonksiyonu ekleyin. Eksik bir parametre için bir hata göreceksiniz.

```

@Query
fun get(key: Long): SleepNight?

```

6. Sorgu, @Query annotationına bir string parametresi olarak sağlanır. Belirli bir SleepNight girdisinden tüm sütunları almak için bir SQLite sorgusu olan bir String parametresini @Query'ye ekleyin.
  - `daily_sleep_quality_table`'dan tüm kolonları seçin (`SELECT`)
  - `nightId` `:key` argümanı ile eşleşsin. (`WHERE`)

`:key`'ye dikkat edin. Fonksiyon içindeki argümanlara referans vermek için iki nokta notasyonunu kullanmalısınız.

```

("SELECT * from daily_sleep_quality_table WHERE nightId = :key")

```

7. daily_sleep_quality_table'dan her şeyi silebilmek (DELETE) için clear() fonksiyonu ve SQLite sorgusu içeren bir @Query daha ekleyin. Bu sorgu tablonun kendisini silmez.

@Delete annotation'ı bir öğeyi siler ve uyku gecelerinin bir listesini sağlayarak @Delete ile onları silebilirsiniz. Dezavantajı, tabloda ne olduğunu getirmeniz veya bilmeniz gerektiğidir. @Delete annotationı, belirli girdileri silmek için harikadır, ancak bir tablodaki tüm girdileri silmek için verimli değildir.

```

@Query("DELETE FROM daily_sleep_quality_table")
fun clear()

```

8. `getTonight()` fonksiyonuna bir `@Query` ekleyin. `getTonight()` tarafından döndürülen `SleepNight`'ı null yapılabilir yapın, böylece fonksiyon tablonun boş olduğu durumlarda çalışmaya devam edebilir. (Tablo başlangıçta ve veriler temizlendikten sonra  boş olacak.)

Veritabanından "tonight"ı (bu gece) almak için, `nightId`'ye göre azalan sırada (descending) sıralanan bir sonuç listesinin ilk öğesini döndüren bir SQLite sorgusu yazın. Yalnızca bir öğe döndürmek için `LIMIT 1` kullanın.

```

@Query("SELECT * FROM daily_sleep_quality_table ORDER BY nightId DESC LIMIT 1")
fun getTonight(): SleepNight?

```

9. `getAllNights()` fonksiyonuna bir `@Query` ekleyin:
- SQLite sorgusunun `daily_sleep_quality_table`'daki tüm sütunları azalan sırada döndürmesini sağlayın.
- `getAllNights()`'ın `SleepNight` entitylerini bir `LiveData` listesi olarak döndürmesini sağlayın. `Room` bu `LiveData`'yı sizin için güncel tutacaktır, bu verileri yalnızca bir kez explicit olarak almanız gerektiği anlamına gelir.
- `LiveData`'yı `androidx.lifecycle.LiveData`'dan import etmeniz gerebilir.

```

@Query("SELECT * FROM daily_sleep_quality_table ORDER BY nightId DESC")
fun getAllNights(): LiveData<List<SleepNight>>

```

10. Görünür bir değişiklik görmeseniz de, hata olmadığından emin olmak için uygulamanızı çalıştırın.


## <a name="d"></a>Aşama 4 : Bir Room database oluşturun & test edin

Bu aşamada, bir önceki aşamada oluşturduğunuz `Entity` ve DAO'yu kullanan bir `Room` veritabanı oluşturacaksınız.

`@Database` ile annotate edilmiş, abstract bir database holder class oluşturmalısınız. Bu classın, veritabanı yoksa veritabanının bir instance'ını oluşturan veya mevcut bir veritabanına referans döndüren bir metodu vardır.

Bir Room veritabanını oluşturma kısmı biraz karmaşıktır, bu nedenle koda başlamadan önce genel süreç nasıl bir bakalım:

- `RoomDatabase'i extend eden` bir `public abstract` class oluşturun. Bu class bir database holder olarak hareket edecektir. Class abstract olmalıdır, çünkü `Room` sizin için implementation'ı yaratacaktır.
- Classı `@Database` ile annotate edin. Argümanlarda, veritabanı için entityleri bildirin ve versiyon numarasını girin.
- Bir `companion` object içinde, `SleepDatabaseDao`'yu döndüren bir abstract metot ya da property (özellik) tanımlayın. `Room` sizin için metodun body'sini oluşturacaktır.
- Tüm uygulamanız için bir tane `Room` veritabanı instance'ına ihtiyacınız vardır, bu yüzden `RoomDatabase`'i bir singleton yapın.
- Eğer veritabanı mevcut değilse, veritabanını oluşturmak için `Room`'un database builder'ını kullanın. Aksi takdirde, mevcut veritabanını döndürün.

>İpucu: Bu kod, herhangi bir `Room` veritabanı için hemen hemen aynı olacaktır, bu nedenle bu kodu şablon olarak kullanabilirsiniz.

### Adım 1: Veritabanını oluşturun


### Adım 2: SleepDatabase'i test edin

