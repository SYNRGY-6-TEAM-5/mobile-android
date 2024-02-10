package com.synrgy.aeroswift.presentation.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.synrgy.aeroswift.databinding.ItemAirportListBinding
import com.synrgy.domain.response.airport.AirportData

class AirportListViewHolder(
    private val binding: ItemAirportListBinding
): RecyclerView.ViewHolder(binding.root) {
    fun bindData(data: AirportData, clickListener: (AirportData) -> Unit) {
        binding.root.setOnClickListener { clickListener(data) }
        binding.tv1AirportList.text = "${data.cityName} (${data.cityIataCode}), ${data.countryIsoCode}"
        binding.tv2AirportList.text = "${data.airPortName} (${data.iataCode})"
        binding.tv3AirportList.text = data.iataCode
    }
}