package com.synrgy.aeroswift.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.synrgy.aeroswift.databinding.ActivitySearchBinding
import com.synrgy.aeroswift.presentation.adapter.DatePagerAdapter
import com.synrgy.aeroswift.presentation.viewmodel.HomeViewModel
import com.synrgy.aeroswift.presentation.viewmodel.ticket.TicketViewModel
import com.synrgy.domain.local.FlightSearch
import com.synrgy.domain.response.ticket.TicketData
import com.synrgy.presentation.helper.Helper
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {
    companion object {
        const val KEY_DEPARTURE_AIRPORT = "key_departure_airport"
        const val KEY_ARRIVAL_AIRPORT = "key_arrival_airport"
        const val KEY_DEPARTURE_DATE = "key_departure_date"
        const val KEY_RETURN_DATE = "key_return_date"

        fun startActivity(context: Context, bundle: Bundle? = null) {
            val intent = Intent(context, SearchActivity::class.java)
            if (bundle != null) intent.putExtras(bundle)

            context.startActivity(intent)
        }
    }

    private lateinit var binding: ActivitySearchBinding

    private val homeViewModel: HomeViewModel by viewModels()
    private val ticketViewModel: TicketViewModel by viewModels()

    private val numberDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    private val dateFormat = SimpleDateFormat("d MMMM", Locale.US)
    private val calendar = Calendar.getInstance()

    private var departureAirport = ""
    private var arrivalAirport = ""
    private var departureDate = ""

    private var isFirst = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        departureAirport = intent.getStringExtra(KEY_DEPARTURE_AIRPORT) ?: ""
        arrivalAirport = intent.getStringExtra(KEY_ARRIVAL_AIRPORT) ?: ""
        departureDate = intent.getStringExtra(KEY_DEPARTURE_DATE) ?: ""

        handleSetViewPager()
        homeViewModel.getFlightSearch()
        ticketViewModel.getDepartTickets(departureAirport, arrivalAirport, departureDate)
        observeViewModel()

        binding.ivBack.setOnClickListener { onBackPressed() }
        binding.ivEdit.setOnClickListener { onBackPressed() }
    }

    private fun observeViewModel() {
        homeViewModel.flightSearch.observe(this, ::handleGetFlightSearch)
        ticketViewModel.tickets.observe(this, ::handleGetTickets)
    }

    private fun handleGetFlightSearch(data: FlightSearch) {
        if (Helper.isValidFlightSearch(data)) {
            binding.tvOrigin.text = data.origin
            binding.tvDestination.text = data.destination
            binding.tvOriginCity.text = data.oCity
            binding.tvDestinationCity.text = data.dCity

            val passenger = if (data.totalSeat!! > 1) "Passengers" else "Passenger"

            binding.searchDate.text = Helper.formatDateDay(data.depDate!!)
            binding.searchPassenger.text = "${data.totalSeat} $passenger"
            binding.searchClass.text = data.ticketClass
        }
    }

    private fun handleSetViewPager() {
        val dateAdapter = DatePagerAdapter(this)
        binding.vpTicket.adapter = dateAdapter

        binding.vpTicket.setCurrentItem(1, false)

        binding.tlDate.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.position?.let { binding.vpTicket.setCurrentItem(it, true) }

                val date = numberDateFormat.format(getDate(tab?.position!!))

                binding.searchDate.text = Helper.formatDateDay(date)

                ticketViewModel.getDepartTickets(
                    departureAirport,
                    arrivalAirport,
                    date
                )

                binding.vpTicket.requestLayout()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                binding.vpTicket.requestLayout()
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                binding.vpTicket.requestLayout()
            }
        })
    }

    private fun handleGetTickets(tickets: ArrayList<TicketData>) {
        if (tickets.isEmpty() && isFirst) {
            val bundle = Bundle()
            bundle.putString(KEY_DEPARTURE_AIRPORT, departureAirport)
            bundle.putString(KEY_ARRIVAL_AIRPORT, arrivalAirport)

            FlightBookingActivity.startActivity(this, bundle)
            this.finish()
        }

        isFirst = false

        val cheapestPrice = when (tickets.isNotEmpty()) {
            true -> tickets.sortedBy { it.fareAmount }.first().fareAmount
            false -> null
        }

        TabLayoutMediator(binding.tlDate, binding.vpTicket) { tab, position ->
            var text = "${dateFormat.format(getDate(position))}"
            if (cheapestPrice != null) text += "\n" + "${Helper.formatPrice(cheapestPrice)}"
            tab.text = text
        }.attach()
    }

    private fun getDate(position: Int): Date {
        calendar.time = Helper.formatStringDate(departureDate)!!
        calendar.add(Calendar.DAY_OF_YEAR, position - 1)
        return calendar.time
    }
}