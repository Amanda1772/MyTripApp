package com.example.mytripapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mytripapp.R
import com.example.mytripapp.data.TripEntity

class TripAdapter(
    private var items: List<TripEntity>,
    private val onPin: (TripEntity) -> Unit,
    private val onEdit: (TripEntity) -> Unit,
    private val onDelete: (TripEntity) -> Unit
) : RecyclerView.Adapter<TripAdapter.VH>() {

    fun submitList(newItems: List<TripEntity>) {
        items = newItems
        notifyDataSetChanged()
    }

    fun getItemAt(position: Int): TripEntity = items[position]

    class VH(view: View) : RecyclerView.ViewHolder(view) {
        val tvDestination: TextView = view.findViewById(R.id.tvDestination)
        val tvDates: TextView = view.findViewById(R.id.tvDates)
        val tvNotes: TextView = view.findViewById(R.id.tvNotes)
        val btnPin: ImageButton = view.findViewById(R.id.btnPin)
        val btnEdit: TextView = view.findViewById(R.id.btnEdit)
        val btnDelete: TextView = view.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_trip, parent, false)
        return VH(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        val trip = items[position]

        holder.tvDestination.text = trip.destination
        holder.tvDates.text = "${trip.startDate} to ${trip.endDate}"
        holder.tvNotes.text = if (trip.notes.isBlank()) "No notes yet" else trip.notes

        holder.btnPin.setImageResource(
            if (trip.pinned) android.R.drawable.btn_star_big_on
            else android.R.drawable.btn_star_big_off
        )

        holder.btnPin.setOnClickListener { onPin(trip) }
        holder.btnEdit.setOnClickListener { onEdit(trip) }
        holder.btnDelete.setOnClickListener { onDelete(trip) }

        holder.itemView.setOnClickListener { onEdit(trip) }
    }
}