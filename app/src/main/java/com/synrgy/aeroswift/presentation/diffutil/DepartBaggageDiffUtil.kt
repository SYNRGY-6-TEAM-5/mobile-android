package com.synrgy.aeroswift.presentation.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.synrgy.domain.local.DepartBaggage

class DepartBaggageDiffUtil: DiffUtil.ItemCallback<DepartBaggage>() {
    override fun areItemsTheSame(oldItem: DepartBaggage, newItem: DepartBaggage): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: DepartBaggage, newItem: DepartBaggage): Boolean {
        return oldItem.weight == newItem.weight
    }
}