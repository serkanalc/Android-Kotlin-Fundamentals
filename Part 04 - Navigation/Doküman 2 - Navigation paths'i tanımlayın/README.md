# Navigation Paths'i tanımlayın

- [Projeye Navigation Components Ekleyin](#0) 
- [NavHostFragment Oluşturun.](#1) 
- [Navigation Grafiğine Fragment Ekleyin](#2) 
- [Koşullu (Conditional) Navigation Ekleyin](#3) 
- [Geri Butonunun Hedefini Değiştirin](#4) 
- [App Bar'ına Up Butonu Ekleyin](#5)

## <a name="0"></a>Aşama Ø : Projeye Navigation Components Ekleyin

Navigation Component (navigation bileşenleri), uygulamanızdaki ekranlar arasında geçen karmaşık gezinme, geçiş animasyonu, derin bağlantı ve derleme zamanı kontrol edilen argümanı yönetebilen bir kitaplıktır.

 Navigation Component kullanmak için, navigation dependencies'i Gradle dosyalarınıza eklemeniz gerekir.
 
 1. Başlamak için AndroidTriviaFragment başlangıç uygulamasını indirin veya önceki kodlab'ten AndroidTrivia uygulamasının kendi kopyanızı kullanın. Android Studio'da AndroidTrivia uygulamasını açın.
 2. Proje: Android bölmesinde Gradle Scripts klasörünü açın. Dosyayı açmak için proje-level'deki build.gradle dosyasına çift tıklayın.

![image](https://user-images.githubusercontent.com/80598532/149426473-3649b58c-75b1-4de0-bedd-7ab524f70ff1.png)

3. Proje-level'daki build.gradle dosyasının en üstünde, diğer ext değişkenleriyle birlikte navigationVersion için bir değişken ekleyin. En son navigation sürüm numarasını bulmak için Android geliştirici belgelerindeki Declaring dependencies bölümüne bakın.

```
ext {
        ...
        navigationVersion = "2.3.0"
        ...
    }
``` 

4. Gradle Scripts Dosyaları klasöründe, modül düzeyinde build.gradle dosyasını açın. Navigasyon-fragment-ktx ve navigasyon-ui-ktx için dependencies'i aşağıda gösterildiği gibi ekleyin:

```
dependencies {
  ...
  implementation "androidx.navigation:navigation-fragment-ktx:$navigationVersion"
  implementation "androidx.navigation:navigation-ui-ktx:$navigationVersion"
  ...
}
```

5. Projeyi rebuild edin.

Not: Android Studio, dependencies yeni sürümleri konusunda sizi uyarabilir. Project Structure dialog kutusunu, yani File->Project Structure Yapısı'nı kullanarak sürüm numarası değişkenlerini güncellemeniz ve en son sürümü elde etmeniz gerekebilir.

### 2.Adım: Projeye Navigation Grafiği Ekleyin.

1. Proje: Android bölmesinde, res klasörüne sağ tıklayın ve New > Android Resource File seçin.
2. New Resource File dialog kutusunda, Kaynak türü olarak Navigation''ı seçin.
3. Dosya adı alanında, navigation dosyasını adlandırın.
4. Seçilen niteleyiciler kutusunun (Chosen qualifiers box) boş olduğundan emin olun ve Tamam'ı tıklayın. res > navigation klasöründe yeni bir dosya olan navigation.xml görünür.

![image](https://user-images.githubusercontent.com/80598532/149427682-e723cc2c-465f-43f2-add7-ef247187f52f.png)

5. res > navigation > navigation.xml dosyasını açın ve Navigation Editor açmak için Designsekmesine tıklayın. Layout editor'de NavHostFragments bulunamadı mesajına dikkat edin. Bu sorunu bir sonraki görevde düzeltirsiniz.

![image](https://user-images.githubusercontent.com/80598532/149427832-6660be88-9aef-446a-9737-967f309fdf8e.png)


## <a name="1"></a>Aşama 1 : NavHostFragment Oluşturun.

Bir navigation host fragment, navigation grafiğindeki fragmentlar için bir host görevi görür. Navigation host Fragment genellikle NavHostFragment olarak adlandırılır.

Kullanıcı, navigation grafiğinde tanımlanan hedefler arasında hareket ederken, navigation host Fragment, gerektiğinde fragmentları içeri ve dışarı değiştirir. Fragment, ayrıca uygun Fragment back stack'ini oluşturur ve yönetir.

Bu görevde, TitleFragment'i NavHostFragment ile değiştirmek için kodunuzu değiştirirsiniz.

1. es > layout > activity_main.xml dosyasını açın ve Code sekmesini açın.
2. Activity_main.xml dosyasında, mevcut Fragment başlığının adını androidx.navigation.fragment.NavHostFragment olarak değiştirin.
3. ID'yi myNavHostFragment olarak değiştirin.
4. Navigation host Fragment, hangi navigation grafiği kaynağının kullanılacağını bilmelidir. app:navGraph attribute'unu ekleyin ve @navigation/navigation olan navigation grafiği kaynağına ayarlayın.
5. app:defaultNavHost attribute'unu ekleyin ve "true" olarak ayarlayın. Şimdi bu navigation host varsayılan ana host'tur ve sistem back butonunu durduracaktır.

Activity_main.xml layout dosyasının içinde, fragmentınız şimdi aşağıdaki gibi görünür:

```
<!-- The NavHostFragment within the activity_main layout -->
            <fragment
                android:id="@+id/myNavHostFragment"
                android:name="androidx.navigation.fragment.NavHostFragment"
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                app:navGraph="@navigation/navigation"
                app:defaultNavHost="true" />
```


## <a name="2"></a>Aşama 2: Navigation Grafiğine Fragment Ekleyin.

Bu görevde, uygulamanın navigation grafiğine Fragment başlığını ve oyun Fragmenti'ni eklersiniz. Fragmentları birbirine bağlarsınız. Ardından, kullanıcının başlık ekranından oyun ekranına gidebilmesi için Play butonuna clickhandler eklersiniz.

### 1.Adım: Navigation Grafiğine 2 Fragment Ekleyin ve Onları Bir Action ile Birleştirin.

1. Navigation resource klasöründen navigation.xml'i açın. Navigation Editor'de, New Destination Button'a ![image](https://user-images.githubusercontent.com/80598532/149429793-ca4b49ce-6dd1-485c-bf23-86cf2c7d0973.png) tıklayın. Fragmentların ve activitylerin bir listesi görünür.

![image](https://user-images.githubusercontent.com/80598532/149429850-dc293163-2270-47d2-a180-1f3658e85017.png)

2. fragment_title'ı seçin. TitleFragment, uygulama kullanıcılarının uygulamayı ilk açtıklarında başladıkları yer olduğu için önce fragment_title eklersiniz.

![image](https://user-images.githubusercontent.com/80598532/149429952-a9454040-4882-4048-adaa-7a15dd5c60ca.png)
![image](https://user-images.githubusercontent.com/80598532/149429962-2e15a7a7-3db4-4abb-9f75-3b5b3a364bc5.png)

3. GameFragment'i eklemek için yeni Destination Butonu kullanın.

Önizleme bir "Önizleme Kullanılamıyor" mesajı gösteriyorsa, navigation XML'sini açmak için Code sekmesine tıklayın. gameFragment için fragment öğesinin aşağıda gösterildiği gibi "tools:layout="@layout/fragment_game" içerdiğinden emin olun.

```
<!-- The game fragment within the navigation XML, complete with tools:layout. -->
<fragment
   android:id="@+id/gameFragment"
   android:name="com.example.android.navigation.GameFragment"
   android:label="GameFragment"
   tools:layout="@layout/fragment_game" />
```

4.  Layout editor'de (Design view kullanarak), gameFragment'i Fragment başlığıyla örtüşmeyecek şekilde sağa sürükleyin.

![image](https://user-images.githubusercontent.com/80598532/149430168-e51bb059-901b-4931-82c3-ccc55b0269cf.png)

5. Önizlemede, işaretçiyi Fragment başlığının üzerinde tutun. Fragment view'un sağ tarafında dairesel bir bağlantı noktası görünür. Bağlantı noktasına tıklayın ve onu gameFragment'e sürükleyin veya gameFragment önizlemesinde herhangi bir yere sürükleyin. İki fragmentı birbirine bağlayan bir action oluşturulur.
6. Action attributes'u görmek için iki parçayı birbirine bağlayan oku tıklayın. Attributes bölmesinde, action kimliğinin action_titleFragment_to_gameFragment olarak ayarlandığını kontrol edin.

![image](https://user-images.githubusercontent.com/80598532/149430453-9ebdfa25-1522-47be-a175-907417e8aac0.png)

### 2.Adım: Play Butonuna Clickhandler Ekleyin.

Fragment başlığı, Fragment oyununa bir action ile bağlanır. Şimdi, kullanıcıyı oyun ekranına yönlendirmek için başlık ekranındaki Play butonunu istiyorsunuz.

1. Android Studio'da TitleFragment.kt dosyasını açın. onCreateView() yönteminin içine, return ifadesinden önce aşağıdaki kodu ekleyin:
```
binding.playButton.setOnClickListener{}
```

2. SetOnClickListener'ın içine, bindibg class aracılığıyla Play butonuna erişmek için kod ekleyin ve oyun parçasına gidin:
```
//The complete onClickListener with Navigation
binding.playButton.setOnClickListener { view : View ->
       view.findNavController().navigate(R.id.action_titleFragment_to_gameFragment)
}
```

3. Uygulamayı oluşturun ve ihtiyaç duyduğu tüm importlara sahip olduğundan emin olun. Örneğin, TitleFragment.kt dosyasına aşağıdaki satırı eklemeniz gerekebilir:
```
import androidx.navigation.findNavController
```

4. Uygulamayı çalıştırın ve başlık ekranında Play butonuna dokunun. Oyun ekranı açılır.

![image](https://user-images.githubusercontent.com/80598532/149430832-72ddc0a8-6476-46ad-9261-7e6c05a49d55.png)


## <a name="3"></a>Aşama 3: Koşullu (Conditional) Navigation Ekleyin.

Bu adımda, yalnızca belirli bağlamlarda kullanıcı tarafından kullanılabilen navigation olan koşullu navigation'u eklersiniz. Koşullu navigation için yaygın bir kullanım durumu, kullanıcının oturum açıp açmamasına bağlı olarak bir uygulamanın farklı bir akışa sahip olmasıdır.

Uygulamanız farklı bir case: Uygulamanız, kullanıcının tüm soruları doğru yanıtlayıp yanıtlamadığına bağlı olarak farklı bir Fragmenta gidecektir.

Başlangıç kodu, koşullu navigation'da kullanmanız için iki fragment içerir:
- GameWonFragment, kullanıcıyı "Tebrikler!" yazan bir ekrana götürür.
- GameOverFragment, kullanıcıyı "Yeniden Dene" mesajı gösteren bir ekrana götürür.

### 1.Adım: Navigation Grafiğine GameWonFragment ve GameOverFragment Ekleyin.

1. Navigation klasöründe bulunan navigation.xml dosyasını açın.
2. Game-over Fragmentini navigation grafiğine eklemek için, Navigation editor'ünde New Destination Butonuna ![image](https://user-images.githubusercontent.com/80598532/149434187-eca7b8b8-bb67-4012-b874-512d3bfd05f1.png) tıklayın ve fragment_game_over'ı seçin.

![image](https://user-images.githubusercontent.com/80598532/149434197-0e8d726b-0182-4090-94bf-a92eb0271f05.png)

3. Layout editor'un önizleme alanında, game-over fragmentı, ikisi örtüşmeyecek şekilde game Fragmanı'nın sağına sürükleyin. Game-over Fragment'in ID attribute'unu gameOverFragment olarak değiştirdiğinizden emin olun.
4. game-won fragment'ı navigation grafiğine eklemek için New destination butonuna ![image](https://user-images.githubusercontent.com/80598532/149434394-1cd9c30a-6621-409b-b623-21ab85c72cdb.png) tıklayın ve fragment_game_won öğesini seçin.

![image](https://user-images.githubusercontent.com/80598532/149434410-5d085278-a009-4f4c-b85f-7437adab95b6.png)

5. game-won fragment'ı üst üste gelmemesi için game-over fragmentının altına sürükleyin. game-won fragmentının ID attribute'unu gameWonFragment olarak adlandırdığınızdan emin olun.

![image](https://user-images.githubusercontent.com/80598532/149434578-e52e2877-6708-4349-9e5b-06ab08c61288.png)

### 2.Adım: Game Fragment'ı Game-Result Fragment'ına Bağlayın.
Bu adımda, game fragment'ını hem game-won Fragment'a hem de game-over Fragmentin'a bağlarsınız.

1. Layout editor'un önizleme alanında, dairesel bağlantı noktası görünene kadar işaretçiyi game fragment üzerinde tutun.
2. Bağlantı noktasına tıklayın ve game-over fragmenta'a doğru sürükleyin. Artık game Fragment'ı game-over fragment'a bağlayan bir action temsil eden mavi bir bağlantı oku görünür.
3. Aynı şekilde, game Fragment'ını game-won Fragment'a bağlayan bir action oluşturun. Layout editor şimdi aşağıdaki ekran görüntüsüne benziyor:

![image](https://user-images.githubusercontent.com/80598532/149541016-d43f8cc0-e222-441d-b6c7-4a2fc7bc55af.png)

4. Önizlemede, imleci game Fragmentini game-won Fragment'a bağlayan çizginin üzerinde tutun. Action ID'nin otomatik olarak atandığına dikkat edin.

### 3.Adım: Bir Fragment'tan Diğerine Geçmek (Navigate etmek) İçin Kod Ekleyin.
GameFragment, oyunla ilgili soruları ve cevapları içeren bir Fragment class'ıdır. Class ayrıca kullanıcının oyunu kazanıp kazanmadığını belirleyen mantığı da içerir. Oyuncunun kazanmasına veya kaybetmesine bağlı olarak GameFragment classına conditional navigation eklemeniz gerekir.

1. GameFragment.kt dosyasını açın. onCreateView() yöntemi, oyuncunun kazanıp kazanmadığını belirleyen bir if/else koşulunu tanımlar:

```
binding.submitButton.setOnClickListener @Suppress("UNUSED_ANONYMOUS_PARAMETER")
        { 
              ...
                // answer matches, we have the correct answer.
                if (answers[answerIndex] == currentQuestion.answers[0]) {
                    questionIndex++
                    // Advance to the next question
                    if (questionIndex < numQuestions) {
                        currentQuestion = questions[questionIndex]
                        setQuestion()
                        binding.invalidateAll()
                    } else {
                        // We've won!  Navigate to the gameWonFragment.
                    }
                } else {
                    // Game over! A wrong answer sends us to the gameOverFragment.
                }
            }
        }
```

2. Oyunu kazanmak için else koşulunun içine, gameWonFragment'e giden aşağıdaki kodu ekleyin. Action adının (bu örnekte action_gameFragment_to_gameWonFragment) navigation.xml dosyasında ayarlananla tam olarak eşleştiğinden emin olun.

``` 
// We've won!  Navigate to the gameWonFragment.
view.findNavController()
   .navigate(R.id.action_gameFragment_to_gameWonFragment)
```

3. Oyunu kaybetmek için else koşulunun içine, gameOverFragment'e giden aşağıdaki kodu ekleyin:

```
// Game over! A wrong answer sends us to the gameOverFragment.
view.findNavController().
   navigate(R.id.action_gameFragment_to_gameOverFragment)
```

4. Uygulamayı çalıştırın ve soruları cevaplayarak oyunu oynayın. Üç soruyu da doğru cevaplarsanız, uygulama GameWonFragment'e gider.

![image](https://user-images.githubusercontent.com/80598532/149541977-01e8146d-adff-4993-a4e7-4fadec4bd2fb.png)

Herhangi bir soruyu yanlış anlarsanız, uygulama hemen GameOverFragment'e gider.

![image](https://user-images.githubusercontent.com/80598532/149542058-94d5efae-5c4d-48e2-abbc-aa69d745aec1.png)

Android sisteminin Geri butonu yukarıdaki ekran görüntüsünde 1 olarak gösterilmiştir. Kullanıcı, oyunun kazanıldığı fragment'ta veya oyunun bittiği fragment'ta Geri butonuna basarsa, uygulama soru ekranına gider. İdeal olarak, Geri butonu uygulamanın başlık ekranına geri dönmelidir. Sonraki görevde Geri butonunun hedefini değiştirirsiniz.


## <a name="4"></a>Aşama 4: Geri Butonunun Hedefini Değiştirin.
Android sistemi, kullanıcıların Android destekli bir cihazda nereye gittiklerini takip eder. Kullanıcı cihazda yeni bir hedefe her gittiğinde, Android bu hedefi back stack'e ekler.

Kullanıcı Geri butonuna bastığında uygulama, back stack'in en üstündeki hedefe gider. Default olarak, back stack'in üst kısmı, kullanıcının en son görüntülediği ekrandır. Geri butonu, aşağıda gösterildiği gibi, genellikle ekranın alt kısmındaki en soldaki butondur. (Geri butonunun tam görünümü farklı cihazlarda farklıdır.)

![image](https://user-images.githubusercontent.com/80598532/149542853-0810a900-15f8-457b-a595-834ddddd7f13.png)

Şimdiye kadar, back stack'i sizin için navigation controller'a bıraktınız. Kullanıcı uygulamanızda bir hedefe gittiğinde, Android bu hedefi back stack'e ekler.

AndroidTrivia uygulamasında, kullanıcı GameOverFragment veya GameWonFragment ekranından Geri butonuna bastığında GameFragment'e geri döner. Ancak oyun bittiği için kullanıcıyı GameFragment'e göndermek istemezsiniz. Kullanıcı oyunu yeniden başlatabilir, ancak kendilerini tekrar başlık ekranında bulması daha iyi bir deneyim olacaktır.

Bir navigation action back stack'i değiştirebilir. Bu görevde, Game Fragment'a navigate eden action'u, GameFragment'i back stack'ten kaldıracak şekilde değiştirirsiniz. Kullanıcı oyunu kazandığında veya kaybettiğinde, Geri butonuna basarsa, uygulama GameFragment'i atlar ve TitleFragment'e geri döner.

### 1.Adım: Navigation Actions İçin Pop Davranışını Ayarlayın.

Bu adımda, kullanıcı GameWon veya GameOver ekranındayken Geri butonuna basıldığında onları başlık ekranına döndürecek şekilde back stack'i yönetirsiniz. Fragmentları birbirine bağlayan actionlar için "pop" davranışını ayarlayarak back stack'i yönetirsiniz:

- Bir actionun "popUpTo" attribute'u, navigation'dan önce back stack'ı belirli bir hedefe açar. (Hedefler back stack'ten kaldırılır.)
- popUpToInclusive attribute'u false ise veya ayarlanmazsa, popUpTo belirtilen hedefe kadar olan hedefleri kaldırır, ancak belirtilen hedefi back stack'te bırakır.
- popUpToInclusive true olarak ayarlanırsa, popUpTo attribute'u, back stack'ten verilen hedef de dahil olmak üzere tüm hedefleri kaldırır.
- popUpToInclusive true ise ve popUpTo uygulamanın başlangıç konumuna ayarlanmışsa, action tüm uygulama hedeflerini back stack'ten kaldırır. Geri butonu, kullanıcıyı uygulamadan tamamen çıkarır.

Bu adımda, önceki görevde oluşturduğunuz iki action için popUpTo attribute'u ayarlarsınız. Bunu, Layout Editor'un Attributes bölmesindeki Pop To alanını kullanarak yaparsınız.

1.  res > navigation klasöründe navigasyon.xml'i açın. Navigation grafiği, layout editor'de görüntülenmezse, Design sekmesine tıklayın.
2.  gameFragment'ten gameOverFragment'e gitmek için action'u seçin. (Önizleme alanında action, iki fragment'ı birbirine bağlayan mavi bir çizgiyle temsil edilir.)
3.  Attributes bölmesinde, popUpTo'yu gameFragment olarak ayarlayın. popUpToInclusive onay kutusunu seçin.

![image](https://user-images.githubusercontent.com/80598532/149545218-12dc7c5a-c451-4071-a1f6-ca036932b7a9.png)

Bu, XML'deki popUpTo ve popUpToInclusive attributelerini ayarlar. Attributeler, navigation components'e, GameFragment dahil olmak üzere back stack'den fragmentları kaldırmasını söyler. (Bu, popUpTo alanını titleFragment olarak ayarlamak ve popUpToInclusive onay kutusunu temizlemekle aynı etkiye sahiptir.)

4. gameFragment'ten gameWonFragment'e gitmek için action'u seçin. Yine, Attributes bölmesinde popUpTo'yu gameFragment olarak ayarlayın ve popUpToInclusive onay kutusunu seçin.

![image](https://user-images.githubusercontent.com/80598532/149546134-d61820ac-45cf-4ba3-b778-5222411922b0.png)

5. Uygulamayı çalıştırın ve oyunu oynayın, ardından Geri butonuna basın. Kazansanız da kaybetseniz de Geri butonu sizi TitleFragment'e geri götürür.

### 2.Adım: Daha fazla Navigation Actions ve onClick handlers Ekleyin.
Uygulamanız şu anda aşağıdaki kullanıcı akışına sahip:
- Kullanıcı oyunu oynar ve kazanır veya kaybeder ve uygulama GameWon veya GameOver ekranına gider.
- Kullanıcı bu noktada sistem Geri butonuna basarsa, uygulama TitleFragment'e gider. (Bu davranışı, yukarıdaki görevin 1. Adımında uyguladınız.)

Bu adımda, kullanıcı akışının iki adımını daha uygularsınız:
- Kullanıcı Next Match veya Try Again butonuna dokunursa uygulama GameFragment ekranına gider.
- Kullanıcı bu noktada sistem Geri butonuna basarsa, uygulama TitleFragment ekranına gider (GameWon veya GameOver ekranına geri dönmek yerine).

Bu kullanıcı akışını oluşturmak için back stack'i yönetmek için popUpTo attribute'unu kullanın:
1. Navigasyon.xml dosyasının içine gameOverFragment'ı gameFragment'e bağlayan bir navigation action ekleyin. Action ID'deki fragment adlarının, XML'deki fragment adlarıyla eşleştiğinden emin olun. Örneğin, action ID action_gameOverFragment_to_gameFragment olabilir.

![image](https://user-images.githubusercontent.com/80598532/149548141-09cbbc5f-7473-4a79-882b-4f9ad05722f7.png)

2. Attribute bölmesinde, action'un popUpTo attribute'unu titleFragment olarak ayarlayın.
3. TitleFragment öğesinin back stack'ten kaldırılan hedeflere dahil edilmesini istemediğiniz için popUpToInclusive onay kutusunu temizleyin. Bunun yerine, back stack'ten titleFragment'e (ancak buna dahil değil) kadar olan her şeyin kaldırılmasını istiyorsunuz.

![image](https://user-images.githubusercontent.com/80598532/149548478-d032b9ab-c1d4-424f-a9a3-621793a90040.png)

4. Navigation.xml dosyasının içine gameWonFragment'ı gameFragment'e bağlayan bir navigation action ekleyin.

![image](https://user-images.githubusercontent.com/80598532/149548626-d748dddb-3640-4149-af57-590e2bd70b64.png)

5. Az önce oluşturduğunuz action için popUpTo attribute'unu titleFragment olarak ayarlayın ve popUpToInclusive onay kutusunu temizleyin.

![image](https://user-images.githubusercontent.com/80598532/149548716-0444c556-7c60-461a-864e-a4409d062d44.png)

Şimdi Try Again ve SNext Mtch butonlarına işlevsellik ekleyin. Kullanıcı herhangi bir butona dokunduğunda, kullanıcının oyunu tekrar deneyebilmesi için uygulamanın GameFragment ekranına gitmesini istersiniz.

1. GameOverFragment.kt Kotlin dosyasını açın. onCreateView() yönteminin sonunda, return ifadesinden önce aşağıdaki kodu ekleyin. Kod, Try Again butonuna bir click listener ekler. Kullanıcı butona dokunduğunda, uygulama Fragment oyununa gider.

```
// Add OnClick Handler for Try Again button
        binding.tryAgainButton.setOnClickListener{view: View->
        view.findNavController()
                .navigate(R.id.action_gameOverFragment_to_gameFragment)}
```

2. GameWonFragment.kt Kotlin dosyasını açın. onCreateView() yönteminin sonunda, return ifadesinden önce aşağıdaki kodu ekleyin:

```
// Add OnClick Handler for Next Match button
        binding.nextMatchButton.setOnClickListener{view: View->
            view.findNavController()
                    .navigate(R.id.action_gameWonFragment_to_gameFragment)}
```

3. Uygulamanızı çalıştırın, oyunu oynayın ve Next Match ve Try Again buutonlarınıtest edin. Oyunu tekrar deneyebilmeniz için her iki buton da sizi oyun ekranına geri götürmelidir.
4. Oyunu kazandıktan veya kaybettikten sonra Next Match veya Try Again'e dokunun. Şimdi sistem Geri butonuna basın. Uygulama, az önce geldiğiniz ekrana geri dönmek yerine başlık ekranına gitmelidir.



## <a name="5"></a>Aşama 5: App Bar'ına Up Butonu Ekleyin.

### App Bar
Bazen action bar olarak da adlandırılan app bar, uygulama markası ve kimliği için ayrılmış bir alandır. Örneğin, app barın rengini ayarlayabilirsiniz.App bar, kullanıcının seçenekler menüsü gibi tanıdık navigation özelliklerine erişmesini sağlar. App bar'dan seçenekler menüsüne erişmek için kullanıcı, ![image](https://user-images.githubusercontent.com/80598532/149550058-3abe1791-7dbe-4455-b953-ccaeb3b0832c.png) dikey üç noktalı simgeye dokunur.

App bar, her ekranda değişebilen bir başlık dizesi görüntüler. AndroidTrivia uygulamasının başlık ekranı için app bar'da "Android Trivia" görüntülenir. Soru ekranında, başlık dizesi, kullanıcının hangi soruyu sorduğunu da gösterir ("1/3", "2/3" veya "3/3.")

#### Up Butonu
Şu anda uygulamanızda, kullanıcı önceki ekranlara gitmek için sistem Geri butonunu kullanır. Bununla birlikte, Android uygulamalarında, app barın sol üst kısmında görünen bir ekran Up(Yukarı) butonu da olabilir.

AndroidTrivia uygulamasında,Up butonunun başlık ekranı dışında her ekranda görünmesini istiyorsunuz. Başlık ekranı, uygulamanın ekran hiyerarşisinin en üstünde olduğundan, kullanıcı başlık ekranına ulaştığında Up butonu kaybolmalıdır.

#### Up(Yukarı) Butonu vs Back(Geri) Butonu

- Aşağıdaki ekran görüntüsünde 1 olarak gösterilen Up butonu, app bar'da görünür.
- Up butonu, ekranlar arasındaki hiyerarşik ilişkilere dayalı olarak uygulama içinde gezinir. Up butonu, kullanıcıyı asla uygulamadan çıkarmaz.
- Aşağıdaki ekran görüntüsünde 2 olarak gösterilen Back(Geri) butonu, hangi uygulama açık olursa olsun sistem gezinme çubuğunda veya cihazın kendisinde mekanik bir buton olarak görünür.
- Geri(Back) butonu, kullanıcının yakın zamanda çalıştığı ekranlarda (back stack'de) geriye doğru gider.

![image](https://user-images.githubusercontent.com/80598532/149551161-ea21c8e5-13ac-487e-a8c4-d524387e028a.png)

#### Up Butonu için Destek Ekleyin
Navigation components, NavigationUI adlı bir UI kitaplığı içerir. navigation components, bir NavigationUI classı içerir. Bu class, üst app bar, navigation drawer vebottom navigation ile navigation'u yöneten statik yöntemler içerir. Navigation controller, Up butonun davranışını uygulamak içinapp bar ile bütünleşir, böylece bunu kendiniz yapmanız gerekmez.

Aşağıdaki adımlarda, uygulamanıza bir Up butonu eklemek için navigation controller kullanırsınız:

1. MainActivity.kt kotlin dosyasını açın. onCreate() yönteminin içine, navigation controller object'i bulmak için kod ekleyin:

```
val navController = this.findNavController(R.id.myNavHostFragment)
```

2. Ayrıca onCreate() yönteminin içinde, navigation controller'ı app bar'a bağlamak için kod ekleyin:

```
NavigationUI.setupActionBarWithNavController(this,navController)
```

3. onCreate() yönteminden sonra, navigation controller'de navigationUp() öğesini çağırmak için onSupportNavigateUp() yöntemini geçersiz kılın:

```
override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.myNavHostFragment)
        return navController.navigateUp()
    }
```

4. Uygulamayı çalıştırın. Up butonu, başlık ekranı dışındaki her ekranda app bar'da görünür. Uygulamanın neresinde olursanız olun, Up butonuna dokunmak sizi başlık ekranına götürür.

Sol üst köşede "fragment_title" ifadesini görebilirsiniz. res>navigation>navigation.xml dosyasını düzenleyin ve com.example.android.navigation.TitleFragment etiketini "fragment_title" yerine @string/app_name olarak değiştirin. Ardından res>values>strings.xml içindeki bu kaynağı "Android Trivia" olarak tanımlayın ve uygulamayı yeniden çalıştırın.

![image](https://user-images.githubusercontent.com/80598532/149590699-e66bfdd4-f1ba-4d24-9112-697873c34ef8.png)


## <a name="6"></a>Aşama 6: Option Menusu Ekleyin.
Android, options menüsü de dahil olmak üzere farklı türde menülere sahiptir. Modern Android cihazlarda kullanıcı, app bar'da görünen üç dikey noktaya ![image](https://user-images.githubusercontent.com/80598532/149591094-9038b25a-ce87-46a2-97d3-6ff7cae700e6.png) dokunarak options menüsüne erişir.

Bu görevde, options menüsüne bir About menü öğesi eklersiniz. Kullanıcı About menü öğesine dokunduğunda, uygulama AboutFragment'e gider ve kullanıcı, uygulamanın nasıl kullanılacağına ilişkin bilgileri görür.

### 1.Adım: Navigation Grafiğine AboutFragment Ekleyin.

1. Navigasyon.xml dosyasını açın ve navigation grafiğini görmek için Design sekmesine tıklayın.
2. New Destination butonuna tıklayın ve fragman_about'u seçin.

![image](https://user-images.githubusercontent.com/80598532/149591436-827465f0-7d8d-497a-a84c-b265c96fbc93.png)

3. Layout Editor'de, "about" fragmentını sola sürükleyin, böylece diğer fragmentlarla örtüşmez. Fragment ID'sinin aboutFragment olduğundan emin olun.

![image](https://user-images.githubusercontent.com/80598532/149591534-fbde3965-295e-420c-acd9-8aae07297880.png)

### 2.Adım: Options-menu Kaynağını Ekleyin.
1. Android Studio Projes bölmesinde, res klasörüne sağ tıklayın ve New > Android Resource File'ı seçin.
2. New Resource File kutusunda, dosyayı options_menu olarak adlandırın.
3. Kaynak türü olarak Menu'yu seçin ve Tamam'a tıklayın.

![image](https://user-images.githubusercontent.com/80598532/149591746-a67723c3-e6e3-4432-bd38-b72ce25f865f.png)

4. res > menu klasöründen options_menu.xml dosyasını açın ve Layout Editor'u görmek için Design sekmesine tıklayın.
5. Palet bölmesinden bir Menü Öğesini (aşağıdaki ekran görüntüsünde 1 olarak gösterilir) sürükleyin ve design editor bölmesinde (2) herhangi bir yere bırakın. Önizlemede (3) ve Component Tree'de(4) bir menü öğesi belirir.

![image](https://user-images.githubusercontent.com/80598532/149591868-f521e3c6-bfd3-4e6f-b67c-8e325b99abc7.png)

6. Önizlemede veya Component Tree'de, Attributes bölmesinde niteliklerini göstermek için menü öğesine tıklayın.
7. Menu öğesinin ID'sini aboutFragment olarak ayarlayın. Başlığı @string/about olarak ayarlayın.

![image](https://user-images.githubusercontent.com/80598532/149591939-6966639f-c6c8-449a-b0c9-ed670f2fc92d.png)

İpucu: Yeni eklediğiniz menu öğesinin ID'sinin, navigation grafiğine eklediğiniz AboutFragment'in ID'si ile tam olarak aynı olduğundan emin olun. Bu, onClick handler kodunu çok daha basit hale getirecektir.

### 3.Adım: 
Bu adımda, kullanıcı About  menü öğesine dokunduğunda davranışı uygulamak için kod eklersiniz.

1. TitleFragment.kt Kotlin dosyasını açın. onCreateView() yönteminin içinde, return'den önce setHasOptionsMenu() yöntemini çağırın ve true olarak iletin.

```
override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                         savedInstanceState: Bundle?): View? {
   ...
   setHasOptionsMenu(true)
   return binding.root
}
```

2. onCreateView() yönteminden sonra onCreateOptionsMenu() yöntemini geçersiz kılın. Yöntemde, options menüsünü ekleyin ve menü kaynak dosyasını inflate edin.

```
override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.options_menu, menu)
}
```

3. Menü öğesine dokunulduğunda uygun action'u gerçekleştirmek için onOptionsItemSelected() yöntemini geçersiz kılın. Bu durumda action, seçilen menü öğesiyle aynı ID'ye sahip fragmenta gitmektir.

```
override fun onOptionsItemSelected(item: MenuItem): Boolean {
     return NavigationUI.
            onNavDestinationSelected(item,requireView().findNavController())
            || super.onOptionsItemSelected(item)
}
```

4. Uygulama oluşturulmazsa, koddaki çözülmemiş referansları düzeltmek için paketleri import etmeniz gerekip gerekmediğini kontrol edin. Örneğin, birkaç referansı çözümlemek için import android.view.* ekleyebilir (ve import android.view.ViewGroup gibi daha spesifik importları değiştirebilirsiniz).
5. Uygulamayı çalıştırın ve options menüsündeki About menü öğesini test edin. Menü öğesine dokunduğunuzda, uygulama "about" ekranına gitmelidir.

![image](https://user-images.githubusercontent.com/80598532/149592701-cd867afd-954a-4530-82d2-8798fe980778.png)


## <a name="7"></a>Aşama 7: Navigation Drawer Ekleyin.
Bu görevde, AndroidTrivia uygulamasına bir Navigation Drawer eklersiniz. Navigation Drawer, ekranın kenarından dışarı kayan bir paneldir. Drawer tipik olarak bir başlık ve bir menü içerir.

Telefon boyutundaki cihazlarda navigation drawer kullanılmadığında gizlenir. İki tür kullanıcı eylemi, navigation drawerın görünmesini sağlayabilir:

- Drawer, kullanıcı ekranın başlangıç kenarından bitiş kenarına doğru kaydırdığında görünür. AndroidTrivia uygulamasında, kullanıcı soldan sağa kaydırdığında navigation drawer görünür.
- Drwaer, kullanıcı uygulamanın başlangıç noktasındayken görünür ve app bar'daki drawer simgesine dokunur. (drawer simgesine bazen navigation drawer butonu veya hamburger simgesi ![image](https://user-images.githubusercontent.com/80598532/149593085-57bd17ae-4860-4809-b771-122bcbbabaf0.png) adı verilir.)

Aşağıdaki ekran görüntüsü açık bir navigation drawer göstermektedir.

![image](https://user-images.githubusercontent.com/80598532/149593141-b9f14e43-37f7-4b41-bee4-6ec0fe7222d8.png)

Navigation drawer, Android için  Material Components kütüphanesi veya Material kütüphanesinin bir parçasıdır. Google'ınMaterial Design yönergelerinin parçası olan kalıpları uygulamak için Material kütüphanesini kullanırsınız.

AndroidTrivia uygulamanızda, navigation drawer iki menü öğesi içerecektir. İlk öğe mevcut "about" fragmentı işaret eder ve ikinci öğe yeni bir "rules" fragmentini işaret eder.

![image](https://user-images.githubusercontent.com/80598532/149593420-1885b3ad-c799-41ef-8e13-335372e7bc2f.png)

### 1.Adım:  Material Kütüphanesini Projenize Ekleyin.

App-level Gradle derleme dosyasında, Malzeme kütüphaneisi dependency'lerini ekleyin:

```
dependencies {
    ...
    implementation "com.google.android.material:material:$version"
    ...
}
```

2. Projenizi senkronize edin.

Not: Projenizin File>ProjectStructure>Variables ayarlarında $version değişkenini en son sürümle eşleşecek şekilde oluşturmanız veya güncellemeniz gerekebilir.

### 2.Adım: Destination Fragmentların ID'leri Olduğundan Emin Olun.
Navigation drawer'da, her biri Navigation drawer'dan erişilebilen bir fragmentı temsil eden iki menü öğesi bulunur. Her iki destinationun da (varış noktasının da) navigation grafiğinde bir ID'si olmalıdır.

AboutFragment'in Navigation grafiğinde zaten bir ID'si var, ancak RulesFragment'te yok, bu yüzden şimdi ekleyin:

1. Nasıl göründüğünü görmek için fragman_rules.xml layout dosyasını açın. Design editor'de önizlemeye bakmak için Design sekmesine tıklayın.
2. Navigation Editor'de navigation.xml dosyasını açın. New Destination Butonuna tıklayın ve Fragment kurallarını ekleyin. ID'sini RulesFragment olarak ayarlayın.

![image](https://user-images.githubusercontent.com/80598532/149594614-355f9fb4-5ad0-4ab0-a707-203def4fddb8.png)

![image](https://user-images.githubusercontent.com/80598532/149594627-36723f72-c9a7-414c-b6c3-574ea1f39cd9.png)


### 3.Adım: Drawe Menüsünü ve Drawer Layout'unu Oluşturun
Bir Navigation drawer oluşturmak için Navigation menüsünü oluşturursunuz. Ayrıca, viewlarınızı layout dosyasındaki bir DrawerLayout içine koymanız gerekir.

1. Drawer için menüyü oluşturun. Proje bölmesinde, res klasörüne sağ tıklayın ve New Resource File'ı seçin. Dosyayı navdrawer_menu olarak adlandırın, kaynak türünü Menü olarak ayarlayın ve Tamam'a tıklayın.

![image](https://user-images.githubusercontent.com/80598532/149594864-fd0c6fe2-3883-44cf-a337-a0f63c2fe4d6.png)

2. res > menu klasöründen navdrawer_menu.xml dosyasını açın, ardından Design sekmesine tıklayın. Palet bölmesinden Component Tree bölmesine sürükleyerek iki menü öğesi ekleyin.
3. İlk menü öğesi için ID'yi RulesFragment olarak ayarlayın. (Bir menü öğesinin ID'si, Fragment'ın ID'si ile aynı olmalıdır.) Başlığı @string/rules ve iconu @drawable/rules olarak ayarlayın.

![image](https://user-images.githubusercontent.com/80598532/149595746-dc7b7c5a-0ffa-45f0-8efd-9f06287efaa6.png)

4. İkinci menü öğesi için ID'yi aboutFragment, başlık dizesini @string/about ve iconu @drawable/about_android_trivia olarak ayarlayın.

![image](https://user-images.githubusercontent.com/80598532/149595821-921cc438-9572-4079-b533-ddfe70052db2.png)

Not:Menü öğesi için destination Fragment ile aynı ID'yi kullanırsanız, onClick Listenerı uygulamak için herhangi bir kod yazmanız gerekmez!

5. Activity_main.xml layout dosyasını açın. Tüm drawer işlevlerini free olarak elde etmek için viewkarınızı bir DrawerLayout içine yerleştirin. <LinearLayout> öğesinin tamamını bir <DrawerLayout> içine alın. (Başka bir deyişle, root görünüm olarak bir DrawerLayout ekleyin.)

```
 <layout xmlns:android="http://schemas.android.com/apk/res/android"
   xmlns:app="http://schemas.android.com/apk/res-auto">
   <androidx.drawerlayout.widget.DrawerLayout
       android:id="@+id/drawerLayout"
       android:layout_width="match_parent"
       android:layout_height="match_parent">

   <LinearLayout
       . . . 
       </LinearLayout>
   </androidx.drawerlayout.widget.DrawerLayout>
</layout>
```

 6. Şimdi, az önce tanımladığınız navdrawer_menu'yu kullanan bir NavigationView olan drawer'ı ekleyin. </LinearLayout> öğesinden sonra DrawerLayout'a aşağıdaki kodu ekleyin:

```
<com.google.android.material.navigation.NavigationView
   android:id="@+id/navView"
   android:layout_width="wrap_content"
   android:layout_height="match_parent"
   android:layout_gravity="start"
   app:headerLayout="@layout/nav_header"
   app:menu="@menu/navdrawer_menu" />
 ```

### 4.Adım: Navigation Drawer'ı Gösterin

Navigation drawer ve Navigation drawer layout'u için menü öğeleri oluşturdunuz. Artık kullanıcılar Navigation drawerdaki öğeleri seçtiğinde uygulamanın uygun fragmenta gitmesi için Navigation drawerı Navigation controllera bağlamanız gerekir.

1. MainActivity.kt Kotlin dosyasını açın. onCreate() içinde, kullanıcının navigation drawer görüntülemesine izin veren kodu ekleyin. Bunu setupWithNavController() öğesini çağırarak yapın. onCreate() öğesinin altına aşağıdaki kodu ekleyin:
 
 ```
 NavigationUI.setupWithNavController(binding.navView, navController)
 ``` 

2. Uygulamanızı çalıştırın.Navigation drawer'ı görüntülemek için sol kenardan kaydırın ve drawerdaki menü öğelerinin her birinin doğru yere gittiğinden emin olun.

Navigation drawer çalışıyor olsa da, bir şeyi daha düzeltmeniz gerekiyor. Genellikle uygulamalar, kullanıcıların ana ekrandaki app bar'da drawer butonuna(üç satır) ![image](https://user-images.githubusercontent.com/80598532/149596456-d34ee7c6-3ae6-4ea4-8f48-8c9476238a71.png) dokunarak Navigation drawerı görüntülemelerine de olanak tanır. Uygulamanız henüz ana ekranda Navigation drawerı göstermiyor.

### 5.Adım: Drawer Butonundan Navigation Drawerı Görüntüleyin.
 
 1. MainActivity.kt Kotlin dosyasında, drawer düzenini temsil etmek için lateinit drawerLayout üye değişkenini ekleyin:
 
 ```
    private lateinit var drawerLayout: DrawerLayout
 ```
 
 Not: Kotlin bir "null safety" dilidir. Boş güvenlik sağlama yollarından biri, herhangi bir boş referans döndürme tehlikesi olmadan değişkenin başlatılmasını geciktirmenize izin veren lateinit değiştiricisidir.
Bu durumda, drawerLayout, onu null yapılabilir hale getirmekten kaçınmak için lateinit ile bildirilir. onCreate() içine initialize edilecektir.
 
 2. onCreate() yönteminin içinde, binding değişkeni initialize edildikten sonra drawerLayout'u initialize edin.
 
 ```
 val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this,
                R.layout.activity_main)

drawerLayout = binding.drawerLayout
 ```
 
3. setupActionBarWithNavController() yöntemine üçüncü parametre olarak drawerLayout'u ekleyin:
 
 ```
 NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
 ```
 
 4. navController.navigateUp döndürmek yerine NavigationUI.navigateUp döndürmek için onSupportNavigateUp() yöntemini düzenleyin. Navigation controllerı ve drawer layoutu navigationUp()'a iletin. Yöntem aşağıdaki gibi görünecektir:
 
 ```
 override fun onSupportNavigateUp(): Boolean {
   val navController = this.findNavController(R.id.myNavHostFragment)
   return NavigationUI.navigateUp(navController, drawerLayout)
}
 ```
 
 5. Tüm referansların çözülmesi için dosyaya başka bir import eklemeniz gerekebilir, örneğin:
 
 ```
 import androidx.drawerlayout.widget.DrawerLayout
 ```
 
 6.Uygulamanızı çalıştırın. Navigation drawerı görüntülemek için sol kenardan kaydırın ve drawerdaki menü öğelerinin her birinin doğru yere gittiğinden emin olun.
 7. Ana ekrana gidin ve Navigation drawerın göründüğünden emin olmak için ![image](https://user-images.githubusercontent.com/80598532/149597124-0f1bad09-e804-45a5-8b63-bf0972b44776.png) Navigation drawer butonuna dokunun. Navigation drawerdaki Rules veya About seçeneklerine tıklamanın sizi doğru yere götürdüğünden emin olun.

![image](https://user-images.githubusercontent.com/80598532/149597136-c3ad87a4-1d18-4bf8-a239-60b27dced8fa.png)

#### Tebrikler!
Artık uygulamanıza birkaç farklı navigation seçeneği eklediniz.

Kullanıcı artık oyunu oynayarak uygulamada ilerleyebilir. Up butonunu kullanarak istedikleri zaman ana ekrana dönebilirler. About ekranınaOptions menüsünden veya navigation drawerdan ulaşabilirler. Geri butonuna basmak, onları uygulama için anlamlı olacak şekilde önceki ekranlara geri götürür. Kullanıcı, herhangi bir ekranda soldan kaydırarak veya ana ekrandakiapp bardaki drawer butonuna dokunarak navigation drawerı açabilir.

Uygulamanız, kullanıcılarınızın kullanması için sezgisel olan sağlam, mantıksal navigaiton yolları içerir. Tebrikler!


