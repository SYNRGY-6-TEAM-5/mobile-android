package com.synrgy.aeroswift.presentation.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.synrgy.domain.local.FlightBooking

class FlightBookingDiffUtil: DiffUtil.ItemCallback<FlightBooking>() {
    override fun areItemsTheSame(oldItem: FlightBooking, newItem: FlightBooking): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: FlightBooking, newItem: FlightBooking): Boolean {
        return oldItem.id == newItem.id
    }
}