# <a name="1"></a>RecyclerView ile DiffUtil & Data Binding

- [Şimdiye kadar neler yaptığınızı gözden geçirin & başlayın](#a)
- [DiffUtil ile listenin içeriğini yenileyin](#b)
- [Listenizi yönetmek için ListAdapter kullanın](#c)
- [RecyclerView ile DataBinding kullanın](#d)
- [Binding adapterlar yaratın](#e)

Başlangıç uygulamasının, aşağıdaki şekilde gösterildiği gibi fragmentlarla temsil edilen iki ekranı vardır.

![app image](https://developer.android.com/codelabs/kotlin-android-training-diffutil-databinding/img/76ec2be31e018176.png)

Solda gösterilen ilk ekranda, izlemeyi başlatmak ve durdurmak için butonlar vardır. Ekran, kullanıcının tüm uyku verilerini gösterir. **Clear** butonu, uygulamanın kullanıcı için topladığı tüm verileri kalıcı olarak siler. Sağda gösterilen ikinci ekran, uyku kalitesi derecelendirmesini seçmek içindir.

Bu uygulama, uyku verilerini sürdürmek (persist) için bir UI controller, `ViewModel` ve `LiveData` ve bir `Room` veritabanı kullanacak şekilde tasarlanmıştır.

![architecture image](https://developer.android.com/codelabs/kotlin-android-training-diffutil-databinding/img/49f975f1e5fe689.png)

Uyku verileri bir `RecyclerView`'da görüntülenir. Bu aşamada, `RecyclerView` için `DiffUtil` ve data binding bölümünü oluşturacaksınız. Bu aşamadan sonra uygulamanız tamamen aynı görünecek, ancak daha verimli ve ölçeklenmesi ve bakımı daha kolay olacaktır.

## <a name="a"></a>Aşama 1 : Şimdiye kadar neler yaptığınızı gözden geçirin & başlayın

Önceki aşamadan SleepTracker uygulamasını kullanmaya devam edebilir veya GitHub'dan RecyclerViewDiffUtilDataBinding-Starter uygulamasını indirebilirsiniz. <!-- link ekle -->

1. Gerekirse GitHub'dan RecyclerViewDiffUtilDataBinding-Starter uygulamasını indirin ve projeyi Android Studio'da açın.
2. Uygulamayı çalıştırın.
3. `SleepNightAdapter.kt` dosyasını açın.
4. Uygulamanın yapısını tanımak için kodu inceleyin. Kullanıcıya uyku verilerini göstermek için adapter pattern'ıyla `RecyclerView` kullanımının bir özeti için aşağıdaki şemaya bakın.

![adapter pattern](https://developer.android.com/codelabs/kotlin-android-training-diffutil-databinding/img/6c32c918f96efb46.png)

- Uygulama, kullanıcı girişinden `SleepNight` nesnelerinin bir listesini oluşturur. Her `SleepNight` nesnesi, tek bir uyku gecesini, süresini ve kalitesini temsil eder.
- `SleepNightAdapter`, `SleepNight` nesnelerinin listesini `RecyclerView`'un kullanabileceği ve görüntüleyebileceği bir şeye uyarlar.
- `SleepNightAdapter` adapter'ı, recycler view'un verileri görüntülemesi için viewları, verileri ve meta bilgileri içeren `ViewHolder`lar üretir.
- `RecyclerView`, görüntülenecek kaç öğe olduğunu belirlemek için `SleepNightAdapter`'ı kullanır (`getItemCount()`). `RecyclerView`, görüntüleme için verilere bağlı view holderları elde etmek için `onCreateViewHolder()` ve `onBindViewHolder()` kullanır.

### notifyDataSetChanged() metodu verimsizdir

`RecyclerView`'a listedeki bir öğenin değiştiğini ve güncellenmesi gerektiğini söylemek için, geçerli kod aşağıda gösterildiği gibi `SleepNightAdapter`'da [`notifyDataSetChanged()`](https://developer.android.com/reference/android/widget/BaseAdapter) fonksiyonunu çağırır.

```

var data =  listOf<SleepNight>()
   set(value) {
       field = value
       notifyDataSetChanged()
   }
   
```

Ancak `notifyDataSetChanged()`, `RecyclerView`'a tüm listenin potansiyel olarak geçersiz olduğunu söyler. Sonuç olarak, `RecyclerView`, ekranda görünmeyen öğeler de dahil olmak üzere listedeki her öğeyi yeniden bind eder ve yeniden çizer. Bu çok gereksiz bir iş. Büyük veya karmaşık listeler için bu işlem, kullanıcı listede gezinirken ekranın titremesine veya teklemeye başlamasına yetecek kadar uzun sürebilir.

Bu sorunu çözmek için `RecyclerView`'a tam olarak neyin değiştiğini söyleyebilirsiniz. `RecyclerView` daha sonra yalnızca ekranda değişen görünümleri güncelleyebilir.

`RecyclerView`, tek bir öğeyi güncellemek için zengin bir API'a sahiptir. `RecyclerView`'a bir öğenin değiştiğini söylemek için [`notifyItemChanged()`](https://developer.android.com/reference/android/support/v7/widget/RecyclerView.Adapter#notifyitemchanged)'ı kullanabilir ve eklenen, kaldırılan veya taşınan öğeler için benzer fonksiyonları kullanabilirsiniz. Hepsini manuel olarak yapabilirsiniz, ancak bu görev önemsiz olmayacak ve oldukça fazla kod gerektirebilir.

Neyse ki, daha iyi bir yol var.

### DiffUtil verimlidir ve zor işi sizin için yapar

`RecyclerView`, iki liste arasındaki farkları hesaplamak için `DiffUtil` adında bir class'a sahiptir. `DiffUtil` eski bir liste ve yeni bir listeyi alır ve neyin farklı olduğunu bulur. Eklenen, kaldırılan veya değiştirilen öğeleri bulur. Ardından, yeni listeyi oluşturmak için eski listeden yapılacak minimum değişiklik sayısını bulmak için [Eugene W. Myers'ın fark algoritması](https://en.wikipedia.org/wiki/Diff) adlı bir algoritma kullanır.

`DiffUtil` neyin değiştiğini bulduğunda, `RecyclerView` bu bilgiyi yalnızca değiştirilen, eklenen, kaldırılan veya taşınan öğeleri güncellemek için kullanabilir, bu da tüm listeyi yeniden yapmaktan çok daha verimlidir.

## <a name="b"></a>Aşama 2 : DiffUtil ile listenin içeriğini yenileyin

Bu aşamada, verilerdeki değişiklikler için `RecyclerView`'ı optimize etmek için `DiffUtil`'i kullanmak üzere `SleepNightAdapter`'ı iyileştirirsiniz.

### Adım 1: SleepNightDiffCallback'i uygulayın

`DiffUtil` class'ının işlevselliğini kullanmak için `DiffUtil.ItemCallback`'i extend edin.

1. `SleepNightAdapter.kt`'yi açın
2. `SleepNightAdapter` için class tanımının altında, `DiffUtil.ItemCallback`'i extend eden `SleepNightDiffCallback` adlı yeni bir top-level class oluşturun. Generic bir parametre olarak `SleepNight`'ı iletin.

```

class SleepNightDiffCallback : DiffUtil.ItemCallback<SleepNight>() {
}

```

4. İmleci `SleepNightDiffCallback` class adına getirin.
5. `Alt+Enter` (Mac'te `Option+Enter`) tuşlarına basın ve **Implement Members** seçeneğini seçin.
6. Açılan iletişim kutusunda, `areItemsTheSame()` ve `areContentsTheSame()` metotlarını seçmek için shift tuşuna basarak sol tıklayın, ardından **OK**'a tıklayın.

Bu, aşağıda gösterildiği gibi iki metot için `SleepNightDiffCallback` içinde taslaklar oluşturur. `DiffUtil`, listenin ve öğelerin nasıl değiştiğini anlamak için bu iki metodu kullanır.


```

   override fun areItemsTheSame(oldItem: SleepNight, newItem: SleepNight): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun areContentsTheSame(oldItem: SleepNight, newItem: SleepNight): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

```

6. `areItemsTheSame()` içindeki TODO'yu, geçirilen iki SleepNight öğesinin, `oldItem` ve `newItem`'in aynı olup olmadığını test eden kodla değiştirin. Öğeler aynı `nightId`'ye sahipse, bunlar aynı öğedir, bu nedenle `true` değerini döndürmelidir. Aksi takdirde, `false` dönmelidir. `DiffUtil`, bir öğenin eklenip eklenmediğini, kaldırıldığını veya taşındığını keşfetmeye yardımcı olmak için bu testi kullanır.

```

override fun areItemsTheSame(oldItem: SleepNight, newItem: SleepNight): Boolean {
   return oldItem.nightId == newItem.nightId
}

```

7. `areContentsTheSame()` içinde, `oldItem` ve `newItem`'in aynı verileri içerip içermediğini kontrol edin; yani, eşit olup olmadıklarını. `SleepNight` bir data class olduğundan, bu eşitlik kontrolü tüm alanları kontrol edecektir. `Data` classları, sizin için `equals` ve birkaç başka metodu sizin için otomatik olarak tanımlar. `oldItem` ve `newItem` arasında farklılıklar varsa, bu kod `DiffUtil`'e öğenin güncellendiğini bildirir.

```

override fun areContentsTheSame(oldItem: SleepNight, newItem: SleepNight): Boolean {
   return oldItem == newItem
}

```

## <a name="c"></a>Aşama 3 : Listenizi yönetmek için ListAdapter kullanın

Değişen bir listeyi görüntülemek için bir `RecyclerView` kullanmak yaygın bir pattern'dır. `RecyclerView`, bir liste tarafından desteklenen bir `RecyclerView` adapter oluşturmanıza yardımcı olan `ListAdapter` adlı bir adapter class'ı sağlar.

`ListAdapter` sizin için listenin kaydını tutar ve liste güncellendiğinde adapter'a haber verir.

### Adım 1: Adapter'ınızı ListAdapter'ı extend etmesi için değiştirin

1. `SleepNightAdapter.kt` dosyasında, `ListAdapter`'ı extend etmek için `SleepNightAdapter`'ın class imzasını değiştirin.
2. İstenirse `androidx.recyclerview.widget.ListAdapter`'ı import edin.
3. `SleepNightAdapter.ViewHolder`'dan önce, `ListAdapter`'a ilk argüman olarak `SleepNight`'ı ekleyin.
4. Constructor'a parametre olarak `SleepNightDiffCallback()` ekleyin. `ListAdapter` bunu listede nelerin değiştiğini bulmak için kullanacaktır. Bitmiş `SleepNightAdapter` class imzanız aşağıda gösterildiği gibi görünmelidir.

```

class SleepNightAdapter : ListAdapter<SleepNight, SleepNightAdapter.ViewHolder>(SleepNightDiffCallback()) {

```

5. `SleepNightAdapter` classının içinde, setter dahil tüm `data` alanını silin. Artık buna ihtiyacınız yok, çünkü `ListAdapter` listeyi sizin için takip edecek.
6. `ListAdapter` bu metodu sizin için uyguladığı için `getItemCount()` override'ını silin.
7. `onBindViewHolder()`'daki hatadan kurtulmak için `item` değişkenini değiştirin. Bir `item`'i almak için `data`'yı kullanmak yerine, `ListAdapter`'ın sağladığı `getItem(position)` metodunu çağırın.

```

val item = getItem(position)

```

### Adım 2: Listeyi güncel tutmak için submitList()'i kullanın

Değiştirilmiş bir liste mevcut olduğunda, kodunuzun bunu `ListAdapter`'a bildirmesi gerekir. `ListAdapter`, `ListAdapter`'a listenin yeni bir sürümünün mevcut olduğunu bildirmek için `sendList()` adlı bir metot sağlar. Bu metot çağrıldığında, `ListAdapter` yeni listeyi eski liste ile karşılaştırır ve eklenen, kaldırılan, taşınan veya değiştirilen öğeleri algılar. Ardından `ListAdapter`, `RecyclerView` tarafından gösterilen öğeleri günceller.

1. `SleepTrackerFragment.kt`'yi açın.
2. `onCreateView()` içinde, `sleepTrackerViewModel` üzerindeki observer'da, sildiğiniz `data` değişkeninin referans alındığı hatayı bulun.
3. `adapter.data = it`'i, `adapter.submitList(it)` çağrısıyla değiştirin. Güncellenmiş kod aşağıda gösterilmektedir.

```

sleepTrackerViewModel.nights.observe(viewLifecycleOwner, Observer {
   it?.let {
       adapter.submitList(it)
   }
})

```

4. Uygulamanızı çalıştırın. findNavController'ı import etmeniz gerekebilir. Uygulamanızın daha hızlı çalıştığını fark edebilirsiniz, listeniz küçükse fark edilmeyebilir.


## <a name="d"></a>Aşama 4 : RecyclerView ile DataBinding kullanın

Bu aşamada, data binding'i ayarlamak için önceki codelabler ile aynı tekniği kullanacak ve `findViewById()` çağrılarını ortadan kaldıracaksınız.

### Adım 1: Layout dosyasına data binding ekleyin

1. **Code** sekmesinde `list_item_sleep_night.xml` layout dosyasını açın.
2. İmleci `ConstraintLayout` tag'inin üzerine getirin ve `Alt+Enter` (Mac'te `Option+Enter`) tuşlarına basın. Intention menüsü ("quick fix" menüsü) açılacaktır.
3. **Convert to data binding layout**'u seçin. Bu, layout'u `<layout>` içine sarar ve içine bir `<data>` tag'i ekler.
4. Gerekirse en başa dönün ve `<data>` tag'inin içinde `sleep` adında bir değişken bildirin.
5. `type`'ını `SleepNight`'ın tam adı yapın, `com.example.android.trackmysleepquality.database.SleepNight`. Bitmiş `<data>` tag'iniz aşağıda gösterildiği gibi görünmelidir.

```

 <data>
        <variable
            name="sleep"
            type="com.example.android.trackmysleepquality.database.SleepNight"/>
    </data>

```

6. `Binding` nesnesinin oluşturulmasını zorlamak için önce **Build > Clean Project**'i, sonra **Build > Rebuild Project**'i seçin. (Eğerhala sorun yaşıyorsanız, **File > Invalidate Caches / Restart**'ı seçin.) `ListItemSleepNightBinding` binding nesnesi, ilgili kodla birlikte projenin oluşturulan dosyalarına eklenir.


### Adım 2: Data binding kullanarak item layout'u inflate edin

1. `SleepNightAdapter.kt`'yi açın.
2. `companion object` içinde, `from(parent: ViewGroup)` fonksiyonunu bulun.
3. `view` değişkeninin bildirimini silin.

**Silinecek** kod:

```

val view = layoutInflater
       .inflate(R.layout.list_item_sleep_night, parent, false)
       
```

4. `view` değişkeninin olduğu yerde, aşağıda gösterildiği gibi `ListItemSleepNightBinding` binding nesnesini inflate eden `binding` adlı yeni bir değişken tanımlayın. Binding nesnesinin gerekli içe aktarımını yapın.

```

val binding =
ListItemSleepNightBinding.inflate(layoutInflater, parent, false)

```

5. Fonksiyonun sonunda, `view` döndürmek yerine `binding` döndürün.

```

return ViewHolder(binding)

```

6. `binding` üzerindeki hatadan kurtulmak için imlecinizi `binding` kelimesinin üzerine getirin. Intention menüsünü açmak için `Alt+Enter `(Mac'te `Option+Enter`) tuşlarına basın.
7. **Change parameter ‘itemView' type of primary constructor of class ‘ViewHolder' to ‘ListItemSleepNightBinding'**'i seçin. Bu, `ViewHolder` classının parametre türünü günceller.

![intention_menu](https://developer.android.com/codelabs/kotlin-android-training-diffutil-databinding/img/d7213f958ae695b5.png)

8. İmzadaki değişikliği görmek için `ViewHolder`'ın class tanımına gidin. `from()` metodunda `itemView`'ı `binding` olarak değiştirdiğiniz için `itemView` için bir hata görüyorsunuz.

`ViewHolder` class tanımında, `itemView` oluşumlarından birine sağ tıklayın ve **Refactor** > **Rename** öğesini seçin. Adı `binding` olarak değiştirin.

9. `binding` parametresini bir özellik (property) yapmak için önüne `val` anahtar kelimesini koyun.
10. `RecyclerView.ViewHolder` parent class'ına yapılan çağrıda, parametreyi `binding` yerine `binding.root` olarak değiştirin. Bir `View`'u iletmeniz gerekir ve `binding.root`, öğe layout'unuzdaki kök (root) `ConstraintLayout`'tur.
11. Bitmiş class bildiriminiz aşağıdaki kod gibi görünmelidir.

```

class ViewHolder private constructor(val binding: ListItemSleepNightBinding) : RecyclerView.ViewHolder(binding.root){

```

Ayrıca `findViewById()` çağrıları için hatalar göreceksiniz. Bu hataları bir sonraki bölümde düzelteceksiniz.

### Adım 3: findViewById()'yi değiştirin

Artık findViewById() yerine `binding` nesnesini kullanmak için `sleepLength`, `quality` ve `qualityImage` özelliklerini güncelleyebilirsiniz.

1. Aşağıda gösterildiği gibi, `binding` nesnesinin viewlarını kullanmak için `sleepLength`, `qualityString` ve `qualityImage`'ın ilk değer tanımlamalarını değiştirin. Bundan sonra, kodunuz daha fazla hata göstermemelidir.

```

val sleepLength: TextView = binding.sleepLength
val quality: TextView = binding.qualityString
val qualityImage: ImageView = binding.qualityImage

```

binding nesnesi yerindeyken, artık `sleepLength`, `quality` ve `qualityImage` özelliklerini tanımlamanız gerekmez. `DataBinding`, aramaları (lookups) önbelleğe alır, bu nedenle bu özellikleri bildirmeye gerek yoktur.

2. `sleepLength`, `quality` ve `qualityImage` özellik adlarına sağ tıklayın. Her özellik için **Refactor > Inline**'ı seçin veya `Ctrl+Alt+N`'ye (Mac'te `Option+Command+N`) basın.

![inline property](https://developer.android.com/codelabs/kotlin-android-training-diffutil-databinding/img/b136364471dd01fb.png)

3. Uygulamanızı çalıştırın. (Hatalar varsa projenizi **Clean** ve **Rebuild** etmeniz gerekebilir.)

## <a name="e"></a>Aşama 5 : Binding adapterlar yaratın

Bu aşamada, viewlarınızdaki verileri ayarlamak için binding adapterlar ile data binding kullanmak için uygulamanızı güncelleyeceksiniz.

Önceki bir codelabde, `LiveData`'yı almak ve text viewlarda görüntülemek üzere biçimlendirilmiş stringler oluşturmak için [`Transformations`](https://developer.android.com/reference/android/arch/lifecycle/Transformations) classını kullandınız. Ancak, farklı veri türlerini veya karmaşık türleri bind etmeniz gerekiyorsa, data binding'in bu türleri kullanmasına yardımcı olmak için binding adapterlar sağlayabilirsiniz. Binding adapterlar, verilerinizi alan ve data binding'in text veya image gibi bir view'u bind etmek için kullanabileceği bir şeye uyarlayan adapterlardır.

Uyku kalitesi görüntüsü için bir tane ve her text alanı için bir tane olmak üzere üç binfing adapter uygulayacaksınız. Özetle, bir binding adapter'ı bildirmek için, bir öğe ve bir view alan bir metot tanımlarsınız ve bunu `@BindingAdapter` ile annotate edersiniz. Metodun bodysinde, dönüşümü uygularsınız. Kotlin'de, verileri alan view classına bir extension fonksiyonu olarak bir binding adapter yazabilirsiniz.

### Adım 1: Binding adapterlar oluşturun

Bu adımda bir dizi class'ı import etmeniz gerekeceğini unutmayın.

1. `SleepNightAdapter.kt`'yi açın.
2. `ViewHolder` classının içinde `bind()` metodunu bulun ve kendinize bu metodun ne yaptığını hatırlatın. `binding.sleepLength`, `binding.quality` ve `binding.qualityImage` için değerleri hesaplayan kodu alacak ve bunun yerine adapter'ın içinde kullanacaksınız. (Şimdilik kodu olduğu gibi bırakın; sonraki adımda taşıyacaksınız.)
3. `sleeptracker` paketinde, `BindingUtils.kt` isimli yeni bir dosya oluşturun ve açın.
4. Bir sonraki adımda statik fonksiyonlar oluşturacağınız için `BindingUtils` classındaki her şeyi silin.

```

class BindingUtils {}

```

5. `TextView` üzerinde `setSleepDurationFormatted` adlı bir extension fonksiyonu bildirin ve bir `SleepNight` iletin. Bu fonksiyon, uyku süresini hesaplamak ve biçimlendirmek için adapter'ınız olacaktır.

```

fun TextView.setSleepDurationFormatted(item: SleepNight) {}

```

6. `setSleepDurationFormatted` gövdesinde, verileri `ViewHolder.bind()`'da yaptığınız gibi view'a bind edin. `convertDurationToFormatted()` öğesini çağırın ve ardından `TextView` `text`'ini biçimlendirilmiş metne ayarlayın. (Bu, `TextView`'daki bir extension fonksiyonu olduğundan, `text` özelliğine doğrudan erişebilirsiniz.)


```

text = convertDurationToFormatted(item.startTimeMilli, item.endTimeMilli, context.resources)

```

7. Bu binding adapter hakkında data binding'i anlatmak için, fonksiyonu `@BindingAdapter` ile annotate edin.
8. Bu fonksiyon, `sleepDurationFormatted` özelliğinin adapter'ıdır, bu nedenle `sleepDurationFormatted` öğesini `@BindingAdapter` öğesine argüman olarak iletin.

```

@BindingAdapter("sleepDurationFormatted")

```

9. İkinci adapter, uyku kalitesini bir `SleepNight` nesnesindeki değere göre ayarlar. `TextView` üzerinde `setSleepQualityString()` adlı başka bir extension fonksiyonu oluşturun ve bir `SleepNight` iletin.
10. Body'de, verileri `ViewHolder.bind()`'da yaptığınız gibi view'a bind edin. `convertNumericQualityToString`'i çağırın ve `text`'i ayarlayın.
11. `@BindingAdapter("sleepQualityString")` ile fonksiyonu annotate edin.

```

@BindingAdapter("sleepQualityString")
fun TextView.setSleepQualityString(item: SleepNight) {
   text = convertNumericQualityToString(item.sleepQuality, context.resources)
}

```

12. Görüntüyü bir image view'a ayarlayan üçüncü bir binding adapter'a ihtiyacımız var. Üçüncü extension fonksiyonunu `ImageView` üzerinde oluşturun, `setSleepImage`'ı çağırın ve aşağıda gösterildiği gibi `ViewHolder.bind()`'daki kodu kullanın.

```

@BindingAdapter("sleepImage")
fun ImageView.setSleepImage(item: SleepNight) {
   setImageResource(when (item.sleepQuality) {
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

convertDurationToFormatted ve convertNumericQualityToString'i import etmeniz gerekebilir.

### Adım 2: SleepNightAdapter'ı güncelleyin

1. `SleepNightAdapter.kt`'yi açın.
2. `bind()` metodundaki her şeyi silin, çünkü artık bu işi sizin yerinize yapmak için data binding'i ve yeni adapterlarınızı kullanabilirsiniz.

```

fun bind(item: SleepNight) {
}

```

3. `bind()` içinde, `item`'e sleep'i atayın, çünkü binding nesnesine yeni `SleepNight`'ınız hakkında bilgi vermeniz gerekir.

```

binding.sleep = item

```

4. Bu satırın altına `binding.executePendingBindings()` ekleyin. Bu çağrı, data binding'in bekleyen bağlamaları hemen yürütmesini isteyen bir optimizasyondur. Bir `RecyclerView`'da binding adapterları kullandığınızda `executePendingBindings()` öğesini çağırmak her zaman iyi bir fikirdir, çünkü viewları boyutlandırmayı biraz hızlandırabilir.

```

binding.executePendingBindings()

```

### Adım 3: XML layout'una bindingleri ekleyin

1. `list_item_sleep_night.xml`'i açın.
2. `ImageView`'da, görüntüyü ayarlayan binding adapter ile aynı ada sahip bir `app` özelliği ekleyin. Aşağıda gösterildiği gibi `sleep` değişkenini iletin.

Bu özellik, adapter aracılığıyla view ve binding nesnesi arasındaki bağlantıyı oluşturur. `sleepImage`'a her referans edildiğinde, adapter `SleepNight`'tan gelen verileri uyarlayacaktır.


```

app:sleepImage="@{sleep}"

```

3. Şimdi, `sleep_length` ve `quality_string` text viewları için benzer bir app özelliği ekleyin. `sleepDurationFormatted` veya `sleepQualityString` her referans edildiğinde, adapterlar `SleepNight`'tan gelen verileri uyarlayacaktır. Her özelliği ilgili `TextView`'a koyduğunuzdan emin olun.

```

app:sleepDurationFormatted="@{sleep}"

```

```

app:sleepQualityString="@{sleep}"

```

4. Uygulamanızı çalıştırın. Daha önce olduğu gibi tamamen aynı şekilde çalışır. Binding adapterlar, veri değiştikçe viewları biçimlendirme ve güncelleme işlerinin tümünü üstlenir, `ViewHolder`'ı basitleştirir ve koda eskisinden çok daha iyi bir yapı verir.

Son birkaç alıştırma için aynı listeyi görüntülediniz. Bu tasarım gereği böyle yapıldı; `Adapter` interface'inin kodunuzu birçok farklı şekilde tasarlamanıza izin verdiğini göstermek için. Kodunuz ne kadar karmaşıksa, onu iyi tasarlamak o kadar önemli hale gelir. Üretim uygulamalarında bu patternlar ve diğerleri `RecyclerView` ile birlikte kullanılır. Patternların hepsi işe yarıyor ve her birinin faydaları var. Hangisini seçeceğiniz, ne inşa ettiğinize bağlıdır.

Tebrikler! Bu noktada Android'de `RecyclerView`'da ustalaşma yolundasınız.





