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
3. XML'in `<data>` bölümünde, data binding değişkeninin yazımını kontrol edin.

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
   
Data binding, `ViewModel` nesneleriyle kullanılan `LiveData` ile iyi çalışır. Artık `ViewModel` nesnelerine data binding eklediğinize göre, `LiveData`'yı dahil etmeye hazırsınız.

Bu aşamada, GuessTheWord uygulamasını, `LiveData` observer metotlarını kullanmadan, verilerdeki değişiklikler hakkında UI'ı bilgilendirmek için data binding kaynağı olarak `LiveData`'yı kullanacak şekilde değiştireceksiniz.   
   
   
### Adım 1: game_fragment.xml dosyasına word LiveData ekleyin
  
Bu adımda, geçerli word text view'u doğrudan ViewModel'daki LiveData nesnesine bağlayacaksınız.   
  
1. `game_fragment.xml`'de, `word_text` text view'a `android:text` özelliğimi ekleyin.

`gameViewModel` binding değişkenini kullanarak `GameViewModel`'dan `word` olan `LiveData` nesnesine ayarlayın.

```xml

<TextView
   android:id="@+id/word_text"
   ...
   android:text="@{gameViewModel.word}"
   ... />
   
```

`word.value` kullanmanız gerekmediğine dikkat edin. Bunun yerine gerçek `LiveData` nesnesini kullanabilirsiniz. `LiveData` nesnesi, `word`'un geçerli değerini görüntüler. `word` değeri null ise, `LiveData` nesnesi boş bir string görüntüler.

2. `GameFragment`'ta, `onCreateView()`'da `gameViewModel`'ı initialize ettikten sonra, fragment view'u `binding` değişkeninin lifecycle sahibi olarak ayarlayın. Bu, yukarıdaki `LiveData` nesnesinin kapsamını tanımlayarak nesnenin `game_fragment.xml` layout'undaki viewları otomatik olarak güncellemesine olanak tanır.

```kotlin

binding.gameViewModel = ...
// Fragment view'u binding'in lifecycle sahibi olarak belirtin.
// Bu, binding'in LiveData güncellemelerini gözlemleyebilmesi için kullanılır.
binding.lifecycleOwner = viewLifecycleOwner
   
```

3. `GameFragment`'ta `word` `LiveData`'sı için olan obersver'ı kaldırın.

Kaldırılacak kod:

```kotlin

/** LiveData gözlem ilişkisini kurma **/
viewModel.word.observe(viewLifecycleOwner, Observer { newWord ->
   binding.wordText.text = newWord
})
   
```

4. Uygulamanızı çalıştırın ve oyunu oynayın. Şimdi mevcut kelime, UI controller'da bir observer metodu olmadan güncelleniyor.

### Adım 2: score_fragment.xml dosyasına score LiveData ekleyin

Bu adımda, `LiveData` `score`'u score fragment'taki score text view'a bağlarsınız.

1. `score_fragment.xml` dosyasında,  score text view'a `android:text` özelliğini ekleyin. `text` özelliğine `scoreViewModel.score` atayın. `score` bir tamsayı olduğundan, onu `String.valueOf()` kullanarak bir string'e dönüştürün.

```xml

<TextView
   android:id="@+id/score_text"
   ...
   android:text="@{String.valueOf(scoreViewModel.score)}"
   ... />
   
```

2. ScoreFragment'ta, scoreViewModel'ı başlattıktan sonra, geçerli activity'yi binding değişkeninin lifeycle sahibi olarak ayarlayın.

```kotlin

binding.scoreViewModel = ...
// Fragment view'u binding'in lifecycle sahibi olarak belirtin.
// Bu, binding'in LiveData güncellemelerini gözlemleyebilmesi için kullanılır.
binding.lifecycleOwner = viewLifecycleOwner
   
```

3. `ScoreFragment`'ta, `score` nesnesi için observer'ı kaldırın.

Kaldırılacak kod:

```kotlin

// score için observer ekleyin
viewModel.score.observe(viewLifecycleOwner, Observer { newScore ->
   binding.scoreText.text = newScore.toString()
})
   
```

4. Uygulamanızı çalıştırın ve oyunu oynayın. Score fragment'taki puanın, score fragment'ta bir observer olmadan doğru şekilde görüntülendiğine dikkat edin.

### Adım 3: Data binding ile string formatlama ekleyin

Layout'ta, data binding ile birlikte string formatlama ekleyebilirsiniz. Bu aşamada, çevresine tırnak işaretleri eklemek için geçerli sözcüğü formatlayacaksınız. Ayrıca, aşağıdaki resimde gösterildiği gibi, **Current Score**'un önüne eklenecek şekilde puan string'ini formatlayacaksınız.

![app](https://developer.android.com/codelabs/kotlin-android-training-live-data-data-binding/img/b48f4a9fba085e11.png)


1. `string.xml`'de, `word` ve `score` text viewlarını formatlamak için kullanacağınız aşağıdaki stringleri ekleyin. `%s` ve `%d`, geçerli kelime ve geçerli puan için yer tutuculardır.


```xml

<string name="quote_format">\"%s\"</string>
<string name="score_format">Current Score: %d</string>
   
```

2. `game_fragment.xml`'de, `quote_format` string kaynağını kullanmak için `word_text` text view'un `text` özelliğini güncelleyin. `gameViewModel.word`'ü aktarın. Bu, geçerli sözcüğü formatlama string'ine bir argüman olarak iletir.

```xml

<TextView
   android:id="@+id/word_text"
   ...
   android:text="@{@string/quote_format(gameViewModel.word)}"
   ... />
   
```

3. `score` text view'u `word_text`'e benzer şekilde formatlayın. `game_fragment.xml`'de, `score_text` text view'a `text` özelliğini ekleyin. `%d` yer tutucusu tarafından temsil edilen bir sayısal argüman alan `score_format` kaynak string'ini kullanın. `LiveData` nesnesini, `score`, bu formatlama string'ine bir argüman olarak iletin.

```xml

<TextView
   android:id="@+id/score_text"
   ...
   android:text="@{@string/score_format(gameViewModel.score)}"
   ... />
   
```

4. `GameFragment` class'ında, `onCreateView()` metodunun içinde `score` observer kodunu kaldırın.

Kaldırılacak kod:


```kotlin

viewModel.score.observe(viewLifecycleOwner, Observer { newScore ->
   binding.scoreText.text = newScore.toString()
})
   
```

5. Uygulamanızı temizleyin, yeniden oluşturun (clean & rebuild) ve çalıştırın, ardından oyunu oynayın. Geçerli kelimenin ve puanın oyun ekranında formatlandığına dikkat edin.

![app](https://developer.android.com/codelabs/kotlin-android-training-live-data-data-binding/img/aabcbeeaddf0af06.png)
