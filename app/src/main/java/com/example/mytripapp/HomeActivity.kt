package com.example.mytripapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val btnAddTrip = findViewById<MaterialButton>(R.id.btnAddTrip)
        val btnOpenSelected = findViewById<MaterialButton>(R.id.btnOpenSelected)
        val btnLogout = findViewById<MaterialButton>(R.id.btnLogout)

        btnAddTrip.setOnClickListener {
            startActivity(Intent(this, TripDetailActivity::class.java))
        }

        btnOpenSelected.setOnClickListener {
        }

        btnLogout.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}
