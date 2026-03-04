package com.example.mytripapp.ui

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate

object SettingsManager {

    private const val PREFS = "mytrip_settings"
    private const val KEY_NAME = "name"
    private const val KEY_EMAIL = "email"
    private const val KEY_PHONE = "phone"
    private const val KEY_DARK = "dark_mode"

    fun applyTheme(context: Context) {
        val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        val dark = prefs.getBoolean(KEY_DARK, false)

        AppCompatDelegate.setDefaultNightMode(
            if (dark) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
        )
    }

    fun save(
        context: Context,
        name: String,
        email: String,
        phone: String,
        darkMode: Boolean
    ) {
        val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        prefs.edit()
            .putString(KEY_NAME, name)
            .putString(KEY_EMAIL, email)
            .putString(KEY_PHONE, phone)
            .putBoolean(KEY_DARK, darkMode)
            .apply()

        AppCompatDelegate.setDefaultNightMode(
            if (darkMode) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
        )
    }

    fun load(context: Context): SettingsData {
        val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        return SettingsData(
            name = prefs.getString(KEY_NAME, "").orEmpty(),
            email = prefs.getString(KEY_EMAIL, "").orEmpty(),
            phone = prefs.getString(KEY_PHONE, "").orEmpty(),
            darkMode = prefs.getBoolean(KEY_DARK, false)
        )
    }
}

data class SettingsData(
    val name: String,
    val email: String,
    val phone: String,
    val darkMode: Boolean
)