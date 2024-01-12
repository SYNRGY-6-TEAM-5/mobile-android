package com.synrgy.aeroswift.presentation.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.synrgy.aeroswift.R
import com.synrgy.aeroswift.presentation.diffutil.AirportListDiffUtil
import com.synrgy.aeroswift.presentation.viewholder.AirportListViewHolder
import com.synrgy.domain.AirportList


class AirportListAdapter(
    private val clickListener: () -> Unit
): ListAdapter<AirportList, AirportListViewHolder>(
    AirportListDiffUtil()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AirportListViewHolder {
        return AirportListViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_airport_list,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: AirportListViewHolder, position: Int) {
        holder.bindData(getItem(position))

        holder.itemView.setOnClickListener {
            clickListener()
        }
    }
}