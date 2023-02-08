package com.arup.numlify

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.arup.numlify.HistoryAdapter.HistoryViewHolder
import java.text.SimpleDateFormat
import java.util.*

class HistoryAdapter internal constructor(
    private val cursor: Cursor,
    private val context: Context
) : RecyclerView.Adapter<HistoryViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.recycler_view_history, parent, false)
        return HistoryViewHolder(view)
    }


    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        cursor.moveToNext()
        var arr = arrayOf("", "")
        try {
            arr = timeLongToString(cursor.getString(2).toLong())
        }
        catch (_: Exception) {}

        holder.headingTextView.text = cursor.getString(0)
        holder.textView.text = cursor.getString(1)
        holder.date.text = arr[0]
        holder.time.text = arr[1]

        val orientation: Int = context.resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            holder.textView.maxEms = 12
        }


        holder.card.setOnClickListener {
            val message = "Copied text form of " + holder.headingTextView.text.toString()
            val clipboard: ClipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData: ClipData = ClipData.newPlainText("Text Copied!", holder.textView.text.toString())
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            clipboard.setPrimaryClip(clipData)
        }

        holder.card.setOnLongClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here!")
            intent.putExtra(Intent.EXTRA_TEXT, holder.textView.text.toString())
            context.startActivity(Intent.createChooser(intent, "Share Via"))
            false
        }
    }

    override fun getItemCount(): Int {
        return cursor.count
    }

    class HistoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val headingTextView: TextView
        val textView: TextView
        val time: TextView
        val date: TextView
        val card: View

        init {
            headingTextView = view.findViewById(R.id.numb)
            textView = view.findViewById(R.id.help_text)
            time = view.findViewById(R.id.time)
            date = view.findViewById(R.id.date)
            card = view.findViewById(R.id.history_card)
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun timeLongToString(timeLong: Long): Array<String> {
        val dt = Date(timeLong)
        val df = SimpleDateFormat("dd MMM yyyy hh:mm a")
        val timeStr = df.format(dt)
        return arrayOf(
            timeStr.substring(0, 11),
            timeStr.substring(12)
        )
    }
}