package com.synrgy.aeroswift.presentation.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.synrgy.domain.response.airport.AirportData

class AirportListDiffUtil: DiffUtil.ItemCallback<AirportData>() {
    override fun areItemsTheSame(oldItem: AirportData, newItem: AirportData): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: AirportData, newItem: AirportData): Boolean {
        return oldItem.id == newItem.id
    }
}