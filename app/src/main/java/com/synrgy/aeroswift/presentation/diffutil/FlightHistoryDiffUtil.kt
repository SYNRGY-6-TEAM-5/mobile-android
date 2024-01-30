package com.synrgy.aeroswift.presentation.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.synrgy.domain.FlightHistory

class FlightHistoryDiffUtil: DiffUtil.ItemCallback<FlightHistory>() {
    override fun areItemsTheSame(oldItem: FlightHistory, newItem: FlightHistory): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: FlightHistory, newItem: FlightHistory): Boolean {
        return oldItem.id == newItem.id
    }
}