# <a name="1"></a>RecyclerView Temelleri

- [RecyclerView](#a)
- [RecyclerView ve bir Adapter uygulayın](#b)
- [Tüm uyku datası için bir ViewHolder oluşturun](#c)
- [Kodunuzu geliştirin](#d)

>Not: Önceki codelab üzerinde çalıştıysanız, bu codelab için başlangıç uygulamasını çok tanıdık bulacaksınız. Başlangıç uygulaması, önceki codelab'de oluşturduğunuz uygulama gibi, uyku-gece verilerini kayan bir metin görünümünde (scrolling text view) görüntüler. Ancak, bu codelab için başlangıç uygulaması, bir RecyclerView'un uygulanmasıyla doğrudan ilgili olmayan birçok işten tasarruf etmenizi sağlayacak ek varlıklar ve yardımcı programlar içerir. Bu ders için başlangıç uygulamasını indirip kullandığınızdan emin olun.

Bu aşamada, uyku kalitesini izleyen bir uygulamanın `RecyclerView` bölümünü oluşturursunuz. Uygulama, zaman içinde uyku verilerini depolamak için bir `Room` veritabanı kullanır.

Başlangıç yygulamasının, aşağıdaki şekilde gösterildiği gibi fragmentlarla temsil edilen iki ekranı vardır.

![image](https://developer.android.com/codelabs/kotlin-android-training-recyclerview-fundamentals/img/e28eb795b6812ee4.png)

Solda gösterilen ilk ekranda, izlemeyi başlatmak ve durdurmak için butonlar vardır. Ekran, kullanıcının tüm uyku verilerini gösterir. **Clear** butonu, uygulamanın kullanıcı için topladığı tüm verileri kalıcı olarak siler. Sağda gösterilen ikinci ekran, uyku kalitesi derecelendirmesini seçmek içindir.

Bu uygulama, bir UI controller, `ViewModel` ve `LiveData` ile basitleştirilmiş bir mimari kullanır. Uygulama ayrıca uyku verilerini kalıcı hale (persistent) getirmek için bir `Room` veritabanı kullanır.

![image](https://developer.android.com/codelabs/kotlin-android-training-recyclerview-fundamentals/img/49f975f1e5fe689.png)

İlk ekranda görüntülenen uyku geceleri listesi işlevseldir, ancak güzel değildir. Uygulama, metin görünümü için metin stringleri ve kalite için sayılar oluşturmak üzere karmaşık bir biçimlendirici kullanır. Ayrıca, bu tasarım ölçeklenebilirliğimizi azaltacak şekilde biraz karmaşıktır. Bu codelab'deki tüm bu sorunları giderdikten sonra, son uygulama orijinal uygulamayla aynı işlevselliğe sahiptir ancak geliştirilmiş ana ekranın okunması daha kolaydır:

![image](https://developer.android.com/codelabs/kotlin-android-training-recyclerview-fundamentals/img/76d78f63f88c3c86.png)

## <a name="a"></a>Aşama 1 : RecyclerView

Bir veri listesi veya grid'i görüntülemek, Android'deki en yaygın UI görevlerinden biridir. Listeler basitten, çok karmaşığa değişir. Bir text view listesi, alışveriş listesi gibi basit verileri gösterebilir. Açıklamalı tatil lokasyonları listesi gibi karmaşık bir liste, kullanıcıya başlıklarla birlikte kayan bir grid içinde birçok ayrıntı gösterebilir.

Tüm bu kullanım durumlarını desteklemek için Android, `RecyclerView` widget'ını sağlar.

![image](https://developer.android.com/codelabs/kotlin-android-training-recyclerview-fundamentals/img/643a2240444361ad.png)

`RecyclerView`'un en büyük yararı, büyük listeler için çok verimli olmasıdır:

- Varsayılan olarak, `RecyclerView` yalnızca o anda ekranda görünen öğeleri işlemek veya çizmek için çalışır. Örneğin, listenizde bin öğe varsa ancak yalnızca 10 öğe görünür durumdaysa, `RecyclerView` ekranda yalnızca 10 öğe çizmeye yetecek kadar iş yapar. Kullanıcı sayfayı kaydırdığında, `RecyclerView` ekranda hangi yeni öğelerin olması gerektiğini bulur ve bu öğeleri görüntülemek için yeterli iş yapar.
- Bir öğe ekrandan kaydığında, öğenin görünümleri geri dönüştürülür. Bu, öğenin ekranda kaydırılırken yeni içerikle doldurulduğu anlamına gelir. Bu `RecyclerView` davranışı, çok fazla işlem süresi kazandırır ve listelerin sorunsuz bir şekilde kaydırılmasına yardımcı olur.
- Bir öğe değiştiğinde, tüm listeyi yeniden çizmek yerine `RecyclerView` o öğeyi güncelleyebilir. Bu, karmaşık öğelerin uzun listelerini görüntülerken büyük bir verimlilik kazancıdır!

Aşağıda gösterilen sırada, bir görünümün `ABC` verileriyle doldurulduğunu görebilirsiniz. Bu görünüm ekranı kaydırdıktan sonra, `RecyclerView` yeni veriler için, `XYZ`, görünümü yeniden kullanır.

![image](https://developer.android.com/codelabs/kotlin-android-training-recyclerview-fundamentals/img/dcf4599789b9c2a1.png)

## <a name="b"></a>Aşama 2 : RecyclerView ve bir Adapter uygulayın
## <a name="c"></a>Aşama 3 : Tüm uyku datası için bir ViewHolder oluşturun
## <a name="d"></a>Aşama 4 : Kodunuzu geliştirin
