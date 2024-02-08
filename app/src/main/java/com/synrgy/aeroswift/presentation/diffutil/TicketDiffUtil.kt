package com.synrgy.aeroswift.presentation.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.synrgy.domain.response.ticket.TicketData

class TicketDiffUtil: DiffUtil.ItemCallback<TicketData>() {
    override fun areItemsTheSame(oldItem: TicketData, newItem: TicketData): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: TicketData, newItem: TicketData): Boolean {
        return oldItem.ticketId == newItem.ticketId
    }
}