# Material Design

- [Floating Action Button (FAB) Ekleyin](#a)
- [Material Design Dünyasında Stil Kullanın](#b)
- [Toolbar Temasını Değiştirin](#c)
- [Dimension'ları Kullanın](#d)
- [Renkleri Kullanın](#e)


GDG-finder başlangıç uygulaması, bu kursta şimdiye kadar öğrendiğiniz her şeyin üzerine inşa edilmiştir.

Uygulama, üç ekran yerleştirmek için ConstraintLayout'u kullanır. Ekranlardan ikisi, Android'de renkleri ve metni keşfetmek için kullanacağınız layout dosyalarıdır.

Üçüncü ekran bir GDG bulucudur. GDG'ler veya Google Geliştirici Grupları, Android dahil olmak üzere Google teknolojilerine odaklanan geliştirici topluluklarıdır. Dünyanın dört bir yanındaki GDG'ler buluşmalara, konferanslara, Study Jam'lere ve diğer etkinliklere ev sahipliği yapıyor.

Bu uygulamayı geliştirirken, GDG'lerin gerçek listesi üzerinde çalışıyorsunuz. Bulucu ekranı, GDG'leri mesafeye göre sıralamak için cihazın konumunu kullanır.

Şanslıysanız ve bölgenizde bir GDG varsa, web sitesine göz atabilir ve etkinliklerine kaydolabilirsiniz! GDG etkinlikleri, diğer Android geliştiricileriyle tanışmak ve bu kursa uymayan sektördeki en iyi uygulamaları öğrenmek için harika bir yoldur.

Aşağıdaki ekran görüntüleri, uygulamanızın bu codelab'in başından sonuna kadar nasıl değişeceğini gösterir.

![Ekran Resmi 2022-01-14 10 02 12](https://user-images.githubusercontent.com/70329389/149465415-0aa06b39-e12c-48ed-b642-e34e1394e700.png)


## <a name="a"></a>Aşama 1 : Floating Action Button (FAB) Ekleyin

Bu görevde, GDG Finder uygulamasının ana ekranına kayan bir eylem düğmesi (FAB) eklersiniz.

FAB, kullanıcının ekranda yapması gereken ana şey olan birincil eylemi temsil eden büyük bir yuvarlak düğmedir. FAB, aşağıdaki soldaki ekran görüntüsünde gösterildiği gibi diğer tüm içeriğin üzerinde yüzer. Kullanıcı FAB'ye dokunduğunda, sağda gösterildiği gibi bir GDG listesine götürülür.

![Ekran Resmi 2022-01-14 10 06 01](https://user-images.githubusercontent.com/70329389/149465837-80d17f0c-3af9-4bdf-8ebe-08e87ce646a1.png)

#### Adım 1 : Home Fragment Layout'a bir FAB ekleyin

1. Bu codelab için başlangıç uygulaması olan [GDGFinderStyles](https://github.com/google-developer-training/android-kotlin-fundamentals-apps/tree/master/GDGFinderStyles) uygulamasını indirin ve çalıştırın. Önceki codelab'i yaptıysanız, o codelab'in son kodundan devam edebilirsiniz.
2. **build.gradle(Module: app)** dosyasında, Material kitaplığının dahil edildiğini doğrulayın. Material Tasarımı bileşenlerini kullanmak için bu kitaplığı eklemeniz gerekir. En son kitaplık sürümünü kullandığınızdan emin olun.

```
implementation 'com.google.android.material:material:1.2.0'
```

3. **res/layout/home_fragment.xml** dosyasını açın ve **Code** sekmesine geçin.

Şu anda, ana ekran layout'u, bir alt öğe olarak **ConstraintLayout** ile tek bir **ScrollView** kullanır. ConstraintLayout'a bir FAB eklediyseniz, FAB, ConstraintLayout'un içinde olur, tüm içeriğin üzerinde kaymaz ve ConstraintLayout'un geri kalanıyla birlikte kayar. FAB'yi mevcut layout'unuz üzerinde kaydırmanın bir yoluna ihtiyacınız var.

**CoordinatorLayout**, görünümleri üst üste yığmanıza izin veren bir görünüm grubudur. CoordinatorLayout herhangi bir süslü layout yeteneğine sahip olmasa da, bu uygulama için yeterlidir. ScrollView tam ekranı almalı ve FAB ekranın alt kenarına yakın yüzmelidir.

4. **home_fragment.xml** dosyasında, **ScrollView** çevresine bir **CoordinatorLayout** ekleyin.

```
<androidx.coordinatorlayout.widget.CoordinatorLayout
       android:layout_height="match_parent"
       android:layout_width="match_parent">
...

</androidx.coordinatorlayout.widget.CoordinatorLayout>
```
5. `<ScrollView>` öğesini `<androidx.core.widget.NestedScrollView>` ile değiştirin. Coordinator layout kaydırmayı bilir ve kaydırmanın doğru çalışması için kaydırma ile başka bir görünüm içinde **NestedScrollView** kullanmanız gerekir.

```
androidx.core.widget.NestedScrollView
```

6. **CoordinatorLayout**'un içine ve altına, **NestedScrollView**'ın altına bir **FloatingActionButton** ekleyin.

```
<com.google.android.material.floatingactionbutton.FloatingActionButton
       android:layout_width="wrap_content"
       android:layout_height="wrap_content"/>
```

7. Uygulamanızı çalıştırın. Sol üst köşede renkli bir nokta görüyorsunuz

![image](https://user-images.githubusercontent.com/70329389/149467533-4c3ba503-925f-4bcb-adf2-83897014c174.png)

8. Düğmeye dokunun. Düğmenin görsel olarak yanıt verdiğine dikkat edin.
9. Sayfayı kaydırın. Düğmenin yerinde kaldığına dikkat edin.

#### Adım 2 : FAB Button'unu Stillendirin

Bu adımda, FAB'i sağ alt köşeye taşır ve FAB'nin eylemini gösteren bir görüntü eklersiniz.

Yine **home_fragment.xml**'de, **layout_gravity** niteliğini FAB'a ekleyin ve düğmeyi ekranın altına ve sonuna taşıyın. layout_gravity niteliği, bir görünüme ekranın üst, alt, başlangıç, bitiş veya ortasına yerleştirilmesini söyler. Dikey bir çubuk kullanarak konumları birleştirebilirsiniz.

```
 android:layout_gravity="bottom|end"
```

1. Düğmenin taşındığını Önizleme bölmesinde kontrol edin.
2. FAB'e ekranın kenarından kaydırmak için 16dp'lik bir **layout_margin** ekleyin.

```
android:layout_margin="16dp"
```
3. FAB için görüntü olarak sağlanan **ic_gdg** simgesini kullanın. Aşağıdaki kodu ekledikten sonra FAB'nin içinde bir şerit göreceksiniz.

```
app:srcCompat="@drawable/ic_gdg"
```

4. Uygulamayı çalıştırın ve aşağıdaki ekran görüntüsü gibi görünmelidir.

![image](https://user-images.githubusercontent.com/70329389/149544488-89a136ee-235c-4601-8b23-f29a0df27543.png)

#### Adım 3 : FAB'a Bir Click Listener Ekleyin

Bu adımda, kullanıcıyı bir GDG listesine götüren FAB'ye bir click listener eklersiniz. Önceki kod laboratuvarlarına click listener eklediniz, bu nedenle talimatlar kısa ve öz.

1. **home_fragment.xml**'de, `<data>` etiketinde, sağlanan **HomeViewModel** için bir **viewModel** değişkeni tanımlayın.

```
<variable
   name="viewModel"
   type="com.example.android.gdgfinder.home.HomeViewModel"/>
```

2. FAB'a onClick listener ekleyin ve onu **nFabClicked()** olarak adlandırın.

```
android:onClick="@{() -> viewModel.onFabClicked()}"
```

3. Home paketinde, sağlanan HomeViewModel sınıfını açın ve aşağıda da gösterilen navigasyon live data'larına ve function'larına bakın. FAB tıklandığında, onFabClicked()click işleyicisinin çağrıldığına ve uygulamanın gezinmeyi tetiklediğine dikkat edin.

```
private val _navigateToSearch = MutableLiveData<Boolean>()
val navigateToSearch: LiveData<Boolean>
   get() = _navigateToSearch

fun onFabClicked() {
   _navigateToSearch.value = true
}

fun onNavigatedToSearch() {
   _navigateToSearch.value = false
}
```

4. Home paketinde **HomeFragment** sınıfını açın. onCreateView() öğesinin HomeViewModel'i oluşturduğuna ve onu viewModel'e atadığına dikkat edin.
5. onCreateView() içindeki bağlamaya viewModel'i ekleyin.

```
binding.viewModel = viewModel
```
6. Hatayı ortadan kaldırmak için, binding nesnesini güncellemek üzere projenizi temizleyin ve yeniden oluşturun.
7. Ayrıca onCreateView() içinde, GDG'ler listesine giden bir observer ekleyin. İşte kod:

```
viewModel.navigateToSearch.observe(viewLifecycleOwner,
            Observer<Boolean> { navigate ->
                if(navigate) {
                    val navController = findNavController()
                    navController.navigate(R.id.action_homeFragment_to_gdgListFragment)
                    viewModel.onNavigatedToSearch()
               }
             })
```

8. androidx'ten gerekli import'ları yapın. Bu findNavController ve Observer'ı içe aktarın:

```
import androidx.navigation.fragment.findNavController
import androidx.lifecycle.Observer
```
9. Uygulamanızı çalıştırın.
10. FAB'a dokunun ve sizi GDG listesine götürür. Uygulamayı fiziksel bir cihazda çalıştırıyorsanız, konum izni istenir. Uygulamayı bir emülatörü çalıştırıyorsanız, aşağıdaki mesajı içeren boş bir sayfa görebilirsiniz:

![image](https://user-images.githubusercontent.com/70329389/149546880-bc7aeda3-6bcf-42aa-b4c6-8c35f27fafb3.png)

Bu mesajı emülatörde çalışıyorsa, internete bağlı olduğunuzdan ve konum ayarlarının açık olduğundan emin olun. Ardından konum hizmetlerini etkinleştirmek için Haritalar uygulamasını açın. Ayrıca emülatörünüzü yeniden başlatmanız gerekebilir.

![image](https://user-images.githubusercontent.com/70329389/149547004-fe17bb0f-355a-422f-bf7d-b1ae36094975.png)

## <a name="b"></a>Aşama 2 : Material Design Dünyasında Stil Kullanın

Materyal Tasarımı bileşenlerinden en iyi şekilde yararlanmak için tema attributelerini kullanın. Tema attributelerini, uygulamanın ana rengi gibi farklı stil bilgileri türlerine işaret eden değişkenlerdir. **MaterialComponents** teması için tema attributelerini belirterek uygulama stilinizi basitleştirebilirsiniz. Renkler veya yazı tipleri için ayarladığınız değerler tüm widget'lar için geçerlidir, böylece tutarlı bir tasarıma ve markaya sahip olabilirsiniz.

#### Adım 1 : Material Tema Attribute'lerini Kullanın

Bu adımda, görünümlerinize stil vermek için Materyal Tasarımı tema niteliklerini kullanmak için ana ekrandaki title başlıklarının stilini değiştirirsiniz. Bu, uygulamanızın stilini özelleştirirken Malzeme stil kılavuzunu izlemenize yardımcı olur.

1. Tipografi teması için [Material web sayfasını](https://material.io/develop/android/theming/typography/) açın Sayfa, Malzeme temalarıyla kullanılabilen tüm stilleri gösterir.
2. Sayfada textAppearanceHeadline5 (Normal 24sp) ve textAppearanceHeadline6 (Normal 20sp) bulmak için arama yapın veya kaydırın. Bu iki özellik, uygulamanız için iyi eşleşmelerdir.
3. `home_fragment.xml` içinde, TextView başlığının geçerli stilini (`android:textAppearance="@style/TextAppearance.Title"`) `style="?attr/textAppearanceHeadline5"` ile değiştirin. `?attr` sözdizimi, bir tema özniteliğini aramanın ve geçerli temada tanımlandığı gibi Başlık 5'in değerini uygulamanın bir yoludur.

```
<TextView
       android:id="@+id/title"
       style="?attr/textAppearanceHeadline5"
```

4. Değişikliklerinizi önizleyin. Stil uygulandığında başlığın yazı tipinin değiştiğine dikkat edin. Bunun nedeni, aşağıdaki stil öncelikli piramit diyagramında gösterildiği gibi, görünümde ayarlanan stilin tema tarafından ayarlanan stili geçersiz kılmasıdır.

![image](https://user-images.githubusercontent.com/70329389/149553083-52b02654-f1a7-4dc1-b9e9-79a3068425a4.png)

Piramit diyagramında `TextAppearance` temanın altındadır. `TextAppearance`, herhangi bir görünümde metin stili uygulayan bir niteliktir. Bir stille aynı değildir ve yalnızca metnin nasıl görüntüleneceğini tanımlamanıza izin verir. Materyal Tasarımı bileşenlerindeki tüm metin stilleri aynı zamanda `textAppearanc`e olarak da kullanılabilir; bu şekilde, tanımladığınız tüm tema öznitelikleri öncelikli olur.

5. TextView başlığında, az önce eklediğiniz stili bir textAppearance ile değiştirin.
6. Değişikliklerinizi önizleyin veya farkı görmek için uygulamayı çalıştırın. Karşılaştırma için, aşağıdaki ekran görüntüleri, Başlığa Material stili uygulandığında ve Başlık stilini geçersiz kıldığında farkları gösterir.

| MATERIAL STYLE: | TEXT APPEARANCE |
|:---------------:|:---------------:|
|style="?attr/textAppearanceHeadline5"|android:textAppearance="?attr/textAppearanceHeadline5"|
|![image](https://user-images.githubusercontent.com/70329389/149553985-ba327e0d-46b1-4813-83fd-2238519d5854.png)|![image](https://user-images.githubusercontent.com/70329389/149554070-4233bf58-6840-4786-91fc-d0df97dea0d6.png)|

#### Adım 2 : Material Temasındaki Stili Değiştirin

`textAppearanceHeadline6`, `subtitle` için iyi bir Material seçimi olacaktır, ancak varsayılan boyutu, uygulamanın `Title` stilinde tanımlandığı gibi 18sp değil, 20sp'dir. Her `subtitle` görünümünde boyutu geçersiz kılmak yerine, Material temasını değiştirebilir ve varsayılan stilini geçersiz kılabilirsiniz.

1. style.xml'i açın.

2. `Title` ve `subtitle` stillerini silin. Başlık yerine zaten `textAppearanceHeadline5` kullanıyorsunuz ve `subtitle` ihtiyacını ortadan kaldıracaksınız.

3. 18sp bir `textSize` ile bir `CustomHeadline6` stili tanımlayın. Açıkça geçersiz kılmadığınız her şeyi devralması için ona bir `TextAppearance.MaterialComponents.Headline6` parent'i verin.

```
<style name="TextAppearance.CustomHeadline6" parent="TextAppearance.MaterialComponents.Headline6">
   <item name="android:textSize">18sp</item>
</style>
```

4. Bu stilin içinde, `textAppearanceHeadline6`'yı yeni eklediğiniz özel stille şekillendiren bir öğe tanımlayarak temanın varsayılan `textAppearanceHeadline6`'sını geçersiz kılın.

```
<item name="textAppearanceHeadline6">@style/TextAppearance.CustomHeadline6</item>
```

5. `home_fragment.xml` dosyasında, subtitle görünümüne `textAppearanceHeadline6`'yı uygulayın ve kodunuzu yeniden biçimlendirin (**Code > Reformat code**).

```
 android:textAppearance="?attr/textAppearanceHeadline6"
```

6. Uygulamayı çalıştırın. Yazı tipi rengindeki farka dikkat edin, ki bu incedir, ancak ekranın ne kadar okunabilir olduğu konusunda büyük bir fark yaratır.

| Orjinal | Sonrası |
|:---------------:|:---------------:|
|![image](https://user-images.githubusercontent.com/70329389/149556509-13dc0318-7ede-4025-96d3-17d3d36a9e9c.png)|![image](https://user-images.githubusercontent.com/70329389/149556541-26654b9e-8b15-4af1-820d-f7b20f96146a.png)|

## <a name="c"></a>Aşama 3 : Toolbar Temasını Değiştirin

Bazen ekranınızın bazı bölümlerini farklı bir temayla değiştirmek isteyebilirsiniz, ancak tamamını değil. Örneğin, araç çubuğunun dark Material components temasını kullanmasını sağlayabilirsiniz. Bunu tema overlay'leri kullanarak yaparsınız.

Tüm uygulama adına genel temayı ayarlamak için bir Tema kullanılır. Bir `ThemeOverlay`, özellikle araç çubuğu olmak üzere belirli görünümler için bu temayı geçersiz kılmak (veya "overlay") için kullanılır.

Tema kaplamaları, bir pastanın üzerine krema gibi mevcut bir temayı kaplamak için tasarlanmış "thin theme'lardır". Uygulamanızın bir alt bölümünü değiştirmek istediğinizde kullanışlıdırlar, örneğin, araç çubuğunu dark olacak şekilde değiştirin, ancak ekranın geri kalanı için açık bir tema kullanmaya devam edin. Bir görünüme tema kaplaması uygularsınız ve kaplama o görünüme ve tüm alt öğelerine uygulanır.

Bunu, istediğiniz temayı, onu kullanmak istediğiniz görünüm hiyerarşisinin kök görünümüne uygulayarak yaparsınız. Bu henüz hiçbir şeyi değiştirmiyor! Hiyerarşide bir görünümün, kaplama temasında tanımlanan belirli bir attribute kullanmasını istediğinizde, görünümdeki attribute'u özel olarak ayarlar ve değeri `?attr/valuename` olarak ayarlarsınız.

#### Adım 1 : Tema Overlay'lerini Kullanın

MaterialComponents temasının, açık renkli bir ekranda karanlık bir araç çubuğu seçeneği yoktur. Bu adımda, yalnızca araç çubuğunun temasını değiştirirsiniz. MaterialComponents'ta bulunan Dark temasını araç çubuğuna kaplama olarak uygulayarak araç çubuğunu koyu hale getirirsiniz.

1. `Activity_main.xml` dosyasını açın ve `Toolbar`'ın nerede tanımlandığını bulun (`androidx.appcompat.widget.Toolbar`). `Toolbar`, Material Design'ın bir parçasıdır ve etkinliklerin varsayılan olarak kullandığı uygulama çubuğundan daha fazla özelleştirmeye olanak tanır.
2. Araç çubuğunu ve alt öğelerinden herhangi birini karanlık temayla değiştirmek için `Toolbar`'ın temasını `Dark.ActionBar` temasına ayarlayarak başlayın. Bunu `ImageView`'de değil, `Toolbar`'da yaptığınızdan emin olun.

```
<androidx.appcompat.widget.Toolbar
    android:theme="@style/ThemeOverlay.MaterialComponents.Dark.ActionBar"
```

3. Araç Çubuğunun arka planını `colorPrimaryDark` olarak değiştirin.

```
android:background="?attr/colorPrimaryDark"
```

![image](https://user-images.githubusercontent.com/70329389/149595925-70726017-7b0c-4fc1-b18b-2b6d583d7e1c.png)

Şeffaf bir arka plan üzerinde hem renkli ok hem de gri "GDG Finder" metnini içeren başlıktaki (drawable/logo.png) resme dikkat edin. Değiştirilen arka plan rengi ile metin artık çok fazla öne çıkmıyor. Yeni bir görüntü oluşturabilir veya yeni bir görüntü oluşturmadan kontrastı artırmak için ImageView üzerinde bir renk tonu ayarlayabilirsiniz. Bu, tüm ImageView'in belirtilen renge "tinted olmasına" neden olur. colorOnPrimary attribute'u, birincil rengin üzerine çizildiğinde metin veya ikonografi için erişilebilirlik yönergelerini karşılayan bir renktir.

4. Araç Çubuğunun içindeki ImageView'da renk tonunu colorOnPrimary olarak ayarlayın. drawable, hem görüntüyü hem de GDG Finder metnini içerdiğinden, ikisi de hafif olacaktır.

```
android:tint="?attr/colorOnPrimary"
```

5. Uygulamayı çalıştırın ve temadaki karanlık başlığa dikkat edin. Renk tonu, simgeyi ve "GDG Finder" metnini içeren hafif logo görüntüsünden sorumludur.

![image](https://user-images.githubusercontent.com/70329389/149596173-b8dedfb2-fef3-41ff-bded-803a9f2dac85.png)

## <a name="d"></a>Aşama 4 : Dimension'ları Kullanın

Profesyonel görünümlü bir uygulama, tutarlı bir görünüme ve hisse sahiptir. Her ekranda aynı renklere ve benzer layout'lara sahiptir. Bu, uygulamanın yalnızca daha iyi görünmesini sağlamakla kalmaz, aynı zamanda kullanıcıların ekranları anlamasını ve ekranlarla etkileşim kurmasını kolaylaştırır.

Dimens, veya dimensions, uygulamanız için yeniden kullanılabilir ölçümler belirtmenize olanak tanır. dp kullanarak kenar boşlukları, yükseklikler veya dolgu gibi şeyleri belirtin. sp kullanarak yazı tipi boyutlarını belirtin.

Bu görevde, ekranın sağ ve sol taraflarına tutarlı bir kenar boşluğu uygulamak için kullanılacak bir `Dimens` tanımlarsınız.

#### 1. Adım: Kodunuzu İnceleyin

1. **home_fragment xml**'yi açın.

2. **Design** sekmesine bakın ve **blueprint**'in görünür olduğundan emin olun.

3. Component Tree'de, **start_guideline** ve **end_guideline**'ı seçin. blueprint'de hafifçe vurgulanmalıdırlar.

![Ekran Resmi 2022-01-15 03 56 09](https://user-images.githubusercontent.com/70329389/149602464-16aa2473-bccf-4dad-ad55-b27a8510ebc7.png)

4. Sırasıyla başlangıç ve bitiş yönergelerinin ekini gösteren soldaki 16 ve sağdaki 26 numaraya dikkat edin.
5. Code sekmesine geçin.
6. **ConstraintLayout**'un alt kısmında iki Yönergenin tanımlandığına dikkat edin. Yönergeler, ekranınızda içeriğinizin kenarlarını tanımlayan dikey veya yatay çizgiler tanımlar. Tam ekran görüntüler dışında her şey bu satırların içine yerleştirilir.

```
<androidx.constraintlayout.widget.Guideline
   android:id="@+id/start_guideline"
   android:layout_width="wrap_content"
   android:layout_height="wrap_content"
   android:orientation="vertical"
   app:layout_constraintGuide_begin="16dp" />

<androidx.constraintlayout.widget.Guideline
   android:id="@+id/end_guideline"
   android:layout_width="wrap_content"
   android:layout_height="wrap_content"
   android:orientation="vertical"
   app:layout_constraintGuide_end="26dp" />
```
`layout_constraintGuide_begin="16dp"`, Material özelliklerine göre doğrudur. Ancak `app:layout_constraintGuide_end="26dp"` ayrıca 16dp olmalıdır. Bunu burada manuel olarak düzeltebilirsin. Ancak, bu kenar boşlukları için bir boyut oluşturmak ve ardından bunları uygulamanız boyunca tutarlı bir şekilde uygulamak daha iyidir.

#### 2. Adım: Bir Dimension Oluşturun
