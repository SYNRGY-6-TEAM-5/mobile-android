package com.synrgy.aeroswift.presentation.viewholder

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.synrgy.aeroswift.R
import com.synrgy.aeroswift.databinding.ItemAddonCardBinding
import com.synrgy.aeroswift.models.AddonModels

class AddonViewHolder(
    private val binding: ItemAddonCardBinding
): RecyclerView.ViewHolder(binding.root) {
    fun bindData(data: AddonModels) {
        val context = binding.root.context

        binding.tvName.text = data.name
        binding.tvCount.text = data.count

        if (data.isSeat) {
            binding.tvCount.setTextColor(ContextCompat.getColor(context, R.color.error_500))
        }
    }
}