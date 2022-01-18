package com.begumyolcu.dessertclickerlogs

import android.content.ActivityNotFoundException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.core.app.ShareCompat
import androidx.databinding.DataBindingUtil
import com.begumyolcu.dessertclickerlogs.databinding.ActivityMainBinding
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    private var revenue = 0
    private var dessertsSold = 0

    // Tüm viewları içerir
    private lateinit var binding: ActivityMainBinding

    /** Dessert Verileri **/

    /**
     * Bir tatlıyı (dessert) temsil eden basit data class. Resimle ilişkili kaynak id'si integer'ını,
     * satıldığı fiyatı ve tatlının ne zaman üretilmeye başladığını belirleyen startProductionAmount
     * değerini içerir.
     */
    data class Dessert(val imageId: Int, val price: Int, val startProductionAmount: Int)

    // Tüm tatlıların üretilmeye başladıkları zamana göre bir listesini oluşturun.
    private val allDesserts = listOf(
        Dessert(R.drawable.cupcake, 5, 0),
        Dessert(R.drawable.donut, 10, 5),
        Dessert(R.drawable.eclair, 15, 20),
        Dessert(R.drawable.froyo, 30, 50),
        Dessert(R.drawable.gingerbread, 50, 100),
        Dessert(R.drawable.honeycomb, 100, 200),
        Dessert(R.drawable.icecreamsandwich, 500, 500),
        Dessert(R.drawable.jellybean, 1000, 1000),
        Dessert(R.drawable.kitkat, 2000, 2000),
        Dessert(R.drawable.lollipop, 3000, 4000),
        Dessert(R.drawable.marshmallow, 4000, 8000),
        Dessert(R.drawable.nougat, 5000, 16000),
        Dessert(R.drawable.oreo, 6000, 20000)
    )
    private var currentDessert = allDesserts[0]

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Viewlara referans almak için Data Binding'i kullanın
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.dessertButton.setOnClickListener {
            onDessertClicked()
        }

        // TextView'ları doğru değerlere ayarlayın
        binding.revenue = revenue
        binding.amountSold = dessertsSold

        // Doğru tatlının gösterildiğinden emin olun
        binding.dessertButton.setImageResource(currentDessert.imageId)
    }

    /**
     * Tatlıya tıklandığında puanı günceller. Muhtemelen yeni bir tatlı gösterir.
     */
    private fun onDessertClicked() {

        // Puanı güncelle
        revenue += currentDessert.price
        dessertsSold++

        binding.revenue = revenue
        binding.amountSold = dessertsSold

        // Sonraki tatlıyı göster
        showCurrentDessert()
    }

    /**
     * Hangi tatlıyı göstereceğinizi belirleyin.
     */
    private fun showCurrentDessert() {
        var newDessert = allDesserts[0]
        for (dessert in allDesserts) {
            if (dessertsSold >= dessert.startProductionAmount) {
                newDessert = dessert
            }
            // Tatlı listesi startProductionAmount'a göre sıralanır. Daha fazla tatlı sattıkça,
            // startProductionAmount tarafından belirlenen daha pahalı tatlılar üretmeye
            // başlayacaksınız. "startProductionAmount"un satılandan daha fazla olduğunu gördüğümüz anda
            // break edeceğimizi biliyoruz.
            else break
        }

        // Yeni tatlı aslında mevcut tatlıdan farklıysa, resmi güncelleyin
        if (newDessert != currentDessert) {
            currentDessert = newDessert
            binding.dessertButton.setImageResource(newDessert.imageId)
        }
    }

    /**
     * Menu metotları
     */
    private fun onShare() {
        val shareIntent = ShareCompat.IntentBuilder.from(this)
            .setText(getString(R.string.share_text, dessertsSold, revenue))
            .setType("text/plain")
            .intent
        try {
            startActivity(shareIntent)
        } catch (ex: ActivityNotFoundException) {
            Toast.makeText(this, getString(R.string.sharing_not_available),
                Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.shareMenuButton -> onShare()
        }
        return super.onOptionsItemSelected(item)
    }

    /** Lifecycle Metotlaı **/
    override fun onStart() {
        super.onStart()

        Timber.i("onStart çağrıldı")
    }

    override fun onResume() {
        super.onResume()
        Timber.i("onResume çağrıldı")
    }

    override fun onPause() {
        super.onPause()
        Timber.i("onPause çağrıldı")
    }

    override fun onStop() {
        super.onStop()
        Timber.i("onStop çağrıldı")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.i("onDestroy çağrıldı")
    }

    override fun onRestart() {
        super.onRestart()
        Timber.i("onRestart çağrıldı")
    }
}