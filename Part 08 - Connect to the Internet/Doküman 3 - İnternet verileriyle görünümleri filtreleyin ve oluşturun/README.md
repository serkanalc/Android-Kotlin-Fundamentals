# <a name="1"></a>İnternetten görüntüleri yükleme ve görüntüleme

- [Overview'a "satılık" görselleri ekleyin](#a)
- [Sonuçları filtreleyin](#b)
- [Bir detay sayfası oluşturun ve navigasyonu ayarlayın](#c)
- [Daha kullanışlı bir detay sayfası oluşturun](#d)

Bu codelab'te (ve ilgili codelab'lerde), Mars'ta satılık mülkleri gösteren MarsRealEstate adlı bir 
uygulamayla çalışıyorsunuz. Bu uygulama, fiyat ve mülkün satılık veya kiralık olup olmadığı gibi ayrıntılar da dahil 
olmak üzere mülk verilerini almak ve görüntülemek için bir internet sunucusuna bağlanır. Her bir mülkü temsil eden 
görüntüler, NASA'nın Mars gezicilerinden çekilen Mars'tan alınan gerçek zamanlı fotoğraflarıdır. Önceki codelab'lerde, 
tüm özellik fotoğrafları için ızgara düzenine sahip bir RecyclerView oluşturdunuz:

![image](https://user-images.githubusercontent.com/29903779/151003128-433068f9-d306-4439-ac90-3933355c34d7.png)

Uygulamanın bu sürümünde, mülkün türüyle (kiraya karşı satın alma) çalışır ve satılık mülkleri işaretlemek için 
ızgara düzenine bir simge eklersiniz:

![image](https://user-images.githubusercontent.com/29903779/151003531-9d43f02a-99ad-481d-96ba-cd47d40ff657.png)

Uygulamanın seçenek menüsünü, ızgarayı yalnızca kiralık veya satılık mülkleri gösterecek şekilde filtreleyecek 
şekilde değiştirirsiniz:

![image](https://user-images.githubusercontent.com/29903779/151003585-7f9aa062-7842-48f8-b35e-6da477753da1.png)

Son olarak, tek bir mülk için bir detay görünümü oluşturursunuz ve overview görünümündeki simgeleri navigasyon 
ile bu detay fragment'a bağlarsınız:

![image](https://user-images.githubusercontent.com/29903779/151003714-757e37ac-a5cb-41a4-abbd-54b3886049b9.png)

## <a name="a"></a>Aşama 1 : Overview'a "satılık" görselleri ekleyin

Şimdiye kadar kullandığınız Mars mülk verilerinin tek parçası mülk resminin URL'sidir. Ancak MarsProperty sınıfında 
tanımladığınız mülk verileri aynı zamanda bir ID, price ve bir type (kiralık veya satılık) içerir. Belleğinizi 
yenilemek için, web servisinden aldığınız JSON verilerinin bir parçası:

```kotlin
{
   "price":8000000,
   "id":"424908",
   "type":"rent",
   "img_src": "http://mars.jpl.nasa.gov/msl-raw-images/msss/01000/mcam/1000ML0044631290305226E03_DXXX.jpg"
}
```

Bu görevde, overview sayfasındaki satılık mülklere dolar işareti eklemek için Mars mülk türüyle çalışmaya başlarsınız.

### Adım 1: Türü eklemek için MarsProperty'yi güncelleyin

MarsProperty sınıfı, web servisi tarafından sağlanan her bir özellik için veri yapısını tanımlar. Önceki 
bir codelab'de, Mars web servisinden gelen ham JSON yanıtını ayrı MarsProperty veri nesnelerine ayrıştırmak 
için Moshi kitaplığını kullandınız.

Bu adımda, bir mülkün kiralık olup olmadığını (yani, type'ın "rent" veya "buy" string değeri olup olmadığını) 
belirtmek için MarsProperty sınıfına bir logic eklersiniz. Bu logic birden fazla yerde kullanılacak, bu yüzden 
onu kopyalamaktansa burada data sınıfında bulundurmak daha iyidir.

1. Son kod laboratuvarından MarsRealEstate uygulamasını açın. (Uygulamanız yoksa MarsRealEstateGrid'i indirebilirsiniz.)
2. Açık ağ/MarsProperty.kt. MarsProperty sınıf tanımına bir gövde ekleyin ve nesne "rent" türündeyse, isRental için true 
döndüren özel bir getter ekleyin.

```kotlin
data class MarsProperty(
       val id: String,
       @Json(name = "img_src") val imgSrcUrl: String,
       val type: String,
       val price: Double)  {
   val isRental
       get() = type == "rent"
}
```

### Adım 2: Izgara öğesi tasarımını güncelleyin

Şimdi, yalnızca satılık olan mülk görüntülerinde bir dolar işareti gösterecek şekilde görüntü ızgarası için 
öğe tasarımını güncellersiniz:

![image](https://user-images.githubusercontent.com/29903779/151005902-7bdda14f-821c-4b83-ad4a-b4ce15c1ee91.png)

Data binding ifadeleriyle, bu testi tamamen ızgara öğeleri için XML tasarımında yapabilirsiniz.

1. res/layout/grid_view_item.xml dosyasını açın. Bu, RecyclerView için ızgara düzenindeki her bir hücrenin tasarım dosyasıdır. 
Şu anda dosya özellik görüntüsü için yalnızca `<ImageView>` öğesini içerir.
2. `<data>` öğesinin içine, View sınıfı için bir `<import>` öğesi ekleyin. Bir tasarım dosyasında bir data binding ifadesi içinde 
bir sınıfın bileşenlerini kullanmak istediğinizde içe aktarmaları kullanırsınız. Bu durumda, View.GONE ve View.VISIBLE sabitlerini 
kullanacaksınız, dolayısıyla View sınıfına erişmeniz gerekiyor.

```kotlin
<import type="android.view.View"/>
```

3. dollar-sign drawable'ının, özellik görüntüsünün üstüne denk gelmesini sağlamak için tüm görüntü görünümünü bir FrameLayout ile çevreleyin.

```kotlin
<FrameLayout
   android:layout_width="match_parent"
   android:layout_height="170dp">
             <ImageView 
                    android:id="@+id/mars_image"
            ...
</FrameLayout>
```

4. ImageView için, yeni FrameLayout'u doldurmak için Android:layout_height niteliğini match_parent olarak değiştirin.

```kotlin
android:layout_height="match_parent"
```

5. FrameLayout'un içine, birinci `<ImageView>`'ın hemen altına ikinci bir `<ImageView>` öğesi ekleyin. Aşağıda gösterilen tanımı kullanın. 
Bu görüntü, ızgara öğesinin sağ alt köşesinde Mars görüntüsünün üstünde görünür ve dolar işareti simgesi için 
res/drawable/ic_for_sale_outline.xml içinde tanımlanan drawable'ı kullanır.

```kotlin
<ImageView
   android:id="@+id/mars_property_type"
   android:layout_width="wrap_content"
   android:layout_height="45dp"
   android:layout_gravity="bottom|end"
   android:adjustViewBounds="true"
   android:padding="5dp"
   android:scaleType="fitCenter"
   android:src="@drawable/ic_for_sale_outline"
   tools:src="@drawable/ic_for_sale_outline"/>
```

6. android:visibility niteliğini mars_property_type resim görünümüne ekleyin. Mülk türünü test etmek için bağlayıcı 
bir ifade kullanın ve görünürlüğü View.GONE (kiralama için) veya View.VISIBLE (satın alma için) olarak atayın.

```kotlin
android:visibility="@{property.rental ? View.GONE : View.VISIBLE}"
```

Şimdiye kadar yalnızca `<data>` öğesinde tanımlanan değişkenleri kullanan mizanpajlarda binding ifadeleri gördünüz. 
Binding ifadeleri son derece güçlüdür. Testler ve matematik hesaplamaları gibi işlemleri tamamen XML düzeniniz içinde 
yapmanızı sağlar. Bu durumda, bir test yapmak için ternary operatörü (?:) kullanırsınız (bu nesne kiralık mı?). Doğru için 
bir sonuç (View.GONE ile dolar işareti simgesini gizleyin) ve yanlış için başka bir sonuç sağlarsınız (bu simgeyi View.VISIBLE ile gösterin).

Yeni grid_view_item.xml dosyası aşağıda gösterilmiştir:

```kotlin
<layout xmlns:android="http://schemas.android.com/apk/res/android"
       xmlns:app="http://schemas.android.com/apk/res-auto"
       xmlns:tools="http://schemas.android.com/tools">
   <data>
       <import type="android.view.View"/>
       <variable
           name="property"
           type="com.example.android.marsrealestate.network.MarsProperty" />
   </data>
   <FrameLayout
       android:layout_width="match_parent"
       android:layout_height="170dp">

       <ImageView
           android:id="@+id/mars_image"
           android:layout_width="match_parent"
           android:layout_height="match_parent"
           android:scaleType="centerCrop"
           android:adjustViewBounds="true"
           android:padding="2dp"
           app:imageUrl="@{property.imgSrcUrl}"
           tools:src="@tools:sample/backgrounds/scenic"/>

       <ImageView
           android:id="@+id/mars_property_type"
           android:layout_width="wrap_content"
           android:layout_height="45dp"
           android:layout_gravity="bottom|end"
           android:adjustViewBounds="true"
           android:padding="5dp"
           android:scaleType="fitCenter"
           android:src="@drawable/ic_for_sale_outline"
           android:visibility="@{property.rental ? View.GONE : View.VISIBLE}"
           tools:src="@drawable/ic_for_sale_outline"/>
   </FrameLayout>
</layout>
```

7. Uygulamayı derleyin, çalıştırın ve kiralık olmayan mülklerin dolar işareti simgesine sahip olduğunu unutmayın.

![image](https://user-images.githubusercontent.com/29903779/151013734-ab844ae0-de24-4730-9d1f-0fe7cffde003.png)

## <a name="b"></a>Aşama 2 : Sonuçları filtreleyin

Şu anda uygulamanız overview kılavuzunda tüm Mars özelliklerini görüntüler. Bir kullanıcı Mars'ta kiralık bir 
mülk için alışveriş yapıyorsa, mevcut mülklerden hangilerinin satılık olduğunu gösteren simgelere sahip olmak faydalı 
olabilir ancak sayfada hala kaydırılacak çok sayıda mülk var. Bu görevde overview fragment'a kullanıcının 
yalnızca kiralık mülkleri, satılık mülkleri veya tümünü göstermesini sağlayan bir seçenekler menüsü eklersiniz.

![image](https://user-images.githubusercontent.com/29903779/151576723-c206fffb-85bb-4bfa-a125-5d2790736377.png)

Bu görevi gerçekleştirmenin bir yolu, overview kılavuzundaki her bir MarsProperty için türü test etmek ve 
yalnızca eşleşen özellikleri görüntülemektir. Ancak gerçek Mars web servisi, yalnızca `<rent>` veya `<buy>` 
türündeki özellikleri almanızı sağlayan bir sorgu parametresine veya seçeneğine (filtre) sahiptir. 
Bu filtre sorgusunu realestate web servisi URL'si ile aşağıdaki gibi bir tarayıcıda kullanabilirsiniz:

```https://android-kotlin-fun-mars-server.appspot.com/realestate?filter=buy```

Bu görevde, Retrofit ile web servisi isteğine bir sorgu seçeneği eklemek için MarsApiService sınıfını değiştirirsiniz. 
Ardından, bu sorgu seçeneğini kullanarak tüm Mars özelliği verilerini yeniden indirmek için seçenekler menüsünü bağlarsınız. 
Web servisinden aldığınız yanıt yalnızca ilgilendiğiniz özellikleri içerdiğinden, overview ızgarası için görünüm 
görüntüleme mantığını değiştirmeniz gerekmez.

### Adım 1: Mars API hizmetini güncelleyin

İsteği değiştirmek için bu serideki ilk codelab'de uyguladığınız MarsApiService sınıfını tekrar ziyaret etmeniz gerekir. 
Bir filtreleme API'si sağlamak için sınıfı değiştirirsiniz.

1. network/MarsApiService.kt'yi açın. İmportların hemen altında, web servisinin beklediği sorgu değerleriyle eşleşen sabitleri 
tanımlamak için MarsApiFilter adlı bir enum oluşturun.

```kotlin
enum class MarsApiFilter(val value: String) {
   SHOW_RENT("rent"),
   SHOW_BUY("buy"),
   SHOW_ALL("all") }
```

2. Filtre sorgusu için string girdisi almak üzere getProperties() metodunu değiştirin ve aşağıda gösterildiği gibi bu girdiye 
@Query("filter") ile açıklama ekleyin.

İstendiğinde retrofit2.http.Query'yi import edin.

@Query annotation'ı, getProperties() metoduna (ve dolayısıyla Retrofit) filtre seçeneğiyle web servisi isteğinde bulunmasını 
söyler. getProperties() her çağrıldığında, istek URL'si, web hizmetini bu sorguyla eşleşen sonuçlarla yanıt vermeye 
yönlendiren ?filter=type bölümünü içerir.

```kotlin
suspend fun getProperties(@Query("filter") type: String): List<MarsProperty>
```  

### Adım 2: Overview view modeli güncelleyin

OverviewViewModel'deki getMarsRealEstateProperties() metodunda MarsApiService'den veri talep edersiniz. Şimdi filtre 
argümanını almak için bu isteği güncellemeniz gerekiyor.

1. overview/OverviewViewModel.kt dosyasını açın. Bir önceki adımda yaptığınız değişikliklerden dolayı Android Studio'da 
hatalar göreceksiniz. getMarsRealEstateProperties() çağrısına bir parametre olarak MarsApiFilter'ı (olası filtre değerlerinin listesi) ekleyin.

İstendiğinde com.example.android.marsrealestate.network.MarsApiFilter'ı import edin.

```kotlin
private fun getMarsRealEstateProperties(filter: MarsApiFilter) {
```

2. Bu filtre sorgusunu bir string olarak iletmek için Retrofit hizmetindeki getProperties() çağrısını değiştirin.

```kotlin
 _properties.value = MarsApi.retrofitService.getProperties(filter.value)
```

3. Uygulama ilk yüklendiğinde tüm özellikleri göstermek için init {} bloğunda, MarsApiFilter.SHOW_ALL öğesini 
getMarsRealEstateProperties() öğesine bir argüman olarak iletin.

```kotlin
init {
   getMarsRealEstateProperties(MarsApiFilter.SHOW_ALL)
}
```

4. Sınıfın sonuna, bir MarsApiFilter argümanı alan ve bu argümanla getMarsRealEstateProperties() 
öğesini çağıran bir updateFilter() metodu ekleyin.

```kotlin
fun updateFilter(filter: MarsApiFilter) {
   getMarsRealEstateProperties(filter)
}
```

### Adım 3: Fragment'ı seçenekler menüsüne bağlayın

Son adım, kullanıcı bir menü seçeneği seçtiğinde view modelde updateFilter() metodunu çağırmak için 
overflow_menu.xml'i fragment'a bağlamaktır.

1. res/menu/overflow_menu.xml dosyasını açın. MarsRealEstate uygulaması, mevcut üç seçeneği sağlayan mevcut 
bir overflow menüye sahiptir: tüm mülkleri gösterme, yalnızca kiralık mülkleri gösterme ve yalnızca satılık mülkleri gösterme.

```kotlin
<menu xmlns:android="http://schemas.android.com/apk/res/android">
   <item
       android:id="@+id/show_all_menu"
       android:title="@string/show_all" />
   <item
       android:id="@+id/show_rent_menu"
       android:title="@string/show_rent" />
   <item
       android:id="@+id/show_buy_menu"
       android:title="@string/show_buy" />
</menu>
```

2. overview/OverviewFragment.kt dosyasını açın. Sınıfın sonunda, menü öğesi seçimlerini işlemek için onOptionsItemSelected() yöntemini uygulayın.

```kotlin
override fun onOptionsItemSelected(item: MenuItem): Boolean {
} 
```

3. onOptionsItemSelected() içinde, uygun filtreyle görünüm modelinde updateFilter() yöntemini çağırın. 
Seçenekler arasında geçiş yapmak için {} bloğu olduğunda bir Kotlin kullanın. Varsayılan filtre değeri 
için MarsApiFilter.SHOW_ALL kullanın. Menü öğesini ele aldığınız için true değerini döndürün. 
İstendiğinde MarsApiFilter'ı (com.example.android.marsrealestate.network.MarsApiFilter) import edin. 
Tamamlanmış onOptionsItemSelected() metodu aşağıda gösterilmiştir.

```kotlin
override fun onOptionsItemSelected(item: MenuItem): Boolean {
   viewModel.updateFilter(
           when (item.itemId) {
               R.id.show_rent_menu -> MarsApiFilter.SHOW_RENT
               R.id.show_buy_menu -> MarsApiFilter.SHOW_BUY
               else -> MarsApiFilter.SHOW_ALL
           }
   )
   return true
}
```

4. Uygulamayı derleyin ve çalıştırın. Uygulama, tüm mülk türleri ve dolar simgesiyle işaretlenmiş satılık 
mülkler ile ilk overview ızgarasını başlatır.
5. Seçenekler menüsünden Rent'i seçin. Özellikler yeniden yüklenir ve hiçbiri dolar simgesiyle görünmez. 
(Yalnızca kiralık mülkler gösterilir.) Ekranın yalnızca filtrelenmiş mülkleri gösterecek şekilde yenilenmesi 
için birkaç dakika beklemeniz gerekebilir.
6. Seçenekler menüsünden Buy'ı seçin. Özellikler yeniden yüklenir ve hepsi dolar simgesiyle görünür. 
(Yalnızca satılık mülkler gösterilmektedir.)

## <a name="c"></a>Aşama 3 : Bir detay sayfası oluşturun ve navigasyonu ayarlayın

Artık Mars mülkleri için kaydırılabilir bir ızgaranız var ancak daha fazla detay almanın zamanı geldi. 
Bu görevde, belirli bir özelliğin ayrıntılarını görüntülemek için bir detail fragment eklersiniz. Bu detail fragment, 
ister kiralık ister satılık olsun, daha büyük bir resim, fiyat ve mülk türünü gösterecektir.

![image](https://user-images.githubusercontent.com/29903779/151581111-f2c5d6fd-68b8-4271-ba94-3a4954271148.png)

Bu fragment, kullanıcı overview kılavuzunda bir resme dokunduğunda başlatılır. Bunu başarmak için, RecyclerView 
ızgara öğelerine bir onClick dinleyicisi eklemeniz ve ardından yeni parçaya gitmeniz gerekir. Bu dersler boyunca 
yaptığınız gibi, ViewModel'de bir LiveData değişikliğini tetikleyerek gezinirsiniz. Ayrıca, seçilen MarsProperty 
bilgilerini overview fragment'tan detail fragment'a geçirmek için Navigation bileşeninin Safe Args eklentisini 
de kullanırsınız.

### Adım 1: Detail view modeli oluşturun ve detail tasarımını güncelleyin

Overview view modeli ve fragment'ları için kullandığınız işleme benzer şekilde, şimdi detail fragment için 
view modeli ve tasarım dosyalarını düzenlemeniz gerekir.

1. detail/DetailViewModel.kt dosyasını açın. Servisle ilgili Kotlin dosyalarının network klasöründe ve overview dosyalarının 
overview'da yer alması gibi, detail klasörü de detail görünümüyle ilişkili dosyaları içerir. DetailViewModel 
sınıfının (şu anda boş), constructor'da parametre olarak bir marsProperty aldığına dikkat edin.

```kotlin
class DetailViewModel( marsProperty: MarsProperty,
                     app: Application) : AndroidViewModel(app) {
}
```

2. Sınıf tanımının içinde, bu bilgileri detay görünümüne sunmak için seçilen Mars özelliği için LiveData ekleyin. 
MarsProperty'nin kendisini tutmak için bir MutableLiveData oluşturma modelini izleyin ve ardından değişmeyen (immutable)
bir LiveData özelliğini ortaya çıkarın.

androidx.lifecycle.LiveData'yı içe aktarın ve istendiğinde androidx.lifecycle.MutableLiveData'yı import edin.

```kotlin
private val _selectedProperty = MutableLiveData<MarsProperty>()
val selectedProperty: LiveData<MarsProperty>
   get() = _selectedProperty
```

3. Bir init {} bloğu oluşturun ve yapıcıdan MarsProperty nesnesiyle seçilen Mars özelliğinin değerini ayarlayın.
   
```kotlin
init {
        _selectedProperty.value = marsProperty
    }
```

4. res/layout/fragment_detail.xml dosyasını açın ve tasarım görünümünde ona bakın.

Bu, detail fragment'ın tasarım dosyasıdır. Büyük fotoğraf için bir ImageView, mülk türü (kiralık veya satış) için 
bir TextView ve fiyat için bir TextView içerir. Constraint layout'un bir ScrollView ile sarıldığına dikkat edin. 
Böylece görünüm ekran için çok büyürse, örneğin kullanıcı onu yatay modda görüntülediğinde otomatik olarak kaydırılır.
5. Layout içinde Metin sekmesine gidin. Layout'un en üstünde `<ScrollView>` öğesinden hemen önce, detail view modelini 
layout ile ilişkilendirmek için bir `<data>` öğesi ekleyin.

```kotlin
<data>
   <variable
       name="viewModel"
       type="com.example.android.marsrealestate.detail.DetailViewModel" />
</data>
```

6. app:imageUrl niteliğini ImageView öğesine ekleyin. View modelin seçili özelliğinden imgSrcUrl olarak ayarlayın.

Glide kullanarak bir görüntüyü yükleyen binding adapter burada da otomatik olarak kullanılacaktır, 
çünkü bu adapter tüm app:imageUrl özniteliklerini izler.

```kotlin
 app:imageUrl="@{viewModel.selectedProperty.imgSrcUrl}"
```

### Adım 2: Overview view modelinde navigation tanımlayın

Kullanıcı overview modelinde bir fotoğrafa dokunduğunda, tıklanan öğeyle ilgili detayları gösteren 
bir fragment navigasyonu tetiklemelidir.

1. overview/OverviewViewModel.kt dosyasını açın. Bir _navigateToSelectedProperty MutableLiveData özelliği 
ekleyin ve bunu değişmez bir LiveData ile gösterin.

Bu LiveData null olmayan olarak değiştiğinde, gezinme tetiklenir. (Yakında bu değişkeni gözlemlemek ve 
navigasyonu tetiklemek için kodu ekleyeceksiniz.)

```kotlin
private val _navigateToSelectedProperty = MutableLiveData<MarsProperty>()
val navigateToSelectedProperty: LiveData<MarsProperty>
   get() = _navigateToSelectedProperty
```

2. Sınıfın sonunda, _navigateToSelectedProperty öğesini seçili Mars özelliğine ayarlayan bir displayPropertyDetails() metodu ekleyin.

```kotlin
fun displayPropertyDetails(marsProperty: MarsProperty) {
   _navigateToSelectedProperty.value = marsProperty
}
```

3. _navigateToSelectedProperty değerini geçersiz kılan bir displayPropertyDetailsComplete() metodu ekleyin. 
Navigation durumunu tamamlamak için işaretlemek ve kullanıcı detay görünümünden döndüğünde navigation'ın yeniden 
tetiklenmesini önlemek için buna ihtiyacınız vardır.

```kotlin
fun displayPropertyDetailsComplete() {
   _navigateToSelectedProperty.value = null
}
```
