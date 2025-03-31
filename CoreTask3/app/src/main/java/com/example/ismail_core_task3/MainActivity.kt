package com.example.ismail_core_task3

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.opencsv.CSVReader
import java.io.BufferedReader
import java.io.InputStreamReader
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: GroupAdapter
    private var groupList: List<Group> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Load data from CSV
        groupList = readCsvFile()

        // Set up adapter
        adapter = GroupAdapter(groupList) { group ->
            val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
            val intent = Intent(this, DetailActivity::class.java).apply {
                putExtra("GROUP_NAME", group.name)
                putExtra("GROUP_DATETIME", dateFormat.format(group.dateTime))
                putExtra("GROUP_LOCATION", group.location)
            }
            startActivity(intent)
        }
        recyclerView.adapter = adapter
    }

    private fun parseDate(dateString: String): Date? {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        return dateFormat.parse(dateString)
    }

    private fun readCsvFile(): List<Group> {
        val inputStream = resources.openRawResource(R.raw.groups)
        val reader = CSVReader(InputStreamReader(inputStream))
        val groups = mutableListOf<Group>()
        reader.use {
            it.readAll().drop(1).forEach { line ->
                val dateString = line[1] // Assuming the date is in the second column
                val parsedDate = parseDate(dateString)
                if (parsedDate != null) {
                    val group = Group(line[0], parsedDate, line[2])
                    groups.add(group)
                }
            }
        }
        return groups.sortedBy { it.dateTime } // Sort by date/time
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_sort -> {
                sortListByColor()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun sortListByColor() {
        val sortedGroups = groupList.sortedWith(compareBy { getColorCode(it.name) })
        adapter.updateList(sortedGroups)
    }

    private fun getColorCode(name: String): Int {
        return when (name) {
            "Apolitical" -> 0 // Green
            "Football" -> 1 // Red
            "UN Students" -> 2 // Blue
            "XSPORTS" -> 3 // Light Gray
            else -> 4 // Default White
        }
    }
}

data class Group(val name: String, val dateTime: Date, val location: String)
