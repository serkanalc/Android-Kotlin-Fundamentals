# <a name="1"></a>LiveData & LiveData Observarları

- [Başlayın](#a)
- [GameViewModel'a LiveData ekleyin](#b)
- [LiveData nesnelerine observerlar bağlayın](#c)
- [LiveData'yı encapsulate edin](#d)
- [Bir oyun bitti event'i ekleyin](#e)
- [ScoreViewModel'a LiveData ekleyin](#f)
- [Tekrar Oyna butonu ekleyin](#g)

Bu dokümanda, başlangıç kodundan başlayarak GuessTheWord uygulamasını geliştireceksiniz. GuessTheWord, oyuncuların mümkün olan en yüksek puanı elde etmek için işbirliği yaptığı iki oyunculu, sessiz sinema ([charades](https://en.wikipedia.org/wiki/Charades)) tarzı bir oyundur.

İlk oyuncu uygulamadaki kelimelere bakar ve kelimeyi ikinci oyuncuya göstermemeye dikkat ederek sırayla her birini hareketlerle anlatmaya çalışır. İkinci oyuncu kelimeyi tahmin etmeye çalışır.

Oyunu oynamak için ilk oyuncu cihazda uygulamayı açar ve aşağıdaki ekran görüntüsünde gösterildiği gibi örneğin "gitar" gibi bir kelime görür.

![app](https://developer.android.com/codelabs/kotlin-android-training-view-model/img/8df85c3b8266c7a8.png)

İlk oyuncu, kelimenin kendisini gerçekten söylememeye dikkat ederek kelimeyi canlandırır.

- İkinci oyuncu kelimeyi doğru tahmin ettiğinde, ilk oyuncu, sayımı birer birer artıran ve bir sonraki kelimeyi gösteren **Anladım** butonuna basar.
- İkinci oyuncu kelimeyi tahmin edemezse, ilk oyuncu, sayımı birer birer azaltan ve bir sonraki kelimeye atlayan **Atla** butonuna basar.
- Oyunu bitirmek için **Oyunu Bitir** butonuna basın. (Bu işlev, serideki ilk dökümanın başlangıç kodunda değildir.)

Bu partta, kullanıcı uygulamadaki tüm kelimeler arasında geçiş yaptığında oyunu sona erdirmek için bir event ekleyerek GuessTheWord uygulamasını geliştireceksiniz. Ayrıca, kullanıcının oyunu tekrar oynayabilmesi için score fagment'ına bir **Tekrar Oynat** butonu ekleyeceksiniz.

![app screens](https://user-images.githubusercontent.com/46448616/151831290-a92d55a9-0fe9-4a63-966f-5c8bab7c6c78.png)

## <a name="a"></a>Aşama 1 : Başlayın

Bu aşamada, bu doküman için başlangıç kodunuzu bulup çalıştıracaksınız. Başlangıç kodunuz olarak önceki dokümanda oluşturduğunuz GuessTheWord uygulamasını kullanabilir veya bir başlangıç uygulaması indirebilirsiniz.

1. (Opsiyonel) Önceki dokümandaki kodunuzu kullanmıyorsanız, bu doküman için başlangıç kodunu indirin. Kodu açın ve projeyi Android Studio'da açın.
2. Uygulamayı çalıştırın ve oyunu oynayın.
3. **Atla** butonunun bir sonraki kelimeyi gösterdiğine ve puanı birer birer azalttığına ve **Anladım** butonunun bir sonraki kelimeyi gösterdiğine ve puanı bir artırdığına dikkat edin. **Oyunu Bitir** butonu oyunu bitirir.

## <a name="b"></a>Aşama 2 : GameViewModel'a LiveData ekleyin

[`LiveData`](https://developer.android.com/topic/libraries/architecture/livedata), lifecycle-aware olan observable (gözlemlenebilir) bir data holder class'ıdır. Örneğin, GuessTheWord uygulamasında bir `LiveData`'yı mevcut skorun etrafına sarabilirsiniz. Bu dokümanda, `LiveData`'nın çeşitli özellikleri hakkında bilgi edineceksiniz:

- `LiveData` observable'dır, yani `LiveData` nesnesi tarafından tutulan veriler değiştiğinde bir observer'a bildirilir.
- `LiveData` verileri tutar; `LiveData`, herhangi bir veriyle kullanılabilen bir sarmalayıcıdır (wrapper).
- `LiveData`, lifecycle-aware'dir. `LiveData`'ya bir observer eklediğinizde, observer bir [`LifecycleOwner`](https://developer.android.com/topic/libraries/architecture/lifecycle#lco) (genellikle bir Activity veya Fragment) ile ilişkilendirilir. LiveData, yalnızca [`STARTED`](https://developer.android.com/reference/android/arch/lifecycle/Lifecycle.State.html#STARTED) veya [`RESUMED`](https://developer.android.com/reference/android/arch/lifecycle/Lifecycle.State.html#RESUMED) gibi aktif bir lifecycle state'inde olan observarları günceller. `LiveData` ve observation (gözlem) hakkında daha fazla bilgiyi [burada](https://developer.android.com/topic/libraries/architecture/livedata.html#work_livedata) bulabilirsiniz.

Bu aşamada, `GameViewModel`'deki geçerli puanı ve geçerli sözcük verilerini [`LiveData`](https://developer.android.com/reference/android/arch/lifecycle/LiveData.html)'ya dönüştürerek herhangi bir veri türünü `LiveData` nesnelerine nasıl saracağınızı öğreneceksiniz. Daha sonraki bir aşamada, bu `LiveData` nesnelerine bir observer ekleyecek ve `LiveData`'yı nasıl gözlemleyeceğinizi öğrenirsiniz.

### Adım 1: LiveData'yı kullanmak için score ve word'ü değiştirin

1. `screens/game` paketinin altında `GameViewModel` dosyasını açın.
2. `score` ve `word` değişkenlerinin türünü [`MutableLiveData`](https://developer.android.com/reference/android/arch/lifecycle/MutableLiveData) olarak değiştirin.

`MutableLiveData`, değeri değiştirilebilen bir `LiveData`'dır. `MutableLiveData` bir generic class'tır, bu nedenle tuttuğu veri türünü belirtmeniz gerekir.

```kotlin

// Geçerli kelime
val word = MutableLiveData<String>()
// Geçerli puan
val score = MutableLiveData<Int>()

```

3. `GameViewModel`'da, `init` bloğunun içinde, `score` ve `word`'ü initialize edin. Bir `LiveData` değişkeninin değerini değiştirmek için değişken üzerinde `setValue()` metodunu kullanırsınız. Kotlin'de `value` özelliğini kullanarak `setValue()`'yu çağırabilirsiniz.

```kotlin

init {

   word.value = ""
   score.value = 0
  ...
}

```

### Adım 2: LiveData nesne referansını güncelleyin

`score` ve `word` değişkenleri artık `LiveData` türündedir. Bu adımda, `value` özelliğini kullanarak bu değişkenlere yapılan referansları değiştirirsiniz.

1. `GameViewModel`'da, `onSkip()` metodunda `score`'u, `score.value` olarak değiştirin. `score`'un muhtemelen `null` olmasıyla ilgili hataya dikkat edin. Hemen bu hatayı düzelteceksiniz.
2. Hatayı çözmek için `onSkip()` içindeki `score.value` için bir `null` kontrol ekleyin. Ardından, çıkarma işlemini `null`-safety ile gerçekleştiren, skordaki [`minus()`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/minus.html) fonksiyonunu çağırın.

```kotlin

fun onSkip() {
   score.value = (score.value)?.minus(1)
   nextWord()
}

```

3. `onCorrect()` metodunu aynı şekilde güncelleyin: `score` değişkenine bir `null` kontrol ekleyin ve [`plus()`](url) fonksiyonunu kullanın.

```kotlin

fun onCorrect() {
   score.value = (score.value)?.plus(1)
   nextWord()
}

```

4. `GameViewModel`'da `nextWord()` metodunun içinde, `word` referansını `word.value` olarak değiştirin.

```kotlin

private fun nextWord() {
   if (!wordList.isEmpty()) {
       //Listeden bir kelime seçin ve kaldırın
       word.value = wordList.removeAt(0)
   }
}

```

5. `GameFragment`'ta `updateWordText()` metodunun içinde, `viewModel.word` referansını `viewModel.word.value` olarak değiştirin.

```kotlin

/** UI'yı güncellemek için metotlar **/
private fun updateWordText() {
   binding.wordText.text = viewModel.word.value
}

```

6. `GameFragment`'ta `updateScoreText()` metodunun içinde, `viewModel.score` referansını `viewModel.score.value` olarak değiştirin.

```kotlin

private fun updateScoreText() {
   binding.scoreText.text = viewModel.score.value.toString()
}

```

7. `GameFragment`'ta, `gameFinished()` metodunun içinde, `viewModel.score` referansını `viewModel.score.value` olarak değiştirin. Gerekli `null`-safety kontrolünü ekleyin.

```kotlin

private fun gameFinished() {
   Toast.makeText(activity, "Oyun yeni bitti", Toast.LENGTH_SHORT).show()
   val action = GameFragmentDirections.actionGameToScore()
   action.score = viewModel.score.value?:0
   NavHostFragment.findNavController(this).navigate(action)
}

```

8. Kodunuzda hata olmadığından emin olun. Uygulamanızı derleyin ve çalıştırın. Uygulamanın işlevselliği öncekiyle aynı olmalıdır.


## <a name="c"></a>Aşama 3 : LiveData nesnelerine observerlar bağlayın

Bu aşama, score ve word verilerini `LiveData` nesnelerine dönüştürdüğünüz önceki aşamayla yakından ilişkilidir. Bu aşamada, bu `LiveData` nesnelerine [`Observer`](https://developer.android.com/reference/android/arch/lifecycle/Observer.html) nesneleri eklersiniz. Fragment view'unu ([`viewLifecycleOwner`](https://developer.android.com/reference/kotlin/androidx/fragment/app/Fragment.html#getviewlifecycleowner)) [`LifecycleOwner`](https://developer.android.com/reference/androidx/lifecycle/LifecycleOwner.html) olarak kullanacaksınız.

>**viewLifecycleOwner neden kullanılır?**
>Fragment'ın kendisi yok edilmese bile, kullanıcı bir fragment'tan uzaklaştığında fragment viewları yok edilir. Bu temelde iki lifecycle yaratır, fragment lifecycle ve fragment view lifecycle. Fragment view lifecycle yerine fragment lifecycle'a referansta bulunmak, fragment view'unu güncellerken [ince hatalara](https://www.youtube.com/watch?v=pErTyQpA390&feature=youtu.be&t=349) neden olabilir. Bu nedenle, fragment'ın view'unu etkileyen observarlar kurarken şunları yapmalısınız:
>1. `onCreateView()` içinde observarları ayarlayın
>2. `viewLifecycleOwner`'ı observarlara iletin

1. `GameFragment`'ta, `onCreateView()` metodunun içinde, geçerli puan olan `viewModel.score` için `LiveData` nesnesine bir `Observer` nesnesi ekleyin. `observe()` metodunu kullanın ve kodu `viewModel`'in başlatılmasından sonra yerleştirin. Kodu basitleştirmek için bir lambda ifadesi kullanın. (_Bir lambda ifadesi, bildirilmeyen, ancak hemen bir ifade olarak geçirilen anonim bir işlevdir._)

```kotlin

viewModel.score.observe(viewLifecycleOwner, Observer { newScore ->
})

```

`Observer`'a referansı çözümleyin. Bunu yapmak için `Observer`'a tıklayın, `Alt+Enter`'a (Mac'te `Option+Enter`) basın ve `androidx.lifecycle.Observer`'ı import edin.

2. Yeni oluşturduğunuz observer, gözlemlenen `LiveData` nesnesi tarafından tutulan veriler değiştiğinde bir event alır. Observer'ın içinde, `TextView` puanını yeni puanla güncelleyin.


```kotlin

/** LiveData gözlem ilişkisi kurma **/
viewModel.score.observe(viewLifecycleOwner, Observer { newScore ->
   binding.scoreText.text = newScore.toString()
})

```

3. Geçerli word `LiveData` nesnesine bir `Observer` nesnesi ekleyin. Mevcut puana bir `Observer` nesnesi eklediğiniz gibi yapın.


```kotlin

/** LiveData gözlem ilişkisi kurma **/
viewModel.word.observe(viewLifecycleOwner, Observer { newWord ->
   binding.wordText.text = newWord
})

```

`score`'un veya `word`'ün değeri değiştiğinde, ekranda görüntülenen `score` veya `word` artık otomatik olarak güncellenir.

4. `GameFragment`'ta `updateWordText()` ve `updateScoreText()` metotlarını ve bunlara yapılan tüm referansları silin. Artık bunlara ihtiyacınız yok çünkü text viewlar `LiveData` observer metotlarıyla güncelleniyor.
5. Uygulamanızı çalıştırın. Oyun uygulamanız tam olarak eskisi gibi çalışmalıdır, ancak şimdi `LiveData` ve `LiveData` observerlarını kullanıyor.

## <a name="d"></a>Aşama 4 : LiveData'yı encapsulate edin

_Encapsulation_, bir nesnenin bazı alanlarına doğrudan erişimi kısıtlamanın bir yoludur. Bir nesneyi encapsulate ettiğinizde, private internal alanları değiştiren bir dizi public metodu ortaya çıkarırsınız. Encapsulation'ı kullanarak, diğer classların bu internal alanları nasıl değiştirdiğini kontrol edersiniz.

Geçerli kodunuzda, herhangi bir external class, `value` özelliğini kullanarak, örneğin `viewModel.score.value` kullanarak, `score` ve `word` değişkenlerini değiştirebilir. Bu dokümanda geliştirdiğiniz uygulamada önemli olmayabilir, ancak bir üretim uygulamasında `ViewModel` nesnelerindeki veriler üzerinde kontrol sahibi olmak istersiniz.

Uygulamanızdaki verileri yalnızca `ViewModel` düzenlemelidir. Ancak UI controllerların verileri okuması gerekir, bu nedenle veri alanları tamamen private olamaz. Uygulamanızın verilerini encapsulate etmek için hem [`MutableLiveData`](https://developer.android.com/reference/android/arch/lifecycle/MutableLiveData) hem de [`LiveData`](https://developer.android.com/reference/android/arch/lifecycle/LiveData) nesnelerini kullanırsınız.

`MutableLiveData` vs `LiveData`:

- Adından da anlaşılacağı gibi, bir `MutableLiveData` nesnesindeki veriler değiştirilebilir. `ViewModel` içinde, veriler düzenlenebilir olmalıdır, bu nedenle `MutableLiveData` kullanır.
- `LiveData` nesnesindeki veriler okunabilir ancak değiştirilemez. `ViewModel`'ın dışından, veriler okunabilir olmalı, ancak düzenlenemez olmalıdır, bu nedenle veriler `LiveData` olarak gösterilmelidir.

Bu stratejiyi gerçekleştirmek için bir Kotlin [backing property](https://kotlinlang.org/docs/reference/properties.html#backing-properties) (destek özelliği) kullanırsınız. Bir backing property, tam nesneden farklı bir getter'dan bir şey döndürmenize izin verir. Bu aşamada, GuessTheWord uygulamasındaki `score` ve `word` nesneleri için bir backing property uygulayacaksınız.

### score ve word'e bir backing property ekleyin

1. `GameViewModel`'da mevcut `score` nesnesini `private` yapın.
2. Backing propertylerde kullanılan adlandırma kuralına uymak için `score`'u `_score` olarak değiştirin. `_score` özelliği artık internal olarak kullanılacak oyun score'unun değiştirilebilir versiyonudur.
3. `LiveData` türünün `score` adı verilen bir public versiyonunu oluşturun.

```kotlin

// Mevcut puan
private val _score = MutableLiveData<Int>()
val score: LiveData<Int>

```

4. Bir initialization hatası görüyorsunuz. Bu hata, `GameFragment` içinde `score`'un bir `LiveData` referansı olması ve `score`'un artık setter'ına erişememesi nedeniyle oluşur. Kotlin'deki getter ve setterlar hakkında daha fazla bilgi edinmek için bkz. [Getters and Setters](https://kotlinlang.org/docs/reference/properties.html#getters-and-setters).

Hatayı çözmek için `GameViewModel`'daki `score` nesnesi için [`get()`](https://kotlinlang.org/docs/reference/properties.html#getters-and-setters) metodunu override edin ve `_score` backing property'sini döndürün.

```kotlin

val score: LiveData<Int>
   get() = _score

```

5. `GameViewModel`'da, `score` referanslarını internal değişken versiyonu olan `_score` ile değiştirin.

```kotlin

init {
   ...
   _score.value = 0
   ...
}

...
fun onSkip() {
   _score.value = (score.value)?.minus(1)
  ...
}

fun onCorrect() {
   _score.value = (score.value)?.plus(1)
   ...
}

```

6. `word` nesnesini `_word` olarak yeniden adlandırın ve `score` nesnesi için yaptığınız gibi bunun için bir backing property ekleyin.


```kotlin

// Mevcut kelime
private val _word = MutableLiveData<String>()
val word: LiveData<String>
   get() = _word
...
init {
   _word.value = ""
   ...
}
...
private fun nextWord() {
   if (!wordList.isEmpty()) {
       //Listeden bir kelime seçin ve kaldırın
       _word.value = wordList.removeAt(0)
   }
}

```

Harika iş, `LiveData` nesneleri `word` ve `score`'u encapsulate ettiniz.

## <a name="e"></a>Aşama 5 : Bir oyun bitti event'i ekleyin

Kullanıcı **Oyunu Bitir** butonuna dokunduğunda mevcut uygulamanız puan ekranına gider. Buna ek olarak, oyuncular tüm kelimeler arasında geçiş yaptıklarında uygulamanın puan ekranına gitmesini istiyorsunuz. Oyuncular son kelimeyi bitirdikten sonra, kullanıcının butona dokunmasına gerek kalmaması için oyunun otomatik olarak bitmesini istiyorsunuz.

Bu işlevi uygulamak için, tüm kelimeler gösterildiğinde tetiklenecek ve `ViewModel`'dan fragment'a iletilecek bir event'e ihtiyacınız vardır. Bunu yapmak için, oyunla tamamlanmış bir event'i modellemek için `LiveData` observer pattern'ını kullanırsınız.

### Observer pattern

_Observer pattern_ bir yazılım tasarım pattern'ıdır. Nesneler arasındaki iletişimi belirtir: bir _observable_ (gözlemin "öznesi") ve _observerlar_. Bir observable, state'indeki değişiklikler hakkında observerları bilgilendiren bir nesnedir.

![observer pattern](https://developer.android.com/codelabs/kotlin-android-training-live-data/img/b608df5e5e5fa4f8.png)

Bu uygulamadaki `LiveData` durumunda, observable (konu) `LiveData` nesnesidir ve observerlar, fragmentlar gibi UI controllerlarındaki metotlardır. `LiveData` içine sarılmış veriler değiştiğinde bir state değişikliği gerçekleşir. `LiveData` classları, `ViewModel`'dan fragment'a iletişim kurmak için çok önemlidir.

### Adım 1: Oyun bitti event'ini tespit etmek için LiveData kullanın

Bu görevde, bir oyun bitti event'ini modellemek için `LiveData` observer pattern'ı kullanacaksınız.

1. `GameViewModel`'da, `_eventGameFinish` adlı bir `Boolean` `MutableLiveData` nesnesi oluşturun. Bu nesne oyunun bittiği event'i tutacaktır.
2. `_eventGameFinish` nesnesini initialize ettikten sonra `eventGameFinish` adlı bir backing property oluşturun ve initialize edin.

```kotlin

// Oyunun sonunu tetikleyen event
private val _eventGameFinish = MutableLiveData<Boolean>()
val eventGameFinish: LiveData<Boolean>
   get() = _eventGameFinish

```

3. `GameViewModel`'da bir `onGameFinish()` metodu ekleyin. Metotta, oyun bitti event'ini, `eventGameFinish`, `true` olarak ayarlayın.

```kotlin

/** Oyun bitti event'i için metot **/
fun onGameFinish() {
   _eventGameFinish.value = true
}

```

4. Game `ViewModel`'da, `nextWord()` metodunun içinde, kelime listesi boşsa oyunu bitirin.

```kotlin

private fun nextWord() {
   if (wordList.isEmpty()) {
       onGameFinish()
   } else {
       //Listeden bir _word seçin ve kaldırın
       _word.value = wordList.removeAt(0)
   }
}

```

5. `GameFragment`'ta, `onCreateView()` içinde, `viewModel`'ı initialize ettikten sonra eventGameFinish'e bir observer ekleyin. [`observe()`](https://developer.android.com/reference/android/arch/lifecycle/LiveData.html#observe(android.arch.lifecycle.LifecycleOwner,%0Aandroid.arch.lifecycle.Observer%3CT%3E)) metodunu kullanın. Lambda fonksiyonunun içinde `gameFinished()` metodunu çağırın.

```kotlin

// Oyun bitti event'i için observer
viewModel.eventGameFinish.observe(viewLifecycleOwner, Observer<Boolean> { hasFinished ->
   if (hasFinished) gameFinished()
})

```

6. Uygulamanızı çalıştırın, oyunu oynayın ve tüm kelimeleri geçin. Siz `Oyunu Bitir`'e dokunana kadar, oyun bölümünde kalmak yerine uygulama otomatik olarak skor ekranına gider.

Kelime listesi boşaldıktan sonra `eventGameFinish` ayarlanır, oyun fragment'taki ilişkili observer metodu çağrılır ve uygulama ekran fragment'ına gider.

7. Eklediğiniz kod bir lifecycle sorununa neden oldu. Sorunu anlamak için `GameFragment` class'ında `gameFinished()` metodundaki navigation kodunu yorumlayın. `Toast` mesajını metotta tuttuğunuzdan emin olun.

```kotlin

private fun gameFinished() {
       Toast.makeText(activity, "Oyun yeni bitti", Toast.LENGTH_SHORT).show()
//        val action = GameFragmentDirections.actionGameToScore()
//        action.score = viewModel.score.value?:0
//        NavHostFragment.findNavController(this).navigate(action)
   }

```

8. Uygulamanızı çalıştırın, oyunu oynayın ve tüm kelimeleri geçin. Oyun ekranının altında kısaca "Oyun yeni bitti" yazan bir toast mesajı belirir ve bu beklenen davranıştır.

Şimdi cihazı veya emülatörü döndürün. Toast tekrar görüntüleniyor! Cihazı birkaç kez daha çevirin ve muhtemelen her seferinde toast'u göreceksiniz. Bu bir hatadır, çünkü toast oyun bittiğinde yalnızca bir kez gösterilmelidir. Toast, fragment her yeniden oluşturulduğunda görüntülenmemelidir. Bu sorunu bir sonraki aşamada çözeceksiniz.

![app rotation](https://user-images.githubusercontent.com/46448616/152019492-8f5e9df6-b2bd-4a22-9736-3caef29a4ead.png)


### Adım 2: Oyun bitti event'ini sıfırlayın

Genellikle `LiveData`, yalnızca veriler değiştiğinde observerlara güncellemeler sunar. Bu davranışın bir istisnası, observer aktif olmayan durumdan aktif duruma geçtiğinde observerların da güncellemeler almasıdır.

Bu nedenle, oyun bitti toast'u uygulamanızda tekrar tekrar tetiklenir. Oyun aktif'ı bir ekran dönüşünden sonra yeniden oluşturulduğunda, aktif olmayan durumdan aktif duruma geçer. Fragment'taki observer, mevcut `ViewModel`'a yeniden bağlanır ve mevcut verileri alır. `gameFinished()` metodu yeniden tetiklenir ve toast görüntülenir.

Bu aşamada, `GameViewModel`'da `eventGameFinish` flag'ini sıfırlayarak bu sorunu giderecek ve toast'u yalnızca bir kez görüntüleyeceksiniz.

1. `GameViewModel`'da, oyun bitti event'i `_eventGameFinish`'i sıfırlamak için bir `onGameFinishComplete()` metodu ekleyin.

```kotlin

/** Oyun tamamlandı event'i için metot **/

fun onGameFinishComplete() {
   _eventGameFinish.value = false
}

```

2. `GameFragment`'ta, `gameFinished()`'in sonunda, `viewModel` nesnesinde `onGameFinishComplete()`'i çağırın. (Navigation kodunu `gameFinished()`'te şimdilik yorum olarak bırakın.)

```kotlin

private fun gameFinished() {
   ...
   viewModel.onGameFinishComplete()
}

```

3. Uygulamayı çalıştırın ve oyunu oynayın. Tüm kelimeleri gözden geçin, ardından cihazın ekran yönünü değiştirin. Toast yalnızca bir kez görüntülenir.
4. `GameFragment`'ta, `gameFinished()` metodunun içinde navigation kodunun yorumunu kaldırın.

Android Studio'da açıklamayı kaldırmak için yorum yapılan satırları seçin ve `Control+/` (Mac'te `Command+/`) düğmesine basın.

```kotlin

private fun gameFinished() {
   Toast.makeText(activity, "Oyun yeni bitti", Toast.LENGTH_SHORT).show()
   val action = GameFragmentDirections.actionGameToScore()
   action.score = viewModel.score.value?:0
   findNavController(this).navigate(action)
   viewModel.onGameFinishComplete()
}

```

Android Studio tarafından istenirse, `androidx.navigation.fragment.NavHostFragment.findNavController`'ı import edin.

5. Uygulamayı çalıştırın ve oyunu oynayın. Tüm kelimeleri geçtikten sonra uygulamanın otomatik olarak son puan ekranına gittiğinden emin olun.

![app](https://user-images.githubusercontent.com/46448616/152020751-9978d9ae-b894-4e0f-b7cd-f644348c24a4.png)

Harika bir iş! Uygulamanız, `GameViewModel`'dan oyun fragment'ına kelime listesinin boş olduğunu bildirmek için bir oyun bitti event'i tetiklemek için `LiveData`'yı kullanır. Oyun fragment'ı daha sonra puan fragment'ına gider.


## <a name="f"></a>Aşama 6 : ScoreViewModel'a LiveData ekleyin

Bu aşamada, puanı `ScoreViewModel`'da bir `LiveData` nesnesi olarak değiştirecek ve ona bir observer ekleyeceksiniz. Bu aşama, `LiveData`'yı `GameViewModel`'a eklediğinizde yaptığınıza benzer.

Bu değişiklikleri eksiksiz olması için `ScoreViewModel`'da yaparsınız, böylece uygulamanızdaki tüm veriler `LiveData` kullanır.

1. `ScoreViewModel`'da puan değişkeni türünü `MutableLiveData` olarak değiştirin. Kural olarak `_score` olarak yeniden adlandırın ve bir backing property ekleyin.


```kotlin

private val _score = MutableLiveData<Int>()
val score: LiveData<Int>
   get() = _score

```

2. `ScoreViewModel`'da, `init` bloğunun içinde `_score`'u başlatın. `init` bloğundaki log'u istediğiniz gibi kaldırabilir veya bırakabilirsiniz.

```kotlin

init {
   _score.value = finalScore
}

```

3. `ScoreFragment`'ta, `onCreateView()` içinde, `viewModel`'ı initialize ettikten sonra, `LiveData` nesnesi için bir observer ekleyin. Lambda ifadesinin içinde, puan değerini puan text view'a ayarlayın. Puan değerini text view'a doğrudan atayan kodu `ViewModel`'dan kaldırın.

Eklenecek kod:

```kotlin

// Puan için observer ekleyin
viewModel.score.observe(viewLifecycleOwner, Observer { newScore ->
   binding.scoreText.text = newScore.toString()
})

```

Kaldırılacak kod:

```kotlin

binding.scoreText.text = viewModel.score.toString()

```

Android Studio tarafından istenirse, `androidx.lifecycle.Observer`'ı import edin.

4. Uygulamanızı çalıştırın ve oyunu oynayın. Uygulama eskisi gibi çalışmalı, ancak şimdi skoru güncellemek için `LiveData` ve bir observer kullanıyor.

## <a name="g"></a>Aşama 7 : Tekrar Oyna butonu ekleyin

Bu aşamada, skor ekranına bir **Tekrar Oyna** butonu ekleyecek ve bir `LiveData` event'i kullanarak click listener uygulayacaksınız. Buton, skor ekranından oyun ekranına gitmek için bir event tetikler.

Uygulamanın başlangıç kodu, **Tekrar Oyna** butonunu içerir, ancak buton gizlidir.

1. `res/layout/score_fragment.xml` dosyasında `play_again_button` butonu için `visibility` özelliğinin değerini `visible` olarak değiştirin.

```xml

<Button
   android:id="@+id/play_again_button"
...
   android:visibility="visible"
 />
 
```

2. `ScoreViewModel`'da, `_eventPlayAgain` adlı bir `Boolean` değerini tutmak için bir `LiveData` nesnesi ekleyin. Bu nesne, skor ekranından oyun ekranına gitmek için `LiveData` event'ini kaydetmek için kullanılır.

```kotlin

private val _eventPlayAgain = MutableLiveData<Boolean>()
val eventPlayAgain: LiveData<Boolean>
   get() = _eventPlayAgain
   
```

3. `ScoreViewModel`'da, `_eventPlayAgain` olayını ayarlamak ve sıfırlamak için metotlar tanımlayın.

```kotlin

fun onPlayAgain() {
   _eventPlayAgain.value = true
}
fun onPlayAgainComplete() {
   _eventPlayAgain.value = false
}
   
```

4. `ScoreFragment`'ta `eventPlayAgain` için bir observer ekleyin. Kodu `onCreateView()`'un sonuna, `return` ifadesinin önüne koyun. Lambda ifadesinin içinde oyun ekranına geri dönün ve `eventPlayAgain`'i sıfırlayın.

```kotlin

// Butona basıldığında oyuna geri döner
viewModel.eventPlayAgain.observe(viewLifecycleOwner, Observer { playAgain ->
   if (playAgain) {
      findNavController().navigate(ScoreFragmentDirections.actionRestart())
       viewModel.onPlayAgainComplete()
   }
})
   
```

Android Studio tarafından istendiğinde, `androidx.navigation.fragment.findNavController`'ı import edin.

5. `ScoreFragment`'ta, `onCreateView()` içinde, **Tekrar Oyna** butonuna bir click listener ekleyin ve `viewModel.onPlayAgain()`'i çağırın.

```kotlin

binding.playAgainButton.setOnClickListener {  viewModel.onPlayAgain()  }
   
```

6. Uygulamanızı çalıştırın ve oyunu oynayın. Oyun bittiğinde skor ekranı son skoru ve **Tekrar Oyna** butonunu gösterir. **Tekrar Oyna** butonuna dokunun ve uygulama, oyunu tekrar oynayabilmeniz için oyun ekranına gidecektir.

![score screen](https://developer.android.com/codelabs/kotlin-android-training-live-data/img/d6bf6cf728fb6c09.png)

