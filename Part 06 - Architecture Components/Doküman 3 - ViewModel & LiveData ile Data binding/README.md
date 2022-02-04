# <a name="1"></a>ViewModel & LiveData ile DataBinding

- [Başlayın](#a)
- [ViewModel data binding ekleyin](#b)
- [Data Binding'e LiveData ekleyin](#c)

Bu dokümanda, başlangıç kodundan başlayarak GuessTheWord uygulamasını geliştireceksiniz. GuessTheWord, oyuncuların mümkün olan en yüksek puanı elde etmek için işbirliği yaptığı iki oyunculu, sessiz sinema ([charades](https://en.wikipedia.org/wiki/Charades)) tarzı bir oyundur.

İlk oyuncu uygulamadaki kelimelere bakar ve kelimeyi ikinci oyuncuya göstermemeye dikkat ederek sırayla her birini hareketlerle anlatmaya çalışır. İkinci oyuncu kelimeyi tahmin etmeye çalışır.

Oyunu oynamak için ilk oyuncu cihazda uygulamayı açar ve aşağıdaki ekran görüntüsünde gösterildiği gibi örneğin "gitar" gibi bir kelime görür.

![app](https://developer.android.com/codelabs/kotlin-android-training-view-model/img/8df85c3b8266c7a8.png)

İlk oyuncu, kelimenin kendisini gerçekten söylememeye dikkat ederek kelimeyi canlandırır.

- İkinci oyuncu kelimeyi doğru tahmin ettiğinde, ilk oyuncu, sayımı birer birer artıran ve bir sonraki kelimeyi gösteren **Anladım** butonuna basar.
- İkinci oyuncu kelimeyi tahmin edemezse, ilk oyuncu, sayımı birer birer azaltan ve bir sonraki kelimeye atlayan **Atla** butonuna basar.
- Oyunu bitirmek için **Oyunu Bitir** butonuna basın. (Bu işlev, serideki ilk dökümanın başlangıç kodunda değildir.)

Bu dokümanda, ViewModel nesnelerinde LiveData ile data binding'i entegre ederek GuessTheWord uygulamasını iyileştireceksiniz. Bu, düzendeki görünümler ile ViewModel nesneleri arasındaki iletişimi otomatikleştirir ve LiveData kullanarak kodunuzu basitleştirmenize olanak tanır.

## <a name="a"></a>Aşama 1 : Başlayın
## <a name="b"></a>Aşama 2 : ViewModel data binding ekleyin
## <a name="c"></a>Aşama 3 : Data Binding'e LiveData ekleyin



