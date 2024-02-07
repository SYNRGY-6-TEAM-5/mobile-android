package com.synrgy.aeroswift.presentation.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.synrgy.domain.local.PassengerReq

class PassengerReqDiffUtil: DiffUtil.ItemCallback<PassengerReq>() {
    override fun areItemsTheSame(oldItem: PassengerReq, newItem: PassengerReq): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: PassengerReq, newItem: PassengerReq): Boolean {
        return oldItem.id == newItem.id
    }
}