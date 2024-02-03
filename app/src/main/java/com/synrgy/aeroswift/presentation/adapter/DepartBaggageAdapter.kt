package com.synrgy.aeroswift.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import com.google.android.material.card.MaterialCardView
import com.synrgy.aeroswift.R
import com.synrgy.aeroswift.databinding.ItemDepartBaggageBinding
import com.synrgy.aeroswift.presentation.diffutil.DepartBaggageDiffUtil
import com.synrgy.aeroswift.presentation.viewholder.DepartBaggageViewHolder
import com.synrgy.domain.local.DepartBaggage

class DepartBaggageAdapter(
    private val selectedItem: Int? = null,
    private val clickListener: (DepartBaggage) -> Unit
): ListAdapter<DepartBaggage, DepartBaggageViewHolder>(
    DepartBaggageDiffUtil()
) {
    private val cardViewList = ArrayList<MaterialCardView>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DepartBaggageViewHolder {
        return DepartBaggageViewHolder(
            ItemDepartBaggageBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: DepartBaggageViewHolder, position: Int) {
        if (!cardViewList.contains(holder.cardView)) {
            cardViewList.add(holder.cardView)
        }

        if (selectedItem != null && position == selectedItem) {
            holder.setActive(holder.item.itemBaggageCard)
        }

        holder.bindData(getItem(position), cardViewList, clickListener)
    }
}