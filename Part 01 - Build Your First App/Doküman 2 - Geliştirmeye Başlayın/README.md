# <a name="başlangıç"></a>Geliştirmeye Başlayalım

- ["HelloWorld" projesini oluşturun](#1)
- [Android Studio'yu Keşfedin](#2)
- [Gradle Scripts klasörünü keşfedin](#3)
- [Uygulamanızı sanal bir cihazda çalıştırın (emülatör)](#4)
- [Uygulamanızı fiziksel bir cihazda çalıştırın](#5)
- [Çalışma Örnekleri](#6)

IDE(integrated development environment) kurulumu tamamlandığına göre artık başlayabiliriz.

## <a name="1"></a>"HelloWorld" projesini oluşturun

Bu bölümde, Android Studio'nun uygun şekilde yüklendiğini doğrulamak için yeni bir uygulama projesi oluşturursunuz.

1. Henüz açılmadıysa Android Studio'yu açın.
2. Android Studio'ya Hoş Geldiniz, "start new Android Studio project" öğesini tıklayın .

![3c9131b738575413](https://user-images.githubusercontent.com/70329389/139844998-5aa1805f-4ecd-4dd9-b38f-2cdb66a80a87.png)

3. Projeniz seçin sayfası görünmesi gerekiyor. Aşağıda gösterildiği gibi **Empty Activity** seçeneğini seçin ve **Next** butonuna tıklayın.

![25b3537dfe22b058](https://user-images.githubusercontent.com/70329389/139845733-46597e5a-e2dd-4a54-a225-cb7f1c34ca7f.png)

Her uygulamanın giriş noktası olarak en az bir etkinliği olmalıdır. bunu diğer programlardaki **"main()"** fonksyonu olarak düşünebilirsiniz. Bir etkinlik tipik olarak, User Interface (UI) öğelerinin ekranda nasıl görüneceğini tanımlayan, kendisiyle ilişkilendirilmiş bir düzene sahiptir. Android Studio, başlamanıza yardımcı olacak birkaç **"Activity"** şablon sunar.

4. **"Configure your project"** sayfasına gelmiş olmanız gerekiyor. "Name" kısmına "HelloWord" yazın.

![f95a9c8d496de18](https://user-images.githubusercontent.com/70329389/139894687-0e6fb8b6-c3aa-494c-86a6-8e78444b8431.png)

5. Şirket alanı için varsayılan **"android.example.com'u""** kabul edin veya benzersiz bir şirket alanı oluşturun. Bu değer artı uygulamanın adı, uygulamanızın paket adıdır. Uygulamanızı yayınlamayı **planlamıyorsanız**, varsayılanı kabul edin. Uygulamanızın paket adını daha sonra değiştirebilirsiniz, ancak bu ekstra bir iştir.
6. Varsayılan **Save location'ın** uygulamanızı depolamak istediğiniz yer olduğunu doğrulayın . Değilse, size uygun olduğunu düşündüğünüz konumla değiştirin.
7. **Language** alanının Kotlin olduğundan emin olun.
8. Minimum API seviyesinin **API 19: Android 4.4 (KitKat)** olduğundan emin olun . Bu codelab yazıldığı sırada Android Studio, bu API düzeyiyle uygulamanın cihazların yaklaşık **%95,3**'ünde çalışacağını belirtti.
(Daha sonraki bir kod laboratuvarında minimum API seviyeleri hakkında daha fazla bilgi edineceksiniz. Hemen şimdi daha fazla bilgi edinmek için, API seviyeleri hakkında bilgi içeren bir pencere açan Seçmeme **Help me choose'a** tıklayın.)

9. **Use AndroidX artifacts** onay kutusunu seçin.

10. Diğer tüm onay kutularını boş bırakın ve **Finish** butonuna tıklayın. Projeniz, seçtiğiniz hedef SDK için daha fazla bileşen gerektiriyorsa, Android Studio bunları otomatik olarak yükler ve bu biraz zaman alabilir. İstemleri izleyin ve varsayılan seçenekleri kabul edin.

Android Studio şimdi projenizi oluşturuyor ve bu biraz zaman alabilir. Herhangi bir hata almamalısınız. Herhangi bir uyarı alırsanız, onları dikkate almayın.

## <a name="2"></a>Android Studio'yu Keşfedin

Bu görevde, Android Studio'da "HelloWorld" projesini keşfedecek ve Android Studio ile geliştirmenin temellerini öğreneceksiniz.

### Adım 1 : Proje bölmesini keşfedin

Eğer Proje sekmesi zaten seçili değilse, onu seçin. Proje sekmesi Android Studio penceresinin sol tarafında dikey sekme sütununda yer alır. Proje bölmesi açılır.

![image](https://user-images.githubusercontent.com/70329389/139906364-713de209-3f66-4efd-b7ec-e0438224f5d4.png)

Projeyi standart bir Android proje hiyerarşisi olarak görüntülemek için Proje bölmesinin üst kısmındaki açılır menüden Android'i seçin. ( Android varsayılandır.) Dosyaları dosya sistemi hiyerarşisinde nasıl göründüklerini görüntülemek de dahil olmak üzere proje dosyalarını birçok farklı şekilde görüntüleyebilirsiniz. Ancak, Android görünümünü kullanarak proje üzerinde çalışmak daha kolaydır.

![image](https://user-images.githubusercontent.com/70329389/139906481-cebee99a-6cad-4735-baf4-f1af0aa8e723.png)

### Adım 2 : Uygulama klasörünü keşfedin

Uygulamanız için tüm kod ve kaynaklar **app** klasörde bulunur.

Proje > Android bölmesinden uygulama klasörünü genişletin. Uygulama klasörünün içinde dört alt klasör bulunur: **manifests**, **java**, **generatedJava** ve **res**.

**java** klasörüne tıklayarak açın ve **MainActivity** Kotlin dosyasını görmek için **com.example.android.HelloWorld** klasörünü genişletin.

![image](https://user-images.githubusercontent.com/70329389/139907844-54f6babe-e8f3-487b-9f39-4d364dfdb695.png)

Java klasörü, bir Android uygulaması için tüm ana Kotlin kodunu içerir. Kotlin kodunuzun java klasöründe görünmesinin tarihsel nedenleri vardır. Bu kural, Kotlin'in aynı proje ve uygulamada bile Java programlama dilinde yazılmış kodla sorunsuz bir şekilde birlikte çalışmasına olanak tanır.

Uygulamanızın sınıf dosyaları, yukarıdaki şekilde gösterildiği gibi üç alt klasörde bulunur. **com.example.hello.helloworld** (veya belirttiğiniz alan adı) klasörü, bir uygulama paketinin tüm dosyalarını içerir. Özellikle **MainActivity** sınıfı, uygulamanız için ana giriş noktasıdır. Sonraki codelab'de **MainActivity** hakkında daha fazla bilgi edineceksiniz. **Java** klasöründeki diğer iki klasör, unit testleri gibi testlerle ilgili kodlar için kullanılır.

> Dikkat: Dosya sisteminde Kotlin dosyalarınız bir .kt uzantısına ve bir K simgesine sahiptir. Proje görünümünde, Android Studio, uzantı olmadan size sınıf adını (MainActivity) gösterir.

Oluşturulan Java klasörünü not edin. Bu klasör, uygulamayı oluştururken Android Studio'nun oluşturduğu dosyaları içerir. Uygulamayı yeniden oluşturduğunuzda değişiklikleriniz geçersiz kılınabileceğinden, bu klasördeki hiçbir şeyi düzenlemeyin. Ancak hata ayıklama sırasında bu dosyalara bakmanız gerektiğinde bu klasör hakkında bilgi sahibi olmanızda fayda var.

### Adım 3 : Res klasörünü keşfedin

Proje > Android bölmesinde res klasörünü genişletin.

res klasörü kaynakları tutar. Android'deki kaynaklar, uygulamalarınızda kullanılan statik içeriktir. Kaynaklar, görüntüleri, metin dizelerini, ekran düzenlerini, stilleri ve onaltılık renkler veya standart boyutlar gibi değerleri içerir.

Android uygulamaları, Kotlin kodunu ve kaynaklarını mümkün olduğunca ayırır. Bu, uygulamanın kullanıcı arayüzünde kullanılan tüm dizeleri veya simgeleri bulmayı çok daha kolaylaştırır. Ayrıca, bu kaynak dosyalardan birini değiştirdiğinizde, değişiklik dosyanın uygulamada kullanıldığı her yerde geçerli olur.

res klasörü içinde, **aktivite_main.xml** dosyasını görmek için **layout** klasörünü genişletin.

![image](https://user-images.githubusercontent.com/70329389/139909735-3c1a17fb-b3bc-48a8-8550-d410547331e0.png)

**Activity'niz** genellikle **res/layout** dizininde bir XML dosyası olarak tanımlanan bir UI layout dosyasıyla ilişkilendirilir. Bu düzen dosyası genellikle etkinliğinden sonra adlandırılır. Bu durumda, aktivite adı **MainActivity'dir**, dolayısıyla ilişkili düzen **activity_main'dir**.

### Adım 4 : Manifests dosyasını ve AndroidManifest.xml'yi keşfedin

Manifests klasörü, uygulamanızla ilgili temel bilgileri Android sistemine sağlayan dosyaları içerir.

**Manifests** klasörünü genişletin ve açmak için **AndroidManifest.xml'e** çift tıklayın. AndroidManifest.xml dosyası, uygulamanın parçası olan etkinlikler de dahil olmak üzere, uygulamanızı çalıştırmak için Android sisteminin ihtiyaç duyduğu ayrıntıları içerir.

![image](https://user-images.githubusercontent.com/70329389/139912941-132a4a66-a8d7-4223-8602-33d268c16692.png)

MainActivity'ye  `<activity>`  öğesinde başvurulduğunu unutmayın. Uygulamanızdaki herhangi bir Etkinlik bildirimde bildirilmelidir. MainActivity için bir örnek:

```
  
  <activity android:name=".MainActivity">
   <intent-filter>
       <action android:name="android.intent.action.MAIN"/>

       <category android:name="android.intent.category.LAUNCHER"/>
   </intent-filter>
</activity>
  
```
  
 `<activity>`  içindeki  `<intent-filter>`  öğesini not edin. Bu intent filtresindeki  `<action>`  ve  `<category>`  öğeleri, Android'e, kullanıcı başlatıcı simgesini tıkladığında uygulamayı nerede başlatacağını söyler.
  
**AndroidManifest.xml** dosyası aynı zamanda uygulamanızın ihtiyaç duyduğu tüm izinleri tanımlayacağınız yerdir. İzinler, uygulamanızın telefon kişilerini okuma, internet üzerinden veri gönderme veya cihazın kamerası gibi donanımlara erişme yeteneğini içerir.


## <a name="3"></a>Gradle Scripts klasörünü keşfedin

Gradle, uygulamanın proje yapısını, yapılandırmasını ve bağımlılıklarını açıklamak için alana özgü bir dil kullanan bir yapı otomasyon sistemidir. Uygulamanızı derleyip çalıştırdığınızda, Gradle derlemesinin çalışmasıyla ilgili bilgileri görürsünüz. Ayrıca, yüklenmekte olan Android Package Kit (APK) hakkındaki bilgileri de görürsünüz. (APK, Android işletim sisteminin mobil uygulamaları dağıtmak ve yüklemek için kullandığı paket dosya biçimidir.)

**Gradle sistemini keşfedin:**

**Gradle Scripts* klasörünü genişletin. **Proje>Android** bölmesinde, bu klasör derleme sisteminin ihtiyaç duyduğu tüm dosyaları içerir.

![image](https://user-images.githubusercontent.com/70329389/139918331-906c69bf-f3de-4260-aab1-c3668bda23fb.png)

**build.gradle(Project: HelloWorld)** dosyasını arayın.

Bu dosya, projenizi oluşturan tüm modüllerde ortak olan yapılandırma seçeneklerini içerir. Her Android Studio projesi, tek bir üst düzey Gradle derleme dosyası içerir. Bu dosya, projedeki tüm modüller için ortak olan Gradle depolarını ve bağımlılıklarını tanımlar.

**build.gradle(Module:app)** dosyasını arayın.

Proje düzeyindeki **build.gradle** dosyasına ek olarak, her modülün kendine ait bir **build.gradle** dosyası vardır. Modül düzeyinde **build.gradle** dosyası, her modül için yapı ayarlarını yapılandırmanıza olanak tanır. (HelloWorld uygulamasının yalnızca bir modülü vardır, uygulamanın kendisi için olan modül.) Bu **build.gradle** dosyası, uygulama düzeyinde derleme yapılandırmalarını değiştirirken en sık düzenlediğiniz dosyadır. Örneğin, uygulamanızın desteklediği SDK düzeyini değiştirdiğinizde veya **dependencies** bölümünde yeni bağımlılıklar bildirdiğinizde bu **build.gradle** dosyasını düzenlersiniz. Daha sonraki bir codelab'de bu iki şey hakkında daha fazla bilgi edineceksiniz.


## <a name="4"></a>Uygulamanızı sanal bir cihazda çalıştırın (emülatör)

Bu görevde, bir sanal cihaz (emülatör) oluşturmak için [Android Virtual Device (AVD)](https://developer.android.com/studio/run/managing-avds) manager kullanmalısınız. Sanal cihaz, belirli bir Android cihazı türü için yapılandırmayı simüle eder. Ardından, uygulamayı çalıştırmak için o sanal cihazı kullanmalısınız.

Android Emulator bağımsız bir uygulamadır ve kendi sistem gereksinimleri vardır. Sanal cihazlar çok fazla disk alanı kullanabilir. Herhangi bir sorunla karşılaşırsanız, [Android Emulator'da uygulamaları çalıştırma](https://developer.android.com/studio/run/emulator.html#Requirements) konusuna bakın.

### Adım 1 : Bir Android sanal cihazı (AVD) oluşturun

Bilgisayarınızda bir emülatör çalıştırmak için sanal aygıtı tanımlayan bir yapılandırma oluşturmanız gerekir.

Android Studio'da **Tools>AVD Yöneticisi'ni** seçin veya araç çubuğundaki **AVD Manager** Simgesine tıklayın. **Your Virtual Divices** sekmesi açılacaktır. Zaten sanal cihazlar oluşturduysanız, iletişim kutusu bunları gösterir (aşağıdaki şekilde gösterildiği gibi), Aksi takdirde boş bir liste görürsünüz.

![image](https://user-images.githubusercontent.com/70329389/139925494-3df32d32-455c-4018-a61f-3d58353c8f15.png)

İletişim kutusunun sol alt kısmındaki **+Create Virtual Device'a tıklayın.** Önceden yapılandırılmış donanım aygıtlarının bir listesini gösteren **Select Hardware** iletişim kutusu görünür. Tablo, her aygıt için diyagonal ekran boyutu (**Size**), piksel cinsinden ekran çözünürlüğü (**Resolution**) ve piksel yoğunluğu (**Density**) için bir sütun sağlar.

![image](https://user-images.githubusercontent.com/70329389/139925974-0cc1c3d8-4be8-46f3-b50a-bd0c8cad4333.png)

**Nexus 5x** veya **Pixel XL** gibi bir cihaz seçin ve **Next**e tıklayın. **System Image** sekmesi açılacaktır.

**Recommended** sekmesine tıklayın ve sanal cihazda (**Pie** gibi) çalıştırılacak Android sisteminin hangi sürümünü seçin.

![image](https://user-images.githubusercontent.com/70329389/139926584-b9e4a380-e278-43e5-bd26-1cf55972f4d6.png)

> Dikkat Önerilen sekmesinde gösterilenden çok daha fazla Android sistemi sürümü mevcuttur. Bunları görmek için x86 Görüntüler ve Diğer Görüntüler sekmelerine bakın.

> Bu görüntüler çok fazla disk alanı kullanır, bu nedenle yalnızca birkaçı orijinal kurulumunuzun bir parçasıdır. Kullanmak istediğiniz sistem görüntüsünün yanında bir İndirme bağlantısı görünüyorsa, o görüntü yüklenmemiştir. Uzun zaman alabilen indirme işlemini başlatmak için bağlantıya tıklayın. İndirme tamamlandığında, Finish'e tıklayın.

Bir sistem görüntüsü seçtikten sonra **Next**e tıklayın. **Android Virtual Device (AVD)** iletişim kutusu açılır. Yapılandırmanızı kontrol edin ve **Finish**e tıklayın.

### Adım 2 : Uygulamayı sanal cihazda çalıştırın

Bu aşamada nihayet yeni uygulamanızı çalıştıracaksınız.

Android Studio'da **Run>Run app** seçin veya **Run** simgesine tıklayın, araç çubuğundaki Android Studio Run simgesi. **Select Deployment Target** sekmesi açılır ve sizi hiçbir aygıtın bulunmadığı konusunda uyarır. Geliştirme bilgisayarınıza bağlı fiziksel bir cihazınız yoksa veya henüz bir sanal cihaz başlatmadıysanız bu uyarıyı görürsünüz.

**Select Deployment Target** sekmesinde, **Available Virtual Devices** altında, oluşturduğunuz sanal aygıtı seçin. **OK**e tıklayın.

![image](https://user-images.githubusercontent.com/70329389/139927898-bad4f146-934d-4a3f-addf-4fa7d3bb3a12.png)

Emülatör, tıpkı fiziksel bir aygıt gibi başlar ve önyüklenir. Bilgisayarınızın hızına bağlı olarak bu işlem biraz zaman alabilir. Uygulamanız derlenir ve emülatör hazır olduğunda Android Studio, uygulama APK'sını emülatöre yükler ve çalıştırır.

Aşağıdaki şekilde gösterildiği gibi HelloWorld uygulamasını görmelisiniz.

![image](https://user-images.githubusercontent.com/70329389/139928084-35ea0f7e-8dea-4f0a-908d-afe6b5e97f00.png)


## <a name="5"></a>Uygulamanızı fiziksel bir cihazda çalıştırın

Bu aşamada, varsa, telefon veya tablet gibi fiziksel bir mobil cihazda uygulamanızı çalıştırırsınız. Uygulamalarınızı her zaman hem sanal hem de fiziksel cihazlarda test edin.

Neye ihtiyacın var:

- Telefon veya tablet gibi bir Android cihaz.
- Android cihazınızı USB bağlantı noktası üzerinden bilgisayarınıza bağlamak için bir USB veri kablosu.
- Bir Linux veya Windows sistemi kullanıyorsanız, ek adımlar uygulamanız gerekebilir. [Run apps on a hardware device](https://developer.android.com/studio/run/device) belgelerine bakın. Cihazınız için uygun USB sürücüsünü de yüklemeniz gerekebilir. Windows tabanlı USB sürücüleri için [Install OEM USB drivers](https://developer.android.com/studio/run/oem-usb) sayfasına bakın.

### Adım 1 : USB hata ayıklamasını açın

Android Studio'nun Android cihazınızla iletişim kurmasına izin vermek için cihazın **Developer options** ayarlarında USB hata ayıklamasını etkinleştirmelisiniz.

Android 4.2 (Jellybean) ve sonraki sürümlerde, **Developer options** ayarları varsayılan olarak gizlidir. Geliştirici seçeneklerini göstermek ve USB hata ayıklamasını etkinleştirmek için:

- Cihazınızda **Ayarlar'ı** açın, **Telefon hakkında'yı** arayın, Telefon hakkında'ya dokunun ve **Build number'a** yedi kez dokunun.
- Önceki sayfaya dönün **(Ayarlar / Sistem)**. **Geliştirici seçenekleri** listede görünür. **Geliştirici seçenekleri'ne** dokunun.
- **USB hata ayıklamayı** seçin.

### Adım 2 : Uygulamanızı Android cihazda çalıştırın

Artık cihazınızı bağlayabilir ve uygulamayı Android Studio'dan çalıştırabilirsiniz.

Android cihazını bir USB kablosuyla geliştirme makinenize bağlayın. Cihazda, USB hata ayıklamasına izin verilmesini isteyen bir iletişim kutusu görünmelidir.

![image](https://user-images.githubusercontent.com/70329389/139941612-2294b725-ab68-422b-a20f-1bbf4a35ca01.png)

Bu bilgisayarı hatırlamak için **Always allow** seçeneğini seçin . **OK**a dokunun.

Bilgisayarınızda, Android Studio araç çubuğunda **Run** düğmesini Android Studio Çalıştır simgesini tıklayın. Kullanılabilir emülatörlerin ve bağlı cihazların listesini içeren **Select Deployment Target** sekmesi açılır. Fiziksel cihazınızı herhangi bir emülatörle birlikte görmelisiniz.

![image](https://user-images.githubusercontent.com/70329389/139942040-59f89f40-6cb4-4ba6-b108-00d1accd02b4.png)

Cihazınızı seçin ve **OK**a tıklayın. Android Studio, uygulamayı cihazınıza yükler ve çalıştırır.

### Adım 3 : Sorun giderme

Android Studio cihazınızı tanımıyorsa aşağıdakileri deneyin:

- USB kablosunu çıkarın ve tekrar takın.
- Android Studio'yu yeniden başlatın.

Bilgisayarınız aygıtı yine de bulamazsa veya "yetkisiz" olduğunu bildirirse şu adımları izleyin:

- USB kablosunu çıkarın.
- Cihazda, Ayarlar uygulamasında Geliştirici seçeneklerini açın. USB hata ayıklama yetkilerini iptal et'e dokunun.
- Cihazı bilgisayarınıza yeniden bağlayın. İstendiğinde, yetkiler verin.


Cihazınız için uygun USB sürücüsünü yüklemeniz gerekebilir.[Run apps on a hardware device.](https://developer.android.com/studio/run/device) konusuna bakın.

## <a name="6"></a> Ev Ödevi

- Boş Şablondan yeni bir Android projesi oluşturun.
- res/strings.xmlDosyayı açın .
- **app_name** dizesinin değerini "My Dice Roller" olarak değiştirin. Bu ad başlık çubuğunda görünür.
- İstediğiniz herhangi bir Android sürümünü hedefleyen bir cihaz için bir emülatör oluşturun ve uygulamayı çalıştırın. Uygulamanın başlığının nasıl değiştiğine dikkat edin.


## <a name="7"></a> Ödül:

Eğer bu yazıyı okuduysanız artık hızlı bir quizle çalışmanızı ödüllendirebilirsiniz. [Bu linkten](https://developer.android.com/courses/pathways/kotlin-fundamentals-one) ilerleyişinizi takip edebilir, kendinizi test edebilir ve Google Developers Profile ve diğer sosyal medyalarda paylaşılabilir bir badge kazanabilirsiniz.

![image](https://user-images.githubusercontent.com/70329389/139955476-aaf0c4f1-43e1-4bc4-860b-368ecf11de55.png)
