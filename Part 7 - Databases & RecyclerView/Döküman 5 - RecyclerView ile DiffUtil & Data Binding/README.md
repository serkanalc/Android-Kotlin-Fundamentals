# <a name="1"></a>RecyclerView ile DiffUtil & Data Binding

- [Şimdiye kadar neler yaptığınızı gözden geçirin & başlayın](#a)
- [DiffUtil ile listenin içeriğini yenileyin](#b)
- [Listenizi yönetmek için ListAdapter kullanın](#c)
- [RecyclerView ile DataBinding kullanın](#d)
- [Binding adapterlar yaratın](#e)

Başlangıç yygulamasının, aşağıdaki şekilde gösterildiği gibi fragmentlarla temsil edilen iki ekranı vardır.

![app image](https://developer.android.com/codelabs/kotlin-android-training-diffutil-databinding/img/76ec2be31e018176.png)

Solda gösterilen ilk ekranda, izlemeyi başlatmak ve durdurmak için butonlar vardır. Ekran, kullanıcının tüm uyku verilerini gösterir. **Clear** butonu, uygulamanın kullanıcı için topladığı tüm verileri kalıcı olarak siler. Sağda gösterilen ikinci ekran, uyku kalitesi derecelendirmesini seçmek içindir.

Bu uygulama, uyku verilerini sürdürmek (persist) için bir UI controller, `ViewModel` ve `LiveData` ve bir `Room` veritabanı kullanacak şekilde tasarlanmıştır.

![architecture image](https://developer.android.com/codelabs/kotlin-android-training-diffutil-databinding/img/49f975f1e5fe689.png)

Uyku verileri bir `RecyclerView`'da görüntülenir. Bu aşamada, `RecyclerView` için `DiffUtil` ve data binding bölümünü oluşturacaksınız. Bu aşamadan sonra uygulamanız tamamen aynı görünecek, ancak daha verimli ve ölçeklenmesi ve bakımı daha kolay olacaktır.

## <a name="a"></a>Aşama 1 : Şimdiye kadar neler yaptığınızı gözden geçirin & başlayın
## <a name="b"></a>Aşama 2 : DiffUtil ile listenin içeriğini yenileyin
## <a name="c"></a>Aşama 3 : Listenizi yönetmek için ListAdapter kullanın
## <a name="d"></a>Aşama 4 : RecyclerView ile DataBinding kullanın
## <a name="e"></a>Aşama 5 : Binding adapterlar yaratın
