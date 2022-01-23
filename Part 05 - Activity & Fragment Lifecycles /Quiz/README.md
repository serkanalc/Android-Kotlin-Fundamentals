# Kotlin Fundamentals: Activity and Fragment lifecycles

Bu Quiz Repository and WorkManager aşaması quizinin tam çevirisidir. Aşağıdaki soruları çözdüyseniz [bu linkten](https://developer.android.com/courses/quizzes/kotlin-fundamentals-five/kotlin-fundamentals-five?continue=https%3A%2F%2Fdeveloper.android.com%2Fcourses%2Fpathways%2Fkotlin-fundamentals-five%23quiz-%2Fcourses%2Fquizzes%2Fkotlin-fundamentals-five%2Fkotlin-fundamentals-five)  cevapları gönderip başarı seviyenizi görebilir ve paylaşılabilir bir badge kazanabilirsiniz! Bu sınavı geçmek için en az 5 soruyu doğru cevaplamalısınız.

1. Aşağıdakilerden hangisi bir activity lifecycle state'i (durumu) değildir?
- [ ] Started
- [ ] Waiting
- [ ] Created
- [ ] Destroyed

2. Bir activity'yi görünür kılmak için hangi lifecycle metodu çağrılır?
- [ ] onPause()
- [ ] onVisible()
- [ ] onStart()
- [ ] onDestroy()

3. Bir activity'ye odağı vermek için hangi lifecycle metodu çağrılır?
- [ ] onResume()
- [ ] onVisible()
- [ ] onStart()
- [ ] onFocus() 

4. Uygulamanız, ekranda görüntülenmesi için yoğun hesaplama gerektiren bir fizik simülasyonu içeriyor. Ardından kullanıcı bir telefon görüşmesi alır. Aşağıdakilerden hangisi doğrudur?
- [ ] Telefon görüşmesi sırasında fizik simülasyonunda nesnelerin konumlarını hesaplamaya devam etmelisiniz.
- [ ] Telefon görüşmesi sırasında, fizik simülasyonunda nesnelerin konumlarını hesaplamayı bırakmalısınız.

5. Uygulama ekranda değilken simülasyonu duraklatmak için hangi lifecycle metodunu override etmelisiniz?
- [ ] onDestroy()
- [ ] onPause()
- [ ] onStop()
- [ ] onSaveInstanceState()

6. Jetpack lifecycle kütüphanesi aracılığıyla bir class'ı lifecycle-aware (lifecycle'a duyarlı) hale getirmek için class hangi interface'i uygulamalıdır?
- [ ] Lifecycle
- [ ] LifecycleOwner 
- [ ] Lifecycle.Event 
- [ ] LifecycleObserver
