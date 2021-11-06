# Görüntü kaynakları ve uyumluluk

- [Görüntü Kaynakları Ekleyin & Güncelleyin](#a)
- [Görünümleri Verimli Bir Şekilde Bulun](#b)
- [Varsayılan Bir Resim Kullanın](#c)
- [API Düzeylerini & Uyumluluğu Anlayın](#d)

Bu codelab'de, önceki codelab'de başlattığınız DiceRoller uygulamasını geliştirirsiniz ve zar atıldığında değişen zar görüntüleri eklersiniz. Son DiceRoller uygulaması şöyle görünür:

![image](https://user-images.githubusercontent.com/70329389/140610934-aa1f52ea-9c6e-49cd-af06-f8aff02992f3.png)

## <a name="a"></a>Görüntü Kaynakları Ekleyin & Güncelleyin

Son kod laboratuvarının sonunda, kullanıcı bir düğmeye her dokunduğunda 1 ile 6 arasında bir sayıyla metin görünümünü güncelleyen bir uygulamanız vardı. Ancak, uygulamanın adı 1-6 Sayı Üreticisi değil, DiceRoller'dır, bu nedenle zarlar gerçekten zar gibi görünse iyi olurdu. Bu görevde, uygulamanıza bazı zar görüntüleri eklersiniz. Ardından, düğmeye basıldığında metni güncellemek yerine, her rulo sonucu için farklı bir görüntüde geçiş yaparsınız.

### Aşama 1 : Resimleri Ekleyin

1. Henüz açık değilse, DiceRoller uygulama projesini Android Studio'da açın. 
2. **Project > Android** görünümünde **res** klasörünü genişletin ve ardından çekilebilir öğesini **drawable**.

![image](https://user-images.githubusercontent.com/70329389/140611122-ff412699-3167-4dc5-a2d8-8f38256bc435.png)

Uygulamanız, resimler ve simgeler, renkler, dizeler ve XML layoutları dahil olmak üzere birçok farklı kaynak kullanır. Tüm bu kaynaklar **res** klasöründe saklanır. **Drawable** klasör, uygulamanız için tüm görüntü kaynaklarını koymanız gereken yerdir. Zaten drawable klasörde, uygulamanın başlatıcı simgeleri için kaynakları bulabilirsiniz.

3. **ic_launcher_background.xml** dosyasını çift tıklayın. Bunların simgeyi bir vektör görüntüsü olarak tanımlayan XML dosyaları olduğunu unutmayın. Vektörler, resimlerinizin birçok farklı boyut ve çözünürlükte çizilmesini sağlar. PNG veya GIF gibi bitmap görüntülerin farklı cihazlar için ölçeklenmesi gerekebilir, bu da kalite kaybına neden olabilir.
4. Drawable vektörü görsel biçimde görüntülemek için XML editörünün sağ sütununda  **Preview**'e tıklayın.

![image](https://user-images.githubusercontent.com/70329389/140611259-b7b2520a-5ff9-4503-b998-c723115f10f4.png)

5. DiceImages.zip'ten uygulamanız için [zar resimlerini](https://github.com/serkanalc/Android-Kotlin-Fundamentals/blob/main/Part%202%20-%20Build%20an%20interactive%20app/Dok%C3%BCman%202%20-%20G%C3%B6r%C3%BCnt%C3%BC%20Kaynaklar%C4%B1%20%26%20Uyumluluk/DiceImages.zip) indirin. Arşivi açın. Şuna benzeyen bir XML dosyaları klasörünüz olmalıdır:

![image](https://user-images.githubusercontent.com/70329389/140611328-6fe0c43f-cdb8-4bff-9ac1-fad909bdc214.png)

6. Android Studio'da, şu anda Android yazan proje görünümünün üst kısmındaki açılır menüyü tıklayın ve Proje'yi seçin. Aşağıdaki ekran görüntüsü, uygulamanızın yapısının dosya sisteminde nasıl göründüğünü gösterir.

![image](https://user-images.githubusercontent.com/70329389/140611384-7e39f309-6cc4-4ff4-9c7b-63ca1d1dc3c0.png)

7. **DiceRoller > app > src > main > res > drawable** öğesini genişletin.
8. **DiceImages** klasöründeki tüm XML dosyalarını Android Studio'ya ve **drawable** klasöre sürükleyin. **OK**'ı tıklayın.

> Not: Dosyaları drawable24 klasörüne değil, drawable klasörüne bıraktığınızdan emin olun. Bu klasör ve diğerlerinin ne için kullanıldığı hakkında daha sonra daha fazla bilgi edineceksiniz.

Ayrıca, **DiceImages** klasörünün kendisini dahil etmeyin. Yalnızca XML dosyalarını sürükleyin.

9. Projeyi tekrar Android görünümüne çevirin ve zar resmi XML dosyalarınızın drawable klasörde olduğuna dikkat edin.
10. **dice_1.xml**'ye çift tıklayın ve bu görüntü için XML koduna dikkat edin. Bu drawable vektörün gerçekte nasıl göründüğünün bir önizlemesini almak için **Preview** düğmesini tıklayın.

![image](https://user-images.githubusercontent.com/70329389/140611513-54cc166f-b2ff-432f-a6ac-d61c517a2540.png)

### Aşama 2 : Resimleri Kullanmak İçin Layout'u Güncelleyin

Artık **res/drawables** klasörünüzde zar görüntü dosyalarına sahip olduğunuza göre, bu dosyalara uygulamanızın layoutunda ve kodundan erişebilirsiniz. Bu adımda, viewları görüntülemek için sayıları görüntüleyen TextView'ı bir ImageView ile değiştirirsiniz.

1. Henüz açık değilse, **aktivite_main.xml** layout dosyasını açın. Düzenin XML kodunu görüntülemek için **Text** sekmesine tıklayın.
2. `<TextView>` öğesini silin.
3. Şu attribute'lara sahip bir `<ImageView>` öğesi ekleyin:

```
<ImageView
   android:id="@+id/dice_image"
   android:layout_width="wrap_content"
   android:layout_height="wrap_content"
   android:layout_gravity="center_horizontal"
   android:src="@drawable/dice_1" />
```

Layout'unuzda bir resmi görüntülemek için bir **ImageView** kullanırsınız. Bu öğe için tek yeni attribute, görüntünün kaynak kaynağını belirtmek için **Android:src'dir**. Bu durumda, **@drawable/dice_1** görüntü kaynağı, Android'in **dice_1** adlı görüntü için drawable kaynaklara (**res/drawable**) bakması gerektiği anlamına gelir.

4. Düzeni önizlemek için **Preview** düğmesini tıklayın. Şöyle görünmelidir:

![image](https://user-images.githubusercontent.com/70329389/140612282-4eacf1b1-2e50-42b2-922f-9e2db38b225e.png)

### Aşama 3 : Kodunuzu Güncelleyin

1. **MainActivity**'yi açın. **RollDice()** fonksyonu şu ana kadar şöyle görünüyor:

```
private fun rollDice() {
   val randomInt = (1..6).random()

   val resultText: TextView = findViewById(R.id.result_text)
   resultText.text = randomInt.toString()
}
```

**R.id.result_text** referansının kırmızıyla vurgulanabileceğine dikkat edin; bunun nedeni **TextView**'i layouttan sildiğiniz ve bu ID'nin artık mevcut olmamasıdır.

2. **resultText** değişkenini tanımlayan ve Text özelliğini ayarlayan fonksyonun sonundaki iki satırı silin. Artık layoutta bir **TextView** kullanmıyorsunuz, bu nedenle her iki satıra da ihtiyacınız yok.
3. ID'ye (**R.id.dice_image**) göre layoutundaki yeni **ImageView**'e bir referans almak için **findViewByID()** kullanın ve bu görünümü yeni bir **diceImage** değişkenine atayın:

```
val diceImage: ImageView = findViewById(R.id.dice_image)
```

4. **RandomInteger** değerine bağlı olarak belirli bir zar görüntüsü seçmek için bir **When** bloğu ekleyin:

```
val drawableResource = when (randomInt) {
   1 -> R.drawable.dice_1
   2 -> R.drawable.dice_2
   3 -> R.drawable.dice_3
   4 -> R.drawable.dice_4
   5 -> R.drawable.dice_5
   else -> R.drawable.dice_6
}
```

ID'lerde olduğu gibi, drawable klasördeki zar görüntülerine **R** sınıfındaki değerlerle başvurabilirsiniz. Burada **R.drawable**, uygulamanın drawable klasörünü ifade eder ve **dice_1**, bu klasör içindeki belirli bir zar görüntü kaynağıdır.

5. **ImageView**'in kaynağını **setImageResource()** yöntemiyle ve az önce bulduğunuz zar görüntüsüne referansla güncelleyin.

```
diceImage.setImageResource(drawableResource)
```

6. Uygulamayı derleyin ve çalıştırın. Şimdi **Roll** düğmesine tıkladığınızda, görüntü uygun görüntü ile güncellenmelidir.

## <a name="b"></a>Görünümleri Verimli Bir Şekilde Bulun

Uygulamanızdaki her şey çalışır, ancak uygulama geliştirmek için yalnızca çalışan koda sahip olmaktan daha fazlası vardır. Ayrıca, performans gösteren, iyi davranan uygulamaların nasıl yazılacağını da anlamalısınız. Bu, kullanıcınız en pahalı Android cihazına veya en iyi ağ bağlantısına sahip olmasa bile uygulamalarınızın iyi çalışması gerektiği anlamına gelir. Siz daha fazla özellik ekledikçe uygulamalarınız da sorunsuz çalışmaya devam etmeli ve kodunuz okunabilir ve iyi organize edilmiş olmalıdır.

Bu bölümde, uygulamanızı daha verimli hale getirmenin bir yolunu öğreneceksiniz.

1. Henüz açık değilse **MainActivity**'yi açın. **rollDice()** yönteminde, diceImage değişkeninin bildirimine dikkat edin:

```
val diceImage : ImageView = findViewById(R.id.dice_image)
```

**rollDice()**, Roll düğmesinin tıklama işleyicisi olduğundan, kullanıcı bu düğmeye her dokunduğunda, uygulamanız **findViewById()** öğesini çağırır ve bu **ImageView**'e başka bir referans alır. İdeal olarak, **findViewById()** öğesine yapılan çağrı sayısını en aza indirmelisiniz, çünkü Android sistemi her seferinde tüm görünüm hiyerarşisini arar ve bu pahalı bir işlemdir.

Bunun gibi küçük bir uygulamada, büyük bir sorun değil. Daha yavaş bir telefonda daha karmaşık bir uygulama çalıştırıyorsanız, sürekli olarak **findViewById()** öğesinin çağrılması uygulamanızın gecikmesine neden olabilir. Bunun yerine findViewById() öğesini bir kez çağırmak ve View nesnesini bir alanda depolamak en iyi uygulamadır. ImageView referansını bir alanda tutmak, sistemin herhangi bir zamanda doğrudan Görünüme erişmesini sağlar, bu da performansı artırır.

2. Sınıfın en üstünde, **onCreate()**'den önce **ImageView**'i tutacak bir alan oluşturun.

```
var diceImage : ImageView? = null
```

İdeal olarak, bu değişkeni bildirildiğinde burada veya bir kurucuda başlatırsınız - ancak Android etkinlikleri kurucuları kullanmaz. Aslında, layout'dali görünümler **setContentView()** çağrısıyla **onCreate()** yönteminde  inflate edilene kadar bellekte erişilebilir nesneler değildir. Bu gerçekleşene kadar **diceImage** değişkenini başlatamazsınız.

Seçeneklerden biri, bu örnekte olduğu gibi **diceImage** değişkenini **null** olarak tanımlamaktır. Bildirildiğinde onu **null** olarak ayarlayın ve ardından **findViewById()** ile **onCreate()** içindeki gerçek **ImageView**'a atayın. Ancak bu, kodunuzu karmaşıklaştıracaktır, çünkü artık **diceImage**'ı her kullanmak istediğinizde boş değeri kontrol etmeniz gerekiyor. Daha iyi bir yol var.

3. **Lateinit** anahtar sözcüğünü kullanmak için **diceImage** bildirimini değiştirin ve **null** atamayı kaldırın:

```
lateinit var diceImage : ImageView
```

**lateinit** anahtar sözcüğü, Kotlin derleyicisine, kod üzerinde herhangi bir işlem çağırmadan önce değişkenin başlatılacağını vaat eder. Bu nedenle, burada **null** olarak değişkeni başlatmamıza gerek yok ve onu kullandığımızda onu **non-nullable** bir değişken olarak değerlendirebiliriz. **Lateinit**'i görünümleri bu şekilde tutan alanlarla kullanmak en iyi uygulamadır.

4. **onCreate()** içinde, **setContentView()** yönteminden sonra **ImageView**'i almak için **findViewById()** öğesini kullanın.

```
diceImage = findViewById(R.id.dice_image)
```

**ImageView**'i bildiren ve alan **rollDice()** içindeki eski satırı silin. Bu satırı daha önce alan bildirimiyle değiştirdiniz.

```
val diceImage : ImageView = findViewById(R.id.dice_image)
```

6. Beklendiği gibi çalıştığını görmek için uygulamayı tekrar çalıştırın.

## <a name="c"></a>Varsayılan Bir Resim Kullanın

Şu anda zar için ilk görüntü olarak **dice_1** kullanıyorsunuz. Bunun yerine, zar ilk kez atılana kadar hiçbir görüntü göstermemek istediğinizi varsayalım. Bunu başarmanın birkaç yolu vardır.

1. **Text** sekmesinde **aktivite_main.xml**'i açın.
2. `<ImageView>` öğesinde, **android:src** niteliğini "**@drawable/empty_dice"** olarak ayarlayın:

```
android:src="@drawable/empty_dice" 
```

**empty_dice** resmi, indirdiğiniz ve drawable klasöre eklediğiniz resimlerden biriydi. Diğer zar resimleriyle aynı boyutta, sadece boş. Bu resim, uygulama ilk başladığında gösterilecek olan resimdir.

3. **Design** sekmesine tıklayın. Zar resmi artık boş, ancak önizlemede de görünmüyor.

![image](https://user-images.githubusercontent.com/70329389/140613372-7aae666a-9505-40dc-8422-80d0b07f5973.png)

Bir tasarımın içeriğinin çalışma zamanında dinamik olarak tanımlanması oldukça yaygındır; örneğin, internetten veri alan herhangi bir uygulama muhtemelen boş bir ekranla başlamalıdır. Ancak, bir uygulamayı layoutta bir tür yer tutucu veriye sahip olacak şekilde tasarlarken, ne düzenlediğinizi bilmeniz için yararlıdır. 
4. **Activity_main.xml**'de **android:src** satırını kopyalayın ve ikinci bir kopya yapıştırın. "Android" kelimesini ""tools" olarak değiştirin, böylece iki özelliğiniz şöyle görünür:

```
android:src="@drawable/empty_dice" 
tools:src="@drawable/empty_dice" />
```

Burada, bu özelliğin XML namespace'ini varsayılan Android namespace tools namespace değiştirdiniz. Araçlar namespace, yalnızca Android Studio'da önizleme veya tasarım düzenleyicide kullanılan yer tutucu içeriği tanımlamak istediğinizde kullanılır. Uygulamayı derlerken, **tools** namespace kullanan nitelikler kaldırılır.

Ad alanları, aynı ada sahip özniteliklere atıfta bulunurken belirsizliğin çözülmesine yardımcı olmak için kullanılır. Örneğin, `<ImageView>` etiketindeki bu özniteliklerin her ikisi de aynı ada (**src**) sahiptir, ancak namespace'e farklıdır.

5. Layout dosyasının kökündeki `<LinearLayout>` öğesini inceleyin ve burada tanımlanan iki namespace'e dikkat edin.

```
<LinearLayout
   xmlns:android="http://schemas.android.com/apk/res/android"
   xmlns:tools="http://schemas.android.com/tools"
   ...
```

6. **ImageView** etiketindeki **tools:src** niteliğini **empty_dice** yerine **dice_1** olarak değiştirin:

```
android:src="@drawable/empty_dice" 
tools:src="@drawable/dice_1" />
```

**dice_1** görüntüsünün, önizlemede yer tutucu görüntü olarak artık yerinde olduğuna dikkat edin.

7. Uygulamayı derleyin ve çalıştırın. Gerçek uygulamada zar görüntüsünün, siz **Roll'a** tıklayana veya dokunana kadar boş olduğuna dikkat edin.


## <a name="d"></a>API Düzeylerini & Uyumluluğu Anlayın

Android için geliştirmenin en güzel yanlarından biri, Nexus One'dan Pixel'e, tabletler, Pixelbook'lar, saatler, TV'ler ve arabalar gibi faktörler oluşturmak için kodunuzun çalışabileceği çok sayıda cihazdır.

Android için yazarken, bu farklı cihazların her biri için tamamen ayrı uygulamalar yazmazsınız; saatler ve TV'ler gibi kökten farklı form faktörlerinde çalışan uygulamalar bile kod paylaşabilir. Ancak tüm bunları desteklemek için bilmeniz gereken kısıtlamalar ve uyumluluk stratejileri var.

Bu görevde, uygulamanızı belirli Android API düzeyleri (sürümleri) için nasıl hedefleyeceğinizi ve eski cihazları desteklemek için Android Jetpack kitaplıklarını nasıl kullanacağınızı öğreneceksiniz.

### Aşama 1 : API Düzeylerini Keşfedin

Önceki dokümanda projenizi oluşturduğunuzda, uygulamanızın desteklemesi gereken belirli Android API düzeyini belirtmiştiniz. Android işletim sistemi, alfabetik sıraya göre lezzetli ikramlardan sonra adlandırılan farklı sürüm numaralarına sahiptir. Her işletim sistemi sürümü, yeni özellikler ve işlevlerle birlikte gelir. Örneğin, Android Oreo, [Picture-in-picture(Resim içinde resim)](https://developer.android.com/guide/topics/ui/picture-in-picture) uygulamaları desteğiyle birlikte gelirken, Android Pie [Slices](https://developer.android.com/guide/slices)'ı tanıttı. API seviyeleri, Android sürümlerine karşılık gelir. Örneğin, API 19, Android 4.4'e (KitKat) karşılık gelir.

Donanımın neyi destekleyebileceği, kullanıcıların cihazlarını güncellemeyi seçip seçmemeleri ve üreticilerin farklı işletim sistemi seviyelerini destekleyip desteklemediği gibi bir dizi faktör nedeniyle, kullanıcılar kaçınılmaz olarak farklı işletim sistemi sürümlerini çalıştıran cihazlarla sonuçlanır.

Uygulama projenizi oluşturduğunuzda, uygulamanızın desteklediği minimum API düzeyini belirtirsiniz. Yani, uygulamanızın desteklediği en eski Android sürümünü belirtirsiniz. Uygulamanızın ayrıca derlendiği bir düzeyi ve hedeflediği bir düzeyi vardır. Bu seviyelerin her biri, Gradle derleme dosyalarınızdaki bir yapılandırma parametresidir.

1. **Gradle Scripts** Dosyaları klasörünü genişletin ve **build.gradle (Module: app)** dosyasını açın.

Bu dosya, uygulama modülüne özgü yapı parametrelerini ve bağımlılıkları tanımlar. **build.gradle (Project: DiceRoller)** dosyası, bir bütün olarak proje için derleme parametrelerini tanımlar. Çoğu durumda, uygulama modülünüz projenizdeki tek modüldür, bu nedenle bu bölüm rastgele görünebilir. Ancak uygulamanız daha karmaşık hale gelir ve birkaç bölüme ayırırsanız veya uygulamanız Android watch gibi platformları destekliyorsa aynı projede farklı modüllerle karşılaşabilirsiniz. 

2. **Android** bölümünü **build.gradle** dosyasının üst kısmına doğru inceleyin. (Aşağıdaki örnek bölümün tamamı değildir, ancak bu kod laboratuvarı için en çok ilgilendiğiniz şeyi içerir.)

```
android {
   compileSdkVersion 28
   defaultConfig {
       applicationId "com.example.android.diceroller"
       minSdkVersion 19
       targetSdkVersion 28
       versionCode 1
       versionName "1.0"
   }
```

3. **compileSdkVersion** parametresini inceleyin.

```
compileSdkVersion 28
```

Bu parametre, Gradle'ın uygulamanızı derlemek için kullanması gereken Android API düzeyini belirtir. Bu, uygulamanızın destekleyebileceği en yeni Android sürümüdür. Yani uygulamanız, bu API düzeyinde ve daha düşük düzeyde yer alan API özelliklerini kullanabilir. Bu durumda uygulamanız, Android 9'a (Pie) karşılık gelen API 28'i destekler.

4. **defaultConfig** bölümünün içindeki **targetSdkVersion** parametresini inceleyin:

```
targetSdkVersion 28
```

Bu değer, uygulamanızı test ettiğiniz en son API'dir. Çoğu durumda bu, **compileSdkVersion** ile aynı değerdir.

5. **minSdkVersion** parametresini inceleyin.

```
minSdkVersion 19
```

Bu parametre, uygulamanızın üzerinde çalışacağı en eski Android sürümünü belirlediği için üç parametreden en önemlisidir. Bu API seviyesinden daha eski Android işletim sistemini çalıştıran cihazlar, uygulamanızı hiçbir şekilde çalıştıramaz.

Uygulamanız için minimum API düzeyini seçmek zor olabilir. API düzeyini çok düşük ayarlarsanız Android işletim sisteminin daha yeni özelliklerini kaçırırsınız. Çok yükseğe ayarladığınızda uygulamanız yalnızca daha yeni cihazlarda çalışabilir.

Projenizi kurduğunuzda ve uygulamanız için minimum API seviyesini tanımladığınız yere geldiğinizde, **API Version Distribution** sekmesini görmek için **Help me choose**'a tıklayın. İletişim kutusu, kaç cihazın farklı işletim sistemi seviyelerini kullandığı ve işletim sistemi seviyelerine eklenen veya değiştirilen özellikler hakkında bilgi verir. Farklı API düzeylerini desteklemenin etkileri hakkında daha fazla bilgi içeren Android dokümantasyon sürüm notlarına ve kontrol paneline de göz atabilirsiniz.

### Aşama 2 : Uyumluluğu Keşfedin

Farklı Android API seviyeleri için yazmak, uygulama geliştiricilerinin karşılaştığı yaygın bir zorluktur, bu nedenle Android framework ekibi size yardımcı olmak için çok çalıştı.

2011'de ekip, geriye dönük uyumlu sınıflar ve yardımcı işlevler sunan Google tarafından geliştirilen bir kitaplık olan ilk destek kitaplığını yayınladı. 2018'de Google, destek kitaplığının önceki sınıflarının ve işlevlerinin çoğunu içeren ve aynı zamanda destek kitaplığını genişleten bir kitaplık koleksiyonu olan Android Jetpack'i duyurdu.

1. MainActivity'yi açın.
2. MainActivity sınıfınızın Activity'nin kendisinden değil, AppCompatActivity'den extend edildiğine dikkat edin.

```
class MainActivity : AppCompatActivity() { 
...
```

**AppCompatActivity**, etkinliğinizin farklı platform işletim sistemi düzeylerinde aynı görünmesini sağlayan bir uyumluluk sınıfıdır.

3. Sınıfınızın içe aktarmayı(import etmeyi) extend etmek için **import** ile başlayan satırın yanındaki **+** sembolüne tıklayın. AppCompatActivity sınıfının **androidx.appcompat.app** paketinden import edildiğini unutmayın. Android Jetpack kitaplıkları için namespace **androidx**'tir.
4. **build.gradle**'ı (**Module: app**) açın ve dependencies bölümüne gidin.

```
dependencies {
   implementation fileTree(dir: 'libs', include: ['*.jar'])
   implementation"org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlin_version"
   implementation 'androidx.appcompat:appcompat:1.0.0-beta01'
   implementation 'androidx.core:core-ktx:1.0.1'
   implementation 'androidx.constraintlayout:constraintlayout:1.1.2'
   testImplementation 'junit:junit:4.12'
   androidTestImplementation 'androidx.test:runner:1.1.0-alpha4'
   androidTestImplementation 
        'androidx.test.espresso:espresso-core:3.1.0-alpha4'
}
```

**androidx**'in bir parçası olan ve **AppCompatActivity** sınıfını içeren **appcompat** kitaplığına olan dependency'e dikkat edin.

> İpucu: Genel olarak, uygulamanız Jetpack kitaplıklarından bir uyumluluk sınıfı kullanabiliyorsa, bu sınıflar mümkün olan en fazla sayıda özellik ve cihaz için destek sağladığından bu sınıflardan birini kullanmalıdır.

### Aşama 3 : Vektör Drawable'ları İçin Uyumluluk Ekleyin

namespaces, Gradle ve uyumluluk hakkındaki yeni bilgilerinizi, uygulamanızda eski platformlarda uygulamanızın boyutunu optimize edecek son bir ayarlama yapmak için kullanacaksınız.

1. **res** klasörünü genişletin ve ardından **drawable** alanını genişletin. Zar resimlerinden birine çift tıklayın.

Daha önce öğrendiğiniz gibi, tüm zar görüntüleri aslında zarların renklerini ve şekillerini tanımlayan XML dosyalarıdır. Bu tür dosyalara vektör çizimleri denir. PNG gibi bitmap görüntü biçimlerine karşı vektör çizimleriyle ilgili güzel şey, vektör çizimlerinin kaliteden ödün vermeden ölçeklenebilmesidir. Ayrıca, drawable bir vektör genellikle aynı görüntüden bitmap formatında çok daha küçük bir dosyadır.

Vektör çizimleri hakkında dikkat edilmesi gereken önemli bir nokta, API 21'den itibaren desteklenmeleridir. Ancak uygulamanızın minimum SDK'sı API 19'a ayarlanmıştır. Uygulamanızı bir API 19 cihazında veya emilatöründe denediyseniz, uygulamanın iyi bir şekilde derlendiğini ve çalıştığını görürsünüz. Peki bu nasıl çalışıyor?

Uygulamanızı oluşturduğunuzda, Gradle oluşturma işlemi, vektör dosyalarının her birinden bir PNG dosyası oluşturur ve bu PNG dosyaları, 21'in altındaki herhangi bir Android cihazında kullanılır. Bu ekstra PNG dosyaları, uygulamanızın boyutunu artırır. Gereksiz yere büyük uygulamalar harika değildir; kullanıcılar için indirme işlemlerini yavaşlatır ve cihazlarının sınırlı alanından daha fazlasını kaplar. Büyük uygulamaların kaldırılma ve kullanıcıların bu uygulamaları indirememeleri veya indirmelerini iptal etme şansları da daha yüksektir.

İyi haber şu ki, vektör çizimleri için API düzeyi 7'ye kadar bir  Android X compatibility kitaplığı var. 

2. **build.gradle**'ı açın (**Module: app**). Bu satırı **defaultConfig** bölümüne ekleyin:

```
vectorDrawables.useSupportLibrary = true
```

3. **Sync Now** düğmesini tıklayın. Bir **build.gradle** dosyası her değiştirildiğinde, derleme dosyalarını projeyle eşitlemeniz gerekir.
4. **Activity_main.xml** layout dosyasını açın. Bu namespace, araçlar namespace altındaki kök `<LinearLayout>` etiketine ekleyin:

```
xmlns:app="http://schemas.android.com/apk/res-auto"
```

Uygulama namespace, temel Android framework'ünden değil, özel kodunuzdan veya kitaplıklardan gelen attributelar içindir.

5. `<ImageView>` öğesindeki **android:src** niteliğini **app:srcCompat** olarak değiştirin.

```
app:srcCompat="@drawable/empty_dice"
```

**app:srcCompat** attribute'u, Android'in eski sürümlerinde, API düzeyi 7'ye kadar vektör çizimlerini desteklemek için Android X kitaplığını kullanır.

6. Uygulamanızı oluşturun ve çalıştırın. Ekranda farklı bir şey görmezsiniz, ancak artık uygulamanızın nerede çalışırsa çalışsın zar görüntüleri için oluşturulmuş PNG dosyalarını kullanmasına gerek yoktur, bu da daha küçük bir uygulama dosyası anlamına gelir.

## <a name="e"></a>Ödev

Soru 1 : Hangi `<ImageView>` özelliği, yalnızca Android Studio'da kullanılması gereken bir kaynak resmi belirtir?

- [ ] android:srcCompat
- [ ] app:src
- [ ] tools:src
- [ ] tools:sourceImage

Soru 2 : Kotlin kodunda bir ImageView için görüntü kaynağını hangi yöntem değiştirir?

- [ ] setImageResource()
- [ ] setImageURI()
- [ ] setImage()
- [ ] setImageRes()

Soru 3 : Bir değişken bildirimindeki lateinit anahtar sözcüğü Kotlin kodunda neyi gösterir?

- [ ] Değişken hiçbir zaman başlatılmaz.
- [ ] Değişken yalnızca uygulama çalışma zamanında başlatılır.
- [ ] Değişken otomatik olarak null olarak başlatılır.
- [ ] Değişken daha sonra başlatılacaktır. Söz veriyorum!

Soru 4 : Hangi Gradle konfigürasyonunda, uygulamanızın test edildiği en son API düzeyini gösterir?

- [ ] minSdkVersion
- [ ] compileSdkVersion
- [ ] targetSdkVersion
- [ ] testSdkVersion

Soru 5 : Kodunuzda androidx ile başlayan bir import satırı görüyorsunuz. Ne anlama geliyor?

- [ ] Sınıf, Android Jetpack kitaplıklarının bir parçasıdır.
- [ ] Sınıf, uygulama çalıştığında dinamik olarak yüklenecek olan harici bir kitaplıkta bulunur.
- [ ] Sınıf, sınıfınız için "ekstra" ve isteğe bağlıdır.
- [ ] Sınıf, Android'in XML desteğinin bir parçasıdır.


