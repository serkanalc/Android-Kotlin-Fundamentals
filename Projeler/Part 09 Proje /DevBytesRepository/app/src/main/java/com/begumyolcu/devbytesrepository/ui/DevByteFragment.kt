package com.begumyolcu.devbytesrepository.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.begumyolcu.devbytesrepository.domain.DevByteVideo
import com.begumyolcu.devbytesrepository.viewmodels.DevByteViewModel
import com.begumyolcu.devbytesrepository.R
import com.begumyolcu.devbytesrepository.databinding.DevbyteItemBinding
import com.begumyolcu.devbytesrepository.databinding.FragmentDevByteBinding

class DevByteFragment : Fragment() {
    /**
     * Uygun bir lifecycle metoduna kadar viewModel oluşturulmasını geciktirmenin bir yolu lazy kullanmaktır.
     * Bu, bu Fragment'te yaptığımız onActivityCreated'den önce viewModel'e referans edilmemesini gerektirir.
     */
    private val viewModel: DevByteViewModel by lazy {
        val activity = requireNotNull(this.activity) {
            "viewModel'e yalnızca onActivityCreated() sonrasında erişebilirsiniz."
        }
        ViewModelProvider(this, DevByteViewModel.Factory(activity.application))
            .get(DevByteViewModel::class.java)
    }

    /**
     * Video listesini cardlara dönüştürmek için RecyclerView Adapter'ı.
     */
    private var viewModelAdapter: DevByteAdapter? = null

    /**
     * onCreateView() return edildikten ve fragment'ın view hiyerarşisi oluşturulduktan hemen sonra çağrılır.
     * Viewları almak veya state'i geri yüklemek gibi bu parçalar yerine yerleştirildikten sonra son
     * initialization için kullanılabilir.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.playlist.observe(viewLifecycleOwner, Observer<List<DevByteVideo>> { videos ->
            videos?.apply {
                viewModelAdapter?.videos = videos
            }
        })
    }

    /**
     * Fragment'ın kullanıcı interface view'unu instantiate etmesi için çağrılır.
     *
     * Buradan bir View döndürürseniz, view yayınlandığında daha sonra
     * onDestroyView içinde aranacaksınız.
     *
     * @param inflater Fragment'taki herhangi bir view'u inflate etmek
     * için kullanılabilen LayoutInflater nesnesi,
     * @param container Null değilse, bu, fragment'ın UI'ımım eklenmesi gereken
     * parent view'dur. Fragment, view'un kendisini eklememelidir, ancak bu,
     * view'un LayoutParams'ını oluşturmak için kullanılabilir.
     * @param savedInstanceState Null değilse, bu fragment burada belirtildiği
     * gibi bir önceki kaydedilmiş state'ten yeniden oluşturuluyor.
     *
     * @return Fragment'ın UI'yı için View'u return eder.
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding: FragmentDevByteBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_dev_byte,
            container,
            false)
        // lifecycleOwner'ı DataBinding'in LiveData observe edebilmesi için ayarlayın
        binding.setLifecycleOwner(viewLifecycleOwner)

        binding.viewModel = viewModel

        viewModelAdapter = DevByteAdapter(VideoClick {
            // Bir video tıklandığında bu blok veya lambda DevByteAdapter tarafından çağrılır

            // coontext etrafta değil, Fragment artık ekranda olmadığı için bu
            // tıklamayı güvenle atabiliriz
            val packageManager = context?.packageManager ?: return@VideoClick

            // YouTube uygulamasına doğrudan bir intent oluşturmaya çalışın
            var intent = Intent(Intent.ACTION_VIEW, it.launchUri)
            if(intent.resolveActivity(packageManager) == null) {
                // Youtube uygulaması bulunmazsa, web url'sini kullan
                intent = Intent(Intent.ACTION_VIEW, Uri.parse(it.url))
            }

            startActivity(intent)
        })

        binding.root.findViewById<RecyclerView>(R.id.recycler_view).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = viewModelAdapter
        }


        // Ağ hatası için observer.
        viewModel.eventNetworkError.observe(viewLifecycleOwner, Observer<Boolean> { isNetworkError ->
            if (isNetworkError) onNetworkError()
        })

        return binding.root
    }

    /**
     * Ağ hataları için bir Toast hata mesajı görüntüleme metodu.
     */
    private fun onNetworkError() {
        if(!viewModel.isNetworkErrorShown.value!!) {
            Toast.makeText(activity, "Network Hatası", Toast.LENGTH_LONG).show()
            viewModel.onNetworkErrorShown()
        }
    }

    /**
     * YouTube uygulama bağlantıları oluşturmak için yardımcı metot
     */
    private val DevByteVideo.launchUri: Uri
        get() {
            val httpUri = Uri.parse(url)
            return Uri.parse("vnd.youtube:" + httpUri.getQueryParameter("v"))
        }
}

/**
 * Videolar için bir click listener. Bloka bir isim vererek, okuyucunun ne yaptığını anlamasına yardımcı olur.
 *
 */
class VideoClick(val block: (DevByteVideo) -> Unit) {
    /**
     * Bir video tıklandığında çağrılır
     *
     * @param video tıklanan video
     */
    fun onClick(video: DevByteVideo) = block(video)
}

/**
 * Listedeki öğeler üzerinde data binding'i ayarlamak için RecyclerView Adapter'ı.
 */
class DevByteAdapter(val callback: VideoClick) : RecyclerView.Adapter<DevByteViewHolder>() {

    /**
     * Adapter'ımızın göstereceği videolar
     */
    var videos: List<DevByteVideo> = emptyList()
        set(value) {
            field = value
            // Ekstra bir zorluk için, bunu paging kütüphanesi kullanacak şekilde güncelleyin.

            // Kayıtlı observerlara veri kümesinin değiştiğini bildirin. Bu, RecyclerView'ımızdaki
            // her öğenin geçersiz kılınmasına (invalidate) neden olacaktır.
            notifyDataSetChanged()
        }

    /**
     * RecyclerView'ın bir öğeyi temsil etmesi için verilen türde yeni bir ViewHolder'a
     * ihtiyacı olduğunda çağrılır
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DevByteViewHolder {
        val withDataBinding: DevbyteItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            DevByteViewHolder.LAYOUT,
            parent,
            false)
        return DevByteViewHolder(withDataBinding)
    }

    override fun getItemCount() = videos.size

    /**
     * Verileri belirtilen konumda görüntülemek için RecyclerView tarafından çağrılır. Bu metot,
     * öğeyi verilen konumda yansıtacak şekilde ViewHolder#itemView içeriğini güncellemelidir.
     */
    override fun onBindViewHolder(holder: DevByteViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.video = videos[position]
            it.videoCallback = callback
        }
    }

}

/**
 * DevByte öğeleri için ViewHolder. Tüm işler data binding ile yapılır.
 */
class DevByteViewHolder(val viewDataBinding: DevbyteItemBinding) :
    RecyclerView.ViewHolder(viewDataBinding.root) {
    companion object {
        @LayoutRes
        val LAYOUT = R.layout.devbyte_item
    }
}