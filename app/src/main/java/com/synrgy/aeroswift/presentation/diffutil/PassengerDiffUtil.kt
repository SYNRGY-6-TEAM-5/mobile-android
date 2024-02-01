package com.synrgy.aeroswift.presentation.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.synrgy.domain.local.PassengerData

class PassengerDiffUtil: DiffUtil.ItemCallback<PassengerData>() {
    override fun areItemsTheSame(oldItem: PassengerData, newItem: PassengerData): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: PassengerData, newItem: PassengerData): Boolean {
        return oldItem.id == newItem.id
    }
}