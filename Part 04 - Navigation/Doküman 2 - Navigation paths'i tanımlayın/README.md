# Navigation Paths'i tanımlayın

- [Projeye navigation components ekleyin](#0) 
- [NavHostFragment oluşturun.](#1) 
- [Navigation Grafiğine Fragment Ekleyin](#2) 

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

















