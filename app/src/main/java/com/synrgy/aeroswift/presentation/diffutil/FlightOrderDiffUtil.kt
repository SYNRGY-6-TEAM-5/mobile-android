package com.synrgy.aeroswift.presentation.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.synrgy.domain.FlightOrder

class FlightOrderDiffUtil: DiffUtil.ItemCallback<FlightOrder>() {
    override fun areItemsTheSame(oldItem: FlightOrder, newItem: FlightOrder): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: FlightOrder, newItem: FlightOrder): Boolean {
        return oldItem.position == newItem.position
    }
}