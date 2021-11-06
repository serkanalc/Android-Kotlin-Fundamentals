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


## <a name="d"></a>API Düzeylerini & Uyumluluğu Anlayın

