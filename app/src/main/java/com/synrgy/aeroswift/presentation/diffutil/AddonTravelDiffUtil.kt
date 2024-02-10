package com.synrgy.aeroswift.presentation.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.synrgy.aeroswift.models.AddonTravelModels

class AddonTravelDiffUtil: DiffUtil.ItemCallback<AddonTravelModels>() {
    override fun areItemsTheSame(oldItem: AddonTravelModels, newItem: AddonTravelModels): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: AddonTravelModels, newItem: AddonTravelModels): Boolean {
        return oldItem.id == newItem.id
    }
}