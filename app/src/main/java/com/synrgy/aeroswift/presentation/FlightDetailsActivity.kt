package com.synrgy.aeroswift.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.synrgy.aeroswift.databinding.ActivityFlightDetailsBinding
import com.synrgy.aeroswift.dialog.AuthDialog
import com.synrgy.aeroswift.dialog.LoadingDialog
import com.synrgy.aeroswift.presentation.adapter.TicketDetailsAdapter
import com.synrgy.aeroswift.presentation.viewmodel.HomeViewModel
import com.synrgy.aeroswift.presentation.viewmodel.auth.AuthViewModel
import com.synrgy.aeroswift.presentation.viewmodel.checkout.AddonViewModel
import com.synrgy.aeroswift.presentation.viewmodel.ticket.TicketViewModel
import com.synrgy.domain.local.FlightSearch
import com.synrgy.domain.local.TicketDetails
import com.synrgy.presentation.constant.Constant
import com.synrgy.presentation.helper.Helper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FlightDetailsActivity : AppCompatActivity() {
    companion object {
        const val KEY_TICKET_ID = "key_ticket_id"

        fun startActivity(context: Context, bundle: Bundle? = null) {
            val intent = Intent(context, FlightDetailsActivity::class.java)
            if (bundle != null) intent.putExtras(bundle)

            context.startActivity(intent)
        }
    }

    private lateinit var binding: ActivityFlightDetailsBinding

    private val homeViewModel: HomeViewModel by viewModels()
    private val addonViewModel: AddonViewModel by viewModels()
    private val authViewModel: AuthViewModel by viewModels()
    private val ticketViewModel: TicketViewModel by viewModels()

    private lateinit var authDialog: AuthDialog
    private lateinit var loadingDialog: LoadingDialog

    private val adapter = TicketDetailsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFlightDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val ticketId = intent.getIntExtra(KEY_TICKET_ID, 1)

        authDialog = AuthDialog(this)
        loadingDialog = LoadingDialog(this)

        authViewModel.getUser()
        authViewModel.checkAuth()
        homeViewModel.getFlightSearch()
        ticketViewModel.getTicketById(ticketId)
        observeViewModel()

        binding.rvFlightDetails.layoutManager = LinearLayoutManager(this)
        binding.rvFlightDetails.adapter = this.adapter

        binding.btnSelectFlight.setOnClickListener {
            addonViewModel.deleteAddons()
            CheckoutActivity.startActivity(this)
        }

        binding.toolbar.setOnClickListener { onBackPressed() }
    }

    private fun observeViewModel() {
        homeViewModel.flightSearch.observe(this, ::handleFlightSearch)
        ticketViewModel.loading.observe(this, ::handleLoading)
        authViewModel.authentication.observe(this, ::handleAuthentication)
    }

    private fun handleAuthentication(token: String) {
        if (token.isEmpty() || token.isBlank()) authDialog.show()
    }

    private fun handleFlightSearch(data: FlightSearch) {
        if (Helper.isValidFlightSearch(data)) {
            val passenger = if (data.totalSeat!! > 1) "Passengers" else "Passenger"

            binding.tvDetailsDeparture.text = data.origin
            binding.tvDetailsArrival.text = data.destination
            binding.tvPassengerCount.text = "${data.totalSeat} $passenger"

            ticketViewModel.ticket.observe(this) { ticket ->
                binding.tvTotalPrice.text = Helper.formatPrice(ticket.fareAmount!!)

                val oAirport = ticket.flight?.departure!!
                val dAirport = ticket.flight?.arrival!!

                val oAirportName = oAirport.airportDetails?.airportName!!
                val dAirportName = dAirport.airportDetails?.airportName!!

                val origin = oAirport.airportDetails?.iataCode!!
                val destination = dAirport.airportDetails?.iataCode!!

                val depDate = oAirport.scheduledTime!!
                val arrivalDate = dAirport.scheduledTime!!

                val ticketList = arrayListOf(
                    TicketDetails(
                        id = Constant.TripType.ONE_WAY.value,
                        origin = origin,
                        destination = destination,
                        depDate = depDate,
                        arrivalDate = arrivalDate,
                        oAirportName = oAirportName,
                        dAirportName = dAirportName,
                        oTerminal = "Terminal ${oAirport.terminal} Domestic",
                        dTerminal = "Terminal ${dAirport.terminal} Domestic",
                        airlineCode = "${ticket.flight?.airline?.iata} - ${data.ticketClass}",
                        airlineImage = ticket.flight?.airline?.image!!,
                        airlineName = ticket.flight?.airline?.name!!,
                        status = ticket.flight?.flightStatus!!
                    )
                )

                if (data.tripType == Constant.TripType.ROUNDTRIP.value) {
                    ticketList.add(
                        TicketDetails(
                            id = Constant.TripType.ROUNDTRIP.value,
                            origin = destination,
                            destination = origin,
                            depDate = depDate,
                            arrivalDate = arrivalDate,
                            oAirportName = dAirportName,
                            dAirportName = oAirportName,
                            oTerminal = "Terminal ${dAirport.terminal} Domestic",
                            dTerminal = "Terminal ${oAirport.terminal} Domestic",
                            airlineCode = "${ticket.flight?.airline?.iata} - ${data.ticketClass}",
                            airlineImage = ticket.flight?.airline?.image!!,
                            airlineName = ticket.flight?.airline?.name!!,
                            status = ticket.flight?.flightStatus!!
                        )
                    )
                }

                this.adapter.submitList(ticketList)
            }
        }
    }

    private fun handleLoading(loading: Boolean) {
        if (loading) loadingDialog.startLoadingDialog() else loadingDialog.dismissDialog()
    }
}