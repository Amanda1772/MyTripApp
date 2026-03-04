package com.example.mytripapp.ui

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import com.example.mytripapp.R

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val etName = view.findViewById<EditText>(R.id.etName)
        val etEmail = view.findViewById<EditText>(R.id.etEmail)
        val etPhone = view.findViewById<EditText>(R.id.etPhone)
        val switchDark = view.findViewById<SwitchCompat>(R.id.switchDarkMode)
        val btnSave = view.findViewById<TextView>(R.id.btnSaveSettings)

        val current = SettingsManager.load(requireContext())
        etName.setText(current.name)
        etEmail.setText(current.email)
        etPhone.setText(current.phone)
        switchDark.isChecked = current.darkMode

        btnSave.setOnClickListener {
            val name = etName.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val phone = etPhone.text.toString().trim()
            val darkMode = switchDark.isChecked

            if (name.isBlank()) {
                Toast.makeText(requireContext(), "Name is required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            SettingsManager.save(requireContext(), name, email, phone, darkMode)

            Toast.makeText(requireContext(), "Saved", Toast.LENGTH_SHORT).show()

            requireActivity().recreate()
        }
    }
}