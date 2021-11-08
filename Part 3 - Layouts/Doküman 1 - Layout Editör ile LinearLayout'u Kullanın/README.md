# Layout Editör ile LinearLayout'u Kullanın

- [AboutMe Projesini Oluşturun](#1)
- [LinearLayout'u Kullanmak İçin Root Layout'unu Değiştirin](#2)
- [Layout Editörünü Kullanarak bir TextView Ekleyin](#3)
<!-- - [TextView'inize Style Verin](#4)
- [Bir ImageView Ekleyin](#5)
- [Bir ScrollView Ekleyin](#3) -->

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


<!-- ## <a name="4"></a>TextView'inize Style Verin
## <a name="5"></a>Bir ImageView Ekleyin
## <a name="6"></a>Bir ScrollView Ekleyin -->
