# Material Design

- [Floating Action Button (FAB) Ekleyin](#a)
- [Material Design Dünyasında Stil Kullanın](#b)
- [Toolbar Temasını Değiştirin](#c)
- [Dimension'ları Kullanın](#d)
- [Renkleri Kullanın](#e)


GDG-finder başlangıç uygulaması, bu kursta şimdiye kadar öğrendiğiniz her şeyin üzerine inşa edilmiştir.

Uygulama, üç ekran yerleştirmek için ConstraintLayout'u kullanır. Ekranlardan ikisi, Android'de renkleri ve metni keşfetmek için kullanacağınız layout dosyalarıdır.

Üçüncü ekran bir GDG bulucudur. GDG'ler veya Google Geliştirici Grupları, Android dahil olmak üzere Google teknolojilerine odaklanan geliştirici topluluklarıdır. Dünyanın dört bir yanındaki GDG'ler buluşmalara, konferanslara, Study Jam'lere ve diğer etkinliklere ev sahipliği yapıyor.

Bu uygulamayı geliştirirken, GDG'lerin gerçek listesi üzerinde çalışıyorsunuz. Bulucu ekranı, GDG'leri mesafeye göre sıralamak için cihazın konumunu kullanır.

Şanslıysanız ve bölgenizde bir GDG varsa, web sitesine göz atabilir ve etkinliklerine kaydolabilirsiniz! GDG etkinlikleri, diğer Android geliştiricileriyle tanışmak ve bu kursa uymayan sektördeki en iyi uygulamaları öğrenmek için harika bir yoldur.

Aşağıdaki ekran görüntüleri, uygulamanızın bu codelab'in başından sonuna kadar nasıl değişeceğini gösterir.

![Ekran Resmi 2022-01-14 10 02 12](https://user-images.githubusercontent.com/70329389/149465415-0aa06b39-e12c-48ed-b642-e34e1394e700.png)


## <a name="a"></a>Aşama 1 : Floating Action Button (FAB) Ekleyin

Bu görevde, GDG Finder uygulamasının ana ekranına kayan bir eylem düğmesi (FAB) eklersiniz.

FAB, kullanıcının ekranda yapması gereken ana şey olan birincil eylemi temsil eden büyük bir yuvarlak düğmedir. FAB, aşağıdaki soldaki ekran görüntüsünde gösterildiği gibi diğer tüm içeriğin üzerinde yüzer. Kullanıcı FAB'ye dokunduğunda, sağda gösterildiği gibi bir GDG listesine götürülür.

![Ekran Resmi 2022-01-14 10 06 01](https://user-images.githubusercontent.com/70329389/149465837-80d17f0c-3af9-4bdf-8ebe-08e87ce646a1.png)

#### Adım 1 : Home Fragment Layout'a bir FAB ekleyin

1. Bu codelab için başlangıç uygulaması olan [GDGFinderStyles](https://github.com/google-developer-training/android-kotlin-fundamentals-apps/tree/master/GDGFinderStyles) uygulamasını indirin ve çalıştırın. Önceki codelab'i yaptıysanız, o codelab'in son kodundan devam edebilirsiniz.
2. **build.gradle(Module: app)** dosyasında, Material kitaplığının dahil edildiğini doğrulayın. Material Tasarımı bileşenlerini kullanmak için bu kitaplığı eklemeniz gerekir. En son kitaplık sürümünü kullandığınızdan emin olun.

```
implementation 'com.google.android.material:material:1.2.0'
```

3. **res/layout/home_fragment.xml** dosyasını açın ve **Code** sekmesine geçin.

Şu anda, ana ekran layout'u, bir alt öğe olarak **ConstraintLayout** ile tek bir **ScrollView** kullanır. ConstraintLayout'a bir FAB eklediyseniz, FAB, ConstraintLayout'un içinde olur, tüm içeriğin üzerinde kaymaz ve ConstraintLayout'un geri kalanıyla birlikte kayar. FAB'yi mevcut layout'unuz üzerinde kaydırmanın bir yoluna ihtiyacınız var.

**CoordinatorLayout**, görünümleri üst üste yığmanıza izin veren bir görünüm grubudur. CoordinatorLayout herhangi bir süslü layout yeteneğine sahip olmasa da, bu uygulama için yeterlidir. ScrollView tam ekranı almalı ve FAB ekranın alt kenarına yakın yüzmelidir.

4. **home_fragment.xml** dosyasında, **ScrollView** çevresine bir **CoordinatorLayout** ekleyin.

```
<androidx.coordinatorlayout.widget.CoordinatorLayout
       android:layout_height="match_parent"
       android:layout_width="match_parent">
...

</androidx.coordinatorlayout.widget.CoordinatorLayout>
```
5. `<ScrollView>` öğesini `<androidx.core.widget.NestedScrollView>` ile değiştirin. Coordinator layout kaydırmayı bilir ve kaydırmanın doğru çalışması için kaydırma ile başka bir görünüm içinde **NestedScrollView** kullanmanız gerekir.

```
androidx.core.widget.NestedScrollView
```

6. **CoordinatorLayout**'un içine ve altına, **NestedScrollView**'ın altına bir **FloatingActionButton** ekleyin.

```
<com.google.android.material.floatingactionbutton.FloatingActionButton
       android:layout_width="wrap_content"
       android:layout_height="wrap_content"/>
```

7. Uygulamanızı çalıştırın. Sol üst köşede renkli bir nokta görüyorsunuz

![image](https://user-images.githubusercontent.com/70329389/149467533-4c3ba503-925f-4bcb-adf2-83897014c174.png)

8. Düğmeye dokunun. Düğmenin görsel olarak yanıt verdiğine dikkat edin.
9. Sayfayı kaydırın. Düğmenin yerinde kaldığına dikkat edin.

#### Adım 2 : FAB Button'unu Stillendirin
