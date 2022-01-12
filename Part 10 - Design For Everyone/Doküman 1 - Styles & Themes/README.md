# <a name="1"></a> Styles & Themes


- [Android'in Stil Sistemi](#a)
- [Stil İçin Attribute'leri Kullanın](#b)
- [Temaları ve İndirilebilir Yazı Tiplerini Kullanın](#c)
- [Stilleri Kullan](#d)

GDG-finder başlangıç uygulaması, bu kursta şimdiye kadar öğrendiğiniz her şeyin üzerine inşa edilmiştir.

Uygulama, üç ekran yerleştirmek için `ConstraintLayout'u kullanır`. Ekranlardan ikisi, Android'de renkleri ve metni keşfetmek için kullanacağınız düzen dosyalarıdır.

Üçüncü ekran bir GDG bulucudur. GDG'ler veya Google Geliştirici Grupları, Android dahil olmak üzere Google teknolojilerine odaklanan geliştirici topluluklarıdır. Dünyanın dört bir yanındaki GDG'ler buluşmalara, konferanslara, çalışma sıkışıklığına ve diğer etkinliklere ev sahipliği yapıyor.

Bu uygulamayı geliştirirken, GDG'lerin gerçek listesi üzerinde çalışıyorsunuz. Bulucu ekranı, GDG'leri mesafeye göre sıralamak için cihazın konumunu kullanır.

Şanslıysanız ve bölgenizde bir GDG varsa, web sitesine göz atabilir ve etkinliklerine kaydolabilirsiniz! GDG etkinlikleri, diğer Android geliştiricileriyle tanışmak ve bu kursa uymayan sektördeki en iyi uygulamaları öğrenmek için harika bir yoldur.

Aşağıdaki ekran görüntüleri, uygulamanızın bu codelab'in başından sonuna kadar nasıl değişeceğini gösterir.

![Ekran Resmi 2022-01-12 23 18 08](https://user-images.githubusercontent.com/70329389/149215348-6e2924a7-b14d-4a1f-9348-7b67cf31b654.png)

## <a name="a"></a>Aşama 1 : Android'in Stil Sistemi

Android, uygulamanızdaki tüm view'ların görünümünü kontrol etmenizi sağlayan zengin bir stil sistemi sağlar. Stili etkilemek için temaları, stilleri ve görünüm özelliklerini kullanabilirsiniz. Aşağıda gösterilen şema, her bir şekillendirme yönteminin önceliğini özetlemektedir. Piramit diyagramı, sistem tarafından uygulanan şekillendirme yöntemlerinin aşağıdan yukarıya doğru sırasını gösterir. Örneğin, temada metin boyutunu ayarlarsanız ve ardından görünüm niteliklerinde metin boyutunu farklı ayarlarsanız, görünüm nitelikleri tema stilini geçersiz kılar.

![image](https://user-images.githubusercontent.com/70329389/149216959-b6382115-efcb-410e-b206-d8494852758c.png)

#### Nitelikleri görüntüle

- Her görünüm için öznitelikleri açıkça ayarlamak için görünüm özniteliklerini kullanın. (Stiller gibi görünüm nitelikleri yeniden kullanılamaz.)
- Stiller veya temalar aracılığıyla ayarlanabilen her özelliği kullanabilirsiniz.

margins, paddings veya constraints gibi özel veya tek seferlik tasarımlar için kullanın.

