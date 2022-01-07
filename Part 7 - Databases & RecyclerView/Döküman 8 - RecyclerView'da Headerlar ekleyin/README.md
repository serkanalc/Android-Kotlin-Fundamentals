# <a name="1"></a>RecyclerView'da Headerlar Ekleyin

- [RecyclerView'da Headerlar](#a)
- [RecyclerView'unuza bir header ekleyin](#b)
- [Liste manipülasyonları için coroutineleri kullanın](#c)
- [Header'ı ekrana yayılacak şekilde uzatın](#d)

Sleep-tracker uygulamasının, aşağıdaki şekilde gösterildiği gibi fragmentlarla temsil edilen üç ekranı vardır.

![app_image](https://developer.android.com/codelabs/kotlin-android-training-headers/img/b0abde98c5f99bf6.png)
![app_image](https://developer.android.com/codelabs/kotlin-android-training-headers/img/43590f0a4c00e138.png)
![app_image](https://developer.android.com/codelabs/kotlin-android-training-headers/img/1018f2610bca049.png)

Solda gösterilen ilk ekranda, izlemeyi başlatmak ve durdurmak için butonlar vardır. Ekran, kullanıcının tüm uyku verilerini gösterir. **Clear** butonu, uygulamanın kullanıcı için topladığı tüm verileri kalıcı olarak siler. Sağda gösterilen ikinci ekran, uyku kalitesi derecelendirmesini seçmek içindir. Üçüncü ekran, kullanıcı grid'deki bir öğeye dokunduğunda açılan ayrıntılı bir view'dur.

Bu uygulama, uyku verilerini sürdürmek (persist) için bir UI controller, `ViewModel` ve `LiveData` ve bir `Room` veritabanı ile basitleştirilmiş bir mimari kullanır.

![architecture image](https://developer.android.com/codelabs/kotlin-android-training-grid-layout/img/49f975f1e5fe689.png)

Bu aşamada, görüntülenen öğelerin grid'ine bir başlık eklersiniz. Son ana ekranınız şöyle görünecek:

![app_image](https://developer.android.com/codelabs/kotlin-android-training-headers/img/eb760aae692a980c.png)

## <a name="a"></a>Aşama 1 : RecyclerView'da Headerlar

Bu aşama, bir `RecyclerView`'da farklı layoutlar kullanan öğeleri dahil etmenin genel ilkesini öğretir. Yaygın bir örnek, listenizde veya grid'inizde başlıklara sahip olmaktır. Bir liste, öğe içeriğini açıklamak için tek bir başlığa sahip olabilir. Bir liste ayrıca, öğeleri tek bir listede gruplamak ve ayırmak için birden çok başlığa sahip olabilir.

`RecyclerView`, verileriniz veya her bir öğenin ne tür bir layout'a sahip olduğu hakkında hiçbir şey bilmiyor. `LayoutManager` ekrandaki öğeleri düzenler, ancak adapter görüntülenecek verileri uyarlar ve view holderları `RecyclerView`'a iletir. Böylece adapterda başlıklar oluşturmak için kodu ekleyeceksiniz.


### Header eklemenin iki yolu

`RecyclerView`'da listedeki her öğe 0'dan başlayan bir index (dizin) numarasına karşılık gelir. Örneğin:

**[Gerçek Veriler] -> [Adapter Viewları]**

[0: SleepNight] -> [0: SleepNight]

[1: SleepNight] -> [1: SleepNight]

[2: SleepNight] -> [2: SleepNight]

Bir listeye header eklemenin bir yolu, header'ınızın gösterilmesi gereken indexleri kontrol ederek adapterınızı farklı bir `ViewHolder` kullanacak şekilde değiştirmektir. `Adapter`, başlığın takibinden sorumlu olacaktır. Örneğin, tablonun üst kısmında bir header göstermek için sıfır indexli öğeyi düzenlerken header için farklı bir `ViewHolder` döndürmeniz gerekir. Ardından, diğer tüm öğeler, aşağıda gösterildiği gibi header offset'i ile eşlenir.

**[Gerçek Veriler] -> [Adapter Viewları]**

**[0: Header]**

[0: SleepNight] -> [1: SleepNight]

[1: SleepNight] -> [2: SleepNight]

[2: SleepNight] -> [3: SleepNight.

Header eklemenin başka bir yolu, data grid'iniz için destek veri set'ini değiştirmektir. Görüntülenmesi gereken tüm veriler bir listede saklandığından, bir header'ı temsil edecek öğeleri içerecek şekilde listeyi değiştirebilirsiniz. Bunu anlamak biraz daha basittir, ancak farklı öğe türlerini tek bir listede birleştirebilmeniz için nesnelerinizi nasıl tasarladığınızı düşünmenizi gerektirir. Bu şekilde uygulandığında, adapter kendisine iletilen öğeleri gösterecektir. Dolayısıyla, 0 konumundaki öğe bir header'dır ve 1 konumundaki öğe, doğrudan ekrandakiyle eşleşen bir `SleepNight`'tır.

**[Gerçek Veriler] -> [Adapter Viewları]**

**[0: Header] -> [0: Header]**

[1: SleepNight] -> [1: SleepNight]

[2: SleepNight] -> [2: SleepNight]

[3: SleepNight] -> [3: SleepNight]


## <a name="b"></a>Aşama 2 : RecyclerView'unuza bir header ekleyin
## <a name="c"></a>Aşama 3 : Liste manipülasyonları için coroutineleri kullanın
## <a name="d"></a>Aşama 4 : Header'ı ekrana yayılacak şekilde uzatın

