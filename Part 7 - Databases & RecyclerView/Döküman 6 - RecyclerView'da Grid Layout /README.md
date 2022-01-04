# <a name="1"></a>RecyclerView'da Grid Layout

- [Layoutlar & LayoutManagerlar](#a)
- [GridLayout uygulayın](#b)

Sleep-tracker uygulamasının, aşağıdaki şekilde gösterildiği gibi fragmentlarla temsil edilen iki ekranı vardır.

![app image](https://developer.android.com/codelabs/kotlin-android-training-grid-layout/img/76d78f63f88c3c86.png)
![app image](https://developer.android.com/codelabs/kotlin-android-training-grid-layout/img/43590f0a4c00e138.png)

Solda gösterilen ilk ekranda, izlemeyi başlatmak ve durdurmak için butonlar vardır. Ekran, kullanıcının tüm uyku verilerini gösterir. **Clear** butonu, uygulamanın kullanıcı için topladığı tüm verileri kalıcı olarak siler. Sağda gösterilen ikinci ekran, uyku kalitesi derecelendirmesini seçmek içindir.

Bu uygulama, uyku verilerini sürdürmek (persist) için bir UI controller, `ViewModel` ve `LiveData` ve bir `Room` veritabanı ile basitleştirilmiş bir mimari kullanır.

![architecture image](https://developer.android.com/codelabs/kotlin-android-training-grid-layout/img/49f975f1e5fe689.png)

Uyku verileri bir `RecyclerView`'da görüntülenir. Bu aşamada, uygulamayı `GridLayout` kullanacak şekilde değiştirirsiniz. Son ekran aşağıdaki ekran görüntüsü gibi görünecektir.

![grid layout](https://developer.android.com/codelabs/kotlin-android-training-grid-layout/img/b0abde98c5f99bf6.png)

## <a name="a"></a>Aşama 1 : Layoutlar & LayoutManagerlar

Önceki bir dökümanda, `RecyclerView`'ı `fragment_sleep_tracker.xml` dosyasına eklediğinizde, herhangi bir özelleştirme olmadan bir `LinearLayoutManager` eklemiştiniz. Bu kod, verileri dikey bir liste olarak görüntüler.

```

app:layoutManager="androidx.recyclerview.widget.LinearLayoutManager"

```

`LinearLayoutManager`, `RecyclerView` için en yaygın ve basit layout manager'dır ve child vieweların hem yatay hem de dikey yerleşimini destekler. Örneğin, kullanıcının yatay olarak kaydırdığı bir görüntülerden oluşan bir carousel oluşturmak için `LinearLayoutManager`'ı kullanabilirsiniz.

### GridLayout

Diğer bir yaygın kullanım durumu, kullanıcıya `GridLayout` kullanarak yapabileceğiniz çok sayıda veri gösterme ihtiyacıdır. `RecyclerView` için `GridLayoutManager`, verileri aşağıda gösterildiği gibi kaydırılabilir bir ızgara olarak düzenler.

![gridlayoutmanager](https://developer.android.com/codelabs/kotlin-android-training-grid-layout/img/fcf0fc4b78f8650.png)

Tasarım açısından bakıldığında, `GridLayout`, bir fotoğraf uygulamasındaki listeler gibi simgeler veya resimler olarak temsil edilebilen listeler için en iyisidir. Sleep-tracker uygulamasında, her uyku gecesini büyük simgelerden oluşan bir ızgara olarak gösterebilirsiniz. Bu tasarım, kullanıcıya uyku kalitelerine bir bakışta genel bir bakış sağlar.

### GridLayout öğeleri nasıl düzenler?

`GridLayout`, öğeleri bir satır ve sütun ızgarasında düzenler. Dikey kaydırma varsayıldığında, varsayılan olarak, bir satırdaki her öğe bir "span" (aralık) alır. Bazen bir öğe birden çok span'i kaplayabilir. Aşağıdaki durumda, bir span, bir sütunun genişliğine eşittir.

Aşağıda gösterilen ilk iki örnekte, her satır üç span'den oluşur. Varsayılan olarak, `GridLayoutManager`, belirttiğiniz span sayısına kadar her öğeyi tek bir span alanına yerleştirir. Span sayısına ulaştığında, bir sonraki satıra geçer.

Varsayılan olarak, her öğe bir span alanı kaplar, ancak kaç span alanı kaplayacağını belirterek bir öğeyi daha geniş yapabilirsiniz. Örneğin, en sağdaki ekrandaki en üstteki öğe (aşağıda gösterilmiştir) üç span kaplar.

![span 1](https://developer.android.com/codelabs/kotlin-android-training-grid-layout/img/b2c54acbb0d952f.png)
![span 2](https://developer.android.com/codelabs/kotlin-android-training-grid-layout/img/f51951e6ce5d8737.png)
![span 3](https://developer.android.com/codelabs/kotlin-android-training-grid-layout/img/49d9a635f273fa04.png)

>İpucu: Span, "satır" veya "sütun" anlamına gelebilir. [`GridLayoutManager`](https://developer.android.com/reference/android/support/v7/widget/GridLayoutManager) ile, bir ızgaranın kaç sütun veya satıra sahip olduğunu ve ayrıca bir öğenin yatay veya dikey olarak ne kadar ızgara alanı kapladığını belirtmek için `spanCount`'u kullanırsınız.

>Bir GridLayoutManager oluşturduğunuzda, oryantasyonu span sayısından ayrı olarak belirtirsiniz ve "span", "yönden bağımsızdır" ("direction-agnostic"). (Varsayılan) bir dikey konfigürasyonda, "span" ve "column" eşdeğerdir.

## <a name="b"></a>Aşama 2 : GridLayout uygulayın

Bu aşamada, son alıştırmada bitirdiğiniz `RecyclerView`'ı alır ve bir `GridLayoutManager` kullanarak verileri görüntülemek için güncellersiniz. Önceki codelab'den sleetracker uygulamasını kullanmaya devam edebilir veya RecyclerViewGridLayout-Starter uygulamasını GitHub'dan indirebilirsiniz. <!--github link-->

### Adım 1: LayoutManager'ı değiştirin

1. Gerekirse, bu aşama için RecyclerViewGridLayout-Starter uygulamasını GitHub'dan indirin ve projeyi Android Studio'da açın. <!-- linkle-->
2. `fragment_sleep_tracker.xml` layout dosyasını açın.
3. Layout manager'ı `sleep_list` `RecyclerView` tanımından kaldırın.

Silinecek kod:

```

app:layoutManager="androidx.recyclerview.widget.LinearLayoutManager

```

4. `SleepTrackerFragment.kt`'yi açın.
5. `OnCreateView()` içinde, `return` ifadesinden hemen önce, 3 span'li yeni bir dikey, yukarıdan aşağıya bir `GridLayoutManager` oluşturun.

GridLayoutManager constructor'ı en fazla dört argüman alır: activity olan bir context, span sayısı (varsayılan, dikey düzende sütunlar), bir oryantasyon (varsayılan dikeydir) ve bunun bir ters düzen olup olmadığı (varsayılan false'tur).

```

val manager = GridLayoutManager(activity, 3, GridLayoutManager.VERTICAL, false)

```

6. Bu satırın altında, `RecyclerView`'a bu `GridLayoutManager`'ı kullanmasını söyleyin. `RecyclerView`, binding nesnesindedir ve `sleepList` olarak adlandırılır. (Bkz. `fragment_sleep_tracker.xml`)

```

binding.sleepList.layoutManager = manager

```

### Adım 2: Layout'u değiştirin

`list_item_sleep_night.xml` içindeki mevcut layout, verileri gece başına tam bir satır kullanarak görüntüler. Bu adımda, ızgara için daha kompakt bir kare öğe düzeni tanımlarsınız.

>İpucu: Mevcut layout'unuzu kaybetmek istemiyorsanız, önce dosyanın bir kopyasını oluşturun ve onu l`ist_item_sleep_night_linear.xml` olarak adlandırın veya kaldırmak yerine kodu yorumlayın.

1. `list_item_sleep_night.xml`'i açın. XML'i incelemek için **Code** görünümünü kullanın.
2. `sleep_length` `TextView`'unu silin, çünkü yeni tasarımınız buna ihtiyaç duymayacak.
3. `quality_string` `TextView` öğesini `ImageView` altında görüntülenecek şekilde taşıyın. Bunu yapmak için birkaç şeyi güncellemeniz gerekir. `quality_string` `TextView` ve `quality_image` `ImageView` için son düzen:

```

<ImageView
   android:id="@+id/quality_image"
   android:layout_width="@dimen/icon_size"
   android:layout_height="60dp"
   android:layout_marginTop="8dp"
   android:layout_marginBottom="8dp"
   app:layout_constraintBottom_toBottomOf="parent"
   app:layout_constraintEnd_toEndOf="parent"
   app:layout_constraintStart_toStartOf="parent"
   app:layout_constraintTop_toTopOf="parent"
   tools:srcCompat="@drawable/ic_sleep_5"
   app:sleepImage="@{sleep}"/>

<TextView
   android:id="@+id/quality_string"
   android:layout_width="0dp"
   android:layout_height="20dp"
   android:layout_marginEnd="16dp"
   android:textAlignment="center"
   app:layout_constraintBottom_toBottomOf="parent"
   app:layout_constraintEnd_toEndOf="parent"
   app:layout_constraintHorizontal_bias="0.0"
   app:layout_constraintStart_toStartOf="parent"
   app:layout_constraintTop_toBottomOf="@+id/quality_image"
   app:sleepQualityString="@{sleep}"
   tools:text="Excellent!"/>
   
```

4. **Design** görünümünde, `quality_string` `TextView`'un, `ImageView` altına yerleştirildiğini doğrulayın.

![design view](https://developer.android.com/codelabs/kotlin-android-training-grid-layout/img/969906bdadeaa2dd.png)

Data binding kullandığınız için, `Adapter`'da _hiçbir şeyi_ değiştirmeniz gerekmez. Kod sadece çalışmalı ve listeniz bir ızgara olarak görüntülenmelidir.

5. Uygulamayı çalıştırın ve uyku verilerinin bir ızgarada nasıl görüntülendiğini gözlemleyin.

`ConstraintLayout`'un hala tüm genişliği aldığını unutmayın. `GridLayoutManager`, görünümünüze span'ine göre sabit bir genişlik verir. `GridLayoutManager`, ızgarayı düzenlerken, boşluk eklerken veya öğeleri kırparken tüm kısıtlamaları karşılamak için elinden gelenin en iyisini yapar.

6. `SleepTrackerFragment.kt`'de, `GridLayoutManager`'ı oluşturan kodda, `GridLayoutManger` için span sayısını 1 olarak değiştirin. Uygulamayı çalıştırın ve bir liste elde edin.

```

val manager = GridLayoutManager(activity, 1)

```

7. `GridLayoutManager` için span sayısını 10 olarak değiştirin ve uygulamayı çalıştırın. `GridLayoutManager`'ın 10 öğreyi bir satıra sığdırdığına, ancak öğelerin artık kırpıldığına dikkat edin.
8. Span sayısını 5 ve yönü `GridLayoutManager.HORIZONTAL` olarak değiştirin. Uygulamayı çalıştırın ve yatay olarak nasıl kaydırabileceğinizi görün. Oldukça havalı! Görünümünü ve hissini iyileştirmek için hala farklı bir düzene ihtiyacı var. Bunu gelecekteki bir aşama için bırakabiliriz.

```

val manager = GridLayoutManager(activity, 5, GridLayoutManager.HORIZONTAL, false)

```

9. Span sayısını 3'e ve oryantasyonu dikey olarak ayarlamayı unutmayın!

