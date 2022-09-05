package com.arup.numlify

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.arup.jdbc.NumberToWord

class MainActivity : AppCompatActivity() {
    private lateinit var textView: TextView
    private lateinit var editText: EditText
    private lateinit var share: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editText = findViewById(R.id.number)
        textView = findViewById(R.id.textView)
        share = findViewById(R.id.share)
        var str = ""

        editText.addTextChangedListener {
            str = NumberToWord.run(editText.text.toString())
            textView.text = str
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
}