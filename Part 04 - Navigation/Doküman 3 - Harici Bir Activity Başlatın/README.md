# Harici Bir Activity Başlatın

- [Safe Args Plugin'ini kurun ve Kullanın](#0) 
- [Argümanları Ekleyin ve İletin](#1) 
- [Oyun Sonuçlarını Paylaşma](#2) 

## <a name="0"></a>Aşama Ø : Safe Args Plugin'ini kurun ve Kullanın

Kullanıcıların AndroidTrivia uygulamasından oyun sonuçlarını paylaşabilmeleri için kodunuzun parametreleri bir Fragmentten diğerine geçirmesi gerekir. Bu işlemlerdeki hataları önlemek ve güvenli hale getirmek için Safe Args adlı bir Gradle plugini (eklentisi) kullanırsınız. Plugin, NavDirection classları oluşturur ve siz bu classları kodunuza eklersiniz.

Bu kodlabdeki sonraki görevlerde, fragmentlar arasında arguments iletmek için oluşturulan NavDirection classlarını kullanırsınız.

#### Neden Safe Args Plugine İhtiyacınız Var?

Genellikle uygulamanızın fragmentlar arasında veri iletmesi gerekir. Bir Fragmentten diğerine veri aktarmanın bir yolu, Bundle classının bir örneğini kullanmaktır. Android Bundle, bir key/value deposudur.

Sözlük veya ilişkisel dizi olarak da bilinen bir key/value deposu, o key ile ilişkili value'yu almak için benzersiz bir key(dize) kullandığınız bir veri yapısıdır. Örneğin:

| Key | Value |
| --- | --- |
| "name" | "Anika" |
| "favorite_weather" | "sunny" |
| "favorite_color" | "blue" |

Uygulamanız, Fragment A'dan Fragment B'ye veri iletmek için bir Bundle kullanabilir. Örneğin, Fragment A bir paket oluşturur ve bilgilerikey/value çiftleri olarak kaydeder, ardından Paketi Fragment B'ye iletir. Ardından Fragment B, Bundle'dan bir key/value çifti getirmek için bir key kullanır. Bu teknik işe yarar, compile edilen kodla sonuçlanabilir, ancak daha sonra uygulama çalışırken hatalara neden olma potansiyeline sahiptir.

Oluşabilecek hata türleri şunlardır:
- Uyumsuzluk hataları. Örneğin, Fragment A bir dize gönderir ancak Fragment B paketten bir tamsayı isterse, istek varsayılan sıfır değerini döndürür. Sıfır geçerli bir değer olduğundan, bu tür bir uyumsuzluk sorunu, uygulama derlendiğinde bir hata oluşturmaz. Ancak, kullanıcı uygulamayı çalıştırdığında, hata uygulamanın hatalı çalışmasına veya çökmesine neden olabilir. 
- Eksik key hataları. Fragment B, pakette ayarlanmayan bir bağımsız değişken isterse, işlem null değerini döndürür. Yine, bu, uygulama derlendiğinde bir hata oluşturmaz, ancak kullanıcı uygulamayı çalıştırdığında ciddi sorunlara neden olabilir.

Uygulamayı deploy etmeden önce bu hataları yakalayabilmek için Android Studio'da uygulamayı derlerken bu hataları yakalamak istiyorsunuz. Başka bir deyişle, kullanıcılarınızın bunlarla karşılaşmaması için uygulama geliştirme sırasında hataları yakalamak istiyorsunuz.

Bu sorunlara yardımcı olmak için Android'in Navigation Architecture Componenti, Safe Args adlı bir özellik içerir. Safe Args, uygulama çalışana kadar başka türlü ortaya çıkmayabilecek, derleme zamanında hataları algılamaya yardımcı olan kod ve classlar oluşturan bir Gradle eklentisidir.

### 1.Adım: Başlangıç Uygulamasını Açın ve Çalıştırın
1. Bu codelab için [AndroidTriviaNavigation başlangıç uygulamasını](https://github.com/google-developer-training/android-kotlin-fundamentals-apps/tree/master/AndroidTriviaNavigation) indirin:
Uygulamanıza navigation eklemeyle ilgili önceki codelab'i tamamladıysanız, bu codelab'deki çözüm kodunuzu kullanın.

Uygulamayı Android Studio'dan çalıştırın:
1. Uygulamayı Android Studio'da açın.
2. Uygulamayı Android destekli bir cihazda veya bir emülatörde çalıştırın. Uygulama, navigation drawer, başlık ekranında options menüsü ve çoğu ekranın üst kısmında Up butonu bulunan bir bilgi oyunudur.
3. Uygulamayı keşfedin ve oyunu oynayın. Üç soruyu doğru cevaplayarak oyunu kazandığınızda karşınıza Tebrikler ekranı çıkıyor.

![image](https://user-images.githubusercontent.com/80598532/149600626-8e723e87-0b7b-4ee1-9302-68b8f4f45b04.png)

Bu kodlab'de, Tebrikler ekranının üstüne bir paylaşım simgesi eklersiniz. Paylaş simgesi, kullanıcının sonuçlarını bir e-posta veya kısa mesajla paylaşmasına olanak tanır.

### 2.Adım: Projeye Safe Arglar ekleyin
1. Android Studio'da project-lvel'da build.gradle dosyasını açın.
2. Navigation safe-args-gradle-plugin dependency'sini aşağıda gösterildiği gibi ekleyin:

```
// Adding the safe-args dependency to the project Gradle file
dependencies {
   ...
classpath "androidx.navigation:navigation-safe-args-gradle-plugin:$navigationVersion"

}
```

3. App-level'da build.gradle dosyasını açın.
4. Dosyanın en üstünde, diğer tüm pluginlerden sonra, androidx.navigation.safeargs plugin'i ile birlikte Apply plugin ifadesini ekleyin:

```
// Adding the apply plugin statement for safeargs
apply plugin: 'androidx.navigation.safeargs'
```

5. Projeyi yeniden oluşturun. Ek build tools yüklemeniz istenirse, bunları yükleyin.

Uygulama projesi artık oluşturulan NavDirection classlarını içeriyor.

Safe Args eklentisi, her fragment için bir NavDirection classı oluşturur. Bu classlar, uygulamanın tüm actionlarında navigationu temsil eder.

Örneğin, GameFragment artık oluşturulmuş bir GameFragmentDirections classına sahiptir. Game Fragmenti ile uygulamadaki diğer fragmentlar arasında tür açısından safe argumentsı iletmek için GameFragmentDirections classını kullanırsınız.

Oluşturulan dosyaları görmek için Project > Android bölmesinde oluşturulan Java klasörünü keşfedin.

Dikkat: NavDirection classlarını düzenlemeyin. Bu classlar, proje derlendiğinde yeniden oluşturulur ve düzenlemeleriniz kaybolur.

### 3.Adım: Game Fragment'a Bir NavDirection Classı Ekleyin
Bu adımda GameFragmentDirections claasını game Fragmentina eklersiniz. Bu kodu daha sonra GameFragment ve game-state fragments (GameWonFragment ve GameOverFragment) arasında argümanlar iletmek için kullanacaksınız.

1. Java klasöründe bulunan GameFragment.kt Kotlin dosyasını açın.
2. onCreateView() yönteminin içinde, oyunun kazanıldığı koşullu ifadeyi bulun ("Won!"). NavController.navigate() yöntemine geçirilen parametreyi değiştirin: Oyunun kazanıldığı durumun action ID'sini, GameFragmentDirections classından actionGameFragmentToGameWonFragment() yöntemini kullanan bir ID ile değiştirin.
Koşullu ifade şimdi aşağıdaki koda benziyor. 

Sonraki görevde actionGameFragmentToGameWonFragment() yöntemine parametreler ekleyeceksiniz.

```
// Using directions to navigate to the GameWonFragment
view.findNavController()
        .navigate(GameFragmentDirections.actionGameFragmentToGameWonFragment())
```

Aynı şekilde, oyun bitti koşullu ifadesini bulun ("Game-over!"). Game-over durumu için action ID'sini GameFragmentDirections classından game-over yöntemini kullanan bir ID ile değiştirin:

```
// Using directions to navigate to the GameOverFragment
view.findNavController()
        .navigate(GameFragmentDirections.actionGameFragmentToGameOverFragment())
```

## <a name="1"></a>Aşama 1 : Arguments Ekleyin ve İletin
Bu görevde, gameWonFragment'e tür açısından safe-arguments eklersiniz ve argumentları güvenli bir şekilde GameFragmentDirections yöntemine iletirsiniz. Benzer şekilde, diğer Fragment classlarını eşdeğer NavDirection classları ile değiştireceksiniz.

### 1.Adım: Game-won Fragment'a Arguments Ekleyin

1. res > navigation klasöründe bulunan navigasyon.xml dosyasını açın. Fragmentlardaki  bağımsız değişkenleri(arguments) ayarlayacağınız navigation grafiğini açmak için Design sekmesine tıklayın.
2. Önizlemede gameWonFragment'i seçin.
3. Attributes bölmesinde Arguments bölümünü genişletin.
4. Bir argument eklemek için + simgesine tıklayın. numQuestions argumentini adlandırın ve türü Tamsayı olarak ayarlayın, ardından Ekle'ye tıklayın. Bu argument, kullanıcının yanıtladığı soru sayısını temsil eder.

![image](https://user-images.githubusercontent.com/80598532/149601426-60248e71-4a43-4236-933d-dbf997d56444.png)

5. Hala gameWonFragment seçiliyken, ikinci bir argument ekleyin. Bu argumenti numCorrect olarak adlandırın ve türünü Tamsayı olarak ayarlayın. Bu argument, kullanıcının doğru yanıtladığı soru sayısını temsil eder.

![image](https://user-images.githubusercontent.com/80598532/149601458-dc17e103-8569-4ec4-9fe8-d121cc41505f.png)

Uygulamayı şimdi oluşturmaya çalışırsanız, büyük olasılıkla iki derleme hatası alırsınız.

```
No value passed for parameter 'numQuestions'
No value passed for parameter 'numCorrect'
```
Bu hatayı sonraki adımlarda düzeltirsiniz.

Not: Android Studio 3.2 veya önceki sürümünü kullanıyorsanız, navigasyon.xml dosyasında app:type = "integer" öğesini app:argType = "integer" olarak değiştirmeniz gerekebilir.

### 2.Adım: Argumentleri İletin.
Bu adımda, numQuestions ve soruIndex argumentlerini GameFragmentDirections classından actionGameFragmentToGameWonFragment() yöntemine iletirsiniz.

1. GameFragment.kt Kotlin dosyasını açın ve oyunun kazandığı koşullu ifadeyi bulun:

```
else {
 // We've won!  Navigate to the gameWonFragment.
 view.findNavController()
      .navigate(GameFragmentDirections
            .actionGameFragmentToGameWonFragment())
}
```

2. numQuestions ve QuestionIndex parametrelerini actionGameFragmentToGameWonFragment() yöntemine iletin:

```
// Adding the parameters to the Action
view.findNavController()
      .navigate(GameFragmentDirections
            .actionGameFragmentToGameWonFragment(numQuestions, questionIndex))
```

Toplam soru sayısını numQuestions ve denenmekte olan mevcut soruyu soruIndex olarak iletirsiniz. Uygulama, kullanıcının yalnızca tüm soruları doğru yanıtlaması durumunda verilerini paylaşabileceği şekilde tasarlanmıştır; doğru soruların sayısı her zaman yanıtlanan soruların sayısına eşittir. (İsterseniz bu oyun mantığını daha sonra değiştirebilirsiniz.)

3. GameWonFragment.kt'de, argumentleri paketten çıkarın, ardından argumentleri görüntülemek için bir Toast kullanın. Aşağıdaki kodu onCreateView() yöntemine return ifadesinden önce koyun:

```
val args = GameWonFragmentArgs.fromBundle(requireArguments())
Toast.makeText(context, "NumCorrect: ${args.numCorrect}, NumQuestions: ${args.numQuestions}", Toast.LENGTH_LONG).show()
```

4. Uygulamayı çalıştırın ve argumentlerin GameWonFragment'e başarıyla iletildiğinden emin olmak için oyunu oynayın. Tebrikler ekranında "NumCorrect: 3, NumQuestions: 3" yazan tost mesajı görünür.

Yine de önce trivia oyununu kazanman gerekiyor. Oyunu kolaylaştırmak için GameFragment.kt Kotlin dosyasında numQuestions değerini 1 olarak ayarlayarak tek soruluk bir oyuna dönüştürebilirsiniz.

### 3.Adım: Fragment Classlarını NavDirection Classları ile Değiştirin

"Safe arguments" kullandığınızda, navigaiton kodunda kullanılan Fragment classlarını NavDirection classları ile değiştirebilirsiniz. Bunu, uygulamadaki diğer pfragmentlarla type-safe argumentleri kullanabilmeniz için yaparsınız.

1. TitleFragment, GameOverFragment ve GameWonFragment'te, navigation() yöntemine geçirilen action ID'sini değiştirin. Action ID'yi uygun NavDirection classından eşdeğer yöntemle değiştirin:

```
binding.playButton.setOnClickListener { view: View ->
    view.findNavController()
            .navigate(TitleFragmentDirections.actionTitleFragmentToGameFragment())
}
```

2. GameOverFragment.kt dosyasında, Try Again butonunun click handler'ında, Navigation() yönteminin argumentsi olarak GameOver Fragment Directions.actionGameOverFragmentToGameFragment() iletin:

```
binding.tryAgainButton.setOnClickListener { view: View ->
    view.findNavController()
        .navigate(GameOverFragmentDirections.actionGameOverFragmentToGameFragment())
}
```

3. GameWonFragment.kt dosyasında, Next Match butonunun click handler'ında, Navigation() yönteminin argumenti olarak GameWonFragmentDirections.actionGameWonFragmentToGameFragment() iletin:

```
binding.nextMatchButton.setOnClickListener { view: View ->
    view.findNavController()
          .navigate(GameWonFragmentDirections.actionGameWonFragmentToGameFragment())
}
```

4. Uygulamayı çalıştırın.

Uygulamanın çıktısında herhangi bir değişiklik bulamazsınız, ancak artık uygulama, gerektiğinde NavDirection classlarını kullanarak argumentleri kolayca iletebilmeniz için ayarlanmıştır.


## <a name="2"></a>Aşama 2 : Oyun Sonuçlarını Paylaşma.
Bu görevde, kullanıcının oyun sonuçlarını paylaşabilmesi için uygulamaya bir paylaşım özelliği eklersiniz. Bu, bir Android Intent, özellikle de Implicit intents kullanılarak uygulanır. Paylaşım özelliğine GameWonFragment classı içindeki bir options menüsü aracılığıyla erişilebilir. Uygulamanın kullanıcı arayüzünde menü öğesi, Tebrikler ekranının üst kısmında bir paylaşım simgesi olarak görünür.

#### Implicit Intents
Şimdiye kadar, Akctivity'nizdeki fragmentlar arasında navigate edebilmek için navigation components kullandınız. Android, diğer uygulamaların sağladığı activitylere gitmek için intent kullanmanıza da olanak tanır. Kullanıcının oyun oynama sonuçlarını arkadaşlarıyla paylaşmasına izin vermek için AndroidTrivia uygulamasında bu işlevi kullanırsınız.

Intent, Android bileşenleri arasında iletişim kurmak için kullanılan basit bir mesaj nesnesidir. İki tür Intent vardır: Explicit ve Implicit. Explictic Intent kullanarak belirli bir hedefe mesaj gönderebilirsiniz. Implicit Intent ile, hangi uygulamanın veya activity'nin görevi yerine getireceğini bilmeden bir activity başlatırsınız. Örneğin, uygulamanızın fotoğraf çekmesini istiyorsanız, genellikle görevi hangi uygulamanın veya activity'nin gerçekleştirdiğini umursamazsınız. Birden çok Android uygulaması aynı implict intent işleyebildiğinde, Android kullanıcıya bir seçici gösterir, böylece kullanıcı isteği işlemek için bir uygulama seçebilir.

Her implicit intent, yapılacak şeyin türünü tanımlayan bir ACTION'a sahip olmalıdır. ACTION_VIEW, ACTION_EDIT ve ACTION_DIAL gibi yaygın actionlar Intent classında tanımlanır.

Terminoloji uyarısı! Intent activityleri, uygulamanın navigation grafiğinde gösterilen activityler ile ilgisizdir.

### 1.Adım: Tebrikler Ekranına Bir Options Menusu Ekleyin.
1. GameWonFragment.kt Kotlin dosyasını açın.
2. onCreateView() yönteminin içinde, return'den önce setHasOptionsMenu() yöntemini çağırın ve true olarak iletin:

```
  setHasOptionsMenu(true)
```

### 2.Adım: Implicit Intent Oluşturun ve Çağırın
Kullanıcının oyun verileri hakkında mesaj gönderen bir Intent oluşturmak ve çağırmak için kodunuzu değiştirin. Birkaç farklı uygulama bir ACTION_SEND intentini işleyebildiğinden, kullanıcı, bilgilerini nasıl göndermek istediklerini seçmelerine olanak tanıyan bir seçici görecektir.

1. GameWonFragment classının içinde, onCreateView() yönteminden sonra, aşağıda gösterildiği gibi getShareIntent() adlı özel bir yöntem oluşturun. args için bir değer ayarlayan kod satırı, classın onCreateView()'ında kullanılan kod satırıyla aynıdır.

Yöntemin kodunun geri kalanında, kullanıcının paylaşmak istediği mesajı iletmek için bir ACTION_SEND intenti oluşturursunuz. Verinin MIME türü, setType() yöntemiyle belirtilir. Teslim edilecek gerçek veriler EXTRA_TEXT'de belirtilir. ( share_success_text dizesi, strings.xml kaynak dosyasında tanımlanır.)

```
// Creating our Share Intent
private fun getShareIntent() : Intent {
   val args = GameWonFragmentArgs.fromBundle(requireArguments())
   val shareIntent = Intent(Intent.ACTION_SEND)
   shareIntent.setType("text/plain")
            .putExtra(Intent.EXTRA_TEXT, getString(R.string.share_success_text, args.numCorrect, args.numQuestions))
   return shareIntent
}
```

2. getShareIntent() yönteminin altında bir shareSuccess() yöntemi oluşturun. Bu yöntem, intenti getShareIntent() öğesinden alır ve paylaşmaya başlamak için startActivity() öğesini çağırır.

```
// Starting an Activity with our new Intent
private fun shareSuccess() {
   startActivity(getShareIntent())
}
```

3. Başlangıç kodu zaten bir Winner_menu.xml menü dosyası içeriyor. Winner_menu öğesini inflate etmek için GameWonFragment classında onCreateOptionsMenu() öğesini geçersiz kılın.

shareIntent'i almak için getShareIntent() kullanın. shareIntent'in bir Activitye dönüştüğünden emin olmak için, cihazda yüklü olan uygulamaları ve activityleri takip eden Android paket yöneticisine (PaketManager) danışın. Paket yöneticisine erişim sağlamak için Activity'nin packageManager özelliğini kullanın vesolveActivity()'yi çağırın. Sonuç null'a eşitse, yani shareIntent çözülmezse, inflate edilmiş menüden paylaşım menü öğesini bulun ve menü öğesini görünmez yapın.

```
// Showing the Share Menu Item Dynamically
override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
       super.onCreateOptionsMenu(menu, inflater)
       inflater.inflate(R.menu.winner_menu, menu)
       if(getShareIntent().resolveActivity(requireActivity().packageManager)==null){
            menu.findItem(R.id.share).isVisible = false
       }
}
```

4. Menü öğesini işlemek için GameWonFragment claasında onOptionsItemSelected() öğesini geçersiz kılın. Menü öğesi tıklandığında shareSuccess() yöntemini çağırın:

```
// Sharing from the Menu
override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.share -> shareSuccess()
        }
        return super.onOptionsItemSelected(item)
}
```

5. Şimdi uygulamanızı çalıştırın. (Kod çalışmadan önce bazı paketleri GameWonFragment.kt'ye aktarmanız gerekebilir.) Oyunu kazandıktan sonra, app barın sağ üst köşesinde görünen paylaş simgesine dikkat edin. Zaferiniz hakkında bir mesaj paylaşmak için paylaş simgesine tıklayın.

![image](https://user-images.githubusercontent.com/80598532/149602806-07aaeb52-7bcd-4d12-b5b1-aacd12a304c8.png)

![image](https://user-images.githubusercontent.com/80598532/149602832-8012d77e-6b6e-4ed3-b392-286b65eaa7b0.png)










