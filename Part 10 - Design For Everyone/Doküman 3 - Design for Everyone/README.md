# Design for Everyone

- [Right to Left (RTL) Diller İçin Destek Ekleyin](#a)
- [Erişilebilirlik İçin Scan Edin](#b)
- [TalkBack İçin Design](#c)
- [Bölgeleri Filtrelemek İçin Çipleri Kullanın](#d)
- [Night Mode'u Destekleyin](#e)

GDG-finder başlangıç uygulaması, bu kursta şimdiye kadar öğrendiğiniz her şeyin üzerine inşa edilmiştir.

Uygulama, üç ekran yerleştirmek için ConstraintLayout'u kullanır. Ekranlardan ikisi, Android'de renkleri ve metni keşfetmek için kullanacağınız layout dosyalarıdır.

Üçüncü ekran bir GDG bulucudur. GDG'ler veya Google Geliştirici Grupları, Android dahil olmak üzere Google teknolojilerine odaklanan geliştirici topluluklarıdır. Dünyanın dört bir yanındaki GDG'ler buluşmalara, konferanslara, Study Jam'lere ve diğer etkinliklere ev sahipliği yapıyor.

Bu uygulamayı geliştirirken, GDG'lerin gerçek listesi üzerinde çalışıyorsunuz. Bulucu ekranı, GDG'leri mesafeye göre sıralamak için cihazın konumunu kullanır.

Şanslıysanız ve bölgenizde bir GDG varsa, web sitesine göz atabilir ve etkinliklerine kaydolabilirsiniz! GDG etkinlikleri, diğer Android geliştiricileriyle tanışmak ve bu kursa uymayan sektördeki en iyi uygulamaları öğrenmek için harika bir yoldur.

Aşağıdaki ekran görüntüleri, uygulamanızın bu codelab'in başından sonuna kadar nasıl değişeceğini gösterir.

![Ekran Resmi 2022-01-14 10 02 12](https://user-images.githubusercontent.com/70329389/149465415-0aa06b39-e12c-48ed-b642-e34e1394e700.png)


## <a name="a"></a>Aşama 1 : Right to Left (RTL) Diller İçin Destek Ekleyin

Soldan sağa (LTR) ve sağdan sola (RTL) diller arasındaki temel fark, görüntülenen içeriğin yönüdür. UI yönü LTR'den RTL'ye (veya tam tersi) değiştirildiğinde, genellikle yansıtma olarak adlandırılır. Yansıtma, metin, metin alanı simgeleri, düzenler ve yön içeren simgeler (oklar gibi) dahil olmak üzere ekranın çoğunu etkiler. Sayılar (saat, telefon numaraları), yönü olmayan simgeler (uçak modu, WiFi), oynatma kontrolleri ve çoğu çizelge ve grafik gibi diğer öğeler yansıtılmaz.

RTL metin yönünü kullanan diller, dünya çapında bir milyardan fazla insan tarafından kullanılmaktadır. Android geliştiricileri dünyanın her yerindedir ve bu nedenle bir GDG Finder uygulamasının RTL dillerini desteklemesi gerekir.

### Adım 1 : RTL Desteği Ekleyin

Bu adımda GDG Finder uygulamasının RTL dilleriyle çalışmasını sağlarsınız.

1. Bu codelab için başlangıç uygulaması olan [GDGFinderMaterial](https://github.com/google-developer-training/android-kotlin-fundamentals-apps/tree/master/GDGFinderMaterial) uygulamasını indirin ve çalıştırın veya önceki codelab'in son kodundan devam edin.

2. Android Manifest'i açın.

3. `<application>` bölümünde, uygulamanın RTL'yi desteklediğini belirtmek için aşağıdaki kodu ekleyin.

```
<application
        ...
        android:supportsRtl="true">
```

4. **Design** sekmesinde **aktivite_main.xml**'i açın.

5. **Locale for Preview** açılır menüsünden **Preview Right to Left**'i seçin. (Bu menüyü bulamazsanız, ortaya çıkarmak için bölmeyi genişletin veya Attributes bölmesini kapatın.)

![image](https://user-images.githubusercontent.com/70329389/149629146-3eb95048-bffe-4b1c-b9d8-57bdaf9cc8ab.png)

> İpucu: Uygulamanızın görsel olarak RTL'de nasıl sunulduğunu kontrol etmek için cihazınızda diller arasında geçiş yapmanız gerekmez.

6. **Preview**'de, "GDG Finder" başlığının sağa taşındığına ve ekranın geri kalanının hemen hemen aynı kaldığına dikkat edin. Genel olarak, bu ekran başarılı. Ancak metin görünümündeki hizalama artık yanlış çünkü sağ yerine sola hizalanıyor.

![image](https://user-images.githubusercontent.com/70329389/149629189-aa5ccb3e-4d62-4d7b-94ec-2d0cf20ab863.png)

7. Bunun cihazınızda çalışması için cihazınızda veya emülatör Ayarlarında, Geliştirici Seçenekleri'nde RTL düzenini zorla'yı seçin. (Geliştirici Seçeneklerini açmanız gerekiyorsa, Yapı Numarasını bulun ve geliştirici olduğunuzu belirten bir mesaj alana kadar tıklayın. Bu, cihaza ve Android sisteminin sürümüne göre değişir.)

![image](https://user-images.githubusercontent.com/70329389/149629220-8f729aed-a849-44ba-9a9a-961be820d808.png)

8. Uygulamayı çalıştırın ve ana ekranın Önizleme'dekiyle aynı göründüğünü cihazda doğrulayın. FAB'nin şimdi sola ve Hamburger menüsünün sağa çevrildiğine dikkat edin!

9. Uygulamada, navigation drawer'ı açın ve Arama ekranına gidin. Aşağıda gösterildiği gibi, simgeler hala soldadır ve hiçbir metin görünmez. Metnin ekranın dışında, simgenin solunda olduğu ortaya çıktı. Bunun nedeni, kodun görünüm özelliklerinde ve düzen kısıtlamalarında sol/sağ ekran referanslarını kullanmasıdır.

![Ekran Resmi 2022-01-15 19 23 53](https://user-images.githubusercontent.com/70329389/149629382-b1aef36c-72bf-486b-982e-c11c5cac3d87.png)

### 2. Adım: Left & Right Yerine Start & End Kullanın

Ekrandaki "sol" ve "sağ" (ekrana baktığınızda) metnin yönü değişse bile değişmez. Örneğin layout_constraintLeft_toLeftOf, öğenin sol tarafını her zaman ekranın sol tarafıyla sınırlar. Uygulamanızın durumunda, yukarıdaki ekran görüntüsünde gösterildiği gibi metin RTL dillerinde ekranın dışındadır.

Bunu düzeltmek için "left" ve "right" yerine **Start** ve **End** terminolojisini kullanın. Bu terminoloji, metnin başlangıcını ve metnin sonunu mevcut dilde metnin yönüne uygun olarak ayarlar, böylece kenar boşlukları ve düzenler ekranların doğru alanlarında olur.

1. list_item.xml'i açın.
2. Left ve Right yapılan tüm referansları Start ve End referanslarıyla değiştirin.

```
app:layout_constraintStart_toStartOf="parent"

app:layout_constraintStart_toEndOf="@+id/gdg_image"
app:layout_constraintEnd_toEndOf="parent"
```
3. ImageView'ın layout_marginLeft değerini layout_marginStart ile değiştirin. Bu, simgeyi ekranın kenarından uzağa taşımak için kenar boşluğunu doğru yere taşır.

```
<ImageView
android:layout_marginStart="
?
```

4. **fragman_gdg_list.xml** dosyasını açın. Preview bölmesindeki GDG'lerin listesini kontrol edin. Yansıtıldığı için simgenin hala yanlış yönü gösterdiğine dikkat edin (Simge yansıtılmamışsa, sağdan sola önizlemeyi görüntülediğinizden emin olun). Materyal Tasarımı yönergelerine göre simgeler aynalanmamalıdır.

5. **res/drawable/ic_gdg.xml** dosyasını açın.

6. XML kodunun ilk satırında, yansıtmayı devre dışı bırakmak için **Android:autoMirrored="true"** öğesini bulun ve silin.

7. Preview kontrol edin veya uygulamayı tekrar çalıştırın ve GDG Ara ekranını açın. Layout şimdi düzeltilmeli!

![image](https://user-images.githubusercontent.com/70329389/149732595-7bf0f09a-22f0-4a9d-8d7d-81cd5484cc72.png)

> İpucu: Android Studio, start ve end özelliklerinin kullanımını teşvik etmek için size sarı vurgulu ipuçları verir. XML layout'unda Sol ve Sağın tüm oluşumlarını bulmak için büyük/küçük harf eşleştirmeyle ara ve değiştir özelliğini kullanın.

### Adım 3 : Bırakın İşi Sizin İçin Android Studio Yapsın!

Önceki alıştırmada, RTL dillerini desteklemek için ilk adımlarınızı attınız. Neyse ki, Android Studio uygulamanızı tarayabilir ve sizin için birçok temel ayar ayarlayabilir.

1. **list_item.xml**'de, TextView'da **layout_marginStart**'ı tekrar **layout_marginLeft** olarak değiştirin, böylece tarayıcının bulabileceği bir şey olur.

```
<TextView
android:layout_marginLeft="@dimen/spacing_normal"
```

2. Android Studio'da, **Refactor > Add RTL support where possible**'yi seçin ve başlangıç ve bitiş özelliklerini kullanmak için bildirimi ve düzen dosyalarını güncellemek için kutuları işaretleyin.

![image](https://user-images.githubusercontent.com/70329389/149733528-ac6d7950-4573-4d1f-b304-d4eee72135da.png)

3. **Refactoring Preview** bölmesinde, uygulama klasörünü bulun ve tüm ayrıntılara açılana kadar genişletin.
4. Uygulama klasörünün altında, az önce değiştirdiğiniz **layout_marginLeft** öğesinin refactor kodu olarak listelendiğine dikkat edin.

![image](https://user-images.githubusercontent.com/70329389/149733759-d1a75af9-f28c-4e27-a58b-6a746c7ed47d.png)

5. Önizlemenin sistem ve kitaplık dosyalarını da listelediğine dikkat edin. **layout** ve **layout-watch-v20** ve uygulamanın parçası olmayan diğer klasörlere sağ tıklayın ve içerik menüsünden Exclude'u seçin.

![image](https://user-images.githubusercontent.com/70329389/149733915-0879a701-ed0f-4bf4-ae78-e50050ecd756.png)

6. Devam edin ve şimdi yeniden düzenlemeyi yapın. (Sistem dosyaları hakkında bir açılır pencere alırsanız, uygulama kodunuzun parçası olmayan tüm klasörleri hariç tuttuğunuzdan emin olun.)
7. layout_marginLeft öğesinin layout_marginStart olarak değiştirildiğine dikkat edin.

Not: Bazı Görünüm bileşenlerinin, RTL ile düzgün şekilde davranması için daha fazla özelleştirmeye ihtiyacı vardır. Kullanıcı arayüzü üzerinde daha hassas kontrole sahip olmak için kullanabileceğiniz 4 farklı API vardır:

Bir bileşenin düzeninin yönünü ayarlamak için [Android:layoutDirection](https://developer.android.com/reference/android/util/LayoutDirection?authuser=6).
[Android:textDirection](https://developer.android.com/reference/android/view/View.html?authuser=6#attr_android:textDirection) bir bileşenin metninin yönünü ayarlamak için.
[Android:textAlignment](https://developer.android.com/reference/android/view/View.html?authuser=6#attr_android:textAlignment) bir bileşenin metninin hizalamasını ayarlamak için.
[getLayoutDirectionFromLocale()](https://developer.android.com/reference/android/support/v4/text/TextUtilsCompat?authuser=6#getlayoutdirectionfromlocale), yönü belirten yerel ayarı programlı olarak almak için bir yöntemdir

### Adım 4 : Yerel Ayarlar İçin Klasörleri Keşfedin

Şimdiye kadar, uygulama için kullanılan varsayılan dilin yönünü değiştirdiniz. Bir üretim uygulaması için, yeni bir dile çevrilmesi için strings.xml dosyasını bir çevirmene gönderirsiniz. Bu kod laboratuvarı için uygulama, İspanyolca bir strings.xml dosyası sağlar (çevirileri oluşturmak için Google Çeviri'yi kullandık, bu yüzden mükemmel değiller.).

1. Android Studio'da proje görünümünü **Project Files** olarak değiştirin.

2. res klasörünü genişletin ve **res/values** ve **res/values-es** klasörlerine dikkat edin. Klasör adındaki "es", İspanyolca'nın dil kodudur. Değerler-"[language code](https://www.loc.gov/standards/iso639-2/php/code_list.php)" klasörleri, desteklenen her dil için değerler içerir. Uzantısı olmayan değerler klasörü, aksi takdirde geçerli olan varsayılan kaynakları içerir.

![image](https://user-images.githubusercontent.com/70329389/149735120-6c9a107a-4bd9-477e-a391-da6cc97b82ef.png)

3. Değerlerde, strings.xml dosyasını açın ve tüm dizelerin İspanyolca olduğuna dikkat edin.

4. Android Studio'da, Tasarım sekmesinde aktivite_main.xml'i açın.

5. Önizleme için Yerel Ayar açılır menüsünde İspanyolca'yı seçin. Metniniz şimdi İspanyolca olmalıdır.

![image](https://user-images.githubusercontent.com/70329389/149735438-b098f864-7ae8-4fb9-abbf-a0c263de9b95.png)

6. [İsteğe bağlı] Bir RTL dilinde uzmansanız, o dilde bir değerler klasörü ve bir string.xml oluşturun ve bunun cihazınızda nasıl göründüğünü test edin.
7. [İsteğe bağlı] Cihazınızdaki dil ayarlarını değiştirin ve uygulamayı çalıştırın. Geri almayı biraz zorlaştıracağından, cihazınızı okumadığınız bir dile çevirmediğinizden emin olun!

## <a name="b"></a>Aşama 2 : Erişilebilirlik İçin Scan Edin

Önceki görevde, uygulamanızı manuel olarak değiştirdiniz ve ardından yapılacak ek RTL iyileştirmelerini kontrol etmek için Android Studio'yu kullandınız.

[Accessibility Scanner](https://play.google.com/store/apps/details?id=com.google.android.apps.accessibility.auditor) uygulaması, uygulamanızı erişilebilir kılmak söz konusu olduğunda en iyi müttefikinizdir. Hedef cihazınızdaki görünür ekranı tarar ve dokunma hedeflerini büyütme, kontrastı artırma ve uygulamanızı daha erişilebilir hale getirmek adına resimler için açıklamalar sağlama gibi iyileştirmeler önerir. Accessibility Scanner Google tarafından yapılmıştır ve Play Store'dan yükleyebilirsiniz.

### Adım 1: Accessibility Scanner'ı Kurun Ve Çalıştırın

1. Play Store'u açın ve gerekirse oturum açın. Bunu fiziksel cihazınızda veya emülatörünüzde yapabilirsiniz. Bu doküman emülatörü kullanır.

2. Play Store'da, Google LLC tarafından Accessibility Scanner arayın. Doğru uygulamayı seçtiğinize emin olun, arama sonucu birçok uygulama karşınıza çıkacak.

![image](https://user-images.githubusercontent.com/70329389/150338786-3f294c82-772d-40a6-ac85-017f100f2060.png)

3. Scanner'ı emülatöre indirin.
4. Kurulduktan sonra, Aç'ı tıklatın.
5. Başla'yı tıklayın.
6. Ayarlardaki Accessibility Scanner kurulumunu başlatmak için Tamam'ı tıklayın.

![image](https://user-images.githubusercontent.com/70329389/150339396-c1024e23-81d4-447b-bb95-b4190041e1a7.png)

7. Aygıtın erişilebilirlik ayarlarına gitmek için Accessibility Scanner'a tıklayın.

![image](https://user-images.githubusercontent.com/70329389/150339496-1059de2f-a0d2-417f-ac91-ff333981a6f2.png)

8. Aktifleştirmek için **User Service**'e tıklayın

![image](https://user-images.githubusercontent.com/70329389/150339599-32e50b62-a1a0-4824-8812-1c33c089366e.png)

9. Ekrandaki talimatları izleyin ve tüm izinleri verin.

10. Ardından Tamam'ı tıklayın ve ana ekrana dönün. Ekranda bir yerde bir kontrol işaretiyle mavi bir düğmeyi görebilirsiniz. Bu düğmeye tıklamak, ön plandaki uygulamanın testini tetikler. Düğmeyi sürükleyerek yeniden konumlandırabilirsiniz. Bu düğme herhangi bir uygulamanın üstünde kalır, böylece herhangi bir zamanda test tetikleyebilirsiniz.

![image](https://user-images.githubusercontent.com/70329389/150339803-43ce309d-99fa-4223-af6c-1c6be11c5a00.png)

> Tarayıcıyı Ayarlar sekmesinden kapatabilirsiniz.

11. Uygulamanızı açın veya çalıştırın.

12. Mavi düğmeye tıklayın ve ek güvenlik uyarıları ve izinleri kabul edin.

Accessibility Scanner simgesine ilk kez tıkladığınızda, uygulama ekranınızda her şeyin görüntülenmesi için izin ister. Bu çok korkutucu izin gibi görünüyor ve öylede.

Bu gibi bir izin neredeyse hiçbir zaman vermemelisiniz, çünkü bu izin uygulamalarının e-postanızı okuduğunu hatta banka hesap bilgilerinizi erişmesine bile izin verir! Ancak, Accessibility Scanner çalışmalarını yapması için, uygulamanızı bir kullanıcının yapacağı şekilde incelemesi gerekir - bu yüzden bu iznine ihtiyaç duyar.

13. Mavi düğmeyi tıklayın ve analizin tamamlanmasını bekleyin. Aşağıdaki ekran görüntüsü gibi bir şey göreceksiniz, başlık ve Fab kırmızı kutulu. Bu, bu ekranda erişilebilirlik gelişmeleri için iki öneri olduğunu gösterir.

![image](https://user-images.githubusercontent.com/70329389/150341618-0bd48905-4e6d-48c6-b244-5cc5b5f82363.png)

14. GDG bulucuyu çevreleyen kutuya tıklayın. Bu, aşağıda gösterildiği gibi, görüntü kontrastlı sorunları belirten ek bilgiyle bir bölme açar.

15. Görüntü kontrast bilgilerini genişletin ve araç ilaçlar önerir.

16. Bir sonraki öğe için bilgi almak adına sağdaki okları tıklayın.

<img width="799" alt="Ekran Resmi 2022-01-20 15 51 17" src="https://user-images.githubusercontent.com/70329389/150341990-eec65cd5-a442-46ad-b596-e899b97b5eb6.png">

17. Uygulamanızda, **Apply for GDG** gidin ve Accessibility Scanner uygulamasıyla tarayın. Bu, solda gösterildiği gibi, oldukça az sayıda öneri verir. 12. Adil olalım, bunlardan bazıları benzer itemlerin  kopyalardır.

18. Sağ ekran görüntüsünde aşağıda gösterildiği gibi, tüm önerilerin bir listesini almak için alt araç çubuğundaki simgeye tıklayın. Bu codelab'daki tüm bu sorunları göreceksiniz.

![image](https://user-images.githubusercontent.com/70329389/150343207-29200487-8e45-4463-a699-15262cf8513f.png)
![image](https://user-images.githubusercontent.com/70329389/150343311-149ae84c-949a-44ba-90a4-e3f5c86ffcad.png)

## <a name="c"></a>Aşama 3 : TalkBack İçin Design

Google'ın bir uygulama koleksiyonu olan [Android Accessibility Suite](https://play.google.com/store/apps/details?id=com.google.android.marvin.talkback), uygulamaları daha erişilebilir hale getirmeye yardımcı olacak araçlar içerir. TalkBack gibi araçlar içerir. TalkBack, kullanıcıların gözlerini kullanmadan cihazlarında gezinmesine ve içeriği kullanmasına olanak tanıyan işitsel, dokunsal ve sözlü geri bildirim sunan bir ekran okuyucudur.

TalkBack'in yalnızca kör insanlar tarafından değil, aynı zamanda bir tür görme bozukluğu olan birçok kişi tarafından da kullanıldığı ortaya çıktı. Ya da sadece gözlerini dinlendirmek isteyenler bile!

Yani, Erişilebilirlik herkes içindir! Bu görevde, TalkBack'i dener ve uygulamanızı onunla iyi çalışacak şekilde güncellersiniz.

### 1. Adım: Accessibility Suite'i kurun ve çalıştırın

TalkBack birçok fiziksel cihaza önceden yüklenmiş olarak gelir, ancak bir emülatörde onu yüklemeniz gerekir.

1. Play Store'u açın.

2. Accessibility Suite (Erişilebilirlik Paketini) bulun. Google'ın sunduğu doğru uygulama olduğundan emin olun.

3. Kurulu değilse, Erişilebilirlik Paketi'ni yükleyin.

4. Cihazda TalkBack'i etkinleştirmek için **Settings > Accessibility**'e gidin ve **Use Service**'ı seçerek TalkBack'i açın. Erişilebilirlik tarayıcısı gibi TalkBack de ekrandaki içeriği okumak için izinler gerektirir. İzin isteklerini kabul ettiğinizde, TalkBack sizi TalkBack'i nasıl etkili bir şekilde kullanacağınızı öğretmek için bir eğitim listesiyle karşılar.

5. İşiniz bittiğinde TalkBack'i nasıl yeniden kapatacağınızı öğrenmekten başka bir neden yoksa burada duraklayın ve öğreticilere katılın.

6. Eğiticiden ayrılmak için, seçmek üzere geri düğmesini tıklayın, ardından ekranda herhangi bir yere iki kez dokunun.

> İpucu: Hiçbir şey duyamıyorsanız... sesi açın!

> İpucu: Bir öğeyi tıklamak için iki kez dokunun; kaydırmak veya sürüklemek için çift dokunun ve sürükleyin.

7. TalkBack ile GDG Finder uygulamasını kullanarak keşfedin. TalkBack'in ekran veya kontrol hakkında size yararlı bilgiler vermediği yerlere dikkat edin. Bir sonraki alıştırmada bunu düzelteceksiniz.

### 2. Adım: Bir içerik açıklaması ekleyin


