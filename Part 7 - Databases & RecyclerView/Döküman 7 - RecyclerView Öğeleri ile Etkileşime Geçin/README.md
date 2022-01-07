# <a name="1"></a>RecyclerView Öğeleri ile Etkileşime Geçin

- [Başlangıç kodunu alın & uygulamadaki değişiklikleri inceleyin](#a)
- [Öğeleri tıklanabilir yapın](#b)
- [Öğe tıklamalarını yönetin](#c)

Sleep-tracker uygulamasının, aşağıdaki şekilde gösterildiği gibi fragmentlarla temsil edilen iki ekranı vardır.

![app image](https://developer.android.com/codelabs/kotlin-android-training-grid-layout/img/76d78f63f88c3c86.png)
![app image](https://developer.android.com/codelabs/kotlin-android-training-grid-layout/img/43590f0a4c00e138.png)

Solda gösterilen ilk ekranda, izlemeyi başlatmak ve durdurmak için butonlar vardır. Ekran, kullanıcının tüm uyku verilerini gösterir. **Clear** butonu, uygulamanın kullanıcı için topladığı tüm verileri kalıcı olarak siler. Sağda gösterilen ikinci ekran, uyku kalitesi derecelendirmesini seçmek içindir.

Bu uygulama, uyku verilerini sürdürmek (persist) için bir UI controller, `ViewModel` ve `LiveData` ve bir `Room` veritabanı ile basitleştirilmiş bir mimari kullanır.

![architecture image](https://developer.android.com/codelabs/kotlin-android-training-grid-layout/img/49f975f1e5fe689.png)

Bu aşamada, bir kullanıcı ızgaradaki bir öğeye dokunduğunda aşağıdaki gibi bir ayrıntı ekranı açan yanıt verme yeteneği eklersiniz. Bu ekranın kodu (ragment, view model ve navigation) başlangıç uygulamasıyla sağlanır ve tıklama işleme mekanizmasını uygularsınız.

![detay image](https://developer.android.com/codelabs/kotlin-android-training-interacting-with-items/img/1018f2610bca049.png)

## <a name="a"></a>Aşama 1 : Başlangıç kodunu alın & uygulamadaki değişiklikleri inceleyin

>Önemli: Bu aşama için başlangıç uygulaması, önceki aşamadaki son TrackMySleepQuality uygulamasının parçası olmayan ek layoutlar, resourcelar, and utilityler sağlar. Bu aşama üzerinde çalışmak için sağlanan başlangıç kodunu kullanmanızı öneririz.

### Adım 1: Başlangıç uygulamasını alın

1. GitHub'dan RecyclerViewClickHandler-Starter kodunu indirin ve projeyi Android Studio'da açın.
2. Sleep-tracker starter uygulamasını build edin ve çalıştırın.

#### [Opsiyonel] Uygulamayı önceki aşamadan kullanmak istiyorsanız uygulamanızı güncelleyin

Bu codelab için GitHub'da sağlanan başlangıç uygulamasından çalışacaksanız bir sonraki adıma geçin.

Önceki aşamada oluşturduğunuz kendi sleep-tracker uygulamanızı kullanmaya devam etmek istiyorsanız, mevcut uygulamanızı details-screen fragment koduna sahip olacak şekilde güncellemek için aşağıdaki talimatları izleyin.

>İpucu: Dosya sisteminden Android Studio'ya dosya kopyalamak için bunları kopyalayıp yapıştırabilir veya sürükleyip bırakabilirsiniz.

1. Mevcut uygulamanızla devam ediyor olsanız bile, dosyaları kopyalayabilmek için GitHub'dan RecyclerViewClickHandler-Starter kodunu alın.
2. `sleepdetail` paketindeki tüm dosyaları kopyalayın.
3. `layout` klasöründe, `fragment_sleep_detail.xml` dosyasını kopyalayın.
4. `sleep_detail_fragment` için navigation'ı ekleyen `navigation.xml` dosyasının güncellenmiş içeriğini kopyalayın.
5. `database` paketinde, `SleepDatabaseDao`'da yeni `getNightWithId()` metodunu ekleyin:

```

/**
 * Verilen nightId ile geceyi seçer ve döndürür.
*/
@Query("SELECT * from daily_sleep_quality_table WHERE nightId = :key")
fun getNightWithId(key: Long): LiveData<SleepNight>

```

6. `res/values/strings`'e aşağıdaki string resource'unu ekleyin

```

<string name="close">Close</string>

```

7. Data binding'i güncellemek için uygulamanızı clean ve rebuild edin.

### Adım 2: Uyku ayrıntıları ekranı için kodu inceleyin

Bu aşamada, uyku gecesi için bir click handler (tıklama işleyicisi) uygulayacaksınız. Bir kez tıklandığında, uygulama o belirli uyku gecesi hakkında ayrıntıları gösteren bir fragment'a gidecektir. Başlangıç kodunuz zaten bu `SleepDetailFragment` için fragment ve navgation graph'i içerir, çünkü bu oldukça fazla koddur (ve fragmentlar ve navigation bu aşamanın bir parçası değildir). Aşağıdaki kod ayrıntılarını öğrenin:

1. Uygulamanızda `sleepdetail` paketini bulun. Bu paket, bir fragment, view model ve bir gecelik uykunun ayrıntılarını görüntüleyen fragment için bir view model factory içerir.
2. `sleepdetail` paketinde, `SleepDetailViewModel` kodunu açın ve inceleyin. Bu view model, constructor'da bir `SleepNight` için bir anahtar ve bir DAO alır.

Class'ın body'si, verilen anahtar için `SleepNight`'ı alacak koda ve **Close** düğmesine basıldığında `SleepTrackerFragment`'e geri navigation'ı kontrol etmek için `navigationToSleepTracker` değişkenine sahiptir.

`getNightWithId()` fonksiyonu, bir `LiveData<SleepNight>` döndürür ve `SleepDatabaseDao`'da (`database` paketinde) tanımlanır.

3. `sleepdetail` paketinde `SleepDetailFragment` kodunu açın ve inceleyin. Data binding, view model ve navigation için observer kurulumuna dikkat edin.
4. `sleepdetail` paketinde `SleepDetailViewModelFactory` kodunu açın ve inceleyin.
5. layout klasöründe, `fragment_sleep_detail.xml` dosyasını inceleyin. View model'dan her view'da görüntülenecek verileri almak için `<data>` tag'inde tanımlanan `sleepDetailViewModel` değişkenine dikkat edin.

Layout, uyku kalitesi için bir `ImageView`, kalite derecelendirmesi için bir `TextView`, uyku uzunluğu için bir `TextView` ve ayrıntı fragment'ını kapatmak için bir `Button` içeren bir `ConstraintLayout` içerir.

6. `navigation.xml` dosyasını açın. `sleep_tracker_fragment` için, `sleep_detail_fragment`'a yeni action'a dikkat edin.

Yeni action, `action_sleep_tracker_fragment_to_sleepDetailFragment`, uyku izleyici fragment'ından ayrıntılar ekranına yapılan navigation'dır.

## <a name="b"></a>Aşama 2 : Öğeleri tıklanabilir yapın

Bu aşamada, dokunulan öğe için bir ayrıntılar ekranı göstererek kullanıcı dokunuşlarına yanıt vermek için `RecyclerView`'ı güncelleyeceksiniz.

Tıklamaları almak ve bunları işlemek (handling) iki parçalı bir görevdir: İlk olarak, tıklamayı dinler ve alırsınız ve hangi öğenin tıklandığını belirlersiniz. Ardından, tıklamaya bir eylemle yanıt verirsiniz.

Peki, bu uygulama için bir  click listener (tıklama dinleyicisi) eklemek için en iyi yer neresidir?

- `SleepTrackerFragment` çok sayıda farklı `View`'a sahiptir. Bu fragment düzeyinde tıklama olaylarını dinlemek, hangi öğenin tıklandığını size söylemez. Size `RecyclerView`'daki bir öğenin mi yoksa başka bir UI öğesinin mi tıklandığını bile söylemez.
- `RecyclerView` düzeyinde dinlerken, kullanıcının listede tam olarak hangi öğeye tıkladığını anlamak zordur.
- Tıklanan bir öğeden bilgi almak için en iyi yer, bir liste öğesini temsil ettiği için `ViewHolder` nesnesidir.

`ViewHolder`, tıklamaları dinlemek için harika bir yer olsa da, bunları işlemek için genellikle doğru yer değildir. Peki, tıklamaları işlemek için en iyi yer neresidir?

- `Adapter`, veri öğelerini viewlarda görüntüler, böylece adapter'daki tıklamaları işleyebilirsiniz. Bununla birlikte, mimari bir bakış açısından, adapter'ın işi, uygulama mantığı ile uğraşmak değil, verileri görüntülemek için uyarlamaktır.
- Tıklamaları genellikle `ViewModel`'de ele almalısınız. `ViewModel`, tıklamaya yanıt olarak ne olması gerektiğini belirlemek için verilere ve mantığa erişebilir.

>İpucu: `RecyclerView`larda click listenerları (tıklama dinleyicilerini) uygulamanın başka yolları da vardır. Ancak bu aşamada birlikte çalıştığınız kodun açıklanması daha kolay ve uygulanması daha kolaydır. Yapımda olan Android uygulamalarında daha fazla deneyim kazandıkça, `RecyclerView`larda click listenerları (tıklama dinleyicilerini) kullanmak için farklı patternlarla karşılaşacaksınız. Tüm bu çeşitli tasarım patternlarının avantajları vardır.

### Adım 1: Bir click listener oluşturun ve onu öğe layout'undan tetikleyin

1. `sleeptracker` paketinde, **SleepNightAdapter.kt**'yi açın.
2. Dosyanın sonunda, en üst düzeyde, `SleepNightListener` adlı yeni bir listener class'ı oluşturun.

```

class SleepNightListener() {
    
}

```

3. `SleepNightListener` class'ının içine bir `onClick()` fonksiyonu ekleyin. Bir liste öğesini görüntüleyen view tıklandığında, view bu `onClick()` fonksiyonunu çağırır. Yine de view'un `android:onClick` özelliğini layout dosyasında daha sonra ayarlamanız gerekecek.

```

class SleepNightListener() {
    fun onClick() = ...
}

```

4. `onClick()`'e `SleepNight` tipinde bir `night` fonksiyon argümanı ekleyin. View, hangi öğeyi görüntülediğini bilir ve tıklamayı işlemek için bu bilgilerin iletilmesi gerekir.

```

class SleepNightListener() {
    fun onClick(night: SleepNight) = 
}

```

5. `onClick()`'in ne yaptığını tanımlamak için, `SleepNightListener` constructor'ında bir `onClickListener` callback argümanı sağlayın ve onu `onClick()`'e atayın.

Tıklamayı işleyen callback, kullanışlı bir tanımlayıcı adına sahip olmalıdır. Adı olarak `clickListener` kullanın. `clickListener` callback'i, veritabanındaki verilere erişmek için yalnızca `night.nightId`'ye ihtiyaç duyar. Bitmiş `SleepNightListener` class'ınız aşağıdaki kod gibi görünmelidir.

```

class SleepNightListener(val clickListener: (sleepId: Long) -> Unit) {
   fun onClick(night: SleepNight) = clickListener(night.nightId)
}

```

6. **res > layout > list_item_sleep_night.xml**'i açın.
7. `data` bloğunun içine, `SleepNightListener` class'ını data binding yoluyla kullanılabilir hale getirmek için yeni bir değişken ekleyin. Yeni `<variable>` öğesine `clickListener` `name`'ini verin. `type`'ını, aşağıda gösterildiği gibi `com.example.android.trackmysleepquality.sleeptracker.SleepNightListener` class'ının tam adına ayarlayın. Artık bu layout'tan `SleepNightListener` içindeki `onClick()` fonksiyonuna erişebilirsiniz.

```

<variable
            name="clickListener"
type="com.example.android.trackmysleepquality.sleeptracker.SleepNightListener" />

```

8. Bu liste öğesinin herhangi bir bölümündeki tıklamaları dinlemek için, `list_item_sleep_night.xml` layout dosyasındaki `ConstraintLayout`'a `android:onClick` özelliğini ekleyin.

Özelliği, aşağıda gösterildiği gibi bir data binding lambdası kullanarak `clickListener.onClick(sleep)` olarak ayarlayın:

```

android:onClick="@{() -> clickListener.onClick(sleep)}"

```

### Adım 2: Click listener'ı view holder'a ve binding nesnesine iletin

1. **SleepNightAdapter.kt**'yi açın.
2. Bir `val clickListener: SleepNightListener` almak için `SleepNightAdapter` class'ının constructor'ını değiştirin. Adapter `ViewHolder`'ı bind ettiğinde, ona bu click listener'ı sağlaması gerekecektir.

```

class SleepNightAdapter(val clickListener: SleepNightListener):
       ListAdapter<SleepNight, SleepNightAdapter.ViewHolder>(SleepNightDiffCallback()) {
       
```

3. `onBindViewHolder()` içinde, click listener'ı `ViewHolder`'a geçirmek için `hold.bind()` çağrısını güncelleyin. Fonksiyon çağrısına bir parametre eklediğiniz için derleyici hatası (compiler error) alırsınız.

```

holder.bind(getItem(position)!!, clickListener)

```

4. Derleyici hatasını düzeltmek için `bind()` öğesine `clickListener` parametresini ekleyin. Bunu yapmak için, imleci hatanın üzerine getirin ve aşağıdaki ekran görüntüsünde gösterildiği gibi için `Alt+Enter` (Windows) veya `Option+Enter` (Mac) tuşlarına basın.

![error fix](https://developer.android.com/codelabs/kotlin-android-training-interacting-with-items/img/b3997303d8426434.png)

5. `ViewHolder` class'ının içinde, `bind()` fonksiyonunun içindeki `binding` nesnesine click listener'ı atayın. Binding nesnesini güncellemeniz gerektiğinden bir hata görüyorsunuz.

```

binding.clickListener = clickListener

```

Adapter constructor'ından bir click listener aldınız ve onu tamamen view holder'a ve binding nesnesine ilettiniz.

6. Data binding', güncellemek için projeyi **Clean** ve **Rebuild** edin. Önbellekleri de geçersiz kılmanız (invalidate caches) gerekebilir. Android Studio yine de bir sonraki bölümde düzeltilecek bir derleyici hatası gösterebilir.

### Adım 3: Bir öğeye dokunulduğunda bir toast gösterin

Artık bir tıklamayı yakalamak için kodunuz var, ancak bir liste öğesine dokunulduğunda ne olduğunu uygulamadınız. En basit yanıt, bir öğeye tıklandığında `nightId`'yi gösteren bir toast görüntülemektir. Bu, bir liste öğesine tıklandığında doğru `nightId`'nin yakalandığını ve aktarıldığını doğrular.

1.  **SleepTrackerFragment.kt**'yi açın.
2.  `onCreateView()` içinde `adapter` değişkenini bulun. Artık bir click listener parametresi beklediği için bir hata gösterdiğine dikkat edin.
3.  `SleepNightAdapter`'a bir lambda geçirerek bir click listener tanımlayın. Bu basit lambda, aşağıda gösterildiği gibi sadece `nightId`'yi gösteren bir toast görüntüler. `Toast`'ı import etmeniz gerekecektir. Aşağıda tam güncellenmiş tanım bulunmaktadır.

```

val adapter = SleepNightAdapter(SleepNightListener { nightId ->
   Toast.makeText(context, "${nightId}", Toast.LENGTH_LONG).show()
})

```

4. Uygulamayı çalıştırın, öğelere dokunun ve doğru `nightId` ile bir toast görüntülediklerini doğrulayın. Öğelerin artan `nightId` değerleri olduğundan ve uygulama önce en son geceyi görüntülediğinden, en düşük `nightId` değerine sahip öğe listenin en altındadır.

## <a name="c"></a>Aşama 3 : Öğe tıklamalarını yönetin
