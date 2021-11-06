# Görüntü kaynakları ve uyumluluk

- [Görüntü Kaynakları Ekleyin & Güncelleyin](#a)
- [Görünümleri Verimli Bir Şekilde Bulun](#b)
- [Varsayılan Bir Resim Kullanın](#c)
- [API Düzeylerini & Uyumluluğu Anlayın](#d)

Bu codelab'de, önceki codelab'de başlattığınız DiceRoller uygulamasını geliştirirsiniz ve zar atıldığında değişen zar görüntüleri eklersiniz. Son DiceRoller uygulaması şöyle görünür:

![image](https://user-images.githubusercontent.com/70329389/140610934-aa1f52ea-9c6e-49cd-af06-f8aff02992f3.png)

## <a name="a"></a>Görüntü Kaynakları Ekleyin & Güncelleyin

Son kod laboratuvarının sonunda, kullanıcı bir düğmeye her dokunduğunda 1 ile 6 arasında bir sayıyla metin görünümünü güncelleyen bir uygulamanız vardı. Ancak, uygulamanın adı 1-6 Sayı Üreticisi değil, DiceRoller'dır, bu nedenle zarlar gerçekten zar gibi görünse iyi olurdu. Bu görevde, uygulamanıza bazı zar görüntüleri eklersiniz. Ardından, düğmeye basıldığında metni güncellemek yerine, her rulo sonucu için farklı bir görüntüde geçiş yaparsınız.

### Aşama 1 : Resimleri Ekleyin

1. Henüz açık değilse, DiceRoller uygulama projesini Android Studio'da açın. 
2. **Project > Android** görünümünde **res** klasörünü genişletin ve ardından çekilebilir öğesini **drawable**.

![image](https://user-images.githubusercontent.com/70329389/140611122-ff412699-3167-4dc5-a2d8-8f38256bc435.png)

Uygulamanız, resimler ve simgeler, renkler, dizeler ve XML layoutları dahil olmak üzere birçok farklı kaynak kullanır. Tüm bu kaynaklar **res** klasöründe saklanır. **Drawable** klasör, uygulamanız için tüm görüntü kaynaklarını koymanız gereken yerdir. Zaten drawable klasörde, uygulamanın başlatıcı simgeleri için kaynakları bulabilirsiniz.

3. **ic_launcher_background.xml** dosyasını çift tıklayın. Bunların simgeyi bir vektör görüntüsü olarak tanımlayan XML dosyaları olduğunu unutmayın. Vektörler, resimlerinizin birçok farklı boyut ve çözünürlükte çizilmesini sağlar. PNG veya GIF gibi bitmap görüntülerin farklı cihazlar için ölçeklenmesi gerekebilir, bu da kalite kaybına neden olabilir.
4. Drawable vektörü görsel biçimde görüntülemek için XML editörünün sağ sütununda  **Preview**'e tıklayın.

![image](https://user-images.githubusercontent.com/70329389/140611259-b7b2520a-5ff9-4503-b998-c723115f10f4.png)

5. DiceImages.zip'ten uygulamanız için [zar resimlerini](https://github.com/serkanalc/Android-Kotlin-Fundamentals/blob/main/Part%202%20-%20Build%20an%20interactive%20app/Dok%C3%BCman%202%20-%20G%C3%B6r%C3%BCnt%C3%BC%20Kaynaklar%C4%B1%20%26%20Uyumluluk/DiceImages.zip) indirin. Arşivi açın. Şuna benzeyen bir XML dosyaları klasörünüz olmalıdır:

![image](https://user-images.githubusercontent.com/70329389/140611328-6fe0c43f-cdb8-4bff-9ac1-fad909bdc214.png)

6. Android Studio'da, şu anda Android yazan proje görünümünün üst kısmındaki açılır menüyü tıklayın ve Proje'yi seçin. Aşağıdaki ekran görüntüsü, uygulamanızın yapısının dosya sisteminde nasıl göründüğünü gösterir.

![image](https://user-images.githubusercontent.com/70329389/140611384-7e39f309-6cc4-4ff4-9c7b-63ca1d1dc3c0.png)

7. **DiceRoller > app > src > main > res > drawable** öğesini genişletin.
8. **DiceImages** klasöründeki tüm XML dosyalarını Android Studio'ya ve **drawable** klasöre sürükleyin. **OK**'ı tıklayın.

> Not: Dosyaları drawable24 klasörüne değil, drawable klasörüne bıraktığınızdan emin olun. Bu klasör ve diğerlerinin ne için kullanıldığı hakkında daha sonra daha fazla bilgi edineceksiniz.

Ayrıca, **DiceImages** klasörünün kendisini dahil etmeyin. Yalnızca XML dosyalarını sürükleyin.

9. Projeyi tekrar Android görünümüne çevirin ve zar resmi XML dosyalarınızın drawable klasörde olduğuna dikkat edin.
10. **dice_1.xml**'ye çift tıklayın ve bu görüntü için XML koduna dikkat edin. Bu drawable vektörün gerçekte nasıl göründüğünün bir önizlemesini almak için **Preview** düğmesini tıklayın.

![image](https://user-images.githubusercontent.com/70329389/140611513-54cc166f-b2ff-432f-a6ac-d61c517a2540.png)

### Aşama 2 Resimleri Kullanmak İçin Layout'u Güncelleyin








## <a name="b"></a>Görünümleri Verimli Bir Şekilde Bulun
## <a name="c"></a>Varsayılan Bir Resim Kullanın
## <a name="d"></a>API Düzeylerini & Uyumluluğu Anlayın

