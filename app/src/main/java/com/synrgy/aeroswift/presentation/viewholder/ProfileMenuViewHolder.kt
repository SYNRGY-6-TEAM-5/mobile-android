package com.synrgy.aeroswift.presentation.viewholder

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.synrgy.aeroswift.databinding.ItemProfileMenuBinding
import com.synrgy.domain.ProfileMenu

class ProfileMenuViewHolder(
    private val binding: ItemProfileMenuBinding
): ViewHolder(binding.root) {
    fun bindData(data: ProfileMenu) {
        binding.ivIcon.setImageResource(data.icon)
        binding.tvTitle.text = data.title
    }
}