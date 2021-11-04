
# <a name="1"></a>Temel bir Android Projesinin Anatomisi

- [Aktivite ve düzen dosyalarını keşfedin](#a)
- [Bir düğme ekleyin](#b)

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
  
3. Yeni bir Android projesi için aldığınız varsayılan ana bileşeni, design editör ile iyi bir uyum içinde çalışan bir **ConstraintLayout'tur**. Bu uygulama için, constraint layoutdan daha basit olan bir **LinearLayout** view group kullanırsınız. Sonraki derste view groupları  ve constraint layout hakkında çok daha fazlasını öğreneceksiniz.
 
4. **LinearLayout** activity içinde **android:layout_width** attribute'una dikkat edin. Bu **LinearLayout'un** genişliği, **match parent'ıyla** aynı genişlikte olmasını sağlayan parent ile eşleşecek şekilde ayarlanmıştır. Bu ana görünüm olduğundan, layout ekranın tam genişliğine genişler.
 
5. wrap_content olarak ayarlanmış **Android:layout_height** attribute'una dikkat edin. Bu attribute, LinearLayout'un yüksekliğini, şimdilik yalnızca TextView olan, içerdiği tüm layoutların birleşik yüksekliğiyle eşleşmesini sağlar.
 
6. `<TextView>` öğesini inceleyin. Metni görüntüleyen bu TextView, DiceRoller uygulamanızdaki tek görsel öğedir. **Android:text** attribute'u, görüntülenecek gerçek dizeyi, bu durumda "Hello World!" dizesini tutar.
 
7. `<TextView>` öğesindeki her ikisi de **wrap_content** olarak ayarlanmış **Android:layout_width** ve **Android:layout_height** attribute'larına dikkat edin. Metin görünümünün içeriği metnin kendisidir, bu nedenle görünüm yalnızca metin için gereken alanı kaplayacaktır.
 
## <a name="b"></a>Aşama 2 : Bir düğme ekleyin
   
Zar atma uygulaması, kullanıcının zarları atması ve ne attığını görmesi için bir yol olmadan pek kullanışlı değildir. Başlamak için, layouta zarları atmaya yarayan  bir düğme ekleyin ve kullanıcının attığı zar değerini gösteren bir metin ekleyin.
   
### Layout'a Bir Düğme Ekleyin
   
1. `<Button`'a girerek metin görünümünün altındaki düzene bir Button öğesi ekleyin ve ardından Return tuşuna basın. **/>** ile biten ve **layout_width** ve **layout_height** attribute'larını içeren bir **Button** bloğu görünür.
   
```
          
  <Button
   android:layout_width=""
   android:layout_height="" />        
          
```

2. Hem layout_width hem de layout_height özniteliklerini "wrap_content" olarak ayarlayın. Bu değerlerle button, içerdiği metin etiketiyle aynı genişlik ve yüksekliktedir.
   
3. Buttona bir **Android:text** niteliği ekleyin ve ona "Roll" değerini verin. Düğme öğesi şimdi şöyle görünür:

```
          
  <Button
   android:layout_width="wrap_content"
   android:layout_height="wrap_content"
   android:text="Roll" />        
          
``` 
   
Düğme görünümleri için **Text** attribute'ü düğmenin etiketidir. layout editör, attribute, bir ipucu veya uyarıyı belirten sarı renkle vurgulanır. Bu durumda, sarı vurgulamanın nedeni, "Roll" dizesinin düğme etiketinde sabit kodlanmış olmasıdır, ancak string bir kaynak olmalıdır. Bir sonraki bölümde string kaynakları hakkında bilgi edineceksiniz.

### String Kaynaklarını Çıkarın
   
layout veya kod dosyalarınızdaki stringleri sabit kodlamak yerine, tüm uygulama stringlerini ayrı bir dosyaya koymak en iyi uygulamadır. Bu dosyaya **strings.xml** denir ve uygulamanın kaynakları arasında **res/values/** dizininde bulunur.
   
Stringlerin ayrı bir dosyada olması, özellikle bu Stringlerin birden fazla kullanıyorsanız, onları yönetmeyi kolaylaştırır. Ayrıca, her dil için bir string kaynak dosyası oluşturmanız gerektiğinden, uygulamanızı çevirmek ve yerelleştirmek için string kaynakları zorunludur.
   
Android Studio, stringlerinizi ipuçları ve uyarılarla bir kaynak dosyasına koymayı hatırlamanıza yardımcı olur.
   
1. `<Button>` etiketinin **android:text** attribute'undaki "Roll" dizesine bir kez tıklayın.
2. Alt+Enter (macOS'ta Option+Enter) tuşlarına basın ve açılır menüden String kaynağını çıkar'ı seçin.
3. Kaynak adı için **roll_label** girin.
4. Tamam'a tıklayın. **res/values/string.xml** dosyasında bir string kaynağı oluşturulur ve Button öğesindeki string, o kaynağa bir başvuruyla değiştirilir: **android:text="@string/roll_label"**
   
5. **Project>Android** bölmesinde, **res>değerleri** genişletin ve ardından **strings.xml** dosyasında string kaynaklarınızı görmek için **strings.xml** öğesine çift tıklayın:

```
          
  <resources>
   <string name="app_name">DiceRoller</string>
   <string name="roll_label">Roll</string>
  </resources>      
          
``` 

> İpucu: Az önce eklediğiniz stringe ek olarak, strings.xml dosyası uygulama adını da içerir. Uygulama projenizi Boş Şablonu kullanarak başlatırsanız, uygulamanın adı ekranın üst kısmındaki uygulama çubuğunda görünür. app_name kaynağını düzenleyerek uygulama adını değiştirebilirsiniz.
   
###  Stil ve Konum Görünümleri
   
   Layout'unuz artık bir **TextView** ve bir **Button** görünümü içeriyor. Bu görevde, view grouptaki görünümleri daha çekici görünecek şekilde düzenleyeceğiz.
   
Layout'un bir önizlemesini görmek için "Design" sekmesine tıklayın. Şu anda her iki görünüm de yan yana ve ekranın üst kısmına doğru itilmiş durumda.
   
   ![image](https://user-images.githubusercontent.com/70329389/140083394-af705f7e-7927-4791-aca2-e5ce725ff3a9.png)
   
 XML düzenleyicisine dönmek için **Text** sekmesine tıklayın. **LinearLayout** etiketine **android:orientation** niteliğini ekleyin ve ona "vertical" bir değer verin. `<LinearLayout>` öğesi şimdi şöyle görünmelidir:
   
   ```
          
  <LinearLayout
   xmlns:android="http://schemas.android.com/apk/res/android"
   android:layout_width="match_parent"
   android:layout_height="wrap_content"
   android:orientation="vertical"
   tools:context=".MainActivity">      
          
   ``` 
LinearLayout view groupu, içerdiği görünümleri birbiri ardına bir satırda, yatay olarak bir satırda veya bir yığında dikey olarak konumlandırır. Yatay varsayılandır. TextView'in Button'un üstünde bulunmasını istediğiniz için yönlendirmeyi dikey olarak ayarlarsınız. Tasarım şimdi, metnin altındaki düğme ile şuna benziyor:
     
     ![image](https://user-images.githubusercontent.com/70329389/140084535-71946b20-08cb-4210-a2c7-a12bb6f937d0.png)
     
Hem TextView hem de Button'a **android:layout_gravity** attribute'u ekleyin ve ona **"center_horizontal"** değerini verin. Bu, her iki görünümü de yatay eksenin merkezi boyunca hizalar. TextView ve Button öğeleri şimdi şöyle görünmelidir:
     
     
   ```         
  <TextView   
   android:layout_width="wrap_content"
   android:layout_height="wrap_content"
   android:layout_gravity="center_horizontal"
   android:text="Hello World!" />

<Button
   android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:layout_gravity="center_horizontal"
    android:text="@string/roll_label" />             
   ``` 
     
**Android:layout_gravity** attribute'unu lineer layout'a ekleyin ve ona **"center_vertical"** değerini verin. **LinearLayout** öğeniz şimdi şöyle görünmelidir:
     
   ```
   <LinearLayout
      xmlns:android="http://schemas.android.com/apk/res/android"
      xmlns:tools="http://schemas.android.com/tools"
      android:layout_width="match_parent"
      android:layout_height="wrap_content"
      android:orientation="vertical"
      android:layout_gravity="center_vertical"
      tools:context=".MainActivity">  
     
   ```
   
>Not: Hem düğmeye hem de metin görünümlerine center_vertical yerçekimini eklerseniz (center_horizontal yerine), görünümler layoutun ortasında hem yatay hem de dikey olarak ortalanır. Yani viewler birbirinin üzerindedir.
Tüm alt öğeleri bir kerede ortalamak için, yukarıda gösterildiği gibi üst öğede (LinearLayout öğesi) center_vertical öğesini kullanın.
      
      
Metin görünümünde metnin boyutunu artırmak için, "30sp" değeriyle `<TextView>` öğesine **android:textSize** özniteliğini ekleyin. **sp** kısaltması, aygıtın görüntü kalitesinden bağımsız olarak metni boyutlandırmak için bir ölçü olan *scalable pixels* anlamına gelir. TextView öğesi şimdi şöyle görünmelidir:
      
  ```
      
   <TextView   
      android:layout_width="wrap_content"
      android:layout_height="wrap_content"
      android:layout_gravity="center_horizontal"
      android:textSize="30sp"
      android:text="Hello World!" />   
  
  ```

  Uygulamanızı derleyin ve çalıştırın.
      
  ![image](https://user-images.githubusercontent.com/70329389/140088605-c57ff0ca-d05d-49a2-a458-c33a9ab62bff.png)
      
  Artık hem metin hem de düğme güzel bir şekilde yerleştirilmiş ve metin görünümünde daha büyük metin var. Düğmenin henüz bir işlevi yok, bu nedenle tıkladığınızda hiçbir şey olmuyor. Bundan sonrası üzerinde çalışıyor olacağız.
      
      
### Koddaki Düğmeye Bir İşlev Verin
      
**MainActivity**'deki Kotlin kodu, bir düğmeye dokunduğunuzda ne olduğu gibi uygulamanızın etkileşimli kısımlarını tanımlamaktan sorumludur. Düğmeye tıklandığında   çalışan bir işlev yazmak için MainActivity'deki inflate edilmiş düzeninizde Button nesnesine bir referans almanız gerekir. Düğmeye bir referans almak için:
      
- **Button**'a XML dosyasında bir ID atayın.
- Belirli bir ID'ye sahip **view**e başvuru almak için kodunuzdaki **findViewById()** yöntemini kullanın.
      
Button görünümüne bir başvurunuz olduğunda, uygulama çalışırken dinamik olarak değiştirmek için bu görünümdeki yöntemleri çağırabilirsiniz. Örneğin, düğmeye dokunulduğunda kodu yürüten bir tıklama işleyicisi ekleyebilirsiniz.
      
- Henüz açık değilse, **Activity_main.xml** layout dosyasını açın ve **text** sekmesine tıklayın.
- Buttona **android:id** niteliğini ekleyin ve ona bir ad verin (bu durumda **"@+id/roll_button"**). `<Button>` öğeniz şimdi şöyle görünür:
      
  ```
      
   <Button
      android:id="@+id/roll_button"
      android:layout_width="wrap_content"
      android:layout_height="wrap_content"
      android:layout_gravity="center_horizontal"
      android:text="@string/roll_label" />  
  
  ```
      
XML layout dosyasında bir view için bir ID oluşturduğunuzda, Android Studio, oluşturulan **R** sınıfında bu kimliğin adıyla bir integer sabiti oluşturur. Bu nedenle, bir görünüme **roll_button** adını verirseniz, Android Studio, **R** sınıfında **roll_button** adlı bir tamsayı sabiti oluşturur ve oluşturur. Kimlik adının **"@+id"** öneki, derleyiciye bu ID sabitini R sınıfına eklemesini söyler. XML dosyanızdaki tüm görünüm kimlikleri bu ön eke sahip olmalıdır.
      
**MainActivity** Kotlin dosyasını açın. **onCreate()** içinde, **setContentView()** öğesinden sonra şu satırı ekleyin:
      
      
  ```
      
   val rollButton: Button = findViewById(R.id.roll_button)
  
  ```    
      
      
XML sınıfında tanımladığınız view için bir **View** referansı almak için **findViewById()** yöntemini kullanın. Bu durumda, **R** sınıfından **Button** referansını ve **roll_button** ID'sini alırsınız ve bu referansı **rollButton** değişkenine atarsınız.
      
> Not: Satırı kopyalayıp yapıştırmak yerine yazarsanız, yazmaya başladığınızda Android Studio'nun kimlik adı için otomatik tamamlama ipucu sağladığını fark edeceksiniz.
      
      
Android Studio'nun, çözülmemiş bir referans olduğunu ve bu sınıfı kullanabilmeniz için önce içe aktarmanız gerektiğini belirtmek için **Button** sınıfını kırmızıyla vurgulayıp altını çizdiğine dikkat edin. Tam nitelikli sınıf adını gösteren bir araç ipucu da görünebilir:
      
![image](https://user-images.githubusercontent.com/70329389/140391281-b59e3f9f-a35d-44d8-92f7-f1d5d4fde3ca.png)

Tam nitelikli class adını kabul etmek için **Alt+Enter** (**Mac'te Option+Enter**) tuşlarına basın.
            
> İpucu: Anlam açıksa, Android Studio'yu sınıflar için otomatik olarak içe aktarma ifadeleri ekleyecek şekilde yapılandırabilirsiniz. **Editor > General >Auto Import>** Otomatik İçe Aktarma ayarları paneli, içe aktarmaların nasıl işlendiğini belirtir.
      
### Bir tost görüntülemek için bir tıklama işleyici ekleyin
      
*Tıklama işleyicisi*, kullanıcı düğme gibi tıklanabilir bir UI öğesine her tıkladığında veya dokunduğunda çağrılan bir yöntemdir. Bir tıklama işleyicisi oluşturmak için ihtiyacınız olan:
      
- Bazı işlemleri gerçekleştiren bir yönteme.
      
- Buttonu işleyici yöntemine bağlayan **setOnClickListener()** yöntemine.
      
Bu aşamada, bir Tost görüntülemek için bir Click-handler yöntemi oluşturursunuz. (tost, ekranda kısa bir süre için açılan bir mesajdır.) Click-handler yöntemini Button'a bağlarsınız.

- **MainActivity** sınıfınızda **onCreate()**'den sonra **rollDice()** adlı özel bir işlev oluşturun.
      
   ```
      
   private fun rollDice() {
  
   }
  
  ```  
      
- rollDice() çağrıldığında bir Toast görüntülemek için bu satırı rollDice() yöntemine ekleyin:
      
  ```
      
   Toast.makeText(this, "button clicked", 
      Toast.LENGTH_SHORT).show()
  
  ```
      
      
 Bir tost oluşturmak için **Toast.makeText()** yöntemini çağırın. Bu yöntem üç şey gerektirir:
      
- Bir [Context](https://developer.android.com/reference/kotlin/android/content/Context) nesnesi. Context nesnesi, Android işletim sisteminin mevcut durumu ile iletişim kurmanıza ve bu durum hakkında bilgi almanıza olanak tanır. Tost nesnesinin işletim sistemine tostu görüntülemesini söyleyebilmesi için burada bir Context'e ihtiyacınız var. **AppCompatActivity**, Context'in bir alt sınıfı olduğundan, bağlam için sadece this anahtar sözcüğünü kullanabilirsiniz.
      

      
      
      
 
      
      

      
      
 
      
      
   

