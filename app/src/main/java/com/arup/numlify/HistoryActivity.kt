package com.arup.numlify

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HistoryActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        val db = DBHelper(this)

        recyclerView = findViewById(R.id.history_recycler)
        recyclerView.adapter = HistoryAdapter(db.data, this)
        recyclerView.layoutManager = LinearLayoutManager(this)
    }
}