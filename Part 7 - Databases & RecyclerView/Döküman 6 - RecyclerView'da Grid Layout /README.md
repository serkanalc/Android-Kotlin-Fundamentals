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

## <a name="b"></a>Aşama 2 : GridLayout uygulayın
