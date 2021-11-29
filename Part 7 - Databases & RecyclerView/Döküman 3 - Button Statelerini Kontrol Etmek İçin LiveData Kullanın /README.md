# <a name="1"></a>Button Statelerini (Durumlarını) Kontrol Etmek için LiveData Kullanın

- [Navigation ekleyin](#a)
- [Uyku kalitesini kaydedin](#b)
- [Button görünürlüğünü kontrol edin & bir Snackbar ekleyin](#c)

Bu aşamada, TrackMySleepQuality uygulamasının uyku kalitesinde kaydını ve UI'ın son halini oluşturursunuz.

Uygulamanın, aşağıdaki şekilde gösterildiği gibi fragmentlarla temsil edilen iki ekranı vardır.

![app image](https://developer.android.com/codelabs/kotlin-android-training-quality-and-states/img/e28eb795b6812ee4.png)

Solda gösterilen ilk ekranda, izlemeyi başlatmak ve durdurmak için düğmeler vardır. Ekran, kullanıcının tüm uyku verilerini gösterir. **Clear** düğmesi, uygulamanın kullanıcı için topladığı tüm verileri kalıcı olarak siler.

Sağda gösterilen ikinci ekran, uyku kalitesi derecelendirmesini seçmek içindir. Uygulamada, derecelendirme sayısal olarak temsil edilir. Geliştirme amacıyla, uygulama hem yüz simgelerini hem de bunların sayısal eşdeğerlerini gösterir.

Kullanıcı akışı aşağıdaki gibidir:

- Kullanıcı uygulamayı açar ve uyku izleme ekranı ile karşılaşır.
- Kullanıcı **Start** düğmesine dokunur. Bu, başlangıç saatini kaydeder ve görüntüler. **Start** düğmesi devre dışı bırakılır ve **Stop** düğmesi etkinleştirilir.
- Kullanıcı **Stop** düğmesine dokunur. Bu, bitiş saatini kaydeder ve uyku kalitesi ekranını açar.
- Kullanıcı bir uyku kalitesi simgesi seçer. Ekran kapanır ve izleme ekranı uyku bitiş süresini ve uyku kalitesini görüntüler. **Stop** düğmesi devre dışı bırakılır ve **Start** düğmesi etkinleştirilir. Uygulama başka bir gece için hazırdır.
- Veritabanında veri olduğunda **Clear** düğmesi etkinleştirilir. Kullanıcı **Clear** düğmesine dokunduğunda, tüm verileri soru sorulmadan silinir; "Emin misiniz?" diye bir mesaj olmayacaktır.

Bu uygulama, tam mimari bağlamında aşağıda gösterildiği gibi basitleştirilmiş bir mimari kullanır. Uygulama yalnızca aşağıdaki bileşenleri kullanır:

- UI Controller (UI denetleyicisi)
- View model & `LiveData`
- Bir Room veritabanı

![app architecture](https://developer.android.com/codelabs/kotlin-android-training-quality-and-states/img/2268f8ae35a8c715.png)

## <a name="a"></a>Aşama 1 : Navigation Ekleyin

Bu codelab, fragmentları ve navigation dosyasını kullanarak navigation'ı nasıl uygulayacağınızı bildiğinizi varsayar. İşinizi azaltmak için bu kodun büyük bir kısmı sağlanmıştır.

### Adım 1: Kodu inceleyin

1. Başlamak için, bir önceki aşamanın sonundaki kendi kodunuzla devam edin veya başlangıç kodunu indirin. <!-- linkle -->
2. Başlangıç kodunuzda `SleepQualityFragment`'ı inceleyin. Bu class, layout'u inflate eder, uygulamayı alır ve `binding.root`'u döndürür.
3. Tasarım editöründe **navigation.xml**'i açın. `SleepTrackerFragment`'ten `SleepQualityFragment`'e ve tekrar `SleepQualityFragment`'ten `SleepTrackerFragment`'e bir navigation path'i olduğunu göreceksiniz.

![navigation xml](https://developer.android.com/codelabs/kotlin-android-training-quality-and-states/img/903884ca86daf6e.png)

4. **navigation.xml**'in kodunu inceleyin. Özellikle `sleepNightKey` adı verilen `<argument>`'a bakın.

Kullanıcı `SleepTrackerFragment`'tan `SleepQualityFragment`'a geçtiğinde, uygulama güncellenmesi gereken gece için `sleepNightKey`'i `SleepQualityFragment`'a iletir.

### Adım 2: Uyku kalitesi takibi için navigation ekleyin


## <a name="b"></a>Aşama 2 : Uyku kalitesini kaydedin

## <a name="c"></a>Aşama 3 : Button görünürlüğünü kontrol edin & bir Snackbar ekleyin


