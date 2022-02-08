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

## <a name="c"></a>Aşama 3 : LiveData için dönüşüm ekleyin
