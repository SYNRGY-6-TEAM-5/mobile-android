package com.synrgy.aeroswift.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.synrgy.aeroswift.databinding.ItemAirportListBinding
import com.synrgy.aeroswift.presentation.diffutil.AirportListDiffUtil
import com.synrgy.aeroswift.presentation.viewholder.AirportListViewHolder
import com.synrgy.domain.response.airport.AirportData


class AirportListAdapter(
    private val clickListener: (AirportData) -> Unit
): ListAdapter<AirportData, AirportListViewHolder>(
    AirportListDiffUtil()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AirportListViewHolder {
        return AirportListViewHolder(
            ItemAirportListBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: AirportListViewHolder, position: Int) {
        if (position in 0..<itemCount) {
            holder.bindData(getItem(position), clickListener)
        }
    }
}