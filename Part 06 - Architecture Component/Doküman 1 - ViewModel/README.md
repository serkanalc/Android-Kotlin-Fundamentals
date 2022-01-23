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

`ViewModel`, konfigürasyon değişikliklerinden kurtulur, bu nedenle, konfigürasyon değişikliklerinden kurtulması gereken veriler için iyi bir yerdir:

- Ekranda görüntülenecek verileri ve bu verileri işlemek için kodu `ViewModel`'a koyun.
- `ViewModel` asla fragmentlara, activitylere veya viewlara referanslar içermemelidir, çünkü activityler, fragmentlar ve viewlar konfigürasyon değişikliklerinden sağ çıkamaz.

![diagram](https://developer.android.com/codelabs/kotlin-android-training-view-model/img/86c9c22e398e0642.png)

Karşılaştırma için, `ViewModel`'ı eklemeden önce ve `ViewModel`'ı ekledikten sonra `GameFragment` UI verilerinin başlangıç uygulamasında nasıl işlendiği aşağıda açıklanmıştır:

- `ViewModel`'ı eklemeden önce: Uygulama, ekran döndürme gibi bir konfigürasyon değişikliğinden geçtiğinde, game fragment yok edilir ve yeniden oluşturulur. Veriler kaybolur.
- `ViewModel`'ı ekledikten ve game fragment'ın UI verilerini `ViewModel`'a taşıdıktan sonra: Fragment'ın görüntülemesi gereken tüm veriler artık `ViewModel`'dır. Uygulama bir konfigürasyon değişikliğinden geçtiğinde, `ViewModel` hayatta kalır ve veriler korunur.

![diagram](https://developer.android.com/codelabs/kotlin-android-training-view-model/img/6451748b74d3b82c.png)

Bu aşamada, uygulamanın UI verilerini, verileri işleme metotlarıyla birlikte `GameViewModel` class'ına taşırsınız. Bunu, konfigürasyon değişiklikleri sırasında verilerin korunması için yaparsınız.

### Adım 1: Veri alanlarını ve veri işlemeyi ViewModel'a taşıyın

Aşağıdaki veri alanlarını ve metotları `GameFragment`'tan `GameViewModel`'a taşıyın:

1. `word`, `score`, ve `wordList` veri alanlarını taşıyın. `word` ve `score`'un `private` olmadığından emin olun.

Viewlara referanslar içerdiğinden, `GameFragmentBinding` binding değişkenini hareket ettirmeyin. Bu değişken, layout'u inflate etmek, click listenerları ayarlamak ve verileri ekranda görüntülemek için kullanılır - fragment'ın sorumlulukları. 

2. `resetList()` ve `nextWord()` metotlarını taşıyın. Bu metotlar ekranda hangi kelimenin gösterileceğine karar verir. 
3. `onCreateView()` metodunun içinden, metot çağrılarını `resetList()`e ve `nextWord()`'ü `GameViewModel`'in `init` bloğuna taşıyın.

Bu metotlar `init` bloğunda olmalıdır, çünkü kelime listesini fragment her oluşturulduğunda değil, `ViewModel` oluşturulduğunda sıfırlamanız gerekir. `GameViewModel`'ın `init` bloğundaki log ifadesini silebilirsiniz.

`GameFragment`'taki `onSkip()` ve `onCorrect()` click handlerları, verileri işlemek ve UI'yı güncellemek için kod içerir. UI'yı güncelleme kodu fragment'ta kalmalıdır, ancak verileri işleme kodunun `ViewModel`'a taşınması gerekir.

Şimdilik, aynı metotları her iki yere de koyun:

1. `onSkip()` ve `onCorrect()` metotlarını `GameFragment`'tan `GameViewModel`'a kopyalayın.
2. `GameViewModel`'da `onSkip()` ve `onCorrect()` metotlarının `private` olmadığından emin olun, çünkü bu metotlara fragment'tan referans edeceksiniz.

Yeniden düzenlemeden sonra `GameViewModel` class'ının kodu:

```kotlin

class GameViewModel : ViewModel() {
   // Geçerli kelime
   var word = ""
   // Geçerli puan
   var score = 0
   // Kelime listesi - tahmin edilecek bir sonraki kelime listenin başında
   private lateinit var wordList: MutableList<String>

   /**
    * Sözcük listesini sıfırlar ve sırayı rastgele ayarlar
    */
   private fun resetList() {
       wordList = mutableListOf(
               "queen",
               "hospital",
               "basketball",
               "cat",
               "change",
               "snail",
               "soup",
               "calendar",
               "sad",
               "desk",
               "guitar",
               "home",
               "railway",
               "zebra",
               "jelly",
               "car",
               "crow",
               "trade",
               "bag",
               "roll",
               "bubble"
       )
       wordList.shuffle()
   }

   init {
       resetList()
       nextWord()
       Log.i("GameViewModel", "GameViewModel oluşturuldu!")
   }
   /**
    * Listedeki bir sonraki kelimeye gider
    */
   private fun nextWord() {
       if (!wordList.isEmpty()) {
           //Listeden bir kelime seçin ve kaldırın
           word = wordList.removeAt(0)
       }
       updateWordText()
       updateScoreText()
   }
 /** Buton tıklamaları için metotlar **/
   fun onSkip() {
       score--
       nextWord()
   }

   fun onCorrect() {
       score++
       nextWord()
   }

   override fun onCleared() {
       super.onCleared()
       Log.i("GameViewModel", "GameViewModel yok edildi!")
   }
}

```

Yeniden düzenlemeden sonra `GameFragment` class'ının kodu:

```kotlin

/**
* Oyunun oynandığı fragment
*/
class GameFragment : Fragment() {


   private lateinit var binding: GameFragmentBinding


   private lateinit var viewModel: GameViewModel


   override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                             savedInstanceState: Bundle?): View? {

       // View'u inflate et ve binding class'ı için bir instance al
       binding = DataBindingUtil.inflate(
               inflater,
               R.layout.game_fragment,
               container,
               false
       )

       Log.i("GameFragment", "ViewModelProvider.get çağrıldı")
       viewModel = ViewModelProvider(this).get(GameViewModel::class.java)

       binding.correctButton.setOnClickListener { onCorrect() }
       binding.skipButton.setOnClickListener { onSkip() }
       updateScoreText()
       updateWordText()
       return binding.root

   }


   /** Buton click handlerları için metotlar **/

   private fun onSkip() {
       score--
       nextWord()
   }

   private fun onCorrect() {
       score++
       nextWord()
   }


   /** UI'yı güncellemek için metotlar **/

   private fun updateWordText() {
       binding.wordText.text = word
   }

   private fun updateScoreText() {
       binding.scoreText.text = score.toString()
   }
}

```

### Adım 2: GameFragment'te click handlerlar ve veri alanlarına referansları güncelleyin

1. `GameFragment`'ta, `onSkip()` ve `onCorrect()` metotlarını güncelleyin. Puanı güncellemek için kodu kaldırın ve bunun yerine `viewModel`'da karşılık gelen `onSkip()` ve `onCorrect()` metotlarını çağırın.
2. `nextWord()` metodunu `ViewModel`'a taşıdığınız için game fragment artık ona erişemez.

`GameFragment`'ta, `onSkip()` ve `onCorrect()` metotlarında `nextWord()` çağrısını `updateScoreText()` ve u`pdateWordText()` ile değiştirin. Bu metotlar, verileri ekranda görüntüler.

```kotlin

private fun onSkip() {
   viewModel.onSkip()
   updateWordText()
   updateScoreText()
}
private fun onCorrect() {
   viewModel.onCorrect()
   updateScoreText()
   updateWordText()
}

```

3. `GameFragment`'ta, bu değişkenler artık `GameViewModel`'da olduğundan, `GameViewModel` değişkenlerini kullanmak için `score` ve `word` değişkenlerini güncelleyin.

```kotlin

private fun updateWordText() {
   binding.wordText.text = viewModel.word
}

private fun updateScoreText() {
   binding.scoreText.text = viewModel.score.toString()
}

```

>**Hatırlatma:** Uygulamanın activityleri, fragmentları ve viewları konfigürasyon değişikliklerinden sağ çıkamadığından, `ViewModel` uygulamanın activitylerine, fragmentlarına veya viewlarına referanslar içermemelidir.

4. `GameViewModel`'da `nextWord()` metodunun içinde `updateWordText()` ve `updateScoreText()` metotlarına yapılan çağrıları kaldırın. Bu metotlar şimdi `GameFragment`'tan çağrılıyor.
5. Uygulamayı oluşturun ve hata olmadığından emin olun. Hatalarınız varsa, projeyi clean ve rebuild edin.
6. Uygulamayı çalıştırın ve oyunu bazı kelimelerle oynayın. Oyun ekranındayken cihazı döndürün. Oryantasyon değişikliğinden sonra geçerli puanın ve geçerli kelimenin korunduğuna dikkat edin.

Harika bir iş! Artık uygulamanızın tüm verileri bir `ViewModel`'da depolanıyor, bu nedenle yapılandırma konfigürasyon sırasında saklanıyor.

## <a name="e"></a>Aşama 5 : Oyunu Bitir butonu için click listener uygulayın

Bu aşamada, **Oyunu Bitir** butonu için click listener'ı uygulayacaksınız.

1. `GameFragment`'ta `onEndGame()` adlı bir metot ekleyin. Kullanıcı **Oyunu Bitir** butonuna dokunduğunda `onEndGame()` metodu çağrılır.

```kotlin

private fun onEndGame() {
   }
   
```

2. `GameFragment`'ta, `onCreateView()` metodunun içinde, **Anladım** ve **Atla** butonları için click listenerları ayarlayan kodu bulun. Bu iki satırın hemen altında, **Oyunu Bitir** butonu için bir click listener ayarlayın. Binding değişkenini, `binding`, kullanın. Click listener'ın içinde `onEndGame()` metodunu çağırın.

```kotlin

binding.endGameButton.setOnClickListener { onEndGame() }

```

3. `GameFragment`'ta, uygulamada puan ekranına gitmek için `gameFinished()` adlı bir metot ekleyin. [Safe Args](https://developer.android.com/topic/libraries/architecture/navigation/navigation-pass-data#Safe-args)'ı kullanarak puanı bir argüman olarak iletin.

```kotlin

/**
* Oyun bittiğinde çağırılır
*/
private fun gameFinished() {
   Toast.makeText(activity, "Oyun yeni bitti", Toast.LENGTH_SHORT).show()
   val action = GameFragmentDirections.actionGameToScore()
   action.score = viewModel.score
   NavHostFragment.findNavController(this).navigate(action)
}

```

4. `onEndGame()` metodunda, `gameFinished()` metodunu çağırın.

```kotlin

private fun onEndGame() {
   gameFinished()
}

```

5. Uygulamayı çalıştırın, oyunu oynayın ve bazı kelimeler arasında dolaşın. **Oyunu Bitir** butonuna dokunun. Uygulamanın puan ekranına gittiğine, ancak nihai puanın görüntülenmediğine dikkat edin. Bunu bir sonraki aşamada düzelteceksiniz.

![app](https://user-images.githubusercontent.com/46448616/150520837-7f1f93f7-e223-44bb-be54-2f76f0220046.png)

## <a name="f"></a>Aşama 6 : Bir ViewModelFactory kullanın

Kullanıcı oyunu bitirdiğinde `ScoreFragment` skoru göstermez. Bir `ViewModel`'ın `ScoreFragment` tarafından görüntülenecek puanı tutmasını istiyorsunuz. [Factory metodu pattern](https://en.wikipedia.org/wiki/Factory_method_pattern)'ını kullanarak `ViewModel` initialization sırasında puan değerini ileteceksiniz.

_Factory metodu pattern_'ı, nesneler oluşturmak için factory metotlarını kullanan bir [creational design pattern](https://en.wikipedia.org/wiki/Creational_pattern)'dır (yaratıcı tasarım kalıbı). _Factory metodu_, aynı class'ın bir instance'ını döndüren bir yöntemdir.

Bu aşamada, score fragment için parametreli bir constructor'a ve `ViewModel`'i instantiate etmek için bir factory metoduna sahip bir `ViewModel` oluşturacaksınız.

1. `score` paketinin altında `ScoreViewModel` adında yeni bir Kotlin class'ı oluşturun. Bu class, score fragment için `ViewModel` olacaktır.
2. `ScoreViewModel` class'ını `ViewModel`'dan extend edin. Nihai puan için bir constructor parametresi ekleyin. Log ifadesi içeren bir `init` bloğu ekleyin.
3. `ScoreViewModel` class'ında, niahi puanı kaydetmek için `score` adlı bir değişken ekleyin.

```kotlin

class ScoreViewModel(finalScore: Int) : ViewModel() {
   // Nihai puan
   var score = finalScore
   init {
       Log.i("ScoreViewModel", "Nihai puan: $finalScore")
   }
}

```

4. `score` paketinin altında `ScoreViewModelFactory` adında başka bir Kotlin class'ı oluşturun. Bu class, `ScoreViewModel` nesnesini instantiate etmekten sorumlu olacaktır.
5. `ScoreViewModelFactory` class'ını `ViewModelProvider.Factory`'den extend edin. Nihai puan için bir constructor parametresi ekleyin.

```kotlin

class ScoreViewModelFactory(private val finalScore: Int) : ViewModelProvider.Factory {
}

```

6. `ScoreViewModelFactory`'de Android Studio, uygulanmamış bir abstract üye hakkında bir hata gösteriyor. Hatayı çözmek için `create()` metodunu override edin. `create()` metodunda, yeni oluşturulan `ScoreViewModel` nesnesini döndürün.

```kotlin

override fun <T : ViewModel?> create(modelClass: Class<T>): T {
   if (modelClass.isAssignableFrom(ScoreViewModel::class.java)) {
       return ScoreViewModel(finalScore) as T
   }
   throw IllegalArgumentException("Unknown ViewModel class")
}

```

7. `ScoreFragment`'ta, `ScoreViewModel` ve `ScoreViewModelFactory` için class değişkenleri oluşturun.

```kotlin

private lateinit var viewModel: ScoreViewModel
private lateinit var viewModelFactory: ScoreViewModelFactory

```

8. `ScoreFragment`'ta, `onCreateView()` içinde, `binding` değişkenini initialize ettikten sonra `viewModelFactory`'yi initialize edin. `ScoreViewModelFactory`'yi kullanın. `ScoreViewModelFactory()`'ye constructor parametresi olarak argüman bundle'ındaki nihai puanı iletin.

```kotlin

viewModelFactory = ScoreViewModelFactory(ScoreFragmentArgs.fromBundle(requireArguments()).score)

```

9. `onCreateView()` içinde, `viewModelFactory`'yi initialize ettikten sonra `viewModel` nesnesini initialize edin. `ViewModelProvider.get()` metodunu çağırın, ilişkili score fragment context'ini ve `viewModelFactory`'yi iletin. Bu, `viewModelFactory` class'ında tanımlanan factory metodunu kullanarak `ScoreViewModel` nesnesini yaratacaktır.

```kotlin

viewModel = ViewModelProvider(this, viewModelFactory)
       .get(ScoreViewModel::class.java)
       
```

10. `onCreateView()` metodunda, `viewModel`'ı initialize ettikten sonra, `scoreText` view'unun metnini `ScoreViewModel`'de tanımlanan nihai puana ayarlayın.

```kotlin

binding.scoreText.text = viewModel.score.toString()
       
```

11. Uygulamanızı çalıştırın ve oyunu oynayın. Bazı veya tüm kelimeler arasında dolaşın ve **Oyunu Bitir**'e dokunun. Score fragment'ın artık nihai puanı gösterdiğine dikkat edin.

12. Opsiyonel: `ScoreViewModel`'da filtre uygulayarak Logcat'teki `ScoreViewModel` loglarını kontrol edin. Skor değeri görüntülenmelidir.

```

2019-02-07 10:50:18.328 com.example.android.guesstheword I/ScoreViewModel: Final score is 15

```

>**Not:** Bu uygulamada, puanı doğrudan **viewModel.score** değişkenine atayabileceğiniz için **ScoreViewModel** için bir **ViewModelFactory** eklemek gerekli değildir. Ancak bazen verilere tam olarak **viewModel** initialize edildiğinde ihtiyaç duyarsınız.

Bu aşamada, `ViewModel`'ı kullanmak için `ScoreFragment` uyguladınız. Ayrıca `ViewModelFactory` interface'ini kullanarak bir `ViewModel` için parametreli bir construtor oluşturmayı da öğrendiniz.
