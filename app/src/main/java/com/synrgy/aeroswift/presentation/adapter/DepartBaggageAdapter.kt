package com.synrgy.aeroswift.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.google.android.material.card.MaterialCardView
import com.synrgy.aeroswift.databinding.ItemDepartBaggageBinding
import com.synrgy.aeroswift.presentation.diffutil.DepartBaggageDiffUtil
import com.synrgy.aeroswift.presentation.viewholder.DepartBaggageViewHolder
import com.synrgy.domain.local.DepartBaggage

class DepartBaggageAdapter(
    private val selectedPosition: Int? = null,
    private val clickListener: (DepartBaggage) -> Unit
): ListAdapter<DepartBaggage, DepartBaggageViewHolder>(
    DepartBaggageDiffUtil()
) {
    private val cardViewList = ArrayList<MaterialCardView>()
    private val dataList = ArrayList<DepartBaggage>()

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

        if (!dataList.contains(getItem(position))) {
            dataList.add(getItem(position))
        }

        if (selectedPosition != null && position == selectedPosition) {
            holder.setActive(holder.item.itemBaggageCard)
            getItem(selectedPosition).selected = true
        }

        holder.bindData(getItem(position), cardViewList, dataList, clickListener)
    }
}