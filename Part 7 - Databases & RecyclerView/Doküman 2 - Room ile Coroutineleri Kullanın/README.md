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


## <a name="d"></a>Aşama 4 : Data toplayın & görüntüleyin

