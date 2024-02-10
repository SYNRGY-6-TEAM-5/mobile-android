package com.synrgy.aeroswift.presentation.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.synrgy.aeroswift.databinding.ItemFlightBookingBinding
import com.synrgy.domain.local.FlightBooking
import com.synrgy.presentation.helper.Helper

class FlightBookingViewHolder(
    private val binding: ItemFlightBookingBinding
): RecyclerView.ViewHolder(binding.root) {
    fun bindData(data: FlightBooking) {
        binding.tvDepartDate.text = Helper.formatScheduledTime(data.departDate)
        binding.tvReturnDate.text = Helper.formatScheduledTime(data.returnDate)
    }
}