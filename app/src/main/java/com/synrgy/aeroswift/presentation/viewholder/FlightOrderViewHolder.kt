package com.synrgy.aeroswift.presentation.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.synrgy.aeroswift.R
import com.synrgy.aeroswift.databinding.ItemFlightOrderBinding
import com.synrgy.domain.local.FlightOrder

class FlightOrderViewHolder(
    private val binding: ItemFlightOrderBinding
): RecyclerView.ViewHolder(binding.root) {
    fun bindData(data: FlightOrder) {
        val icon = when (data.position) {
            1 -> R.drawable.ic_money_send
            2 -> R.drawable.ic_repeate_music
            3 -> R.drawable.ic_chart_success
            4 -> R.drawable.ic_money_remove
            else -> R.drawable.ic_money_send
        }

        binding.ivIcon.setImageResource(icon)
        binding.tvTitle.text = data.title
        binding.chipCount.text = data.count.toString()
    }
}