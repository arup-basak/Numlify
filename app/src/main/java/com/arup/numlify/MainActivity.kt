package com.arup.numlify

import android.content.*
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.google.gson.Gson
import java.lang.Exception


class MainActivity : AppCompatActivity() {
    private lateinit var textView: TextView
    private lateinit var editText: EditText
    private lateinit var share: ImageButton
    private lateinit var history_button: ImageButton

    private var PREFERENCE_KEY: String = "SavedHistoryPrf"

    private lateinit var GSON: Gson
    private lateinit var histories: Set<String>

    private lateinit var sharedPreference: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreference = getSharedPreferences(PREFERENCE_KEY,Context.MODE_PRIVATE)

        try {
            getSharedPref()
        }
        catch (e: Exception) {
            Toast.makeText(this, "Crashes", Toast.LENGTH_SHORT).show()
            histories = HashSet()
        }


        editText = findViewById(R.id.number)
        textView = findViewById(R.id.textView)
        share = findViewById(R.id.share)
        history_button = findViewById(R.id.open_history)
        GSON = Gson()
        var str = ""

        editText.addTextChangedListener {
            str = NumberToWord.run(editText.text.toString())
            textView.text = str
        }

        editText.setOnEditorActionListener { view, actionId, event ->
            if (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER || actionId == EditorInfo.IME_ACTION_DONE) {
                saveInPref()
            }
            false
        }

        textView.setOnClickListener {
            if(str.isNotEmpty()) {
                copyText(str)
            }
        }

        share.setOnClickListener {
            if(str.isNotEmpty()) {
                shareText(str)
            }
        }

        history_button.setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }
    }

    private fun copyText(text: String) {
        val clipboard: ClipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clipData: ClipData = ClipData.newPlainText("Text Copied!", text)
        Toast.makeText(this, "Text Copied!", Toast.LENGTH_SHORT).show()
        clipboard.setPrimaryClip(clipData)
    }

    private fun shareText(text: String) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here!")
        intent.putExtra(Intent.EXTRA_TEXT, text)
        startActivity(Intent.createChooser(intent, "Share Via"))
    }

    fun saveInPref() {
        val editor = sharedPreference.edit()
        editor.putStringSet("data", histories)
        editor.apply()
    }

    fun getSharedPref() {
        histories = sharedPreference.getStringSet("data", HashSet<String>()) as Set<String>
    }

    fun getCurrentTime(): String {
//        TODO(UPDATED SOON)
        return "v"
    }
}