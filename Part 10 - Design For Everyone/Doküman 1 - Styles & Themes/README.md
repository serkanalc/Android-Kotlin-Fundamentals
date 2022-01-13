# <a name="1"></a> Styles & Themes


- [Android'in Stil Sistemi](#a)
- [Stil İçin Attribute'leri Kullanın](#b)
- [Temaları ve İndirilebilir Yazı Tiplerini Kullanın](#c)
- [Stilleri Kullan](#d)

GDG-finder başlangıç uygulaması, bu kursta şimdiye kadar öğrendiğiniz her şeyin üzerine inşa edilmiştir.

Uygulama, üç ekran yerleştirmek için `ConstraintLayout'u kullanır`. Ekranlardan ikisi, Android'de renkleri ve metni keşfetmek için kullanacağınız düzen dosyalarıdır.

Üçüncü ekran bir GDG bulucudur. GDG'ler veya Google Geliştirici Grupları, Android dahil olmak üzere Google teknolojilerine odaklanan geliştirici topluluklarıdır. Dünyanın dört bir yanındaki GDG'ler buluşmalara, konferanslara, çalışma sıkışıklığına ve diğer etkinliklere ev sahipliği yapıyor.

Bu uygulamayı geliştirirken, GDG'lerin gerçek listesi üzerinde çalışıyorsunuz. Bulucu ekranı, GDG'leri mesafeye göre sıralamak için cihazın konumunu kullanır.

Şanslıysanız ve bölgenizde bir GDG varsa, web sitesine göz atabilir ve etkinliklerine kaydolabilirsiniz! GDG etkinlikleri, diğer Android geliştiricileriyle tanışmak ve bu kursa uymayan sektördeki en iyi uygulamaları öğrenmek için harika bir yoldur.

Aşağıdaki ekran görüntüleri, uygulamanızın bu codelab'in başından sonuna kadar nasıl değişeceğini gösterir.

![Ekran Resmi 2022-01-12 23 18 08](https://user-images.githubusercontent.com/70329389/149215348-6e2924a7-b14d-4a1f-9348-7b67cf31b654.png)

## <a name="a"></a>Aşama 1 : Android'in Stil Sistemi

Android, uygulamanızdaki tüm view'ların görünümünü kontrol etmenizi sağlayan zengin bir stil sistemi sağlar. Stili etkilemek için temaları, stilleri ve görünüm özelliklerini kullanabilirsiniz. Aşağıda gösterilen şema, her bir şekillendirme yönteminin önceliğini özetlemektedir. Piramit diyagramı, sistem tarafından uygulanan şekillendirme yöntemlerinin aşağıdan yukarıya doğru sırasını gösterir. Örneğin, temada metin boyutunu ayarlarsanız ve ardından görünüm niteliklerinde metin boyutunu farklı ayarlarsanız, görünüm nitelikleri tema stilini geçersiz kılar.

![image](https://user-images.githubusercontent.com/70329389/149216959-b6382115-efcb-410e-b206-d8494852758c.png)

#### View Attributes

- Her görünüm için öznitelikleri açıkça ayarlamak için görünüm özniteliklerini kullanın. (Stiller gibi görünüm nitelikleri yeniden kullanılamaz.)
- Stiller veya temalar aracılığıyla ayarlanabilen her özelliği kullanabilirsiniz.

margins, paddings veya constraints gibi özel veya tek seferlik tasarımlar için kullanın.

#### Styles

- Yazı tipi boyutu veya renkleri gibi yeniden kullanılabilir stil bilgilerinin bir koleksiyonunu oluşturmak için bir stil kullanın.
- Uygulamanız genelinde kullanılan küçük ortak tasarım kümelerini bildirmek için idealdir.

Varsayılan stili geçersiz kılarak birkaç görünüme bir stil uygulayın. Örneğin, stili tutarlı başlıklar veya bir dizi düğme oluşturmak için bir stil kullanın.

#### Default Style

- Bu, Android sistemi tarafından sağlanan varsayılan stildir.

#### Themes

- Tüm uygulamanız için renkleri tanımlamak üzere bir tema kullanın.
- Tüm uygulama için varsayılan yazı tipini ayarlamak için bir tema kullanın.
- Metin görünümleri veya radyo düğmeleri gibi tüm görünümlere uygulayın.
- Tüm uygulama için tutarlı bir şekilde uygulayabileceğiniz özellikleri yapılandırmak için kullanın.

#### TextAppearance

- Yalnızca fontFamily gibi metin öznitelikleriyle stil oluşturmak için.

Android bir görünüme stil verdiğinde, özelleştirebileceğiniz bir temalar, stiller ve nitelikler kombinasyonu uygular. Nitelikler her zaman bir stilde veya temada belirtilen her şeyin üzerine yazar. Ve stiller her zaman bir temada belirtilen her şeyin üzerine yazar.

Aşağıdaki ekran görüntüleri, açık tema (solda) ve koyu tema (sağda) ile GDG bulucu uygulamasını ve ayrıca özel yazı tipi ve başlık boyutlarıyla gösterir. Bu, birkaç şekilde uygulanabilir ve bunlardan bazılarını bu kod laboratuvarında öğreneceksiniz.

![Ekran Resmi 2022-01-13 03 35 32](https://user-images.githubusercontent.com/70329389/149244987-4295eb92-ee95-4fc2-8fe4-f97fd1dc79fc.png)

## <a name="b"></a>Aşama 2 : Stil İçin Attribute'leri Kullanın

Bu görevde, uygulama layout'undaki metnin başlıklarını biçimlendirmek için attribute'leri kullanırsınız.

1. [GDG-finder starter app](https://github.com/google-developer-training/android-kotlin-fundamentals-starter-apps/tree/master/GDGFinder-Starter) indirin ve çalıştırın.
2. Ana ekranda, sayfanın ne hakkında olduğunu ve neyin önemli olduğunu bulmayı zorlaştıran, tek tip biçimlendirilmiş çok sayıda metin olduğuna fark edeceksiniz.
3. `home_fragment.xml` layout dosyasını açın.
4. Layout'un, öğeleri bir ScrollView içinde konumlandırmak için ConstraintLayout kullandığına dikkat edin.
5. Her view için, constraint ve margin layout attribute'lerinin view'da ayarlandığına dikkat edin, çünkü bu öznitelikler her görünüm ve ekran için özelleştirilme eğilimindedir.
6. Başlık metni görünümünde, metnin boyutunu `24sp` olarak değiştirmek için bir textSize attribute'ü ekleyin.

*Hatırlatma olarak, `sp`, hem piksel yoğunluğuna hem de kullanıcının cihaz ayarlarında belirlediği yazı tipi boyutu tercihine göre ölçeklenen ölçekten bağımsız pikselleri ifade eder. Android, metni çizerken metnin ekranda ne kadar büyük olması gerektiğini hesaplar. Metin boyutları için her zaman `sp` kullanın.*

```
<TextView
       android:id="@+id/title"
...

android:textSize="24sp"
```
7. Başlık text görünümünü **textColar**'ını , `#FF555555` RGB değerine düzenleyerek opak griye ayarlayın.

```
<TextView
       android:id="@+id/title"
...

android:textColor="#FF555555"
```

8. Android Studio'da **Preview** sekmesini açmak için **View > Tool Windows > Preview**'i seçin veya layout Editörü'nün sağ kenarındaki dikey **Preview** düğmesini tıklayın. Önizlemede(Preview), başlığın aşağıda gösterildiği gibi gri ve öncekinden daha büyük olduğunu doğrulayın.

![image](https://user-images.githubusercontent.com/70329389/149246368-82d9eb2a-8062-4f5d-86c0-62e64521a420.png)

9. Altyazıyı başlıkla aynı renge sahip olacak şekilde, daha küçük bir yazı tipi olan `18sp` ile biçimlendirin. (Varsayılan alfa `FF`, opaktır. Değiştirmiyorsanız alfa değerini atlayabilirsiniz.) 

```
<TextView
       android:id="@+id/subtitle"
...
android:textSize="18sp"
android:textColor="#555555"
```

![image](https://user-images.githubusercontent.com/70329389/149246621-e8738fa0-e6fb-44e6-b8af-be90fd67c7e0.png)

10. Bu dokümanda amaç, uygulamayı profesyonel görünürken biraz tuhaf olacak şekilde biçimlendirmektir, ancak istediğiniz gibi şekillendirebilirsiniz. **Subtitle** görünümü için aşağıdaki attribute'leri deneyin. Uygulamanızın görünümünün nasıl değiştiğini görmek için **Preview** sekmesini kullanın. Ardından bu özellikleri kaldırın.

```
<TextView
       android:id="@+id/subtitle"
       ...
       android:textAllCaps="true"
       android:textStyle="bold"
       android:background="#ff9999"
```

11. Devam etmeden önce subtitle görünümünden `textAllCaps`, `textStyle`, ve `background` attribute'lerini geri almayı unutmayın.
12. Uygulamanızı çalıştırın, şimdiden daha iyi gözüktüğünü göreceksiniz.

![image](https://user-images.githubusercontent.com/70329389/149247030-53b060a7-9b87-4939-9735-7e1d4cca349f.png)

## <a name="c"></a>Aşama 3 : Temaları ve İndirilebilir Yazı Tiplerini Kullanın

Uygulamanızla yazı tiplerini kullanırken, gerekli yazı tipi dosyalarını APK'nızın bir parçası olarak gönderebilirsiniz. Basit olsa da, bu çözüm genellikle önerilmez, çünkü uygulamanızın indirilmesi ve yüklenmesi daha uzun sürer.

Android, İndirilebilir Yazı Tipleri API'sini kullanarak uygulamaların yazı tiplerini çalışma zamanında indirmesine izin verir. Uygulamanız cihazdaki başka bir uygulamayla aynı yazı tipini kullanıyorsa Android, yazı tipini yalnızca bir kez indirerek cihazın depolama alanından tasarruf sağlar.

Bu görevde, uygulamanızda temayı kullanan her görünümün yazı tipini ayarlamak için indirilebilir yazı tiplerini kullanırsınız.

#### 1. Adım: İndirilebilir bir yazı tipi uygulayın.

1. Tasarım sekmesinde `home_fragment.xml` dosyasını açın.
2. `Component tree` bölmesinde, `title` text görünümünü seçin.
3. Attributes bölmesinde, `fontFamily` niteliğini bulun. Tüm Attributes bölümünde bulabilir veya sadece arayabilirsiniz.
4. Açılır oku tıklayın.
5. `More Fonts`'a gidin ve onu seçin. Bir `Resources` penceresi açılır.

![image](https://user-images.githubusercontent.com/70329389/149301566-5045a20b-8783-4de2-a8f1-a56c44ff24d6.png)

6. `Resources` penceresinde `lobster` arayın.
7. Sonuçlarda, Lobster Two'yu seçin.
8. Sağ tarafta, yazı tipi adının altında **Create downloadable font** radyo düğmesini seçin. Tamam'a tıklayın.
9. Android Manifest dosyasını açın.
10. Bildirimin alt kısmına yakın bir yerde, ad ve kaynak attribute'leri `"preloaded_fonts"` olarak ayarlanmış yeni `<meta-data>` etiketini bulun. Bu etiket, Google Play Hizmetlerine bu uygulamanın indirilen yazı tiplerini kullanmak istediğini bildirir. Uygulamanız çalıştığında ve `Lobster Two` yazı tipini istediğinde, yazı tipi cihazda zaten mevcut değilse, yazı tipi sağlayıcısı yazı tipini internetten indirir.

```
<meta-data android:name="preloaded_fonts" android:resource="@array/preloaded_fonts"/>
```
11. `res/values` klasöründe, bu uygulama için indirilebilir tüm yazı tiplerini listeleyen diziyi tanımlayan `preloaded_fonts.xml` dosyasını bulun.
12. Aynı şekilde `res/fonts/lobster_two.xml` dosyası da yazı tipi hakkında bilgi içerir.
13. `home_fragment.xml` dosyasını açın ve kodda ve önizlemede `Lobster Two` yazı tipinin TextView başlığına ve böylece `title`'a uygulandığına dikkat edin.

![image](https://user-images.githubusercontent.com/70329389/149304029-951fe1f7-83a9-4d0d-b3fa-9782e06ff57d.png)

14. `res/values/styles.xml` dosyasını açın ve proje için oluşturulmuş varsayılan `AppTheme` temasını inceleyin. Şu anda aşağıda gösterildiği gibi görünüyor. Tüm metne yeni `Lobster Two` yazı tipini uygulamak için bu temayı güncellemeniz gerekecek.
15. `<style>` etiketinde, parent niteliğine dikkat edin. Her stil etiketi bir ebeveyn belirtebilir ve parent'lerin tüm niteliklerini devralabilir. Kod, Android kitaplıkları tarafından tanımlanan Temayı belirtir. Düğmelerin nasıl çalıştığından araç çubuklarının nasıl çizileceğine kadar her şeyi belirten [MaterialComponents](https://material.io/develop/android/theming/theming-overview) teması. Temanın makul varsayılanları vardır, böylece yalnızca istediğiniz bölümleri özelleştirebilirsiniz. Uygulama, yukarıdaki ekran görüntüsünde görebileceğiniz gibi, bu temanın `Light` sürümünü eylem çubuğu (NoActionBar) olmadan kullanır.

```
<!-- Base application theme. -->
<style name="AppTheme" parent="Theme.MaterialComponents.Light.NoActionBar">
   <!-- Customize your theme here. -->
   <item name="colorPrimary">@color/colorPrimary</item>
   <item name="colorPrimaryDark">@color/colorPrimaryDark</item>
   <item name="colorAccent">@color/colorAccent</item>
</style>
```
16. `AppTheme` stilinin içinde yazı tipi parent'ini `lobster_two` olarak ayarlayın. Ana tema her ikisini de kullandığından, hem Android:fontFamily hem de `fontFamily`'yi ayarlamanız gerekir. Değişikliklerinizi önizlemek için Tasarım sekmesinde `home_fragment.xml` dosyasını kontrol edebilirsiniz.

```
<style name="AppTheme"  
...    
        <item name="android:fontFamily">@font/lobster_two</item>
        <item name="fontFamily">@font/lobster_two</item>
```

17. Uygulamayı tekrar çalıştırın. Yeni yazı tipi tüm metne uygulanır! navigation drawer açın ve diğer ekranlara geçin, yazı tipinin orada da uygulandığını görürsünüz.

#### 2. Adım: Temayı başlığa uygulayın.

1. `home_fragment.xml` içinde, `lobster_two` yazı tipi attribute'une sahip `title` metni görünümünü bulun. fontFamily attribute'unu silin ve uygulamayı çalıştırın. Tema aynı font family ayarladığından herhangi bir değişiklik olmaz.
2. Başlık metni görünümüne farklı bir fontFamily attribute'u koyun:
`app:fontFamily="serif-monospace"`
Uygulama alanında olduğundan emin olun!

```
<TextView
       android:id="@+id/title"
       ...
       app:fontFamily="serif-monospace"
```

3. Uygulamayı çalıştırın ve görünümün yerel attribute'ünün temayı geçersiz kıldığını görürün.
4. `FontFamily` attribute'ünü **tittle** metin görünümünden kaldırın.

## <a name="d"></a>Aşama 4 : Stilleri Kullan

Temalar, uygulamanıza varsayılan yazı tipi ve ana renkler gibi genel temalar uygulamak için harikadır. Attributes, belirli bir görünümü şekillendirmek ve her ekrana özgü olma eğiliminde olan margins, padding, ve constraints gibi düzen bilgileri eklemek için mükemmeldir.

Style-hierarchy piramidinin ortasında stiller bulunur. Stiller, seçtiğiniz görünümlere uygulayabileceğiniz yeniden kullanılabilir nitelik "gruplarıdır". Bu görevde başlık ve alt başlık için bir stil kullanacaksınız.

#### Aşama 1 : Bir Stil Yaratın

1. res/values/styles.xml dosyasını açın.
2. `<resources>` etiketinin içinde, aşağıda gösterildiği gibi `<style>` etiketini kullanarak yeni bir stil tanımlayın.

```
<style name="TextAppearance.Title" parent="TextAppearance.MaterialComponents.Headline6">
</style>
```
Stil adlarını adlandırırken anlamsal olarak düşünmek önemlidir. Stilin etkilediği özelliklere göre değil, stilin ne için kullanılacağına göre bir stil adı seçin. Örneğin, bu stili **Title** olarak adlandırın, **LargeFontInGrey** gibi bir şey değil. Bu stil, uygulamanızın herhangi bir yerindeki herhangi bir başlık tarafından kullanılacaktır. Kural olarak, **TextAppearance** stillerine **TextAppearance.Name** adı verilir, bu nedenle bu durumda ad **TextAppearance.Title**'dır.

Stilin bir parent'ı vardır, tıpkı bir temanın bir parent'ı olabileceği gibi. Ancak bu sefer, bir temayı genişletmek yerine, stil bir stili, **TextAppearance.MaterialComponents.Headline6**'yı genişletir. Bu stil, **MaterialComponents** teması için varsayılan bir metin stilidir, bu nedenle onu genişleterek sıfırdan başlamak yerine varsayılan stili değiştirirsiniz.

3. Yeni stilin içinde iki öğe tanımlayın. Bir öğede, Boyut metnini `24sp` olarak ayarlayın. Diğer öğede, Renk metnini daha önce kullanılanla aynı koyu griye ayarlayın.

```
 <item name="android:textSize">24sp</item>
 <item name="android:textColor">#555555</item>
```

4. Altyazılar için başka bir stil tanımlayın. **TextAppearance.Subtitle** olarak adlandırın.
5. TextAppearance.Title öğesinden tek fark metin boyutunda olacağından, bu stili TextAppearance.Title öğesinin child'ı yapın.
6. Altyazı stilinin içinde metin boyutunu `18sp` olarak ayarlayın. İşte tamamlanmış stil:

```
<style name="TextAppearance.Subtitle" parent="TextAppearance.Title" >
   <item name="android:textSize">18sp</item>
</style>
```
#### Aşama 2 : Oluşturduğunuz Stili Uygulayın

1. `home_fragment.xml` dosyasında, title metni görünümüne `TextAppearance.Title` stilini ekleyin. **textSize** ve **textColor** attribute'lerini silin.

Temalar, ayarladığınız herhangi bir TextAppearance stilini geçersiz kılar. (Codelab'ın başlangıcındaki piramit diyagramı, stilin uygulanma sırasını gösterir.) Stili TextAppearance olarak uygulamak için textAppearance özelliğini kullanın, böylece Tema'da ayarlanan yazı tipi burada ayarladığınızı geçersiz kılar.

```
<TextView
       android:id="@+id/title"
       android:textAppearance="@style/TextAppearance.Title"
```

2. Ayrıca altyazı metni görünümüne TextAppearance.Subtitle stilini ekleyin ve textSize ve textColor özniteliklerini silin. Bu stili textAppearance olarak da uygulamanız gerekir, böylece temada ayarlanan yazı tipi burada ayarladığınızı geçersiz kılar.

```
<TextView
       android:id="@+id/subtitle"
       android:textAppearance="@style/TextAppearance.Subtitle"
```

> Önemli: Metni işleyen hem temalarınız hem de stilleriniz olduğunda, temadaki metin özelliklerinin stilde ayarlanan ve devralınanları geçersiz kılmasını istiyorsanız, metin özelliklerini textAppearance özniteliği olarak uygulamanız gerekir.

3. Uygulamayı çalıştırın ve metniniz artık tutarlı bir şekilde biçimlendirilir.
