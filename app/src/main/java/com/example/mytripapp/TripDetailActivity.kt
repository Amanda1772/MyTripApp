package com.example.mytripapp

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class TripDetailActivity : AppCompatActivity() {

    private val items = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trip_detail)

        val tripName = intent.getStringExtra("TRIP_NAME") ?: "Trip"
        findViewById<TextView>(R.id.tvTripName).text = tripName

        val lvItems = findViewById<ListView>(R.id.lvItems)
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, items)
        lvItems.adapter = adapter

        findViewById<MaterialButton>(R.id.btnAddItem).setOnClickListener {
            showAddItemDialog(adapter)
        }

        findViewById<MaterialButton>(R.id.btnBack).setOnClickListener {
            finish()
        }
    }

    private fun showAddItemDialog(adapter: ArrayAdapter<String>) {
        val input = EditText(this)
        input.hint = "Example: Flight at 9 AM"

        AlertDialog.Builder(this)
            .setTitle("Add Itinerary Item")
            .setMessage("Enter an itinerary item")
            .setView(input)
            .setPositiveButton("Add") { _, _ ->
                val text = input.text.toString().trim()
                if (text.isNotEmpty()) {
                    items.add(text)
                    adapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(this, "Item cannot be empty", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}
