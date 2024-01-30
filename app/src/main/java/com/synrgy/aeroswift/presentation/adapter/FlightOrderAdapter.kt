package com.synrgy.aeroswift.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.synrgy.aeroswift.databinding.ItemFlightOrderBinding
import com.synrgy.aeroswift.presentation.diffutil.FlightOrderDiffUtil
import com.synrgy.aeroswift.presentation.viewholder.FlightOrderViewHolder
import com.synrgy.domain.FlightOrder

class FlightOrderAdapter(
    private val clickListener: (FlightOrder) -> Unit
): ListAdapter<FlightOrder, FlightOrderViewHolder>(
    FlightOrderDiffUtil()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlightOrderViewHolder {
        return FlightOrderViewHolder(
            ItemFlightOrderBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: FlightOrderViewHolder, position: Int) {
        holder.bindData(getItem(position))

        holder.itemView.setOnClickListener { clickListener(getItem(position)) }
    }
}