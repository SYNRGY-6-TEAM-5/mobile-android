package com.synrgy.aeroswift.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.synrgy.aeroswift.databinding.ItemPriceDetailsBinding
import com.synrgy.aeroswift.presentation.diffutil.PriceDetailsDiffUtil
import com.synrgy.aeroswift.presentation.viewholder.PriceDetailsViewHolder
import com.synrgy.domain.local.PriceDetails

class PriceDetailsAdapter: ListAdapter<PriceDetails, PriceDetailsViewHolder>(
    PriceDetailsDiffUtil()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PriceDetailsViewHolder {
        return PriceDetailsViewHolder(
            ItemPriceDetailsBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: PriceDetailsViewHolder, position: Int) {
        holder.bindData(getItem(position))
    }
}