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

## <a name="d"></a>Aşama 4 : Bir Room database oluşturun & test edin


