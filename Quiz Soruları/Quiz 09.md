# Kotlin Fundamentals: Repository and WorkManager

Bu Quiz Repository and WorkManager aşaması quizinin tam çevirisidir. Aşağıdaki soruları çözdüyseniz [bu linkten](https://developer.android.com/courses/quizzes/kotlin-fundamentals-nine/kotlin-fundamentals-nine?continue=https%3A%2F%2Fdeveloper.android.com%2Fcourses%2Fpathways%2Fkotlin-fundamentals-nine%23quiz-%2Fcourses%2Fquizzes%2Fkotlin-fundamentals-nine%2Fkotlin-fundamentals-nine)  cevapları gönderip başarı seviyenizi görebilir ve paylaşılabilir bir badge kazanabilirsiniz! Bu sınavı geçmek için en az 5 soruyu doğru cevaplamalısınız.

#### Q1. Android Mimari Bileşenlerindeki hangi bileşen, çevrimdışı önbelleği güncel tutmaktan ve ağdan veri almaktan sorumludur? 
- [ ] ViewModel
- [ ] LiveData
- [ ] Repository
- [ ] Activities


#### Q2. Çevrimdışı cache etme için yapılandırılmış verileri cihaz dosya sistemine kaydetmenin en iyi yolu nedir?
- [ ] Room
- [ ] Files
- [ ] Shared preferences
- [ ] Retrofit caching

#### Q3. Transformations.map(), bir LiveData'yı diğer `_____________`'ya/ye dönüştürür.
- [ ] ViewModel
- [ ] LiveData
- [ ] Repository
- [ ] DAO nesnesi

#### Q4. Çevrimdışı cache etme uygularken, aşağıdaki stratejilerden hangisi separation of concerns ilkesine iyi bir örnektir?
- [ ] Ağ, domain ve veritabanı nesnelerini temsil etmek için ayrı classlar oluşturun.
- [ ] Ağ, domain ve veritabanı nesnelerini temsil etmek için tek class oluşturun.
- [ ] Ağ ve domain nesnelerini temsil etmek için tek bir class ve veritabanı nesnesini temsil etmek için başka bir class oluşturun.
- [ ] Ağ nesnesini temsil etmek için tek bir class ve veritabanı ve domain nesnelerini temsil etmek için başka bir sınıf oluşturun.

#### Q5. Aşağıdakilerden hangisi WorkRequest class'ının somut uygulamalarıdır? Geçerli olanların tümünü seçin.
(Uygun gördüğünüz kadar çok cevap seçin.)
- [ ] ListenableWorker class'ı, runtime görevleri içindir
- [ ] OneTimeWorkRequest class'ı tek seferlik görevler içindir.
- [ ] PeriodicWorkRequest class'ı, periyodik işler, aralıklarla tekrar eden işler içindir.
- [ ] RxWorker class'ı, tek seferlik görevler için birlikte çalışabilirlik (interoperability) Worker uygulamasıdır.

#### Q6. WorkManager, API 23 ve sonraki sürümlerde arka plan görevini schedule etmek için aşağıdaki classlardan hangisini kullanır
- [ ] Sadece JobScheduler
- [ ] BroadcastReceiver ve AlarmManager
- [ ] AlarmManager ve JobScheduler
- [ ] Scheduler ve BroadcastReceiver

#### Q7. Bir WorkRequest'e constraintler eklemek için hangi API'yi kullanmalısınız?
- [ ] setConstraints()
- [ ] addConstraints()
- [ ] setRequiresCharging(true)
- [ ] addConstraintsToWorkRequest(true)
