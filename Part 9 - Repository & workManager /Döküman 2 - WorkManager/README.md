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

1. Henüz DevBytes uygulamanız yoksa, bu döküman için DevBytes başlangıç kodunu GitHub'daki DevBytesRepository projesinden indirin.
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















## <a name="e"></a>Aşama 5 : Periyodik bir WorkRequest tanımlayın
## <a name="f"></a>Aşama 6 : Constraintler ekleyin
