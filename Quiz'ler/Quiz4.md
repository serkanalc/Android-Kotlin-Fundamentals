# Kotlin Fundamentals: Navigation Quiz

Bu Navigation aşaması quizinin tam çevirisidir. Aşağıdaki soruları çözdüyseniz [bu linkten](https://developer.android.com/courses/quizzes/kotlin-fundamentals-four/kotlin-fundamentals-four?authuser=6&continue=https%3A%2F%2Fdeveloper.android.com%2Fcourses%2Fpathways%2Fkotlin-fundamentals-four%3Fauthuser%3D6%23quiz-%2Fcourses%2Fquizzes%2Fkotlin-fundamentals-four%2Fkotlin-fundamentals-four)  cevapları gönderip başarı seviyenizi görebilir ve paylaşılabilir bir badge kazanabilirsiniz! Bu sınavı geçmek için en az 5 soruyu doğru cevaplamalısınız.

### 1. Fragment'larla ilgili aşağıdaki ifadelerden hangisi doğrudur?

- [ ] Bir Fragment birden fazla aktivitede kullanabilirsiniz.
- [ ] Bir aktivitenin birden fazla Fragment'ı olabilir.
- [ ] Bir layout dosyasında bir fragment ekleneceği yeri tanımlamak için tag kullanılır.
- [ ] Bir fragment sınıfını tanımladıktan sonra, fragment otomatik olarak aktivite_main.xml düzen dosyasına eklenir.

### 2. Projenizin navigation components' kullanmasını nasıl sağlarsınız?

- [ ] Her Activity sınıfının NavigationActivity sınıfından genişlettiğinden emin olun.
- [ ] Launch activity olarak NavigationController sınıfını kullanın.
- [ ] Android manifest dosyasına ekleyin.
- [ ] build.gradle dosyasına (module level) navigasyon-fragment-ktx ve navigasyon-ui-ktx için dependencies ekleyin.


### 3. NavHostFragment ile ilgili aşağıdaki ifadelerden hangileri doğrudur?

- [ ] Kullanıcı navigasyon graph'da tanımlanan hedefler arasında hareket ederken, NavHostFragment fragment'ları gerektiği gibi içeri ve dışarı değiştirir.
- [ ] Navigasyon graph'ını açmak için Proje görünümünde NavHostFragment'e tıklayabilirsiniz.
- [ ] NavHostFragment öğesini, android:name özniteliği androidx.navigation.fragment.NavHostFragment ekleyerek ana activity layout'una eklersiniz.
- [ ] Tek bir NavHostFragment alt sınıfı oluşturmanız ve farklı gezinme türlerini (düğme tıklamaları gibi) işlemek için onNavigate() yöntemini uygulamanız gerekir.

### 4. Bir menü için itemleri nerede tanımlarsınız?

- [ ] Menünün nerede gösterileceğine bağlıdır. navigation drawer menu için res > çekmece klasöründeki menu.xml dosyasındaki her menü öğesi için bir `<item>` etiketi ekleyin. Seçenekler menüsü için res > options klasöründeki menu.xml dosyasındaki her menü öğesi için bir `<item>` etiketi ekleyin.
- [ ] Menüyü görüntüleyen parça veya activity'nin layout dosyasında, her öğe için `<item>` etiketlerini içeren bir `<menu>` etiketi ekleyin.
- [ ] res > menü klasöründeki bir menu_name.xml dosyasında, her menü öğesi için bir `<item>` etiketi ekleyin. Her ayrı menü için ayrı XML dosyaları oluşturun.
- [ ] AndroidManifest.xml dosyasında, her menü için bir `<menu>` etiketi içeren bir `<menus>` etiketi ekleyin. Her `<menu>` etiketi için, her menü öğesi için bir `<item>` etiketi ekleyin.

### 5. Uygulamanıza bir navigation drawer eklemek için ne yapmanız gerekiyor? Projenizin bir gezinme grafiği olduğunu ve drawer için menü öğelerini zaten tanımladığınızı varsayabilirsiniz.

- [ ] İlgili layout dosyasında root view olarak DrawerLayout'u kullanın ve bu root view alt öğe olarak bir NavigationView ekleyin.
- [ ] İlgili layout dosyasında root view olarak relevant layout kullanın ve bu root view alt öğe olarak bir Navigasyon Görünümü ekleyin.
- [ ] Layout NavigationView öğesinde, app:menu niteliğini navigation drawer menu kaynak resource ID'e ayarlayın.
- [ ] Navigation graph XML dosyasında, navigation menüsünün bir ID'si olduğundan emin olun.

### 6. Bağımsız değişkenlerinizin tür açısından güvenli olduğundan emin olmak için Safe Args kullanmadan, Fragment A'dan Fragment B'ye bağımsız değişkenler iletirseniz, uygulama çalışırken uygulamanın çökmesine neden olabilecek aşağıdaki sorunlardan hangisi oluşabilir?

- [ ] Fragment B, Fragment A'nın kendisine göndermediği verileri ister.
- [ ] Fragment A, Fragment B'nin istemediği verileri gönderebilir.
- [ ] Fragment A, Fragment B'nin ihtiyaç duyduğundan farklı türde veriler gönderebilir. Örneğin, A fragment'ı bir string gönderebilir, ancak B fragment'ı bir integer ister.
- [ ] Fragment A, Fragment B isteklerinden farklı bağımsız değişken adları kullanır.
  
### 7. Safe Args Gradle eklentisi ne işe yarar?

- [ ] Hedefler ve eylemler için belirtilen bağımsız değişkenlere tür açısından güvenli erişim için basit nesne ve oluşturucu sınıfları oluşturur
- [ ] Fragment'lar arasında parametrelerin geçişini basitleştirmek için düzenleyebileceğiniz Navigation sınıfları oluşturur
- [ ] Kodunuzda Android Bundle'ları kullanmanıza gerek kalmayacak şekilde yapılır.
- [ ] Gezinme grafiğinde tanımladığınız her eylem için bir yöntem oluşturur.
- [ ] Bir Paketten veri çıkarırken kodunuzun yanlış anahtarı kullanmasını önler.
  
### 8. Implicit intent nedir ?

- [ ] Uygulamanızın bir bölümünde başlattığı ve başka bir bölümde tamamladığı bir görev.
- [ ] Kullanıcıya seçilen bir iletişim kutusunu göstererek uygulamanızın her zaman tamamladığı bir görev.
- [ ] Hangi uygulamanın veya etkinliğin görevi yerine getireceğini bilmeden uygulamanızın başlattığı bir görev.
- [ ] implicit intent, navigasyon graph'ında hedefler arasında ayarladığınız eylemle aynı şeydir.






