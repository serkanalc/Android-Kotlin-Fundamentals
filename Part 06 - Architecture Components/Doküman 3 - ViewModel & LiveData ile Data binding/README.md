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

```xml

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

[_Listener bindingleri_](https://developer.android.com/topic/libraries/data-binding/expressions#listener_bindings), `onClick()`, `onZoomIn()` veya `onZoomOut()` gibi eventler tetiklendiğinde çalışan binding ifadeleridir. Listener bindingleri lambda ifadeleri olarak yazılır.

Data binding, bir listener oluşturur ve listener'ı view'a ayarlar. Dinlenen event gerçekleştiğinde, listener lambda ifadesini değerlendirir. Listener bindingleri, Android Gradle Plugin sürüm 2.0 veya üzeri ile çalışır. Daha fazla bilgi edinmek için [Layouts and binding expressions](https://developer.android.com/topic/libraries/data-binding/expressions#listener_bindings) bölümünü okuyun.

Bu adımda `GameFragment`'taki click listener'ı `game_fragment.xml` dosyasındaki listener bindinglerle değiştireceksiniz.

1. `game_fragment.xml`'de, `skip_button`'a `onClick` özelliğini ekleyin. Bir binding ifadesi tanımlayın ve `GameViewModel`'da `onSkip()` metodunu çağırın. Bu binding ifadesine _listener binding_ denir.

```xml

<Button
   android:id="@+id/skip_button"
   ...
   android:onClick="@{() -> gameViewModel.onSkip()}"
   ... />

```

2. Benzer şekilde, `GameViewModel`'daki `correct_button`'un tıklama event'ini `onCorrect()` metoduna bağlayın.

```xml

<Button
   android:id="@+id/correct_button"
   ...
   android:onClick="@{() -> gameViewModel.onCorrect()}"
   ... />

```

3. end_game_button tıklama event'ini GameViewModel'daki onGameFinish() metoduna bağlayın.

```xml

<Button
   android:id="@+id/end_game_button"
   ...
   android:onClick="@{() -> gameViewModel.onGameFinish()}"
   ... />

```

4. `GameFragment`'ta, click listenerları ayarlayan ifadeleri kaldırın ve click listenerların çağırdığı fonskiyonları kaldırın. Artık onlara ihtiyacınız yok.

Kaldırılacak kod:

```kotlin

binding.correctButton.setOnClickListener { onCorrect() }
binding.skipButton.setOnClickListener { onSkip() }
binding.endGameButton.setOnClickListener { onEndGame() }

/** Buton tıklamaları için metotlar **/
private fun onSkip() {
   viewModel.onSkip()
}
private fun onCorrect() {
   viewModel.onCorrect()
}
private fun onEndGame() {
   gameFinished()
}

```

### Adım 3: ScoreViewModel için data binding ekleyin

Bu adımda, `ScoreViewModel`'ı ilgili layout dosyası olan `score_fragment.xml` ile ilişkilendireceksiniz.

1. `score_fragment.xml` dosyasında, `ScoreViewModel` türünde bir binding değişkeni ekleyin. Bu adım, yukarıda `GameViewModel` için yaptığınıza benzerdir.

```xml

<layout ...>
   <data>
       <variable
           name="scoreViewModel"
           type="com.example.android.guesstheword.screens.score.ScoreViewModel" />
   </data>
   <androidx.constraintlayout.widget.ConstraintLayout

```

2. `score_fragment.xml` dosyasında, `play_again_button`'a `onClick` özelliğini ekleyin. Bir listener binding tanımlayın ve `ScoreViewModel`'da `onPlayAgain()` metodunu çağırın.


```xml

<Button
   android:id="@+id/play_again_button"
   ...
   android:onClick="@{() -> scoreViewModel.onPlayAgain()}"
   ... />

```

3. `ScoreFragment`'ta, `onCreateView()` içinde, `viewModel`'ı initialize edin. Ardından `binding.scoreViewModel` binding değişkenini initialize edin.

```kotlin

viewModel = ...
binding.scoreViewModel = viewModel

```

4. `ScoreFragment`'ta, `playAgainButton` için click listener'ı ayarlayan kodu kaldırın. Android Studio bir hata gösteriyorsa, projeyi temizleyin ve yeniden oluşturun (clean & rebuild).

Kaldırılacak kod:


```kotlin

binding.playAgainButton.setOnClickListener {  viewModel.onPlayAgain()  }

```

5. Uygulamanızı çalıştırın. Uygulama daha önce olduğu gibi çalışmalıdır, ancak şimdi buton viewları doğrudan `ViewModel` nesneleriyle iletişim kurar. Viewlar artık `ScoreFragment`'taki buton click listenerlar aracılığıyla iletişim kurmuyor.

### Data binding hata mesajlarında sorun giderme

Bir uygulama data binding kullandığında, derleme işlemi data binding için kullanılan ara classları oluşturur. Bir uygulamada, siz uygulamayı derlemeye çalışana kadar Android Studio'nun algılamadığı hatalar olabilir, bu nedenle kodu yazarken uyarı veya kırmızı kod görmezsiniz. Ancak derleme zamanında, oluşturulan ara classlardan gelen değişik hatalar alırsınız.

Eğer değişik bir hata mesajı alırsanız:

1. Android Studio **Build** bölmesindeki mesaja dikkatlice bakın. `databinding` ile biten bir konum görürseniz, data binding ile ilgili bir hata vardır.
2. Layout XML dosyasında, data binding kullanan `onClick` özelliklerindeki hataları kontrol edin. Lambda ifadesinin çağırdığı fonksiyonu arayın ve var olduğundan emin olun.
3. XML'in <data> bölümünde, data binding değişkeninin yazımını kontrol edin.

Örneğin, aşağıdaki özellik değerinde `onCorrect()` fonksiyon adının yanlış yazıldığına dikkat edin:

`android:onClick="@{() -> gameViewModel.onCorrectx()}"`

Ayrıca, XML dosyasının `<data>` bölümündeki `gameViewModel`'ın yazım hatasına dikkat edin:

```xml

<data>
   <variable
       name="gameViewModelx"
       type="com.example.android.guesstheword.screens.game.GameViewModel" />
</data>

```
   
Android Studio, siz uygulamayı derleyene kadar bunun gibi hataları algılamaz ve ardından derleyici aşağıdaki gibi bir hata mesajı gösterir:   
   
```
error: cannot find symbol
import com.example.android.guesstheword.databinding.GameFragmentBindingImpl"

symbol:   class GameFragmentBindingImpl
location: package com.example.android.guesstheword.databinding
   
```

   
## <a name="c"></a>Aşama 3 : Data Binding'e LiveData ekleyin



