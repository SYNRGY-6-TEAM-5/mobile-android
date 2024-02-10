package com.synrgy.aeroswift.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.synrgy.aeroswift.databinding.ItemFlightBookingBinding
import com.synrgy.aeroswift.presentation.diffutil.FlightBookingDiffUtil
import com.synrgy.aeroswift.presentation.viewholder.FlightBookingViewHolder
import com.synrgy.domain.local.FlightBooking

class FlightBookingAdapter: ListAdapter<FlightBooking, FlightBookingViewHolder>(
    FlightBookingDiffUtil()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlightBookingViewHolder {
        return FlightBookingViewHolder(
            ItemFlightBookingBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: FlightBookingViewHolder, position: Int) {
        holder.bindData(getItem(position))
    }
}