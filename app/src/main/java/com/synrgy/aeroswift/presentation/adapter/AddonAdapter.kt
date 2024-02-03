package com.synrgy.aeroswift.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.synrgy.aeroswift.databinding.ItemAddonCardBinding
import com.synrgy.aeroswift.models.AddonModels
import com.synrgy.aeroswift.presentation.diffutil.AddonDiffUtil
import com.synrgy.aeroswift.presentation.viewholder.AddonViewHolder


class AddonAdapter: ListAdapter<AddonModels, AddonViewHolder>(
    AddonDiffUtil()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddonViewHolder {
        return AddonViewHolder(
            ItemAddonCardBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: AddonViewHolder, position: Int) {
        holder.bindData(getItem(position))
    }
}