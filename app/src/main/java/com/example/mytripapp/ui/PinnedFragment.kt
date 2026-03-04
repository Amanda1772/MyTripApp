package com.example.mytripapp.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.mytripapp.R

class PinnedFragment : Fragment(R.layout.fragment_pinned) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        UiStyle.applyScreenBackground(view)
    }
}