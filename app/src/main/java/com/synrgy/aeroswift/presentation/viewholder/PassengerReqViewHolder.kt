package com.synrgy.aeroswift.presentation.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.synrgy.aeroswift.databinding.ItemPassengerRequirementBinding
import com.synrgy.domain.local.PassengerReq

class PassengerReqViewHolder(
    private val binding: ItemPassengerRequirementBinding
): RecyclerView.ViewHolder(binding.root) {
    val item = binding

    fun bindData(data: PassengerReq) {
        binding.ivPassenger.setImageResource(data.image)
        binding.tvPassenger.text = data.text
    }
}