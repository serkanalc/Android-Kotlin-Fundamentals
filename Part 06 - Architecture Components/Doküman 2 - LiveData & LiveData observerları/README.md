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
## <a name="c"></a>Aşama 3 : LiveData nesnelerine observerlar bağlayın
## <a name="d"></a>Aşama 4 : LiveData'yı encapsulate edin
## <a name="e"></a>Aşama 5 : Bir oyun bitti event'i ekleyin
## <a name="f"></a>Aşama 6 : ScoreViewModel'a LiveData ekleyin
## <a name="g"></a>Aşama 7 : Tekrar Oyna butonu ekleyin
