package com.begumyolcu.dessertclickerlogs

//import android.os.Handler
//import timber.log.Timber
//import android.os.Looper
///**
// * Bu, başlatabileceğiniz veya durdurabileceğiniz bir zamanlayıcıyı temsil eden bir class'tır.
// * secondsCount, her saniyede başladığından beri kaç saniye olduğunu gösterir.
// *
// * -----
// *
// * Handler ve Runnable bu dersin kapsamı dışındadır. Bunun nedeni kısmen, daha sonraki bir derste
// * ele alınacak olan karmaşık bir konu olan threading ile ilgilenmeleridir.
// * Şimdi daha fazlasını öğrenmek istiyorsanız, thread oluşturma hakkında Android Geliştirici
// * belgelerine göz atabilirsiniz:
// *
// * https://developer.android.com/guide/components/processes-and-threads
// *
// */
//class DessertTimer {
//
//    // Zamanlayıcı başladığından beri sayılan saniye sayısı
//    var secondsCount = 0
//
//    /**
//     * [Handler], bir ileti kuyruğunu ([android.os.Message]s olarak bilinir) veya
//     * eylemleri ([Runnable]s olarak bilinir) işlemek için tasarlanmış bir class'tır.
//     */
//    private var handler = Handler(Looper.getMainLooper())
//    private lateinit var runnable: Runnable
//
//
//    fun startTimer() {
//        // Bir log yazdıran ve saniye sayacını artıran runnable eylemi oluşturun
//        runnable = Runnable {
//            secondsCount++
//            Timber.i("Timer is at : $secondsCount")
//            // postDelayed, Eylemi, Handler'ın döngüye girdiği eylem sırasına yeniden ekler.
//            // DelayMillis parametresi, handler'a runnable dosyayı 1 saniyede (1000ms)
//            // çalıştırmasını söyler.
//            handler.postDelayed(runnable, 1000)
//        }
//
//        // Zamanlayıcıyı ilk başlatan şey budur
//        handler.postDelayed(runnable, 1000)
//
//        // Handler'ın üzerinde çalıştığı Thread'in Looper adlı bir class tarafından belirlendiğini unutmayın.
//    }
//
//    fun stopTimer() {
//        // Runnable'ın bekleyen tüm gönderilerini handler'ın kuyruğundan kaldırarak zamanlayıcıyı
//        // etkin bir şekilde durdurur
//        handler.removeCallbacks(runnable)
//    }
//}