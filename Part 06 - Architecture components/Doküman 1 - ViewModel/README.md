# <a name="1"></a>ViewModel

- [Başlangıç uygulamasını keşfedin](#a)
- [Başlangıç uygulamasındaki problemleri bulun](#b)
- [GameViewModel'ı oluşturun](#c)
- [GameViewModel'ı doldurun](#d)
- [Oyunu Bitir butonu için click listener uygulayın](#e)
- [Bir ViewModelFactory kullanın](#f)

Bu partta, başlangıç kodundan başlayarak GuessTheWord uygulamasını geliştireceksiniz. GuessTheWord, oyuncuların mümkün olan en yüksek puanı elde etmek için işbirliği yaptığı iki oyunculu, sessiz sinema ([charades](https://en.wikipedia.org/wiki/Charades)) tarzı bir oyundur.

İlk oyuncu uygulamadaki kelimelere bakar ve kelimeyi ikinci oyuncuya göstermemeye dikkat ederek sırayla her birini hareketlerle anlatmaya çalışır. İkinci oyuncu kelimeyi tahmin etmeye çalışır.

Oyunu oynamak için ilk oyuncu cihazda uygulamayı açar ve aşağıdaki ekran görüntüsünde gösterildiği gibi örneğin "gitar" gibi bir kelime görür.

![app](https://developer.android.com/codelabs/kotlin-android-training-view-model/img/8df85c3b8266c7a8.png)

İlk oyuncu, kelimenin kendisini gerçekten söylememeye dikkat ederek kelimeyi canlandırır.

- İkinci oyuncu kelimeyi doğru tahmin ettiğinde, ilk oyuncu, sayımı birer birer artıran ve bir sonraki kelimeyi gösteren **Anladım** butonuna basar.
- İkinci oyuncu kelimeyi tahmin edemezse, ilk oyuncu, sayımı birer birer azaltan ve bir sonraki kelimeye atlayan **Atla** butonuna basar.
- Oyunu bitirmek için **Oyunu Bitir** butonuna basın. (Bu işlev, serideki ilk dökümanın başlangıç kodunda değildir.)

## <a name="a"></a>Aşama 1 : Başlangıç uygulamasını keşfedin

Bu aşamada, başlangıç uygulamasını indirip çalıştırıcak ve kodu inceleyeceksiniz.

### Adım 1: Başlayın

1. GuessTheWord başlangıç kodunu indirin ve projeyi Android Studio'da açın.
2. Uygulamayı Android destekli bir cihazda veya bir emülatörde çalıştırın.
3. Butonlara dokunun. **Atla** butonunun bir sonraki kelimeyi gösterdiğine ve puanı birer birer azalttığına ve **Anladım** butonunun bir sonraki kelimeyi gösterdiğine ve puanı bir artırdığına dikkat edin. **Oyunu Bitir** butonu uygulanmadı, bu nedenle ona dokunduğunuzda hiçbir şey olmuyor.

### Adım 2: Bir kod keşfi yapın

1. Android Studio'da, uygulamanın nasıl çalıştığına dair bir fikir edinmek için kodu keşfedin.
2. Aşağıda açıklanan, özellikle önemli olan dosyalara baktığınızdan emin olun.

#### MainActivity.kt

Bu dosya yalnızca varsayılan, şablon tarafından oluşturulan kodu içerir.

#### res/layout/main_activity.xml

Bu dosya, uygulamanın ana layout'unu içerir. [NavHostFragment](https://developer.android.com/reference/androidx/navigation/fragment/NavHostFragment), kullanıcı uygulamada gezinirken diğer fragmentları barındırır.

#### UI fragments

Başlangıç kodu, `com.example.android.guesstheword.screens` paketinin altında üç farklı pakette üç fragment'a sahiptir:

- `title/TitleFragment`, başlık ekranı için
- `game/GameFragment`, oyun ekranı için
- `score/ScoreFragment`, puan ekranı için

#### screens/title/TitleFragment.kt

Title (başlık) fragment'ı, uygulama başlatıldığında görüntülenen ilk ekrandır. Oyun ekranına gitmek için **Oynat** düğmesine bir click handler ayarlanmıştır.

#### screens/game/GameFragment.kt

Bu, oyunun eyleminin çoğunun gerçekleştiği ana fragment'tır:

- Geçerli kelime ve geçerli puan için değişkenler tanımlanır.
- `resetList()` metodunda tanımlanan `wordList`, oyunda kullanılacak örnek bir kelime listesidir.
- `onSkip()` metodu, **Atla** butonunun click handler'ıdır. Puanı 1 azaltır, ardından `nextWord()` metodunu kullanarak sonraki sözcüğü görüntüler.
- `onCorrect()` metodu, **Anladım** butonunun click handler'ıdır. Bu metot, `onSkip()` metoduna benzer şekilde uygulanır. Tek fark, bu metodun puana çıkarmak yerine 1 eklemesidir.

#### screens/score/ScoreFragment.kt

`ScoreFragment`, oyundaki son ekrandır ve oyuncunun son puanını gösterir. Bu dökümanda, bu ekranı görüntülemek ve nihai puanı göstermek için gerekli uygulamayı yapacaksınız.

#### res/navigation/main_navigation.xml

Navigation graph, fragmentların navigation yoluyla nasıl bağlandığını gösterir:

- Kullanıcı title fragment'tan, game fragment'a gidebilir.
- Kullanıcı game fragment'tan, score fragment'a gidebilir.
- Kullanıcı, score fragment'tan game fragment'a geri dönebilir.

## <a name="b"></a>Aşama 2 : Başlangıç uygulamasındaki problemleri bulun

Bu aşamada, GuessTheWord başlangıç uygulamasıyla ilgili sorunları bulacaksınız.

1. Başlangıç kodunu çalıştırın ve her kelimeden sonra **Atla** veya **Anladım**'a dokunarak oyunu birkaç kelimeyle oynayın.
2. Oyun ekranı artık bir kelimeyi ve mevcut skoru gösteriyor. Cihazı veya emülatörü döndürerek ekran yönünü değiştirin. Mevcut puanın kaybolduğuna dikkat edin.
3. Oyunu birkaç kelimeyle daha çalıştırın. Oyun ekranı bir miktar puanla görüntülendiğinde, uygulamayı kapatın ve yeniden açın. Uygulama state'i kaydedilmediği için oyunun en baştan yeniden başladığına dikkat edin.
4. Oyunu birkaç kelime ile oynayın, ardından **Oyunu Bitir** butonuna dokunun. Dikkat edin, hiçbir şey olmuyor.

Uygulamadaki sorunlar:

- Başlangıç uygulaması, cihaz yönünün değişmesi veya uygulamanın kapanıp yeniden başlatılması gibi konfigürasyon değişiklikleri sırasında uygulama state'ini kaydetmez ve geri yüklemez. Bu sorunu [`onSaveInstanceState()`](https://developer.android.com/guide/components/activities/activity-lifecycle#save-simple-lightweight-ui-state-using-onsaveinstancestate) callback'ini kullanarak çözebilirsiniz. Ancak `onSaveInstanceState()` metodunu kullanmak, state'i bir bundle'a kaydetmek için fazladan kod yazmanızı ve bu state'i almak için mantığı uygulamanızı gerektirir. Ayrıca, saklanabilecek veri miktarı minimumdur.
- Kullanıcı **Oyunu Bitir** butonuna dokunduğunda oyun ekranı skor ekranına gitmiyor.

Bu dökümanda öğreneceğiniz uygulama mimarisi ([app architecture](https://developer.android.com/jetpack/docs/guide)) bileşenlerini kullanarak bu sorunları çözebilirsiniz.

### App Architecture

App architecture, uygulamalarınızın classlarını ve aralarındaki ilişkileri, kodun organize edilmesi, belirli senaryolarda iyi performans göstermesi ve birlikte çalışması kolay olacak şekilde tasarlamanın bir yoludur. Bu dört dökümandan oluşan sette, GuessTheWord uygulamasında yaptığınız iyileştirmeler, [Android app architecture](https://developer.android.com/jetpack/docs/guide) yönergelerini takip eder ve [Android Architecture Components](https://developer.android.com/jetpack/#architecture-components)'ı kullanırsınız. Android app architecture, [MVVM](https://en.wikipedia.org/wiki/Model–view–viewmodel) (model-view-viewmodel) mimari modeline benzer.

GuessTheWord uygulaması, [separation of concerns](https://en.wikipedia.org/wiki/Separation_of_concerns) tasarım ilkesini takip eder ve her class ayrı bir concern'i ele alan classlara ayrılır. Dersin bu ilk dökümanında, birlikte çalıştığınız classlar bir UI denetleyicisi (UI Controller), bir `ViewModel` ve bir `ViewModelFactory`'dir.

#### UI Controller

Bir UI controller, `Activity` veya `Fragment` gibi UI tabanlı bir sınıftır. Bir UI controller yalnızca, viewları görüntüleme ve kullanıcı girdisini yakalama gibi UI ve işletim sistemi etkileşimlerini işleyen mantığı içermelidir. Görüntülenecek metni belirleyen mantık gibi karar verme mantığını UI controller'a koymayın.

GuessTheWord başlangıç kodunda, UI controllerlar üç fragment'tır: `GameFragment`, `ScoreFragment` ve `TitleFragment`. "Separation of concerns" tasarım ilkesini izleyen `GameFragment`, yalnızca oyun öğelerini ekrana çizmekten ve kullanıcının butonlara ne zaman dokunduğunu bilmekten sorumludur, başka bir şey değil. Kullanıcı bir butona dokunduğunda, bu bilgi `GameViewModel`'e iletilir.

#### ViewModel

Bir [`ViewModel`](https://developer.android.com/reference/android/arch/lifecycle/ViewModel), `ViewModel` ile ilişkili bir fragment veya activity'de görüntülenecek verileri tutar. Bir `ViewModel`, UI controller tarafından görüntülenecek verileri hazırlamak için veriler üzerinde basit hesaplamalar ve dönüşümler yapabilir. Bu mimaride, karar verme işlemini `ViewModel` gerçekleştirir.

`GameViewModel`, puan değeri, sözcük listesi ve geçerli sözcük gibi verileri tutar, çünkü bu, ekranda görüntülenecek verilerdir. `GameViewModel`, verilerin mevcut state'inin ne olduğuna karar vermek için basit hesaplamalar yapmak için iş mantığını (business logic) da içerir.

#### ViewModelFactory

Bir [ViewModelFactory](https://developer.android.com/reference/android/arch/lifecycle/ViewModelProvider.Factory), constructor parametreleri olsun veya olmasın ViewModel nesnelerini başlatır.

![viewmodel](https://developer.android.com/codelabs/kotlin-android-training-view-model/img/d115344705100cf1.png)

Daha sonraki dökümanlarda, UI controllerlar ve `ViewModel` ile ilgili diğer Android Architecture Components hakkında bilgi edineceksiniz.

## <a name="c"></a>Aşama 3 : GameViewModel'ı oluşturun

[`ViewModel`](https://developer.android.com/reference/android/arch/lifecycle/ViewModel.html) class'ı, UI ile ilgili verileri depolamak ve yönetmek için tasarlanmıştır. Bu uygulamada, her `ViewModel` bir fragment ile ilişkilendirilir.

Bu aşamada, uygulamanıza ilk `ViewModel`'ınızı, `GameFragment` için `GameViewModel`'ı ekleyeceksiniz. Ayrıca `ViewModel`'ın lifecycle-aware olmasının ne anlama geldiğini de öğreneceksiniz.

### Adım 1: GameViewModel class'ını ekleyin

1. `build.gradle(module:app)` dosyasını açın. `dependencies` bloğunun içine, `ViewModel` için Gradle dependency'sini ekleyin.

Kütüphanenin [en son sürümünü](https://developer.android.com/jetpack/androidx/releases/lifecycle) kullanıyorsanız, çözüm uygulaması beklendiği gibi derlenmelidir. Olmazsa, sorunu çözmeyi deneyin veya aşağıda gösterilen sürüme geri dönün.

```kotlin

//ViewModel
implementation 'androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0'

```

2. Pakette `screens/game/` klasöründe `GameViewModel` adında yeni bir Kotlin class'ı oluşturun.
3. `GameViewModel` class'ının abstarct [`ViewModel`](https://developer.android.com/reference/android/arch/lifecycle/ViewModel) class'ını extend etmesini sağlayın.
4. `ViewModel`'in nasıl lifecycle-aware olduğunu daha iyi anlamanıza yardımcı olmak için bir `log` ifadesiyle bir `init` bloğu ekleyin.

```kotlin

class GameViewModel : ViewModel() {
   init {
       Log.i("GameViewModel", "GameViewModel created!")
   }
}

```

### Adım 2: onCleared()'ı override edin ve loglama ekleyin

İlişkili fragment ayrıldığında (detached) veya activity bittiğinde `ViewModel` yok edilir. `ViewModel` yok edilmeden hemen önce, kaynakları temizlemek için `onCleared()` callback'i çağrılır.

1. `GameViewModel` class'ında `onCleared()` metodunu override edin.
2. `GameViewModel` lifecycle'ını izlemek için `onCleared()` içine bir log ifadesi ekleyin.

```kotlin

override fun onCleared() {
   super.onCleared()
   Log.i("GameViewModel", "GameViewModel destroyed!")
}

```

### Adım 3: GameViewModel'i game fragment ile ilişkilendirin

Bir `ViewModel`'in bir UI controller ile ilişkilendirilmesi gerekir. İkisini ilişkilendirmek için, UI controller'ın içindeki `ViewModel`'e bir referans oluşturursunuz.

Bu adımda, `GameFragment` olan ilgili UI controller'ın içinde `GameViewModel`'ın bir referansını oluşturursunuz.

1. `GameFragment` class'ında, class değişkeni olarak en üst düzeyde `GameViewModel` türünde bir alan ekleyin.

```kotlin

private lateinit var viewModel: GameViewModel

```

### Adım 4: ViewModel'ı initialize edin

Ekran döndürme gibi konfigürasyon değişiklikleri sırasında, fragmentlar gibi UI controllerlar yeniden oluşturulur. Ancak, `ViewModel` instanceları hayatta kalır. `ViewModel` instance'ını `ViewModel` class'ını kullanarak oluşturursanız, fragment her yeniden oluşturulduğunda yeni bir nesne oluşturulur. Bunun yerine, bir [`ViewModelProvider`](https://developer.android.com/reference/androidx/lifecycle/ViewModelProvider) kullanarak `ViewModel` instance'ını oluşturun.

![diagram](https://developer.android.com/codelabs/kotlin-android-training-view-model/img/4b1c6b4b4c62a8ef.png)

>**Önemli:** Doğrudan bir `ViewModel` instance'ını instantiate etmek yerine `ViewModel` nesneleri oluşturmak için her zaman [`ViewModelProvider`](https://developer.android.com/reference/androidx/lifecycle/ViewModelProvider) kullanın.

`ViewModelProvider` nasıl çalışır:

- `ViewModelProvider`, varsa mevcut bir `ViewModel` döndürür veya mevcut değilse yeni bir tane oluşturur.
- `ViewModelProvider`, verilen kapsamla (scope) (bir activity veya bir fragment) ilişkili olarak bir `ViewModel` instance'ı oluşturur.
- Oluşturulan `ViewModel`, scope canlı olduğu sürece korunur. Örneğin, scope bir fragment ise, fragment ayrılana kadar (detach) `ViewModel` korunur.

Bir `ViewModelProvider` oluşturmak için [`ViewModelProvider.get()`](https://developer.android.com/reference/androidx/lifecycle/ViewModelProvider#get(java.lang.Class%3CT%3E)) metodunu kullanarak `ViewModel`'i initialize edin:

1. `GameFragment` class'ında `viewModel` değişkenini initialize edin. Bu kodu, binding değişkeninin tanımından sonra `onCreateView()` içine koyun. `ViewModelProvider.get()` metodunu kullanın ve ilişkili `GameFragment` context'ini ve `GameViewModel` class'ını iletin.
2. `ViewModel` nesnesinin initialization'ının üstünde, `ViewModelProvider.get()` metot çağrısını log'a kaydetmek için bir log ifadesi ekleyin.

```kotlin

Log.i("GameFragment", "Called ViewModelProvider.get")
viewModel = ViewModelProvider(this).get(GameViewModel::class.java)

```

3. Uygulamayı çalıştırın. Android Studio'da **Logcat** bölmesini açın ve `Game` ile filtreleyin. Cihazınızdaki veya emülatörünüzdeki **Oynat** düğmesine dokunun. Oyun ekranı açılacaktır.

Logcat'te gösterildiği gibi, `GameFragment`'in `onCreateView()` metodu, `GameViewModel`'i oluşturmak için `ViewModelProvider.get()` metodunu çağırır. `GameFragment`'e ve `GameViewModel`'e eklediğiniz log ifadeleri Logcat'te görünür.

```

I/GameFragment: Called ViewModelProvider.get
I/GameViewModel: GameViewModel created!

```

4. Cihazınızda veya emülatörünüzde otomatik döndürme ayarını etkinleştirin ve ekran yönünü birkaç kez değiştirin. `GameFragment` her seferinde yok edilir ve yeniden oluşturulur, bu nedenle her seferinde `ViewModelProvider.get()` çağrılır. Ancak `GameViewModel` yalnızca bir kez oluşturulur ve her çağrı için yeniden oluşturulmaz veya yok edilmez.

```

I/GameFragment: Called ViewModelProvider.get
I/GameViewModel: GameViewModel created!
I/GameFragment: Called ViewModelProvider.get
I/GameFragment: Called ViewModelProvider.get
I/GameFragment: Called ViewModelProvider.get

```

5. Oyundan veya oyun fragment'tan çıkın. `GameFragment` yok edildi. İlişkili `GameViewModel` de yok edilir ve `onCleared()` callback'i çağrılır.

```

I/GameFragment: Called ViewModelProvider.get
I/GameViewModel: GameViewModel created!
I/GameFragment: Called ViewModelProvider.get
I/GameFragment: Called ViewModelProvider.get
I/GameFragment: Called ViewModelProvider.get
I/GameViewModel: GameViewModel destroyed!

```

## <a name="d"></a>Aşama 4 : GameViewModel'ı doldurun
## <a name="e"></a>Aşama 5 : Oyunu Bitir butonu için click listener uygulayın
## <a name="f"></a>Aşama 6 : Bir ViewModelFactory kullanın
