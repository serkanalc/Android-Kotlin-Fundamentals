# <a name="1"></a>WorkManager
- [Kurulum ve başlangıç kodu kılavuzu](#a)
- [WorkManager](#b)
- [WorkManager dependency'sini ekleyin](#c)
- [Background worker ekleyin](#d)
- [Periyodik bir WorkRequest tanımlayın](#e)
- [Constraintler ekleyin](#f)

Bu dökümanda, önceki dökümanda geliştirdiğiniz DevBytes uygulaması üzerinde çalışacaksınız. (Bu uygulamaya sahip değilseniz, bu ders için başlangıç kodunu indirebilirsiniz.)

DevBytes uygulaması, [Google Android geliştirici ilişkileri ekibi](https://www.youtube.com/channel/UCVHFbqXqoYvEWM1Ddxl0QDg) tarafından hazırlanan kısa eğitimler olan DevByte videolarının bir listesini görüntüler. Videolar, geliştirici özelliklerini ve Android geliştirme için best practiceleri tanıtır.

Videoları günde bir kez önceden fetch ederek uygulamadaki kullanıcı deneyimini geliştireceksiniz. Bu, kullanıcının uygulamayı açar açmaz yeni içerik almasını sağlar.

![app image](https://developer.android.com/codelabs/kotlin-android-training-work-manager/img/30ee74d946a2f6ca.png)

## <a name="a"></a>Aşama 1 : Kurulum ve başlangıç kodu kılavuzu

Bu aşamada, başlangıç kodunu indirip inceleyebilirsiniz.

## Adım 1: Başlangıç uygulamasını indirin ve çalıştırın

Önceki dökümanda (varsa) oluşturduğunuz DevBytes uygulamasıyla çalışmaya devam edebilirsiniz. Alternatif olarak, başlangıç uygulamasını indirebilirsiniz.

Bu aşamada, başlangıç uygulamasını indirip çalıştırır ve başlangıç kodunu incelersiniz.

1. Henüz DevBytes uygulamanız yoksa, bu döküman için DevBytes başlangıç kodunu GitHub'daki [DevBytesRepository projesi](https://github.com/serkanalc/Android-Kotlin-Fundamentals-Projeler/tree/main/DevBytesRepository)nden indirin.
2. Kodu zip'ten çıkarın ve projeyi Android Studio'da açın.
3. Henüz bağlı değilse, test cihazınızı veya emülatörünüzü internete bağlayın. Uygulamayı build edin ve çalıştırın. Uygulama, ağdan DevByte videolarının bir listesini alır ve görüntüler.
4. Uygulamada, YouTube uygulamasında açmak için herhangi bir videoya tıklayın.

### Adım 2: Kodu keşfedin

Başlangıç uygulaması, önceki dökümanda tanıtılan birçok kodla birlikte gelir. Bu döküman için başlangıç kodunda ağ iletişimi, kullanıcı interface'i, çevrimdışı cache ve repository modülleri bulunur. `WorkManager`'ı kullanarak arka plan görevini zamanlamaya odaklanabilirsiniz.

1. Android Studio'da tüm paketleri genişletin.
2. `database` paketini keşfedin. Paket, `Room` kullanılarak uygulanan veritabanı entitylerini ve yerel veritabanını içerir.
3. `repository` paketini keşfedin. Paket, veri katmanını uygulamanın geri kalanından soyutlayan (abstract eden) `VideosRepository` class'ını içerir.
4. Başlangıç kodunun geri kalanını kendi başınıza ve önceki dökümanın yardımıyla keşfedin.

## <a name="b"></a>Aşama 2 : WorkManager

[`WorkManager`](https://developer.android.com/arch/work), Android Mimarisi Bileşenlerinden ([Android Architecture Components](https://developer.android.com/topic/libraries/architecture/)) biridir ve [Android Jetpack](http://d.android.com/jetpack)'in bir parçasıdır. `WorkManager`, deferrable (ertelenebilir) ve guaranteed execution (garantili yürütme) gerektiren arka plan çalışmaları içindir:

- _Deferrable_, işin hemen çalıştırılması gerekmediği anlamına gelir. Örneğin, analitik verileri sunucuya göndermek veya arka planda veritabanını senkronize etmek ertelenebilecek bir iştir.
- _Guaranteed execution_, uygulamadan çıkılsa veya cihaz yeniden başlatılsa bile görevin çalışacağı anlamına gelir.

![workmanager jetpack](https://developer.android.com/codelabs/kotlin-android-training-work-manager/img/4b9d878415255637.png)

`WorkManager` arka planda çalışırken, uyumluluk sorunlarıyla ve pil ve sistem sağlığı için en iyi uygulamalarla ilgilenir. `WorkManager`, API düzeyi 14'e kadar uyumluluk sunar. `WorkManager`, aygıt API düzeyine bağlı olarak bir arka plan görevi planlamak için uygun bir yol seçer. [`JobScheduler`](https://developer.android.com/reference/android/app/job/JobScheduler)'ı (API 23 ve üzeri sürümlerde) veya [`AlarmManager`](https://developer.android.com/reference/android/app/AlarmManager) ile [`BroadcastReceiver`](https://developer.android.com/reference/android/app/BroadcastReceiver)'ın bir kombinasyonunu kullanabilir.

![background scheduler diagram](https://developer.android.com/codelabs/kotlin-android-training-work-manager/img/e04f53ac665e07c9.png)

WorkManager ayrıca arka plan görevinin ne zaman çalıştırılacağına ilişkin ölçütler belirlemenizi sağlar. Örneğin, görevin yalnızca pil durumu, ağ durumu veya şarj durumu belirli kriterleri karşıladığında çalışmasını isteyebilirsiniz. Bu dökümanda daha sonra constraintleri (kısıtlamaları) nasıl ayarlayacağınızı öğreneceksiniz.

>**Not:** 
> - `WorkManager`, uygulama süreci sonlandırılırsa güvenli bir şekilde sonlandırılabilecek süreç içi arka plan çalışması için tasarlanmamıştır.
> - `WorkManager`, anında yürütme gerektiren görevler için tasarlanmamıştır.

Bu dökümanda, DevBytes video oynatma listesini günde bir kez ağdan önceden getirmek için bir görev planlayacaksınız. Bu görevi zamanlamak için `WorkManager` kütüphanesini kullanacaksınız.

## <a name="c"></a>Aşama 3 : WorkManager dependency'sini ekleyin

1. `build.gradle (Module:app)` dosyasını açın ve `WorkManager` dependency'sini projeye ekleyin.

Kütüphanenin en son sürümünü kullanıyorsanız, çözüm uygulaması beklendiği gibi derlenmelidir. Olmazsa, sorunu çözmeyi deneyin veya aşağıda gösterilen kütüphane sürümüne geri dönün.

```

// WorkManager dependency
def work_version = "1.0.1"
implementation "android.arch.work:work-runtime-ktx:$work_version"

```

2. Projenizi Sync'leyin ve derleme hatası olmadığından emin olun.

## <a name="d"></a>Aşama 4 : Background worker ekleyin

Projeye kod eklemeden önce WorkManager kütüphanesindeki aşağıdaki classları öğrenin:

- [`Worker`](https://developer.android.com/reference/androidx/work/Worker.html)

Bu class, arka planda çalıştırılacak fiili çalışmayı (görevi) tanımladığınız yerdir. Bu class'ı extend eder ve [`doWork()`](https://developer.android.com/reference/androidx/work/Worker.html#doWork()) metodunu override edersiniz. `doWork()` metodu, verileri sunucuyla eşitleme veya görüntüleri işleme gibi arka planda gerçekleştirilecek kodu koyduğunuz yerdir. `Worker`'ı bu görevde uygularsınız.
- [`WorkRequest`](https://developer.android.com/reference/androidx/work/WorkRequest.html)

Bu class, worker'ı arka planda çalıştırma isteğini temsil eder. Cihaz takılı veya Wi-Fi bağlı gibi [`Constraintlerin`](https://developer.android.com/reference/androidx/work/Constraints.html) yardımıyla çalışan worker görevinin nasıl ve ne zaman çalıştırılacağını yapılandırmak için `WorkRequest`'i kullanın. `WorkRequest`'i sonraki bir aşamada uygulayacaksınız.
- [`WorkManager`](https://developer.android.com/reference/androidx/work/WorkManager.html)

Bu class, `WorkRequest`'inizi planlar ve çalıştırır. `WorkManager`, work requestleri, belirttiğiniz constraintleri yerine getirirken sistem kaynakları üzerindeki yükü yayacak şekilde zamanlar. `WorkManager`'ı sonraki bir aşamada uygulayacaksınız.

### Adım 1: Bir worker oluşturun

Bu aşamada, arka planda DevBytes video oynatma listesini önceden fetch etmek (getirmek) için bir [`Worker`](https://developer.android.com/reference/androidx/work/Worker) ekleyeceksiniz.

1. `devbyteviewer` paketinin içinde, `work` adı verilen yeni bir paket oluşturun.
2. `work` paketinin içinde, `RefreshDataWorker` adı verilen yeni bir Kotlin class'ı oluşturun.
3. `RefreshDataWorker` class'ını `CoroutineWorker` class'ından extend edin. `context`'i ve [`WorkerParameters`](https://developer.android.com/reference/androidx/work/WorkerParameters.html)'ı constructor parametreleri olarak iletin.

```

class RefreshDataWorker(appContext: Context, params: WorkerParameters) :
       CoroutineWorker(appContext, params) {
}

```

4. Abstract class hatasını çözmek için, `RefreshDataWorker` class'ı içindeki `doWork()` metodunu override edin.

```

override suspend fun doWork(): Result {
  return Result.success()
}

```

Bir _suspending function_, daha sonra duraklatılabilen ve devam ettirilebilen bir fonksiyondur. Suspending function, uzun süredir devam eden bir işlemi yürütebilir ve main thread'i engellemeden tamamlanmasını bekleyebilir.

### Adım 2: doWork()'u uygulayın

`Worker` class'ı içindeki `doWork()` metodu, bir arka plan thread'inde çağrılır. Metot, işi senkron olarak gerçekleştirir ve bir [`ListenableWorker.Result`](https://developer.android.com/reference/androidx/work/ListenableWorker.Result.html) nesnesi döndürmelidir. Android sistemi, bir `Worker`'a yürütmesini tamamlaması ve bir `ListenableWorker.Result` nesnesi döndürmesi için maksimum 10 dakika verir. Bu süre geçtikten sonra sistem `Worker`'ı zorla durdurur.

Bir `ListenableWorker.Result` nesnesi oluşturmak için, arka plan çalışmasının tamamlanma durumunu belirtmek üzere aşağıdaki statik metotlardan birini çağırın:

- [`Result.success()`](https://developer.android.com/reference/androidx/work/ListenableWorker.Result.html#success())—work başarıyla tamamlandı.
- [`Result.failure()`](https://developer.android.com/reference/androidx/work/ListenableWorker.Result.html#failure())—work kalıcı bir başarısızlıkla tamamlandı.
- [`Result.retry()`](https://developer.android.com/reference/androidx/work/ListenableWorker.Result.html#retry())—work geçici bir hatayla karşılaştı ve yeniden denenmesi gerekiyor.

Bu aşamada, DevBytes video oynatma listesini ağdan almak için `doWork()` metodunu uygulayacaksınız. Verileri ağdan almak için `VideosRepository` class'ındaki mevcut metotları yeniden kullanabilirsiniz.

1. `RefreshDataWorker` class'ında, `doWork()` içinde, bir `VideosDatabase` nesnesi ve bir `VideosRepository` nesnesi oluşturun ve instantiate edin.

```

override suspend fun doWork(): Result {
   val database = getDatabase(applicationContext)
   val repository = VideosRepository(database)

   return Result.success()
}

```

2. `RefreshDataWorker` class'ında, `doWork()` içinde, `return` ifadesinin yukarısında, bir `try` bloğu içindeki `refreshVideos()` metodunu çağırın. Worker'ın ne zaman çalıştırıldığını izlemek için bir log ekleyin.

```

try {
   repository.refreshVideos( )
   Timber.d("Work request for sync is run")
   } catch (e: HttpException) {
   return Result.retry()
}

```

 "Unresolved reference" hatasını çözmek için, `retrofit2.HttpException`'ı import edin.
 
 3. Referansınız için tam RefreshDataWorker class'ı:

```

class RefreshDataWorker(appContext: Context, params: WorkerParameters) :
       CoroutineWorker(appContext, params) {

   override suspend fun doWork(): Result {
       val database = getDatabase(applicationContext)
       val repository = VideosRepository(database)
       try {
           repository.refreshVideos()
       } catch (e: HttpException) {
           return Result.retry()
       }
       return Result.success()
   }
}

```

## <a name="e"></a>Aşama 5 : Periyodik bir WorkRequest tanımlayın

Bir `Worker`, bir iş birimini tanımlar ve [`WorkRequest`](https://developer.android.com/reference/androidx/work/WorkRequest), işin nasıl ve ne zaman çalıştırılması gerektiğini tanımlar. `WorkRequest` class'ının iki somut uygulaması vardır:

- [`OneTimeWorkRequest`](https://developer.android.com/reference/androidx/work/OneTimeWorkRequest.html) class'ı tek seferlik görevler içindir. (Tek seferlik bir görev yalnızca bir kez gerçekleşir.)
- [`PeriodicWorkRequest`](https://developer.android.com/reference/androidx/work/PeriodicWorkRequest.html) class'ı, periyodik işler, aralıklarla tekrarlanan işler içindir.

Görevler tek seferlik veya periyodik olabilir, bu nedenle class'ı buna göre seçmelisiniz. Yinelenen çalışmayı (recurring work) zamanlama hakkında daha fazla bilgi için [yinelenen çalışma belgelerine](https://developer.android.com/topic/libraries/architecture/workmanager/how-to/recurring-work) bakın.

>**Not:** Periyodik çalışma için minimum aralık 15 dakikadır. Periyodik çalışma, constraintlerinden biri olarak initial delay'e sahip olamaz.

Bu aşamada, önceki aşamada oluşturduğunuz worker'ı çalıştırmak için bir `WorkRequest` tanımlayacak ve planlayacaksınız.

### Adım 1: Yinelenen çalışmayı ayarlayın

Bir Android uygulamasında, `Application` class'ı, activityler ve servisler gibi diğer tüm bileşenleri içeren temel class'tır. Uygulamanız veya paketiniz için süreç oluşturulduğunda, `Application` class'ı (veya herhangi bir `Application` subclass'ı), diğer herhangi bir class'tan önce instantiate edilir.

Bu örnek uygulamada `DevByteApplication` class'ı, `Application` class'ının bir subclass'ıdır. `DevByteApplication` class'ı, `WorkManager`'ı zamanlamak için iyi bir yerdir.

1. `DevByteApplication` class'ında, yinelenen arka plan çalışmasını ayarlamak için `setupRecurringWork()` adlı bir metot oluşturun.

```

/**
* Her gün yeni ağ verilerini 'fetch' etmek için WorkManager arka plan işini kurun.
*/
private fun setupRecurringWork() {
}

```

2. `setupRecurringWork()` metodunun içinde, [`PeriodicWorkRequestBuilder()`](https://developer.android.com/reference/androidx/work/PeriodicWorkRequest.Builder) metodunu kullanarak günde bir kez çalıştırılacak bir periyodik work request'i oluşturun ve başlatın. Önceki aşamada oluşturduğunuz `RefreshDataWorker` class'ını iletin. `TimeUnit.``DAYS` zaman birimiyle `1` tekrar aralığında iletin.

```

val repeatingRequest = PeriodicWorkRequestBuilder<RefreshDataWorker>(1, TimeUnit.DAYS)
       .build()
       
```

Hatayı düzeltmek için, `java.util.concurrent.TimeUnit`'i import edin.

### Adım 2: WorkManager ile bir WorkRequest zamanlayın (schedule)

`WorkRequest`'inizi tanımladıktan sonra, [`enqueueUniquePeriodicWork()`](https://developer.android.com/reference/androidx/work/WorkManager.html#enqueueUniquePeriodicWork(java.lang.String,%20androidx.work.ExistingPeriodicWorkPolicy,%20androidx.work.PeriodicWorkRequest)) metodunu kullanarak `WorkManager` ile programlayabilirsiniz. Bu metot, belirli bir ada sahip yalnızca bir [`PeriodicWorkRequest`](https://developer.android.com/reference/androidx/work/PeriodicWorkRequest.html)'in aynı anda etkin olabileceği sıraya, benzersiz olarak adlandırılmış bir `PeriodicWorkRequest` eklemenize olanak tanır.

Örneğin, yalnızca bir sync işleminin etkin olmasını isteyebilirsiniz. Bekleyen bir sync işlemi varsa, [ExistingPeriodicWorkPolicy](https://developer.android.com/reference/androidx/work/ExistingPeriodicWorkPolicy.html) kullanarak çalışmasına izin vermeyi veya yeni çalışmanızla değiştirmeyi seçebilirsiniz.

Bir `WorkRequest` schedule etmenin yolları hakkında daha fazla bilgi edinmek için [`WorkManager`](https://developer.android.com/reference/androidx/work/WorkManager.html#public-methods_1) belgelerine bakın.

1. `RefreshDataWorker` class'ında, class'ın başına bir companion object ekleyin. Bu worker'ı benzersiz şekilde tanımlamak için bir work (çalışma) adı tanımlayın.

>**Not:** Bu work ismi class'ın en üstünde de bulacağınız, package isminden gelmektedir. Kendi kodunuzda bu örnektekinden biraz farklı olabilir.

```

companion object {
   const val WORK_NAME = "com.example.android.devbyteviewer.work.RefreshDataWorker"
}

```

2. `DevByteApplication` class'ında `setupRecurringWork()` metodunun sonunda, work'u `enqueueUniquePeriodicWork()` metodunu kullanarak zamanlayın (schedule edin). `ExistingPeriodicWorkPolicy` için `KEEP` enum'unu iletin. `PeriodicWorkRequest` parametresi olarak `repeatingRequest`'i iletin.

```

WorkManager.getInstance().enqueueUniquePeriodicWork(
       RefreshDataWorker.WORK_NAME,
       ExistingPeriodicWorkPolicy.KEEP,
       repeatingRequest)
       
```

Aynı ada sahip bekleyen (tamamlanmamış) work varsa, `ExistingPeriodicWorkPolicy.``KEEP` parametresi `WorkManager`'ın önceki periyodik work'ü tutmasını ve yeni work request'i reddetmesini sağlar.

>**Best Practice:** `onCreate()` metodu main thread'i çalışır. `onCreate()` içinde uzun süreli bir işlem (long-running operation) gerçekleştirmek, UI thread'i engelleyebilir ve uygulamanın yüklenmesinde gecikmeye neden olabilir. Bu sorunu önlemek için, Timber'ı başlatma ve `WorkManager`'ı main thread'den schedule etme gibi görevleri bir coroutine içinde çalıştırın. 

3. `DevByteApplication` class'ının başında bir [`CoroutineScope`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/coroutine-scope.html) nesnesi oluşturun. [`Dispatchers.Default`](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/-dispatchers/-default.html)'ı constructor parametresi olarak iletin.

```

private val applicationScope = CoroutineScope(Dispatchers.Default)

```

4. `DevByteApplication` class'ında, bir coroutine başlatmak için `delayedInit()` adlı yeni bir metot ekleyin.

```

private fun delayedInit() {
   applicationScope.launch {
   }
}

```

5. `delayedInit()` metodunun içinde, `setupRecurringWork()`ü çağırın.
6.  Timber initialization'ını `onCreate()` metodundan `delayedInit()` metoduna taşıyın.

```

private fun delayedInit() {
   applicationScope.launch {
       Timber.plant(Timber.DebugTree())
       setupRecurringWork()
   }
}

```

7. `DevByteApplication` class'ında, `onCreate()` metodunun sonunda, `delayedInit()` metoduna bir çağrı ekleyin.

```

override fun onCreate() {
   super.onCreate()
   delayedInit()
}

```

8. Android Studio penceresinin altındaki **Logcat** bölmesini açın. `RefreshDataWorker` üzerine filtreleyin.
9. Uygulamayı çalıştırın. `WorkManager`, yinelenen work'ünüzü hemen planlar.

**Logcat** bölmesinde, work request'inin schedule edildiğini ve ardından başarıyla çalıştığını gösteren log ifadelerine dikkat edin.

```

D/RefreshDataWorker: Work request for sync is run
I/WM-WorkerWrapper: Worker result SUCCESS for Work [...]

```

`WM-WorkerWrapper` log'u `WorkManager` kütüphanesinden görüntülenir, bu nedenle bu log mesajını değiştiremezsiniz.

### Adım 3: (Opsiyonel) WorkRequest'i minimum bir aralık için schedule edin

Bu adımda zaman aralığını 1 günden 15 dakikaya düşürürsünüz. Bunu, periyodik bir work request'in loglarını çalışırken görebilmek için yaparsınız.

1. `DevByteApplication` class'ında, `setupRecurringWork()` metodunun içinde, mevcut `repeatingRequest` tanımını commentleyin. `15` dakikalık periyodik tekrar aralığı ile yeni bir work request ekleyin.

```

// val repeatingRequest = PeriodicWorkRequestBuilder<RefreshDataWorker>(1, TimeUnit.DAYS)
//        .build()
val repeatingRequest = PeriodicWorkRequestBuilder<RefreshDataWorker>(15, TimeUnit.MINUTES)
       .build()

```

2. `Logcat` bölmesini Android Studio'da açın ve `RefreshDataWorker` üzerine filtreleyin. Önceki logları temizlemek için **Clear logcat** simgesini tıklayın. ![Clear logcat](https://developer.android.com/codelabs/kotlin-android-training-work-manager/img/73fa94afd290964.png)

3. Uygulamayı çalıştırın ve `WorkManager` yinelenen work'ünüzü hemen planlar. **Logcat** bölmesinde, loglara dikkat edin; work request her 15 dakikada bir çalıştırılır. Başka bir work request logları kümesi görmek için 15 dakika bekleyin. Uygulamayı çalışır durumda bırakabilir veya kapatabilirsiniz; work manager yine de çalışmalıdır.

Aralığın bazen 15 dakikadan az, bazen 15 dakikadan fazla olduğuna dikkat edin. (Tam zamanlama, işletim sistemi pil optimizasyonlarına tabidir.)

```
12:44:40 D/RefreshDataWorker: Work request for sync is run
12:44:40 I/WM-WorkerWrapper: Worker result SUCCESS for Work 
12:59:24 D/RefreshDataWorker: Work request for sync is run
12:59:24 I/WM-WorkerWrapper: Worker result SUCCESS for Work 
13:15:03 D/RefreshDataWorker: Work request for sync is run
13:15:03 I/WM-WorkerWrapper: Worker result SUCCESS for Work 
13:29:22 D/RefreshDataWorker: Work request for sync is run
13:29:22 I/WM-WorkerWrapper: Worker result SUCCESS for Work 
13:44:26 D/RefreshDataWorker: Work request for sync is run
13:44:26 I/WM-WorkerWrapper: Worker result SUCCESS for Work

```

Tebrikler! `WorkManager` ile bir work oluşturdunuz ve work request'i planladınız. Ancak bir sorun var: herhangi bir constraint belirtmediniz. `WorkManager`, cihazın pili düşük, uyku halinde veya ağ bağlantısı olmasa bile çalışmayı günde bir kez planlayacaktır. Bu, cihazın pilini ve performansını etkiler ve kötü bir kullanıcı deneyimine neden olabilir.

Bir sonraki aşamada, bu sorunu constraintler ekleyerek çözeceksiniz.

## <a name="f"></a>Aşama 6 : Constraintler ekleyin

Önceki görevde, bir work request planlamak için `WorkManager` 'ı kullandınız. Bu aşamada, işin ne zaman yürütüleceğine ilişkin ölçütler eklersiniz.

`WorkRequest`'i tanımlarken, `Worker`'ın ne zaman çalışması gerektiğine ilişkin constraintler belirleyebilirsiniz. Örneğin, çalışmanın yalnızca cihaz boştayken (idle) veya yalnızca cihaz prize takılı ve Wi-Fi'ye bağlıyken çalışması gerektiğini belirtmek isteyebilirsiniz. Ayrıca, çalışmayı yeniden denemek için bir backoff policy (geri çekilme ilkesi) belirleyebilirsiniz. [Desteklenen constraintler](https://developer.android.com/reference/androidx/work/Constraints.Builder#public-methods_1), `Constraints.Builder`'da ayarlanan metotlardır. Daha fazla bilgi edinmek için [Work Requestlerinizi Tanımlama](https://developer.android.com/topic/libraries/architecture/workmanager/how-to/define-work) bölümüne bakın.

>**PeriodicWorkRequest ve constraintler**

Tekrarlanan work için bir `WorkRequest`, örneğin `PeriodicWorkRequest`, iptal edilene kadar birden çok kez yürütülür. İlk yürütme hemen veya verilen constraintler karşılanır karşılanmaz gerçekleşir.

Bir sonraki yürütme, bir sonraki periyot aralığında gerçekleşir. `WorkManager`, örneğin cihaz [Doze modundayken](https://developer.android.com/training/monitoring-device-state/doze-standby) OS pil optimizasyonlarına tabi olduğundan yürütmenin gecikebileceğini unutmayın.

### Adım 1: Bir Constraints nesnesi ekleyin ve bir constraint ayarlayın

Bu adımda, bir `Constraints` nesnesi oluşturursunuz ve nesne üzerinde bir ağ türü constraint'i olan bir constraint belirlersiniz. (Yalnızca bir constraint ile logları fark etmek daha kolaydır. Daha sonraki bir adımda başka constraintler eklersiniz.)

1. `DevByteApplication` class'ında `setupRecurringWork()` öğesinin başında, `Constraints` türünde bir `val` tanımlayın. `Constraints.Builder()` metodunu kullanın.

```

val constraints = Constraints.Builder()

```

Hatayı düzeltmek için, `androidx.work.Constraints`'i import edin.

2. `constraints` nesnesine bir ağ türü constraint'i eklemek için [`setRequiredNetworkType()`](https://developer.android.com/reference/androidx/work/Constraints.Builder#setRequiredNetworkType(androidx.work.NetworkType)) metodunu kullanın. Work request'in yalnızca cihaz ölçülmemiş bir ağdayken (unmetered network) çalışması için `UNMETERED` enum'u kullanın.

```

.setRequiredNetworkType(NetworkType.UNMETERED)

```

3. Builder'dan constraints'i oluşturmak için `build()` metodunu kullanın.

```

val constraints = Constraints.Builder()
       .setRequiredNetworkType(NetworkType.UNMETERED)
       .build()
       
```

Şimdi yeni oluşturulan `Constraints` nesnesini work request'e ayarlamanız gerekiyor.

4. `DevByteApplication` class'ında, `setupRecurringWork()` metodunun içinde, `Constraints` nesnesini `repeatingRequest` olan periyodik work request'e ayarlayın. Constraintleri ayarlamak için [`setConstraints()`](https://developer.android.com/reference/androidx/work/WorkRequest.Builder.html#setConstraints(androidx.work.Constraints)) metodunu `build()` metot çağrısının üstüne ekleyin.

```

      val repeatingRequest = PeriodicWorkRequestBuilder<RefreshDataWorker>(15, TimeUnit.MINUTES)
               .setConstraints(constraints)
               .build()
               
```

### Adım 2: Uygulamayı çalıştırın ve loglara dikkat edin

Bu adımda, uygulamayı çalıştırırsınız ve belirli aralıklarla arka planda çalışan kısıtlı work request'i fark edersiniz.

1. Önceden zamanlanmış görevleri iptal etmek için uygulamayı cihazdan veya emülatörden kaldırın.
2. Android Studio'da **Logcat** bölmesini açın. **Logcat** bölmesinde, soldaki **Clear logcat** simgesine tıklayarak önceki logları temizleyin. `work`'e göre filtreleyin. ![clear logcat icon](https://developer.android.com/codelabs/kotlin-android-training-work-manager/img/73fa94afd290964.png)

3. Constraintlerin nasıl çalıştığını görebilmek için cihazdaki veya emülatördeki Wi-Fi'yi kapatın. Geçerli kod, isteğin yalnızca ölçülmemiş bir ağda (unmetered network) çalışması gerektiğini belirten yalnızca bir constraint belirler. Wi-Fi kapalı olduğundan, cihaz ağa bağlı değildir, ölçülü veya ölçülü değildir. Bu nedenle, bu constraint karşılanmayacaktır.
4. Uygulamayı çalıştırın ve **Logcat** bölmesine dikkat edin. `WorkManager`, arka plan görevini hemen schedule eder. Ağ constraint'i karşılanmadığından görev çalıştırılmaz.

```

11:31:44 D/DevByteApplication: Periodic Work request for sync is scheduled

```

5. Cihazda veya emülatörde Wi-Fi'yi açın ve **Logcat** bölmesini izleyin. Artık zamanlanmış arka plan görevi, ağ constrainti karşılandığı sürece yaklaşık her 15 dakikada bir çalıştırılır.

```

11:31:44 D/DevByteApplication: Periodic Work request for sync is scheduled
11:31:47 D/RefreshDataWorker: Work request for sync is run
11:31:47 I/WM-WorkerWrapper: Worker result SUCCESS for Work [...]
11:46:45 D/RefreshDataWorker: Work request for sync is run
11:46:45 I/WM-WorkerWrapper: Worker result SUCCESS for Work [...] 
12:03:05 D/RefreshDataWorker: Work request for sync is run
12:03:05 I/WM-WorkerWrapper: Worker result SUCCESS for Work [...] 
12:16:45 D/RefreshDataWorker: Work request for sync is run
12:16:45 I/WM-WorkerWrapper: Worker result SUCCESS for Work [...] 
12:31:45 D/RefreshDataWorker: Work request for sync is run
12:31:45 I/WM-WorkerWrapper: Worker result SUCCESS for Work [...] 
12:47:05 D/RefreshDataWorker: Work request for sync is run
12:47:05 I/WM-WorkerWrapper: Worker result SUCCESS for Work [...] 
13:01:45 D/RefreshDataWorker: Work request for sync is run
13:01:45 I/WM-WorkerWrapper: Worker result SUCCESS for Work [...]

```

### Adım 3: Daha fazla constraint ekleyin

Bu adımda, `PeriodicWorkRequest`'e aşağıdaki constraintleri eklersiniz:

- Pil düşük değil.
- Cihaz şarj oluyor.
- Cihaz boşta (idle); yalnızca API düzeyi 23 (Android M) ve üzeri sürümlerde mevcuttur.

Aşağıdakileri `DevByteApplication` class'ında uygulayın.

1. `DevByteApplication` class'ında, `setupRecurringWork()` metodunun içinde, work request'in yalnızca pil düşük değilse çalışması gerektiğini belirtin. Constraint'i `build()` metot çağrısından önce ekleyin ve [`setRequiresBatteryNotLow()`](https://developer.android.com/reference/androidx/work/Constraints.Builder#setRequiresBatteryNotLow(boolean)) metodunu kullanın.

```

.setRequiresBatteryNotLow(true)

```

2. Work request'i yalnızca cihaz şarj olurken çalışacak şekilde güncelleyin. Constraint'i `build()` metot çağrısından önce ekleyin ve [`setRequiresCharging()`](https://developer.android.com/reference/androidx/work/Constraints.Builder#setRequiresCharging(boolean)) metodunu kullanın.

```

.setRequiresCharging(true)

```

3. Work request'i yalnızca cihaz boştayken çalışacak şekilde güncelleyin. Constraint'i `build()` metot çağrısından önce ekleyin ve [`setRequiresDeviceIdle()`](https://developer.android.com/reference/androidx/work/Constraints.Builder#setRequiresDeviceIdle(boolean)) metodunu kullanın. Bu constraint, work request'i yalnızca kullanıcı cihazı aktif olarak kullanmadığında çalıştırır. Bu özellik yalnızca Android 6.0 (Marshmallow) ve sonraki sürümlerde mevcuttur, bu nedenle SDK `M` ve üzeri sürümler için bir constraint ekleyin.

```

.apply {
   if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
       setRequiresDeviceIdle(true)
   }
}

```

İşte `constraints` nesnesinin tam tanımı.

```

val constraints = Constraints.Builder()
       .setRequiredNetworkType(NetworkType.UNMETERED)
       .setRequiresBatteryNotLow(true)
       .setRequiresCharging(true)
       .apply {
           if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
               setRequiresDeviceIdle(true)
           }
       }
       .build()

```
 
 4. `setupRecurringWork()` metodunun içinde, request aralığını günde bir kez olarak değiştirin.
 
 ```
 
 val repeatingRequest = PeriodicWorkRequestBuilder<RefreshDataWorker>(1, TimeUnit.DAYS)
       .setConstraints(constraints)
       .build()
       
 ```
 
Periyodik work request'inin ne zaman planlandığını izleyebilmeniz için bir logla `setupRecurringWork()` metodunun eksiksiz uygulaması buradadır.
 
```
 
private fun setupRecurringWork() {

       val constraints = Constraints.Builder()
               .setRequiredNetworkType(NetworkType.UNMETERED)
               .setRequiresBatteryNotLow(true)
               .setRequiresCharging(true)
               .apply {
                   if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                       setRequiresDeviceIdle(true)
                   }
               }
               .build()
       val repeatingRequest = PeriodicWorkRequestBuilder<RefreshDataWorker>(1, TimeUnit.DAYS)
               .setConstraints(constraints)
               .build()
       
       Timber.d("Periodic Work request for sync is scheduled")
       WorkManager.getInstance().enqueueUniquePeriodicWork(
               RefreshDataWorker.WORK_NAME,
               ExistingPeriodicWorkPolicy.KEEP,
               repeatingRequest)
   }
   
``` 
 
5. Önceden planlanmış work request'i kaldırmak için DevBytes uygulamasını cihazınızdan veya emülatörünüzden kaldırın.
6. Uygulamayı çalıştırın ve `WorkManager` hemen work request'i planlar. Work request, tüm constraintler karşılandığında günde bir kez çalışır.
7. Bu work request, uygulama yüklü olduğu sürece, uygulama çalışmıyor olsa bile arka planda çalışacaktır. Bu nedenle uygulamayı telefondan kaldırmalısınız.
 
İyi iş! DevBytes uygulamasında videoların günlük önden fetch edilmesi için pil dostu bir work request uyguladınız ve planladınız. `WorkManager`, sistem kaynaklarını optimize ederek işi planlayacak ve çalıştıracaktır. Kullanıcılarınız ve pilleri çok mutlu olacak. 
 
