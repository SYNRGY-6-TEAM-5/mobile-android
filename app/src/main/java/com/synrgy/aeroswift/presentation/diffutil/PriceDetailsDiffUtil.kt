package com.synrgy.aeroswift.presentation.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.synrgy.domain.local.PriceDetails

class PriceDetailsDiffUtil: DiffUtil.ItemCallback<PriceDetails>() {
    override fun areItemsTheSame(oldItem: PriceDetails, newItem: PriceDetails): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: PriceDetails, newItem: PriceDetails): Boolean {
        return oldItem.id == newItem.id
    }
}