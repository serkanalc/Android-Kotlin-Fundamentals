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
## <a name="b"></a>Aşama 2 : Zamanlayıcı ekleyin
## <a name="c"></a>Aşama 3 : LiveData için dönüşüm ekleyin
