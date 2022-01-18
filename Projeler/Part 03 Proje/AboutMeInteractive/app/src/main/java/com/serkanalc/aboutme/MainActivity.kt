package com.serkanalc.aboutme

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView



/*
   Bu Uygulama Aşağıdaki Maddeleri İçermektedir

   - Bir EditText ile kullanıcı girişi alma.
   - Bir EditText'ten metin almak ve bir TextView'da düzenlemek için bir Düğme işleyiciyi tanımlama.
   - Bir TextView üzerinde bir tıklama işleyicisi ayarlama( Tıklanabilir Bir TextView ).
   - bir visibility görünürlük durumunu belirle.
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.done_button).setOnClickListener {
            addNickname(it)
        }
        findViewById<TextView>(R.id.nickname_text).setOnClickListener {
            updateNickname(it)
        }

    }

    /*
        - Tamamlandı Butonu için bir tıklama işleyicisi
        - EditText ve Done buttonunu saklama
        - EditText içeriğini TextView olarak ayarlar ve görüntüleme.
     */
    private fun addNickname(view: View) {
        val editText= findViewById<EditText>(R.id.nickname_edit)
        val nicknameTextView=findViewById<TextView>(R.id.nickname_text)

        nicknameTextView.text = editText.text
        editText.visibility = View.GONE
        view.visibility = View.GONE
        nicknameTextView.visibility = View.VISIBLE


        // Klavyeyi Saklamak için:
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)



    }
    private fun updateNickname (view: View) {

        val editText = findViewById<EditText>(R.id.nickname_edit)
        val doneButton = findViewById<Button>(R.id.done_button)

        editText.visibility = View.VISIBLE
        doneButton.visibility = View.VISIBLE
        view.visibility = View.GONE

        // Odağı düzenleme metnine ayarlayın.
        editText.requestFocus()

        // Klavyeyi Saklamak için:

        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(editText, 0)

    }
}
