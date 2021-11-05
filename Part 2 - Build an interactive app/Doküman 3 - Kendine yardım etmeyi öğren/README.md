# Kendine yardım etmeyi öğren

Bu codelab'de, Kotlin Android geliştiricileri için mevcut olan şablonlar, örnekler, belgeler ve diğer kaynaklar hakkında bilgi edineceksiniz.

Önce bir Android Studio şablonundan basit bir uygulama oluşturur ve uygulamayı değiştirirsiniz. Ardından Android Sunflower örnek uygulamasını indirip keşfedersiniz. Örnek uygulamanın başlatıcı simgesini (ayçiçeği) Android Studio'da bulunan bir küçük resim görüntüsü varlığıyla (gülen yüz) değiştirirsiniz.

- [Proje şablonlarını kullanın](#a)

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

### Şablonun Ürettiği Uygulamayı Özelleştirin

Temel Etkinlik şablonu tarafından oluşturulan uygulamanın görünümünü değiştirin. Örneğin, uygulama çubuğunun rengini durum çubuğuna uyacak şekilde değiştirebilirsiniz. (Bazı cihazlarda durum çubuğu, uygulama çubuğunun kullandığı ana rengin daha koyu bir tonudur.)

1. Uygulama çubuğunun görüntülediği uygulamanın adını değiştirin. Bunu yapmak için **res > values > strings.xml** dosyasındaki **app_name** dize kaynağını aşağıdaki şekilde değiştirin:

```

<string name="app_name">New Application</string>


```



   

