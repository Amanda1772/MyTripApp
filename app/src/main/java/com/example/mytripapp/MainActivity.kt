package com.example.mytripapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.mytripapp.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        // Apply theme BEFORE content is shown
        SettingsManager.applyTheme(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottom = findViewById<BottomNavigationView>(R.id.bottomNav)

        if (savedInstanceState == null) {
            openFragment(TripsFragment())
        }

        bottom.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_trips -> openFragment(TripsFragment())
                R.id.nav_pinned -> openFragment(PinnedFragment())
                R.id.nav_settings -> openFragment(SettingsFragment())
            }
            true
        }
    }

    private fun openFragment(f: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, f)
            .commit()
    }
}