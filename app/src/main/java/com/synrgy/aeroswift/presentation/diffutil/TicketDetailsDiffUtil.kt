package com.synrgy.aeroswift.presentation.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.synrgy.domain.local.TicketDetails

class TicketDetailsDiffUtil: DiffUtil.ItemCallback<TicketDetails>() {
    override fun areItemsTheSame(oldItem: TicketDetails, newItem: TicketDetails): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: TicketDetails, newItem: TicketDetails): Boolean {
        return oldItem.id == newItem.id
    }
}