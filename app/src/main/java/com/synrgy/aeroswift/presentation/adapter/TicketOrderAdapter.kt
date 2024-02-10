package com.synrgy.aeroswift.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.synrgy.aeroswift.databinding.ItemTicketOrderDetailsBinding

class TicketOrderAdapter: RecyclerView.Adapter<TicketOrderAdapter.TicketOrderViewHolder>() {

    inner class TicketOrderViewHolder(val binding: ItemTicketOrderDetailsBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketOrderViewHolder {
        val binding = ItemTicketOrderDetailsBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return TicketOrderViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return 2
    }

    override fun onBindViewHolder(holder: TicketOrderViewHolder, position: Int) {
        if (position == 1) holder.binding.tvHeading.text = "Return"
    }
}