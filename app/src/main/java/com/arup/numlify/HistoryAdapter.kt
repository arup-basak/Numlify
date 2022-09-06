package com.arup.numlify

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.database.Cursor
import androidx.recyclerview.widget.RecyclerView
import com.arup.numlify.HistoryAdapter.HistoryViewHolder
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast

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
        holder.headingTextView.text = cursor.getString(0)
        holder.textView.text = cursor.getString(1)
        holder.time.text = cursor.getString(2)
        holder.card.setOnClickListener {
            val message = "Copied text form of " + holder.headingTextView.text.toString()
            val clipboard: ClipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData: ClipData = ClipData.newPlainText("Text Copied!", holder.textView.text.toString())
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            clipboard.setPrimaryClip(clipData)
        }
    }

    override fun getItemCount(): Int {
        return cursor.count
    }

    class HistoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val headingTextView: TextView
        val textView: TextView
        val time: TextView
        val card: View

        init {
            headingTextView = view.findViewById(R.id.numb)
            textView = view.findViewById(R.id.help_text)
            time = view.findViewById(R.id.time)
            card = view.findViewById(R.id.history_card)
        }
    }
}