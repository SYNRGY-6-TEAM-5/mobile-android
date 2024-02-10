package com.synrgy.aeroswift.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.synrgy.aeroswift.databinding.ItemFlightHistoryBinding
import com.synrgy.aeroswift.presentation.diffutil.FlightHistoryDiffUtil
import com.synrgy.aeroswift.presentation.viewholder.FlightHistoryViewHolder
import com.synrgy.domain.local.FlightHistory

class FlightHistoryAdapter(
    private val clickListener: () -> Unit = {}
): ListAdapter<FlightHistory, FlightHistoryViewHolder>(
    FlightHistoryDiffUtil()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlightHistoryViewHolder {
        return FlightHistoryViewHolder(
            ItemFlightHistoryBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: FlightHistoryViewHolder, position: Int) {
        holder.bindData(getItem(position), clickListener)
    }
}