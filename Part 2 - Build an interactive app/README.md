# Build an Interactive App

 - [Temel bir Android Projesinin Anatomisi](#1)
    -  [Aktivite ve düzen dosyalarını keşfedin](#a)

Bu Bölümde, Bir Android uygulama projesinin temel anatomisini, uygulamanıza nasıl resim ekleyeceğinizi, uygulamanın geriye dönük uyumluluğunu (Android'in eski sürümleriyle) nasıl etkinleştireceğinizi ve çevrimiçi belgelerde nasıl gezineceğinizi öğreneceksiniz. Yol boyunca, bir düğmeye dokunulduğunda rastgele bir zar atmanıza izin veren bir uygulama olan **DiceRoller** uygulamasını tamamlayacaksınız.


# <a name="1"></a>Temel bir Android Projesinin Anatomisi

Bu aşamada, DiceRoller adlı yeni bir uygulama projesi oluşturacak ve bir düğmeyle temel etkileşimi ekleyeceğiz. Düğmeye her tıklandığında, görüntülenen metnin değeri değişecek. Aşamayı tamamladığınızda göreceğiniz ekran :

![image](https://user-images.githubusercontent.com/70329389/140047711-657773e7-d9af-42ab-9ab0-170582342b76.png)

Hadi başlayalım!

## <a name="a"></a>Aşama 1 : Aktivite ve düzen dosyalarını keşfedin

[bir önceki Part](https://github.com/serkanalc/Android-Kotlin-Fundamentals/tree/main/Part%201%20-%20Build%20Your%20First%20App)'ta, **java** ve **res** dizinleri de dahil olmak üzere bir uygulama projesinin ana bölümlerini öğrendiniz. Bu görevde, uygulamanızı oluşturan en önemli iki dosyaya odaklanacağız: **MainActivity** Kotlin dosyası ve **aktivite_main.xml** layout dosyası.

### MainActivity'yi İnceleyelim

**MainActivity**, bir **Activity** örneğidir. Bir **Activity**, bir Android uygulaması User Interface (UI) çizen ve giriş olaylarını alan temel bir Android sınıfıdır. Uygulamanız başlatıldığında, **AndroidManifest.xml** dosyasında belirtilen etkinliği başlatır.

Birçok programlama dili, programı başlatan bir ana yöntem tanımlar. Android uygulamalarının ana yöntemi yoktur. Bunun yerine, **AndroidManifest.xml** dosyası, kullanıcı uygulamanın başlatıcı simgesine dokunduğunda MainActivity'nin başlatılması gerektiğini belirtir. Bir etkinliği başlatmak için Android işletim sistemi, uygulama ortamını ayarlamak ve MainActivity'yi oluşturmak için bildirimdeki bilgileri kullanır. Ardından MainActivity sırayla bazı kurulumlar yapar.

Her Activity ile ilişkili bir layout dosyası vardır. Faaliyet ve yerleşim düzeni, *layout inflation* olarak bilinen bir süreçle birbirine bağlanır. Activity başladığında, XML layout dosyalarında tanımlanan görünümler bellekteki Kotlin görünüm nesnelerine dönüştürülür (veya "inflated" edilir). Bu gerçekleştiğinde, Activity bu nesneleri ekrana çizebilir ve ayrıca bunları dinamik olarak değiştirebilir.

- Android Studio'da, yeni bir proje oluşturmak için **File > New > New Project**i seçin. Boş aktiviteyi kullanın ve İleri'ye tıklayın.
- DiceRoller projesini oluşturun ve proje adı proje konumu için diğer tüm değerleri doğrulayın. "AndroidX Artifacts Kullan" seçeneğinin işaretli olduğundan emin olun. Bitir'i tıklayın.

![image](https://user-images.githubusercontent.com/70329389/140049523-7c2022b5-6ad9-46a1-83f1-b4b8fa04b4e3.png)

- **Project>Android** bölmesinde **Java>com.example.android.diceroller** öğesini genişletin. MainActivity'ye çift tıklayın. Kod düzenleyici, MainActivity'deki kodu gösterir.

![image](https://user-images.githubusercontent.com/70329389/140049585-2f8a1c4f-2206-4a52-9952-573fed57d3b8.png)

- Paket adının ve içe aktarma ifadelerinin altında MainActivity için sınıf bildirimi bulunur. MainActivity sınıfı, AppCompatActivity'yi genişletir.

```

class MainActivity : AppCompatActivity() { ...

```

> AppCompatActivity, Android'in eski sürümleriyle geriye dönük uyumluluk sağlarken tüm modern Android özelliklerini destekleyen bir Activity alt sınıfıdır. Uygulamanızı mümkün olan en fazla sayıda cihaza ve kullanıcıya sunmak için her zaman AppCompatActivity'yi kullanmanız önerilir.

- **onCreate()** yöntemine dikkat edin. Activity, nesneyi başlatmak için bir kurucu kullanmaz. Bunun yerine, etkinlik kurulumunun bir parçası olarak bir dizi önceden tanımlanmış yöntem ("lifecycle methods" olarak adlandırılır) çağrılır. Bu yaşam döngüsü yöntemlerinden biri, kendi uygulamanızda her zaman geçersiz kıldığınız **onCreate()** yöntemidir. Daha sonraki bir Part'ta lifecycle methods hakkında daha fazla bilgi edineceksiniz.

onCreate()'de, Activity ile hangi düzenin ilişkilendirileceğini belirlersiniz ve layoutu inflate ederbilirsiniz. setContentView() yöntemi her ikisini de yapar.

```

override fun onCreate(savedInstanceState: Bundle?) {
   super.onCreate(savedInstanceState)
   setContentView(R.layout.activity_main)
}

```

setContentView() yöntemi, aslında bir integer referansı olan **R.layout.activity_main'i** kullanarak layouta başvurur. Uygulamanızı oluşturduğunuzda **R** sınıfı oluşturulur. **R** sınıfı, res dizininin içeriği de dahil olmak üzere uygulamanın tüm varlıklarını içerir.

Bu durumda, **R.layout.activity_main**, oluşturulan **R** sınıfına, layout klasörüne ve Activity_main.xml layout dosyasına atıfta bulunur. (Kaynaklar dosya uzantılarını içermez.) R sınıfındaki benzer referansları kullanarak uygulamanın birçok kaynağına (görüntüler, dizeler ve düzen dosyasındaki öğeler dahil) başvuracaksınız.

### Uygulama düzeni dosyasını inceleyin ve keşfedin

Uygulamanızdaki tüm activity'lerin , uygulamanın **res/layout** dizininde ilişkili bir layout dosyası vardır. Layout dosyası, bir etkinliğin gerçekte nasıl göründüğünü ifade eden bir XML dosyasıdır. Bir layout dosyası, görünümleri tanımlayarak ve görünümlerin ekranda nerede görüneceğini tanımlayarak bunu yapar.

View'lar, View sınıfını genişleten metin, resimler ve düğmeler gibi şeylerdir. **TextView**, **Button**, **ImageView** ve **CheckBox** dahil olmak üzere birçok view türü vardır.

Bu aşamada, uygulama düzeni dosyasını inceleyecek ve değiştireceksiniz.

- **Project>Android** bölmesinde, **res>layout** öğesini genişletin ve **Activity_main.xml** öğesine çift tıklayın. Layout tasarım düzenleyicisi açılır. Android Studio, uygulamanızın düzenini görsel bir şekilde oluşturmanıza ve layout tasarımını önizlemenize olanak tanıyan bu düzenleyiciyi içerir. Daha sonraki bir Part'ta tasarım düzenleyici hakkında daha fazla bilgi edineceksiniz.
- Layout dosyasını XML olarak görüntülemek için pencerenin altındaki **Text** sekmesine tıklayın.

![image](https://user-images.githubusercontent.com/70329389/140053874-4dfe2573-6751-4eef-b255-172c29551cc4.png)

- Layout editörde mevcut tüm XML kodunu silin. Android Studio design editörüyle çalışıyorsanız, yeni bir projeyle elde ettiğiniz varsayılan layout iyi bir başlangıç noktasıdır. Bu derste, sıfırdan yeni bir layout oluşturmak için temeldeki XML ile çalışacaksınız.
- Bu kodu kopyalayıp düzene yapıştırın:

```

<?xml version="1.0" encoding="utf-8"?>

<LinearLayout   
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    tools:context=".MainActivity" >

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Hello World!" />

</LinearLayout>

```

Şimdi kodu inceleyelim:

1. Layoutun üst düzey veya ana öğesi bir <LinearLayout> öğesidir. LinearLayout görünümü bir **ViewGroup'tur**. View groups, diğer görünümleri tutan ve görünümlerin ekrandaki konumlarını belirlemeye yardımcı olan kaplardır.

2. Layouta eklediğiniz tüm view ve view group, en üstteki XML öğesi bu hiyerarşinin kökü olacak şekilde bir görünüm hiyerarşisinde düzenlenir. ana görünüm, diğer viewları ve view grupları içerebilir ve içerilen view grupları, diğer view ve view grupları içerebilir. Uygulamanız XML layout dosyanızdaki görünüm hiyerarşisini çalıştırdığında, düzen  inflated edildiğinde nesnelerin hiyerarşisi haline gelir. Bu durumda ana view grup, alt viewlarını birbiri ardına ( vertically veya horizontally) linear olarak düzenleyen linearl bir layoutdur.
  
3.





