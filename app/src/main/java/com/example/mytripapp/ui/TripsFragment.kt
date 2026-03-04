package com.example.mytripapp.ui

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mytripapp.R
import com.example.mytripapp.data.TripEntity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.android.material.textfield.TextInputLayout

class TripsFragment : Fragment(R.layout.fragment_trips) {

    companion object {
        const val REQUEST_SEARCH = "request_search"
        const val KEY_SEARCH = "key_search"
    }

    private lateinit var vm: TripViewModel
    private lateinit var adapter: TripAdapter

    private var allTrips: List<TripEntity> = emptyList()
    private var activeQuery: String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val root = view as ConstraintLayout
        val header = view.findViewById<View>(R.id.header)
        UiStyle.applyScreenBackground(root, header)

        val tilSearch = view.findViewById<TextInputLayout>(R.id.tilSearch)
        UiStyle.applySearchStyle(tilSearch)

        // FIXED ViewModel creation (prevents crash)
        val factory =
            ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().application)
        vm = ViewModelProvider(this, factory)[TripViewModel::class.java]

        val rv = view.findViewById<RecyclerView>(R.id.rvTrips)
        val fab = view.findViewById<FloatingActionButton>(R.id.fabAdd)
        val tvGreeting = view.findViewById<TextView>(R.id.tvGreeting)
        val etSearch = view.findViewById<MaterialAutoCompleteTextView>(R.id.etSearch)

        val settingsData = SettingsManager.load(requireContext())
        val name = settingsData.name.trim()
        tvGreeting.text = if (name.isNotEmpty()) "Welcome, $name" else "Welcome"

        adapter = TripAdapter(
            items = emptyList(),
            onPin = { vm.togglePinned(it) },
            onEdit = { openEdit(it) },
            onDelete = { vm.deleteTrip(it) }
        )

        rv.layoutManager = LinearLayoutManager(requireContext())
        rv.adapter = adapter

        vm.trips.observe(viewLifecycleOwner) { list: List<TripEntity> ->
            allTrips = list
            setupSearchDropdown(etSearch)
            render()
        }

        fab.setOnClickListener {
            startActivity(Intent(requireContext(), TripDetailActivity::class.java))
        }

        etSearch.threshold = 1

        etSearch.setOnItemClickListener { _, _, position, _ ->
            val selected = etSearch.adapter.getItem(position)?.toString().orEmpty()
            activeQuery = selected
            render()
        }

        etSearch.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {}

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                activeQuery = s?.toString().orEmpty()
                render()
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        parentFragmentManager.setFragmentResultListener(
            REQUEST_SEARCH,
            viewLifecycleOwner
        ) { _, bundle ->

            activeQuery = bundle.getString(KEY_SEARCH).orEmpty()
            etSearch.setText(activeQuery, false)
            render()
        }

        val swipe = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT
        ) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(
                viewHolder: RecyclerView.ViewHolder,
                direction: Int
            ) {
                val pos = viewHolder.adapterPosition
                val item = adapter.getItemAt(pos)
                vm.deleteTrip(item)
            }
        }

        ItemTouchHelper(swipe).attachToRecyclerView(rv)
    }

    private fun setupSearchDropdown(etSearch: MaterialAutoCompleteTextView) {

        val destinations = allTrips
            .map { it.destination.trim() }
            .filter { it.isNotEmpty() }
            .distinct()
            .sorted()

        val dropdownAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            destinations
        )

        etSearch.setAdapter(dropdownAdapter)
    }

    private fun render() {

        val q = activeQuery.trim()

        val filtered =
            if (q.isBlank()) allTrips
            else allTrips.filter {
                it.destination.contains(q, ignoreCase = true)
            }

        adapter.submitList(filtered)
    }

    private fun openEdit(trip: TripEntity) {

        val i = Intent(requireContext(), TripDetailActivity::class.java)

        i.putExtra(TripDetailActivity.EXTRA_ID, trip.id)
        i.putExtra(TripDetailActivity.EXTRA_DEST, trip.destination)
        i.putExtra(TripDetailActivity.EXTRA_START, trip.startDate)
        i.putExtra(TripDetailActivity.EXTRA_END, trip.endDate)
        i.putExtra(TripDetailActivity.EXTRA_NOTES, trip.notes)
        i.putExtra(TripDetailActivity.EXTRA_PINNED, trip.pinned)

        startActivity(i)
    }
}