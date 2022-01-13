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

#### View Attributes

- Her görünüm için öznitelikleri açıkça ayarlamak için görünüm özniteliklerini kullanın. (Stiller gibi görünüm nitelikleri yeniden kullanılamaz.)
- Stiller veya temalar aracılığıyla ayarlanabilen her özelliği kullanabilirsiniz.

margins, paddings veya constraints gibi özel veya tek seferlik tasarımlar için kullanın.

#### Styles

- Yazı tipi boyutu veya renkleri gibi yeniden kullanılabilir stil bilgilerinin bir koleksiyonunu oluşturmak için bir stil kullanın.
- Uygulamanız genelinde kullanılan küçük ortak tasarım kümelerini bildirmek için idealdir.

Varsayılan stili geçersiz kılarak birkaç görünüme bir stil uygulayın. Örneğin, stili tutarlı başlıklar veya bir dizi düğme oluşturmak için bir stil kullanın.

#### Default Style

- Bu, Android sistemi tarafından sağlanan varsayılan stildir.

#### Themes

- Tüm uygulamanız için renkleri tanımlamak üzere bir tema kullanın.
- Tüm uygulama için varsayılan yazı tipini ayarlamak için bir tema kullanın.
- Metin görünümleri veya radyo düğmeleri gibi tüm görünümlere uygulayın.
- Tüm uygulama için tutarlı bir şekilde uygulayabileceğiniz özellikleri yapılandırmak için kullanın.

#### TextAppearance

- Yalnızca fontFamily gibi metin öznitelikleriyle stil oluşturmak için.

Android bir görünüme stil verdiğinde, özelleştirebileceğiniz bir temalar, stiller ve nitelikler kombinasyonu uygular. Nitelikler her zaman bir stilde veya temada belirtilen her şeyin üzerine yazar. Ve stiller her zaman bir temada belirtilen her şeyin üzerine yazar.

Aşağıdaki ekran görüntüleri, açık tema (solda) ve koyu tema (sağda) ile GDG bulucu uygulamasını ve ayrıca özel yazı tipi ve başlık boyutlarıyla gösterir. Bu, birkaç şekilde uygulanabilir ve bunlardan bazılarını bu kod laboratuvarında öğreneceksiniz.

![Ekran Resmi 2022-01-13 03 35 32](https://user-images.githubusercontent.com/70329389/149244987-4295eb92-ee95-4fc2-8fe4-f97fd1dc79fc.png)




