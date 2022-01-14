# <a name="1"></a>Karmaşık lifecycle durumlarını yönetme

- [Lifecycle hatalarından kaçının](#a)
- [Android lifecycle kütüphanesini kullanın](#b)
- [Uygulama kapanması simülasyonu yapın ve onSaveInstanceState() kullanın](#c)
- [Konfigürasyon değişikliklerini keşfedin](#d)

Bu dökümanda, önceki dökümandan DessertClicker uygulamasını genişleteceksiniz. Bir arka plan zamanlayıcı ekleyecek, ardından uygulamayı [Android lifecycle kütüphanesini](https://developer.android.com/topic/libraries/architecture/lifecycle) kullanacak şekilde dönüştüreceksiniz.

![app](https://developer.android.com/codelabs/kotlin-android-training-complex-lifecycle/img/5f5803e64f578dd5.png)

## <a name="a"></a>Aşama 1 : Lifecycle hatalarından kaçının

Önceki dökümanda, çeşitli lifecycle callbackleri override ederek ve sistem bu callbackleri çağırdığında log'a kaydederek activity'yi ve fragment lifecyclelarını nasıl gözlemleyeceğinizi öğrendiniz. Bu aşamada, DessertClicker uygulamasında lifecycle görevlerini yönetmenin daha karmaşık bir örneğini keşfedeceksiniz. Her saniye, çalıştığı saniye sayısıyla birlikte bir log ifadesi yazdıran bir zamanlayıcı kullanacaksınız.

### Adım 1:  DessertTimer'ı kurun

1. Son dökümandan DessertClicker uygulamasını açın. (Uygulamaya sahip değilseniz, DessertClickerLogs'u buradan indirebilirsiniz.)
2. **Project** görünümünde **Java > com.example.android.dessertclicker**'ı genişletin ve `DessertTimer.kt`'yi açın. Şu anda tüm kodun yorumlandığına dikkat edin, bu nedenle uygulamanın bir parçası olarak çalışmayacaktır.
3. Editör penceresindeki tüm kodu seçin. **Code > Comment with Line Comment**'i seçin veya `Control+/` (Mac'te `Command+/`) tuşlarına basın. Bu komut, dosyadaki tüm kodun yorumunu kaldırır. (Android Studio, siz uygulamayı yeniden oluşturana kadar unresolved reference hataları gösterebilir.)
4. `DessertTimer` class'ının, zamanlayıcıyı başlatan ve durduran `startTimer()` ve `stopTimer()` öğelerini içerdiğine dikkat edin. `startTimer()` çalışırken, zamanlayıcı her saniye, sürenin çalıştığı toplam saniye sayısıyla birlikte bir log mesajı yazdırır. `stopTimer()` metodu, sırayla, zamanlayıcıyı ve log ifadelerini durdurur.

>**Not:** `DessertTimer` class'ı, ilişkili `Runnable` ve `Handler` classlarıyla birlikte zamanlayıcı için bir background thread kullanır. Bu döküman için bunları bilmenize gerek yok (yine de daha sonraki bir dökümanda threadler hakkında daha fazla bilgi edineceksiniz). İlgileniyorsanız daha fazla ayrıntı için [Processes and threads](https://developer.android.com/guide/components/processes-and-threads)'e bakın.

5.` MainActivity.kt`'yi açın. Class'ın en üstünde, `dessertsSold` değişkeninin hemen altında, zamanlayıcı için bir değişken ekleyin:

```kotlin

private lateinit var dessertTimer: DessertTimer

```

6. `onCreate()` öğesine gidin ve `setOnClickListener()` çağrısından hemen sonra yeni bir `DessertTimer` nesnesi oluşturun:

```kotlin

dessertTimer = DessertTimer()

```

Artık bir dessert timer nesnesine sahip olduğunuza göre, **yalnızca** activity ekrandayken çalışmasını sağlamak için zamanlayıcıyı nereden başlatmanız ve durdurmanız gerektiğini düşünün. Sonraki adımlarda birkaç seçeneğe bakacaksınzı.

### Adım 2: Zamanlayıcıyı başlatın ve durdurun

onStart() metodu, activity görünür hale gelmeden hemen önce çağrılır. onStop() metodu, activity görünür olmayı bıraktıktan sonra çağrılır. Bu gcallbackler, zamanlayıcının ne zaman başlatılacağı ve durdurulacağı konusunda iyi adaylar gibi görünüyor.

1. `MainActivity` class'ında, `onStart()` callback'inde zamanlayıcıyı başlatın:


```kotlin

override fun onStart() {
   super.onStart()
   dessertTimer.startTimer()

   Timber.i("onStart called")
}

```

2. `onStop()`'ta zamanlayıcıyı durdurun.

```kotlin

override fun onStop() {
   super.onStop()
   dessertTimer.stopTimer()

   Timber.i("onStop Called")
}

```

3. Uygulamayı derleyin ve çalıştırın. Android Studio'da **Logcat** bölmesine tıklayın. Logcat arama kutusuna, hem `MainActivity` hem de `DessertTimer` classlarına göre filtre uygulayacak `dessertclicker` yazın. Uygulama başladığında, zamanlayıcının da hemen çalışmaya başladığına dikkat edin.

![Logcat](https://developer.android.com/codelabs/kotlin-android-training-complex-lifecycle/img/a399e0fea061361e.png)

4. **Geri** butonuna tıklayın ve zamanlayıcının tekrar durduğuna dikkat edin. Zamanlayıcı durur çünkü hem activity hem de kontrol ettiği zamanlayıcı yok edilmiştir.
5. Uygulamaya geri dönmek için recents ekranını kullanın. Logcat'te zamanlayıcının 0'dan yeniden başladığına dikkat edin.
6. **Paylaş** düğmesini tıklayın. Logcat'te zamanlayıcının hala çalıştığına dikkat edin.

![share button](https://developer.android.com/codelabs/kotlin-android-training-complex-lifecycle/img/e2319779260eb5ee.png)

7. **Home** butonuna tıklayın. Logcat'te zamanlayıcının çalışmayı durdurduğuna dikkat edin.
8. Uygulamaya geri dönmek için recents ekranını kullanın. Logcat'te, `onStart()` metodunda `startTimer()` öğesini çağırdığımız için zamanlayıcının kaldığı yerden yeniden başladığına dikkat edin.
9. `MainActivity`'de, `onStop()` metodunda `stopTimer()` çağrısını yorumlayın. `stopTimer()`'ın yorumlanması, `onStart()`'ta bir işlemi başlattığınız, ancak `onStop()`'ta tekrar durdurmayı unuttuğunuz durumu gösterir.
10. Uygulamayı derleyin ve çalıştırın ve zamanlayıcı başladıktan sonra Home butonuna tıklayın. Uygulama arka planda olmasına rağmen zamanlayıcı çalışıyor ve sürekli olarak sistem kaynaklarını kullanır. Zamanlayıcının devam etmesi, telefonunuzdaki bilgi işlem kaynaklarını gereksiz yere kullanabilir ve muhtemelen istediğiniz davranışı kullanamayabilir.

Genel pattern, bir callback'de bir şey kurduğunuzda veya başlattığınızda, karşılık gelen bir callback'de o şeyi durdurur veya kaldırırsınız. Bu şekilde, artık ihtiyaç duyulmadığında herhangi bir şeyin çalışmasını önlersiniz.

>**Not:** Müzik çalma gibi bazı durumlarda, şeyi çalışır durumda tutmak istersiniz. Bir şeyi çalışır durumda tutmanın uygun ve etkili yolları vardır, ancak bunlar bu dersin kapsamı dışındadır.

11. Zamanlayıcıyı durdurduğunuz yerde `onStop()`'daki yorumun kaldırın.
12. `onStart()` öğesinden `onCreate()` öğesine `startTimer()` çağrısını kesip yapıştırın. Bu değişiklik, bir kaynağı initialize etmek için `onCreate()` ve başlatmak için `onStart()` kullanmak yerine `onCreate()` içinde bir kaynağı hem initialize ettiğiniz hem de başlattığınız durumu gösterir.
13. Uygulamayı derleyin ve çalıştırın. Beklediğiniz gibi zamanlayıcının çalışmaya başladığına dikkat edin.
14. Uygulamayı durdurmak için Home'a tıklayın. Zamanlayıcı, beklediğiniz gibi çalışmayı durdurur.
15. Uygulamaya geri dönmek için recents ekranını kullanın. Bu durumda zamanlayıcının yeniden başlamadığına dikkat edin, çünkü `onCreate()` yalnızca uygulama başladığında çağrılır—bir uygulama ön plana döndüğünde çağrılmaz.

Hatırlanması gereken önemli noktalar:

- Bir lifecycle callback'inde bir kaynak kurduğunuzda (setup), kaynağı da parçalayın (teardown).
- İlgili metotlarda setup ve teardown yapın.
- `onStart()`'ta bir şey setup ederseniz, durdurun veya `onStop()`'ta tekrar teardown edin.

![lifecycle diagram](https://developer.android.com/codelabs/kotlin-android-training-complex-lifecycle/img/f6b25a71cec4e401.png)

## <a name="b"></a>Aşama 2 : Android lifecycle kütüphanesini kullanın
## <a name="c"></a>Aşama 3 : Uygulama kapanması simülasyonu yapın ve onSaveInstanceState() kullanın
## <a name="d"></a>Aşama 4 : Konfigürasyon değişikliklerini keşfedin
