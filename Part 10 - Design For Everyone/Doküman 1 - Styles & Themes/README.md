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



