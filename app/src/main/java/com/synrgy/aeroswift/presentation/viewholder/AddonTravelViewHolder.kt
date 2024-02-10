package com.synrgy.aeroswift.presentation.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.synrgy.aeroswift.R
import com.synrgy.aeroswift.databinding.ItemAddonTravelBinding
import com.synrgy.aeroswift.models.AddonTravelModels
import com.synrgy.presentation.helper.Helper

class AddonTravelViewHolder(
    private val binding: ItemAddonTravelBinding
): RecyclerView.ViewHolder(binding.root) {
    val item = binding

    fun bindData(data: AddonTravelModels) {
        val context = binding.root.context

        binding.tvTrip.text = data.trip
        binding.tvPrice.text = context.getString(R.string.depart_baggage_price, Helper.formatPrice(data.price))
        binding.tvOrigin.text = data.origin
        binding.tvDest.text = data.destination
        binding.tvName.text = data.name
        binding.tvCount.text = data.count
    }
}