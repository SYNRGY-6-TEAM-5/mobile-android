package com.synrgy.aeroswift.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.synrgy.aeroswift.databinding.ItemPassengerListBinding
import com.synrgy.aeroswift.presentation.diffutil.PassengerDiffUtil
import com.synrgy.aeroswift.presentation.viewholder.PassengerViewHolder
import com.synrgy.domain.PassengerData

class PassengerAdapter(
    private val clickListener: (PassengerData) -> Unit
): ListAdapter<PassengerData, PassengerViewHolder>(
    PassengerDiffUtil()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PassengerViewHolder {
        return PassengerViewHolder(
            ItemPassengerListBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: PassengerViewHolder, position: Int) {
        holder.bindData(getItem(position))

        holder.itemView.setOnClickListener { clickListener(getItem(position)) }
    }
}