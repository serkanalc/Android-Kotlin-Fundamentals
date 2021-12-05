# Data Binding Kullanın

- [findViewById() Öğesini Ortadan Kaldırmak için Data Binding Kullanın](#1)
- [Verileri görüntülemek için Data Binding kullanın](#2)



Bu kod laboratuvarında, [AboutMe uygulamasıyla](https://github.com/serkanalc/Android-Kotlin-Fundamentals-Projeler/tree/main/About%20me%20Interactive) başlarsınız ve uygulamayı veri bağlamayı kullanacak şekilde değiştirirsiniz. İşiniz bittiğinde uygulama tamamen aynı görünecek!

İşte AboutMe uygulamasının yaptığı:

- Kullanıcı uygulamayı açtığında, uygulama bir ad, takma ad girmek için bir alan, bir Bitti düğmesi, bir yıldız resmi ve kaydırılabilir metin gösterir.
- Kullanıcı bir takma ad girebilir ve Bitti düğmesine dokunabilir. Düzenlenebilir alan ve düğme, girilen takma adı gösteren bir metin görünümüyle değiştirilir.

![Ekran Resmi 2021-12-05 16 35 22](https://user-images.githubusercontent.com/70329389/144748729-84750368-4505-4436-8ddb-59d980997d62.png)

Önceki codelab'de oluşturduğunuz kodu kullanabilir veya AboutMeDataBinding-Starter kodunu GitHub'dan indirebilirsiniz.

## <a name="1"></a>findViewById() Öğesini Ortadan Kaldırmak için Data Binding Kullanın

Önceki kod laboratuvarlarında yazdığınız kod, görünümlere referanslar elde etmek için `findViewById()` işlevini kullanır.

Görünüm oluşturulduktan veya yeniden oluşturulduktan sonra bir görünüm aramak için `findViewById()` öğesini her kullandığınızda, Android sistemi onu bulmak için çalışma zamanında görünüm hiyerarşisini geçer. Uygulamanızın yalnızca bir avuç görünümü olduğunda, bu bir sorun değildir. Bununla birlikte, üretim uygulamalarının bir düzende düzinelerce görünümü olabilir ve en iyi tasarımda bile iç içe görünümler olacaktır.

Metin görünümü içeren bir kaydırma görünümü içeren doğrusal bir düzen düşünün. Büyük veya derin bir görünüm hiyerarşisi için, bir görünüm bulmak, uygulamayı kullanıcı için fark edilir şekilde yavaşlatacak kadar zaman alabilir. Görünümleri değişkenlerde önbelleğe almak yardımcı olabilir, ancak yine de her ad alanında her görünüm için bir değişken başlatmanız gerekir. Çok sayıda görüntüleme ve birden fazla etkinlikle, bu da eklenir.

Çözümlerden biri, her görünüme bir başvuru içeren bir nesne oluşturmaktır. `Binding` nesnesi olarak adlandırılan bu nesne, tüm uygulamanız tarafından kullanılabilir. Bu tekniğe *Data Binding*(veri bağlama) denir. Uygulamanız için bir binding nesnesi oluşturulduktan sonra, görünüm hiyerarşisini geçmek veya verileri aramak zorunda kalmadan binding nesnesi aracılığıyla görünümlere ve diğer verilere erişebilirsiniz.

![image](https://user-images.githubusercontent.com/70329389/144749064-39958e89-3f36-4748-a67e-4a6be117fac6.png)

Data Binging aşağıdaki avantajlara sahiptir:

- Kod, **findViewById()** kullanan koddan daha kısadır, okunması daha kolaydır ve bakımı daha kolaydır.
- Data ve Views açıkça ayrılmıştır. Data Binding'in bu yararı, bu kursun ilerleyen bölümlerinde giderek daha önemli hale gelmektedir.
- Android sistemi, her bir görünümü almak için görünüm hiyerarşisini yalnızca bir kez geçer ve bu, kullanıcı uygulamayla etkileşim kurduğunda çalışma zamanında değil, uygulama başlatılırken gerçekleşir.
- Görünümlere erişmek için [type safety(tür güvenliği)](https://en.wikipedia.org/wiki/Type_safety)  elde edersiniz. (type safety, derleyicinin derleme sırasında türleri doğruladığı anlamına gelir ve bir değişkene yanlış tür atamaya çalışırsanız hata verir.)

Bu dokümanda, data binding'i ayarlarsınız ve findViewById() öğesine yapılan çağrıları binding nesnesine yapılan çağrılarla değiştirmek için veri bağlamayı kullanırsınız.

### Aşama 1 : Data Binding'i Etkinleştirme

Data Binding'i kullanmak için, varsayılan olarak etkinleştirilmediğinden Gradle dosyanızda data binding'i etkinleştirmeniz gerekir. Bunun nedeni, veri bağlamanın derleme süresini artırması ve uygulama başlatma süresini etkileyebilmesidir.

1. Önceki bir AboutMe uygulamasına sahip değilseniz, GitHub'dan [AboutMeDataBinding-Starter](https://github.com/google-developer-training/android-kotlin-fundamentals-starter-apps/tree/master/AboutMeDataBinding-Starter) kodunu alın. Android Studio'da açın.

2. build.gradle (Modül: app) dosyasını açın.
3. Android bölümünün içinde, kapanış ayracından önce bir buildFeatures bölümü ekleyin ve dataBinding'i true olarak ayarlayın.

```
buildFeatures {
    dataBinding true
}
```

4. İstendiğinde, projeyi **Sync**(senkronize) edin. İstenmezse, **File > Sync Project with Gradle Files** seçin.
5. Uygulamayı çalıştırabilirsiniz, ancak herhangi bir değişiklik görmezsiniz.

### Aşama 2 : Layout Dosyasını Data Binding ile Kullanılabilecek şekilde Değiştirin

Data Binding'le çalışmak için XML düzeninizi bir `<layout>` etiketiyle sarmanız gerekir. Bu, kök sınıfın artık bir görünüm grubu olmaması, bunun yerine görünüm gruplarını ve görünümleri içeren bir layout olması içindir. Binding nesnesi daha sonra layout ve içindeki görünümler hakkında bilgi sahibi olabilir.

1. Activity_main.xml dosyasını açın.
2. Code sekmesine geçin.
3. `<LinearLayout>` çevresinde en dıştaki etiket olarak `<layout></layout>` ekleyin.

```
<layout>
   <LinearLayout ... >
   ...
   </LinearLayout>
</layout>
```

4. Kod girintisini düzeltmek için **Code > Reformat code**'i seçin.
Bir layout için ad alanı bildirimleri en dıştaki etikette olmalıdır.
5. `<LinearLayout>`'tan ad alanı bildirimlerini kesin ve bunları `<layout>` etiketine yapıştırın. Açılış `<layout>` etiketiniz aşağıda gösterildiği gibi görünmeli ve `<LinearLayout>` etiketi yalnızca görünüm özelliklerini içermelidir. 

```
<layout xmlns:android="http://schemas.android.com/apk/res/android"
   xmlns:app="http://schemas.android.com/apk/res-auto">
```

6. Bunu doğru yaptığınızı doğrulamak için uygulamanızı oluşturun ve çalıştırın.

### Adım 3: Ana Aktivitede Bir Binding Nesnesi Oluşturun

Görünümlere erişmek ve kullanabilmeniz için ana etkinliğe bağlama nesnesine bir başvuru ekleyin:


1. MainActivity.kt dosyasını açın.

2. onCreate()'den önce, en üst düzeyde, binding nesnesi için bir değişken oluşturun. Bu değişkene geleneksel olarak binding denir.

Binding türü, ActivityMainBinding sınıfı, özellikle bu ana aktivite için derleyici tarafından oluşturulur. Ad, düzen dosyasının adından türetilmiştir, yani Activity_main + Binding.

```
private lateinit var binding: ActivityMainBinding
```
3. Android Studio tarafından istenirse ActivityMainBinding'i içe aktarın. İstenmezse, bu eksik sınıfı içe aktarmak için ActivityMainBinding'e tıklayın ve Alt+Enter'a (Mac'te Option+Enter) basın.

Import ifadesi aşağıda gösterilene benzer görünmelidir.

```
import com.example.android.aboutme.databinding.ActivityMainBinding
```

Ardından, geçerli setContentView() işlevini aşağıdakileri yapan bir talimatla değiştirirsiniz:

- Binding nesnesini oluşturur.
- Activity_main düzenini MainActivity ile ilişkilendirmek için DataBindingUtil sınıfından setContentView() işlevini kullanır. Bu setContentView() işlevi, görünümler için bazı veri bağlama kurulumlarıyla da ilgilenir.

4. onCreate() içinde, setContentView() çağrısını aşağıdaki kod satırıyla değiştirin.

```
binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
```

5.DataBindingUtil'i import edin

```
import androidx.databinding.DataBindingUtil
```

### Adım 4: findViewById() Öğesine Yapılan Tüm Çağrıları Değiştirmek İçin Binding Nesnesini Kullanın

Artık findViewById() öğesine yapılan tüm çağrıları, Binding nesnesindeki görünümlere yapılan başvurularla değiştirebilirsiniz. Binding nesnesi oluşturulduğunda, derleyici layout'daki görünümlerin kimliklerinden bağlama nesnesindeki görünümlerin adlarını oluşturur ve bunları camel case(Deve Kemeri: boşluk bırakmak yerine boşluktan sonra gelen kelimenin ilk harfini büyütmek) dönüştürür. Örneğin, bağlama nesnesinde done_button doneButton olur, nick_edit nickEdit olur ve nick_text nickText olur.

1. onCreate() içinde done_button öğesini bulmak için findViewById() öğesini kullanan kodu, bağlama nesnesindeki düğmeye başvuran kodla değiştirin.

Bu kodu değiştirin: `findViewById<Button>(R.id.done_button)` şununla: `binding.doneButton`

onCreate() içindeki tıklama dinleyicisini ayarlamak için bitmiş kodunuz şöyle görünmelidir.


```
binding.doneButton.setOnClickListener {
   addNickname(it)
}
``` 

2. addNickname() işlevinde findViewById() öğesine yapılan tüm çağrılar için aynısını yapın. `findViewById<View>(R.id.id_view)` öğesinin tüm oluşumlarını binding.idView ile değiştirin. Bunu aşağıdaki şekilde yapın:

- findViewById() çağrılarıyla birlikte editText ve NickTextView değişkenlerinin tanımlarını silin. Bu size hatalar verecektir.

- (Silinmiş) değişkenler yerine bağlama nesnesinden NickText, NickEdit ve doneButton görünümlerini alarak hataları düzeltin.

- view.visibility ile binding.doneButton.visibility değiştirin. Geçirilen görünüm yerine bağlama.doneButton kullanılması, kodu daha tutarlı hale getirir.

Sonuç aşağıdaki koddur:

```
binding.nicknameText.text = binding.nicknameEdit.text
binding.nicknameEdit.visibility = View.GONE
binding.doneButton.visibility = View.GONE
binding.nicknameText.visibility = View.VISIBLE
```  

- İşlevsellikte herhangi bir değişiklik yoktur. İsteğe bağlı olarak, artık view parametresini ortadan kaldırabilir ve bu fonksiyon içinde binbining.doneButton'u kullanmak için tüm view kullanımlarını güncelleyebilirsiniz.

3. NickText bir Dize gerektirir ve NickEdit.text bir Editable'dir. Data binding'i kullanırken, Editable'ı açıkça bir Dize'ye dönüştürmek gerekir.

```
binding.nicknameText.text = binding.nicknameEdit.text.toString()
```  

4. Grileştirilmiş içe aktarmaları silebilirsiniz.

Apply{} kullanarak işlevi kotlinize edin.

```
binding.apply {
   nicknameText.text = nicknameEdit.text.toString()
   nicknameEdit.visibility = View.GONE
   doneButton.visibility = View.GONE
   nicknameText.visibility = View.VISIBLE
}
```
Uygulamanızı oluşturun ve çalıştırın... ve eskisi gibi görünmeli ve çalışmalıdır.

> İpucu: Değişiklik yaptıktan sonra derleyici hataları görürseniz, Build > Clean Project'yi ve ardından Build > Rebuild Project'u seçin. Bunu yapmak genellikle oluşturulan dosyaları günceller. Aksi takdirde, daha kapsamlı bir temizlik yapmak için File > Invalidate Caches/Restart'ı seçin.

> İpucu: Uygulamadaki tüm kaynaklara referansları tutan Resources nesnesini daha önce öğrenmiştiniz. Görünümlere atıfta bulunurken Binding nesnesini benzer şekilde düşünebilirsiniz; ancak, Binding nesnesi çok daha karmaşıktır.


## <a name="2"></a>Verileri Görüntülemek İçin Data Binding'i kullanın





