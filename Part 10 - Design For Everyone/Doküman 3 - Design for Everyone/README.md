# Design for Everyone

- [Right to Left (RTL) Diller İçin Destek Ekleyin](#a)
- [Erişilebilirlik İçin Scan Edin](#b)
- [TalkBack için Design](#c)
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
