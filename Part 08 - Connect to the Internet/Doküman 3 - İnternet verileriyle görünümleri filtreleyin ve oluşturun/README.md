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

### Adım 3: Grid (Izgara) adapter ve fragment içerisindeki listener'ları ayarlayın

1. overview/PhotoGridAdapter.kt dosyasını açın. Sınıfın sonunda, marsProperty parametresiyle bir lambda alan özel 
bir OnClickListener sınıfı oluşturun. Sınıf içinde, lambda parametresine ayarlanmış bir onClick() işlevi tanımlayın.

```kotlin
class OnClickListener(val clickListener: (marsProperty:MarsProperty) -> Unit) {
     fun onClick(marsProperty:MarsProperty) = clickListener(marsProperty)
}
```

2. PhotoGridAdapter için sınıf tanımına gidin ve constructor'a özel bir OnClickListener özelliği ekleyin.

```kotlin
class PhotoGridAdapter( private val onClickListener: OnClickListener ) :
       ListAdapter<MarsProperty,              
           PhotoGridAdapter.MarsPropertyViewHolder>(DiffCallback) {
```

3. onBindviewHolder() metodunda, ızgara öğesine onClickListener ekleyerek bir fotoğrafı tıklanabilir yapın. 
getItem() ve bind() çağrıları arasında tıklamaya ait listener'ları tanımlayın.

```kotlin
override fun onBindViewHolder(holder: MarsPropertyViewHolder, position: Int) {
   val marsProperty = getItem(position)
   holder.itemView.setOnClickListener {
       onClickListener.onClick(marsProperty)
   }
   holder.bind(marsProperty)
}
```

4. overview/OverviewFragment.kt dosyasını açın. onCreateView() metodunda, binding.photosGrid.adapter özelliğini 
başlatan satırı aşağıda gösterilen satırla değiştirin.

Bu kod, PhotoGridAdapter.onClickListener nesnesini PhotoGridAdapter constructor'ına ekler ve geçirilen MarsProperty 
nesnesiyle viewModel.displayPropertyDetails() öğesini çağırır. Bu, navigation için view modelde LiveData'yı tetikler.

```kotlin
binding.photosGrid.adapter = PhotoGridAdapter(PhotoGridAdapter.OnClickListener {
   viewModel.displayPropertyDetails(it)
})
```

### Adım 4: Navigation graph'ı değiştirin ve MarsProperty'yi parcelable hale getirin

Bir kullanıcı overview grid içerisinde bir fotoğrafa dokunduğunda uygulama detail fragment'a gitmeli ve seçilen Mars 
özelliğinin detayları geçmelidir. Böylece detay görünümü bu bilgileri görüntüleyebilir.

![image](https://user-images.githubusercontent.com/29903779/151699650-7892a209-5315-452f-b038-f9fecfe9b133.png)

Şu anda dokunma durumunu kontrol etmek için PhotoGridAdapter'da bir tıklama listener'ınız ve view modelden navigation'ı 
tetiklemenin bir yolu var. Ancak henüz detail fragment'a geçirilen bir MarsProperty nesneniz yok. Bunun için navigation 
bileşenindeki Safe Args'ı kullanırsınız.

1. res/navigation/nav_graph.xml dosyasını açın. Navigation graph'ın XML kodunu görüntülemek için Text sekmesine tıklayın.
2. Detail fragment için `<fragment>` öğesinin içine, aşağıda gösterilen `<argument>` öğesini ekleyin. SelectedProperty olarak 
adlandırılan bu argüman, MarsProperty tipine sahiptir.

```kotlin
<argument
   android:name="selectedProperty"
   app:argType="com.example.android.marsrealestate.network.MarsProperty"
   />
```

3. Uygulamayı derleyin. Navigation size bir hata veriyor çünkü MarsProperty parcelable değil. Parcelable arabirimini, nesnelerin 
serileştirilmesine (serialized) olanak tanır, böylece nesnelerdeki veriler fragment veya activity'ler arasında iletilebilir. Bu durumda, 
MarsProperty nesnesinin içindeki verilerin Safe Args aracılığıyla detail fragment'a geçirilmesi için MarsProperty'nin Parcelable
arabirimini uygulaması gerekir. İyi haber şu ki, Kotlin bu arayüzü uygulamak için kolay bir kısayol sağlıyor.
4. network/MarsProperty.kt'yi açın. @Parcelize annotation'ını sınıf tanımına ekleyin.

İstendiğinde kotlinx.android.parcel.Parcelize dosyasını import edin.

@Parcelize annotation'ı, bu sınıf için Parcelable arabirimindeki yöntemleri otomatik olarak uygulamak için Kotlin Android 
uzantılarını kullanır. Başka bir şey yapmanıza gerek yok!

```kotlin
@Parcelize
data class MarsProperty (
```

5. Parcelable'ı extend etmek için MarsProperty'nin sınıf tanımını değiştirin.

İstendiğinde android.os.Parcelable dosyasını import edin.

MarsProperty sınıf tanımı şimdi şöyle görünür:

```kotlin
@Parcelize
data class MarsProperty (
       val id: String,
       @Json(name = "img_src") val imgSrcUrl: String,
       val type: String,
       val price: Double) : Parcelable {
```

### Adım 5: Fragment'ları bağlayın

Hâlâ navigation yapmıyorsunuz (gerçek navigation fragment'lar arasında oluyor). Bu adımda, overview ve detail fragment'ları arasında 
navigation uygulamak için son kodları eklersiniz.

1. overview/OverviewFragment.kt dosyasını açın. onCreateView() içinde fotoğraf grid adapter'ını başlatan satırların 
altına, overview view modelinden navigasyondToSelectedProperty'yi gözlemlemek için aşağıda gösterilen satırları ekleyin.

androidx.lifecycle.Observer'ı içe aktarın ve istendiğinde androidx.navigation.fragment.findNavController'ı import edin.

Gözlemci (Observer), lambda'da bulunan MarsProperty'nin boş olup olmadığını test eder ve eğer öyleyse navigation controller'ı 
findNavController() ile parçadan alır. View modelde, LiveData'yı null olarak sıfırlamasını söylemek için displayPropertyDetailsComplete() 
öğesini çağırın. Böylece uygulama OverviewFragment'a geri döndüğünde yanlışlıkla navigation'ı yeniden tetiklemezsiniz.

```kotlin
viewModel.navigateToSelectedProperty.observe(this, Observer {
   if ( null != it ) {   
      this.findNavController().navigate(
              OverviewFragmentDirections.actionShowDetail(it))             
      viewModel.displayPropertyDetailsComplete()
   }
})
```

2. detail/DetailFragment.kt dosyasını açın. onCreateView() metodunda binding.lifecycleOwner satırının hemen 
altına bu satırı ekleyin. Bu satır, Safe Args'dan seçilen MarsProperty nesnesini alır.

Kotlin'in boş olmayan onaylama operatörünün (!!) kullanımına dikkat edin. SelectedProperty orada değilse, korkunç bir şey 
olmuştur ve aslında kodun bir boş gösterici atmasını istiyorsunuz. (Üretim kodunda, bu hatayı bir şekilde ele almalısınız.)

```kotlin
 val marsProperty = DetailFragmentArgs.fromBundle(arguments!!).selectedProperty
```

3. Yeni bir DetailViewModelFactory almak için bu satırı ekleyin. DetailViewModel'in bir örneğini almak için 
DetailViewModelFactory'yi kullanacaksınız. Başlangıç uygulaması, DetailViewModelFactory'nin bir uygulamasını içerir.
Bu nedenle burada yapmanız gereken tek şey onu başlatmaktır.

```kotlin
val viewModelFactory = DetailViewModelFactory(marsProperty, application)
```

4. Son olarak, factory'den bir DetailViewModel almak ve tüm fragment'ları bağlamak için bu satırı ekleyin.

```kotlin
binding.viewModel = ViewModelProvider(
                this, viewModelFactory).get(DetailViewModel::class.java)
```

5. Uygulamayı derleyin, çalıştırın ve herhangi bir Mars mülkü fotoğrafına dokunun. Detail fragment, o mülkün detayları 
ile görünür. Overview sayfasına dönmek için Geri düğmesine dokunun ve detay ekranının hâlâ biraz seyrek olduğuna 
dikkat edin. Bir sonraki görevde özelliğe ait verileri bu detay sayfasına ekleme işlemini tamamlayacaksınız.

## <a name="d"></a>Aşama 4 : Daha kullanışlı bir detay sayfası oluşturun

Şu anda detay sayfası, yalnızca genel bakış sayfasında görmeye alışık olduğunuz Mars fotoğrafını gösteriyor. 
MarsProperty sınıfının ayrıca bir mülk türü (kiralama veya satın alma) ve bir mülk fiyatı vardır. Detay ekranı bu 
değerlerin her ikisini de içermelidir ve kiralık mülklerin fiyatın aylık bir değer olduğunu belirtmesi yararlı olacaktır. 
Her ikisini de uygulamak için view modelde LiveData dönüşümlerini kullanırsınız.

1. res/values/strings.xml dosyasını açın. Başlangıç kodu, detaylı görünüm için string'leri oluşturmanıza yardımcı olmak üzere 
aşağıda gösterilen string kaynaklarını içerir. Fiyat için, mülk türüne bağlı olarak display_price_monthly_rental kaynağını veya 
display_price kaynağını kullanacaksınız.

```kotlin
<string name="type_rent">Rent</string>
<string name="type_sale">Sale</string>
<string name="display_type">For %s</string>
<string name="display_price_monthly_rental">$%,.0f/month</string>
<string name="display_price">$%,.0f</string>
```

2. detail/DetailViewModel.kt dosyasını açın. Sınıfın en altına, aşağıda gösterilen kodu ekleyin.

İstenirse androidx.lifecycle.Transformations'ı import edin.

Bu dönüşüm, ilk görevdeki aynı testi kullanarak seçilen mülkün kiralık olup olmadığını test eder. Özellik kiralık ise, 
dönüşüm Kotlin when{} ile kaynaklardan uygun string'i seçer. Bu string'lerin her ikisinin de sonunda bir sayı olması 
gerekir. Bu nedenle daha sonra property.price öğesini birleştirirsiniz.

```kotlin
val displayPropertyPrice = Transformations.map(selectedProperty) {
   app.applicationContext.getString(
           when (it.isRental) {
               true -> R.string.display_price_monthly_rental
               false -> R.string.display_price
           }, it.price)
}
```

3. Projedeki string kaynaklarına erişmek için oluşturulan R sınıfını import edin.

```kotlin
import com.example.android.marsrealestate.R
```

4. displayPropertyPrice dönüşümünden sonra aşağıda gösterilen kodu ekleyin. Bu dönüşüm, mülk türünün kiralık olup olmamasına 
bağlı olarak birden çok string kaynağını birleştirir.

```kotlin
val displayPropertyType = Transformations.map(selectedProperty) {
   app.applicationContext.getString(R.string.display_type,
           app.applicationContext.getString(
                   when (it.isRental) {
                       true -> R.string.type_rent
                       false -> R.string.type_sale
                   }))
}
```

5. res/layout/fragment_detail.xml dosyasını açın. Yapılması gereken bir şey daha var ve o da (LiveData dönüşümleriyle oluşturduğunuz) 
yeni string'leri detay görünümüne bağlamak. Bunu yapmak için özellik türüne ait metin alanının değerini viewModel.displayPropertyType 
olarak ve fiyat değerine ait metin alanını da viewModel.displayPropertyPrice olarak ayarlayın.

```kotlin
<TextView
   android:id="@+id/property_type_text"
...
android:text="@{viewModel.displayPropertyType}"
...
   tools:text="To Rent" />

<TextView
   android:id="@+id/price_value_text"
...
android:text="@{viewModel.displayPropertyPrice}"
...
   tools:text="$100,000" />
```

6. Uygulamayı derleyin ve çalıştırın. Artık tüm mülk verileri, güzel bir şekilde biçimlendirilmiş olarak detay sayfasında görünür.

![image](https://user-images.githubusercontent.com/29903779/151700621-e042924c-b893-453f-b72b-4e4ae3f64f57.png)
