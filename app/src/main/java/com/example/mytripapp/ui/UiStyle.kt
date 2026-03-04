package com.example.mytripapp.ui

import android.view.View
import com.example.mytripapp.R
import com.google.android.material.textfield.TextInputLayout

object UiStyle {

    fun applyScreenBackground(root: View, header: View? = null) {
        // One drawable, theme colors swap automatically (values vs values-night)
        root.setBackgroundResource(R.drawable.bg_gradient_main)
        header?.setBackgroundResource(R.drawable.bg_gradient_header)
    }

    fun applySearchStyle(til: TextInputLayout) {
        til.boxStrokeColor = til.context.getColor(R.color.theme_border)
        til.defaultHintTextColor = android.content.res.ColorStateList.valueOf(
            til.context.getColor(R.color.theme_hint)
        )
    }
}