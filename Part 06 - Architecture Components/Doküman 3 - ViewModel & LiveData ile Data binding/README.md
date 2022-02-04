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

Bu dokümanda, `ViewModel` nesnelerinde `LiveData` ile data binding'i entegre ederek GuessTheWord uygulamasını iyileştireceksiniz. Bu, layout'taki viewlar ile `ViewModel` nesneleri arasındaki iletişimi otomatikleştirir ve `LiveData` kullanarak kodunuzu basitleştirmenize olanak tanır.

![app screens](https://user-images.githubusercontent.com/46448616/151831290-a92d55a9-0fe9-4a63-966f-5c8bab7c6c78.png)

## <a name="a"></a>Aşama 1 : Başlayın

Bu aşamada, bu doküman için başlangıç kodunuzu bulup çalıştıracaksınız. Başlangıç kodunuz olarak önceki dokümanda oluşturduğunuz GuessTheWord uygulamasını kullanabilir veya bir başlangıç uygulaması indirebilirsiniz.

1. (Opsiyonel) Önceki dokümandaki kodunuzu kullanmıyorsanız, bu doküman için başlangıç kodunu indirin. Kodu açın ve projeyi Android Studio'da açın.
2. Uygulamayı çalıştırın ve oyunu oynayın.
3. **Anladım** butonunun bir sonraki kelimeyi gösterdiğine ve puanı bir artırdığına, **Atla** butonunun sonraki kelimeyi gösterdiğine ve puanı bir azalttığına dikkat edin. **Oyunu Bitir** butonu oyunu bitirir.
4. Tüm kelimeler arasında dolaşın ve uygulamanın otomatik olarak puan ekranına gittiğine dikkat edin.

## <a name="b"></a>Aşama 2 : ViewModel data binding ekleyin

Önceki dokümanda, GuessTheWord uygulamasındaki viewlara erişmenin güvenli bir yolu olarak data binding'i kullandınız. Ancak data binding'in gerçek gücü, adından da anlaşılacağı gibi: verileri doğrudan uygulamanızdaki view nesnelerine bağlamadadır.

#### Mevcut uygulama mimarisi

Uygulamanızda, viewlar XML layout'unda tanımlanır ve bu viewlara ilişkin veriler ViewModel nesnelerinde tutulur. Her view ve karşılık gelen ViewModel arasında, aralarında bir geçiş görevi gören bir UI controller bulunur.

![architecture](https://developer.android.com/codelabs/kotlin-android-training-live-data-data-binding/img/3f68038d95411119.png)

Örneğin:

- **Anladım** butonu, `game_fragment.xml` layout dosyasında bir `Button` view olarak tanımlanır.
- Kullanıcı **Anladım** butonuna dokunduğunda, `GameFragment` fragment'ındaki bir click listener `GameViewModel`'daki ilgili click listener'ı çağırır.
- Puan `GameViewModel`'da güncellenir.

`Button` view ve `GameViewModel` doğrudan iletişim kurmaz; `GameFragment`'ta bulunan click listener'a ihtiyaçları vardır.

#### Data binding'e ViewModel geçirilmesi

Layout'taki viewların, aracı olarak UI controllerlara güvenmeden `ViewModel` nesnelerindeki verilerle doğrudan iletişim kurması daha kolay olurdu.

![architecture](https://developer.android.com/codelabs/kotlin-android-training-live-data-data-binding/img/7f26738df2266dd6.png)

`ViewModel` nesneleri, GuessTheWord uygulamasındaki tüm UI verilerini tutar. `ViewModel` nesnelerini data binding'e geçirerek, viewlar ve `ViewModel` nesneleri arasındaki iletişimin bir kısmını otomatikleştirebilirsiniz.

Bu aşamada, `GameViewModel` ve `ScoreViewModel` classlarını karşılık gelen XML layoutları ile ilişkilendireceksiniz. Ayrıca, tıklama eventlerini işlemek için listener bindingleri de kuracaksınız.


### Adım 1: GameViewModel'a data binding ekleyin

Bu adımda, `GameViewModel`'ı ilgili layout dosyası `game_fragment.xml` ile ilişkilendireceksiniz.

1. `game_fragment.xml` dosyasına `GameViewModel` türünde bir data binding değişkeni ekleyin. Android Studio'da hatalar varsa, projeyi temizleyin ve yeniden oluşturun (clean & rebuild).

```kotlin

<layout ...>

   <data>

       <variable
           name="gameViewModel"
           type="com.example.android.guesstheword.screens.game.GameViewModel" />
   </data>
  
   <androidx.constraintlayout...

```

2. GameFragment dosyasında GameViewModel'ı data binding'e iletin.

Bunu yapmak için, önceki adımda bildirdiğiniz `binding.gameViewModel` değişkenine `viewModel` atayın. `viewModel` initialize edildikten sonra bu kodu `onCreateView()` içine koyun. Android Studio'da hatalar varsa, projeyi temizleyin ve yeniden oluşturun (clean & rebuild).

```kotlin

// viewModel'ı databinding için ayarlayın - bu, bağlı layout'un ViewModel'daki 
// tüm verilere erişmesine izin verir 
binding.gameViewModel = viewModel

```

### Adım 2: Event işleme için listener bindingleri kullanın





## <a name="c"></a>Aşama 3 : Data Binding'e LiveData ekleyin



