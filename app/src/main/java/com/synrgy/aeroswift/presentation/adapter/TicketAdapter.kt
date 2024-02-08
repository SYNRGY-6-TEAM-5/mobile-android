package com.synrgy.aeroswift.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.synrgy.aeroswift.databinding.ItemSearchFlightBinding
import com.synrgy.aeroswift.presentation.diffutil.TicketDiffUtil
import com.synrgy.aeroswift.presentation.viewholder.TicketViewHolder
import com.synrgy.domain.response.ticket.TicketData

class TicketAdapter(
    private val clickListener: () -> Unit
): ListAdapter<TicketData, TicketViewHolder>(
    TicketDiffUtil()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketViewHolder {
        return TicketViewHolder(
            ItemSearchFlightBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: TicketViewHolder, position: Int) {
        holder.bindData(getItem(position), clickListener)
    }
}