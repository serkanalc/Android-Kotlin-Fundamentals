# Kotlin Fundamentals: Architecture components

Bu Quiz Architecture components aşaması quizinin tam çevirisidir. Aşağıdaki soruları çözdüyseniz [bu linkten](https://developer.android.com/courses/quizzes/kotlin-fundamentals-six/kotlin-fundamentals-six?continue=https%3A%2F%2Fdeveloper.android.com%2Fcourses%2Fpathways%2Fkotlin-fundamentals-six%23quiz-%2Fcourses%2Fquizzes%2Fkotlin-fundamentals-six%2Fkotlin-fundamentals-six) cevapları gönderip başarı seviyenizi görebilir ve paylaşılabilir bir badge kazanabilirsiniz! Bu sınavı geçmek için en az 9 soruyu doğru cevaplamalısınız.

1. Bir cihaz konfigürasyon değişikliği sırasında veri kaybını önlemek için uygulama verilerini bu seçeneklerden hangisinde depolamalısınız?
- [ ] ViewModel
- [ ] LiveData
- [ ] Fragment
- [ ] Fragment

2. Bir ViewModel asla fragmentlara, activitylere veya viewlara referans içermemelidir. Doğru mu, yanlış mı?
- [ ] Doğru
- [ ] Yanlış

3. Bir ViewModel ne zaman yok edilir?
- [ ] İlişkili UI controller, cihaz oryantasyon değişikliği sırasında yok edildiğinde ve yeniden oluşturulduğunda.
- [ ] Bir konfigürasyon değişikliği olduğunda.
- [ ] İlişkili UI controller bittiğinde (bir activity'yse) veya ayrıldığında (detach) (bir fragment'sa).
- [ ] onCleared() callback'i çağrıldığında.

4. Bir ViewModel'de depolanan LiveData'yı, harici nesnelerin verileri güncellemeden okuyabilmesi için nasıl kapsüllersiniz (encapsulate)?
- [ ] ViewModel içinde, özelliğin veri türünü LiveData olarak değiştirin ve private yapın. MutableLiveData türünde salt okunur (read-only) bir özelliği ortaya çıkarmak için bir destek özelliği (backing property) kullanın.
- [ ] ViewModel içinde, özelliğin veri türünü MutableLiveData olarak değiştirin ve private yapın. LiveData türünde salt okunur (read-only) bir özelliği ortaya çıkarmak için bir destek özelliği (backing property) kullanın.
- [ ] UI controller'ın içinde, özelliğin veri türünü MutableLiveData olarak değiştirin. LiveData türünde salt okunur (read-only) bir özelliği ortaya çıkarmak için bir destek özelliği (backing property) kullanın.
- [ ] ViewModel içinde, özelliğin veri türünü LiveData olarak değiştirin.

5. UI controller aşağıdaki durumlardan hangisindeyse, LiveData bir UI controller'ı (bir fragment gibi) günceller?
- [ ] Resumed
- [ ] Arkaplanda
- [ ] Paused
- [ ] Stopped

6. LiveData observer pattern'ında, gözlemlenebilir (observable) öğe nedir (gözlenen nedir)?
- [ ] Observer metodu
- [ ] LiveData nesne instance'ındaki veriler
- [ ] UI controller
- [ ] ViewModel

7. Listener bindingleri ile ilgili aşağıdaki ifadelerden hangisi doğru DEĞİLDİR?
- [ ] Listener bindingleri, bir event gerçekleştiğinde çalışan binding ifadeleridir.
- [ ] Listener bindingleri, Android Gradle eklentisinin tüm sürümleriyle çalışır.
- [ ] Listener bindingleri, lambda ifadeleri olarak yazılır.
- [ ] Listener bindingleri, metot referanslarına benzer, ancak rastgele data binding ifadeleri çalıştırmanıza izin verir.

8. Uygulamanızın bu stirng kaynağını içerdiğini varsayın: Merhaba %s. Aşağıdakilerden hangisi data binding ifadesini kullanarak string'i biçimlendirmek için doğru syntax'tır?
- [ ] android:text= "@{@string/generic_name(user.name)}"
- [ ] android:text= "@{string/generic_name(user.name)}"
- [ ] android:text= "@{@generic_name(user.name)}"
- [ ] android:text= "@{@string/generic_name,user.name}"

9. Bir listener-binding ifade ne zaman değerlendirilir ve çalıştırılır?
- [ ] LiveData tarafından tutulan veriler değiştirildiğinde
- [ ] Bir activity, bir konfigürasyon değişikliği ile yeniden oluşturulduğunda
- [ ] onClick() gibi bir event meydana geldiğinde
- [ ] Activity arka plana geçtiğinde

10. Transformations.map() metodu, LiveData üzerinde veri manipülasyonları gerçekleştirmenin kolay bir yolunu sağlar ve ___ döndürür.
- [ ] Bir ViewModel nesnesi
- [ ] Bir LiveData nesnesi
- [ ] Bir formatlanmış string
- [ ] Bir RoomDatabase nesnesi

11. Transformations.map() metodunun parametreleri nelerdir?
- [ ] LiveData'da depolanan değere uygulanacak bir kaynak LiveData ve bir fonksiyon
- [ ] Yalnızca bir kaynak LiveData
- [ ] Parametre yok
- [ ] ViewModel ve uygulanacak bir fonksiyon

12. Transformations.map() metoduna iletilen lambda fonksiyonu hangi thread'de yürütülür?
- [ ] Main (ana) thread
- [ ] Background (arkaplan) thread
- [ ] UI thread
- [ ] Bir coroutine'de
