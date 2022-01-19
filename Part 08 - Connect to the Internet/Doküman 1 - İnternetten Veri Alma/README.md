# <a name="1"></a>İnternetten veri alma

- [MarsRealEstate başlangıç uygulamasını keşfedin](#a)
- [Retrofit ile bir web servisine bağlanın](#b)
- [Moshi ile JSON yanıtını ayrıştırın](#c)
- [Coroutines ile Retrofit’i kullanın](#d)

Bu codelab içerisinde (ve sonraki codelab’lerde), Mars'ta ait satılık olan özellikleri gösteren MarsRealEstate adlı bir 
başlangıç uygulaması üzerine çalışıyorsunuz. Bu uygulama, fiyat ve mülkün satılık veya kiralık olup olmadığı gibi ayrıntılar 
da dahil olmak üzere mülk verilerini almak ve görüntülemek için bir web hizmetine bağlanır ve her bir mülkü temsil eden 
görüntüler, NASA'nın Mars gezicilerinden çekilen Mars'tan alınan gerçek hayat fotoğraflarıdır.

![image](https://user-images.githubusercontent.com/29903779/150094622-5f931226-295b-4f78-af75-49f8b19ee92f.png)

Bu codelab'de oluşturduğunuz uygulamanın sürümü çok fazla görsele sahip olmayacak: İnternete bağlanma ve bir web servisi 
kullanarak ham özelliklerin verilerini indirmek için uygulamanın ağ katmanı kısmına odaklanır. Verilerin doğru bir şekilde 
alındığından ve ayrıştırıldığından emin olmak için, Mars'taki özelliklerin sayısını bir metin görünümünde yazdırmanız yeterlidir:

![image](https://user-images.githubusercontent.com/29903779/150094955-5a95f68d-0085-4486-b2e6-93d6729f35a2.png)

## <a name="a"></a>Aşama 1 : MarsRealEstate başlangıç uygulamasını keşfedin

MarsRealEstate uygulamasının mimarisinin iki ana modülü vardır:
- Bir RecyclerView ile oluşturulmuş, küçük resimlere sahip özelliklerin listelendiği bir görünüm.
- Her bir özellik hakkında detaylı bilgi içeren bir görünüm.

![image](https://user-images.githubusercontent.com/29903779/150095241-167a6969-d8d2-42d2-871f-71d0abddefa0.png)

Uygulamanın her parçasına ait bir ViewModel vardır. Bu codelab içinde ağ hizmeti için bir katman oluşturursunuz ve ViewModel 
doğrudan bu ağ katmanıyla iletişim kurar. Bu ViewModel, Room veritabanıyla iletişim kurduğunda yapacağınız işlemler önceki 
codelab’lere yaptığınıza benzer olacaktır.
ViewModel, Marsa ait emlak bilgilerini almak için ağ araması yapmaktan sorumludur. Ayrıntılar için oluşturulan ViewModel, 
ayrıntı Fragment içerisinde görüntülenen Mars gayrimenkulünün bilgilerini tutar. Her ViewModel için, veriler değiştiğinde 
uygulama kullanıcı arabirimini güncellemek için yaşam döngüsüne duyarlı olarak data binding yapısı ile LiveData kullanırsınız.
Navigation component’i hem iki parça arasında gezinmek hem de seçilen özelliği bağımsız değişken olarak iletmek için kullanırsınız.
Bu görevde, MarsRealEstate için başlangıç uygulamasını indirip çalıştıracak ve projenin yapısını tanıyacaksınız.

### Adım 1: Fragment'ları ve navigation'ı keşfedin

1. MarsRealEstate başlangıç uygulamasını indirin ve Android Studio'da açın.
2. app/java/MainActivity.kt dosyasını inceleyin. Uygulama her iki ekran için de fragment kullanır, bu nedenle bu activity’nin 
tek görevi activity’nin layout’unu yüklemektir.
3. app/res/layout/activity_main.xml dosyasını inceleyin. Bu activity layout, nav_graph.xml navigation dosyasında tanımlanan iki 
fragment için ana layout işlevi görür. Bu layout, bir NavHostFragment ve onunla ilişkili Navigation controller’ı, nav_graph kaynağıyla başlatır.
4. app/res/navigation/nav_graph.xml dosyasını açın. Burada iki fragment arasındaki navigation ilişkisini görebilirsiniz. Navigation 
grafiğindeki StartDestination, overviewFragment’ı işaret eder. Bu, uygulama başlatıldığında overview fragment’ın oluşturulduğu anlamına gelir.

### Adım 2: Kotlin kaynak dosyalarını ve data binding'i keşfedin

1. Project panelinde, app > java öğesini genişletin. MarsRealEstate uygulamasının üç paket klasörü olduğuna dikkat edin: detail, 
network ve overview. Bunlar, uygulamanızın üç ana bileşenine karşılık gelir: overview ve detail fragment’ları ve ağ katmanının kodu.

![image](https://user-images.githubusercontent.com/29903779/150095792-a1e5c686-558a-4277-b307-4775b08030d2.png)

2. app/java/overview/OverviewFragment.kt dosyasını açın. OverviewFragment, OverviewViewModel'i tembelce başlatır. Bu, OverviewViewModel'in 
sayfa ilk kullanıldığında oluşturulduğu anlamına gelir.
3. onCreateView() metodunu inceleyin. Bu metot, data binding kullanarak fragment_overview layoutunu oluşturur, lifecycle owner’ı kendisine(this) 
ve binding nesnesindeki viewModel değişkenini ona uyumlu hale getirir. Lifecycle owner olarak belirlediğimiz için, data binding için kullanılan 
herhangi bir LiveData, herhangi bir değişiklik için otomatik olarak gözlemlenecek ve kullanıcı arayüzü buna göre güncellenecektir.
4. app/java/overview/OverviewViewModel'i açın. Yapılan işlem sonucu dönecek yanıt bir LiveData olduğundan ve data binding değişkeni için yaşam 
döngüsünü ayarladığımızdan, bu değişkende yapılacak herhangi bir değişiklik uygulama kullanıcı arabirimini güncelleyecektir.
5. init bloğunu inceleyin. ViewModel oluşturulduğunda getMarsRealEstateProperties() yöntemini çağırır.
6. getMarsRealEstateProperties() metodunu inceleyin. Bu başlangıç uygulamasındaki bu yöntem bir yer tutucu yanıtı içerir. Bu codelab’in amacı, 
internetten aldığınız gerçek verileri kullanarak ViewModel içindeki LiveData yanıtını güncellemektir.
7. app/res/layout/fragment_overview.xml dosyasını açın. Bu, bu codelab’te birlikte çalıştığınız overview fragment’ın layout dosyasıdır ve viewmodel 
için ile kullanılan data binding içerir. OverviewViewModel'i içe aktarır ve ardından ViewModel'den gelen yanıtı bir TextView'a bağlar. Daha sonraki 
codelab’lerde textview yapısını, bir RecyclerView'da içerisine yazdırılan görsellerle değiştirireceksiniz.
8.Uygulamayı derleyin ve çalıştırın. Bu uygulamanın mevcut sürümünde gördüğünüz tek şey başlangıç yanıtıdır: "Mars API Yanıtını buradan ayarlayın!"

![image](https://user-images.githubusercontent.com/29903779/150096253-f51561a0-869e-4c9e-88a3-7dd6de73100e.png)

## <a name="b"></a>Aşama 2 : Retrofit ile bir web servisine bağlanın

Marsa ait gayrimenkul verileri, bir REST web servisi aracılığıyla erişilebilen bir web sunucusunda depolanır. REST mimarisi kullanan web 
servisleri, web bileşenleri ve protokolleri kullanılarak oluşturulur.
URI'ler aracılığıyla standart bir şekilde bir web servisine istekte bulunursunuz.

>Not: Web URL'si aslında bir URI türüdür. Bu kurs boyunca hem URL hem de URI birbirinin yerine kullanılır.

Örneğin, bu dersin uygulamasında aşağıdaki sunucu URI'sini kullanarak tüm verileri alırsınız: <br>
https://android-kotlin-fun-mars-server.appspot.com <br>
Tarayıcınıza aşağıdaki URL'yi yazarsanız, Mars'taki mevcut tüm gayrimenkullerin bir listesini alırsınız! <br>
https://android-kotlin-fun-mars-server.appspot.com/realestate

Bir web servisinden gelen yanıt, yaygın olarak yapılandırılmış verileri temsil etmek için bir değiş tokuş biçimi olan JSON kullanılarak 
biçimlendirilir. Bir sonraki görevde JSON hakkında daha fazla bilgi edineceksiniz, ancak kısaca bir JSON nesnesinin bazen dictionary, 
hashmap veya ilişkisel dizi olarak adlandırılan bir anahtar/değer çiftleri koleksiyonu olduğunu bilin.
Bu JSON verilerini uygulamaya almak için uygulamanızın bir sunucuyla ağ bağlantısı kurması ve ardından JSON verilerini alıp uygulamanın 
kullanabileceği bir biçimde ayrıştırması gerekir. Bu codelab'de, bu bağlantıyı yapmak için Retrofit adlı bir REST istemci kitaplığı kullanırsınız.

### Adım 1: Kotlin kaynak dosyalarını ve data binding'i keşfedin

1. build.gradle'ı açın (Module: app).
2. dependencies bölümünde, Retrofit kitaplıkları için şu satırları ekleyin:

```kotlin
implementation "com.squareup.retrofit2:retrofit:$version_retrofit"
implementation "com.squareup.retrofit2:converter-scalars:$version_retrofit"
```

Sürüm numaralarının proje Gradle dosyasında ayrı olarak tanımlandığına dikkat edin. İlk bağımlılık, Retrofit 2 kitaplığının kendisi 
içindir ve ikinci bağımlılık, Retrofit skaler dönüştürücü içindir. Bu dönüştürücü, Retrofit'in JSON yanıtını bir string olarak 
döndürmesini sağlar. İki kütüphane birlikte çalışır.

3. Projeyi yeni bağımlılıklarla yeniden oluşturmak için Sync Now'a tıklayın.

### Adım 2: Java 8 dil özellikleri için destek ekleyin
Retrofit2 dahil olmak üzere birçok üçüncü taraf kitaplık Java 8 dil özelliklerini kullanır. Android Gradle eklentisi, belirli Java 8 
dil özelliklerini kullanmak için yerleşik destek sağlar. Bu yerleşik özellikleri kullanmak için modülün build.gradle dosyasını aşağıda 
gösterildiği gibi güncelleyin:

```kotlin
android {
  ...

  compileOptions {
    sourceCompatibility JavaVersion.VERSION_1_8
    targetCompatibility JavaVersion.VERSION_1_8
  }
  
  kotlinOptions {
    jvmTarget = JavaVersion.VERSION_1_8.toString()
  }
}
```
Bu yeni özellikleri kullanmak üzere projeyi yeniden oluşturmak için Sync Now’a tıklayın.

### Adım 3: MarsApiService'i implement edin.
Retrofit, web servisinin içeriğine dayalı olarak uygulama için bir ağ API'si oluşturur. Web servisinden veri alır ve verilerin 
kodunun nasıl çözüleceğini ve kullanılabilir biçiminde nasıl döndürüleceğini bilen ayrı bir dönüştürücü kitaplığı aracılığıyla 
yönlendirir. Retrofit, XML ve JSON gibi popüler web veri biçimleri için yerleşik destek içerir. Retrofit, isteklerin arka plan 
iş parçacıklarında çalıştırılması gibi kritik ayrıntılar da dahil olmak üzere, ağ katmanının çoğunu sizin için oluşturur.
MarsApiService sınıfı, uygulamanın ağ katmanını tutar. Diğer bir deyişle, ViewModel'inizin web servisiyile iletişim kurmak için 
kullanacağı API'dir. Retrofit hizmet API'sini uygulayacağınız sınıftır.

1. app/java/network/MarsApiService.kt dosyasını açın. Şu anda dosya tek bir şey içeriyor: web hizmetinin temel URL'si için bir 
const değer (sabit).

```kotlin
private const val BASE_URL = "https://android-kotlin-fun-mars-server.appspot.com"
```

2. Bu const değerin hemen altında, bir Retrofit nesnesi oluşturmak için bir Retrofit oluşturucu kullanın. İstendiğinde 
retrofit2.Retrofit ve retrofit2.converter.scalars.ScalarsConverterFactory'yi import edin.

```kotlin
private val retrofit = Retrofit.Builder()
   .addConverterFactory(ScalarsConverterFactory.create())
   .baseUrl(BASE_URL)
   .build()
```

Retrofit, bir web servisi API'si oluşturmak için en az iki şeye ihtiyaç duyar: web servisi için temel URI ve bir converter factory. 
Dönüştürücü, Retrofit'e web servisinden geri aldığı verilerle ne yapacağını söyler. Bu durumda, Retrofit'in web servisinden bir JSON 
yanıtı almasını ve bunu bir string olarak döndürmesini istiyorsunuz. Retrofit, stringleri ve diğer ilkel türleri destekleyen bir 
ScalarsConverter'a sahiptir. Bu nedenle oluşturucuda bir ScalarsConverterFactory örneğiyle addConverterFactory() öğesini çağırırsınız. 
Son olarak, Retrofit nesnesini oluşturmak için build()'i çağırırsınız.

3. Retrofit oluşturucuya yapılan çağrının hemen altında, Retrofit'in HTTP isteklerini kullanarak web servisiyle nasıl iletişim kurduğunu 
tanımlayan bir arabirim tanımlayın. Retrofit2.http.GET ve retrofit2.Call istendiğinde import edin.

```kotlin
interface MarsApiService {
    @GET("realestate")
    fun getProperties():
            Call<String>
}
```

Şu anda amaç, web servisinden JSON yanıt string’ini almak ve bunu yapmak için yalnızca bir metota ihtiyacınız vardır: getProperties(). 
Retrofit'e bu metodun ne yapması gerektiğini söylemek için bir @GET annotation’ı kullanın ve bu web servisi metodunun path veya 
endpoint’ini belirtin. Bu durumda endpoint’e gayrimenkul adı verilir. getProperties() yöntemi çağrıldığında, Retrofit, endpoint gerçek 
durumunu temel URL'ye (Retrofit oluşturucuda tanımladığınız) ekler ve bir Call nesnesi oluşturur. Bu Call nesnesi, isteği başlatmak 
için kullanılır.

4. MarsApiService arabiriminin altında, Retrofit servisini başlatmak için MarsApi adlı genel bir nesne tanımlayın. Bu, bir servis nesnesi 
oluştururken kullanılacak standart bir Kotlin kod kalıbıdır.

```kotlin
object MarsApi {
    val retrofitService : MarsApiService by lazy { 
       retrofit.create(MarsApiService::class.java) }
}
```

Retrofit create() metodu, Retrofit servisinin kendisini MarsApiService arabirimiyle oluşturur. Bu çağrı hesaplama açısından pahalı 
olduğundan, Retrofit hizmetini tembelce (lazy) başlatırsınız. Uygulamanın yalnızca bir Retrofit servisi örneğine ihtiyacı olduğundan, 
MarsApi adlı genel bir nesneyi kullanarak hizmeti uygulamanın geri kalanına sunarsınız. Şimdi tüm kurulum tamamlandıktan sonra, 
uygulamanız MarsApi.retrofitService'i her çağırdığında, MarsApiService'i uygulayan tek bir Retrofit nesnesi alacaktır.

>Not: "lazy ile oluşturmanın, gereksiz hesaplamalardan veya diğer bilgi işlem kaynaklarının kullanımından kaçınmak için nesne oluşturmanın, 
>o nesneye gerçekten ihtiyacınız olana kadar bilerek ertelenmesi olduğunu unutmayın. Kotlin, tembel örnekleme için birinci sınıf desteğe sahiptir.

### Adım 4: OverviewViewModel'de web servisini çağırın

1. app/java/overview/OverviewViewModel.kt dosyasını açın. getMarsRealEstateProperties() metoduna ilerleyin.

```kotlin
private fun getMarsRealEstateProperties() {
   _response.value = "Set the Mars API Response here!"
}
```

Bu, Retrofit servisini çağıracağınız ve döndürülen JSON string’ini işleyeceğiniz metottur. Şu anda yanıt için yalnızca bir yer tutucu string var.

2. "Set the Mars API Response here!" yanıtını ayarlayan yer tutucu satırı silin.
3. getMarsRealEstateProperties() içine aşağıda gösterilen kodu ekleyin. İstendiğinde retrofit2.Callback ve 
com.example.android.marsrealestate.network.MarsApi'yi import edin.

MarsApi.retrofitService.getProperties() metodu, bir Call nesnesi döndürür. Ardından, bir arka plan thread’inde ağ isteğini başlatmak 
için o nesnede enqueue() öğesini çağırabilirsiniz.

```kotlin
MarsApi.retrofitService.getProperties().enqueue( 
   object: Callback<String> {
})
```

4. Kırmızı ile altı çizili object kelimesine tıklayın. Code > Implement methods’u seçin. Listeden hem onResponse() hem de onFailure() öğesini seçin.

![image](https://user-images.githubusercontent.com/29903779/150098538-1786a6a3-aac2-4d83-9458-f4460eb0e95d.png)

Android Studio, kodu TODO'larla birlikte her yönteme ekler:

```kotlin
override fun onFailure(call: Call<String>, t: Throwable) {
       TODO("not implemented") 
}

override fun onResponse(call: Call<String>, 
   response: Response<String>) {
       TODO("not implemented") 
}
```

5. onFailure() içindeki TODO'yu silin ve _response'u aşağıda gösterildiği gibi bir hata mesajına ayarlayın. _response, textview’da 
neyin gösterileceğini belirleyen bir LiveData string değişkenidir. Her durumun _response LiveData'yı güncellemesi gerekir.

Web servisi yanıtı başarısız olduğunda onFailure() geri çağrısı çağrılır. Bu yanıt için, _response durumunu, Throwable değişkeninden 
gelen mesajla birleştirerek "Failure: " olarak ayarlayın.

```kotlin
override fun onFailure(call: Call<String>, t: Throwable) {
   _response.value = "Failure: " + t.message
}
```

6. onResponse() içindeki TODO'yu silin ve _response'u yanıt mesajına ayarlayın. OnResponse() geri çağrısı, istek başarılı olduğunda 
ve web servisi bir yanıt döndürdüğünde çağrılır.

```kotlin
override fun onResponse(call: Call<String>, 
   response: Response<String>) {
      _response.value = response.body()
}
```

### Adım 5: İnternet iznini tanımlayın

1. MarsRealEstate uygulamasını derleyin ve çalıştırın. Uygulamanın bir hatayla hemen kapandığını unutmayın:

![image](https://user-images.githubusercontent.com/29903779/150099075-3ec28f73-32a7-42ff-bd9a-a6dbd05aba29.png)

2. Android Studio'da Logcat sekmesine tıklayın ve aşağıdaki gibi bir satırla başlayan günlükteki hatayı not edin:

```kotlin
Process: com.example.android.marsrealestate, PID: 10646
java.lang.SecurityException: Permission denied (missing INTERNET permission?)
```

Hata mesajı, uygulamanın İNTERNET iznini almamış olabileceğini gösterir. İnternete bağlanmak güvenlik endişelerini 
beraberinde getirir; bu nedenle uygulamaların varsayılan olarak internet bağlantısı yoktur. Android'e uygulamanın 
internete erişmesi gerektiğini açıkça söylemeniz gerekir.

3. app/manifests/AndroidManifest.xml dosyasını açın. Bu satırı `<application>` etiketinin hemen önüne ekleyin:

```kotlin 
<uses-permission android:name="android.permission.INTERNET" />
```
  
4. Uygulamayı tekrar derleyin ve çalıştırın. İnternet bağlantınızla ilgili her şey düzgün çalışıyorsa, Mars Property 
  verilerini içeren JSON metnini görürsünüz.

![image](https://user-images.githubusercontent.com/29903779/150099428-43761ade-b566-4f07-a065-db4ebb0d247a.png)

5. Uygulamayı kapatmak için cihazınızdaki veya emulator’daki Geri düğmesine dokunun.
6. Cihazınızı veya emulator’ınızı uçak moduna alın ve ardından Son Kullanılanlar menüsünden uygulamayı yeniden açın 
veya Android Studio'dan uygulamayı yeniden başlatın.

![image](https://user-images.githubusercontent.com/29903779/150099875-d3d6360e-f534-4244-8f97-11df58281b5b.png)

7. Uçak modunu tekrar kapatın.

## <a name="c"></a>Aşama 3 : Moshi ile JSON yanıtını ayrıştırın

Şimdi, harika bir başlangıç olan Mars web servisinden bir JSON yanıtı alıyorsunuz. Ancak gerçekten ihtiyacınız olan şey, 
büyük bir JSON string’i değil, Kotlin nesneleridir. Bir JSON string’ini Kotlin nesnelerine dönüştüren bir Android JSON 
ayrıştırıcısı olan Moshi adlı bir kitaplık var. Retrofit, Moshi ile çalışan bir dönüştürücüye sahiptir, bu nedenle buradaki 
amacının için harika bir kütüphanedir.
Bu görevde, web servisinden JSON yanıtını yararlı Mars Property Kotlin nesnelerine ayrıştırmak için Retrofit ile Moshi 
kitaplığını kullanırsınız. Uygulamayı, ham JSON'u görüntülemek yerine, döndürülen Mars Property’lerinin sayısını gösterecek 
şekilde değiştirirsiniz.

### Adım 1: Moshi kitaplığı bağımlılıklarını ekleyin

1. build.gradle'ı açın (Module: app).
2. dependencies bölümünde, Moshi bağımlılığını dahil etmek için aşağıda gösterilen kodu ekleyin. Retrofit'te olduğu gibi, 
$version_moshi proje düzeyindeki Gradle dosyasında ayrı olarak tanımlanır. Bu bağımlılık, Kotlin desteğiyle Moshi JSON 
kitaplığına destek ekler.

```kotlin
implementation "com.squareup.moshi:moshi-kotlin:$version_moshi"
```

3. dependencies bloğunda Retrofit skaler dönüştürücü için satırları bulun:

```kotlin
implementation "com.squareup.retrofit2:retrofit:$version_retrofit"
implementation "com.squareup.retrofit2:converter-scalars:$version_retrofit"
```

4. converter-moshi'yi kullanmak için bu satırları değiştirin:

```kotlin
implementation "com.squareup.retrofit2:converter-moshi:$version_retrofit"
```

5. Projeyi yeni bağımlılıklarla yeniden oluşturmak için Sync Now’a tıklayın.

>Not: Proje, kaldırılan Retrofit skaler bağımlılığıyla ilgili derleyici hatalarını gösterebilir. Bunları sonraki adımlarda düzeltirsiniz.

### Adım 2: MarsProperty veri sınıfını uygulayın

Web servisinden aldığınız JSON yanıtının örneği şuna benzer:

```kotlin
[{"price":450000,
"id":"424906",
"type":"rent",
"img_src":"http://mars.jpl.nasa.gov/msl-raw-images/msss/01000/mcam/1000ML0044631300305227E03_DXXX.jpg"},
...]
```

Yukarıda gösterilen JSON yanıtı, köşeli parantezlerle gösterilen bir dizidir. Dizi, küme parantezleriyle çevrelenen 
JSON nesnelerini içerir. Her nesne, iki nokta üst üste ile ayrılmış bir dizi ad-değer çifti içerir. İsimler tırnak içine 
alınır. Değerler sayılar, dizeler ve booleanların yanı sıra diğer nesneler veya diziler olabilir. Bir değer bir string 
ise, ayrıca tırnak içine alınır. Örneğin, bu özelliğin fiyatı 450.000 ABD Dolarıdır ve img_src görüntü dosyasının sunucudaki 
konumu olan bir URL'dir.

Yukarıdaki örnekte, her bir Mars özelliği girişinin şu JSON anahtarına ve değer çiftlerine sahip olduğuna dikkat edin:
fiyat: Mars mülkünün sayı olarak fiyatı.
id: bir dize olarak özelliğin kimliği.
yazın: "rent" veya "buy".
img_src: Bir string olarak görüntünün URL'si.

Moshi, bu JSON verilerini ayrıştırır ve Kotlin nesnelerine dönüştürür. Bunu yapmak için, ayrıştırılan sonuçları depolamak 
için bir Kotlin veri sınıfına sahip olması gerekir. Bu nedenle bir sonraki adım o sınıfı oluşturmaktır.

1. app/java/network/MarsProperty.kt dosyasını açın.
2. Mevcut MarsProperty sınıf tanımını aşağıdaki kodla değiştirin:

```kotlin
data class MarsProperty(
   val id: String, val img_src: String,
   val type: String,
   val price: Double
)
```

MarsProperty sınıfındaki değişkenlerin her birinin JSON nesnesindeki bir anahtar adına karşılık geldiğine dikkat edin. 
Özel JSON yanıtımızdaki türleri eşleştirmek için, Double olan fiyat dışındaki tüm değerler için String nesnelerini 
kullanırsınız. Not, herhangi bir JSON numarasını temsil etmek için bir Double kullanılabilir.

Moshi JSON'u ayrıştırdığında, anahtarları ada göre eşleştirir ve veri nesnelerini uygun değerlerle doldurur.

3. img_src anahtarının satırını aşağıda gösterilen satırla değiştirin. İstendiğinde com.squareup.moshi.Json import edin.

```kotlin
@Json(name = "img_src") val imgSrcUrl: String,
```

Bazen bir JSON yanıtındaki anahtar adları, kafa karıştırıcı Kotlin özellikleri oluşturabilir veya kodlama stilinizle 
eşleşmeyebilir. Örneğin, JSON dosyasında img_src anahtarı bir alt çizgi kullanırken, Kotlin özellikleri genellikle 
büyük ve küçük harfler ("camel case") kullanır.

Veri sınıfınızda JSON yanıtındaki anahtar adlarından farklı değişken adları kullanmak için @Json annotation’ını 
kullanın. Bu örnekte, veri sınıfındaki değişkenin adı imgSrcUrl'dir. Değişken, @Json(name = "img_src") kullanılarak 
JSON özniteliği img_src ile eşlenir.

### Adım 3: MarsApiService ve OverviewViewModel'i güncelleyin

MarsProperty veri sınıfını, artık ağ API'sini ve ViewModel'i Moshi verilerini içerecek şekilde güncelleyebilirsiniz.

1. network/MarsApiService.kt'yi açın. ScalarsConverterFactory için missing-class hataları görebilirsiniz. 
Bunun nedeni 1. Adımda yaptığınız Retrofit bağımlılığı değişikliğidir. Bu hataları yakında düzeltirsiniz.
2. Dosyanın en üstünde, Retrofit oluşturucunun hemen önüne, Moshi örneğini oluşturmak için aşağıdaki kodu ekleyin. 
İstendiğinde com.squareup.moshi.Moshi ve com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory’yi import edin.

```kotlin
private val moshi = Moshi.Builder()
   .add(KotlinJsonAdapterFactory())
   .build()
```

Retrofit ile yaptığınıza benzer şekilde, burada Moshi oluşturucuyu kullanarak bir moshi nesnesi yaratırsınız. 
Moshi'nin ek açıklamalarının Kotlin ile düzgün çalışması için KotlinJsonAdapterFactory'yi ekleyin ve ardından build()'i çağırın.

3. Retrofit oluşturucuyu ScalarConverterFactory yerine MoshiConverterFactory kullanacak şekilde değiştirin ve az 
önce oluşturduğunuz moshi örneğini iletin. İstendiğinde retrofit2.converter.moshi.MoshiConverterFactory'yi import edin.

```kotlin
private val retrofit = Retrofit.Builder()
   .addConverterFactory(MoshiConverterFactory.create(moshi))
   .baseUrl(BASE_URL)
   .build()
```

4. ScalarConverterFactory için importu da silin.

Silinecek kod:

```kotlin
import retrofit2.converter.scalars.ScalarsConverterFactory
```

5. Retrofit'i `Call<String>` döndürmek yerine MarsProperty nesnelerinin bir listesini döndürmesini sağlamak için MarsApiService arabirimini güncelleyin.

```kotlin
interface MarsApiService {
   @GET("realestate")
   fun getProperties():
      Call<List<MarsProperty>>
}
```

6. OverviewViewModel.kt'yi açın. getMarsRealEstateProperties() metodunda getProperties().enqueue() çağrısına ilerleyin.
7. enqueue() değişkenini `Callback<String>` yerine `Callback<List<MarsProperty>>` olarak değiştirin. 
İstendiğinde com.example.android.marsrealestate.network.MarsProperty'yi import edin.

```kotlin
MarsApi.retrofitService.getProperties().enqueue( 
   object: Callback<List<MarsProperty>> {
```

8. onFailure() içinde, `Call<String>` olan argümanı `Call<List<MarsProperty>>` olarak değiştirin:

```kotlin
override fun onFailure(call: Call<List<MarsProperty>>, t: Throwable) {
```

9. onResponse() argümanlarının her ikisinde de aynı değişikliği yapın:

```kotlin
override fun onResponse(call: Call<List<MarsProperty>>, 
   response: Response<List<MarsProperty>>) {
```

10. onResponse() içerisinde, _response.value için mevcut atamayı aşağıda gösterilen atama ile değiştirin. Response.body() 
artık MarsProperty nesnelerinin bir listesi olduğundan, bu listenin boyutu ayrıştırılan özelliklerin sayısıdır. 
Bu yanıt mesajı şu sayıda özelliği yazdırır:

```kotlin
_response.value = 
   "Success: ${response.body()?.size} Mars properties retrieved"
```

11- Uçak modunun kapalı olduğundan emin olun. Uygulamayı derleyin ve çalıştırın. Bu sefer mesaj, web servisinden döndürülen özelliklerin sayısını göstermelidir:

![image](https://user-images.githubusercontent.com/29903779/150102521-bef03c55-4c8d-4ad8-bc82-ea9c70686232.png)

## <a name="d"></a>Aşama 4 : Coroutines ile Retrofit’i kullanın

Şimdi Retrofit API hizmeti çalışıyor, ancak uygulamanız gereken iki callback metoduyla bir geri arama kullanıyor. Bir metot başarıyı, 
diğeri başarısızlığı ele alır ve başarısızlık sonucu istisnaları bildirir. Callback kullanmak yerine exception’ları handle edebileceğiniz 
coroutines yapısı kullansaydınız, kodunuz daha verimli ve okunması daha kolay olurdu. Bu görevde, ağ servisinizi ve ViewModel'i coroutines 
kullanmak için dönüştürürsünüz.

### Adım 1: MarsApiService ve OverviewViewModel'i güncelleyin

1. MarsApiService'de getProperties() öğesini suspend function yapın. `Call<List<MarsProperty>>` öğesini `List<MarsProperty>` olarak değiştirin. 
getProperties() metodu şöyle görünür:

```kotlin
@GET("realestate")
suspend fun getProperties(): List<MarsProperty>
```

2. OverviewViewModel.kt dosyasında, getMarsRealEstateProperties() içindeki tüm kodu silin. Burada enqueue() çağrısı ve onFailure() ve 
onResponse() geri çağrıları yerine coroutines kullanacaksınız.
3. getMarsRealEstateProperties() içinde, viewModelScope kullanarak coroutines’i başlatın.

```kotlin
viewModelScope.launch { 

}
```

Bir ViewModelScope, uygulamanızdaki her ViewModel için tanımlanan yerleşik coroutines kapsamıdır. Bu kapsamda başlatılan herhangi bir 
coroutines, ViewModel temizlenirse otomatik olarak iptal edilir.

4. Başlatma bloğunun içine, exception’ları işlemek için bir try/catch bloğu ekleyin:

```kotlin
try {

} catch (e: Exception) {
  
}
```

5. try {} bloğunun içinde, retrofitService nesnesindeki getProperties() metodunu çağırın:

```kotlin
val listResult = MarsApi.retrofitService.getProperties()
```

MarsApi servisinden getProperties() metodunun çağırılması, bir arka plan thread’inde ağ servisini oluşturur ve başlatır.

6. Ayrıca try {} bloğunun içinde, başarılı dönüş için yanıt mesajını güncelleyin:

```kotlin
_response.value = 
   "Success: ${listResult.size} Mars properties retrieved"
```

7. Catch {} bloğunun içinde, hata yanıtını işleyin:

```kotlin
_response.value = "Failure: ${e.message}"
```

GetMarsRealEstateProperties() metodunun tamamı şimdi şöyle görünür:

```kotlin
private fun getMarsRealEstateProperties() {
   viewModelScope.launch {
       try {
           val listResult = MarsApi.retrofitService.getProperties()
           _response.value = "Success: ${listResult.size} Mars properties retrieved"
       } catch (e: Exception) {
           _response.value = "Failure: ${e.message}"
       }
   }
}
```

8. Uygulamayı derleyin ve çalıştırın. Bu sefer önceki görevdekiyle aynı sonucu alırsınız (özellik sayısı raporu), 
ancak daha basit kod ve hata işleme ile.
