package com.synrgy.aeroswift.presentation.viewholder

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.synrgy.aeroswift.databinding.ItemSearchFlightBinding
import com.synrgy.domain.response.ticket.TicketData
import com.synrgy.presentation.helper.Helper

class TicketViewHolder(
    private val binding: ItemSearchFlightBinding
): RecyclerView.ViewHolder(binding.root) {
    fun bindData(
        data: TicketData,
        clickListener: () -> Unit
    ) {
        binding.ivAirline.load(data.flight?.airline?.image)
        binding.tvAirlineIata.text = data.flight?.airline?.iata
        binding.tvFlightStatus.text = data.flight?.flightStatus

        val timeDeparture = data.flight?.departure?.scheduledTime!!
        val timeArrival = data.flight?.arrival?.scheduledTime!!

        binding.tvDeparture.text = data.flight?.departure?.airportDetails?.iataCode
        binding.timeDeparture.text = Helper.formatDateTime(timeDeparture)

        binding.tvArrival.text = data.flight?.arrival?.airportDetails?.iataCode
        binding.timeArrival.text = Helper.formatDateTime(timeArrival)

        binding.timeTotal.text = Helper.calculateTimeDifference(timeDeparture, timeArrival)

        binding.tvTotalPrice.text = Helper.formatPrice(data.fareAmount!!)
            .replace(",00", "")
            .replace(".", ",")

        binding.root.setOnClickListener { clickListener() }
    }
}