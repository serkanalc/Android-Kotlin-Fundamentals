# <a name="1"></a>Room ile Coroutineleri Kullanın

- [Başlangıç kodunu inceleyin](#a)
- [ViewModel ekleyin](#b)
- [Coroutineler](#c)
- [Data toplayın & görüntüleyin](#d)

Bu aşamada, TrackMySleepQuality uygulamasının view model'ını, coroutinelerini ve veri görüntüleme bölümlerini oluşturacaksınız.

Uygulamanın, aşağıdaki şekilde gösterildiği gibi fragmentlarla temsil edilen iki ekranı vardır.

![image](https://lh4.googleusercontent.com/WTsRlkiNXXQ7fe3myuNV8SigzmETej7zu35m_x7VEPJNGUwKLNrSFg1k7RPur1Y6tvMcInSIKbeHysH-AvR2MYoJCkOsHWBpgyQ6ut4Bvmxa5tpX9NVMIv7lc-7gDLTw4dUSV7wFkQ)

Solda gösterilen ilk ekranda, izlemeyi başlatmak ve durdurmak için düğmeler vardır. Ekran, kullanıcının tüm uyku verilerini gösterir. **Clear** düğmesi, uygulamanın kullanıcı için topladığı tüm verileri kalıcı olarak siler.

Sağda gösterilen ikinci ekran, uyku kalitesi derecelendirmesini seçmek içindir. Uygulamada, derecelendirme sayısal olarak temsil edilir. Geliştirme amacıyla, uygulama hem yüz simgelerini hem de bunların sayısal eşdeğerlerini gösterir.

Kullanıcı akışı aşağıdaki gibidir:

- Kullanıcı uygulamayı açar ve uyku izleme ekranı ile karşılaşır.
- Kullanıcı **Start** düğmesine dokunur. Bu, başlangıç saatini kaydeder ve görüntüler. **Start** düğmesi devre dışı bırakılır ve **Stop** düğmesi etkinleştirilir.
- Kullanıcı **Stop** düğmesine dokunur. Bu, bitiş saatini kaydeder ve uyku kalitesi ekranını açar.
- Kullanıcı bir uyku kalitesi simgesi seçer. Ekran kapanır ve izleme ekranı uyku bitiş süresini ve uyku kalitesini görüntüler. **Stop** düğmesi devre dışı bırakılır ve **Start** düğmesi etkinleştirilir. Uygulama başka bir gece için hazırdır.
- Veritabanında veri olduğunda **Clear** düğmesi etkinleştirilir. Kullanıcı **Clear** düğmesine dokunduğunda, tüm verileri soru sorulmadan silinir; "Emin misiniz?" diye bir mesaj olmayacaktır.

Bu uygulama, tam mimari bağlamında aşağıda gösterildiği gibi basitleştirilmiş bir mimari kullanır. Uygulama yalnızca aşağıdaki bileşenleri kullanır:

- UI Controller (UI denetleyicisi)
- View model & `LiveData`
- Bir Room veritabanı

![image](https://lh5.googleusercontent.com/Q7-Cq9-Y4h13EbiYcZR---sZ74dgkqejB699RY7cCIxGresnPfPKHqjX8HsTVB24r-c3gqRAgWUrERqjraXtQPpdCZ-ZeJhtUHw9s2-j39pW9Cerk0Qethe_Pc3oOy0hVl-q9i47Xg)


## <a name="a"></a>Aşama 1 : Başlangıç kodunu inceleyin

Bu aşamada, biçimlendirilmiş uyku izleme verilerini görüntülemek için bir `TextView` kullanacaksınız. (Bu, son UI değildir. UI'ı başka bir codelab'de iyileştireceksiniz.)

Önceki codelab'de oluşturduğunuz TrackMySleepQuality uygulamasıyla devam edebilir veya bu codelab için [başlangıç uygulamasını indirebilirsiniz.] <!-- linkini koy -->

### Adım 1: Başlangıç uygulamasını indirin & çalıştırın

1. [TrackMySleepQuality-Coroutines-Starter] uygulamasını GitHub'dan indirin. <!-- linkini koy -->
2. Uygulamayı build edin ve çalıştırın. Uygulama `SleepTrackerFragment` fragment'ı için UI'yı gösterecektir, fakat bir data olmayacaktır. Butonlara dokunmalara cevap vermeyecektir.


### Adım 2: Kodu inceleyin

Bu codelab için başlangıç kodu, [Bir Room Database Oluştur codelab](https://github.com/serkanalc/Android-Kotlin-Fundamentals/tree/main/Part%207%20-%20Databases%20%26%20RecyclerView/Dok%C3%BCman%201%20-%20Bir%20Room%20Database%20Olu%C5%9Fturun)'i için çözüm koduyla aynıdır.

1. **res/layout/activity_main.xml**'i açın. Bu layout'ta `nav_host_fragment` fragment'ı bulunur. Ayrıca, `<merge>` tag'ine (etiketine) dikkat edin. **Merge** tag'i, layoutlar eklenirken gereksiz layoutları ortadan kaldırmak için kullanılabilir ve bunu kullanmak iyi bir fikirdir. Gereksiz bir layout örneği, sistemin LinearLayout'u kaldırabileceği ConstraintLayout > LinearLayout > TextView olabilir . Bu tür bir optimizasyon, view hiyerarşisini basitleştirebilir ve uygulama performansını iyileştirebilir. 
2. **navigation** dosyasındaki **navigation.xml**'i açın. İki fragment'ı ve bunları birleştiren navigation actionlarını görebilirsiniz.
3. **layout** dosyasındaki, **fragment_sleep_tracker.xml**'i açın ve XML layout'unu görebilmek için **Code** görünümünü açın. Şunlara dikkat edin:
- Layout verileri, data binding'i etkinleştirmek için `<layout>` elementi ile çevrelenmiştir.
- `ConstraintLayout` ve diğer viewlar `<layout>` elementi içine yerleştirilmiştir.
- Dosyanın bir yer tutucu `<data>` tag'i vardır.

Başlangıç uygulamasında ayrıca UI için boyutlar (dimensions), renkler (colors) ve stil (styling) sağlar. Uygulama bir `Room` veritabanı, bir DAO ve bir `SleepNight` entity'si içerir. Önceki "Bir Room Database Oluştur" codelab'ini tamamlamadıysanız, kodun bu yönlerini kendi başınıza keşfettiğinizden emin olun.

## <a name="b"></a>Aşama 2 : ViewModel ekleyin

Artık bir veritabanınız ve bir UI'ınız olduğuna göre, verileri toplamanız, verileri veritabanına eklemeniz ve verileri görüntülemeniz gereklidir. Tüm bu işler view model'da yapılır. Sleep-tracker view modeliniz, buton tıklamalarını işleyecek, DAO aracılığıyla veritabanıyla etkileşime girecek ve `LiveData` aracılığıyla UI'ya veri sağlayacaktır. Tüm veritabanı işlemlerinin main UI thread'inden çalıştırılmalıdır ve bunu coroutineleri kullanarak yapacaksınız.

### Adım 1: SleepTrackerViewModel ekleyin

1. **sleeptracker** paketindeki, **SleepTrackerViewModel.kt**'yi açın.
2. Başlangıç uygulamasında sizin için sağlanan ve aşağıda da gösterilen `SleepTrackerViewModel` class'ını inceleyin. Class'ın `AndroidViewModel`'ı extend ettiğini unutmayın. Bu class, `ViewModel` ile aynıdır, ancak uygulama context'ini bir constructor parametresi olarak alır ve onu bir özellik olarak kullanılabilir hale getirir. Buna daha sonra ihtiyacınız olacak.

```

class SleepTrackerViewModel(
       val database: SleepDatabaseDao,
       application: Application) : AndroidViewModel(application) {
}

```

### Adım 2: SleepTrackerViewModelFactory Ekleyin

1. **sleeptracker** paketinde, **SleepTrackerViewModelFactory.kt**'yi açın.
2. Aşağıda gösterilen, factory için size verilen kodu inceleyin:

```

class SleepTrackerViewModelFactory(
       private val dataSource: SleepDatabaseDao,
       private val application: Application) : ViewModelProvider.Factory {
   @Suppress("unchecked_cast")
   override fun <T : ViewModel?> create(modelClass: Class<T>): T {
       if (modelClass.isAssignableFrom(SleepTrackerViewModel::class.java)) {
           return SleepTrackerViewModel(dataSource, application) as T
       }
       throw IllegalArgumentException("Unknown ViewModel class")
   }
}

```

Aşağıdakilere dikkat edin:

- Size sağlanan `SleepTrackerViewModelFactory`, `ViewModel` ile aynı argümanı alır ve `ViewModelProvider.Factory`'yi extend eder.
- Factory içindeki kod, herhangi bir class türünü argüman olarak alan ve bir `ViewModel` döndüren `create()` fonksiyonunu override eder.
- `create()`'in body'sindeki kod, bir `SleepTrackerViewModel` class'ının mevcut olup olmadığını kontrol eder ve varsa bunun bir instance'ını döndürür. Aksi takdirde, kod bir exception atar.

>İpucu: Bu çoğunlukla boilerplate koddur, bu nedenle gelecekteki view model factoryleri için kodu yeniden kullanabilirsiniz.

### Adım 3: SleepTrackerFragment'ı güncelleyin

1. SleepTrackerFragment.kt'de uygulama contextine bir referans alın. Referansı, binding'in altına onCreateView() içine koyun. View-model factory provider'a geçirmek için bu fragment'ın eklendiği uygulamaya bir referansa ihtiyacınız var.

`requireNotNull` Kotlin fonksiyonu, [value](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/require-not-null.html#kotlin%24requireNotNull%28kotlin.requireNotNull.T%29%2Fvalue) (değer) `null` ise bir [`IllegalArgumentException`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-illegal-argument-exception/#kotlin.IllegalArgumentException) oluşturur.

```

val application = requireNotNull(this.activity).application

```

2. DAO'ya bir referans aracılığıyla veri kaynağınıza bir referansa ihtiyacınız var. `onCreateView()` içinde, `return`'den önce bir `dataSource` tanımlayın. Veritabanının DAO'suna referans almak için `SleepDatabase.getInstance(application).sleepDatabaseDao`'yu kullanın.

```

val dataSource = SleepDatabase.getInstance(application).sleepDatabaseDao

```

3. onCreateView() içinde, return'den önce viewModelFactory'nin bir instance'ını oluşturun. DataSource ve uygulamayı buna iletmeniz gereklidir.

```

val viewModelFactory = SleepTrackerViewModelFactory(dataSource, application)

```

4. Artık bir factory'niz olduğuna göre, `SleepTrackerViewModel`'e bir referans alın. `SleepTrackerViewModel::class.java` parametresi, bu nesnenin runtime Java class'ını ifade eder.

```

val sleepTrackerViewModel =
       ViewModelProvider(
               this, viewModelFactory).get(SleepTrackerViewModel::class.java)

```

5. Bitmiş kodunuz şöyle görünmelidir:

```

// Create an instance of the ViewModel Factory.
val dataSource = SleepDatabase.getInstance(application).sleepDatabaseDao
val viewModelFactory = SleepTrackerViewModelFactory(dataSource, application)

// Get a reference to the ViewModel associated with this fragment.
val sleepTrackerViewModel =
       ViewModelProvider(
               this, viewModelFactory).get(SleepTrackerViewModel::class.java)

```

İşte şimdiye kadarki onCreateView() metodu:

```

override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Get a reference to the binding object and inflate the fragment views.
        val binding: FragmentSleepTrackerBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_sleep_tracker, container, false)

        val application = requireNotNull(this.activity).application

        val dataSource = SleepDatabase.getInstance(application).sleepDatabaseDao

        val viewModelFactory = SleepTrackerViewModelFactory(dataSource, application)

        val sleepTrackerViewModel =
                ViewModelProvider(
                        this, viewModelFactory).get(SleepTrackerViewModel::class.java)

        return binding.root
    }
    
```

### Adım 4: View model için data binding ekleyin

Temel `ViewModel` yerindeyken, `ViewModel`'i UI'ya bağlamak için `SleepTrackerFragment`'ta data binding'i ayarlamayı tamamlamanız gerekir.

`fragment_sleep_tracker.xml` layout dosyasında:

1. `<data>` bölümünün içinde, `SleepTrackerViewModel` sınıfına referans veren bir `<variable>` oluşturun.

```

<data>
   <variable
       name="sleepTrackerViewModel"
       type="com.example.android.trackmysleepquality.sleeptracker.SleepTrackerViewModel" />
</data>

```

SleepTrackerFragment.kt içinde:

1. Geçerli activity'yi, binding'in lifecycle owner'ı (yaşam döngüsü sahibi) olarak ayarlayın. Bu kodu `onCreateView()` metodunun içine, `return` ifadesinden önce ekleyin:

```

binding.setLifecycleOwner(this)

```

2. `sleepTrackerViewModel` binding değişkenini `sleepTrackerViewModel`'a atayın. Bu kodu `onCreateView()` içine, `SleepTrackerViewModel`'i oluşturan kodun altına koyun:


```

binding.sleepTrackerViewModel = sleepTrackerViewModel

```

3. Binding nesnesini yeniden oluşturmanız gerektiğinden bir hata görebilirsiniz. Hatadan kurtulmak için projeyi temizleyin ve yeniden oluşturun (Clean and reubuild project).
4. Son olarak, her zaman olduğu gibi, kodunuzun hatasız build ettiğinden ve çalıştığından emin olun.

## <a name="c"></a>Aşama 3 : Coroutineler

>Not: Bir uygulamanın devam edebilmesi için bir görevin tamamlanmasını beklemesi gerekiyorsa, uygulamanın normal şekilde yürütülmesi engellenebilir. Örneğin, büyük bir dosyayı okumak veya uzun bir veritabanı çağrısı yapmak, genel uygulamanın yürütülmesini engelleyebilecek veya "block" edebilecek görevler olabilir. Bu, yalnızca uygulamanın kullanıcıya yanıt verme hızını azaltmakla kalmaz, aynı zamanda donanımı verimli bir şekilde kullanmaz. Main thread'i engellemeden uzun süredir devam eden (long-running) görevleri yürütmek için bir pattern, [callbackleri](https://en.wikipedia.org/wiki/Callback_(computer_programming)) kullanmaktır. Bu kullanışlı bir pattern'dır, ancak bazı dezavantajları vardır. Bu kavramlara giriş için [Multi-threading & callbacks primer](https://developer.android.com/courses/extras/multithreading)'a bakın.

Kotlin'de coroutineler, long-running (uzun süredir devam eden) görevleri callbackler yerine zarif ve verimli bir şekilde ele almanın yoludur. Kotlin coroutines, callbacks tabanlı kodu sequential (sıralı) koda dönüştürmenize izin verir. Sırayla yazılan kodun okunması ve bakımı genellikle daha kolaydır. Callbacklerin aksine, coroutineler exceptionlar gibi değerli dil özelliklerini güvenle kullanabilir. En önemlisi de, coroutineler daha yüksek derecede sürdürülebilirlik ve esnekliğe sahiptir. Sonunda, coroutineler ve callbackler aynı işlevi gerçekleştirir: her ikisi de bir uygulama içinde potansiyel olarak long-running asenkron görevleri işlemenin yollarıdır.

![thread image](https://developer.android.com/codelabs/kotlin-android-training-coroutines-and-room/img/952b19bd8601a7a5.png)

Coroutineler aşağıdaki özelliklere sahiptir:

- Coroutineler asenkron ve non-blocking'dir.
- Coroutineler, asenkron kodu sequential hale getirmek için suspend fonksiyonlarını kullanır.

#### Coroutineler asenkrondur.

Bir coroutine, programınızın ana yürütme adımlarından bağımsız olarak çalışır. Bu paralel veya ayrı bir işlemcide olabilir. Ayrıca, uygulamanın geri kalanı girdi beklerken, araya gizlice işleme yapma sokmuş olursunuz. Asenkron programlamanın önemli yönlerinden biri, siz açıkça (explicitly) beklemeden sonucun hemen kullanılabilir olmasını umamayacak olmanızdır.

Örneğin, araştırma gerektiren bir sorunuz olduğunu ve kibarca bir meslektaşınızdan cevabı bulmasını rica ettiğinizi varsayalım. Meslektaşınız daha sonra kendi başına üzerinde çalışmaya başlar. Meslektaşınız bir cevapla dönene kadar cevaba bağlı olmayan ilgisiz diğer işleri yapmaya devam edebilirsiniz. Bu örnekte, meslektaşınız işi asenkrın olarak "ayrı bir thread'de" yapıyor.

#### Coroutineler non-blocking'dir

Non-blocking, bir coroutine'in main veya UI thread'inin ilerlemesini bloke etmediği veya engellemediği anlamına gelir. Böylece, main thread'de çalıştırılan UI etkileşimi her zaman önceliğe sahip olduğundan, coroutinelerle kullanıcılar mümkün olan en sorunsuz deneyimi yaşayabilirler.

#### Coroutineler asenkron kodu sequential hale getirmek için suspend fonksiyonlarını kullanır

`suspend` anahtar sözcüğü, Kotlin'in bir fonksiyonu veya fonksiyon türünü coroutineler tarafından kullanılabilir olarak işaretleme yöntemidir. Bir coroutine, `suspend` ile işaretlenmiş bir fonksiyonu çağırdığında, fonksiyon normal bir fonksiyon çağrısı gibi dönene kadar onu bloke etmek yerine, coroutine, sonuç hazır olana kadar yürütmeyi suspend eder (askıya alır). Ardından coroutine, sonuçla birlikte kaldığı yerden devam eder.

Coroutine suspend edilir ve bir sonuç beklerken, üzerinde çalıştığı thread'in blokesini kaldırır. Bu şekilde, diğer fonksiyonlar ve coroutineler çalışabilir.

`suspend` anahtar sözcüğü, kodun üzerinde çalıştığı thread'i belirtmez. Bir suspend fonksiyonu, bir arka plan thread'inde veya maşn thread'de çalışabilir.

>İpucu: *Bloke etme* ve *suspend etme* arasındaki fark, bir thread engellenirse başka bir iş olmamasıdır. Thread suspend edilirse, sonuç elde edilene kadar başka işler yapılır.

![image suspend and blocking](https://developer.android.com/codelabs/kotlin-android-training-coroutines-and-room/img/ce77d98e12909f3e.png)

Kotlin'de coroutineleri kullanmak için üç şeye ihtiyacınız var
- Bir job
- Bir dispatcher
- Bir scope

**Job**: Temel olarak, bir job iptal edilebilecek herhangi bir şeydir. Her coroutine'in bir job'ı vardır ve job'ı coroutine'i iptal etmek için kullanabilirsiniz. Joblar, parent-child (ebeveyn-çocuk) hiyerarşileri halinde düzenlenebilir. Bir parent job'ı iptal etmek, job'ın tüm childrenlarını hemen iptal eder; bu, her bir coroutine'i manuel olarak iptal etmekten çok daha uygundur.

**Dispacther**: Dispatcher, çeşitli threadler üzerinde çalışmak için coroutineler gönderir. Örneğin, `Dispatchers.Main` görevleri main thread'de çalıştırır ve `Dispatchers.IO`, I/O görevlerini bloke eden yükü paylaşılan bir thread havuzuna aktarır.

**Scope**: Bir coroutine *scope*'u, coroutine'in çalıştığı context'i tanımlar. Scope, bir coroutine'in job'ı ve dispatcherları hakkındaki bilgileri birleştirir. Scopelar coroutineleri takip eder. Bir coroutine'i başlattığınızda, bu "bir scope'tadır", yani hangi scope'un coroutine'i izleyeceğini belirttiğiniz anlamına gelir.

#### Mimari bileşenler (Architecture components) ile Kotlin coroutineler

Bir [`CoroutineScope`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-coroutine-scope/), tüm ecoroutinelerinizi takip eder ve coroutinelerinizin ne zaman çalışması gerektiğini yönetmenize yardımcı olur. Ayrıca, içinde başlatılan tüm coroutineleri da iptal edebilir. Her asenkron işlem veya coroutine, belirli bir `CoroutineScope` içinde çalışır.

[Mimari bileşenler](https://developer.android.com/topic/architecture?authuser=1), uygulamanızdaki mantıksal scopelar için birinci sınıf coroutine desteği sağlar. Mimari bileşenler, uygulamanızda kullanabileceğiniz aşağıdaki yerleşik scopeları tanımlar. Yerleşik coroutine scopeları, karşılık gelen her Mimari bileşeni için [KTX extensionlarındadır](https://developer.android.com/kotlin/ktx?authuser=1). Bu scopeları kullanırken uygun bağımlılıkları (dependencies) eklediğinizden emin olun.

- [`ViewModelScope`](https://developer.android.com/topic/libraries/architecture/coroutines?authuser=1#viewmodelscope)
- [`LifecycleScope`](https://developer.android.com/topic/libraries/architecture/coroutines?authuser=1#lifecyclescope) 
- [`liveData`](https://developer.android.com/topic/libraries/architecture/coroutines?authuser=1#livedata)


[`ViewModelScope`](https://developer.android.com/topic/libraries/architecture/coroutines?authuser=1#viewmodelscope): Uygulamanızdaki her [`
ViewModel`](https://developer.android.com/topic/libraries/architecture/viewmodel?authuser=1) için bir `
ViewModelScope` tanımlanır. Bu scope'ta başlatılan herhangi bir coroutine, `ViewModel` temizlenirse otomatik olarak iptal edilir. Bu codelab'de, veritabanı işlemlerini başlatmak için `ViewModelScope`'u kullanacaksınız.

#### Room & Dispatcher

Bir veritabanı işlemi gerçekleştirmek için Room kütüphanesini kullanırken, Room bir arka plan thread'inde veritabanı işlemlerini gerçekleştirmek için bir `Dispatchers.IO` kullanır. Açıkça (explicitly) herhangi bir `Dispacther` belirtmeniz gerekmez. Room bunu sizin için yapacaktır.

## <a name="d"></a>Aşama 4 : Data toplayın & görüntüleyin


Kullanıcının uyku verileriyle aşağıdaki şekillerde etkileşime girmesini istiyorsunuz:

- Kullanıcı **Start** butonuna dokunduğunda, uygulama yeni bir uyku gecesi oluşturur ve uyku gecesini veritabanında saklar.
- Kullanıcı **Stop** butonuna dokunduğunda, uygulama geceyi bir bitiş saati ile günceller.
- Kullanıcı **Clear** butonuna dokunduğunda, uygulama veritabanındaki verileri siler.

Bu veritabanı işlemleri uzun zaman alabilir, bu nedenle ayrı bir thread üzerinde çalışmalıdırlar.

### Adım 1: DAO fonksiyonlarını suspend fonksiyonu olarak iaşretleyin

`SleepDatabaseDao.kt`'de, fonskiyonları suspend etmek için kolaylaştırıcı metotları değiştirin.

1. `database/SleepDatabaseDao.kt`'yi açın, Room zaten LiveData döndüren o spesifik @Query için bir arka plan thread'i kullandığından, `getAllNights()` hariç tüm fonksiyonlara suspend anahtar kelimesini ekleyin. Tamamlanmış `SleepDatabaseDao` class'ı şöyle gözükecektir:

```

@Dao
interface SleepDatabaseDao {

   @Insert
   suspend fun insert(night: SleepNight)

   @Update
   suspend fun update(night: SleepNight)

   @Query("SELECT * from daily_sleep_quality_table WHERE nightId = :key")
   suspend fun get(key: Long): SleepNight?

   @Query("DELETE FROM daily_sleep_quality_table")
   suspend fun clear()

   @Query("SELECT * FROM daily_sleep_quality_table ORDER BY nightId DESC LIMIT 1")
   suspend fun getTonight(): SleepNight?

   @Query("SELECT * FROM daily_sleep_quality_table ORDER BY nightId DESC")
   fun getAllNights(): LiveData<List<SleepNight>>
}

```

### Adım 2: Database işlemleri için coroutineleri ayarlayın

Sleep Tracker uygulamasındaki **Start** butonuna dokunulduğunda, yeni bir `SleepNight` instance'ı oluşturmak ve bu instance'ı veritabanında depolamak için `SleepTrackerViewModel`'daki bir fonksiyonu çağırmak istiyorsunuz.

Butonlardan herhangi birine dokunmak, `SleepNight` oluşturma veya güncelleme gibi bir veritabanı işlemini tetikler. Veritabanı işlemleri biraz zaman alabileceğinden, uygulamanın butonları için tıklama işleyicilerini (handlers) uygulamak için coroutineleri kullanırsınız.

1. Uygulama seviyesindeki `build.gradle` dosyasını açın. Dependencies bölümünün altında, sizin için eklenen aşağıdaki dependencylere (bağımlılıklara) ihtiyacınız var.

```

implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0"

// Kotlin Extensions and Coroutines support for Room
implementation "androidx.room:room-ktx:$room_version"

```

2. `SleepTrackerViewModel.kt` dosyasını açın
3. Geçerli geceyi tutmak için `tonight` adlı bir değişken (variable) tanımlayın. Değişkeninizi `MutableLiveData` yapın, çünkü verileri gözlemleyebilmeniz ve değiştirebilmeniz gereklidir.

```

private var tonight = MutableLiveData<SleepNight?>()

```

4. `tonight` değişkenine mümkün olan en kısa sürede başlangıç değerini vermek için `tonight` tanımının altında bir `init` bloğu oluşturun ve `initializeTonight()`'ı çağırın. Bir sonraki adımda `initializeTonight()`'ı tanımlayacaksınız.

```

init {
   initializeTonight()
}

```

5. `init` bloğunun altında `initializeTonight()`'ı çağırın. `ViewModelScope`'da bir coroutine başlatmak için `viewModelScope.launch`'ı kullanın. Kıvrımlı parantezlerin içinde, `getTonightFromDatabase()`'i çağırarak veritabanından `tonight` için değeri alın ve değeri `tonight.value`'ya atayın. Bir sonraki adımda `getTonightFromDatabase()`'i tanımlayacaksınız.

`launch` için kıvrımlı parantezlerin kullanımına dikkat edin. İsimsiz bir fonksiyon olan bir lambda ifadesi tanımlıyacaklar. Bu örnekte, `launch` coroutine builder'a bir lambda gönderiyorsunuz. Bu builder bir coroutine oluşturur ve bu lambda'nın yürütülmesini ilgili dispatcher'a atar.

```

private fun initializeTonight() {
   viewModelScope.launch {
       tonight.value = getTonightFromDatabase()
   }
}

```  

6. `getTonightFromDatabase()`'i uygulayın. Başlatılmış bir `SleepNight` yoksa, null yapılabilir bir SleepNight döndüren, bir `private suspend` fonksiyonu olarak tanımlayın. Bu sizi bir hatayla karşı karşıya bırakır, çünkü fonksiyonun bir şey döndürmesi gerekir.

```

private suspend fun getTonightFromDatabase(): SleepNight? { }


```

7. `getTonightFromDatabase()` fonksiyonu body'si içinde, `tonight`'ı (en yeni gece) veritabanından alın. Başlangıç ve bitiş saatleri aynı değilse, yani gece zaten tamamlanmışsa, `null` değerini döndürün. Aksi takdirde, geceyi döndürün.

```

var night = database.getTonight()
       if (night?.endTimeMilli != night?.startTimeMilli) {
           night = null
       }
       return night

```

Tamamlanan `getTonightFromDatabase()` `suspend` fonksiyonunuz şöyle görünmelidir. Daha fazla hata olmamalıdır.

```

private suspend fun getTonightFromDatabase(): SleepNight? {
    var night = database.getTonight()
    if (night?.endTimeMilli != night?.startTimeMilli) {
        night = null
    }
    return night
}

```

### Adım 3: Start butonu için tıklama handler'ını (işleyicisini) ekleyin

Artık **Start** butonunun tıklama handler'ı olan `onStartTracking()`'i uygulayabilirsiniz. Yeni bir `SleepNight` oluşturmanız, veritabanına eklemeniz ve `tonight`'a atamanız gereklidir. `onStartTracking()`'in yapısı `initializeTonight()`'a benzer olacaktır.

1. `SleepTrackerViewModel.kt`'de, `onStartTracking()` için fonksiyon tanımıyla başlayın. Tıklama handlerlarını `getTonightFromDatabase()`'in altına koyabilirsiniz.

```

fun onStartTracking() {}

```

2. `onStartTracking()` içinde, `viewModelScope` içinde bir coroutine başlatın, çünkü bu sonuca devam etmek ve UI'yi güncellemek için ihtiyacınız var.

```

viewModelScope.launch {}

```

4. Coroutine launch içinde, geçerli saati başlangıç zamanı olarak yakalayan yeni bir `SleepNight` oluşturun.

```

val newNight = SleepNight()

```

4. Hala coroutine launch içinde, veritabanına `newNight` eklemek için `insert()`'i çağırın. Bu `insert()` suspend fonksiyonunu henüz tanımlamadığınız için bir hata göreceksiniz. Bunun, `SleepDatabaseDAO.kt`'deki aynı ada sahip metotla aynı `insert()` olmadığına dikkat edin.

```

insert(newNight)

```

5. Yine coroutine launch içinde, `tonight`'ı güncelleyin.

```

tonight.value = getTonightFromDatabase()

```

6. `onStartTracking()`'in altında, `insert()`'i, argümanı olarak bir `SleepNight` alan bir `private suspend` fonksiyonu olarak tanımlayın.

```

private suspend fun insert(night: SleepNight) {}

```

7. `insert()` metodu içinde, veritabanına geceyi eklemek için DAO'yu kullanın.

```

       database.insert(night)

```

Room içeren bir coroutine'in `Dispatchers.IO` kullandığını unutmayın, o nedenle bu main thread'de yürütülmeyecektir.

8. `fragment_sleep_tracker.xml` layout dosyasında, daha önce kurduğunuz data binding büyüsünü kullanarak `onStartTracking()` için tıklama handler'ını `start_button`'a ekleyin. `@{() -> `fonksiyon gösterimi, hiçbir argüman almayan ve `sleepTrackerViewModel`'deki tıklama handler çağıran bir lambda fonksiyonu oluşturur.

```

android:onClick="@{() -> sleepTrackerViewModel.onStartTracking()}"

```

9. Uygulamanızı build edin ve çalıştırın. **Start** butonuna tıklayın. Bu eylem veri oluşturur, ancak henüz hiçbir şey göremezsiniz. Bir sonraki adımda bunu düzelteceksiniz.

>Önemli: Şimdi bir pattern fark ediyor olmalısınız:
>1. Main UI thread'de çalışan bir coroutine başlatın, çünkü bu coroutine'in sonucu UI'da görüntülenenleri etkiler. Aşağıdaki örnekte gösterildiği gibi, ViewModel'in `viewModelScope` özelliği aracılığıyla bir ViewModel'in `CoroutineScope`'una erişebilirsiniz:
>2. Sonucu beklerken UI thread'i engellememeniz için long-running işi yapmak için bir suspend fonksiyonu çağırın.
>3. Long-running çalışmanın sonucu UI'yı etkileyebilir, ancak çalışması UI'dan bağımsızdır. Verimlilik için I/O dispatcher'a geçin (Room kodunu sizin için oluşturur). I/O dispatcher, bu tür işlemler için optimize edilmiş ve ayrılmış bir thread havuzu kullanır.
>4. Ardından işi yapmak için long-running işlevi çağırın.
>Pattern aşağıda gösterilmiştir.


#### Room olmadan

```

fun someWorkNeedsToBeDone {
   viewModelScope.launch {
        suspendFunction()
   }
}

suspend fun suspendFunction() {
   withContext(Dispatchers.IO) {
       longrunningWork()
   }
}

```

#### Room kullanarak

```

// Using Room
fun someWorkNeedsToBeDone {
   viewModelScope.launch {
        suspendDAOFunction()
   }
}

suspend fun suspendDAOFunction() {
   // No need to specify the Dispatcher, Room uses Dispatchers.IO.
   longrunningDatabaseWork()
}

```

### Adım 4: Veriyi gösterin

DAO'daki `getAllNights()`, `LiveData` döndürdüğü için, `SleepTrackerViewModel.kt`'de `nights` değişkeni `LiveData`'ya referans verir.

Veritabanındaki veriler her değiştiğinde `LiveData` `nights`'ın en son verileri gösterecek şekilde güncellenmesi bir `Room` özelliğidir. `LiveData`'yı explicit olarak ayarlamanız veya güncellemeniz gerekmez. `Room`, verileri veritabanıyla eşleşecek şekilde günceller.

Ancak `nights`'ı bir text view'da görüntülerseniz, nesne referansını gösterecektir. Nesnenin içeriğini görmek için verileri biçimlendirilmiş (formatted) bir string'e dönüştürün. `nights` veritabanından her yeni veri aldığında yürütülen bir `Transformation` map kullanın.

1. `Util.kt` dosyasını açın ve `formatNights()` tanımı ve ilgili import ifadeleri için kodun yorumunu kaldırın. Android Studio'da kodun yorumunu kaldırmak için `//` ile işaretlenmiş tüm kodları seçin ve `Cmd+/` veya `Control+/` tuşlarına basın.

2. `formatNights()`'ın HTML biçimli bir string olan `Spanned` türünü döndürdüğüne dikkat edin. Bu çok kullanışlıdır çünkü Android'in TextView'u temel HTML'i işleme yeteneğine sahiptir.

3. **res > values > strings.xml** dosyasını açın. Uyku verilerini görüntülemek için string kaynaklarını biçimlendirmek için [`CDATA`](https://www.w3.org/TR/REC-xml/#sec-cdata-sect) kullanımına dikkat edin.

4. **SleepTrackerViewModel.kt**'yi açın. `SleepTrackerViewModel` class'ında, `nights` adlı bir değişken tanımlayın. Veritabanından tüm geceleri alın ve onları `nights` değişkenine atayın

```

private val nights = database.getAllNights()

```

5. `nights`'ı bir `nightString`'e dönüştürmek için `nights` tanımının hemen altına kod ekleyin. `Util.kt`'den `formatNights()` fonksiyonunu kullanın.

`nights`'ı `Transformations` class'ından gelen [`map()`](https://developer.android.com/reference/android/arch/lifecycle/Transformations#map) fonksiyonuna iletin. String kaynaklarınıza erişmek için mapping fonksiyonunu `formatNights()` çağrısı olarak tanımlayın. `nights` ve bir `Resource` objesini bu fonkisyona verin.

```

val nightsString = Transformations.map(nights) { nights ->
   formatNights(nights, application.resources)
}

```

6. `fragment_sleep_tracker.xml` layout dosyasını açın. Artık, `TextView`'da, `android:text` özelliğinde kaynak string'ini `nightString`'e bir referans ile değiştirebilirsiniz.

```

android:text="@{sleepTrackerViewModel.nightsString}"

```

7. Kodunuzu tekrar build edin ve uygulamanızı çalıştırın. Başlangıç saatleriyle birlikte tüm uyku verileriniz artık görüntülenmelidir.

8. **Start** butonuna birkaç kez daha dokunduğunuzda daha fazla veri göreceksiniz.

![app image](https://developer.android.com/codelabs/kotlin-android-training-coroutines-and-room/img/e6eabf9793d5ab63.png)

Bir sonraki adımda, **Stop** butonu için işlevselliği etkinleştireceksiniz.

### Adım 5: Stop butonu için tıklama handler'ını ekleyin

Önceki adımdakiyle aynı pattern'ı kullanarak, `SleepTrackerViewModel`'deki **Stop** butonu için tıklama handler'ını uygulayın.

1. `ViewModel`'a `onStopTracking()` ekleyin. `viewModelScope`'ta bir coroutine başlatın. Bitiş zamanı henüz ayarlanmadıysa, `endTimeMilli`'yi mevcut sistem saatine ayarlayın ve gece verileriyle `update()` fonksiyonunu çağırın.

Kotlin'de, `return@``label` syntax'ı, iç içe geçmiş birkaç fonksiyon arasında bu ifadenin döndürüldüğü işlevi belirtir.

```

fun onStopTracking() {
   viewModelScope.launch {
       val oldNight = tonight.value ?: return@launch
       oldNight.endTimeMilli = System.currentTimeMillis()
       update(oldNight)
   }
}

```

2. `insert()`'i uygularken kullandığınız pattern'in aynısını kullanarak `update()`'i uygulayın.

```

private suspend fun update(night: SleepNight) {
    database.update(night)
}

```

3. Tıklama handler'ını UI'ya bağlamak için, `fragment_sleep_tracker.xml` layout dosyasını açın ve tıklama handler'ını `stop_button`'a ekleyin.

```

android:onClick="@{() -> sleepTrackerViewModel.onStopTracking()}"

```

4. Uygulamanızı build edin ve çalıştırın.

5. Önce **Start**'a, sonra **Stop**'a tıklayın. Başlangıç saatini, bitiş saatini, değersiz uyku kalitesini ve uyku saatini göreceksiniz.

![app image](https://developer.android.com/codelabs/kotlin-android-training-coroutines-and-room/img/fc7e561d95fd9c02.png)


### Adım 6: Clear butonu için tıklama handler'ını ekleyin

1. Benzer şekilde, `onClear()` ve `clear()`'ı' uygulayın.

```

fun onClear() {
   viewModelScope.launch {
       clear()
       tonight.value = null
   }
}

suspend fun clear() {
    database.clear()
}

```

2. Tıklama handler'ını UI'ya bağlamak için, `fragment_sleep_tracker.xml` dosyasını açın ve tıklama handler'ını `clear_button`'a ekleyin.

```

android:onClick="@{() -> sleepTrackerViewModel.onClear()}"

```

3. Uygulamanızı build edin ve çalıştırın.

4. Tüm verilerden kurtulmak için **Clear**'a tıklayın. Daha sonra **Start** ve **Stop**'a tıklayarak yeni veriler oluşturun.

