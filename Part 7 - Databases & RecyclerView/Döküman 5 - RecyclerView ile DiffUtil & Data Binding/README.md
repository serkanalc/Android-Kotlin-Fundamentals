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
## <a name="d"></a>Aşama 4 : RecyclerView ile DataBinding kullanın
## <a name="e"></a>Aşama 5 : Binding adapterlar yaratın
