package com.synrgy.aeroswift.presentation.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.synrgy.aeroswift.databinding.ItemPassengerListBinding
import com.synrgy.domain.local.PassengerData

class PassengerViewHolder(
    private val binding: ItemPassengerListBinding
): RecyclerView.ViewHolder(binding.root) {
    val item = binding

    fun bindData(data: PassengerData) {
        binding.tvName.text = data.name
    }
}