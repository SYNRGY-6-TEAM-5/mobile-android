package com.synrgy.aeroswift.presentation.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.synrgy.aeroswift.databinding.ItemFlightHistoryBinding
import com.synrgy.domain.FlightHistory

class FlightHistoryViewHolder(
    private val binding: ItemFlightHistoryBinding
): RecyclerView.ViewHolder(binding.root) {
    fun bindData(data: FlightHistory) {
        binding.tvStatus.text = data.category
    }
}