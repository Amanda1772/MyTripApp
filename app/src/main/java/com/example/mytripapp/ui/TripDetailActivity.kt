package com.example.mytripapp.ui

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.mytripapp.R
import com.example.mytripapp.data.TripEntity

class TripDetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ID = "extra_id"
        const val EXTRA_DEST = "extra_dest"
        const val EXTRA_START = "extra_start"
        const val EXTRA_END = "extra_end"
        const val EXTRA_NOTES = "extra_notes"
        const val EXTRA_PINNED = "extra_pinned"
    }

    private lateinit var vm: TripViewModel

    private lateinit var etDestination: EditText
    private lateinit var etStart: EditText
    private lateinit var etEnd: EditText
    private lateinit var etNotes: EditText
    private lateinit var switchPinned: Switch

    private lateinit var btnSave: Button
    private lateinit var btnDelete: Button

    private var tripId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        SettingsManager.applyTheme(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trip_detail)

        // IMPORTANT: factory for AndroidViewModel
        val factory = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        vm = ViewModelProvider(this, factory)[TripViewModel::class.java]

        etDestination = findViewById(R.id.etDestination)
        etStart = findViewById(R.id.etStartDate)
        etEnd = findViewById(R.id.etEndDate)
        etNotes = findViewById(R.id.etNotes)
        switchPinned = findViewById(R.id.switchPinned)

        btnSave = findViewById(R.id.btnSaveTrip)
        btnDelete = findViewById(R.id.btnDeleteTrip)

        tripId = intent.getIntExtra(EXTRA_ID, -1)

        if (tripId != -1) {
            etDestination.setText(intent.getStringExtra(EXTRA_DEST).orEmpty())
            etStart.setText(intent.getStringExtra(EXTRA_START).orEmpty())
            etEnd.setText(intent.getStringExtra(EXTRA_END).orEmpty())
            etNotes.setText(intent.getStringExtra(EXTRA_NOTES).orEmpty())
            switchPinned.isChecked = intent.getBooleanExtra(EXTRA_PINNED, false)
        }

        btnSave.setOnClickListener {
            val destination = etDestination.text.toString().trim()
            val start = etStart.text.toString().trim()
            val end = etEnd.text.toString().trim()
            val notes = etNotes.text.toString().trim()
            val pinned = switchPinned.isChecked

            if (destination.isBlank()) {
                Toast.makeText(this, "Destination required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val trip = TripEntity(
                id = if (tripId == -1) 0 else tripId,
                destination = destination,
                startDate = start,
                endDate = end,
                notes = notes,
                pinned = pinned
            )

            if (tripId == -1) {
                vm.insertTrip(trip)
                Toast.makeText(this, "Trip added", Toast.LENGTH_SHORT).show()
            } else {
                vm.updateTrip(trip)
                Toast.makeText(this, "Trip updated", Toast.LENGTH_SHORT).show()
            }

            finish()
        }

        btnDelete.setOnClickListener {
            if (tripId == -1) {
                Toast.makeText(this, "Nothing to delete yet", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val trip = TripEntity(
                id = tripId,
                destination = etDestination.text.toString(),
                startDate = etStart.text.toString(),
                endDate = etEnd.text.toString(),
                notes = etNotes.text.toString(),
                pinned = switchPinned.isChecked
            )

            vm.deleteTrip(trip)
            Toast.makeText(this, "Trip deleted", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}