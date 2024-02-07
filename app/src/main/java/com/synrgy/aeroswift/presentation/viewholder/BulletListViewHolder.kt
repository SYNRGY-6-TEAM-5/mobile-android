package com.synrgy.aeroswift.presentation.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.synrgy.aeroswift.databinding.ItemBulletListBinding
import com.synrgy.domain.local.BulletList

class BulletListViewHolder(
    private val binding: ItemBulletListBinding
): RecyclerView.ViewHolder(binding.root) {
    fun bindData(data: BulletList) {
        binding.tvText.text = data.text
    }
}