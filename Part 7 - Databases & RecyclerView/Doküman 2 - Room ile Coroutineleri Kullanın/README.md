# <a name="1"></a>Room ile Coroutineleri Kullanın

- [Başlangıç kodunu inceleyin](#a)
- [ViewModel ekleyin](#b)
- [Coroutineler](#c)
- [Data toplayın & görüntüleyin](#d)

Bu aşamada, TrackMySleepQuality uygulamasının view model'ını, coroutinelerini ve veri görüntüleme bölümlerini oluşturacaksınız.

Uygulamanın, aşağıdaki şekilde gösterildiği gibi fragmentlarla temsil edilen iki ekranı vardır.

![image](https://lh4.googleusercontent.com/WTsRlkiNXXQ7fe3myuNV8SigzmETej7zu35m_x7VEPJNGUwKLNrSFg1k7RPur1Y6tvMcInSIKbeHysH-AvR2MYoJCkOsHWBpgyQ6ut4Bvmxa5tpX9NVMIv7lc-7gDLTw4dUSV7wFkQ)

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

![image](https://lh5.googleusercontent.com/Q7-Cq9-Y4h13EbiYcZR---sZ74dgkqejB699RY7cCIxGresnPfPKHqjX8HsTVB24r-c3gqRAgWUrERqjraXtQPpdCZ-ZeJhtUHw9s2-j39pW9Cerk0Qethe_Pc3oOy0hVl-q9i47Xg)


## <a name="a"></a>Aşama 1 : Başlangıç kodunu inceleyin

## <a name="b"></a>Aşama 2 : ViewModel ekleyin

## <a name="c"></a>Aşama 3 : Coroutineler

## <a name="d"></a>Aşama 4 : Data toplayın & görüntüleyin

