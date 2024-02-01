package com.synrgy.aeroswift.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.synrgy.aeroswift.databinding.ActivityFlightDetailsBinding
import com.synrgy.aeroswift.presentation.adapter.TicketDetailsAdapter
import com.synrgy.aeroswift.presentation.viewmodel.HomeViewModel
import com.synrgy.domain.local.FlightSearch
import com.synrgy.domain.local.TicketDetails
import com.synrgy.presentation.constant.Constant
import com.synrgy.presentation.helper.Helper
import dagger.hilt.android.AndroidEntryPoint
import koleton.api.hideSkeleton
import koleton.api.loadSkeleton

@AndroidEntryPoint
class FlightDetailsActivity : AppCompatActivity() {
    companion object {
        fun startActivity(context: Context) {
            context.startActivity(Intent(context, FlightDetailsActivity::class.java))
        }
    }

    private lateinit var binding: ActivityFlightDetailsBinding

    private val homeViewModel: HomeViewModel by viewModels()

    private val adapter = TicketDetailsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFlightDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        homeViewModel.getFlightSearch()
        observeViewModel()

        binding.rvFlightDetails.layoutManager = LinearLayoutManager(this)
        binding.rvFlightDetails.adapter = this.adapter

        binding.btnSelectFlight.setOnClickListener {
            CheckoutActivity.startActivity(this)
        }

        binding.toolbar.setOnClickListener { onBackPressed() }
    }

    private fun observeViewModel() {
        homeViewModel.flightSearch.observe(this, ::handleFlightSearch)
        homeViewModel.loading.observe(this, ::handleLoading)
    }

    private fun handleFlightSearch(data: FlightSearch) {
        if (Helper.isValidFlightSearch(data)) {
            val passenger = if (data.totalSeat!! > 1) "Passengers" else "Passenger"

            binding.tvDetailsDeparture.text = data.origin
            binding.tvDetailsArrival.text = data.destination
            binding.tvPassengerCount.text = "${data.totalSeat} $passenger"

            val ticketList = arrayListOf(
                TicketDetails(
                    id = Constant.TripType.ONE_WAY.value,
                    origin = data.origin!!,
                    destination = data.destination!!,
                    date = data.depDate!!,
                    oAirportName = "Yogyakarta Kulon Progo",
                    dAirportName = "Soekarno Hatta",
                    oTerminal = "Terminal 1 Domestic",
                    dTerminal = "Terminal 3 Domestic"
                )
            )

            if (data.tripType == Constant.TripType.ROUNDTRIP.value) {
                ticketList.add(
                    TicketDetails(
                        id = Constant.TripType.ROUNDTRIP.value,
                        origin = data.destination!!,
                        destination = data.origin!!,
                        date = data.retDate!!,
                        oAirportName = "Soekarno Hatta",
                        dAirportName = "Yogyakarta Kulon Progo",
                        oTerminal = "Terminal 3 Domestic",
                        dTerminal = "Terminal 1 Domestic"
                    )
                )
            }

            this.adapter.submitList(ticketList)
        }
    }

    private fun handleLoading(loading: Boolean) {
        if (loading) binding.layout.loadSkeleton() else binding.layout.hideSkeleton()
    }
}