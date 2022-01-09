# <a name="1"></a>Lifecylelar & loglama

- [Lifecycle metotlarını keşfedin ve temel log kaydı ekleyin](#a)
- [Log kaydı için Timber kullanın](#b)
- [Lifecycle kullanım örneklerini keşfedin](#c)
- [Fragment lifecycle'ı keşfedin](#d)

Bu dökümanda, DessertClicker adlı bir başlangıç uygulamasıyla çalışacaksınız. Bu uygulamada, kullanıcı ekranda bir tatlıya her dokunduğunda, uygulama tatlıyı kullanıcı için "satın alır". Uygulama, satın alınan tatlı sayısı ve kullanıcının harcadığı toplam tutar için düzendeki değerleri günceller.

![app image](https://developer.android.com/codelabs/kotlin-android-training-lifecycles-logging/img/8216c20f5571fc04.png)

Bu uygulama Android lifecycle ilgili birkaç hata içeriyor: Örneğin, belirli durumlarda uygulama tatlı değerlerini 0'a resetler ve uygulama arka planda olsa bile sistem kaynaklarını kullanmaya devam eder. Android lifecycle'ı anlamak, bu sorunların neden olduğunu ve nasıl düzeltileceğini anlamanıza yardımcı olacaktır.

## <a name="a"></a>Aşama 1 : Lifecycle metotlarını keşfedin ve temel log kaydı ekleyin

Her activity ve her fragment, bir lifecycle (yaşam döngüsü) olarak bilinen şeye sahiptir. Bu, bu kelebeğin yaşam döngüsü gibi, hayvan yaşam döngülerine bir göndermedir - kelebeğin farklı durumları, doğumdan tam olarak gelişmiş yetişkinliğe ve ölüme kadar büyümesini gösterir.

![butterfly lifecycle](https://developer.android.com/codelabs/kotlin-android-training-lifecycles-logging/img/c685f48ff799f0c9.png)

Benzer şekilde, activity lifecycle, activity'nin ilk başlatıldığı andan sonunda yok edildiği ve hafızasının sistem tarafından geri alındığı zamana kadar bir activity'nin geçebileceği farklı durumlardan oluşur. Kullanıcı uygulamanızı başlatırken, activityler arasında gezinirken, uygulamanızın içinde ve dışında gezinirken ve uygulamanızdan çıktıkça activity state'i (durumu) değişir. Aşağıdaki şema, tüm activity lifecycle statelerini göstermektedir. Adlarından da anlaşılacağı gibi, bu stateler activity'nin durumunu temsil eder.

![activity lifecycle](https://developer.android.com/codelabs/kotlin-android-training-lifecycles-logging/img/c803811f4cb4034b.png)

Çoğu zaman, bazı davranışları değiştirmek veya activity lifecycle state'i değiştiğinde bazı kodlar çalıştırmak istersiniz. Bu nedenle, Activity class'ının kendisi ve AppCompatActivity gibi herhangi bir Activity subclass'ı, bir dizi lifecycle callback metodu uygular. Android, activity bir state'ten diğerine geçtiğinde bu callbackleri çağırır ve bu lifecycle state değişikliklerine yanıt olarak görevleri gerçekleştirmek için kendi activitylerinizde bu metotları override edebilirsiniz. Aşağıdaki şema, kullanılabilir override edilebilir callbacklerle birlikte lifecycle statelerini gösterir.

![activity lifecycle states](https://developer.android.com/codelabs/kotlin-android-training-lifecycles-logging/img/f6b25a71cec4e401.png)

Bir fragment'ın da bir lifecycle'ı vardır. Bir fragment'ın lifecycle'ı, bir activity'nin lifecycle'ına benzer, bu nedenle öğrendiklerinizin çoğu her ikisi için de geçerlidir. Bu dökümanda, Android'in temel bir parçası olduğu ve basit bir uygulamada gözlemlenmesi en kolay olduğu için activity lifecycle'ına odaklanırsınız. Fragment lifecycle'ı için ilgili diyagram:

![fragment lifecycle](https://developer.android.com/codelabs/kotlin-android-training-lifecycles-logging/img/8494ec955ce0c49d.png)

Bu callbacklerin ne zaman çağrıldığını ve her bir callback metodunda ne yapılması gerektiğini bilmek önemlidir. Ancak bu diyagramların her ikisi de karmaşıktır ve kafa karıştırıcı olabilir. Bu dökümanda, her bir state'in ve callback'in ne anlama geldiğini okumak yerine, bazı dedektiflik çalışmaları yapacak ve neler olup bittiğine dair anlayışınızı geliştireceksiniz.

### Adım 1: onCreate() metodunu inceleyin ve log kaydı ekleyin

Android lifecycle'ında neler olup bittiğini anlamak için çeşitli lifecycle metotlarının ne zaman çağrıldığını bilmek yardımcı olur. Bu, DessertClicker'da işlerin nerede yanlış gittiğini bulmanıza yardımcı olacaktır.

Bunu yapmanın basit bir yolu, Android logging API'sini kullanmaktır. Loglama, uygulama çalışırken bir konsola kısa mesajlar yazmanıza olanak tanır ve bunu, farklı callbackler tetiklendiğinde görmek için kullanabilirsiniz.

1. DessertClicker başlangıç uygulamasını indirin ve Android Studio'da açın.
2. Uygulamayı derleyin ve çalıştırın ve tatlı resmine birkaç kez dokunun. **Satılan Tatlılar**ın değerinin ve toplam dolar tutarının nasıl değiştiğine dikkat edin.
3.` MainActivity.kt`'yi açın ve bu activity için `onCreate()` metodunu inceleyin

```

override fun onCreate(savedInstanceState: Bundle?) {
...
}

```

Activity lifecycle diyagramında, bu callback'i daha önce kullandığınız için `onCreate()` metodunu tanımış olabilirsiniz. Her activity'nin uygulaması gereken tek metottur. `onCreate()` metodu, activity'niz için tek seferlik initializationlar yapmanız gereken yerdir. Örneğin, `onCreate()`'de layout'u inflate edersiniz, click listenerları tanımlarsınız veya data binding'i ayarlarsınız.

![onCreate](https://developer.android.com/codelabs/kotlin-android-training-lifecycles-logging/img/9be2255ff49e0af8.png)

`onCreate()` lifecycle metodu, activity başlatıldıktan hemen sonra (yeni `Activity` nesnesi bellekte oluşturulduğunda) bir kez çağrılır. `onCreate()` yürütüldükten sonra activity yaratılmış olarak kabul edilir.

>**Not:** `onCreate()` metodu bir override'dır. Herhangi bir lifecycle metodunu override ederseniz, hemen `super.onCreate()` öğesini çağırmalısınız.

4. `onCreate()` metodunda, `super.onCreate()` çağrısından hemen sonra aşağıdaki satırı ekleyin. Gerekirse `Log` class'ını içe aktarın. (Mac'te `Alt+Enter` veya `Option+Enter` tuşlarına basın ve **Import**'u seçin.)

```

Log.i("MainActivity", "onCreate çağrıldı")

```

[Log](https://developer.android.com/reference/kotlin/android/util/Log) class'ı, Logcat'e mesajlar yazar. Bu komutun üç bölümü vardır:

- Log mesajının _ciddiyeti_, yani mesajın ne kadar önemli olduğu. Bu durumda [`Log.i()`](https://developer.android.com/reference/kotlin/android/util/Log#i(kotlin.String,%20kotlin.String)) metodu bir bilgi mesajı yazar. `Log` class'ındaki diğer metotlar, hatalar için [`Log.e()`](https://developer.android.com/reference/kotlin/android/util/Log#e(kotlin.String,%20kotlin.String)) veya uyarılar için [`Log.w()`](https://developer.android.com/reference/kotlin/android/util/Log#w(kotlin.String,%20kotlin.String)) içerir.
- Log _tag_'i, bu durumda `"MainActivity"`. Tag, log iletilerinizi Logcat'te daha kolay bulmanızı sağlayan bir string'dir. Tag genellikle class'ın adıdır.
- Gerçek log _mesajı_, kısa bir string, bu durumda `"onCreate çağrıldı"`.

5. DessertClicker uygulamasını derleyin ve çalıştırın. Tatlıya dokunduğunuzda uygulamada herhangi bir davranış farkı görmüyorsunuz. Android Studio'da ekranın alt kısmındaki **Logcat** sekmesine tıklayın.

![Logcat](https://developer.android.com/codelabs/kotlin-android-training-lifecycles-logging/img/ff9c50376701877f.png)

Logcat, mesajların log'a kaydedildiği konsoldur. `Log.i()` metodu veya diğer `Log` class'ı metotlarıyla log'a açıkça gönderdiğiniz mesajlar da dahil olmak üzere, Android'den uygulamanızla ilgili mesajlar burada görünür. 

6. **Logcat** bölmesinde, arama alanına `I/MainActivity` yazın.

![Logcat search](https://developer.android.com/codelabs/kotlin-android-training-lifecycles-logging/img/f5c091e2b480edf8.png)

Logcat, çoğu sizin için yararlı olmayan birçok mesaj içerebilir. Logcat girişlerini birçok şekilde filtreleyebilirsiniz, ancak arama yapmak en kolayıdır. `MainActivity`'yi kodunuzda log tag'i olarak kullandığınız için, log'u filtrelemek için bu tag'i kullanabilirsiniz. Başta `I/` eklemek, bunun `Log.i()` tarafından oluşturulan bir bilgi mesajı olduğu anlamına gelir.

Log mesajınız tarih ve saati, paketin adını (`com.example.android.dessertclicker`), log tag'inizi (başlangıçta `I/` ile) ve asıl mesajı içerir. Bu mesaj log'da göründüğü için `onCreate()` öğesinin yürütüldüğünü bilirsiniz.

### Adım 2: onStart() metodunu uygulayın

onStart() lifecycle metodu, onCreate()'den hemen sonra çağrılır. onStart() çalıştırıldıktan sonra activity'niz ekranda görünür. Activity'nizi başlatmak için yalnızca bir kez çağrılan onCreate()'den farklı olarak onStart(), activity'nizin lifecycle'ında birçok kez çağrılabilir.

![onStart](https://developer.android.com/codelabs/kotlin-android-training-lifecycles-logging/img/385df4ce82ae2de9.png)

onStart() öğesinin karşılık gelen bir onStop() lifecycle metoduyla eşleştirildiğini unutmayın. Kullanıcı uygulamanızı başlatır ve ardından cihazın ana ekranına dönerse activity durdurulur ve artık ekranda görünmez.

1. Android Studio'da `MainActivity.kt` açıkken **Code > Override Methods**'ı seçin veya `Control+o` tuşlarına basın. Bu class'ta override edebileceğiniz tüm metotların büyük bir listesini içeren bir iletişim kutusu görüntülenir.

![override methods](https://developer.android.com/codelabs/kotlin-android-training-lifecycles-logging/img/e1f2460242b2ae.png)

2. Doğru metodu aramak için `onStart`'a girmeye başlayın. Bir sonraki eşleşen öğeye gitmek için aşağı oku kullanın. Listeden `onStart()` öğesini seçin ve boilerplate override kodunu eklemek için **OK**'a tıklayın. Kod şöyle görünür:

```

override fun onStart() {
   super.onStart()
}

```

>**İpucu:** Android Studio, override edilen metot kodunuzu class'ta bir sonraki uygun yere ekler. Lifecycle overridelarını belirli bir yere (class'ın sonundaki gibi) koymak istiyorsanız, **Override Methods**'ı kullanmadan önce insertion noktasını ayarlayın.

3. `onStart()` metodunun içine bir log mesajı ekleyin:

```

override fun onStart() {
   super.onStart()

   Log.i("MainActivity", "onStart Called")
}

```

4. DessertClicker uygulamasını derleyin ve çalıştırın ve **Logcat** bölmesini açın. Log'u filtrelemek için arama alanına `I/MainActivity` yazın. Hem `onCreate()` hem de `onStart()` metotlarının arka arkaya çağrıldığına ve activity'nizin ekranda göründüğüne dikkat edin.
5. Cihazdaki Ana Ekran düğmesine basın ve ardından activity'ye geri dönmek için recents ekranını kullanın. Activity'nin aynı değerlerle kaldığı yerden devam ettiğine ve `onStart()` öğesinin Logcat'e ikinci kez kaydedildiğine dikkat edin. Ayrıca `onCreate()` metodunun genellikle tekrar çağrılmadığına dikkat edin.

>**Not:** Cihazınızla denemeler yaparken ve lifecycle callbacklerini gözlemlerken, cihazınızı döndürdüğünüzde olağandışı davranışlar fark edebilirsiniz. Bir sonraki dökümanda bu davranış hakkında bilgi edineceksiniz.

## <a name="b"></a>Aşama 2 : Log kaydı için Timber kullanın
## <a name="c"></a>Aşama 3 : Lifecycle kullanım örneklerini keşfedin
## <a name="d"></a>Aşama 4 : Fragment lifecycle'ı keşfedin
