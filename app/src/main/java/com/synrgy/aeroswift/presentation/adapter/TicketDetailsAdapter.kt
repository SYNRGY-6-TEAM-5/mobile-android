package com.synrgy.aeroswift.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.synrgy.aeroswift.databinding.ItemTicketDetailsBinding
import com.synrgy.aeroswift.presentation.diffutil.TicketDetailsDiffUtil
import com.synrgy.aeroswift.presentation.viewholder.TicketDetailsViewHolder
import com.synrgy.domain.local.TicketDetails

class TicketDetailsAdapter: ListAdapter<TicketDetails, TicketDetailsViewHolder>(
    TicketDetailsDiffUtil()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketDetailsViewHolder {
        return TicketDetailsViewHolder(
            ItemTicketDetailsBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: TicketDetailsViewHolder, position: Int) {
        holder.bindData(getItem(position))
    }
}