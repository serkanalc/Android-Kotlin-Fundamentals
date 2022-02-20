 <a name="1"></a>LiveData dönüşümleri

- [Başlayın](#a)
- [Zamanlayıcı ekleyin](#b)
- [LiveData için dönüşüm ekleyin](#c)

Bu partta, başlangıç kodundan başlayarak GuessTheWord uygulamasını geliştireceksiniz. GuessTheWord, oyuncuların mümkün olan en yüksek puanı elde etmek için işbirliği yaptığı iki oyunculu, sessiz sinema ([charades](https://en.wikipedia.org/wiki/Charades)) tarzı bir oyundur.

İlk oyuncu uygulamadaki kelimelere bakar ve kelimeyi ikinci oyuncuya göstermemeye dikkat ederek sırayla her birini hareketlerle anlatmaya çalışır. İkinci oyuncu kelimeyi tahmin etmeye çalışır.

Oyunu oynamak için ilk oyuncu cihazda uygulamayı açar ve aşağıdaki ekran görüntüsünde gösterildiği gibi örneğin "gitar" gibi bir kelime görür.

![app](https://developer.android.com/codelabs/kotlin-android-training-view-model/img/8df85c3b8266c7a8.png)

İlk oyuncu, kelimenin kendisini gerçekten söylememeye dikkat ederek kelimeyi canlandırır.

- İkinci oyuncu kelimeyi doğru tahmin ettiğinde, ilk oyuncu, sayımı birer birer artıran ve bir sonraki kelimeyi gösteren **Anladım** butonuna basar.
- İkinci oyuncu kelimeyi tahmin edemezse, ilk oyuncu, sayımı birer birer azaltan ve bir sonraki kelimeye atlayan **Atla** butonuna basar.
- Oyunu bitirmek için **Oyunu Bitir** butonuna basın. (Bu işlev, serideki ilk dökümanın başlangıç kodunda değildir.)

Bu dokümanda, puanın üzerinde görünen bir dakikalık geri sayım sayacı ekleyerek GuessTheWord uygulamasını iyileştireceksiniz. Geri sayım `0`'a ulaştığında zamanlayıcı oyunu bitirir.

Geçen süre `LiveData` nesnesini bir zamanlayıcı string'i `LiveData` nesnesine biçimlendirmek için de bir dönüştürme kullanırsınız. Dönüştürülen `LiveData`, zamanlayıcının text view'u için data binding kaynağıdır.

## <a name="a"></a>Aşama 1 : Başlayın

Bu aşamada, bu doküman için başlangıç kodunuzu bulup çalıştıracaksınız. Başlangıç kodunuz olarak önceki dokümanda oluşturduğunuz GuessTheWord uygulamasını kullanabilir veya bir başlangıç uygulaması indirebilirsiniz.

1. (Opsiyonel) Önceki dokümandaki kodunuzu kullanmıyorsanız, bu doküman için başlangıç kodunu indirin. Kodu açın ve projeyi Android Studio'da açın.
2. Uygulamayı çalıştırın ve oyunu oynayın.
3. **Anladım** butonunun bir sonraki kelimeyi gösterdiğine ve puanı bir artırdığına, **Atla** butonunun sonraki kelimeyi gösterdiğine ve puanı bir azalttığına dikkat edin. **Oyunu Bitir** butonu oyunu bitirir.
4. Tüm kelimeler arasında dolaşın ve uygulamanın otomatik olarak puan ekranına gittiğine dikkat edin.

## <a name="b"></a>Aşama 2 : Zamanlayıcı ekleyin

Bu aşamada uygulamaya bir geri sayım sayacı eklersiniz. Kelime listesi boşaldığında biten oyun yerine, zamanlayıcı bittiğinde oyun biter. Android, zamanlayıcıyı uygulamak için kullandığınız [`CountDownTimer`](https://developer.android.com/reference/android/os/CountDownTimer) adlı bir yardımcı program class'ı sağlar.

Konfigürasyon değişiklikleri sırasında zamanlayıcının zarar görmemesi için zamanlayıcının mantığını `GameViewModel`'< ekleyin. Fragment, zamanlayıcı tıklandığında zamanlayıcı text view'u güncelleme kodunu içerir.

`GameViewModel` class'ında aşağıdaki adımları uygulayın:

1. Zamanlayıcı sabitlerini tutmak için bir `companion` object oluşturun.

```kotlin

companion object {

   // Oyunun bittiği zaman
   private const val DONE = 0L

   // Geri sayım zaman aralığı
   private const val ONE_SECOND = 1000L

   // Oyun için toplam süre
   private const val COUNTDOWN_TIME = 60000L

}

```

2. Zamanlayıcının geri sayım süresini depolamak için, `_currentTime` adlı bir `MutableLiveData` ve `currentTime` adlı bir backing property ekleyin.

```kotlin

// Geri sayım zamanı
private val _currentTime = MutableLiveData<Long>()
val currentTime: LiveData<Long>
   get() = _currentTime

```

3. `CountDownTimer` türünde `timer` adlı bir `private` member değişken ekleyin. Initialization hatasını sonraki adımda çözeceksiniz.

```kotlin

private val timer: CountDownTimer

```

4. `init` bloğunun içinde, zamanlayıcıyı initialize edin ve başlatın. Toplam süreyi iletin, `COUNTDOWN_TIME`. Zaman aralığı için `ONE_SECOND`'ı kullanın. `onTick()` ve `onFinish()` callback metotlarını override edin ve zamanlayıcıyı başlatın.

```kotlin

// Bittiğinde oyunun sonunu tetikleyen bir zamanlayıcı oluşturur
timer = object : CountDownTimer(COUNTDOWN_TIME, ONE_SECOND) {

   override fun onTick(millisUntilFinished: Long) {
       
   }

   override fun onFinish() {
       
   }
}

timer.start()

```

5. Her aralıkta veya her onayda çağrılan `onTick()` callback metodunu uygulayın. İletilen `millisUntilFinished` parametresini kullanarak `_currentTime`'ı güncelleyin. `millisUntilFinished`, zamanlayıcının milisaniye cinsinden bitmesine kadar geçecek süredir. `millisUntilFinished`'i saniyeye dönüştürün ve `_currentTime`'a atayın.

```kotlin

override fun onTick(millisUntilFinished: Long)
{
   _currentTime.value = millisUntilFinished/ONE_SECOND
}

```

6. `onFinish()` callback metodu, zamanlayıcı bittiğinde çağrılır. `_currentTime`'ı güncellemek ve oyun bitiş event'ini tetiklemek için `onFinish()`'i uygulayın.

```kotlin

override fun onFinish() {
   _currentTime.value = DONE
   onGameFinish()
}

```

7. Liste boşken oyunu bitirmek yerine kelime listesini sıfırlamak için `nextWord()` metodunu güncelleyin.


```kotlin

private fun nextWord() {
   // Liste boşsa kelime listesini karıştır
   if (wordList.isEmpty()) {
       resetList()
   } else {
   // Listeden bir kelimeyi kaldır
   _word.value = wordList.removeAt(0)
   }
}

```

8. `onCleared()` metodunun içinde, bellek sızıntılarını (memory leak) önlemek için zamanlayıcıyı iptal edin. Artık gerekmediğinden log ifadesini kaldırabilirsiniz. `onCleared()` metodu, `ViewModel` yok edilmeden önce çağrılır.

```kotlin

override fun onCleared() {
   super.onCleared()
   // Zamanlayıcıyı iptal et
   timer.cancel()
}

```

9. Uygulamanızı çalıştırın ve oyunu oynayın. 60 saniye bekleyin ve oyun otomatik olarak bitecektir. Ancak, zamanlayıcı text'i ekranda görüntülenmez. Sonra düzelteceksiniz.

## <a name="c"></a>Aşama 3 : LiveData için dönüşüm ekleyin

[`Transformations.map()`](https://developer.android.com/reference/androidx/lifecycle/Transformations.html#map(androidx.lifecycle.LiveData%3CX%3E,%20androidx.arch.core.util.Function%3CX,%20Y%3E)) metodu, kaynak `LiveData` üzerinde veri işlemeleri gerçekleştirmenin ve bir sonuç `LiveData` nesnesi döndürmenin bir yolunu sağlar. Bu dönüşümler, bir observer döndürülen `LiveData` nesnesini gözlemlemedikçe hesaplanmaz.

Bu metot, kaynak `LiveData`'yı ve bir fonksiyonu parametre olarak alır. Fonksiyon, kaynak `LiveData`'yı işler.

>**Not**: `Transformation.map()`'e iletilen lambda işlevi main thread'de yürütülür, bu nedenle uzun süren görevleri buraya dahil etmeyin.

Bu görevde, geçen süre `LiveData` nesnesini "`MM:SS`" biçiminde yeni bir `LiveData` string'i olarak biçimlendireceksiniz. Ayrıca ekranda biçimlendirilmiş geçen süreyi de görüntüleyeceksiniz.

`game_fragment.xml` layout dosyası, zamanlayıcı text view'unu zaten içerir. Şimdiye kadar, text view'da görüntülenecek metin olmadığı için zamanlayıcı metni görünmedi.

1. `GameViewModel` class'ında, `currentTime` instance'ını oluşturduktan sonra `currentTimeString` adlı yeni bir LiveData nesnesi oluşturun. Bu nesne, `currentTime`'ın biçimlendirilmiş string versiyonu içindir.
2. `currentTimeString`'i tanımlamak için `Transformations.map()` kullanın. Saati biçimlendirmek için `currentTime` ve bir lambda fonksiyonu iletin. Lambda fonksiyonunu, `long` türünde saniye süren ve onu "`MM:SS`" dize biçimine biçimlendiren [`DateUtils.formatElapsedTime()`](https://developer.android.com/reference/android/text/format/DateUtils.html#formatElapsedTime(long)) utility metodunu kullanarak uygulayabilirsiniz.

```kotlin

// Geçerli zamanın String versiyonu
val currentTimeString = Transformations.map(currentTime) { time ->
   DateUtils.formatElapsedTime(time)
}

```

3. `game_fragment.xml` dosyasında, zamanlayıcı text view'da, `text` özelliğini `gameViewModel`'ın `currentTimeString` öğesine bağlayın.

```XML

<TextView
   android:id="@+id/timer_text"
   ...
   android:text="@{gameViewModel.currentTimeString}"
   ... />

```

4. Uygulamanızı çalıştırın ve oyunu oynayın. Zamanlayıcı metni saniyede bir güncellenir. Tüm kelimeler arasında dolaştığınızda oyunun bitmediğine dikkat edin. Artık zamanlayıcı dolduğunda oyun biter.

![app](https://developer.android.com/codelabs/kotlin-android-training-live-data-transformations/img/beed8336e42b7ece.png)
