# <a name="1"></a>Button Statelerini (Durumlarını) Kontrol Etmek için LiveData Kullanın

- [Navigation ekleyin](#a)
- [Uyku kalitesini kaydedin](#b)
- [Button görünürlüğünü kontrol edin & bir Snackbar ekleyin](#c)

Bu aşamada, TrackMySleepQuality uygulamasının uyku kalitesinde kaydını ve UI'ın son halini oluşturursunuz.

Uygulamanın, aşağıdaki şekilde gösterildiği gibi fragmentlarla temsil edilen iki ekranı vardır.

![app image](https://developer.android.com/codelabs/kotlin-android-training-quality-and-states/img/e28eb795b6812ee4.png)

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

![app architecture](https://developer.android.com/codelabs/kotlin-android-training-quality-and-states/img/2268f8ae35a8c715.png)

## <a name="a"></a>Aşama 1 : Navigation Ekleyin

Bu codelab, fragmentları ve navigation dosyasını kullanarak navigation'ı nasıl uygulayacağınızı bildiğinizi varsayar. İşinizi azaltmak için bu kodun büyük bir kısmı sağlanmıştır.

### Adım 1: Kodu inceleyin

1. Başlamak için, bir önceki aşamanın sonundaki kendi kodunuzla devam edin veya başlangıç kodunu indirin. <!-- linkle -->
2. Başlangıç kodunuzda `SleepQualityFragment`'ı inceleyin. Bu class, layout'u inflate eder, uygulamayı alır ve `binding.root`'u döndürür.
3. Tasarım editöründe **navigation.xml**'i açın. `SleepTrackerFragment`'ten `SleepQualityFragment`'e ve tekrar `SleepQualityFragment`'ten `SleepTrackerFragment`'e bir navigation path'i olduğunu göreceksiniz.

![navigation xml](https://developer.android.com/codelabs/kotlin-android-training-quality-and-states/img/903884ca86daf6e.png)

4. **navigation.xml**'in kodunu inceleyin. Özellikle `sleepNightKey` adı verilen `<argument>`'a bakın.

Kullanıcı `SleepTrackerFragment`'tan `SleepQualityFragment`'a geçtiğinde, uygulama güncellenmesi gereken gece için `sleepNightKey`'i `SleepQualityFragment`'a iletir.

### Adım 2: Uyku kalitesi takibi için navigation ekleyin

Navigation grafiği, `SleepTrackerFragment`'tan `SleepQualityFragment`'a ve tekrar geriye giden pathleri zaten içerir. Ancak, bir parçadan diğerine gezinmeyi uygulayan tıklama handlerları henüz kodlanmamıştır. Bu kodu şimdi `ViewModel`'a ekleyeceksiniz.

Tıklama handlerda, uygulamanın farklı bir hedefe gitmesini istediğinizde değişen bir `LiveData` ayarlarsınız. Fragment bu `LiveData`'yı gözlemler. Veri değiştiğinde, fragment hedefe gider ve state değişkenini sıfırlayacak olan view model'a işin bittiğini söyler.

1. `SleepTrackerViewModel`'ı açın. Kullanıcı **Stop** butonuna dokunduğunda uygulamanın bir kalite derecelendirmesi almak için `SleepQualityFragment`'a gitmesi için navigation eklemeniz gerekir.
2. `SleepTrackerViewModel`'da, uygulamanın `SleepQualityFragment`'a gitmesini istediğinizde değişen bir `LiveData` oluşturun. `LiveData`'nın yalnızca alınabilir bir sürümünü `ViewModel`'a göstermek için [encapsulation](https://kamer-dev.medium.com/oopnin-4-ana-prensibi-encapsulation-inheritence-abstraction-polymorphism-712ed2fbac7e) kullanın.

Bu kodu, class body'sinin en üst düzeyinde (top level) herhangi bir yere koyabilirsiniz.

```

private val _navigateToSleepQuality = MutableLiveData<SleepNight>()

val navigateToSleepQuality: LiveData<SleepNight>
   get() = _navigateToSleepQuality
   
```

3. Navigation'ı tetikleyen değişkeni sıfırlayan bir `doneNavigating()` fonksiyonu ekleyin.

```

fun doneNavigating() {
   _navigateToSleepQuality.value = null
}

```

4. **Stop** butonunun tıklama handlerında, `onStopTracking()`, `SleepQualityFragment`'a navigasyonu tetikleyin. Fonksiyonun sonundaki `_navigateToSleepQuality` değişkenini `launch{}` bloğu içindeki son şey olarak ayarlayın. Bu değişkenin `night`'a ayarlandığını unutmayın. Bu değişkenin bir değeri olduğunda, uygulama night'ı ileterek `SleepQualityFragment`'a gider.

```

_navigateToSleepQuality.value = oldNight


```

5. Uygulamanın ne zaman navigate edeceğini bilmesi için `SleepTrackerFragment`'ın `_navigateToSleepQuality`'yi observe etmesi gerekir. `SleepTrackerFragment`'ta, `onCreateView()`'da `navigateToSleepQuality()` için bir observer ekleyin. Bunun için içe aktarmanın belirsiz olduğunu ve `androidx.lifecycle.Observer` dosyasını içe aktarmanız gerektiğini unutmayın.

```

sleepTrackerViewModel.navigateToSleepQuality.observe(this, Observer {
})

```

![import image](https://developer.android.com/codelabs/kotlin-android-training-quality-and-states/img/572b3400684152c4.png)

6. Observer bloğunun içinde navigate edinb ve mevcut gecenin ID'sini iletin. Ardından `doneNavigating()`'i çağırın. İçe aktarma işleminiz belirsizse, `androidx.navigation.fragment.findNavController` dosyasını içe aktarın.

```

night ->
night?.let {
   this.findNavController().navigate(
           SleepTrackerFragmentDirections
                   .actionSleepTrackerFragmentToSleepQualityFragment(night.nightId))
   sleepTrackerViewModel.doneNavigating()
}

```

7. Uygulamanızı build edin ve çalıştırın. Önce **Start**'a ve sonra sizi `SleepQualityFragment` ekranına götürecek olan **Stop**'a tıklayın. Geri dönmek için sistemin geri butonunu kullanın.

## <a name="b"></a>Aşama 2 : Uyku kalitesini kaydedin

Bu aşamada uyku kalitesini kaydeder ve sleep tracker fragment'a geri dönersiniz. Güncellenen değeri kullanıcıya göstermek için ekran otomatik olarak güncellenmelidir. Bir `ViewModel` ve bir `ViewModelFactory` oluşturmanız ve `SleepQualityFragment`'ı güncellemeniz gerekir.

### Adım 1: Bir ViewModel ve bir ViewModelFactory oluşturun

1. `sleepquality` paketinde, **SleepQualityViewModel.kt**'yi oluşturun veya açın.
2. Bir `sleepNightKey` ve veritabanını argüman olarak alan bir `SleepQualityViewModel` class'ı oluşturun. `SleepTrackerViewModel` için yaptığınız gibi, factory üzerinden `database`'i iletmeniz gerekiyor. Ayrıca navigationdan `sleepNightKey`'i de iletmeniz gereklidir.

```

class SleepQualityViewModel(
       private val sleepNightKey: Long = 0L,
       val database: SleepDatabaseDao) : ViewModel() {
}

```

3. Yukarıdakiyle aynı pattern'ı kullanarak `SleepTrackerFragment`'a geri gitmek için `_navigateToSleepTracker`'ı bildirin. `navigateToSleepTracker` ve `doneNavigating(`)'i uygulayın.

```

private val _navigateToSleepTracker = MutableLiveData<Boolean?>()

val navigateToSleepTracker: LiveData<Boolean?>
   get() = _navigateToSleepTracker

fun doneNavigating() {
   _navigateToSleepTracker.value = null
}

```

4. Kullanılacak tüm uyku kalitesi resimleri için tek tıklama handler'ı olan `onSetSleepQuality()`'yi oluşturun.

Önceki codelab'dekiyle aynı coroutine pattern'ını kullanın:

- `viewModelScope`'ta bir coroutine başlatın
- `sleepNightKey`'i kullanarak `tonight`'ı alın.
- Uyku kalitesini ayarlayın.
- Veritabanını güncelleyin.
- Navigation'ı tetikleyin.

Aşağıdaki kod örneğinin, veritabanı işlemini farklı context'te dışlamak yerine, tıklama handlerındaki tüm işi yaptığına dikkat edin.

```

fun onSetSleepQuality(quality: Int) {
        viewModelScope.launch {
                val tonight = database.get(sleepNightKey) ?: return@launch
                tonight.sleepQuality = quality
                database.update(tonight)
            

            // Setting this state variable to true will alert the observer and trigger navigation.
            _navigateToSleepTracker.value = true
        }
    }
    
```

5. `sleepquality` paketinde, `SleepQualityViewModelFactory.kt`'yi oluşturun veya açın ve aşağıda gösterildiği gibi `SleepQualityViewModelFactory` class'ını ekleyin. Bu class, daha önce gördüğünüz aynı boilerplate kodun bir sürümünü kullanır. Devam etmeden önce kodu inceleyin.

```

class SleepQualityViewModelFactory(
       private val sleepNightKey: Long,
       private val dataSource: SleepDatabaseDao) : ViewModelProvider.Factory {
   @Suppress("unchecked_cast")
   override fun <T : ViewModel?> create(modelClass: Class<T>): T {
       if (modelClass.isAssignableFrom(SleepQualityViewModel::class.java)) {
           return SleepQualityViewModel(sleepNightKey, dataSource) as T
       }
       throw IllegalArgumentException("Unknown ViewModel class")
   }
}

```

### Adım 2: SleepQualityFragment'ı güncelleyin

1. `SleepQualityFragment.kt`'yi açın.
2. `onCreateView()`'da `application`'ı aldıktan sonra, navigation ile birlikte gelen argümanları (`arguments`) almanız gerekir. Bu argümanlar `SleepQualityFragmentArgs` içindedir. Bunları bundle'dan çıkarmanız gerekir.

```

val arguments = SleepQualityFragmentArgs.fromBundle(requireArguments())

```

3. Daha sonra, `dataSource`'u alın.

```

val dataSource = SleepDatabase.getInstance(application).sleepDatabaseDao

```

4. `dataSource` ve `sleepNightKey`'i ileterek bir factory oluşturun.

```

val viewModelFactory = SleepQualityViewModelFactory(arguments.sleepNightKey, dataSource)

```

5. ViewModel referansı alın.

```

val sleepQualityViewModel =
       ViewModelProvider(
               this, viewModelFactory).get(SleepQualityViewModel::class.java)

```

6. ViewModel'ı binding objesine ekleyin. (Binding objesiyle ilgili bir hata görürseniz şimdilik yok sayın.)

```

binding.sleepQualityViewModel = sleepQualityViewModel

```

7. Observer'ı ekleyin. İstendiğinde, `androidx.lifecycle.Observer`'ı içe aktarın

```

sleepQualityViewModel.navigateToSleepTracker.observe(this, Observer {
   if (it == true) { // Observed state is true.
       this.findNavController().navigate(
               SleepQualityFragmentDirections.actionSleepQualityFragmentToSleepTrackerFragment())
       sleepQualityViewModel.doneNavigating()
   }
})

```

### Adım 3: Layout dosyasını güncelleyin ve uygulamayı çalıştırın

1. `fragment_sleep_quality.xml` layout dosyasını açın. `<data>` bloğunda `SleepQualityViewModel` için bir değişken ekleyin.

```

 <data>
       <variable
           name="sleepQualityViewModel"
           type="com.example.android.trackmysleepquality.sleepquality.SleepQualityViewModel" />
   </data>
   
```

2. Altı uyku kalitesi resiminin her biri için aşağıdaki gibi bir tıklama handler'ı ekleyin. Kalite derecelendirmesini resim ile eşleştirin.

```

android:onClick="@{() -> sleepQualityViewModel.onSetSleepQuality(5)}"

```

3. Projeyi temizleyin ve tekrar build edin (clean & rebuild). Bu, binding objesiyle ilgili tüm hataları çözmelidir. Aksi takdirde, önbelleği temizleyin (**File > Invalidate Caches / Restart**) ve uygulamanızı yeniden build edin.

>Devam etmeden önce uygulamanın hatasız çalıştığından ve artık uyku kalitesini kaydedebildiğinizden emin olun.

Tebrikler! Az önce coroutineleri kullanarak eksiksiz bir `Room` veritabanı uygulaması oluşturdunuz.

## <a name="c"></a>Aşama 3 : Button görünürlüğünü kontrol edin & bir Snackbar ekleyin

Artık uygulamanız harika çalışıyor. Kullanıcı, **Start** ve **Stop**'a istediği kadar dokunabilir. Kullanıcı **Stop**'a dokunduğunda bir uyku kalitesi girebilir. Kullanıcı **Clear**'a dokunduğunda, tüm veriler arka planda sessizce temizlenir. Bununla birlikte, tüm düğmeler her zaman etkin ve tıklanabilir, bu da uygulamayı bozmaz, ancak kullanıcıların eksik uyku geceleri oluşturmasına izin verir.

Bu son aşamada, kullanıcıların yalnızca doğru seçimi yapabilmeleri için düğme görünürlüğünü yönetmek için transformation maplerin nasıl kullanılacağını öğreneceksiniz. Tüm veriler temizlendikten sonra arkadaş canlısı bir mesaj görüntülemek için benzer bir yöntem kullanabilirsiniz.

### Adım 1: Buton statelerini güncelleyin

Buradaki fikir, buton durumunu, başlangıçta yalnızca **Start** butonu etkinleştirilecek, yani tıklanabilir olacak şekilde ayarlamaktır.

Kullanıcı **Star**t'a dokunduktan sonra **Stop** butonu etkinleştirilir ve **Start** etkisizleşir. **Clear** butonu yalnızca veritabanında veri olduğunda etkinleştirilir.

1. `fragment_sleep_tracker.xml` layout dosyasını açın.
2. Her butona `android:enabled` özelliğini ekleyin. [`android:enabled`](https://developer.android.com/reference/android/widget/TextView.html#attr_android:enabled) özelliği, butonun etkinleştirilip etkinleştirilmediğini gösteren bir boolean değeridir. (Etkin, yani _enabled_, bir butona dokunulabilir; devre dışı bırakılan bir butona dokunulamaz.) Özelliğe, birazdan tanımlayacağınız bir state değişkeninin değerini verin.

`start_button`:

```

android:enabled="@{sleepTrackerViewModel.startButtonVisible}"

```

`stop_button`:

```

android:enabled="@{sleepTrackerViewModel.stopButtonVisible}"

```

`clear_button`:

```

android:enabled="@{sleepTrackerViewModel.clearButtonVisible}"

```

3. `SleepTrackerViewModel`'ı açın ve karşılık gelen üç değişken oluşturun. Her değişkene onu test eden bir transformation atayın.

- `tonight` `null` olduğunda **Start** butonu etkinleştirilmelidir.
- `tonight` `null` olmadığında **Stop** butonu etkinleştirilmelidir.
- **Clear** butonu yalnızca `nights` ve dolayısıyla veritabanı uyku geceleri içeriyorsa etkinleştirilmelidir.

```

val startButtonVisible = Transformations.map(tonight) {
   it == null
}
val stopButtonVisible = Transformations.map(tonight) {
   it != null
}
val clearButtonVisible = Transformations.map(nights) {
   it?.isNotEmpty()
}

```

4. Uygulamanızı çalıştırın ve butonlarla deneyler yapın.

>**İpucu: Devre dışı bırakılmış (disabled) bir View'un görünümünü ayarlama**

>`enabled` özelliği, `visibility` özelliği ile aynı değildir. `enabled` özelliği `View`'un görünür olup olmadığını değil, yalnızca etkin olup olmadığını belirler.

>"enabled" kelimesinin anlamı subclass'a göre değişir. Kullanıcı, metni enabled bir `EditText`'i düzenleyebilir, ancak disabled bir `EditText`'i düzenleyemez. Kullanıcı, enabled bir `Button`'a dokunabilir, ancak disabled bir `Button`'a dokunamaz.

>`View`'un etkin olmadığını görsel olarak temsil etmek için disabled bir `View`'a varsayılan bir stil uygulanır.

>Ancak, `View`'un bir `background` özelliği veya bir `textColor` özelliği varsa, bu özelliklerin değerleri, `View` görüntülendiğinde, *`View`* *devre dışı bırakılmış olsa bile kullanılır*.

>Enabled ve disabled durumlar için hangi renklerin kullanılacağını tanımlamak için metin rengi için bir [ColorStateList](https://developer.android.com/guide/topics/resources/color-list-resource) ve arka plan rengi için bir [StateListDrawable](https://developer.android.com/guide/topics/resources/drawable-resource#StateList) kullanın.

### Adım 2: Kullanıcıyı bildirmek için bir snackbar kullanın

Kullanıcı veritabanını temizledikten sonra, [Snackbar](https://material.io/develop/android/components/snackbar/) widget'ını kullanarak kullanıcıya bir onay gösterin. Snackbar, ekranın alt kısmındaki bir mesaj aracılığıyla bir işlem hakkında kısa bir geri bildirim sağlar. Bir zaman aşımından sonra, ekranın başka bir yerinde bir kullanıcı etkileşiminden sonra veya kullanıcı snackbar'ı ekrandan kaydırdıktan sonra bir snackbar kaybolur.

Snackbar'ı göstermek bir UI görevidir ve fragment'ta gerçekleşmelidir. Snackbar'ı göstermeye karar vermek, `ViewModel`'da gerçekleşir. Veriler temizlendiğinde bir snackbar ayarlamak ve tetiklemek için, navigation'ı tetikleme ile aynı tekniği kullanabilirsiniz.

1. `SleepTrackerViewModel`'da, encapsulated bir event oluşturun.

```
private var _showSnackbarEvent = MutableLiveData<Boolean>()

val showSnackBarEvent: LiveData<Boolean>
   get() = _showSnackbarEvent

```

2. Sonra, `doneShowingSnackbar()`'ı uygulayın.

```
fun doneShowingSnackbar() {
   _showSnackbarEvent.value = false
}

```

3. `SleepTrackerFragment`'ta, `onCreateView()` içine, bir observer ekleyin.

```

sleepTrackerViewModel.showSnackBarEvent.observe(this, Observer { })

```

4. Observer bloğunun içinde, snackbar'ı görüntüleyin ve event'i hemen sıfırlayın.

```

   if (it == true) { // Observed state is true.
       Snackbar.make(
               requireActivity().findViewById(android.R.id.content),
               getString(R.string.cleared_message),
               Snackbar.LENGTH_SHORT // How long to display the message.
       ).show()
       sleepTrackerViewModel.doneShowingSnackbar()
   }
   
```

5. `SleepTrackerViewModel`'da, event'i `onClear()` metodunda tetikleyin. Bunu yapmak için, `launch` bloğunun içindeki event değerini `true` olarak ayarlayın:

```

_showSnackbarEvent.value = true

```

6. Uygulamanızı build edin ve çalıştırın!
