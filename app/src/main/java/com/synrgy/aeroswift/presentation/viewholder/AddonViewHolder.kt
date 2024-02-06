package com.synrgy.aeroswift.presentation.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.synrgy.aeroswift.databinding.ItemAddonCardBinding
import com.synrgy.aeroswift.models.AddonModels

class AddonViewHolder(
    private val binding: ItemAddonCardBinding
): RecyclerView.ViewHolder(binding.root) {
    fun bindData(data: AddonModels) {
        binding.tvName.text = data.name
        binding.tvCount.text = data.count
    }
}