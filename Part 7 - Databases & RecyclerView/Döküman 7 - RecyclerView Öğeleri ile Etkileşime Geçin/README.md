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

### Adım 1: Uyku ayrıntıları ekranı için kodu inceleyin

## <a name="b"></a>Aşama 2 : Öğeleri tıklanabilir yapın
## <a name="c"></a>Aşama 3 : Öğe tıklamalarını yönetin
