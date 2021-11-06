# Kendine yardım etmeyi öğren

Bu codelab'de, Kotlin Android geliştiricileri için mevcut olan şablonlar, örnekler, belgeler ve diğer kaynaklar hakkında bilgi edineceksiniz.

Önce bir Android Studio şablonundan basit bir uygulama oluşturur ve uygulamayı değiştirirsiniz. Ardından Android Sunflower örnek uygulamasını indirip keşfedersiniz. Örnek uygulamanın başlatıcı simgesini (ayçiçeği) Android Studio'da bulunan bir küçük resim görüntüsü varlığıyla (gülen yüz) değiştirirsiniz.

- [Proje şablonlarını kullanın](#a)
- [Örnek koddan öğrenin](#b)
- [Dokümanları ve diğer kaynakları keşfedin](#c)



## <a name="a"></a>Proje Şablonlarını Kullanın

Android Studio, yaygın ve önerilen uygulama ve etkinlik tasarımları için şablonlar sağlar.  Built-in templates(Yerleşik şablonlar) size zaman kazandırır ve en iyi tasarım uygulamalarını izlemenize yardımcı olur.

Her şablon bir iskelet activity'si ve kullanıcı arayüzü içerir. Bu kursta Boş Etkinlik şablonunu zaten kullandınız. Temel Etkinlik şablonu daha fazla özelliğe sahiptir ve Android destekli cihazlarda uygulama çubuğunda görünen seçenekler menüsü gibi önerilen uygulama özelliklerini içerir.

### Aşama 1 : Temel Etkinlik mimarisini keşfedin

1. Android Studio'da bir proje oluşturun.

2. Projenizi seçin iletişim kutusunda Temel Etkinlik şablonunu seçin ve **Next**'e tıklayın.

3. **Configure your project** iletişim kutusunda, uygulamaya istediğiniz adı verin. Dil için Kotlin'i seçin ve AndroidX yapılarını kullan onay kutusunu seçin. Bitir'i tıklayın.

4. Uygulamayı oluşturun ve bir emülatörde veya Android destekli bir cihazda çalıştırın.

5. Aşağıdaki şekil ve tablodaki etiketli parçaları tanımlayın. Eşdeğerlerini cihazınızda veya emülatör ekranında bulun. Tabloda açıklanan ilgili Kotlin kodunu ve XML dosyalarını inceleyin.

Kotlin kaynak koduna ve XML dosyalarına aşina olmak, Temel Etkinlik şablonunu kendi ihtiyaçlarınıza göre genişletmenize ve özelleştirmenize yardımcı olacaktır.

### Temel Etkinlik şablonunun mimarisi

![image](https://user-images.githubusercontent.com/70329389/140489247-24f91b85-fc79-4b83-a587-2c776b7e60c8.png)

## 1

**UI description:** Android sisteminin sağladığı ve kontrol ettiği durum çubuğu.

**Code reference:** Şablon kodunda görünmez, ancak aktivitenizden durum çubuğuna erişebilirsiniz. Örneğin, gerekirse [durum çubuğunu gizlemek](https://developer.android.com/training/system-ui/status) için **MainActivity.kt**'ye kod ekleyebilirsiniz.
 
## 2

**UI description:** "Action bar" olarak da adlandırılan "App bar", görsel yapı, standartlaştırılmış görsel öğeler ve gezinme sağlar.  

**Code reference:** Activity_main.xml içinde, **AppBarLayout** öğesinin içinde Araç Çubuğu'nu arayın. Geriye dönük uyumluluk için, şablondaki AppBarLayout, ActionBar ile aynı işlevselliğe sahip bir Araç Çubuğu yerleştirir. Uygulama çubuğunun görünümünü değiştirmek için araç çubuğu niteliklerini değiştirin.

## 3

**UI description:** Uygulama adı başlangıçta paket adınızdan türetilmiştir, ancak bunu istediğiniz herhangi bir şeyle değiştirebilirsiniz.

**Code reference:** **AndroidManifest.xml**'de **android:label="@string/app_name"** ifadesini arayın. **app_name** dizesini **strings.xml** içinde belirtin.

## 4

**UI description:** Seçenekler-menü taşma(overflow) düğmesi, aktivite için menü öğelerini tutar. Taşma düğmesi(overflow), uygulama için **Arama** ve **Ayarlar** gibi genel menü seçeneklerini de içerir. Uygulama menüsü öğeleriniz bu menüye girer.

**Code reference:** MainActivity.kt'de, **theonOptionsItemSelected()** yöntemi, kullanıcı bir menü öğesi seçtiğinde ne olduğunu uygular. Seçenekler-menü öğelerini görmek için **res/menu/menu_main.xml** dosyasını açın. Bu şablonda yalnızca Ayarlar menü öğesi belirtilir.

## 5

**UI description:** CoordinatorLayout ViewGroup, UI öğelerinin etkileşim kurması için mekanizmalar sağlayan bir layoutdur. Uygulamanızın kullanıcı arayüzü, bu ViewGroup'ta bulunan content_main.xml dosyasının içine girer.

**Code reference:** Activity_main.xml'de, include layout talimatını arayın. Bu layoutda view belirtilmemiş; bunun yerine layout, uygulamanın görünümlerinin belirtildiği **content_main** layoutunu içerir. Sistem görünümleri, uygulamanıza özgü görünümlerden ayrı tutulur.

## 6

**UI description:** Şablon, "Hello World"ü görüntülemek için bir TextView kullanır. Bu TextView'i uygulamanızın UI öğeleriyle değiştirirsiniz.

**Code reference:** "Hello World" metin görünümü, **content_main.xml** dosyasındadır. Uygulamanızın tüm UI öğeleri bu dosyada tanımlanmalıdır.

## 7

**UI description:** Floating action button (FAB)

**Code reference:** Activity_main.xml içinde **FloatingActionButton** öğesini arayın. FAB, küçük resim simgesi kullanan bir UI öğesi olarak tanımlanır. MainActivity.kt, FAB için bir onClick() dinleyicisi ayarlayan onCreate() içinde bir stub in içerir.

### Aşama 2 : Şablonun Ürettiği Uygulamayı Özelleştirin

Temel Etkinlik şablonu tarafından oluşturulan uygulamanın görünümünü değiştirin. Örneğin, uygulama çubuğunun rengini durum çubuğuna uyacak şekilde değiştirebilirsiniz. (Bazı cihazlarda durum çubuğu, uygulama çubuğunun kullandığı ana rengin daha koyu bir tonudur.)

1. Uygulama çubuğunun görüntülediği uygulamanın adını değiştirin. Bunu yapmak için **res > values > strings.xml** dosyasındaki **app_name** dize kaynağını aşağıdaki şekilde değiştirin:

```
<string name="app_name">New Application</string>
```

2. **Android:background** niteliğini **"?attr/colorPrimaryDark"** olarak değiştirerek **res > layout > Activity_main.xml** içindeki uygulama çubuğunun (Araç Çubuğu) rengini değiştirin. Bu değer, uygulama çubuğu rengini durum çubuğuyla eşleşen daha koyu bir birincil renge ayarlar:

```
android:background="?attr/colorPrimaryDark"
```

3. Uygulamayı çalıştırın. Uygulamanın yeni adı durum çubuğunda görünür ve uygulama çubuğunun arka plan rengi daha koyudur ve durum çubuğunun rengiyle eşleşir. FAB'yi tıkladığınızda, ekran görüntüsünde 1 olarak gösterilen bir snackbar görünür

![image](https://user-images.githubusercontent.com/70329389/140573764-88ddb0f2-7139-448d-8c6f-2ba46fbff2fc.png)

4. Snackbar metnini değiştirin. Bunu yapmak için **MainActivity'yi** açın ve **onCreate()** içinde düğme için bir **onClick()**  listener ayarlayan stub kodunu arayın. "Replace with your own action"i başka bir şeyle değiştir. Örneğin:

```
fab.setOnClickListener { view ->
   Snackbar.make(view, "This FAB needs an action!", Snackbar.LENGTH_LONG)
       .setAction("Action", null).show()
}
```

5. FAB, uygulamanın vurgu rengini kullanır, bu nedenle FAB'ın rengini değiştirmenin bir yolu vurgu rengini değiştirmektir. Vurgu rengini değiştirmek için **res > values > colors.xml** dosyasını açın ve aşağıda gösterildiği gibi **colorAccent** niteliğini değiştirin. (Renk seçiminde yardım için Materyal Tasarımı renk sistemine bakın.)

```
<color name="colorAccent">#1DE9B6</color>
```

6. Uygulamayı çalıştırın. FAB yeni rengi kullanıyor ve snackbar metni değişti.

![image](https://user-images.githubusercontent.com/70329389/140574211-74e73f7e-fbb6-4836-b276-a6b87df6eb3a.png)

> İpucu: Kaynaklara erişmeye yönelik XML syntax hakkında ayrıntılar için [Accessing your app resources](https://developer.android.com/guide/topics/resources/providing-resources#Accessing)'a bakın

### Aşama 3 : Şablonları Kullanarak Etkinliklerin Nasıl Ekleneceğini Keşfedin

Şimdiye kadar bu kurstaki kod laboratuvarları için, yeni projeler başlatmak için Boş Etkinlik ve Temel Etkinlik şablonlarını kullandınız. Projeniz oluşturulduktan sonra etkinlikler oluştururken etkinlik şablonlarını da kullanabilirsiniz.

1. Bir uygulama projesi oluşturun veya mevcut bir projeyi seçin.
2. **Proje > Android** bölmesinde, **Java** klasörüne sağ tıklayın.
3. **New > Activity > Gallery**'yi seçin.
4. **Activity**'e şablonlarından birini seçerek uygulamaya bir **Activity** ekleyin. Örneğin, Navigation Drawer olan bir Activity eklemek için **Navigation Drawer** Activity'sini seçin.
5. Activity'i layout editörde görüntülemek için Activity'nin layout dosyasına çift tıklayın (örneğin **aktivite_main2.xml**). Activity'nin layout önizlemesi ve layout kodu arasında geçiş yapmak için **Design** sekmesini ve **Text** sekmesini kullanın.
   

## <a name="b"></a>Örnek Koddan Öğrenin

GitHub'daki [Google Samples](https://github.com/googlesamples) depoları, üzerinde çalışabileceğiniz, kopyalayabileceğiniz ve projelerinize dahil edebileceğiniz Kotlin Android kod örnekleri sağlar.

### Aşama 1 : Bir Kotlin Android kod örneğini indirin ve çalıştırın

1. Bir tarayıcıda [github.com/android](https://github.com/android) adresine gidin.
2. Dil için **Kotlin**'i seçin.
3. Yakın zamanda değiştirilmiş bir Kotlin Android örnek uygulaması seçin ve uygulamanın proje kodunu indirin. Bu örnek için, Android Jetpack'in bazı bileşenlerini gösteren [android-sunflower](https://github.com/android/sunflower) uygulaması için zip dosyasını indirin.
4. Android Studio'da android-sunflower-master projesini açın.
5. Android Studio'nun önerdiği güncellemeleri kabul edin, ardından uygulamayı bir editörde veya Android destekli bir cihazda çalıştırın.

![image](https://user-images.githubusercontent.com/70329389/140575945-8674bd3e-b392-4e40-95ca-cc18319758e8.png)

> Not: GitHub'daki Google Örnekler deposunda bulunan örnekler, daha fazla geliştirme için bir başlangıç noktası olarak düşünülmüştür. Bu örneklerde kendi fikirlerinizi tasarlamanızı ve oluşturmanızı öneririz.

### Aşama 2 : Bir Kotlin Android kod örneğini keşfedin

Artık Android Studio'da Android Sunflower örnek uygulamasını açtığınıza göre, uygulama hakkında bilgi edinin ve proje dosyalarını keşfedin.

1. Örnek bir uygulamanın neyi gösterdiği hakkında bilgi için uygulamanın GitHub'daki README dosyasını ziyaret edin. Bu örnek için [Android Sunflower README](https://github.com/android/sunflower#readme)'ye bakın.
2. Android Studio'da, uygulamadaki Kotlin etkinlik dosyalarından birini açın, örneğin **GardenActivity.kt.**

![image](https://user-images.githubusercontent.com/70329389/140576224-430539e5-e722-428d-84f0-ee7baaa73a54.png)

GardenActivity.kt'de aşina olmadığınız bir sınıf, tür veya prosedür bulun ve Android Geliştirici belgelerinde arayın. Örneğin, **setContentView()** yöntemi hakkında daha fazla bilgi edinmek için geliştirici.android.com'da **setContentView()** öğesini bulmak için arama yapın.

### Aşama 3 : Başlatıcı simgesini değiştirin

Bu adımda, Android Sunflower örnek uygulamasının başlatıcı simgesini değiştireceksiniz. Bir clip-art resmi ekler ve onu mevcut Android Sunflower başlatıcı simgesini değiştirmek için kullanırsınız.

**Başlatıcı simgeleri**

Android Studio ile oluşturduğunuz her uygulama, uygulamayı temsil eden varsayılan bir başlatıcı simgesiyle başlar. Başlatıcı simgelerine bazen *app icons* veya *product icons* denir.

Google Play'de bir uygulama yayınlarsanız uygulamanın başlatıcı simgesi, uygulamanın girişinde ve Google Play mağazasındaki arama sonuçlarında görünür.

Android destekli bir cihaza bir uygulama yüklendikten sonra, uygulamanın başlatıcı simgesi cihazın ana ekranında ve cihazın başka bir yerinde görünür. Örneğin, Android Sunflower uygulamasının başlatıcı simgesi, aşağıdaki ekran görüntüsünde 1 olarak gösterilen, cihazın Arama Uygulamaları penceresinde görünür. Aşağıda 2 olarak gösterilen varsayılan başlatıcı simgesi, başlangıçta Android Studio'da oluşturduğunuz tüm uygulama projeleri için kullanılır.

![image](https://user-images.githubusercontent.com/70329389/140576724-c3db424f-1159-4f2e-ae46-ceb1c6cc1638.png)

**Başlatıcı simgesini değiştirme**

Başlatıcı simgesini değiştirme sürecinden geçmek, sizi  Android Studio's image asset özellikleriyle tanıştırır.

Android Studio'da, Android Sunflower uygulamasının başlatıcı simgesini nasıl değiştireceğiniz aşağıda açıklanmıştır:

1. **Project > Android** bölmesinde, **res** klasörüne sağ tıklayın (veya Control+tıklayın). **New > Image Asset**'nı seçin. **Configure Image Asset** sekmesi görünür.

![image](https://user-images.githubusercontent.com/70329389/140577089-15daf723-5b4c-44e0-96d7-3d274d06bba0.png)

2. Icon Type alanında, henüz seçili değilse  **Launcher Icons (Adaptive & Legacy)** öğesini seçin.  **Foreground Layer** sekmesine tıklayın.
3. **Asset Type** için, aşağıdaki ekran görüntüsünde 1 olarak gösterilen **Clip Art**'i seçin.

![image](https://user-images.githubusercontent.com/70329389/140577398-e0099d6d-9282-49b3-80aa-1568b9cad83a.png)

4. Yukarıdaki ekran görüntüsünde 2 olarak gösterilen Clip Art alanındaki robot simgesini tıklayın. Malzeme Tasarımı simge setini gösteren **Select Icon** iletişim kutusu görünür. 
5. Select Icon iletişim kutusuna göz atın veya ada göre bir simge arayın. İyi bir ruh hali önermek için **mood** simgesi gibi bir simge seçin. **OK**'ı tıklayın.

![image](https://user-images.githubusercontent.com/70329389/140577594-ba5ea73f-b410-40b0-87a8-b283bf9a064d.png)

6. **Configure Image Asset** iletişim kutusunda, **Background Layer** Katmanı sekmesine tıklayın. **Asset** Type için **Color**'i seçin. Renk çipini tıklayın ve simge için arka plan katmanı olarak kullanmak üzere bir renk seçin.
7. **Legacy** sekmesine tıklayın ve varsayılan ayarları gözden geçirin. Eski, yuvarlak ve Google Play Store simgeleri oluşturmak istediğinizi onaylayın. **Next** tıkla.
8. Simge dosyalarının nereye eklendiğini ve üzerine yazıldığını gösteren **Confirm Icon Path** sekmesi görüntülenir. **Finish**'i tıklayın.
9. Uygulamayı bir AVD emülatörü veya Android destekli bir cihazda çalıştırın.

Android Studio, farklı ekran yoğunlukları için başlatıcı görüntülerini **mipmap** dizinlerine otomatik olarak ekler. Android Sunflower uygulaması artık başlatma simgesi olarak yeni clip-art simgesini kullanıyor.

![image](https://user-images.githubusercontent.com/70329389/140578310-50b2c49a-1fa5-4858-b9b8-69e52ca7738c.png)

> İpucu: Etkili başlatıcı simgeleri tasarlama hakkında bilgi edinmek için [Material Design Product icons](https://material.io/design/iconography/) kılavuzuna bakın.

Uygulamayı tekrar çalıştırın. Uygulama Ara ekranında yeni başlatıcı simgesinin göründüğünden emin olun.

## <a name="c"></a>Dokümanları ve diğer kaynakları keşfedin

### Aşama 1 : Resmi Android Belgelerini Keşfedin

En kullanışlı Android belge sitelerinden birkaçını keşfedin ve mevcut olanlara aşina olun:

1. [Developer.android.com](https://developer.android.com/)'a gidin. Bu resmi Android geliştirici belgeleri, Google tarafından güncel tutulur.
2. [Developer.android.com/design/](https://developer.android.com/design/) adresine gidin. Bu site, yüksek kaliteli Android uygulamalarının görünümünü ve işlevselliğini tasarlamak için yönergeler sunar.
3. Material Design ile ilgili bir site olan [Material.io](https://material.io/)'ya gidin. Materyal Tasarımı, yalnızca Android uygulamalarının değil, tüm uygulamaların mobil cihazlarda nasıl görünmesi ve çalışması gerektiğini özetleyen kavramsal bir tasarım felsefesidir. Materyal Tasarımı hakkında daha fazla bilgi edinmek için bağlantılarda gezinin. Örneğin, renk kullanımı hakkında bilgi edinmek için **Design** sekmesine tıklayın ve ardından **Color**'i seçin.
4. API bilgilerini, başvuru belgelerini, öğretici metinleri, araç kılavuzlarını ve kod örneklerini bulmak için [developer.android.com/docs/](https://developer.android.com/docs) adresine gidin.
5. Google Play'de bir uygulama yayınlama hakkında bilgi bulmak için [developer.android.com/distribute/](https://developer.android.com/distribute) adresine gidin. Google Play, Android SDK ile geliştirilen uygulamalar için Google'ın dijital dağıtım sistemidir. Kullanıcı tabanınızı büyütmek ve [para kazanmaya başlamak](https://developer.android.com/distribute/best-practices/earn/) için [Google Play Console](https://developer.android.com/distribute/console/index.html)'u kullanın.

### Aşama 2 : Android Ekibinden & Google Arama'dan İçerik Keşfedin

1. Harika bir öğretici ve ipucu kaynağı olan [Android Developers YouTube](https://www.youtube.com/user/androiddevelopers) kanalını keşfedin.
2. Android ekibinin haberler ve ipuçları yayınladığı [resmi Android blogunu](https://blog.google/products/android/) ziyaret edin.
3.Google Search'e bir soru girin ve Google Search çeşitli kaynaklardan alakalı sonuçları toplar. Örneğin, "Hindistan'daki en popüler Android işletim sistemi sürümü nedir?" sorusunu sormak için Google Search'ü kullanın. Google Search'e hata mesajları bile girebilirsiniz.

### Aşama 3 : Stack Overflow'da Arama Yapımı

Stack Overflow, birbirine yardım eden programcılardan oluşan bir topluluktur. Bir sorunla karşılaşırsanız, birisinin zaten bir yanıt göndermiş olma ihtimali yüksektir.

1. [Stack Overflow](https://stackoverflow.com/)'a gidin
2. Arama kutusuna "How do I set up and use ADB over Wi-Fi?" gibi bir soru girin. Stack Overflow'ta kaydolmadan arama yapabilirsiniz, ancak yeni bir soru göndermek veya bir soruyu yanıtlamak istiyorsanız kayıt olmanız gerekir.
3. Arama kutusuna **[android]** yazın. **[ ]** köşeli ayraçlar, Android ile ilgili olarak etiketlenmiş gönderileri aramak istediğinizi belirtir.
4. Aramanızı daha spesifik hale getirmek için etiketleri ve arama terimlerini birleştirebilirsiniz. Bu aramaları deneyin:

- **[android] and [layout]**
- **[android] "hello world"**

> İpucu: Stack Overflow'da arama yapabileceğiniz birçok yol hakkında daha fazla bilgi edinmek için Stack Overflow yardım merkezine bakın.








