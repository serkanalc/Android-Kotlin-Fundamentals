# <a name="1"></a>İnternetten görüntüleri yükleme ve görüntüleme

- [Bir internet görseli göster](#a)
- [RecyclerView ile bir görsel ızgarası görüntüleyin](#b)
- [RecyclerView'da hata kontrolü ekleyin](#c)

Bu codelab içerisinde (ve sonraki codelab’lerde), Mars'ta ait satılık olan özellikleri gösteren MarsRealEstate 
adlı bir başlangıç uygulaması üzerine çalışıyorsunuz. Bu uygulama, fiyat ve mülkün satılık veya kiralık olup 
olmadığı gibi ayrıntılar da dahil olmak üzere mülk verilerini almak ve görüntülemek için bir web hizmetine bağlanır 
ve her bir mülkü temsil eden görüntüler, NASA'nın Mars gezicilerinden çekilen Mars'tan alınan gerçek hayat fotoğraflarıdır.

![image](https://user-images.githubusercontent.com/29903779/150182462-f6d202bb-f305-4d5e-b0a6-593b565ca689.png)

Bu codelab’te oluşturduğunuz uygulamanın sürümü, bir resim ızgarası görüntüleyen overview sayfasını doldurur. 
Görüntüler, uygulamanızın Mars emlak web servisinden aldığı mülk verilerinin bir parçasıdır. Uygulamanız, 
görüntüleri yüklemek ve görüntülemek için Glide kitaplığını ve görüntüler için ızgara düzeni oluşturmak için 
bir RecyclerView kullanacaktır. Uygulamanız ayrıca ağ hatalarını zarif bir şekilde işleyecektir.

## <a name="a"></a>Aşama 1 : Bir internet görseli göster

Bir web URL'sinden bir fotoğraf görüntülemek basit gelebilir, ancak bunun iyi çalışmasını sağlamak için oldukça 
fazla yöntem vardır. Görüntü indirilmeli, dahili olarak saklanmalı ve sıkıştırılmış biçiminden Android'in kullanabileceği 
bir görüntüye dönüştürülmelidir. Görüntü bir bellek içi önbelleğe, depolama tabanlı bir önbelleğe veya her ikisine
önbelleğe alınmalıdır. Tüm bunlar, kullanıcı arayüzünün duyarlı kalması için düşük öncelikli arka plan thread’lerinde 
gerçekleşmelidir. Ayrıca, en iyi ağ ve CPU performansı için, aynı anda birden fazla görüntüyü alıp kodunu çözmek isteyebilirsiniz. 
Görüntülerin ağdan nasıl verimli bir şekilde yükleneceğini öğrenmek başlı başına bir codelab olabilir.

Neyse ki resimlerinizi indirmek, arabelleğe almak, kodunu çözmek ve önbelleğe almak için topluluk tarafından geliştirilen
Glide adlı bir kitaplığı kullanabilirsiniz. Glide kullanmadan yapacak daha çok işiniz olurdu.

Glide temelde iki şeye ihtiyaç duyar:
1. Yüklemek ve görüntülemek istediğiniz resmin URL'si.
2. Bu görüntüyü görüntülemek için bir ImageView nesnesi.

Bu görevde, gayrimenkul web servisinden tek bir görüntüyü görüntülemek için Glide'ı nasıl kullanacağınızı öğreneceksiniz.
Web servisinin döndürdüğü özellikler listesinde ilk Mars özelliğini temsil eden görüntüyü görüntülersiniz. İşte öncesi ve 
sonrası ekran görüntüleri:

![image](https://user-images.githubusercontent.com/29903779/150182869-b32e61b1-5abf-4a38-bd06-25bebd68dbc2.png)

### Adım 1: Glide bağımlılığı ekleyin
1. Son codelab içerisindeki MarsRealEstate uygulamasını açın. (Uygulamanız yoksa MarsRealEstateNetwork'ü buradan indirebilirsiniz.)
2. Ne yaptığını görmek için uygulamayı çalıştırın (alınan toplam Mars mülkü sayısını gösterir).
3. build.gradle'ı açın (Module: app).
4. dependencies bölümünde, Glide kitaplığı için şu satırı ekleyin:

```kotlin
implementation "com.github.bumptech.glide:glide:$version_glide"
```

Sürüm numarasının proje Gradle dosyasında zaten ayrı olarak tanımlandığına dikkat edin.
5. Projeyi yeni bağımlılıkla yeniden oluşturmak için Sync Now’a tıklayın.

### Adım 2: view modeli güncelleyin

Ardından OverviewViewModel sınıfını, tek bir Mars özelliği için live data içerecek şekilde güncellersiniz.

1. overview/OverviewViewModel.kt dosyasını açın. _response için LiveData'nın hemen altına, tek bir MarsProperty nesnesi için hem dahili (değişebilir) hem de harici (değiştirilemez) livedata ekleyin.

İstendiğinde MarsProperty sınıfını (com.example.android.marsrealestate.network.MarsProperty) import edin.

```kotlin
private val _property = MutableLiveData<MarsProperty>()

val property: LiveData<MarsProperty>
   get() = _property
```

2. getMarsRealEstateProperties() metodunda, try/catch {} bloğunun içinde, _response.value değerini özellik sayısına ayarlayan satırı bulun. try/catch işleminden sonra aşağıda gösterilen testi ekleyin. MarsProperty nesneleri mevcutsa bu test, _property LiveData'nın değerini listResult'daki ilk özelliğe ayarlar.

```kotlin
if (listResult.size > 0) {   
    _property.value = listResult[0]
}
```

Tamamlanan try/catch{} bloğu şimdi şöyle görünür:

```kotlin
try {
   val listResult = MarsApi.retrofitService.getProperties()
   _response.value = "Success: ${listResult.size} Mars properties retrieved"
    if (listResult.size > 0) {      
       _property.value = listResult[0]
   }
} catch (e: Exception) {
  _response.value = "Failure: ${e.message}"
}
```

3. res/layout/fragment_overview.xml dosyasını açın. `<TextView>` öğesinde, LiveData özelliğinin imgSrcUrl bileşenine bağlanmak için android:text öğesini değiştirin:

```kotlin
android:text="@{viewModel.property.imgSrcUrl}"
```

4. Uygulamayı çalıştırın. TextView, yalnızca ilk Mars özelliğindeki görüntünün URL'sini görüntüler. Şimdiye kadar yaptığınız tek şey, o URL için view modeli ve livedata ayarlamak.

![image](https://user-images.githubusercontent.com/29903779/150183264-8b3ba38f-c4f6-48e4-b88c-d8ebde200a42.png)

### Adım 3: Bir binding adapter oluşturun ve Glide'ı çağırın

Artık görüntülenecek bir resmin URL'sine sahipsiniz, o resmi yüklemek için Glide ile çalışmaya başlamanın zamanı geldi. 
Bu adımda, bir ImageView ile ilişkili bir XML özelliğinden URL'yi almak için bir binding adapter kullanırsınız ve resmi o 
URL'den yüklemek için Glide kullanırsınız. Binding adapter’lar, veriler değiştiğinde özel davranış sağlamak için bir 
görünüm ile bağlı veriler arasında bağlantı kuran metotlardır. Bu durumda yapılacak özel davranış, bir URL'den bir ImageView'e 
bir resim yüklemek için Glide'ı çağırmaktır.

1. BindingAdapters.kt'yi açın. Bu dosya, uygulama boyunca kullandığınız binding adapter’ları barındıracaktır.
2. Parametre olarak ImageView ve String alan bir bindImage() işlevi oluşturun. @BindingAdapter ile işleve açıklama ekleyin. 
@BindingAdapter annotation’ı, bir XML öğesi imageUrl özelliğine sahip olduğunda bu binding adapter’ın çalıştırılmasının 
istendiğini veri bağlamaya bildirir.

İstendiğinde androidx.databinding.BindingAdapter ve android.widget.ImageView'ı import edin.

```kotlin
@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {

}
```

3. bindImage() metodunun içine, imgUrl değişkeni için bir let {} bloğu ekleyin:

```kotlin
imgUrl?.let { 
}
```

4. URL string değerini (XML'den) bir Uri nesnesine dönüştürmek için let {} bloğunun içine aşağıda gösterilen satırı ekleyin. 
İstendiğinde androidx.core.net.toUri'yi import edin.

Son Uri nesnesinin HTTPS şemasını kullanmasını istiyorsunuz çünkü görüntüleri çektiğiniz sunucu bu güvenli şemayı gerektiriyor. 
HTTPS şemasını kullanmak için toUri oluşturucuya buildUpon.scheme("https") ekleyin. toUri() metodu, Android KTX çekirdek 
kitaplığından bir Kotlin uzantısı metodudur, bu nedenle String sınıfının bir parçası gibi görünür.

```kotlin
val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
```

Yine de {} içinde, görüntüyü Uri nesnesinden ImageView'e yüklemek için Glide.with()'i çağırın. İstendiğinde com.bumptech.glide.Glide'ı import edin.

```kotlin
Glide.with(imgView.context)
       .load(imgUri)
       .into(imgView)
```

Module build.gradle dosyanıza aşağıdaki seçenekleri eklemeniz gerekebilir:

```kotlin
android {
...
   kotlinOptions {
        jvmTarget = JavaVersion.VERSION_1_8.toString()
   }
...
}
```

### Adım 4: Layout ve fragment’ları güncelleyin

Glide görüntüyü yüklemiş olsa da, henüz görülecek bir şey yok. Sonraki adım, görüntüyü görüntülemek için layout ve 
fragment'ları bir ImageView ile güncellemektir.

1. res/layout/gridview_item.xml dosyasını açın. Bu, daha sonra codelab'de RecyclerView'daki her öğe için kullanacağınız 
layout kaynak dosyasıdır. Sadece tek bir görüntüyü göstermek için burayı geçici olarak kullanıyorsunuz.

2. `<ImageView>` öğesinin üzerine, data binding için bir `<data>` öğesi ekleyin ve OverviewViewModel sınıfına bağlayın:

```kotlin
<data>
   <variable
       name="viewModel"
       type="com.example.android.marsrealestate.overview.OverviewViewModel" />
</data>
```

3. Yeni resim yükleme amaçlı oluşturulan binding adapter’ı kullanmak için ImageView öğesine bir app:imageUrl niteliği ekleyin:

```kotlin
app:imageUrl="@{viewModel.property.imgSrcUrl}"
```

4. overview/OverviewFragment.kt dosyasını açın. onCreateView() metodunda, FragmentOverviewBinding sınıfını oluşturan ve onu 
  binding değişkenine atayan satırı yorumlayın. Bu satırın kaldırılmasından kaynaklanan hatalar göreceksiniz. Bu sadece geçicidir; 
  daha sonra ona geri döneceksin.

```kotlin
//val binding = FragmentOverviewBinding.inflate(inflater)
```

5. Bunun yerine GridViewItemBinding sınıfını oluşturmak için bir satır ekleyin. databinding.GridViewItemBinding istenirse, 
  com.example.android.marsrealestate'i import edin. 

>Not: Bu değişiklik, Android Studio'da data binding hatalarına neden olabilir. Bu hataları gidermek için uygulamayı clean ve 
  rebuild etmeniz gerekebilir.

```kotlin
val binding = GridViewItemBinding.inflate(inflater)
```

Uygulamayı çalıştırın. Şimdi sonuç listesinde ilk MarsProperty'den görüntünün bir fotoğrafını görmelisiniz.

![image](https://user-images.githubusercontent.com/29903779/150183942-804ea842-07e5-42c7-9211-08f5c908bc7a.png)

### Adım 5: Basit loading ve error görselleri ekleyin

Glide, resim yüklenirken bir yer tutucu resim ve yükleme başarısız olursa veya bozuksa bir hata resmi göstererek kullanıcı 
deneyimini iyileştirebilir. Bu adımda, bu işlevi binding adapter’a ve layout’a eklersiniz.

1. res/drawable/ic_broken_image.xml dosyasını açın ve sağdaki Design sekmesine tıklayın. Hata görseli için yerleşik simge 
kitaplığında bulunan bozuk görüntü simgesini kullanıyorsunuz. Bu çizilebilir vektör, simgeyi griye boyamak için Android:tint 
özelliğini kullanır.

![image](https://user-images.githubusercontent.com/29903779/150184080-1b9e5a3b-58ec-402f-85a0-7a7a6c780af6.png)

2. res/drawable/loading_animation.xml dosyasını açın. Bu çizilebilir `<animated-rotate>` etiketiyle tanımlanmış bir animasyondur. 
Animasyon, çizilebilir bir resmi loading_img.xml’in merkez noktası etrafında döndürür. (Önizlemede animasyonu görmezsiniz.)

![image](https://user-images.githubusercontent.com/29903779/150184142-f632b0a5-49da-4abf-bf0e-30deb9bb0004.png)

3. BindingAdapters.kt dosyasına dönün. bindImage() metodunda, load() ve into() arasında application() fonksiyonunu çağırmak 
için Glide.with() çağrısını güncelleyin. İstendiğinde com.bumptech.glide.request.RequestOptions'ı import edin.

Bu kod, yükleme sırasında kullanılacak yer tutucu yükleme görselini ayarlar (loading_animation çizilebilir). Kod ayrıca, 
görsel yükleme başarısız olursa kullanılacak bir görsel ayarlar (broken_image çizilebilir). Tamamlanmış bindImage() metodu 
şimdi şöyle görünür:

```kotlin
@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        val imgUri = 
           imgUrl.toUri().buildUpon().scheme("https").build()
        Glide.with(imgView.context)
                .load(imgUri)
                .apply(RequestOptions()
                        .placeholder(R.drawable.loading_animation)
                        .error(R.drawable.ic_broken_image))
                .into(imgView)
    }
}
```

4. Uygulamayı çalıştırın. Ağ bağlantınızın hızına bağlı olarak, Glide özellik görselini indirip görüntülerken yükleme 
görselini kısaca görebilirsiniz. Ancak ağınızı kapatsanız bile broken-image simgesini henüz görmezsiniz. Bunu codelab'in 
son bölümünde düzeltirsiniz.

## <a name="b"></a>Aşama 2 : RecyclerView ile bir görsel ızgarası görüntüleyin

Uygulamanız artık Mars gayrimenkul bilgilerini internetten yüklüyor. İlk MarsProperty liste öğesindeki verileri kullanarak, 
görünüm modelinde bir LiveData özelliği oluşturdunuz ve bir ImageView'i doldurmak için bu özellik verilerinden gelen resim 
URL'sini kullandınız. Ancak amaç, uygulamanızın bir resim ızgarası görüntülemesidir, bu nedenle GridLayoutManager ile bir 
RecyclerView kullanmak istiyorsunuz.

### Adım 1: View modeli güncelleyin

Şu anda görünüm modelinde bir MarsProperty nesnesini tutan bir _property LiveData değeri vardır (Web servisinin yanıt listesindeki 
ilk nesne). Bu adımda, bu LiveData'yı MarsProperty nesnelerinin tüm listesini tutacak şekilde değiştirirsiniz.
1. overview/OverviewViewModel.kt dosyasını açın.
2. Özel _property değişkenini _properties olarak değiştirin. Türü, MarsProperty nesnelerinin listesi olacak şekilde değiştirin.

```kotlin
private val _properties = MutableLiveData<List<MarsProperty>>()
```

3. LiveData property değişkenini properties ile değiştirin. Listeyi LiveData türüne buradan da ekleyin:

```kotlin
val properties: LiveData<List<MarsProperty>>
        get() = _properties
```

4. getMarsRealEstateProperties() yöntemine ilerleyin. try {} bloğunun içinde, önceki görevde eklediğiniz tüm testi aşağıda 
gösterilen satırla değiştirin. MarsApi.retrofitService.getProperties(), MarsProperty nesnelerinin bir listesini döndürdüğünden, 
başarılı bir yanıtı test etmek yerine onu yalnızca _properties.value öğesine atayabilirsiniz.

```kotlin
_properties.value = MarsApi.retrofitService.getProperties()
```

Tamamlanan try/catch bloğu şimdi şöyle görünür:

```kotlin
try {
    _properties.value = MarsApi.retrofitService.getProperties()   
    _response.value = "Success: Mars properties retrieved"
} catch (e: Exception) {
   _response.value = "Failure: ${e.message}"
}
```

### Adım 2: Layout’ları ve fragment’ları güncelleyin

Sonraki adım, uygulamanın layout ve fragment’larını, tek imageview görünümü yerine bir recyclerview görünümü ve bir ızgara düzeni 
kullanacak şekilde değiştirmektir.
1. res/layout/gridview_item.xml dosyasını açın. OverviewViewModel'den MarsProperty'ye bağlantı kuran data binding değişkenini değiştirin 
ve değişkeni "property" olarak yeniden adlandırın.

```kotlin
<variable
   name="property"
   type="com.example.android.marsrealestate.network.MarsProperty" />
```

2. `<ImageView>` içinde, MarsProperty nesnesindeki resim URL'sine başvurmak için app:imageUrl özniteliğini değiştirin:

```kotlin
app:imageUrl="@{property.imgSrcUrl}"
```

3. overview/OverviewFragment.kt dosyasını açın. onCreateview() içinde, FragmentOverviewBinding'i oluşturan satırın yorumunu kaldırın. 
  GridViewBinding'i oluşturan satırı silin veya yorum satırı haline getirin. Bu değişiklikler, son görevde yaptığınız geçici değişiklikleri geri alır.

```kotlin
val binding = FragmentOverviewBinding.inflate(inflater)
 // val binding = GridViewItemBinding.inflate(inflater)
```

4. res/layout/fragment_overview.xml dosyasını açın. `<TextView>` öğesinin tamamını silin.
5. Bunun yerine, tek bir öğe için GridLayoutManager ve grid_view_item düzenini kullanan bu `<RecyclerView>` öğesini ekleyin:

```kotlin
<androidx.recyclerview.widget.RecyclerView
            android:id="@+id/photos_grid"
            android:layout_width="0dp"
            android:layout_height="0dp"
            android:padding="6dp"
            android:clipToPadding="false"
            app:layoutManager=
               "androidx.recyclerview.widget.GridLayoutManager"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintLeft_toLeftOf="parent"
            app:layout_constraintRight_toRightOf="parent"
            app:layout_constraintTop_toTopOf="parent"
            app:spanCount="2"
            tools:itemCount="16"
            tools:listitem="@layout/grid_view_item" />
```

### Adım 3: Fotoğraf ızgarası adaptörünü ekleyin

Şimdi fragment_overview düzeninde bir RecyclerView varken grid_view_item düzeninde tek bir ImageView var. Bu adımda, 
verileri bir RecyclerView adapteraracılığıyla RecyclerView'a bağlarsınız.

Not: Bu, RecyclerView codelab’ini gözden geçirmek için iyi bir zaman olabilir!

1. overview/PhotoGridAdapter.kt dosyasını açın.
2. Aşağıda gösterilen constructor parametreleriyle PhotoGridAdapter sınıfını oluşturun. PhotoGridAdapter sınıfı, constructor 
liste öğesi türüne, view holder ve bir DiffUtil.ItemCallback implementation’ı için ihtiyaç duyulan ListAdapter'ı extend eder.

İstendiğinde androidx.recyclerview.widget.ListAdapter ve com.example.android.marsrealestate.network.MarsProperty sınıflarını 
import edin. Aşağıdaki adımlarda, bu constructor’ın hata üreten diğer eksik kısımlarını uygularsınız.

```kotlin
class PhotoGridAdapter : ListAdapter<MarsProperty,
        PhotoGridAdapter.MarsPropertyViewHolder>(DiffCallback) {

}
```

3. PhotoGridAdapter sınıfında herhangi bir yere tıklayın ve onCreateViewHolder() ve onBindViewHolder() olan ListAdapter yöntemlerini 
uygulamak için Control+i tuşlarına basın.

```kotlin
override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoGridAdapter.MarsPropertyViewHolder {
   TODO("not implemented") 
}

override fun onBindViewHolder(holder: PhotoGridAdapter.MarsPropertyViewHolder, position: Int) {
   TODO("not implemented") 
}
```

4. PhotoGridAdapter sınıf tanımının sonunda az önce eklediğiniz yöntemlerden sonra, aşağıda gösterildiği gibi DiffCallback için bir
nesne tanımı ekleyin.

İstendiğinde androidx.recyclerview.widget.DiffUtil'i import edin.
DiffCallback nesnesi, DiffUtil.ItemCallback öğesini karşılaştırmak istediğiniz nesne türü olan MarsProperty ile extend eder.

```kotlin
companion object DiffCallback : DiffUtil.ItemCallback<MarsProperty>() {
}
```

5. areItemsTheSame() ve areContentsTheSame() olan bu nesne için karşılaştırıcı yöntemleri uygulamak için Control+i tuşlarına basın.

```kotlin
override fun areItemsTheSame(oldItem: MarsProperty, newItem: MarsProperty): Boolean {
   TODO("not implemented") 
}

override fun areContentsTheSame(oldItem: MarsProperty, newItem: MarsProperty): Boolean {
   TODO("not implemented") }
```

6. areItemsTheSame() yöntemi için TODO öğesini kaldırın. oldItem ve newItem için nesne başvuruları aynıysa, true değerini döndüren Kotlin'in 
referans eşitlik operatörünü (===) kullanın.

```kotlin
override fun areItemsTheSame(oldItem: MarsProperty, 
                  newItem: MarsProperty): Boolean {
   return oldItem === newItem
}
```

7. areContentsTheSame() için, yalnızca oldItem ve newItem kimliğinde standart yapısal eşitlik operatörünü kullanın.

```kotlin
override fun areContentsTheSame(oldItem: MarsProperty, 
                  newItem: MarsProperty): Boolean {
   return oldItem.id == newItem.id
}
```

8. Yine de PhotoGridAdapter sınıfının içinde, eşlik eden nesnenin altında MarsPropertyViewHolder için RecyclerView.ViewHolder'ı extend eden 
bir iç sınıf tanımı ekleyin.

İstenirse androidx.recyclerview.widget.RecyclerView ve com.example.android.marsrealestate.databinding.GridViewItemBinding'i import edin.
MarsProperty'yi layout’a bağlamak için GridViewItemBinding değişkenine ihtiyacınız var, bu nedenle değişkeni MarsPropertyViewHolder'a iletin. 
Temel ViewHolder sınıfı, constructor içerisinde bir görünüm gerektirdiğinden, ona binding root view iletirsiniz.

```kotlin
class MarsPropertyViewHolder(private var binding: 
                   GridViewItemBinding):
       RecyclerView.ViewHolder(binding.root) {
}
```

9. MarsPropertyViewHolder'da, bir MarsProperty nesnesini argüman olarak alan ve bu nesneye [binding.property](http://bağlama.property) özelliği 
ayarlayan bir bind() yöntemi oluşturun. Güncellemenin hemen yürütülmesini sağlayan özelliği ayarladıktan sonra executePendingBindings() öğesini çağırın.

```kotlin
fun bind(marsProperty: MarsProperty) {
   binding.property = marsProperty
   binding.executePendingBindings()
}
```

>Not: Bu değişiklik, Android Studio'da data binding hatalarına neden olabilir. Bu hataları gidermek için uygulamaya clean, rebuild project 
işlemlerini uygulamanız gerekebilir.

10. onCreateViewHolder() içindeki PhotoGridAdapter sınıfının içinde bulunan TODO'yu kaldırın ve aşağıda gösterilen satırı ekleyin. 
İstendiğinde android.view.LayoutInflater'ı import edin.

onCreateViewHolder() metodunun, GridViewItemBinding'i oluşturarak ve üst ViewGroup bağlamınızdan LayoutInflater'ı kullanarak oluşturulan 
yeni bir MarsPropertyViewHolder döndürmesi gerekir.

```kotlin
return MarsPropertyViewHolder(GridViewItemBinding.inflate(
      LayoutInflater.from(parent.context)))
```

11. onBindViewHolder() metodundaki TODO'yu kaldırın ve aşağıda gösterilen satırları ekleyin. Burada, geçerli RecyclerView konumuyla ilişkili 
MarsProperty nesnesini almak için getItem() öğesini çağırırsınız ve ardından bu özelliği MarsPropertyViewHolder'daki bind() metoduna iletirsiniz.

```kotlin
val marsProperty = getItem(position)
holder.bind(marsProperty)
```

### Adım 4: Binding adapter’ı ekleyin ve fragment’ları bağlayın

Son olarak, PhotoGridAdapter'ı MarsProperty nesneleri listesiyle başlatmak için bir BindingAdapter kullanın. RecyclerView verilerini 
ayarlamak için BindingAdapter kullanmak, data binding’in MarsProperty nesneleri listesi için LiveData'yı otomatik olarak gözlemlemesine 
neden olur. Ardından, MarsProperty listesi değiştiğinde binding adapter otomatik olarak çağrılır.

1. BindingAdapters.kt'yi açın.
2. Dosyanın sonuna, bir RecyclerView ve bir MarsProperty nesneleri listesini değişken olarak alan bir bindRecyclerView() yöntemi ekleyin. 
Bu yönteme bir @BindingAdapter ile açıklama ekleyin.

İstendiğinde androidx.recyclerview.widget.RecyclerView ve com.example.android.marsrealestate.network.MarsProperty'yi import edin.

```kotlin
@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, 
    data: List<MarsProperty>?) {
}
```

3. bindRecyclerView() metodunun içinde, recyclerView.adapter'ı PhotoGridAdapter'a dönüştürün ve verilerle adapter.submitList()'i çağırın. 
Bu, yeni bir liste kullanılabilir olduğunda RecyclerView'a bildirir.
İstenirse com.example.android.marsrealestate.overview.PhotoGridAdapter'ı import edin.

```kotlin
val adapter = recyclerView.adapter as PhotoGridAdapter
adapter.submitList(data)
```

4. res/layout/fragment_overview.xml dosyasını açın. app:listData özelliğini RecyclerView öğesine ekleyin ve data binding kullanarak onu 
viewmodel.properties olarak ayarlayın.

```kotlin
app:listData="@{viewModel.properties}"
```

5. overview/OverviewFragment.kt dosyasını açın. onCreateView() içinde, setHasOptionsMenu() çağrısından hemen önce, bining.photosGrid 
içindeki RecyclerView adapter’ını yeni bir PhotoGridAdapter nesnesine atayın.

```kotlin
binding.photosGrid.adapter = PhotoGridAdapter()
```

6. Uygulamayı çalıştırın. MarsProperty görüntülerinden oluşan bir ızgara görmelisiniz. Yeni görselleri görmek için kaydırdığınızda, 
uygulama görselin kendisini göstermeden önce loading-progress simgesini gösterir. Uçak modunu açarsanız, henüz yüklenmemiş görüntüler 
broken-image simgeleri olarak görünür.

![image](https://user-images.githubusercontent.com/29903779/150185394-e636bfa3-bf1d-4555-ab62-7213e724983a.png)

## <a name="c"></a>Aşama 3 : RecyclerView'a hata kontrolü ekleyin

MarsRealEstate uygulaması, bir görüntü alınamadığında broken-image simgesini görüntüler. Ancak ağ olmadığında uygulama boş bir ekran gösteriyor.

![image](https://user-images.githubusercontent.com/29903779/150185602-39c792f5-a01b-43bd-8e6c-7b4983fe4ee8.png)

Bu iyi bir kullanıcı deneyimi değil. Bu görevde, kullanıcıya neler olduğu hakkında daha iyi bir fikir vermek için temel hata 
kontrolü eklersiniz. İnternet yoksa, uygulama connection-error simgesini gösterir. Uygulama, MarsProperty listesini alırken, 
loading animasyonunu gösterecektir.

### Adım 1: Viewmodel’e durum ekleyin

Başlamak için, web isteğinin durumunu temsil etmek için view model’de bir LiveData oluşturursunuz. Göz önünde bulundurulması gereken üç durum 
vardır: loading, success, and failure. Loading durumu, siz wait() çağrısındaki verileri beklerken gerçekleşir.

1. overview/OverviewViewModel.kt dosyasını açın. Dosyanın en üstüne (import’lardan sonra, sınıf tanımından önce), mevcut tüm durumları temsil 
edecek bir enum değeri ekleyin:

```kotlin
enum class MarsApiStatus { LOADING, ERROR, DONE }
```

2. OverviewViewModel sınıfı boyunca hem dahili hem de harici _response live data tanımlarını _status olarak yeniden adlandırın. Bu codelab’de 
daha önce _properties LiveData değeri eklediğiniz için, web hizmeti yanıtının tamamı kullanılmamıştır. Mevcut durumu takip etmek için burada 
bir LiveData'ya ihtiyacınız var, böylece sadece mevcut değişkenleri yeniden adlandırabilirsiniz.

Ayrıca, türleri String'den MarsApiStatus'a değiştirin.

```kotlin
private val _status = MutableLiveData<MarsApiStatus>()

val status: LiveData<MarsApiStatus>
   get() = _status
```

3. getMarsRealEstateProperties() metoduna gidin ve _response'u burada da _status olarak güncelleyin. "Success" string değerini MarsApiStatus.DONE 
durumuna ve "Failure" string değerini MarsApiStatus.ERROR olarak değiştirin.
4. try {} bloğundan önce durumu MarsApiStatus.LOADING olarak ayarlayın. Bu, coroutines çalışırken ve siz veri beklerken başlangıç durumudur. 
Tam try/ycatch{} bloğu şimdi şöyle görünür:

```kotlin
_status.value = MarsApiStatus.LOADING
try {
   _properties.value = MarsApi.retrofitService.getProperties()
   _status.value = MarsApiStatus.DONE
} catch (e: Exception) {
   _status.value = MarsApiStatus.ERROR
}
```

5. catch {} bloğundaki error durumundan sonra, _properties LiveData'yı boş bir listeye ayarlayın. Bu, RecyclerView'ı temizler.

```kotlin
} catch (e: Exception) {
   _status.value = MarsApiStatus.ERROR
   _properties.value = ArrayList()
}
```

### Adım 2: ImageView durumu için bir binding adapter ekleyin

Artık view model’de bir durumunuz var, ancak bu sadece bir dizi durum. Uygulamanın kendisinde nasıl görünmesini sağlarsınız? Bu adımda, 
loading ve error durumlarında simgeleri görüntülemek için data binding’e bağlı bir ImageView kullanırsınız. Uygulama loading veya error 
durumundayken, ImageView görünür olmalıdır. Uygulamanın yüklenmesi tamamlandığında, ImageView görünmez olmalıdır.

1. BindingAdapters.kt'yi açın. Değişken olarak bir ImageView ve bir MarsApiStatus değeri alan bindStatus() adlı yeni bir binding adapter 
ekleyin. İstendiğinde com.example.android.marsrealestate.overview.MarsApiStatus'u import edin.

```kotlin
@BindingAdapter("marsApiStatus")
fun bindStatus(statusImageView: ImageView, 
          status: MarsApiStatus?) {
}
```

2. Farklı durumlar arasında geçiş yapmak için bindStatus() yönteminin içine bir when{} ekleyin.

```kotlin
when (status) {

}
```

3. when{} içine, yükleme durumu için bir case ekleyin (MarsApiStatus.LOADING). Bu durum için ImageView'i görünür olarak ayarlayın ve 
loading animasyonunu atayın. Bu, önceki görevde Glide için kullandığınız çizilebilir animasyonun aynısıdır. İstendiğinde android.view.View 
dosyasını import edin.

```kotlin
when (status) {
   MarsApiStatus.LOADING -> {
      statusImageView.visibility = View.VISIBLE
      statusImageView.setImageResource(R.drawable.loading_animation)
   }
}
```

4. Error durumu için MarsApiStatus.ERROR olan bir case ekleyin. Loading durumu için yaptığınıza benzer şekilde, ImageView durumunu görünür 
olarak ayarlayın ve bağlantı hatası görünümünü yeniden kullanın.

```kotlin
MarsApiStatus.ERROR -> {
   statusImageView.visibility = View.VISIBLE
   statusImageView.setImageResource(R.drawable.ic_connection_error)
}
```

5. Done durumu için MarsApiStatus.DONE olan bir servis talebi ekleyin. Burada başarılı bir yanıtınız var, bu yüzden onu gizlemek için 
ImageView durumunun görünürlüğünü kapatın.

```kotlin
MarsApiStatus.DONE -> {
   statusImageView.visibility = View.GONE
}
```

### Adım 3: Layout’a ImageView durumunu ekleyin

1. res/layout/fragment_overview.xml dosyasını açın. RecyclerView öğesinin altına, ConstraintLayout'un içine aşağıda gösterilen ImageView'ı ekleyin.

Bu ImageView, RecyclerView ile aynı kısıtlamalara sahiptir. Ancak genişlik ve yükseklik, görüntüyü görünümü dolduracak şekilde germek
yerine görüntüyü ortalamak için wrap_content öğesini kullanır. Ayrıca, görünüm modelindeki durum özelliği değiştiğinde görünümün 
BindingAdapter'ınızı çağırdığı app:marsApiStatus özelliğine de dikkat edin.

```kotlin
<ImageView
   android:id="@+id/status_image"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    app:layout_constraintBottom_toBottomOf="parent"
    app:layout_constraintLeft_toLeftOf="parent"
    app:layout_constraintRight_toRightOf="parent"
    app:layout_constraintTop_toTopOf="parent"
    app:marsApiStatus="@{viewModel.status}" />
```

2. Kayıp ağ bağlantısını simüle etmek için emulator’de veya cihazınızda uçak modunu açın. Uygulamayı derleyin ve çalıştırın ve error 
görüntüsünün göründüğüne dikkat edin:

![image](https://user-images.githubusercontent.com/29903779/150186009-a5cd6d85-68ee-4822-bcd4-6876d298e52e.png)

3. Uygulamayı kapatmak ve uçak modunu kapatmak için Geri düğmesine dokunun. Uygulamayı döndürmek için son ekranlar butonunu kullanın. 
Ağ bağlantınızın hızına bağlı olarak, resimler yüklenmeye başlamadan önce uygulama web servisi sorguladığında çok kısa bir yükleme döndürücü görebilirsiniz.
