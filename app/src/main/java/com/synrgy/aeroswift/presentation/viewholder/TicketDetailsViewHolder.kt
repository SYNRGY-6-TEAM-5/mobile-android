package com.synrgy.aeroswift.presentation.viewholder

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.synrgy.aeroswift.databinding.ItemTicketDetailsBinding
import com.synrgy.domain.local.TicketDetails
import com.synrgy.presentation.helper.Helper

class TicketDetailsViewHolder(
    private val binding: ItemTicketDetailsBinding
): RecyclerView.ViewHolder(binding.root) {
    fun bindData(data: TicketDetails) {
        val date = Helper.formatScheduledDay(data.date)

        binding.tvDepartDateDetails.text = date
        binding.tvArrivalDateDetails.text = date
        binding.tvDeparture.text = data.origin
        binding.tvArrive.text = data.destination
        binding.tvCityDeparture.text = data.oAirportName
        binding.terminalDomesticDeparture.text = data.oTerminal
        binding.tvAirportArrive.text = data.dAirportName
        binding.terminalDomesticArrive.text = data.dTerminal

        binding.imgDepartureFlight.load(data.airlineImage)
        binding.tvPlaneDeparture.text = data.airlineName
        binding.tvPlaneTypeDeparture.text = data.airlineCode
    }
}