# Kotlin Fundamentals: Databases & RecyclerView

Bu Quiz Databases & RecyclerView aşaması quizinin tam çevirisidir. Aşağıdaki soruları çözdüyseniz [bu linkten](https://developer.android.com/courses/quizzes/kotlin-fundamentals-seven/kotlin-fundamentals-seven?continue=https%3A%2F%2Fdeveloper.android.com%2Fcourses%2Fpathways%2Fkotlin-fundamentals-seven%23quiz-%2Fcourses%2Fquizzes%2Fkotlin-fundamentals-seven%2Fkotlin-fundamentals-seven)  cevapları gönderip başarı seviyenizi görebilir ve paylaşılabilir bir badge kazanabilirsiniz! Bu sınavı geçmek için en az 7 soruyu doğru cevaplamalısınız.

#### Q1. Veritabanınızın beklendiği gibi çalıştığını nasıl doğrulayabilirsiniz? (Uygun gördüğünüz kadar çok cevap seçin.)

- [ ] Instrumented unit testler yazın.
- [ ] Uygulama verileri görüntüleyene kadar bekleyin.
- [ ] DAO interface'indeki metotlara yapılan çağrıları, Entity sınıfındaki eşdeğer metotlara yapılan çağrılarla değiştirin.
- [ ] Room kütüphanesi tarafından sağlanan verifyDatabase() fonksiyonunu çalıştırın.

#### Q2. Bir class'ın bir Room veritabanında depolanacak bir Entity'yi temsil ettiğini nasıl belirtirsiniz?

- [ ] Class'ın DatabaseEntity'yi extend etmesini sağlayın.
- [ ] @Entity ile class'a annotation ekleyin.
- [ ] @Database ile class'a annotation ekleyin.
- [ ] Class'ın RoomEntity'yi extend etmesini sağlayın ve ayrıca class'a @Room ile annotation ekleyin.

#### Q3. Aşağıdakilerden hangisi coroutinler için doğru değildir?

- [ ] Non blockinglerdir.
- [ ] Asenkron olarak çalışırlar.
- [ ] Main thread dışındaki threadlerde çalışabilirler.
- [ ] Her zaman uygulamaların daha hızlı çalışmasını sağlar.
- [ ] Exceptionları iletirler.
- [ ] Doğrusal kod olarak yazılabilir ve okunabilirler.

#### Q4. Aşağıdaki ifadelerden hangisi doğru değildir?

- [ ] Yürütme engellendiğinde, engellenen thread üzerinde başka hiçbir iş yürütülemez.
- [ ] Yürütme askıya alındığında, thread, offload edilen işin tamamlanmasını beklerken başka işler yapabilir.
- [ ] Askıya alma, birçok eşzamanlı işlemi desteklerken bellekten engellemeye (blocking) karşı tasarruf sağlar.
- [ ] Engellenmiş (blocked) veya askıya alınmış (suspended) olsun, yürütme devam etmeden önce coroutine sonucunu bekliyor.

#### Q5. RecyclerView öğeleri nasıl görüntüler?

- [ ] Öğeleri bir liste veya grid'de görüntüler.
- [ ] Dikey veya yatay olarak kaydırılabilir.
- [ ] Tabletler gibi daha büyük cihazlarda çapraz olarak kayar.
- [ ] Kullanım durumu için bir liste veya grid yeterli olmadığında özel layoutlara izin verir.

#### Q6. DiffUtil'i kullanmak için aşağıdakilerden hangisi gereklidir? (Uygun gördüğünüz kadar çok cevap seçin.)

- [ ] ItemCallback class'ını genişletin.
- [ ] areItemsTheSame()'i override edin.
- [ ] areContentsTheSame()'i override edin.
- [ ] Öğeler arasındaki farkları izlemek için data binding kullanın.

#### Q7. Aşağıdakilerden hangileri Android tarafından sağlanan layout managerlardır? (Uygun gördüğünüz kadar çok cevap seçin.)

- [ ] LinearLayoutManager
- [ ] GridLayoutManager
- [ ] CircularLayoutManager
- [ ] StaggeredGridLayoutManager

#### Q8. RecyclerView'daki öğelerin tıklamalara yanıt vermesini sağlamak için `android:onClick` özelliğini nereye eklersiniz?

- [ ] RecyclerView'ı görüntüleyen layout dosyasında, öğeye ekleyin.
- [ ] Satırdaki bir öğenin layout dosyasına ekleyin. Tüm öğenin tıklanabilir olmasını istiyorsanız, onu satırdaki öğeleri içeren parent görünüme ekleyin.
- [ ] android:onClick özelliğini RecyclerView.Adapter'a ekleyin.
- [ ] Her zaman MainActivity için layout dosyasına ekleyin.

#### Q9. ViewHolder ile ilgili aşağıdaki ifadelerden hangisi doğrudur?

- [ ] Bir adapter, headerları ve çeşitli veri türlerini tutmak için birden çok ViewHolder class'ını kullanabilir.
- [ ] Veriler için tam olarak bir view holder'ınız ve bir header için bir view holder'ınız olabilir.
- [ ] Bir RecyclerView, birden çok header türünü destekler, ancak verilerin tek tip olması gerekir.
- [ ] Bir header eklerken, header'ı doğru konuma eklemek için RecyclerView subclassını kullanırsınız.
