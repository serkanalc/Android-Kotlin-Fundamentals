# Layout Editör ile LinearLayout'u Kullanın

- [AboutMe Projesini Oluşturun](#1)
- [LinearLayout'u Kullanmak İçin Root Layout'unu Değiştirin](#2)
<!-- - [Layout Editörünü Kullanarak bir TextView Ekleyin](#3)
- [TextView'inize Style Verin](#4)
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


<!-- ## <a name="3"></a>Layout Editörünü Kullanarak bir TextView Ekleyin
## <a name="4"></a>TextView'inize Style Verin
## <a name="5"></a>Bir ImageView Ekleyin
## <a name="6"></a>Bir ScrollView Ekleyin -->
