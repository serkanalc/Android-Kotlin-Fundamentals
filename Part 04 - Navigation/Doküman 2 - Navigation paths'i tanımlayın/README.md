# Navigation Paths'i tanımlayın

- [Projeye navigation components ekleyin](#0) 
- [NavHostFragment oluşturun.](#1) 
- [Navigation Grafiğine Fragment Ekleyin](#2) 
- [Koşullu (Conditional) Navigation Ekleyin](#3) 
- [Geri Butonunun Hedefini Değiştirin](#4) 

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

















