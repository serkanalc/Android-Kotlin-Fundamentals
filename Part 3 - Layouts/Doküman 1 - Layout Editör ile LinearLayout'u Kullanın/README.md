# Layout Editör ile LinearLayout'u Kullanın

- [AboutMe Projesini Oluşturun](#1)
- [LinearLayout'u Kullanmak İçin Root Layout'unu Değiştirin](#2)
- [Layout Editörünü Kullanarak bir TextView Ekleyin](#3)
- [TextView'inize Style Verin](#4)
- [Bir ImageView Ekleyin](#5)
<!-- - [Bir ScrollView Ekleyin](#3) -->

AboutMe uygulamasında kendinizle ilgili ilginç gerçekleri sergileyebilir veya uygulamayı bir arkadaş, aile üyesi veya evcil hayvan için özelleştirebilirsiniz. Uygulama bir ad, bir BİTTİ düğmesi, bir yıldız resmi ve kaydırılabilir bir metin görüntüler.

![image](https://user-images.githubusercontent.com/70329389/140668206-64fa81e6-db8e-4b0a-a7d7-319885aa0d7a.png)

## <a name="1"></a>AboutMe Projesini Oluşturun

Bu görevde, **AboutMe** Android Studio projesini yaratacaksınız.

1. Henüz açık değilse, Android Studio'yu açın.
2. Android Studio'da bir proje zaten açıksa, **File > New > New Project**'yi seçin.

![image](https://user-images.githubusercontent.com/70329389/140668278-891e7757-ea0f-4ec1-8be4-b5c9c014382e.png)

3. Bir proje zaten açık değilse, **Welcome to Android Studio** sekmesinde **+ Start a new Android Studio project**'ı seçin.

![image](https://user-images.githubusercontent.com/70329389/140668318-436854ea-0920-49b7-ae6b-b4e95e5a1900.png)

4. **Create New Project** iletişim kutusundaki **Phone and Tablet** sekmesinde **Empty Activity** şablonunu seçin. **Next**'e tıkla.

![image](https://user-images.githubusercontent.com/70329389/140668869-d2b139f4-eed4-4fbb-b277-49a7f89db0d4.png)


5. Bir sonraki **Create New Project** sekmesinde aşağıdaki parametreleri ayarlayın ve **Finish**'e tıklayın.

| Attribute | Value |
|---|---|
| Application Name | AboutMe |
| Company Name android | **com.android.example.AboutMe** (veya kendi domaininiz) |
| Save location | Varsayılan konumu bırakın veya tercih ettiğiniz dizine değiştirin |
| Language | Kotlin |
| Minimum API level | API 19: Android 4.4 (KitKat) |
| This project will support instant apps | Bu onay kutusunu boş bırakın |
| Use AndroidX artifacts | Bu onay kutusunu seçin |

![image](https://user-images.githubusercontent.com/70329389/140668699-59d63adc-5b91-44fb-b187-52c9d27fb294.png)

Android Studio'nun proje dosyalarını oluşturması biraz zaman alacaktır.

6. Uygulamanızı çalıştırın. Boş ekranda "Hello World" dizesini göreceksiniz

![image](https://user-images.githubusercontent.com/70329389/140668708-601d4709-4282-42a1-877f-c425daf1269a.png)

Boş Etkinlik şablonu(Empty Activity), **Mainactivity.kt** adında tek bir boş etkinlik oluşturur. Şablon ayrıca **activity_main.xml** adlı bir layout dosyası oluşturur. Layout dosyası, root(ana) ViewGroup olarak **ConstraintLayout**'a ve içeriği olarak tek bir TextView'a sahiptir.







## <a name="2"></a>LinearLayout'u Kullanmak İçin Root Layout'unu Değiştirin

Bu bölümded, oluşturulan root ViewGroup'u bir **LinearLayout** olarak değiştirirsiniz. Ayrıca UI öğelerini vertical olarak da düzenlersiniz.

**View Groups**

Bir **View Group**, diğer view ve view group'ları olan *child*(alt) görünümleri içerebilen bir view'dur. Bir layout oluşturan görünümler, root olarak bir view group olan bir Views hiyerarşisi olarak düzenlenir.

**LinearLayout** view grubunda, UI öğeleri yatay(vertically) veya dikey(horizontally) olarak düzenlenir.

![image](https://user-images.githubusercontent.com/70329389/140708374-1399076b-a04a-4fe7-91ec-8d087bfa30b2.png)

Root düzenini, bir LinearLayout view group kullanacak şekilde değiştirin:

1. **Project > Android** bölmesini seçin. **app/res/layout** klasöründe **activity_main.xml** dosyasını açın.
2. **Text** sekmesini seçin ve root view group'unu **ConstraintLayout**'tan **LinearLayout**'a değiştirin.
3. **TextView**'ı kaldırın. **LinearLayout** öğesinde **android:orientatio**n niteliğini ekleyin ve onu **vertical** olarak ayarlayın.

**Öncesi:**

```
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
   xmlns:app="http://schemas.android.com/apk/res-auto"
   xmlns:tools="http://schemas.android.com/tools"
   android:layout_width="match_parent"
   android:layout_height="match_parent"
   tools:context=".MainActivity">

   <TextView
       android:layout_width="wrap_content"
       android:layout_height="wrap_content"
       android:text="Hello World!"
       app:layout_constraintBottom_toBottomOf="parent"
       app:layout_constraintLeft_toLeftOf="parent"
       app:layout_constraintRight_toRightOf="parent"
       app:layout_constraintTop_toTopOf="parent" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

**Sonrası:**

```
<LinearLayout
       xmlns:android="http://schemas.android.com/apk/res/android"
       xmlns:tools="http://schemas.android.com/tools"
       xmlns:app="http://schemas.android.com/apk/res-auto"
       android:layout_width="match_parent"
       android:layout_height="match_parent"
       android:orientation="vertical"
       tools:context=".MainActivity">

</LinearLayout>
```


## <a name="3"></a>Layout Editörünü Kullanarak bir TextView Ekleyin

Layout Editör, Android Studio içindeki bir görsel tasarım aracıdır. Uygulamanızın layout'unu oluşturmak için XML kodunu elle yazmak yerine, UI öğelerini design(tasarım) editörüne sürüklemek için Layout Editör'ü kullanabilirsiniz.

Layout Editör'ünü görmek için **Design** sekmesine tıklayın. Aşağıdaki ekran görüntüsü Layout Editör bölümlerini göstermektedir.

![image](https://user-images.githubusercontent.com/70329389/140751243-fccfc5b6-d355-4116-8703-1adb62f34002.png)

1. **Design Editör**: Tasarım görünümünde, plan görünümünde veya her ikisinde ekran düzeninizin görsel bir temsilini görüntüler. design editörü, layout editörünün ana parçasıdır.
2. **Toolbar:** Design Editöründe layoutunuzun görünümünü yapılandırmak ve bazı layout özelliklerini değiştirmek için düğmeler sağlar. Örneğin, design editörde layoutunuzun görünümünü değiştirmek için  **Select Design Surface** açılır menüsünü kullanın:

- Layoutunuzun gerçek dünya önizlemesi için **Design**'ı kullanın.
- Her görünüm için yalnızca anahatları görmek için **Blueprint**'i kullanın.
- Her iki ekranı da yan yana görmek için Design + Blueprint'i kullanın.

3. **Palet:** Layoutunuza veya **Component Tree** bölmesine sürükleyebileceğiniz viewlerin ve view grupların bir listesini sağlar.
4. **Attributes:** Seçili olan görünüm veya görünüm grubu için Attribute'ları gösterir.
5. **Component Tree:** Layout hiyerarşisini bir **Component Tree** olarak görüntüler. Component Tree, design editörde başka türlü seçemeyeceğiniz küçük, gizli veya örtüşen görünümleriniz olduğunda kullanışlıdır.

### Aşama 1 : Bir TextView Ekleyin

1. Henüz açık değilse, **res/layout/activity_main.xml** dosyasını açın.
2. **Text** sekmesine geçin ve kodu inceleyin. Kodun root görünüm grubu olarak bir **LinearLayout** vardır. (Görünüm grupları, diğer görünümleri içeren görünümlerdir.)

**LinearLayout**, varsayılan olarak **vertical** olan gerekli **layout_height**, **layout_width** ve **orientation** özelliklerine sahiptir. 

3. **Design Editör**'ü açmak için Tasarım sekmesine geçin.

> Not: Design sekmesi ve Text sekmesi aynı düzeni yalnızca farklı bir şekilde gösterir. Bir sekmede yaptığınız değişiklikler diğerine yansıtılır.

4. Palet bölmesinden bir text view'ı design editöre sürükleyin.

![image](https://user-images.githubusercontent.com/70329389/140754418-21bea2fa-03f5-4adc-a05c-5a6dce1af24c.png)

5. **Component Tree** bölmesine dikkat edin. Yeni metin görünümü, **LinearLayout** olan üst görünüm grubunun bir alt öğesi olarak yerleştirilir.

![image](https://user-images.githubusercontent.com/70329389/140756122-dea737c3-1863-4ce3-950a-99b75d2861f7.png)

6. Henüz açık değilse, Attributes bölmesini açın. (Bölmeyi açmak için design editörde yeni eklenen TextView'e çift tıklayın.) 
7. Attributes bölmesinde aşağıdaki nitelikleri ayarlayın:

| Attributes | Value |
| --- | --- |
| ID | name_text |
| text | Adınıza ayarlayın. (Metin alanlarından biri, araçlar ad alanı için olduğunu belirtmek için bir İngiliz anahtarı simgesi gösterir. İngiliz anahtarı olmayan, android ad alanı içindir; bu, istediğiniz metin alanıdır.)|
| textAppearance > textSize | 20sp |
| textAppearance > textColor | @android:color/black |
| textAppearance > textAlignment | Center |

2. Adım : Bir String Kaynağı Oluşturun

Bileşen Ağacında, TextView'in yanında Sarı bir uyarı göreceksiniz. Uyarı metnini görmek için aşağıdaki ekran görüntüsünde gösterildiği gibi simgeye tıklayın veya üzerine gelin.

![image](https://user-images.githubusercontent.com/70329389/140757223-a21411d0-15fd-49b2-8511-05a2d398b861.png)

Uyarıyı çözmek için bir String kaynağı oluşturun:

1. Attribute bölmesinde, adınıza ayarladığınız metin özelliğinin yanındaki üç noktayı tıklayın. Kaynak düzenleyici açılır.

![image](https://user-images.githubusercontent.com/70329389/140757375-160f2827-0d39-48fc-96b7-9bf91820e79a.png)

2. **Resources** iletişim kutusunda  **Add new resource > New string Value** Değeri'ni seçin.
3.  **New String Value Resource** sekmesinde,  **Resource name** alanını **name** olarak ayarlayın. Resource value alanını kendi adınıza ayarlayın. Tamam'ı tıklayın. Uyarının kaybolduğuna dikkat edin.

![image](https://user-images.githubusercontent.com/70329389/140757779-d1d66e47-e134-4de8-a4b3-a7652306e88e.png)

4. **res/values/strings.xml** dosyasını açın ve **name** adlı yeni oluşturulan string kaynağını arayın.

```
<string name="name">Aleks Haecky</string>
```

3. Adım : Bir Dimension(Boyut) Kaynağı Oluşturun

Az önce resource editor(kaynak düzenleyiciyi) kullanarak bir resource(kaynak) eklediniz. Ayrıca, yeni kaynaklar oluşturmak için kaynakları XML kodu düzenleyicisinde ayıklayabilirsiniz:

1. **Activity_main.xml** dosyasında **Text** sekmesine geçin.
2. **textSize** satırında (**20sp**) sayıya tıklayın ve **Alt+Enter** (Mac'te Option+Enter) yazın. Açılır menüden **Extract dimension resource**'ı seçin.
3. **Extract resource** iletişim kutusunda, **Resource name** alanına **text_size** yazın. Tamam'ı tıklayın.

![image](https://user-images.githubusercontent.com/70329389/140758975-c492786b-ba74-4a38-9164-422fc44e7d0d.png)

4. Aşağıdaki oluşturulan kodu görmek için **res/values/dimens.xml** dosyasını açın:

```
<dimen name="text_size">20sp</dimen>
```

> Not: dimens.xml dosyası res/values klasörünüzde mevcut değilse, Android Studio onu oluşturur.

5. **MainActivity.kt** dosyasını açın ve **onCreate()** işlevinin sonunda aşağıdaki kodu arayın:

```
setContentView(R.layout.activity_main)
```
**setContentView()** fonsyonu, layout dosyasını Activity ile birleştirir. Belirtilen layout kaynak dosyası **R.layout.activity_main**'dir:

- R, **Resource**'a bir referanstır. Uygulamanızdaki tüm resource'lar için tanımları olan otomatik olarak oluşturulan bir sınıftır.
- **layout.activity_main**, resource'un **aktivite_main** adlı bir düzen olduğunu belirtir.

6. Uygulamanızı çalıştırın. Adınızı içeren bir TextView görüntülenir.

![image](https://user-images.githubusercontent.com/70329389/140760010-75d51813-9d1d-4715-9b85-4fc077bbeadb.png)


## <a name="4"></a>TextView'inize Style Verin

Uygulamanızın ekranına baktığınızda, adınız ekranın üst kısmına doğru itilir, böylece artık dolgu ve kenar boşluğu ekleyebilirsiniz.

### Padding ve Margin Farkı

Padding, bir görünümün veya öğenin sınırları içindeki boşluktur. Aşağıdaki şekilde gösterildiği gibi, görünümün kenarları ile görünümün içeriği arasındaki boşluktur.

![image](https://user-images.githubusercontent.com/70329389/140827558-e95e4fcb-5ca7-4417-b3ee-10edea0a5504.png)

Bir görünümün boyutu, Padding'ini içerir. Aşağıdakiler yaygın olarak kullanılan Padding nitelikleridir:

- **android:padding** görünümün dört kenarının tümü için dolguyu belirtir.
- **android:paddingTop** üst kenar için dolguyu belirtir.
- **android:paddingBottom**, alt kenar için dolguyu belirtir.
- **android:paddingStart**, görünümün "başlangıç" kenarı için dolguyu belirtir.
- **Android:paddingEnd**, görünümün "bitiş" kenarı için dolguyu belirtir.
- **Android:paddingLeft**, sol kenar için dolguyu belirtir.
- **android:paddingRight**, sağ kenar için dolguyu belirtir.

Margin, görünümün sınırlarının dışına eklenen boşluktur. Yukarıdaki şekilde gösterildiği gibi, görünümün kenarından ebeveynine kadar olan boşluktur. Aşağıdakiler yaygın olarak kullanılan Margin nitelikleridir:

- **android:layout_margin**, görünümün dört tarafının tümü için bir kenar boşluğu belirtir.
- **android:layout_marginTop**, bu görünümün üst tarafında fazladan boşluk belirtir
- **android:layout_marginBottom**, bu görünümün alt tarafının dışındaki boşluğu belirtir.
- **android:layout_marginStart**, bu görünümün "başlangıç" tarafının dışındaki boşluğu belirtir.
- **android:layout_marginEnd**, bu görünümün son tarafındaki boşluğu belirtir.
- **android:layout_marginLeft**, bu görünümün sol tarafındaki boşluğu belirtir.
- **android:layout_marginRight**, bu görünümün sağ tarafındaki boşluğu belirtir.

### Right/left ve start/end Arasındaki Fark

"Sağ" ve "sol", uygulamanız soldan sağa (LTR) veya sağdan sola (RTL) akış kullanıyor olsun, her zaman ekranın sağ ve sol taraflarını ifade eder. "start" ve "end" her zaman akışın başlangıcını ve bitişini ifade eder:

- Bir LTR akışı için başlangıç = sol ve bitiş = sağ.
- Bir RTL akışı için başlangıç=sağ ve bitiş=sol.

Uygulamanız API düzeyi 17 (Android 4.2) veya üzerini hedefliyorsa:

- "Sol" ve "sağ" yerine "başlat" ve "bitiş" kullanın.
- Örneğin, **Android:layout_marginLeft**, RTL dillerini desteklemek için **Android:layout_marginStart** olmalıdır.

Uygulamanızın Android 4.2'den daha düşük sürümlerle çalışmasını istiyorsanız; diğer bir deyişle, uygulamanın targetSdkVersion veya minSdkVersion değeri 16 veya daha düşükse:

- "Sol" ve "sağ"a ek olarak "başlangıç" ve bitiş" ekleyin.
- Örneğin, hem Android:paddingLeft hem de Android:paddingStart kullanın.

### Aşama 1 : Padding Ekleyin

Name ile name text görünümünün üst kenarı arasına boşluk bırakmak için üst padding ekleyin.

1. **aktivite_main.xml** dosyasını açın ve Design sekmesine geçin.
2. Component Tree veya design editorde, Attributes bölmesini açmak için text viewe tıklayın.
3. Attributes bölmesinin üst kısmında, mevcut tüm attribute'leri görmek için çift ok simgesini tıklayın( attribute arama bölmesinden de arayabilirsiniz) .
4. Padding'i arayın, genişletin ve top attribute yanındaki üç noktayı ... tıklayın. **Resources** sekmesi görünür.
5. Resources iletişim kutusunda **Add new resource > New dimen Value**'yi seçin.
6. New Dimension Value Resource sekmesinde, value bölmesine 8dp  name bölmesine small_padding gelecek şekilde yeni bir **dimen** kaynağı oluşturun.

**dp** kısaltması yoğunluktan bağımsız anlamına gelir. Bir UI öğesinin farklı yoğunluktaki ekranlarda aynı boyutta görünmesini istiyorsanız, ölçü biriminiz olarak dp'yi kullanın. Ancak metin boyutunu belirlerken her zaman **sp** (scalable pixels) kullanın.

7. OK'a tıklayın

### Aşama 2 : Margin Ekleyin

Ad metni görünümünü üst öğenin kenarından uzağa taşımak için bir top margin ekleyin.

- Attributes bölmesinde, **Layout_Margin**'i bulmak için "margin" arayın.
- Layout_Margin'i genişletin ve üst özelliğin yanındaki üç noktayı ... tıklayın.
- **layout_margin** adında yeni bir dimen kaynağı oluşturun ve **16dp** yapın. Tamam'ı tıklayın.

### Font Ekleyin

**name** text görünümünün daha iyi görünmesi için Android Roboto yazı tipini kullanın. Bu yazı tipi, destek kitaplığının bir parçasıdır ve yazı tipini kaynak olarak eklersiniz.

1. **Attributes** bölmesinde "fontFamily" ifadesini arayın
2. **fontFamily** alanında, açılır oka tılayın, listenin en altına gidin ve **More Fonts**'u seçin.
3. **Resources** iletişim kutusunda **rob**'u arayın ve **Roboto**'yu seçin. **Preview** listesinde Regular'ı seçin.
4. **Add font to project** seçilebilir düğmesini seçin.
5. **OK**'ı tıklayın.

![image](https://user-images.githubusercontent.com/70329389/140880020-9e8cb513-e103-40b5-b7f0-44ea1cd66f57.png)

**res** klasöründe artık **roboto.ttf** yazı tipi dosyasını içeren bir yazı tipi klasörü vardır. **@font/roboto niteliği**, **TextView**'unuza eklenir.

### Stili Extract Edin

Stil, bir view'ın görünümünü ve biçimini belirten nitelikler topluluğudur. Bir stil, yazı tipi rengini, yazı tipi boyutunu, arka plan rengini, padding'i, margin'i ve diğer ortak attribute'ları içerebilir.

**name** metni görünümünün biçimlendirmesini bir stile extract edebilir ve stili uygulamanızda istediğiniz sayıda görünüm için yeniden kullanabilirsiniz. Bir stili yeniden kullanmak, birden çok görünümünüz olduğunda uygulamanıza tutarlı bir görünüm verir. Stilleri kullanmak, bu ortak özellikleri tek bir yerde tutmanıza da olanak tanır.

1. **Component Tree**'de **TextView**'e sağ tıklayın ve **Refactor > Extract Style**'ı seçin.
2. **Extract Android Style** sekmesinde **layout_width** onay kutusunu, **layout_height** onay kutusunu ve **textAlignment** onay kutusunu temizleyin. Bu nitelikler genellikle her görünüm için farklıdır, bu nedenle onların stilin bir parçası olmasını istemezsiniz.
3. Stil adı alanına **NameStyle** yazın.
4. **Tamam**'ı tıklayın.

![image](https://user-images.githubusercontent.com/70329389/140885131-e028e26a-41ed-4e79-b933-7a7a200ef695.png)

5. Stil aynı zamanda bir resource'tur, bu nedenle stil bir **style.xml** dosyasındaki **res/values/** klasörüne kaydedilir. **Styles.xml** dosyasını açın ve buna benzer görünecek olan NameStyle stili için oluşturulan kodu inceleyin:

```
<style name="NameStyle">
   <item name="android:layout_marginTop">@dimen/layout_margin</item>
   <item name="android:fontFamily">@font/roboto</item>
   <item name="android:paddingTop">@dimen/small_padding</item>
   <item name="android:textColor">@android:color/black</item>
   <item name="android:textSize">@dimen/text_size</item>
</style>
```

6. **Activity_main.xml** dosyasını açın ve **Text** sekmesine geçin. Oluşturulan stilin metin görünümünde **style="@style/NameStyle"** olarak kullanıldığına dikkat edin.
7. Uygulamayı çalıştırın ve yazı tipindeki değişiklikleri ve **TextView**'inizin etrafındaki dolguyu fark edin.

![image](https://user-images.githubusercontent.com/70329389/140885535-0fa58b91-6a72-40f5-a895-5f7963bf17bf.png)

## <a name="5"></a>Bir ImageView Ekleyin

Gerçek dünyadaki Android uygulamalarının çoğu, görüntüleri göstermek, metni göstermek ve kullanıcıdan metin veya tıklama olayları biçiminde girdi kabul etmek için bir görünüm kombinasyonundan oluşur. Bu görevde, bir resmi görüntülemek için bir görünüm eklersiniz.

**ImageView**, görüntü kaynaklarını görüntülemek için bir view'dür. Örneğin, bir **ImageView** PNG, JPG, GIF veya WebP dosyaları gibi **Bitmap** kaynaklarını görüntüleyebilir veya vektör çizimi gibi bir **Drawable** resource görüntüleyebilir.

Örnek simgeler, avatarlar ve arka planlar gibi Android ile birlikte gelen görüntü kaynakları vardır. Bu kaynaklardan birini uygulamanıza ekleyeceksiniz.

1. Layout dosyasını açın ve **Design** sekmesine geçin, **Palet** bölmesinden bir **ImageView**'i **Component Tree**'de aşağıdaki **name_text**'e sürükleyin. **Resource** iletişim kutusu açılır.
2. Henüz seçili değilse Drawable'ı seçin.
3. Android'i Expand edin, kaydırın ve **btn_star_big_on**'u seçin.
4. Tamam'a tıklayın.

![image](https://user-images.githubusercontent.com/70329389/140890808-a595f298-56b5-47bc-ab0e-e16aef31984f.png)

Yıldız resmi, adınızın altındaki layout'a eklenir. vertical(Dikey) bir LinearLayout'unuz olduğundan eklediğiniz görünümler dikey olarak hizalanır.

![image](https://user-images.githubusercontent.com/70329389/140890994-0e82c029-f32b-47ae-8c99-065354df7953.png)

5. **Code** sekmesine geçin ve oluşturulan ImageView koduna bakın. Genişlik, **match_parent** olarak ayarlanmıştır, bu nedenle görünüm, üst öğesi kadar geniş olacaktır. Yükseklik, **wrap_content** olarak ayarlanmıştır, bu nedenle görünüm, içeriği kadar uzun olacaktır. ImageView, drawable **btn_star_big_on**'a başvurur.

```
<ImageView
   android:id="@+id/imageView"
   android:layout_width="match_parent"
   android:layout_height="wrap_content"
   app:srcCompat="@android:drawable/btn_star_big_on" />
```

6. ImageView'in kimliğini yeniden adlandırmak için, "@+id/imageView" üzerine sağ tıklayın ve Refactor > Rename öğesini seçin.
7. Rename sekmesinde ID kısmını **star_image** olarak ayarlayın.  **Refactor**'yi tıklayın.

![image](https://user-images.githubusercontent.com/70329389/140891917-15b0a4dd-8210-4563-a430-d1ac90bffa38.png)

> İpucu: Refactor > Rename, uygulama projenizde bir özniteliğin veya değişken adının tüm oluşumlarını yeniden adlandırır.

8. Design sekmesinde,  Component Tree'de, star_image yanındaki uyarı simgesine tıklayın. Uyarı, ekran okuyucuların görüntüleri kullanıcıya açıklamak için kullandığı eksik bir contentDescription içindir.
9. Attributes bölmesinde, **contentDescription** niteliğinin yanındaki üç noktayı tıklayın **Resources** iletişim kutusu açılır.
10. **Resources** iletişim kutusunda **Add new resource > New string Value**'ni seçin. **Resource name** alanını **yellow_star** olarak ayarlayın ve **Resource value** alanını **Yellow star** olarak ayarlayın. Tamam'ı tıklayın.
11. Yıldız görüntüsünü addan ayırmak için **yellow_star**'a **16 dp**'lik bir top margin (@dimen/layout_margin olan) eklemek için **Attributes** bölmesini kullanın.
12. Uygulamanızı çalıştırın. Adınız ve yıldız resmi, uygulamanızın kullanıcı arayüzünde görüntülenir.

![image](https://user-images.githubusercontent.com/70329389/140898319-75eca6f8-6cee-4a20-9b0b-b8dc210a153c.png)


## <a name="6"></a>Bir ScrollView Ekleyin

ScrollView, içine yerleştirilmiş görünüm hiyerarşisinin kaydırılmasına izin veren bir görünüm grubudur. Bir kaydırma görünümü, alt öğe olarak yalnızca bir başka görünüm veya görünüm grubu içerebilir. Alt görünüm genellikle bir LinearLayout'tur. LinearLayout içinde başka görünümler ekleyebilirsiniz.

Aşağıdaki görüntü, birkaç başka görünüm içeren bir LinearLayout içeren bir ScrollView örneğini göstermektedir.

![image](https://user-images.githubusercontent.com/70329389/140932263-29070883-f6cf-4fad-b9ea-1ad578a1c2d0.png)

Bu aşamada, kullanıcının kısa bir biyografi görüntüleyen bir metin görünümünü kaydırmasına izin veren bir **ScrollView** ekleyeceksiniz. Yalnızca bir görünümü kaydırılabilir yapıyorsanız, görünümü doğrudan bu görevde yaptığınız şey olan **ScrollView**'a koyabilirsiniz.

![image](https://user-images.githubusercontent.com/70329389/140932439-20ccae25-de85-4812-a467-9422e7e566d6.png)

### Aşama 1 : TextView içeren Bir ScrollView Ekleyin

1. **activity_main.xml** dosyasını açın ve Design sekmesine geçin.
2. Design editöre veya **Component Tree**'ye sürükleyerek bir scroll view layouta sürükleyin. Kaydırma görünümünü yıldız görüntüsünün altına yerleştirin.
3. Oluşturulan kodu incelemek için **Code** sekmesine geçin.

```
// Auto generated code
<ScrollView
   android:layout_width="match_parent"
   android:layout_height="match_parent">

   <LinearLayout
       android:layout_width="match_parent"
       android:layout_height="wrap_content"
       android:orientation="vertical" />
</ScrollView>
```

**ScrollView**'ün width ve height'ı, ana öğeyle eşleşir. **name_text** metin görünümü ve **star_image** görüntü görünümü, içeriklerini görüntülemek için yeterli dikey alanı kullandığında, Android sistemi, ekrandaki kullanılabilir alanın geri kalanını doldurmak için **ScrollView**'ü düzenler.

4. ScrollView'a bir **id** ekleyin ve buna **bio_scroll** adını verin. ScrollView'a bir kimlik eklemek, Android sistemine görünüm için bir tutamaç verir, böylece kullanıcı cihazı döndürdüğünde sistem kaydırma konumunu korur.
5. ScrollView içinde **LinearLayout** kodunu kaldırın, çünkü uygulamanız kaydırılabilir yalnızca bir görünüme sahip olacaktır: bir TextView.
6. Paletten Component Tree'ye bir TextView sürükleyin. TextView'i **bio_scroll**'un alt öğesi olarak **bio_scroll**'un altına koyun.

![image](https://user-images.githubusercontent.com/70329389/140934356-c4c0c535-278b-4b32-b6b4-f9b598ecbf93.png)

7. Yeni metin görünümünün id'sini **bio_text** olarak ayarlayın.
8. Ardından, yeni metin görünümü için bir stil eklersiniz. Attributes bölmesinde, **Resources** sekmesini açmak için style özniteliğinin yanındaki üç noktayı ... tıklayın.
9. **Resources** iletişim kutusunda NameStyle'ı arayın. Listeden **NameStyle**'ı seçin ve Tamam'a tıklayın. Metin görünümü artık önceki bir görevde oluşturduğunuz **NameStyle** stilini kullanır.

![image](https://user-images.githubusercontent.com/70329389/140934873-27d30769-697b-4bf3-9c3d-78a24b0b7c3a.png)

### Aşama 2 : Biyografinizi Yeni TextView'a Ekleyin 

1. **strings.xml** dosyasını açın, **bio** adlı bir string kaynağı oluşturun ve kendiniz veya istediğiniz herhangi bir şey hakkında uzun bir metin girin.

### Dikkat:

- Satır sonunu belirtmek için \n kullanın.
- Bir kesme işareti kullanırsanız, ondan ters eğik çizgi ile kaçmanız gerekir. Örneğin: "Ters eğik çizgiyi unutmamalısınız."
- Kalın metin için <b>...</b> kullanın ve italik metin için <i>...</i> kullanın. Örneğin: "Bu metin <b>kalın</b> ve bu metin <i>italik</i>."

İşte örnek bir biyografi:

```
<string name="bio">Hi, my name is Aleks.
\n\nI love fish.
\n\nThe kind that is alive and swims around in an aquarium or river, or a lake, and definitely the ocean.
\nFun fact is that I have several aquariums and also a river.
\n\nI like eating fish, too. Raw fish. Grilled fish. Smoked fish. Poached fish - not so much.
\nAnd sometimes I even go fishing.
\nAnd even less sometimes, I actually catch something.
\n\nOnce, when I was camping in Canada, and very hungry, I even caught a large salmon with my hands.
\n\nI\'ll be happy to teach you how to make your own aquarium.
\nYou should ask someone else about fishing, though.\n\n</string>
```

2. **bio_text** metin görünümünde, text attributes değerini biyografinizi içeren bio string kaynağına ayarlayın.
3. **bio_text** metninin okunmasını kolaylaştırmak için satırlar arasına boşluk ekleyin. **lineSpacingMultiplier** niteliğini kullanın ve ona 1,2 değerini verin.

![image](https://user-images.githubusercontent.com/70329389/140944890-4cc47490-815c-47cc-9257-22267f29d9de.png)

4. Tasarım düzenleyicide, biyografi metninin ekranın yan kenarlarına kadar nasıl çalıştığına dikkat edin. Bu sorunu çözmek için, root LinearLayout'a left, start, right, ve end padding attributeleri ekleyebilirsiniz. Alt dolgu eklemeniz gerekmez, çünkü en alta kadar uzanan metin, kullanıcıya metnin kaydırılabilir olduğunu bildirir.
5. Kök LinearLayout'a 16dp padding ve bitiş dolgusu ekleyin.
6. **Text** sekmesine geçin, dimension resource'unu extract edin ve layout_padding olarak adlandırın.

![image](https://user-images.githubusercontent.com/70329389/140955299-e2a37910-41d8-4a20-b47e-3e16bc5437e2.png)

> Not: API seviyesi 17'den başlayarak, uygulamanızı Arapça gibi RTL dillerine uyarlamak için dolgu ve kenar boşluğu için "left" ve "right" yerine "start" ve "end" kullanın.

7. Uygulamanızı çalıştırın ve metni kaydırın.

![143a3e38f02c6411](https://user-images.githubusercontent.com/70329389/140955621-8a00ee6f-676c-467c-a416-b0f2dee92dd0.gif)







