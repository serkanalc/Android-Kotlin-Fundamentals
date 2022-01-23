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

```kotlin

override fun onCreate(savedInstanceState: Bundle?) {
...
}

```

Activity lifecycle diyagramında, bu callback'i daha önce kullandığınız için `onCreate()` metodunu tanımış olabilirsiniz. Her activity'nin uygulaması gereken tek metottur. `onCreate()` metodu, activity'niz için tek seferlik initializationlar yapmanız gereken yerdir. Örneğin, `onCreate()`'de layout'u inflate edersiniz, click listenerları tanımlarsınız veya data binding'i ayarlarsınız.

![onCreate](https://developer.android.com/codelabs/kotlin-android-training-lifecycles-logging/img/9be2255ff49e0af8.png)

`onCreate()` lifecycle metodu, activity başlatıldıktan hemen sonra (yeni `Activity` nesnesi bellekte oluşturulduğunda) bir kez çağrılır. `onCreate()` yürütüldükten sonra activity yaratılmış olarak kabul edilir.

>**Not:** `onCreate()` metodu bir override'dır. Herhangi bir lifecycle metodunu override ederseniz, hemen `super.onCreate()` öğesini çağırmalısınız.

4. `onCreate()` metodunda, `super.onCreate()` çağrısından hemen sonra aşağıdaki satırı ekleyin. Gerekirse `Log` class'ını içe aktarın. (Mac'te `Alt+Enter` veya `Option+Enter` tuşlarına basın ve **Import**'u seçin.)

```kotlin

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

```kotlin

override fun onStart() {
   super.onStart()
}

```

>**İpucu:** Android Studio, override edilen metot kodunuzu class'ta bir sonraki uygun yere ekler. Lifecycle overridelarını belirli bir yere (class'ın sonundaki gibi) koymak istiyorsanız, **Override Methods**'ı kullanmadan önce insertion noktasını ayarlayın.

3. `onStart()` metodunun içine bir log mesajı ekleyin:

```kotlin

override fun onStart() {
   super.onStart()

   Log.i("MainActivity", "onStart Called")
}

```

4. DessertClicker uygulamasını derleyin ve çalıştırın ve **Logcat** bölmesini açın. Log'u filtrelemek için arama alanına `I/MainActivity` yazın. Hem `onCreate()` hem de `onStart()` metotlarının arka arkaya çağrıldığına ve activity'nizin ekranda göründüğüne dikkat edin.
5. Cihazdaki Ana Ekran düğmesine basın ve ardından activity'ye geri dönmek için recents ekranını kullanın. Activity'nin aynı değerlerle kaldığı yerden devam ettiğine ve `onStart()` öğesinin Logcat'e ikinci kez kaydedildiğine dikkat edin. Ayrıca `onCreate()` metodunun genellikle tekrar çağrılmadığına dikkat edin.

>**Not:** Cihazınızla denemeler yaparken ve lifecycle callbacklerini gözlemlerken, cihazınızı döndürdüğünüzde olağandışı davranışlar fark edebilirsiniz. Bir sonraki dökümanda bu davranış hakkında bilgi edineceksiniz.

## <a name="b"></a>Aşama 2 : Log kaydı için Timber kullanın

Bu aşamada, uygulamanızı `Timber` adlı popüler bir log kütüphanesi kullanacak şekilde değiştirirsiniz. `Timber`, yerleşik Android `Log` class'ına göre çeşitli avantajlara sahiptir. Özellikle, `Timber` kütüphanesi:

- Class adına göre sizin için log tag'i oluşturur.
- Android uygulamanızın yayınlanmış bir sürümünde logların gösterilmesini önlemenize yardımcı olur. (Uygulamanızı yayınlarken Log ifadelerini manuel olarak silmeniz gerekmeyecektir.)
- Crash raporlama kütüphaneleri ile entegrasyona izin verir.

İlk faydayı hemen göreceksiniz; diğerlerini, daha büyük uygulamalar hazırlarken ve gönderirken takdir edeceksiniz.

### Adım 1: Gradle'a Timber'ı ekleyin

1. GitHub'daki [Timber projesi](https://github.com/JakeWharton/timber#download)ne giden bu bağlantıyı ziyaret edin ve `implementation` kelimesiyle başlayan **Download** başlığı altındaki kod satırını kopyalayın. Versiyon numarası farklı olsa da, kod satırı şöyle görünecektir.

```kotlin   

implementation 'com.jakewharton.timber:timber:4.7.1'

```   

2. Android Studio'da, Proje: Android görünümünde **Gradle Scripts**'i genişletin ve **build.gradle (Module: app)** dosyasını açın.
3. Dependencies bölümünün içine kopyaladığınız kod satırını yapıştırın.

```kotlin   

dependencies {
   ...
   implementation 'com.jakewharton.timber:timber:4.7.1'
}

```   

4. Gradle'ı rebuild etmek için Android Studio'nun sağ üst köşesindeki **Sync Now** bağlantısını tıklayın. Derleme hatasız yürütülmelidir.

### Adım 1: Bir Application class'ı oluşturun ve Timber'ı initialize edin

Bu adımda, bir `Application` class'ı oluşturacaksınız. `Application`, uygulamanızın tamamı için genel uygulama durumunu içeren bir temel class'tır. Ayrıca, işletim sisteminin uygulamanızla etkileşim kurmak için kullandığı ana nesnedir. Bir tane belirtmezseniz Android'in kullandığı varsayılan bir `Application` class'ı vardır, bu nedenle uygulamanız için her zaman, onu oluşturmak için özel bir şey yapmanıza gerek kalmadan oluşturulan bir `Application` nesnesi vardır.

`Timber`, `Application` class'ını kullanır çünkü tüm uygulama bu log kütüphanesini kullanacaktır ve diğer her şey ayarlanmadan önce kütüphanenin bir kez initialize edilmesi gerekir. Bu gibi durumlarda, `Application` class'ını ssubclass haline getirebilir ve kendi özel implementation'ınızla varsayılanları override edebilirsiniz.

>**Uyarı:** `Application` class'ına kendi kodunuzu eklemek cazip gelebilir, çünkü class tüm activitylerinizden önce oluşturulur ve global duruma sahip olabilir. Ancak, küresel olarak kullanılabilen okunabilir ve yazılabilir statik değişkenler yapmanın hataya açık olması gibi, `Application` class'ını kötüye kullanmak da kolaydır. Kod gerçekten gerekli olmadıkça, `Application` class'ına herhangi bir activity kodu koymaktan kaçının.

`Application` class'ınızı oluşturduktan sonra, Android manifest'te class'ı belirtmeniz gerekir.

1. `dessertclicker` paketinde `ClickerApplication` adında yeni bir Kotlin class'ı oluşturun. Bunu yapmak için **app > java**'yı genişletin ve **com.example.android.dessertclicker**'a sağ tıklayın. **New > Kotlin File/Class**'ı seçin.
2. Class'a **ClickerApplication** adını verin ve **Kind**'ını **Class** olarak verin. **OK**'a tıklayın.

Android Studio, yeni bir `ClickerApplication` class'ı oluşturur ve onu kod editörde açar. Kod şöyle görünür:

```kotlin   

package com.example.android.dessertclicker

class ClickerApplication {
}

```   

3. Class tanımını `Application`'ın bir subclass'ı olacak şekilde değiştirin ve gerekirse `Application` class'ını import edin.

```kotlin  

class ClickerApplication : Application() {

```   

4. `onCreate()` metodunu override etmek için, **Code > Override Methods**'ı seçin veya `Control+o`'ya basın.

```kotlin   

class ClickerApplication : Application() {
   override fun onCreate() {
       super.onCreate()
   }
}

```   

5. `onCreate()` metodunun içinde, `Timber` kütüphanesini initialize edin:

```kotlin

override fun onCreate() {
    super.onCreate()

    Timber.plant(Timber.DebugTree())
}

```

Bu kod satırı, kütüphaneyi activitylerinizdde kullanabilmeniz için, uygulamanız için `Timber` kütüphanesini başlatır.

6. **AndroidManifest.xml**'i açın.
7. `<application>` öğesinin üstüne, `ClickerApplication` class'ı için yeni bir özellik ekleyin, böylece Android, varsayılan class yerine `Application` class'ınızı kullanmayı bilsin.

```kotlin

<application
   android:name=".ClickerApplication"
...

```

>**Not:** Özel (Custom) `Application` class'ınızı Android manifest'e eklemezseniz uygulamanız hatasız çalışır. Ancak, uygulama class'ınızı kullanmaz ve `Timber`'dan hiçbir log bilgisi görmezsiniz.

### Adım 3: Timber log ifadelerini ekleyin

Bu adımda, `Log.i()` çağrılarınızı `Timber` kullanacak şekilde değiştireceksiniz, ardından diğer tüm lifecycle metotları için log'a kaydetmeyi uygulayacaksınız.

1. `MainActivity`'yi açın ve `onCreate()`'e gidin. `Log.i()`'yi `Timber.i()` ile değiştirin ve log tag'ini kaldırın.

```kotlin

Timber.i("onCreate called")

```

`Log` class'ı gibi, `Timber` da bilgi mesajları için `i()` metodunu kullanır. `Timber` ile bir log tag'i eklemeniz gerekmediğine dikkat edin; `Timber`, log tag'i olarak class'ın adını otomatik olarak kullanır.

2. Benzer şekilde, `Log` çağrısını `onStart()` içinde de değiştirin:

```kotlin

override fun onStart() {
   super.onStart()

   Timber.i("onStart Called")
}

```

3. DessertClicker uygulamasını derleyin ve çalıştırın ve Logcat'i açın. `onCreate()` ve `onStart()` için hala aynı log mesajlarını gördüğünüze dikkat edin, ancak şimdi bu mesajları `Log` class'ı değil, `Timber` oluşturuyor.
3. MainActivity'nizdeki lifecycle metotlarının geri kalanını override edin ve her biri için Timber log ifadeleri ekleyin. İşte kod:

```kotlin

override fun onResume() {
   super.onResume()
   Timber.i("onResume Called")
}

override fun onPause() {
   super.onPause()
   Timber.i("onPause Called")
}

override fun onStop() {
   super.onStop()
   Timber.i("onStop Called")
}

override fun onDestroy() {
   super.onDestroy()
   Timber.i("onDestroy Called")
}

override fun onRestart() {
   super.onRestart()
   Timber.i("onRestart Called")
}

```

5. DessertClicker'ı tekrar derleyin ve çalıştırın ve Logcat'i inceleyin. Bu sefer `onCreate()` ve `onStart()`'a ek olarak, `onResume()` lifecycle callback'i için bir log mesajı olduğuna dikkat edin.

![Logcat](https://developer.android.com/codelabs/kotlin-android-training-lifecycles-logging/img/3b61071b3c4334a4.png)

Bir activity sıfırdan başladığında, bu lifecycle callbacklerinin üçünün de sırayla çağrıldığını görürsünüz:

- Uygulamayı oluşturmak için `onCreate()`
- Uygulamayı başlatmak ve ekranda görünür yapmak için `onStart()`
- Activity'ye odaklanmak ve onu kullanıcının onunla etkileşime girmesi için hazır hale getirmek için `onResume()`

Adına rağmen, devam ettirilecek bir şey olmasa bile başlangıçta `onResume()` metodu çağrılır.

![lifecyle diagram](https://developer.android.com/codelabs/kotlin-android-training-lifecycles-logging/img/160054d59f67519.png)

## <a name="c"></a>Aşama 3 : Lifecycle kullanım örneklerini keşfedin

Artık DessertClicker uygulaması log'a kaydetme için ayarlandığına göre, uygulamayı çeşitli şekillerde kullanmaya ve bu kullanımlara yanıt olarak  lifecycle callbacklerin nasıl tetiklendiğini keşfetmeye hazırsınız.

### Kullanım durumu 1: Activity'yi açma ve kapama

Uygulamanızı ilk kez başlatmak ve ardından uygulamayı tamamen kapatmak olan en temel kullanım durumuyla başlayacaksınız.

1. Zaten çalışmıyorsa, DessertClicker uygulamasını derleyin ve çalıştırın. Gördüğünüz gibi `onCreate()`, `onStart()` ve `onResume()` callbackleri, activity ilk kez başladığında çağrılır.

![Logcat](https://developer.android.com/codelabs/kotlin-android-training-lifecycles-logging/img/3b61071b3c4334a4.png)

2. Cupcake'e birkaç kez tıklayın.
3. Cihazdaki Geri düğmesine dokunun. Logcat'te `onPause()`, `onStop()` ve `onDestroy()`'un bu sırayla çağrıldığına dikkat edin.

![Logcat](https://developer.android.com/codelabs/kotlin-android-training-lifecycles-logging/img/524b1d0c9a584f37.png)

Bu durumda, Geri düğmesinin kullanılması activity'nin (ve uygulamanın) tamamen kapanmasına neden olur. `onDestroy()` metodunun yürütülmesi, activity!nin tamamen kapatıldığı ve çöplerin toplanabileceği (garbage-collection) anlamına gelir. [Garbage collection](https://en.wikipedia.org/wiki/Garbage_collection_(computer_science)), artık kullanmayacağınız nesnelerin otomatik olarak temizlenmesini ifade eder. `onDestroy()` çağrıldıktan sonra, işletim sistemi bu kaynakların atılabilir olduğunu bilir ve bu belleği temizlemeye başlar.

![activity diagram](https://developer.android.com/codelabs/kotlin-android-training-lifecycles-logging/img/2dcc4d9c6478a9f4.png)

Ayrıca, kodunuz activity'nin [`finish()`](https://developer.android.com/reference/android/app/Activity.html#finish()) metodunu manuel olarak çağırırsa veya kullanıcı uygulamadan zorla çıkarsa, activity'niz tamamen kapatılabilir. (Örneğin, kullanıcı, pencerenin köşesindeki **X** işaretini tıklayarak recents ekranında uygulamadan çıkmaya zorlayabilir.) Uygulamanız uzun süredir ekranda değilse, Android sistemi activity'nizi kendi kendine kapatabilir. Android bunu pili korumak ve uygulamanızın kaynaklarının diğer uygulamalar tarafından kullanılmasına izin vermek için yapar.

4. Uygulamaya geri dönmek için recents ekranını kullanın. İşte Logcat:

![Logcat](https://developer.android.com/codelabs/kotlin-android-training-lifecycles-logging/img/fb7d1239f699fba4.png)

Activity önceki adımda yok edildi, bu nedenle uygulamaya döndüğünüzde Android yeni bir activity başlatır ve `onCreate()`, `onStart()` ve `onResume()` metotlarını çağırır. Önceki activity'den sipariş ettiğiniz tatlı öğelerinin sayısı ve toplam fiyatının korunmadığına dikkat edin. Sıfırlanırlar. Bu davranışı uygulamanızda istemeyebilirsiniz. Gelecekteki bir dökümanda bu sorunu çözeceğiz.

Buradaki kilit nokta, `onCreate()` ve `onDestroy()`'un tek bir activity instance'ının ömrü boyunca yalnızca bir kez çağrılmasıdır: uygulamayı ilk kez başlatmak için `onCreate()` ve uygulamanız tarafından kullanılan kaynakları temizlemek için `onDestroy()`.

`onCreate()` metodu önemli bir adımdır; Burası, tüm initialization'ınızın gittiği, düzeni ilk kez inflate ederek kurduğunuz ve değişkenlerinizi initialize ettiğiniz yerdir.

### Kullanım durumu 2: Activity'den uzaklaşma ve activity'ye geri dönme

Uygulamayı başlattığınıza ve tamamen kapattığınıza göre, activity'nin ilk kez oluşturulduğu zamana ilişkin lifecycle durumlarının çoğunu gördünüz. Ayrıca, activity'nin tamamen kapatılıp yok edildiğinde geçtiği tüm lifecycle durumlarını da gördünüz. Ancak kullanıcılar Android destekli cihazlarıyla etkileşime girdikçe, uygulamalar arasında geçiş yapma, home'a dönme, yeni uygulamalar başlatma ve telefon görüşmeleri gibi diğer harici etkinliklerin kesintilerini giderme gibi çeşitli eylemler gerçekleştirirler.

Activity'niz, kullanıcı bu activity'den her uzaklaştığında tamamen kapanmaz:

- Activity'niz artık ekranda görünmediğinde, bu, activity'yi arka plana (_background_) almak olarak bilinir. (Bunun tersi, activity'nin ön planda [`foreground`] veya ekranda olduğu zamandır.)
- Kullanıcı uygulamanıza döndüğünde aynı activity yeniden başlatılır ve tekrar görünür hale gelir. Lifecycle'ın bu bölümüne, uygulamanın _visible_ (görünür) _lifecycle_ denir.

Uygulamanız arka plandayken sistem kaynaklarını ve pil ömrünü korumak için aktif olarak çalışmamalıdır. Devam eden işlemleri duraklatabilmeniz için uygulamanızın ne zaman arka plana geçtiğini bilmek için `Activity` lifecycle'ı ve callbackleri kullanırsınız. Ardından uygulamanız ön plana çıkınca bu işlemleri yeniden başlatırsınız.

Örneğin, büyük ölçüde bilgi işlem kaynaklarına bağımlı bir uygulama düşünün. Bu uygulama, cihazınızın CPU'sunu kullanarak birçok hesaplama yapabilir. İşlem gücü ve pil ömrü genellikle bir mobil cihazda sınırlıdır, bu nedenle Android runtime sisteminin kaynakları dengelemesi gerekir. Arka planda yapılan işlemler performansı yavaşlatabileceğinden veya telefonun pilini zamanından önce bitirebileceğinden, Android ön planda çalışmayan uygulamalar için kaynak kullanımını durdurabilir.

Bu sonraki adımda, uygulama arka plana geçtiğinde ve tekrar ön plana döndüğünde activity lifecycle'a bakacaksınız.

1. DessertClicker uygulaması çalışırken, cupcake'e birkaç kez tıklayın.
2. Cihazınızdaki Home düğmesine basın ve Android Studio'da Logcat'i gözlemleyin. `onPause()` metodunun ve `onStop()` metotlarının çağrıldığına, ancak `onDestroy()`'un çağrılmadığına dikkat edin. Ana ekrana dönmek, uygulamayı tamamen kapatmak yerine uygulamanızı arka plana koyar.

![Logcat](https://developer.android.com/codelabs/kotlin-android-training-lifecycles-logging/img/38710e0d2c4d1910.png)

`onPause()` çağrıldığında, uygulamanın artık odağı yoktur. `onStop()`'tan sonra uygulama artık ekranda görünmez. Activity durdurulmuş olsa da `Activity` nesnesi hala arka planda hafızadadır. Activity yok edilmedi. Kullanıcı uygulamaya geri dönebilir, bu nedenle Android, activity kaynaklarınızı etrafta tutar.

![lifecycle diagram](https://developer.android.com/codelabs/kotlin-android-training-lifecycles-logging/img/b488b32801220b79.png)

3. Uygulamaya geri dönmek için recents ekranını kullanın. Logcat'te activity'nin `onRestart()` ve `onStart()` ile yeniden başlatıldığına ve ardından `onResume()` ile devam ettirildiğine dikkat edin.

![Logcat](https://developer.android.com/codelabs/kotlin-android-training-lifecycles-logging/img/f6275abeaa53abe4.png)

Activity ön plana döndüğünde `onCreate()` metodu tekrar çağrılmaz. Activity nesnesi yok edilmedi, dolayısıyla yeniden yaratılması gerekmiyor. `onCreate()` yerine `onRestart()` metodu çağrılır. Bu sefer activity ön plana döndüğünde **Satılan Tatlılar** numarasının korunduğuna dikkat edin.

4. DessertClicker dışında en az bir uygulama başlatın, böylece cihazın recents ekranında birkaç uygulama bulunur.
5. Recents ekranını açın ve başka bir son kullanılan etkinliği açın. Ardından en son uygulamalara geri dönün ve DessertClicker'ı ön plana geri getirin.

Home düğmesine bastığınızda olduğu gibi burada Logcat'te aynı callbackleri gördüğünüze dikkat edin. uygulama arka plana geçtiğinde `onPause()` ve `onStop()`, geri geldiğinde `onRestart()`, `onStart()` ve `onResume()` çağrılır.

Buradaki önemli nokta, kullanıcı activity'ye gidip gelirken `onStart()` ve `onStop()`'un birden çok kez çağrılmasıdır. Uygulamayı arka plana geçtiğinde durdurmak veya ön plana döndüğünde yeniden başlatmak için bu metotları override etmelisiniz.

Peki ya `onRestart()`? `onRestart()` metodu, `onCreate()` metoduna çok benzer. Activity görünür hale gelmeden önce onCreate() veya `onRestart()` çağrılır. `onCreate()` metodu yalnızca ilk kez çağrılır ve bundan sonra `onRestart()` çağrılır. `onRestart()` metodu, yalnızca activity'bniz ilk kez başlatılmıyorsa çağırmak istediğiniz kodu koyabileceğiniz bir yerdir.

### Kullanım durumu 3: Activity'yi kısmen gizleme

Bir uygulama başlatıldığında ve `onStart()` çağrıldığında, uygulamanın ekranda görünür hale geldiğini öğrendiniz. Uygulama devam ettirildiğinde ve `onResume()` çağrıldığında, uygulama kullanıcı odağını kazanır. Uygulamanın tamamen ekranda olduğu ve kullanıcı odaklı olduğu lifecycle kısmına _interactive_ lifecycle denir.

Uygulama arka plana geçtiğinde, `onPause()`'dan sonra odak kaybolur ve uygulama `onStop()`'tan sonra artık görünmez.

Odak (focus) ve görünürlük (visiblity) arasındaki fark önemlidir, çünkü bir activity'nin ekranda `kısmen` görünmesi, ancak kullanıcının odağına sahip olmaması mümkündür. Bu adımda, bir activity'nin kısmen görünür olduğu ancak kullanıcı odaklı olmadığı bir duruma bakacaksınız.

1. DessertClicker uygulaması çalışırken, ekranın sağ üst köşesindeki **Paylaş** düğmesine tıklayın.

![app](https://developer.android.com/codelabs/kotlin-android-training-lifecycles-logging/img/9ddc8b1dc79b1bff.png)

Paylaşım activity'si ekranın alt yarısında görünür, ancak activity üst yarıda görünmeye devam eder.

2. Logcat'i inceleyin ve yalnızca `onPause()` öğesinin çağrıldığını unutmayın.

![Logcat](https://developer.android.com/codelabs/kotlin-android-training-lifecycles-logging/img/cf96ef14999a9a3c.png)

Bu kullanım durumunda, activity hala kısmen görünür olduğundan `onStop()` çağrılmaz. Ancak activity'nin kullanıcı odaklılığı yoktur ve kullanıcı onunla etkileşime giremez. Ön planda olan "paylaş" activity'si, kullanıcı odaklıdır.

Bu fark neden önemlidir? Hesaplama açısından yoğun uygulamamızı önceden düşünün. Uygulamanın arka plandayken durmasını, ancak uygulama kısmen gizlendiğinde çalışmaya devam etmesini isteyebilirsiniz. Bu durumda onu `onStop()` ile sonlandıracaksınız. Uygulamanın, activity kısmen gizlendiğinde de durmasını istiyorsanız, uygulamayı sonlandırmak için kodu `onPause()` içine koyarsınız.

`onPause()` içinde çalışan kod ne olursa olsun, diğer şeylerin görüntülenmesini engeller, bu nedenle kodu `onPause()`'da hafif tutun. Örneğin, bir telefon araması gelirse, `onPause()` içindeki kod gelen arama bildirimini geciktirebilir.

3. Uygulamaya geri dönmek için paylaşım iletişim kutusunun dışına tıklayın ve `onResume()` öğesinin çağrıldığına dikkat edin.

Hem `onResume()` hem de `onPause()` odakla ilgilidir. `onResume()` metodu, activity'ye odaklanıldığında çağrılır ve activity odağı kaybettiğinde `onPause()` çağrılır.

## <a name="d"></a>Aşama 4 : Fragment lifecycle'ı keşfedin

Android fragment lifecycle, activity lifecycle'a benzer ve ek oalrak fragment'a özgü birkaç metodu vardır.

![fragment lifecycle diagram](https://developer.android.com/codelabs/kotlin-android-training-lifecycles-logging/img/8494ec955ce0c49d.png)

Bu aşamada, önceki aşamalarda oluşturduğunuz AndroidTrivia uygulamasına bakacaksınız ve fragment lifecycle'ı keşfetmek için bazı loglar ekleyeceksiniz. AndroidTrivia uygulaması, Android geliştirmeyle ilgili soruları yanıtlamanıza olanak tanır; Eğer arka arkaya üç doğru cevap verirseniz oyunu kazanırsınız.

AndroidTrivia uygulamasındaki her ekran bir `Fragment`'tır.

İşleri basit tutmak için bu görevde Timber kütüphanesi yerine Android log kaydı API'ını kullanırsınız.

1. Önceki dökümanlarımızın birinden AndroidTriviaNavigation uygulamasını açın veya GitHub'dan AndroidTriviaNavigation çözüm kodunu indirin.
2. `TitleFragment.kt` dosyasını açın. Siz uygulamayı yeniden oluşturana kadar Android Studio'nun binding hataları ve unresolved-reference hataları gösterebileceğini unutmayın.
3. `onCreateView()` metoduna ilerleyin. Fragment'ın layout'unun inflate edildiğine ve data binding'in burada gerçekleştiğine dikkat edin.
4. `onCreateView()` metoduna, `setHasOptionsMenu()` satırı ile döndürülecek son çağrı arasına bir log ifadesi ekleyin:

```kotlin

setHasOptionsMenu(true)

Log.i("TitleFragment", "onCreateView called")

return binding.root

```

5. onCreateView() metodunun hemen altına, kalan fragment lifecycle metotlarının her biri için log ifadeleri ekleyin. İşte kod:

```kotlin

override fun onAttach(context: Context) {
   super.onAttach(context)
   Log.i("TitleFragment", "onAttach called")
}
override fun onCreate(savedInstanceState: Bundle?) {
   super.onCreate(savedInstanceState)
   Log.i("TitleFragment", "onCreate called")
}

override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    Log.i("TitleFragment", "onViewCreated called")
}

override fun onStart() {
   super.onStart()
   Log.i("TitleFragment", "onStart called")
}
override fun onResume() {
   super.onResume()
   Log.i("TitleFragment", "onResume called")
}
override fun onPause() {
   super.onPause()
   Log.i("TitleFragment", "onPause called")
}
override fun onStop() {
   super.onStop()
   Log.i("TitleFragment", "onStop called")
}
override fun onDestroyView() {
   super.onDestroyView()
   Log.i("TitleFragment", "onDestroyView called")
}
override fun onDetach() {
   super.onDetach()
   Log.i("TitleFragment", "onDetach called")
}

```

6. Uygulamayı derleyin ve çalıştırın ve Logcat'i açın.
7. Log'u filtrelemek için arama alanına `I/TitleFragment` yazın. Uygulama başladığında, Logcat aşağıdaki gibi görünecektir:

```

21933-21933/com.example.android.navigation I/TitleFragment: onAttach called
21933-21933/com.example.android.navigation I/TitleFragment: onCreate called
21933-21933/com.example.android.navigation I/TitleFragment: onCreateView called
21933-21933/com.example.android.navigation I/TitleFragment: onViewCreated called
21933-21933/com.example.android.navigation I/TitleFragment: onStart called
21933-21933/com.example.android.navigation I/TitleFragment: onResume called

```

Burada, bu callbackler dahil, fragment'ın tüm başlangıç lifecycle'ını görebilirsiniz:

- `onAttach()`: Fragment, sahip activity'si ile ilişkilendirildiğinde çağrılır.
- `onCreate()`: Activity için `onCreate()`'e benzer şekilde, fragment için `onCreate()` ilk fragment oluşturmayı (layout dışında) yapmak üzere çağrılır.
- `onCreateView()`: Fragment'ın layout'ını inflate etmek için çağrılır.
- `onViewCreated()`: `onCreateView()` döndürüldükten hemen sonra, ancak kaydedilen herhangi bir state view'a geri yüklenmeden önce çağrılır.
- `onStart()`: Fragment görünür hale geldiğinde çağrılır; activity'nin `onStart()` metoduna paralel.
- `onResume()`: Fragment kullanıcı odağını kazandığında çağrılır; activity'nin `onResume()` metoduna paralel.

8. Trivia oyununa devam etmek için **Oyna** butonuna dokunun ve Logcat'i şimdi fark edin.

```

21933-21933/com.example.android.navigation I/TitleFragment: onAttach called
21933-21933/com.example.android.navigation I/TitleFragment: onCreate called
21933-21933/com.example.android.navigation I/TitleFragment: onCreateView called
21933-21933/com.example.android.navigation I/TitleFragment: onViewCreated called
21933-21933/com.example.android.navigation I/TitleFragment: onStart called
21933-21933/com.example.android.navigation I/TitleFragment: onResume called
21933-21933/com.example.android.navigation I/TitleFragment: onPause called
21933-21933/com.example.android.navigation I/TitleFragment: onStop called
21933-21933/com.example.android.navigation I/TitleFragment: onDestroyView called

```

Sonraki fragment'ın açılması, başlık (title) fragment'ının kapanmasına ve bu lifecycle metotlarının çağrılmasına neden olur:

- `onPause()`: Fragment kullanıcı odağını kaybettiğinde çağrılır; activity'nin `onPause()` metoduna paralel.
- `onStop()`: Fragment artık ekranda görünmediğinde çağrılır; activity'nin `onStop()` metoduna paralel.
- `onDestroyView()`: Fragment'ın view'una artık ihtiyaç duyulmadığında, o view ile ilişkili kaynakları temizlemek için çağrılır.

9. Uygulamada, başlık (title) fragment'ına dönmek için Up butonuna (ekranın sol üst köşesindeki ok) dokunun.

```

21933-21933/com.example.android.navigation I/TitleFragment: onPause called
21933-21933/com.example.android.navigation I/TitleFragment: onStop called
21933-21933/com.example.android.navigation I/TitleFragment: onDestroyView called
21933-21933/com.example.android.navigation I/TitleFragment: onCreateView called
21933-21933/com.example.android.navigation I/TitleFragment: onViewCreated called
21933-21933/com.example.android.navigation I/TitleFragment: onStart called
21933-21933/com.example.android.navigation I/TitleFragment: onResume called

```

Bu sefer, fragment'ı başlatmak için muhtemelen `onAttach()` ve `onCreate()` çağrılmadı. Fragment nesnesi hala var ve hala sahip activity'sine bağlı, bu nedenle lifecycle `onCreateView()` ile yeniden başlıyor.

10. Cihazın Ana Ekran (Home) butonuna basın. Logcat'te yalnızca `onPause()` ve `onStop()`'un çağrıldığına dikkat edin. Bu, activity ile aynı davranıştır: ana ekrana dönmek, activity'yi ve fragment'ı arka plana koyar.
11. Uygulamaya geri dönmek için recents ekranını kullanın. Activity'de olduğu gibi, fragment'ı ön plana döndürmek için `onStart()` ve `onResume()` metotları çağrılır.
