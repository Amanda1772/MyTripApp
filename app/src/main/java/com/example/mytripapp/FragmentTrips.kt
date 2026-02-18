package com.example.mytripapp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FragmentTrips : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TripAdapter

    private val tripsList: MutableList<TripItem> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_trips, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Toast.makeText(requireContext(), "FragmentTrips loaded", Toast.LENGTH_SHORT).show()

        recyclerView = view.findViewById(R.id.recyclerViewTrips)
        val etSearch = view.findViewById<EditText>(R.id.etSearch)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Sample data so you SEE the enhancements working immediately
        if (tripsList.isEmpty()) {
            tripsList.add(TripItem(1, "Miami", "2026-03-01", "2026-03-05"))
            tripsList.add(TripItem(2, "Orlando", "2026-04-10", "2026-04-12"))
            tripsList.add(TripItem(3, "New York", "2026-05-20", "2026-05-25"))
        }

        adapter = TripAdapter(tripsList.toList()) { trip ->
            deleteTrip(trip)
        }

        recyclerView.adapter = adapter

        etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val searchText = s.toString()

                val filteredList =
                    if (searchText.isEmpty()) tripsList.toList()
                    else tripsList.filter { it.destination.contains(searchText, ignoreCase = true) }

                adapter.updateList(filteredList)
            }
        })
    }

    private fun deleteTrip(trip: TripItem) {
        tripsList.remove(trip)
        adapter.updateList(tripsList.toList())
        Toast.makeText(requireContext(), "Trip deleted", Toast.LENGTH_SHORT).show()
    }
}
