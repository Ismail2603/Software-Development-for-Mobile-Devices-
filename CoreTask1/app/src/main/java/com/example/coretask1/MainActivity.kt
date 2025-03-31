package com.example.coretask1

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var diceValueTextView: TextView
    private lateinit var scoreTextView: TextView
    private lateinit var rollButton: Button
    private lateinit var addButton: Button
    private lateinit var subtractButton: Button
    private lateinit var resetButton: Button

    private var totalScore: Int = 0
    private var currentDiceValue: Int = 0
    private var isRollButtonClicked: Boolean = false
    private val random = Random(1) // Initialize Random object with seed 1

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        // Check if the default locale is English
        val defaultLocale = Locale.getDefault()
        val isEnglishLocale = defaultLocale.language == "en"

        if (!isEnglishLocale) {
            // Set locale to Malay
            val locale = Locale("ms")
            Locale.setDefault(locale)
            val resources = resources
            val configuration = resources.configuration
            configuration.setLocale(locale)
            val displayMetrics = resources.displayMetrics
            resources.updateConfiguration(configuration, displayMetrics)
        }

        setContentView(R.layout.activity_main)

        // Initialize views
        diceValueTextView = findViewById(R.id.diceValueTextView)
        scoreTextView = findViewById(R.id.scoreTextView)
        rollButton = findViewById(R.id.rollButton)
        addButton = findViewById(R.id.addButton)
        subtractButton = findViewById(R.id.subtractButton)
        resetButton = findViewById(R.id.resetButton)

        // Restore saved state if available
        if (savedInstanceState != null) {
            totalScore = savedInstanceState.getInt("totalScore", 0)
            currentDiceValue = savedInstanceState.getInt("currentDiceValue", 0)
            isRollButtonClicked = savedInstanceState.getBoolean("isRollButtonClicked", false)
            updateViews()
        }

        // Set click listeners
        rollButton.setOnClickListener { rollDice() }
        addButton.setOnClickListener { addToScore() }
        subtractButton.setOnClickListener { subtractFromScore() }
        resetButton.setOnClickListener { resetScore() }

        // Enable/disable buttons based on conditions
        updateButtons()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("totalScore", totalScore)
        outState.putInt("currentDiceValue", currentDiceValue)
        outState.putBoolean("isRollButtonClicked", isRollButtonClicked)
    }

    private fun rollDice() {
        currentDiceValue = random.nextInt(6) + 1 // Roll a dice from 1 to 6
        diceValueTextView.text = "Dice Value: $currentDiceValue"

        // Set the flag to indicate that the roll button is clicked
        isRollButtonClicked = true

        // Enable the add and subtract buttons
        addButton.isEnabled = true
        subtractButton.isEnabled = true

        // Disable the roll button
        rollButton.isEnabled = false
    }

    private fun addToScore() {
        totalScore += currentDiceValue
        updateViews()
        Log.d("MainActivity", "Added $currentDiceValue to score")

        // Disable add and subtract buttons
        addButton.isEnabled = false
        subtractButton.isEnabled = false

        // Enable the roll button
        rollButton.isEnabled = true
    }

    private fun subtractFromScore() {
        totalScore = if (totalScore - currentDiceValue > 0) {
            totalScore - currentDiceValue
        } else {
            0
        }
        updateViews()
        Log.d("MainActivity", "Subtracted $currentDiceValue from score")

        // Disable add and subtract buttons
        addButton.isEnabled = false
        subtractButton.isEnabled = false

        // Enable the roll button
        rollButton.isEnabled = true
    }

    private fun resetScore() {
        totalScore = 0
        updateViews()
        Log.d("MainActivity", "Score reset to 0")

        // Enable the roll button and disable add and subtract buttons
        rollButton.isEnabled = true
        addButton.isEnabled = false
        subtractButton.isEnabled = false
    }

    private fun updateViews() {
        scoreTextView.text = "Score: $totalScore"
    }

    private fun updateButtons() {
        if (isRollButtonClicked) {
            addButton.isEnabled = true
            subtractButton.isEnabled = true
            resetButton.isEnabled = true
        } else {
            addButton.isEnabled = false
            subtractButton.isEnabled = false
            resetButton.isEnabled = false
        }
    }
}
