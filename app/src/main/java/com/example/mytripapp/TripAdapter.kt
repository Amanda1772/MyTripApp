package com.example.mytripapp

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TripAdapter(
    private var tripList: List<TripItem>,
    private val onDeleteClick: (TripItem) -> Unit
) : RecyclerView.Adapter<TripAdapter.TripViewHolder>() {

    class TripViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvDestination: TextView = itemView.findViewById(R.id.tvDestination)
        val tvDates: TextView = itemView.findViewById(R.id.tvDates)
        val btnMap: Button = itemView.findViewById(R.id.btnMap)
        val btnDelete: Button = itemView.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_trip_item, parent, false)
        return TripViewHolder(view)
    }

    override fun onBindViewHolder(holder: TripViewHolder, position: Int) {
        val trip = tripList[position]

        holder.tvDestination.text = trip.destination
        holder.tvDates.text = "${trip.startDate} - ${trip.endDate}"

        holder.btnMap.setOnClickListener {
            val uri = Uri.parse("geo:0,0?q=${trip.destination}")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            intent.setPackage("com.google.android.apps.maps")
            holder.itemView.context.startActivity(intent)
        }

        holder.btnDelete.setOnClickListener {
            onDeleteClick(trip)
        }
    }

    override fun getItemCount(): Int = tripList.size

    fun updateList(newList: List<TripItem>) {
        tripList = newList
        notifyDataSetChanged()
    }
}
