package com.synrgy.aeroswift.presentation.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.synrgy.domain.AirportList

class AirportListDiffUtil: DiffUtil.ItemCallback<AirportList>() {
    override fun areItemsTheSame(oldItem: AirportList, newItem: AirportList): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: AirportList, newItem: AirportList): Boolean {
        return oldItem.title == newItem.title
    }
}