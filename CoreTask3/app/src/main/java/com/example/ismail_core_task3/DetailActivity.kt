package com.example.ismail_core_task3

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val name = intent.getStringExtra("GROUP_NAME")
        val dateTime = intent.getStringExtra("GROUP_DATETIME")
        val location = intent.getStringExtra("GROUP_LOCATION")

        findViewById<TextView>(R.id.tvGroupName).text = name
        findViewById<TextView>(R.id.tvGroupDateTime).text = dateTime
        findViewById<TextView>(R.id.tvGroupLocation).text = location
    }
}
