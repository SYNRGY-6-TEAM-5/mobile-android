package com.synrgy.aeroswift.presentation.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.synrgy.aeroswift.models.AddonModels

class AddonDiffUtil: DiffUtil.ItemCallback<AddonModels>() {
    override fun areItemsTheSame(oldItem: AddonModels, newItem: AddonModels): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: AddonModels, newItem: AddonModels): Boolean {
        return oldItem.id == newItem.id
    }
}