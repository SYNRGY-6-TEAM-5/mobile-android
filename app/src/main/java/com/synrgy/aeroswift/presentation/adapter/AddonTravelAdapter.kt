package com.synrgy.aeroswift.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.synrgy.aeroswift.databinding.ItemAddonTravelBinding
import com.synrgy.aeroswift.models.AddonTravelModels
import com.synrgy.aeroswift.presentation.diffutil.AddonTravelDiffUtil
import com.synrgy.aeroswift.presentation.viewholder.AddonTravelViewHolder
import com.synrgy.presentation.constant.Constant


class AddonTravelAdapter: ListAdapter<AddonTravelModels, AddonTravelViewHolder>(
    AddonTravelDiffUtil()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddonTravelViewHolder {
        return AddonTravelViewHolder(
            ItemAddonTravelBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: AddonTravelViewHolder, position: Int) {
        if (position == itemCount - 1 && getItem(itemCount - 1).id == Constant.TripType.ROUNDTRIP.value) {
            holder.item.cardEmpty.visibility = View.VISIBLE
            holder.item.layoutBottom.visibility = View.GONE
        }

        holder.bindData(getItem(position))
    }
}