package com.example.noteapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView

const val TEXT_TITLE = "title"
const val TEXT_DESC = "desc"
class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val title = intent.getStringExtra(TEXT_TITLE)
        val desc = intent.getStringExtra(TEXT_DESC)
        findViewById<TextView>(R.id.title).text = title
        findViewById<TextView>(R.id.desc).text = desc
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
}