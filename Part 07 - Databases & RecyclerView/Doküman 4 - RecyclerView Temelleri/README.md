# <a name="1"></a>RecyclerView Temelleri

- [RecyclerView](#a)
- [RecyclerView ve bir Adapter uygulayın](#b)
- [Tüm uyku datası için bir ViewHolder oluşturun](#c)
- [Kodunuzu geliştirin](#d)

>Not: Önceki codelab üzerinde çalıştıysanız, bu codelab için başlangıç uygulamasını çok tanıdık bulacaksınız. Başlangıç uygulaması, önceki codelab'de oluşturduğunuz uygulama gibi, uyku-gece verilerini kayan bir metin görünümünde (scrolling text view) görüntüler. Ancak, bu codelab için başlangıç uygulaması, bir RecyclerView'un uygulanmasıyla doğrudan ilgili olmayan birçok işten tasarruf etmenizi sağlayacak ek varlıklar ve yardımcı programlar içerir. Bu ders için başlangıç uygulamasını indirip kullandığınızdan emin olun.

Bu aşamada, uyku kalitesini izleyen bir uygulamanın `RecyclerView` bölümünü oluşturursunuz. Uygulama, zaman içinde uyku verilerini depolamak için bir `Room` veritabanı kullanır.

Başlangıç yygulamasının, aşağıdaki şekilde gösterildiği gibi fragmentlarla temsil edilen iki ekranı vardır.

![image](https://developer.android.com/codelabs/kotlin-android-training-recyclerview-fundamentals/img/e28eb795b6812ee4.png)

Solda gösterilen ilk ekranda, izlemeyi başlatmak ve durdurmak için butonlar vardır. Ekran, kullanıcının tüm uyku verilerini gösterir. **Clear** butonu, uygulamanın kullanıcı için topladığı tüm verileri kalıcı olarak siler. Sağda gösterilen ikinci ekran, uyku kalitesi derecelendirmesini seçmek içindir.

Bu uygulama, bir UI controller, `ViewModel` ve `LiveData` ile basitleştirilmiş bir mimari kullanır. Uygulama ayrıca uyku verilerini kalıcı hale (persistent) getirmek için bir `Room` veritabanı kullanır.

![image](https://developer.android.com/codelabs/kotlin-android-training-recyclerview-fundamentals/img/49f975f1e5fe689.png)

İlk ekranda görüntülenen uyku geceleri listesi işlevseldir, ancak güzel değildir. Uygulama, metin görünümü için metin stringleri ve kalite için sayılar oluşturmak üzere karmaşık bir biçimlendirici kullanır. Ayrıca, bu tasarım ölçeklenebilirliğimizi azaltacak şekilde biraz karmaşıktır. Bu codelab'deki tüm bu sorunları giderdikten sonra, son uygulama orijinal uygulamayla aynı işlevselliğe sahiptir ancak geliştirilmiş ana ekranın okunması daha kolaydır:

![image](https://developer.android.com/codelabs/kotlin-android-training-recyclerview-fundamentals/img/76d78f63f88c3c86.png)

## <a name="a"></a>Aşama 1 : RecyclerView

Bir veri listesi veya grid'i görüntülemek, Android'deki en yaygın UI görevlerinden biridir. Listeler basitten, çok karmaşığa değişir. Bir text view listesi, alışveriş listesi gibi basit verileri gösterebilir. Açıklamalı tatil lokasyonları listesi gibi karmaşık bir liste, kullanıcıya başlıklarla birlikte kayan bir grid içinde birçok ayrıntı gösterebilir.

Tüm bu kullanım durumlarını desteklemek için Android, `RecyclerView` widget'ını sağlar.

![image](https://developer.android.com/codelabs/kotlin-android-training-recyclerview-fundamentals/img/643a2240444361ad.png)

`RecyclerView`'un en büyük yararı, büyük listeler için çok verimli olmasıdır:

- Varsayılan olarak, `RecyclerView` yalnızca o anda ekranda görünen öğeleri işlemek veya çizmek için çalışır. Örneğin, listenizde bin öğe varsa ancak yalnızca 10 öğe görünür durumdaysa, `RecyclerView` ekranda yalnızca 10 öğe çizmeye yetecek kadar iş yapar. Kullanıcı sayfayı kaydırdığında, `RecyclerView` ekranda hangi yeni öğelerin olması gerektiğini bulur ve bu öğeleri görüntülemek için yeterli iş yapar.
- Bir öğe ekrandan kaydığında, öğenin görünümleri geri dönüştürülür. Bu, öğenin ekranda kaydırılırken yeni içerikle doldurulduğu anlamına gelir. Bu `RecyclerView` davranışı, çok fazla işlem süresi kazandırır ve listelerin sorunsuz bir şekilde kaydırılmasına yardımcı olur.
- Bir öğe değiştiğinde, tüm listeyi yeniden çizmek yerine `RecyclerView` o öğeyi güncelleyebilir. Bu, karmaşık öğelerin uzun listelerini görüntülerken büyük bir verimlilik kazancıdır!

Aşağıda gösterilen sırada, bir görünümün `ABC` verileriyle doldurulduğunu görebilirsiniz. Bu görünüm ekranı kaydırdıktan sonra, `RecyclerView` yeni veriler için, `XYZ`, görünümü yeniden kullanır.

![image](https://developer.android.com/codelabs/kotlin-android-training-recyclerview-fundamentals/img/dcf4599789b9c2a1.png)

### Adapter pattern'ı

Farklı elektrik prizleri kullanan ülkeler arasında seyahat ediyorsanız, muhtemelen bir adaptör kullanarak cihazlarınızı yabancı prizlere nasıl takabileceğinizi biliyorsunuzdur. Adaptör, iş türünü diğerine dönüştürmenize olanak tanır, yani gerçekte bir interface türünü diğerine dönüştürür.

Yazılım mühendisliğindeki [adapter pattern](https://en.wikipedia.org/wiki/Adapter_pattern) da benzer bir konsept kullanır. Bu pattern, bir class'ın API'ının başka bir API olarak kullanılmasına izin verir. `RecyclerView`, uygulamanın verileri saklama ve işleme şeklini değiştirmeden, uygulama verilerini `RecyclerView`'ın görüntüleyebileceği bir şeye dönüştürmek için bir adaptör kullanır. Sleep-tracker uygulaması için, `Room` veritabanındaki verileri `ViewModel`'ı değiştirmeden `RecyclerView`'un nasıl görüntüleneceğini bildiği bir şeye uyarlayan bir adaptör oluşturursunuz.

### RecyclerView'u uygulamak

![image](https://developer.android.com/codelabs/kotlin-android-training-recyclerview-fundamentals/img/4e9c18b463f00bf7.png)

Verilerinizi bir `RecyclerView`'da görüntülemek için aşağıdaki parçalara ihtiyacınız vardır:

- Görüntülenecek data
- Viewlar için container görevi görmesi için layout dosyanızda tanımlanan bir [`RecyclerView`](https://developer.android.com/reference/kotlin/androidx/recyclerview/widget/RecyclerView) instance'ı.
- Bir veri öğesi için bir layout. Tüm liste öğeleri aynı görünüyorsa, hepsi için aynı layout'u kullanabilirsiniz, ancak bu zorunlu değildir. Öğe layout'u, fragment layout'undan ayrı olarak oluşturulmalıdır, böylece her seferinde bir öğe view'u oluşturulabilir ve verilerle doldurulabilir.
- Bir layout manager. Layout manager, bir view'da UI bileşenlerinin organizasyonunu (layout'ını) yönetir.
- Bir view holder. View holder, `ViewHolder` class'ını extend eder. Öğe layout'ından bir öğeyi görüntülemek için view bilgilerini içerir. Vİew holderlar ayrıca, `RecyclerView`'un viewları ekranda verimli bir şekilde taşımak için kullandığı bilgileri de ekler. 
- Bir adapter. Adapter verilerinizi `RecyclerView`'a bağlar. Verileri bir `ViewHolder`'da görüntülenebilecek şekilde uyarlar. Bir `RecyclerView`, verilerin ekranda nasıl görüntüleneceğini bulmak için adaptörü kullanır.

## <a name="b"></a>Aşama 2 : RecyclerView ve bir Adapter uygulayın

Bu aşamada layout dosyanıza bir `RecyclerView` ekleyecek ve uyku verilerini `RecyclerView`'a göstermek için bir `Adapter` kuracaksınız.

### Adım 1: LayoutManager'lı bir RecyclerView ekleyin

Bu adımda, `fragment_sleep_tracker.xml` dosyasındaki `ScrollView`'u bir `RecyclerView` ile değiştireceksiniz.

1.  RecyclerViewFundamentals-Starter uygulamasını GitHub'dan indirin. <!-- linki ekle -->
2.  Uygulamayı build edin ve çalıştırın. Verilerin basit text olarak nasıl görüntülendiğine dikkat edin.
3.  Android Studio'nun **Design** sekmesinde `fragment_sleep_tracker.xml` layout dosyasını açın.
4.  **Component Tree** bölmesinden `ScrollView`'u silin. Bu eylem ayrıca `ScrollView` içindeki `TextView`'u da siler.
5.  **Palette** bölmesinde, **Containers**'ı bulmak için soldaki component types listesinde gezinin, ardından onu seçin.
6.  **Palette** bölmesinden **Component Tree** bölmesine bir `RecyclerView` sürükleyin. `RecyclerView`'u `ConstraintLayout` içine yerleştirin.

![design tab image](https://developer.android.com/codelabs/kotlin-android-training-recyclerview-fundamentals/img/8c6cfd99d4237c7d.png)

7. Dependency eklemek isteyip istemediğinizi soran bir iletişim kutusu açılırsa, Android Studio'nun `recyclerview` dependency'sini (bağımlılığını) Gradle dosyanıza eklemesine izin vermek için **OK**'a tıklayın. Birkaç saniye sürebilir ve ardından uygulamanız senkronize edilir.

![dependency dialog](https://developer.android.com/codelabs/kotlin-android-training-recyclerview-fundamentals/img/828133c3a2314dc7.png)

8. Module `build.gradle` dosyasını açın, sonuna gidin ve aşağıdaki kods benzer olan yeni dependency'ye dikkat edin:

```

implementation 'androidx.recyclerview:recyclerview:1.0.0'

```

9. `fragment_sleep_tracker.xml`'e geri dönün.
10. **Code** sekmesinde, aşağıda gösterilen `RecyclerView` kodunu arayın:

```

<androidx.recyclerview.widget.RecyclerView
   android:layout_width="match_parent"
   android:layout_height="match_parent" />
   
```

11. `RecyclerView`'a `sleep_list` `id`'sini verin.

```

android:id="@+id/sleep_list"

```

12. `RecyclerView`'ı `ConstraintLayout` içindeki ekranın kalan kısmını alacak şekilde konumlandırın. Bunu yapmak için, `RecyclerView`'un üstünü **Start** butonuyla, altını **Clear** butonuyla ve her iki tarafı da parent ile constrain edin. Aşağıdaki kodu kullanarak Layout Editor'da veya XML'de layout genişliğini ve yüksekliğini 0 dp olarak ayarlayın:

```

android:layout_width="0dp"
    android:layout_height="0dp"
    app:layout_constraintBottom_toTopOf="@+id/clear_button"
    app:layout_constraintEnd_toEndOf="parent"
    app:layout_constraintStart_toStartOf="parent"
    app:layout_constraintTop_toBottomOf="@+id/stop_button"
    
```

13. `RecyclerView` XML'ine bir layout manager ekleyin. Her `RecyclerView`'ın listedeki öğeleri nasıl konumlandıracağını söyleyen bir layout manager'a ihtiyacı vardır. Android, öğeleri varsayılan olarak tam genişlikte satırlardan oluşan dikey bir listede düzenleyen bir `LinearLayoutManager` sağlar.

```

app:layoutManager="androidx.recyclerview.widget.LinearLayoutManager"

```

14. **Design** sekmesine geçin ve eklenen constraintlerin `RecyclerView`'ın mevcut alanı dolduracak şekilde genişlemesine neden olduğunu fark edin.

![image](https://developer.android.com/codelabs/kotlin-android-training-recyclerview-fundamentals/img/eef3940d35065b97.png)

### Adım 2: Liste öğesi layout'unu ve text view holder'ı oluşturun

`RecyclerView` yalnızca bir container'dır. Bu adımda, `RecyclerView` içinde görüntülenecek öğeler için layout'u ve altyapıyı oluşturursunuz.

Çalışan bir `RecyclerView`'a mümkün olduğunca çabuk ulaşmak için, önce sadece uyku kalitesini bir sayı olarak gösteren basit bir liste öğesi kullanacaksınız. Bunun için bir view holder'a ihtiyacınız var, `TextItemViewHolder`. Ayrıca veriler için bir view'a, bir `TextView`, ihtiyacınız var. (Daha sonraki bir adımda, view holderlar ve tüm uyku verilerinin nasıl düzenleneceği hakkında daha fazla bilgi edineceksiniz.)

1. `text_item_view.xml` isminde bir layout dosyası oluşturun. Kök öğe olarak ne kullandığınızın bir önemi yok çünkü şablon kodunu değiştireceksiniz.
2. `text_item_view.xml` içinde verilen tüm kodu silin.
3. Başında ve sonunda `16dp` padding olan ve `24sp` text size'ı olan bir `TextView` ekleyin. Genişliğin parentla eşleşmesini ve yüksekliğin wrap content olmasını sağlayın. Bu view `RecyclerView` içinde görüntülendiğinden, view'u bir `ViewGroup` içine yerleştirmeniz gerekmez.

```

<?xml version="1.0" encoding="utf-8"?>
<TextView xmlns:android="http://schemas.android.com/apk/res/android"
    android:textSize="24sp"
    android:paddingStart="16dp"
    android:paddingEnd="16dp"
    android:layout_width="match_parent"       
    android:layout_height="wrap_content" />
    
```

4. `Util.kt`'yi açın. Sona ilerleyin ve aşağıda gösterilen, `TextItemViewHolder` class'ını oluşturan tanımı ekleyin. Kodu, son kapanış ayracından sonra, dosyanın altına koyun. Kod, `Util.kt`'de bulunur çünkü bu view holder geçicidir ve daha sonra değiştirirsiniz.

```

class TextItemViewHolder(val textView: TextView): RecyclerView.ViewHolder(textView)

```

5. İstenirse `android.widget.TextView` ve `androidx.recyclerview.widget.RecyclerView`'u import edin.

### Adım 3: SleepNightAdapter'ı oluşturun

Bir `RecyclerView`'u uygularken temel görev, adapter oluşturmaktır.

Öğe view'u için basit bir view holder'ınız ve her öğe için bir layout'ınız var. Bu parçaların her ikisi ile artık bir adapter oluşturabilirsiniz. Adapter bir view holder oluşturur ve bunu `RecyclerView`'ın görüntülemesi için verilerle doldurur.

1. `sleeptracker` paketinde, `SleepNightAdapter` adlı yeni bir Kotlin class'ı oluşturun.
2. `SleepNightAdapter` class'ının `RecyclerView.Adapter`'ı extend etmesini sağlayın. Class, `SleepNightAdapter` olarak adlandırılır çünkü bir `SleepNight` nesnesini `RecyclerView`'ın kullanabileceği bir şeye uyarlar. Adapter'ın hangi view holder'ı kullanacağını bilmesi gerekir, bu nedenle `TextItemViewHolder`'ı iletin. İstendiğinde gerekli bileşenleri import edin ve ardından adapter'da uygulanması zorunlu yöntemler olduğundan bir hata göreceksiniz.

```

class SleepNightAdapter: RecyclerView.Adapter<TextItemViewHolder>() {}

```

3. `SleepNightAdapter`'ın en üst seviyesinde (top level), verileri tutmak için bir `SleepNight` değişkeni listesi oluşturun (`listOf`).

```

var data =  listOf<SleepNight>()

```

4. SleepNightAdapter'da, verilerdeki uyku geceleri listesinin boyutunu döndürmek için getItemCount()'ı override edin. RecyclerView, adapter'ın görüntülemesi için kaç öğeye sahip olduğunu bilmelidir ve bunu getItemCount()'u çağırarak yapar.

```

override fun getItemCount() = data.size

```

5. Aşağıda görüldüğü gibi, `SleepNightAdapter`'da, `onBindViewHolder()` fonksiyonunu override edin.

`onBindViewHolder()` fonksiyonu, belirtilen konumdaki bir liste öğesinin verilerini görüntülemek için `RecyclerView` tarafından çağrılır. Dolayısıyla `onBindViewHolder()` fonksiyonu iki argüman alır: bir view holder ve bağlanacak verinin konumu. Bu uygulama için holder `TextItemViewHolder`'dır ve konum listedeki konumdur.

```

override fun onBindViewHolder(holder: TextItemViewHolder, position: Int) {
}

```

6. `onBindViewHolder()` içinde, verilerde belirli bir konumda olan öğe için bir değişken oluşturun.

```

 val item = data[position]

```

7. Yeni oluşturduğunuz `ViewHolder`, `textView` adlı bir özelliğe sahiptir. `onBindViewHolder()` içinde, `textView` `text`'ini uyku kalitesi numarasına ayarlayın. Bu kod yalnızca bir sayı listesi görüntüler, ancak bu basit örnek, adapter'ın verileri view holder'a ve ekrana nasıl aldığını görmenizi sağlar.

```

holder.textView.text = item.sleepQuality.toString()

```

8. `SleepNightAdapter`'da, `RecyclerView` bir view holder'a ihtiyaç duyduğunda çağrılan `onCreateViewHolder()` öğesini override edin ve uygulayın.

Bu fonksiyon iki parametre alır ve bir `ViewHolder` döndürür. View holder'ı tutan view group olan `parent` parametre her zaman `RecyclerView`'dır. Aynı `RecyclerView` içinde birden çok view olduğunda `viewType` parametresi kullanılır. Örneğin, text viewların bir listesini, bir resmi ve bir videoyu aynı `RecyclerView`'a koyarsanız, `onCreateViewHolder()` işlevinin ne tür bir view kullanacağını bilmesi gerekir.

```

override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TextItemViewHolder {
}

```

9. `onCreateViewHolder()` içinde, `layoutinflater`'dan onu inflate etmesini isteyerek `view`'u oluşturun.

View için XML layout'Unu ve view için `parent` view group'u iletin. Üçüncü, boolean argümanı, `attachToRoot`'tur. Bu argümanın `false` olması gerekir, çünkü `RecyclerView` zamanı geldiğinde bu öğeyi sizin için view hiyerarşisine ekler.

```

val view = layoutInflater
       .inflate(R.layout.text_item_view, parent, false) as TextView
       
```

11. `onCreateViewHolder()`'da `view` ile oluşturulmuş bir `TextItemViewHolder` döndürün.

```

return TextItemViewHolder(view)

```

12. Adater'ın, verilerin (`data`) ne zaman değiştiğini `RecyclerView`'a bildirmesi gerekir, çünkü `RecyclerView` veriler hakkında hiçbir şey bilmez. Sadece adapter'ın kendisine verdiği view holderları bilir.

`RecyclerView`'a gösterdiği veriler değiştiğinde bunu söylemek için, `SleepNightAdapter` sınıfının en üstündeki `data` değişkenine bir custom setter ekleyin. Setter'da, `data`'ya yeni bir değer verin, ardından listeyi yeni verilerle yeniden çizmeyi tetiklemek için `notifyDataSetChanged()` öğesini çağırın.

```

var data =  listOf<SleepNight>()
   set(value) {
       field = value
       notifyDataSetChanged()
   }
   
```

>Not: `notifyDataSetChanged()` çağrıldığında, `RecyclerView` yalnızca değiştirilen öğeleri değil, tüm listeyi yeniden çizer. Bu basit ve şimdilik işe yarıyor. Bu codelab serisinde daha sonra bu kodu geliştireceksiniz.


### Adım 4: RecyclerView'a Adapter'dan bahsedin

`RecyclerView`'ın, view holderları elde etmek için kullanılacak adapter hakkında bilgi sahibi olması gerekir.

1. SleepTrackerFragment.kt'ı açın.
2. `onCreateview()`'da bir adapter oluşturun. Bu kodu `ViewModel` modelinin oluşturulmasından sonra ve `return` ifadesinden önce koyun.

```

val adapter = SleepNightAdapter()

```

3. Binding objesine bir referans aldıktan sonra, `adapter`'ı `RecyclerView` ile ilişkilendirin.

```

binding.sleepList.adapter = adapter

```

4. `binding` objesini güncellemek için uygulamanızı temizleyin ve tekrar build edin. (Clean and rebuild)

`binding.sleepList` veya `binding.FragmentSleepTrackerBinding` çevresinde hala hatalar görüyorsanız, önbellekleri geçersiz kılın ve yeniden başlatın. (**File > Invalidate Caches / Restart**)

Uygulamayı şimdi çalıştırırsanız hata olmaz, ancak **Start**'a ve ardından **Stop**'a dokunduğunuzda görüntülenen herhangi bir veri görmezsiniz.


### Adım 5: Veriyi adapter'a verin

Şimdiye kadar bir adapter'ınız ve adapter'dan `RecyclerView`'a veri almanın bir yolu var. Şimdi `ViewModel`'den adapter'a veri almanız gerekiyor.

1. `SleepTrackerViewModel`' açın.
2. Görüntülenecek veri olan tüm uyku gecelerini saklayan `nights` değişkenini bulun. `nights` değişkeni, veritabanında `getAllNights()` çağrılarak ayarlanır.
3. private'ı nights'tan kaldırın, çünkü bu değişkene erişmesi gereken bir observer yaratacaksınız. İfadeniz şöyle görünmelidir:

```

val nights = database.getAllNights()

```

4. `database` paketindeki `SleepDatabaseDao`'yu açın.
5. `getAllNights()` fonksiyonunu bulun. Bu fonksiyonun `LiveData` olarak bir `SleepNight` değerleri listesi döndürdüğüne dikkat edin. Bu, `nights` değişkeninin, `Room` tarafından güncellenen `LiveData`'yı içerdiği ve ne zaman değiştiğini bilmek için `nights`'ı gözlemleyebileceğiniz anlamına gelir.
6. `SleepTrackerFragment`'ı açın.
7. `onCreateView()` içinde, ViewModel instantiate edildikten ve referansına sahip olduktan sonra, `nights` değişkenine bir observer oluşturun.

Fragment'ın `viewLifecycleOwner`'ını lifecycle owner olarak sağlayarak, bu observer'ın yalnızca `RecyclerView` ekrandayken etkin olduğundan emin olabilirsiniz.

```

sleepTrackerViewModel.nights.observe(viewLifecycleOwner, Observer {
   })

```

8. Observer içinde, ne zaman null olmayan bir değer alırsanız (`nights` için), değeri adapter'ın `data`'sına atayın. Bu, observer ve verileri ayarlamak için tamamlanmış koddur:

```

sleepTrackerViewModel.nights.observe(viewLifecycleOwner, Observer {
   it?.let {
       adapter.data = it
   }
})

```

9. Kodunuzu build edin ve çalıştırın.

Adapter'ınız çalışıyorsa, uyku kalitesi numaralarını bir liste olarak göreceksiniz. Soldaki ekran görüntüsü, **Start**'a dokunduktan sonra -1'i gösterir. Sağdaki ekran görüntüsü, **Stop**'a dokunup bir kalite derecelendirmesi seçtikten sonra güncellenen uyku kalitesi numarasını gösterir.

![image](https://developer.android.com/codelabs/kotlin-android-training-recyclerview-fundamentals/img/89f71f10deda270.png)


### Adım 6: View holderların nasıl geri dönüştürüldüğünü keşfedin

`RecyclerView`, view holderları _geri dönüştürür_, bu da onları yeniden kullandığı anlamına gelir. Bir view ekrandan kayarken, `RecyclerView` ekrana kaydırılmak üzere olan öğenin view'unu yeniden kullanır.

Bu view holderlar geri dönüştürüldüğünden, `onBindViewHolder()`'ın önceki öğelerin bir view holderda ayarlamış olabileceği özelleştirmeleri ayarladığından veya sıfırladığından emin olun.

Örneğin, 1'e eşit veya daha düşük kalite derecelendirmelerine sahip olan ve kötü uykuyu temsil eden view holderlarda metin rengini kırmızıya ayarlayabilirsiniz.

1. `SleepNightAdapter` class'ında, `onBindViewHolder()`'ın sonuna aşağıdaki kodu ekleyin.

```

if (item.sleepQuality <= 1) {
   holder.textView.setTextColor(Color.RED) // red
}

```

2. Uygulamayı çalıştırın.
3. Düşük puanlı birkaç uyku kalitesi verisi ekleyin ve sayı kırmızı olacaktır.
4. Ekranda kırmızı bir yüksek sayı görene kadar uyku kalitesi için yüksek derecelendirmeler ekleyin.

RecyclerView, view holderları yeniden kullandığından, sonunda yüksek kalite derecelendirmesi için kırmızı view holderlardan birini yeniden kullanır. Yüksek derecelendirme, hatalı olarak kırmızı görüntüleniyor.

![image](https://developer.android.com/codelabs/kotlin-android-training-recyclerview-fundamentals/img/b57e7915d6dd3c78.png)

5. Bunu düzeltmek için, kalite bire eşit veya daha az değilse rengi siyah olarak ayarlamak için bir `else` ifadesi ekleyin.

Her iki koşul da açık olduğunda, view holder her öğe için doğru metin rengini kullanacaktır.

```

if (item.sleepQuality <= 1) {
   holder.textView.setTextColor(Color.RED) // red
} else {
   // reset
   holder.textView.setTextColor(Color.BLACK) // black
}

```

6. Uygulamayı çalıştırın ve sayılar her zaman doğru renge sahip olacaktır.

Tebrikler! Artık tamamen işlevsel bir temel `RecyclerView`'a sahipsiniz.

## <a name="c"></a>Aşama 3 : Tüm uyku datası için bir ViewHolder oluşturun

Bu aşamada, basit view holder'ı bir uyku gecesi için daha fazla veri görüntüleyebilen biriyle değiştireceksiniz.

`Util.kt`'ye eklediğiniz basit `ViewHolder`, bir `TextView`'u `TextItemViewHolder`'a sarar.

```

class TextItemViewHolder(val textView: TextView): RecyclerView.ViewHolder(textView)

```

Peki neden `RecyclerView` doğrudan bir `TextView` kullanmıyor? Bu tek satırlık kod, birçok işlevsellik sağlar. Bir `ViewHolder`, `RecyclerView` içindeki yeri hakkında bir öğe view'u ve metadata'yı tanımlar. `RecyclerView`, liste kaydırılırken görünümü doğru bir şekilde konumlandırmak ve `Adapter`'a öğeler eklendiğinde veya kaldırıldığında viewları canlandırmak gibi ilginç şeyler yapmak için bu işlevselliğe güvenir.

`RecyclerView`'ın `ViewHolder`'da depolanan viewlara erişmesi gerekiyorsa, bunu view holder'ın [`itemView`](https://developer.android.com/reference/android/support/v7/widget/RecyclerView.ViewHolder#fields_1) özelliğini kullanarak yapabilir. `RecyclerView`, bir öğeyi ekranda görüntülenecek şekilde bind ederken, kenarlık (border) gibi bir view'un etrafına süslemeler çizerken ve erişilebilirliği uygulamak için `itemView`'ı kullanır.

### Adım 1: Öğe layout'unu oluşturun

Bu adımda, bir öğe için layout dosyası oluşturursunuz. Layout, uyku kalitesi için bir `ImageView`, uyku uzunluğu için bir `TextView` ve text olarak kalite için bir `TextView` içeren bir `ConstraintLayout`'tan oluşur. Daha önce layoutlar yaptığınız için sağlanan XML kodunu kopyalayıp yapıştırın.

1. Yeni bir layout resource dosyası oluşturun ve onu `list_item_sleep_night` olarak adlandırın.
2. Dosyadaki tüm kodu aşağıdaki kodla değiştirin. Ardından, az önce oluşturduğunuz layout'u tanıyın.

```

<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
   xmlns:app="http://schemas.android.com/apk/res-auto"
   xmlns:tools="http://schemas.android.com/tools"
   android:layout_width="match_parent"
   android:layout_height="wrap_content">

   <ImageView
       android:id="@+id/quality_image"
       android:layout_width="@dimen/icon_size"
       android:layout_height="60dp"
       android:layout_marginStart="16dp"
       android:layout_marginTop="8dp"
       android:layout_marginBottom="8dp"
       app:layout_constraintBottom_toBottomOf="parent"
       app:layout_constraintStart_toStartOf="parent"
       app:layout_constraintTop_toTopOf="parent"
       tools:srcCompat="@drawable/ic_sleep_5" />

   <TextView
       android:id="@+id/sleep_length"
       android:layout_width="0dp"
       android:layout_height="20dp"
       android:layout_marginStart="8dp"
       android:layout_marginTop="8dp"
       android:layout_marginEnd="16dp"
       app:layout_constraintEnd_toEndOf="parent"
       app:layout_constraintStart_toEndOf="@+id/quality_image"
       app:layout_constraintTop_toTopOf="@+id/quality_image"
       tools:text="Wednesday" />

   <TextView
       android:id="@+id/quality_string"
       android:layout_width="0dp"
       android:layout_height="20dp"
       android:layout_marginTop="8dp"
       app:layout_constraintEnd_toEndOf="@+id/sleep_length"
       app:layout_constraintHorizontal_bias="0.0"
       app:layout_constraintStart_toStartOf="@+id/sleep_length"
       app:layout_constraintTop_toBottomOf="@+id/sleep_length"
       tools:text="Excellent!!!" />
</androidx.constraintlayout.widget.ConstraintLayout>

```

3. Android Studio'daki Design sekmesine geçin. Design görünümünde, layout'unuz aşağıda, soldaki ekran görüntüsüne benzer. Blueprint görünümünde, sağdaki ekran görüntüsü gibi görünecek.

![image](https://developer.android.com/codelabs/kotlin-android-training-recyclerview-fundamentals/img/8240174f46c2c380.png)

### Adım 2: ViewHolder oluşturun

1. `SleepNightAdapter.kt`'yi açın.
2. `SleepNightAdapter` içinde `ViewHolder` adında bir class oluşturun ve `RecyclerView.ViewHolder`'ı extend etmesini sağlayın.

```

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){}

```

3. `ViewHolder` içinde, viewlara referanslar alın. Bu `ViewHolder`'ın güncelleyeceği viewlara bir referansa ihtiyacınız var. Bu `ViewHolder`'ı her bind ettiğinizde, resme ve her iki text view'a da erişmeniz gerekir. (Bu kodu daha sonra data binding kullanmak için dönüştüreceksiniz.)

```

val sleepLength: TextView = itemView.findViewById(R.id.sleep_length)
val quality: TextView = itemView.findViewById(R.id.quality_string)
val qualityImage: ImageView = itemView.findViewById(R.id.quality_image)

```

### Adım 3: ViewHolder'ı SleepNightAdapter'da kullanın

1. `SleepNightAdapter` class'ı signature tanımında `TextItemViewHolder` yerine az önce oluşturduğunuz `SleepNightAdapter.ViewHolder`'ı kullanın.

```

class SleepNightAdapter: RecyclerView.Adapter<SleepNightAdapter.ViewHolder>() {

```

`onCreateViewHolder()`'ı güncelleyin:

2. `onCreateViewHolder()`'ın signature'ını `ViewHolder` döndürecek şekilde değiştirin.
3. Doğru layout resource'u, `list_item_sleep_night`, kullanmak için layout inflater'ı değiştirin.
4. `TextView`'a cast etmeyi kaldırın.
5. Bir `TextItemViewHolder` döndürmek yerine, bir `ViewHolder` döndürün.

İşte tamamlanmış ve güncellenmiş `onCreateViewHolder()` fonksiyonu:

```

  override fun onCreateViewHolder(
            parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = 
            LayoutInflater.from(parent.context)
        val view = layoutInflater
                .inflate(R.layout.list_item_sleep_night, 
                         parent, false)
        return ViewHolder(view)
    }
    
```

`onBindViewHolder()`'ı güncelleyin:

6. `onBindViewHolder()` signature'ınnı, `holder` parametresi `TextItemViewHolder` yerine `ViewHolder` olacak şekilde değiştirin.
7. `onBindViewHolder()` içinde, `item` tanımı dışındaki tüm kodu silin.
8. Bu view için kaynaklara (resources) referans tutan bir `val` `res` tanımlayın.

```

val res = holder.itemView.context.resources

```

9. `sleepLength` text view'un textini süreye ayarlayın. Başlangıç koduyla birlikte sağlanan bir biçimlendirme fonksiyonunu çağıran aşağıdaki kodu kopyalayın.


```

holder.sleepLength.text = convertDurationToFormatted(item.startTimeMilli, item.endTimeMilli, res)

```

10. Bu bir hata verir, çünkü `convertDurationToFormatted()`'ın tanımlanması gerekir. `Util.kt`'yi açın ve bunun için kodu ve ilişkili içe aktarmaların yorumlamasını kaldırın. (**Code > Comment with Line comments**)
11. `SleepNightAdapter.kt` içindeki `onBindViewHolder()`'a geri dönün, kaliteyi ayarlamak için `convertNumericQualityToString()`'i kullanın.

```

holder.quality.text= convertNumericQualityToString(item.sleepQuality, res)

```

12. Bu işlevleri manuel olarak içe aktarmanız gerekebilir.

```

import com.example.android.trackmysleepquality.convertDurationToFormatted
import com.example.android.trackmysleepquality.convertNumericQualityToString

```

13. Kaliteyi ayarladıktan hemen sonra kalite için doğru simgeyi ayarlayın. Yeni `ic_sleep_active` simgesi, başlangıç kodunda sizin için sağlanmıştır.

```

holder.qualityImage.setImageResource(when (item.sleepQuality) {
   0 -> R.drawable.ic_sleep_0
   1 -> R.drawable.ic_sleep_1
   2 -> R.drawable.ic_sleep_2
   3 -> R.drawable.ic_sleep_3
   4 -> R.drawable.ic_sleep_4
   5 -> R.drawable.ic_sleep_5
   else -> R.drawable.ic_sleep_active
})

```

14. İşte, `ViewHolder` için tüm verileri ayarlayan, tamamlanmış güncellenmiş `onBindViewHolder()` fonksiyonu:

```

   override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        val res = holder.itemView.context.resources
        holder.sleepLength.text = convertDurationToFormatted(item.startTimeMilli, item.endTimeMilli, res)
        holder.quality.text= convertNumericQualityToString(item.sleepQuality, res)
        holder.qualityImage.setImageResource(when (item.sleepQuality) {
            0 -> R.drawable.ic_sleep_0
            1 -> R.drawable.ic_sleep_1
            2 -> R.drawable.ic_sleep_2
            3 -> R.drawable.ic_sleep_3
            4 -> R.drawable.ic_sleep_4
            5 -> R.drawable.ic_sleep_5
            else -> R.drawable.ic_sleep_active
        })
    }

```

15. Uygulamanızı çalıştırın. Ekranınız, uyku süresi ve uyku kalitesi için textle birlikte uyku kalitesi simgesini gösteren aşağıdaki ekran görüntüsü gibi görünmelidir.

![image](https://developer.android.com/codelabs/kotlin-android-training-recyclerview-fundamentals/img/d5deef86fa39fbfc.png)

## <a name="d"></a>Aşama 4 : Kodunuzu geliştirin

`RecyclerView`'ınız şimdi tamamlandı! Bir `Adapter` ve `ViewHolder`'ın nasıl uygulanacağını öğrendiniz ve bunları bir `RecyclerView` `Adapter` ile bir liste görüntülemek için bir araya getirdiniz.

Şimdiye kadarki kodunuz, bir adapter ve view holder oluşturma sürecini gösterir. Ancak, bu kodu geliştirebilirsiniz. Görüntülenecek kod ve view holderlar yönetecek kod karıştırılmıştır ve `onBindViewHolder()`, `ViewHolder`'ın nasıl güncelleneceğiyle ilgili ayrıntıları bilir.

Bir üretim uygulamasında, birden çok view holderınız, daha karmaşık adapterlarınız ve değişiklik yapan birden çok developerınız olabilir. Kodunuzu, bir view holderla ilgili her şey yalnızca view holder'da olacak şekilde yapılandırmalısınız.

### Adım 1: onBindViewHolder()'ı refactor edin

Bu adımda, kodu yeniden düzenler (refactor) ve tüm view holder işlevselliğini `ViewHolder`'a taşırsınız. Bu yeniden düzenlemenin amacı, uygulamanın kullanıcıya nasıl göründüğünü değiştirmek değil, developerların kod üzerinde çalışmasını daha kolay ve daha güvenli hale getirmektir. Neyse ki, Android Studio'nun yardımcı olacak araçları var.

1. `SleepNightAdapter.kt`'de, `onBindViewHolder()` fonksiyonunda, `item` değişkenini bildirmek için olan ifade dışında her şeyi seçin.
2. Sağ tıklayın, ardından **Refactor > Extract > Function**'ı seçin.
3. Fonksiyona `bind` adını verin ve önerilen parametreleri kabul edin. **OK**'a tıklayın.

`bind()` fonksiyonu `onBindViewHolder()`'ın altına yerleştirilir.

```

private fun bind(holder: ViewHolder, item: SleepNight) {
        val res = holder.itemView.context.resources
        holder.sleepLength.text = convertDurationToFormatted(item.startTimeMilli, item.endTimeMilli, res)
        holder.quality.text = convertNumericQualityToString(item.sleepQuality, res)
        holder.qualityImage.setImageResource(when (item.sleepQuality) {
            0 -> R.drawable.ic_sleep_0
            1 -> R.drawable.ic_sleep_1
            2 -> R.drawable.ic_sleep_2
            3 -> R.drawable.ic_sleep_3
            4 -> R.drawable.ic_sleep_4
            5 -> R.drawable.ic_sleep_5
            else -> R.drawable.ic_sleep_active
        })
    }

```

4. Aşağıdaki signature ile bir [extension function](https://kotlinlang.org/docs/reference/extensions.html) oluşturun:

```

private fun ViewHolder.bind(item: SleepNight) {...}

```

5. Bu `ViewHolder.bind()` fonksiyonunu kesip `SleepNightAdapter.kt`'nin altındaki `ViewHolder` inner class'ına yapıştırın.
6. `bind()`'ı public yapın.
7. Gerekirse, `bind()`'ı adapter'a import edin.
8. Artık `ViewHolder`'da olduğundan, signature'ın `ViewHolder` bölümünü kaldırabilirsiniz. İşte `ViewHolder` class'ındaki `bind()` fonksiyonunun son kodu.

```

fun bind(item: SleepNight) {
   val res = itemView.context.resources
   sleepLength.text = convertDurationToFormatted(
           item.startTimeMilli, item.endTimeMilli, res)
   quality.text = convertNumericQualityToString(
           item.sleepQuality, res)
   qualityImage.setImageResource(when (item.sleepQuality) {
       0 -> R.drawable.ic_sleep_0
       1 -> R.drawable.ic_sleep_1
       2 -> R.drawable.ic_sleep_2
       3 -> R.drawable.ic_sleep_3
       4 -> R.drawable.ic_sleep_4
       5 -> R.drawable.ic_sleep_5
       else -> R.drawable.ic_sleep_active
   })
}

```

Şimdi `SleepNightAdapter.kt`'de, `onBindViewHolder()` fonksiyou `bind(holder, item)` üzerinde çözülmemiş bir referans (unresolved reference) hatası gösterecektir. Bu fonksiyon artık ViewHolder inner class'ının bir parçasıdır, dolayısıyla belirli ViewHolder'ı belirtmemiz gerekiyor. Bu aynı zamanda ilk argümanı kaldırabileceğimiz anlamına gelir.

```

override fun onBindViewHolder(holder: ViewHolder, position: Int) {
   val item = data[position]
   holder.bind(item)
}

```

### Adım 1: onCreateViewHolder'ı refactor edin

Adapter'daki `onCreateViewHolder()` fonksiyonu, şu anda `ViewHolder` için layout resource'daki view'u inflate ediyor. Ancak inlflation'ın adapterla ilgisi yoktur ama `ViewHolder` ile ilgisi vardır. Inflation ViewHolder'da gerçekleşmelidir.

1. `onCreateViewHolder()` içinde, fonksiyonun body'sindeki tüm kodu seçin.
2. Sağ tıklayın, ardından **Refactor > Extract > Function**'ı seçin.
3. Fonkisyonu `from` olarak adlandırın ve önerilen parametreleri kabul edin. **OK**'a tıklayın.
4. İmleci fonksiyon adı `from`'un üzerine getirin. Intention menüsünü açmak için `Alt+Enter` (Mac'te `Option+Enter`) tuşlarına basın.
5. **Move to companion object**'i seçin. `from()` fonksiyonunun bir  companion object'te  olması gerekir, böylece bir `ViewHolder` instance'ında değil, `ViewHolder` class'ında çağrılabilir.
6. `companion` object'i `ViewHolder` class'ına taşıyın.
7. `from()`'u public yapın.
8. `onCreateViewHolder()` içinde, `ViewHolder` class'ında `from()` çağrısının sonucunu döndürmek için `return` ifadesini değiştirin.

Tamamlanmış `onCreateViewHolder()` ve `from()` fonksiyonlarınız aşağıdaki koda benzemeli ve kodunuz hatasız build etmeli ve çalışmalıdır.

```

override fun onCreateViewHolder(parent: ViewGroup, viewType: 
Int): ViewHolder {
        return ViewHolder.from(parent)
    }
    
```

```

companion object {
   fun from(parent: ViewGroup): ViewHolder {
       val layoutInflater = LayoutInflater.from(parent.context)
       val view = layoutInflater
               .inflate(R.layout.list_item_sleep_night, parent, false)
       return ViewHolder(view)
   }
}

```

9. Constructor private olacak şekilde `ViewHolder` class'ının signature'ını değiştirin. `from()` artık yeni bir `ViewHolder` instance'ı döndüren bir fonksiyon olduğundan, artık birinin `ViewHolder`'ın constructor'ını çağırması için hiçbir neden yoktur.

```

class ViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView){

```

10. Uygulamayı çalıştırın. Uygulamanız öncekiyle aynı şekilde build etmeli ve çalışmalıdır; bu, yeniden düzenlemeden sonra istenen sonuçtur.

