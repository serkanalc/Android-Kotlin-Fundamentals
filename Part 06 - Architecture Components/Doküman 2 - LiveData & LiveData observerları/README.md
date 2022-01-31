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
## <a name="f"></a>Aşama 6 : ScoreViewModel'a LiveData ekleyin
## <a name="g"></a>Aşama 7 : Tekrar Oyna butonu ekleyin
