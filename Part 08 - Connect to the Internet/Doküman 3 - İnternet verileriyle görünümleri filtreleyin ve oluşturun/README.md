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
