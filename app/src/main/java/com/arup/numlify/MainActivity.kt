package com.arup.numlify

import android.content.*
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener


class MainActivity : AppCompatActivity() {

    private lateinit var textView: TextView
    private lateinit var editText: EditText
    private lateinit var share: View
    private lateinit var history_button: View
    private lateinit var help_text: TextView
    private lateinit var fade_in: Animation
    private lateinit var fade_out: Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editText = findViewById(R.id.number)
        textView = findViewById(R.id.textView)
        share = findViewById(R.id.share)
        history_button = findViewById(R.id.open_history)
        help_text = findViewById(R.id.help_text)

        fade_in = AnimationUtils.loadAnimation(applicationContext, R.anim.fade_in)
        fade_out = AnimationUtils.loadAnimation(applicationContext, R.anim.fade_out)

        var str = ""

        help_text.alpha = 0.0F
        share.alpha = 0.0F

        editText.addTextChangedListener {
            str = NumberToWord.run(editText.text.toString())

            textView.text = str
            textView.startAnimation(fade_in)

            invisibleView(str.isNotEmpty())
        }

        editText.setOnEditorActionListener { v, actionId, event ->
            if (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER || actionId == EditorInfo.IME_ACTION_DONE) {
                saveInDatabase(editText.text.toString(), textView.text.toString())
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


    private fun saveInDatabase(value: String, answer: String?) {
        val db = DBHelper(this)
        if(answer.isNullOrEmpty()) {
            db.insert(value, NumberToWord.run(value))
        }
        db.insert(value, answer)
    }

    private fun invisibleView(boolean: Boolean) {
        if(boolean && help_text.alpha == 0F) {
            help_text.startAnimation(fade_in)
            share.startAnimation(fade_in)
            help_text.alpha = 1F
            share.alpha = 1F
        }
        else if (!boolean && help_text.alpha == 1F) {
            help_text.startAnimation(fade_out)
            share.startAnimation(fade_out)
        }

    }
}
