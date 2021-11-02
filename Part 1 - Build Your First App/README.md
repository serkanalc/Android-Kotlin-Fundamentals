# Build Your First App

Selamlar Android Kotlin Fundamentals serisinin ilk bölümüne hoş geldiniz. Bu bölümde aşağıdaki konular üzerinde duruyor olacağız:

- [Android Studio Kurulumu](#kurulum)
- [Geliştirmeye Başlayalım](#başlangıç)

Hadi Başlayalım❗


## <a name="kurulum"></a>Android Studio Kurulumu

Android Studio, sizlere gelişmiş bir kod düzenleyici ve uygulama şablonları dahil eksiksiz bir IDE sağlar. Ayrıca, uygulama geliştirmeyi daha hızlı ve daha kolay hale getiren geliştirme, hata ayıklama, test etme ve performans araçları içerir. Uygulamalarınızı çok çeşitli önceden yapılandırılmış emülatörlere veya kendi mobil cihazınızda test etmek için Android Studio'yu kullanabilirsiniz. Ayrıca Google Play Store'da mobil uygulamalar oluşturabilir ve bu uygulamaları yayınlayabilirsiniz.

> **Not:**  Android Studio sürekli olarak geliştirilmektedir. Sistem gereksinimleri ve kurulum talimatları hakkında en son bilgiler için [ Android Studio download page](https://developer.android.com/studio/) e bakın.

Android Studio, Windows veya Linux çalıştıran bilgisayarlar ve macOS çalıştıran Mac'ler için kullanılabilir. En yeni OpenJDK (Java Development Kit), Android Studio ile birlikte gelir.

Kurulum tüm platformlar için benzerdir. Herhangi bir farklılık aşağıda belirtilmiştir.

- [Android Studio indirme sayfasına](https://developer.android.com/studio/) gidin ve Android Studio'yu indirip yüklemek için [talimatları](https://developer.android.com/studio/install.html) izleyin .
- Tüm adımlar için varsayılan yapılandırmaları kabul edin ve kurulum için tüm bileşenlerin seçildiğinden emin olun.
- Kurulum tamamlandıktan sonra kurulum sihirbazı, Android SDK dahil olmak üzere ek bileşenleri indirir ve kurar. Sabırlı olun, çünkü bu işlem internet hızınıza bağlı  olarak biraz zaman alabilir.
- Kurulum tamamlandığında Android Studio başlar ve ilk projenizi oluşturmaya hazırsınız.

> **Sorun Giderme:** Kurulumunuzla ilgili sorun yaşarsanız, [Android Studio release notes](https://developer.android.com/studio/releases/index.html) veya [Troubleshoot Android Studio.](https://developer.android.com/studio/troubleshoot) sayfalarına bakın.




## <a name="başlangıç"></a>Geliştirmeye Başlayalım

- ["HelloWorld" projesini oluşturun](#1)
- [Android Studio'yu Keşfedin](#2)

IDE(integrated development environment) kurulumu tamamlandığına göre artık başlayabiliriz.

### <a name="1"></a>"HelloWorld" projesini oluşturun

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
9.**Use AndroidX artifacts** onay kutusunu seçin.
10.Diğer tüm onay kutularını boş bırakın ve **Finish** butonuna tıklayın. Projeniz, seçtiğiniz hedef SDK için daha fazla bileşen gerektiriyorsa, Android Studio bunları otomatik olarak yükler ve bu biraz zaman alabilir. İstemleri izleyin ve varsayılan seçenekleri kabul edin.

Android Studio şimdi projenizi oluşturuyor ve bu biraz zaman alabilir. Herhangi bir hata almamalısınız. Herhangi bir uyarı alırsanız, onları dikkate almayın.

### <a name="2"></a>"Android Studio'yu Keşfedin

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





