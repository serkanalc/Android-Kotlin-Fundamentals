
package com.example.android.aboutme

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.android.aboutme.databinding.ActivityMainBinding


/**
 * AboutMeDataBinding uygulamasının MainActivity dosyasıdır. Bu uygulama şunları içerir:
 *     * TextViews, ImageView, Button, EditText ve ScrollView ile LinearLayout
 *     * Kaydırılabilir metni görüntülemek için ScrollView
 *     * EditText ile kullanıcı girişi alma.
 *     * Bir EditText'ten metin almak ve onu bir TextView'da ayarlamak için bir Düğme için işleyiciye tıklayın.
 *     * Bir görünümün görünürlük durumunu ayarlama.
 *     * MainActivity ile aktivite_main.xml arasındaki veri bağlama. findViewById nasıl kaldırılır,
 *       ve veri bağlama nesnesi kullanılarak görünümlerde verilerin nasıl görüntüleneceği.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val myName: MyName = MyName("Serkan Alc")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.myName = myName

        binding.doneButton.setOnClickListener {
            addNickname(it)
        }

    }

    private fun addNickname(view: View) {
        binding. apply {
            myName?.nickname = nicknameEdit.text.toString()
            invalidateAll()
            nicknameEdit.visibility = View.GONE
            doneButton.visibility = View.GONE
            nicknameText.visibility = View.VISIBLE
        }


        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
