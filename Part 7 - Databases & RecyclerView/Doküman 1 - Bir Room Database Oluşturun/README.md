# <a name="1"></a>Bir Room Database Oluşturun

- [Başlangıç uygulamasını indirin & inceleyin](#a)
- [SleepNight entity'sini oluşturun](#b)
- [DAO'yu oluşturun](#c)

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

## <a name="c"></a>Aşama 3 : DAO'yu oluşturun



