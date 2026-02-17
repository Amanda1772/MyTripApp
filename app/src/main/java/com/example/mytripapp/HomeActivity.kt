package com.example.mytripapp

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {

    private val trips = mutableListOf("Hawaii April 2026", "NYC Weekend", "Orlando Day Trip")
    private var selectedIndex: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val lvTrips = findViewById<ListView>(R.id.lvTrips)
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_single_choice, trips)
        lvTrips.adapter = adapter
        lvTrips.choiceMode = ListView.CHOICE_MODE_SINGLE

        lvTrips.setOnItemClickListener { _, _, position, _ ->
            selectedIndex = position
        }

        findViewById<Button>(R.id.btnAddTrip).setOnClickListener {
            showAddTripDialog(adapter)
        }

        findViewById<Button>(R.id.btnOpenSelected).setOnClickListener {
            if (selectedIndex < 0) {
                Toast.makeText(this, "Select a trip first", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val intent = Intent(this, TripDetailActivity::class.java)
            intent.putExtra("TRIP_NAME", trips[selectedIndex])
            startActivity(intent)
        }

        findViewById<Button>(R.id.btnLogout).setOnClickListener {
            finish()
        }
    }

    private fun showAddTripDialog(adapter: ArrayAdapter<String>) {
        val input = EditText(this)
        input.hint = "Trip name"

        AlertDialog.Builder(this)
            .setTitle("Add Trip")
            .setMessage("Enter a trip name")
            .setView(input)
            .setPositiveButton("Add") { _, _ ->
                val text = input.text.toString().trim()
                if (text.isNotEmpty()) {
                    trips.add(text)
                    adapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(this, "Trip name cannot be empty", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}
