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

Header eklemenin başka bir yolu, data grid'iniz için destek veri kümesini değiştirmektir. Görüntülenmesi gereken tüm veriler bir listede saklandığından, bir header'ı temsil edecek öğeleri içerecek şekilde listeyi değiştirebilirsiniz. Bunu anlamak biraz daha basittir, ancak farklı öğe türlerini tek bir listede birleştirebilmeniz için nesnelerinizi nasıl tasarladığınızı düşünmenizi gerektirir. Bu şekilde uygulandığında, adapter kendisine iletilen öğeleri gösterecektir. Dolayısıyla, 0 konumundaki öğe bir header'dır ve 1 konumundaki öğe, doğrudan ekrandakiyle eşleşen bir `SleepNight`'tır.

**[Gerçek Veriler] -> [Adapter Viewları]**

**[0: Header] -> [0: Header]**

[1: SleepNight] -> [1: SleepNight]

[2: SleepNight] -> [2: SleepNight]

[3: SleepNight] -> [3: SleepNight]


Her metodolojinin avantajları ve dezavantajları vardır. Veri kümesini değiştirmek, adapter kodunun geri kalanında fazla bir değişiklik getirmez ve veri listesini değiştirerek header mantığı ekleyebilirsiniz. Öte yandan, headerlar için indexleri kontrol ederek farklı bir `ViewHolder` kullanmak, header'ın layout'unda daha fazla özgürlük sağlar. Ayrıca, adapter'ın, destek verileri değiştirmeden verilerin view'a nasıl uyarlandığını yönetmesini sağlar.

Bu aşamada, `RecyclerView`'ınızı listenin başında bir header gösterecek şekilde güncelleyeceksiniz. Bu durumda, uygulamanız header için veri öğelerinden farklı bir `ViewHolder` kullanacaktır. Uygulama, hangi `ViewHolder`'ın kullanılacağını belirlemek için listenin indexini kontrol edecektir.

## <a name="b"></a>Aşama 2 : RecyclerView'unuza bir header ekleyin

### Adım 1: Bir DataItem class'ı oluşturun

Öğe türünü abstract hale getirmek ve adapter'ın yalnızca "öğeler" ile ilgilenmesine izin vermek için, `SleepNight` veya `Header`'ı temsil eden bir data holder class'ı oluşturabilirsiniz. Veri kümeniz daha sonra data holder öğelerinin bir listesi olacaktır.

Başlangıç uygulamasını GitHub'dan alabilir veya önceki aşamada oluşturduğunuz SleepTracker uygulamasını kullanmaya devam edebilirsiniz.

1. GitHub'dan RecyclerViewHeaders-Starter kodunu indirin. RecyclerViewHeaders-Starter dizini, bu aşama için gereken SleepTracker uygulamasının başlangıç sürümünü içerir. İsterseniz, önceki aşamadan bitmiş uygulamanıza da devam edebilirsiniz.
2. **SleepNightAdapter.kt**'yi açın.
3. `SleepNightListener` class'ının altında, en üst düzeyde, bir veri öğesini temsil eden `DataItem` adlı bir `sealed` class tanımlayın.

Bir `sealed` class, kapalı bir tür tanımlar; bu, `DataItem`'in tüm subclasslarının bu dosyada tanımlanması gerektiği anlamına gelir. Sonuç olarak, subclassların sayısı derleyici tarafından bilinir. Kodunuzun başka bir bölümünün adapter'ınızı bozabilecek yeni bir `DataItem` türü tanımlaması mümkün değildir.

```

sealed class DataItem {

 }

```

4. `DataItem` class'ının body'si içinde, farklı veri öğesi türlerini temsil eden iki class tanımlayın. Birincisi, bir `SleepNight`'ı saran bir `SleepNightItem`'dir, bu yüzden `sleepNight` adında tek bir değer alır. Sealed class'ın bir parçası yapmak için `DataItem`'i extend etmesini sağlayın.

```

data class SleepNightItem(val sleepNight: SleepNight): DataItem()

```

5. İkinci class, bir başlığı temsil eden `Header`'dır. Bir header'ın gerçek verisi olmadığı için onu bir `object` olarak bildirebilirsiniz. Bu, yalnızca bir `Header` instance'ı olacağı anlamına gelir. Yine, `DataItem`'i extend etmesini sağlayın.

```

object Header: DataItem()

```

6. `DataItem` içinde, class düzeyinde, `id` adlı bir `abstract` `Long` özellik tanımlayın. Adapter, bir öğenin değişip değişmediğini ve nasıl değiştiğini belirlemek için `DiffUtil`'i kullandığında, `DiffItemCallback`'in her öğenin id'sini bilmesi gerekir. `SleepNightItem` ve `Header`'ın abstract özellik olan `id`'yi override etmesi gerektiğinden bir hata göreceksiniz.

```

abstract val id: Long

```

7. `SleepNightItem`'de, `nightId` değerini döndürmek için `id` override edin.

```

override val id = sleepNight.nightId

```

8. `Header`'da, çok, çok küçük bir sayı olan [`Long.MIN_VALUE`](https://docs.oracle.com/javase/7/docs/api/java/lang/Long.html) değerini döndürmek için `id`'yi override edin (kelimenin tam anlamıyla, -2 üzeri 63). Bu nedenle, bu asla var olan herhangi bir `nightId` ile çelişmez.

```

override val id = Long.MIN_VALUE

```

9. Bitmiş kodunuz şöyle görünmeli ve uygulamanız build etmelidir.

```

sealed class DataItem {
    abstract val id: Long
    data class SleepNightItem(val sleepNight: SleepNight): DataItem()      {
        override val id = sleepNight.nightId
    }

    object Header: DataItem() {
        override val id = Long.MIN_VALUE
    }
}
    
```

### Adım 2: Header için bir ViewHolder oluşturun

1. Header için layout'u, bir `TextView` görüntüleyen **header.xml** adlı yeni bir layout resource dosyasında oluşturun. Bu konuda heyecan verici bir şey yok, işte kod.

```

<?xml version="1.0" encoding="utf-8"?>
<TextView xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/text"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:textAppearance="?android:attr/textAppearanceLarge"
    android:text="Sleep Results"
    android:padding="8dp" />
    
```

2. `"Sleep Results"`'ı bir string resource'una çıkarın ve onu `header_text` olarak adlandırın.

```

<string name="header_text">Sleep Results</string>

```

3. **SleepNightAdapter.kt** içinde, `SleepNightAdapter` içinde, `ViewHolder` class'ının üzerinde yeni bir `TextViewHolder` class'ı oluşturun. Bu class, **textview.xml** layout'ını inflate eder ve bir `TextViewHolder` instance'ını döndürür. Bunu daha önce yaptığınız için, kod burada ve `View` ve `R`'yi içe import etmeniz gerekecek:

```

 class TextViewHolder(view: View): RecyclerView.ViewHolder(view) {
        companion object {
            fun from(parent: ViewGroup): TextViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.header, parent, false)
                return TextViewHolder(view)
            }
        }
    }

```

### Adım 3: SleepNightAdapter'ı güncelle

Ardından, `SleepNightAdapter` bildirimini güncellemeniz gerekir. Yalnızca bir tür `ViewHolder`'ı desteklemek yerine, herhangi bir view holder'ı kullanabilmesi gerekir.

#### Öğe türlerini tanımlayın

1. `SleepNightAdapter.kt`'de, en üst düzeyde, import ifadelerinin altında ve `SleepNightAdapter`'ın üstünde, view türleri için iki constant (sabit) tanımlayın.

`RecyclerView`'ın her öğenin view türünü ayırt etmesi gerekir, böylece ona bir view holder'ı doğru şekilde atayabilir.

```

    private val ITEM_VIEW_TYPE_HEADER = 0
    private val ITEM_VIEW_TYPE_ITEM = 1

```

2. `SleepNightAdapter` içinde, geçerli öğenin türüne bağlı olarak doğru header'ı veya öğe sabitini döndürmek için `getItemViewType()` öğesini override edecek bir fonksiyon oluşturun.

```

override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DataItem.Header -> ITEM_VIEW_TYPE_HEADER
            is DataItem.SleepNightItem -> ITEM_VIEW_TYPE_ITEM
        }
    }
    
```

#### SleepNightAdapter tanımını güncelleyin

1. `SleepNightAdapter` tanımında, `ListAdapter` için `SleepNight`'tan `DataItem`'e ilk argümanı güncelleyin.
2. `SleepNightAdapter` tanımında, `ListAdapter` için ikinci genel argümanı `SleepNightAdapter.ViewHolder`'dan `RecyclerView.ViewHolder`'a değiştirin. Gerekli güncellemeler için bazı hatalar göreceksiniz ve class header'ınız aşağıda gösterildiği gibi görünmelidir.

```

class SleepNightAdapter(val clickListener: SleepNightListener):
       ListAdapter<DataItem, RecyclerView.ViewHolder>(SleepNightDiffCallback()) {
       
```

#### onCreateViewHolder()'ı güncelleyin

1. Bir `RecyclerView.ViewHolder` döndürmek için `onCreateViewHolder()` imzasını değiştirin.

```

override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder

```

2. Her öğe türü için uygun view holder'ı test etmek ve döndürmek için `onCreateViewHolder()` yönteminin uygulamasını expand edin. Güncellenmiş yönteminiz aşağıdaki kod gibi görünmelidir.

```

override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_HEADER -> TextViewHolder.from(parent)
            ITEM_VIEW_TYPE_ITEM -> ViewHolder.from(parent)
            else -> throw ClassCastException("Unknown viewType ${viewType}")
        }
    }
    
```

#### onBindViewHolder()'ı güncelleyin

1. `onBindViewHolder()` parametre türünü `ViewHolder`'dan `RecyclerView.ViewHolder`'a değiştirin.

```

override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int)

```

2. Holder bir `ViewHolder` ise, yalnızca view holder'a veri atamak için bir koşul ekleyin.

```

        when (holder) {
            is ViewHolder -> {...}

```

3. `getItem()` tarafından döndürülen nesne türünü `DataItem.SleepNightItem` öğesine cast edin. Biten `onBindViewHolder()` fonksiyonunuz şöyle görünmelidir.

```

 override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> {
                val nightItem = getItem(position) as DataItem.SleepNightItem
                holder.bind(nightItem.sleepNight, clickListener)
            }
        }
    }
    
```

####  diffUtil callbacklerini güncelleyin

1. `SleepNight` yerine yeni `DataItem` class'ınızı kullanmak için `SleepNightDiffCallback` içindeki metotları değiştirin. Aşağıdaki kodda gösterildiği gibi lint uyarısını bastırın.

```

class SleepNightDiffCallback : DiffUtil.ItemCallback<DataItem>() {
    override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem.id == newItem.id
    }
    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem == newItem
    }
}

```

#### Header'ı ekleyin ve gönderin

1. `SleepNightAdapter` içinde, `onCreateViewHolder()` altında, aşağıda gösterildiği gibi `addHeaderAndSubmitList()` fonksiyonunu tanımlayın. Bu fonksiyon `SleepNight` listesini alır. Listenizi göndermek için `ListAdapter` tarafından sağlanan `sendList()`'i kullanmak yerine, bir header eklemek ve ardından listeyi göndermek için bu fonksiyonu kullanacaksınız.

```

fun addHeaderAndSubmitList(list: List<SleepNight>?) {}

```

2. `addHeaderAndSubmitList()` içinde, iletilen liste `null`'sa, yalnızca bir header döndürün, aksi takdirde header'ı listenin başına ekleyin ve ardından listeyi gönderin.

```

val items = when (list) {
                null -> listOf(DataItem.Header)
                else -> listOf(DataItem.Header) + list.map { DataItem.SleepNightItem(it) }
            }
submitList(items)

```

3. **SleepTrackerFragment.kt**'yi açın ve çağrıyı `submitList()`'ten `addHeaderAndSubmitList()`'te çevirin.
4. Uygulamanızı çalıştırın ve header'ınızın uyku öğeleri listesinde ilk öğe olarak nasıl görüntülendiğini gözlemleyin.

![app_image](https://developer.android.com/codelabs/kotlin-android-training-headers/img/cdb7b972b40279a0.png)


## <a name="c"></a>Aşama 3 : Liste manipülasyonları için coroutineleri kullanın

Bu uygulama için düzeltilmesi gereken iki şey var. Biri görünür, biri görünmez.

- Header sol üst köşede görünür ve kolayca ayırt edilemez.
- Tek header içeren kısa bir liste için çok önemli değil, ancak UI thread'inde `addHeaderAndSubmitList()` içinde liste manipülasyonu yapmamalısınız. Öğelerin nereye eklenmesi gerektiğine karar vermek için yüzlerce öğe, birden çok header ve mantık içeren bir liste hayal edin. Bu çalışma bir coroutine'e aittir.

Coroutines kullanmak için `addHeaderAndSubmitList()` öğesini değiştirin:

1. `SleepNightAdapter` class'ının en üst düzeyinde, `Dispatchers.Default` ile bir `CoroutineScope` tanımlayın.

```

private val adapterScope = CoroutineScope(Dispatchers.Default)

```

2. `addHeaderAndSubmitList()` içinde, listeyi işlemek için `AdapterScope`'ta bir coroutine başlatın. Ardından, aşağıdaki kodda gösterildiği gibi listeyi göndermek için `Dispatchers.Main` context'ine geçin.

```

 fun addHeaderAndSubmitList(list: List<SleepNight>?) {
        adapterScope.launch {
            val items = when (list) {
                null -> listOf(DataItem.Header)
                else -> listOf(DataItem.Header) + list.map { DataItem.SleepNightItem(it) }
            }
            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }
    
```

3. Kodunuz build etmeli ve çalışmalıdır ve herhangi bir fark görmezsiniz.

## <a name="d"></a>Aşama 4 : Header'ı ekrana yayılacak şekilde uzatın

Şu anda header, grid'deki diğer öğelerle aynı genişliktedir ve yatay ve dikey olarak bir span kaplar. Tüm grid, bir span genişliğindeki üç öğeyi yatay olarak sığdırır, bu nedenle header yatay olarak üç span kullanmalıdır.

Header genişliğini düzeltmek için `GridLayoutManager`'a verilerin tüm sütunlara ne zaman yayılacağını söylemeniz gerekir. Bunu bir `GridLayoutManager`'da `SpanSizeLookup`'ı yapılandırarak yapabilirsiniz. Bu, `GridLayoutManager`'ın listedeki her bir öğe için kaç span kullanacağını belirlemek için kullandığı bir yapılandırma nesnesidir.

1. **SleepTrackerFragment.kt**'yi açın.
2. `onCreateView()`'ın sonuna doğru, `manager`'ı tanımladığınız kodu  bulun.


```

val manager = GridLayoutManager(activity, 3)

```

3. `manager`'ın altında, gösterildiği gibi `manager.spanSizeLookup` öğesini tanımlayın. `setSpanSizeLookup` lambda almadığı için bir `object` yapmanız gerekiyor. Kotlin'de bir `object` yapmak için `object : classname` yazın, bu durumda `GridLayoutManager.SpanSizeLookup`.

```

manager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
}

```

4. Constructor çağırmak için bir derleyici hatası alabilirsiniz. Bunu yaparsanız, constructor çağrısını uygulamak için intention menüsünü `Option+Enter` (Mac) veya `Alt+Enter` (Windows) ile açın.
5. Ardından, metotları override etmeniz gerektiğini söyleyen `object`'te bir hata alırsınız. İmleci `object`'in üzerine getirin, intention menüsünü açmak için `Option+Enter` (Mac) veya `Alt+Enter` (Windows) tuşlarına basın, ardından `getSpanSize()` metodunu override edin.
6. `getSpanSize()` body'sinde, her position için doğru span boyutunu döndürün. 0 position'ının span boyutu 3'tür ve diğer positionların span boyutu 1'dir. Tamamlanan kodunuz aşağıdaki kod gibi görünmelidir:

```

   manager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int) =  when (position) {
                0 -> 3
                else -> 1
            }
        }
        
```

7. Header'ınızın görünümünü iyileştirmek için **header.xml** dosyasını açın ve bu kodu **header.xml** layout dosyasına ekleyin.

```

android:textColor="@color/white_text_color"
android:layout_marginStart="16dp"
android:layout_marginTop="16dp"
android:layout_marginEnd="16dp"
android:background="@color/colorAccent"

```

8. Uygulamanızı çalıştırın. Aşağıdaki ekran görüntüsü gibi görünmelidir.

![final app](https://developer.android.com/codelabs/kotlin-android-training-headers/img/eb760aae692a980c.png)

Tebrikler! Bitirdiniz.

