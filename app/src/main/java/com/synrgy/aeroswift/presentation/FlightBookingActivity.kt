package com.synrgy.aeroswift.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.synrgy.aeroswift.R
import com.synrgy.aeroswift.databinding.ActivityFlightBookingBinding
import com.synrgy.aeroswift.dialog.LoadingDialog
import com.synrgy.aeroswift.presentation.adapter.FlightBookingAdapter
import com.synrgy.aeroswift.presentation.viewmodel.HomeViewModel
import com.synrgy.aeroswift.presentation.viewmodel.flight.FlightViewModel
import com.synrgy.domain.local.FlightBooking
import com.synrgy.domain.local.FlightSearch
import com.synrgy.domain.response.flight.FlightData
import com.synrgy.presentation.helper.Helper
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class FlightBookingActivity : AppCompatActivity() {
    companion object {
        fun startActivity(context: Context, bundle: Bundle? = null) {
            val intent = Intent(context, FlightBookingActivity::class.java)
            if (bundle != null) intent.putExtras(bundle)

            context.startActivity(intent)
        }
    }

    private lateinit var binding: ActivityFlightBookingBinding

    private val homeViewModel: HomeViewModel by viewModels()
    private val flightViewModel: FlightViewModel by viewModels()

    private val bookingAdapter = FlightBookingAdapter()

    private lateinit var loadingDialog: LoadingDialog

    private var departureAirport = ""
    private var arrivalAirport = ""

    private val flightBookings = mutableMapOf<Int, MutableList<FlightBooking>>()

    private var position = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFlightBookingBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        loadingDialog = LoadingDialog(this)

        departureAirport = intent.getStringExtra(SearchActivity.KEY_DEPARTURE_AIRPORT) ?: ""
        arrivalAirport = intent.getStringExtra(SearchActivity.KEY_ARRIVAL_AIRPORT) ?: ""

        binding.rvBooking.layoutManager = LinearLayoutManager(this)
        binding.rvBooking.adapter = this.bookingAdapter

        homeViewModel.getFlightSearch()
        flightViewModel.getFlights(departureAirport, arrivalAirport)
        observeViewModel()

        binding.btnSearchSchedule.setOnClickListener { handleNavigateSearch() }
        binding.tvNext.setOnClickListener {
            val lastIndex = flightBookings.size - 1
            if (position != lastIndex && flightBookings.isNotEmpty()) {
                position += 1
                handleSetPosition(position)
            }
        }
        binding.tvPrev.setOnClickListener {
            if (position != 0) {
                position -= 1
                handleSetPosition(position)
            }
        }
    }

    private fun observeViewModel() {
        homeViewModel.flightSearch.observe(this, ::handleGetFlightSearch)
        flightViewModel.loading.observe(this, ::handleLoading)
        flightViewModel.flights.observe(this, ::handleGetFlights)
    }

    private fun handleLoading(loading: Boolean) {
        if (loading) loadingDialog.startLoadingDialog() else loadingDialog.dismissDialog()
    }

    private fun handleGetFlightSearch(data: FlightSearch) {
        if (Helper.isValidFlightSearch(data)) {
            binding.tvOrigin.text = data.origin
            binding.tvDest.text = data.destination
            binding.tvOriginCity.text = data.oCity
            binding.tvDestCity.text = data.dCity
        }
    }

    private fun handleGetFlights(flights: ArrayList<FlightData>) {
        flights
            .sortedBy { it.departure?.scheduledTime }
            .forEach { flight ->
            val scheduledTimeString = flight.departure?.scheduledTime
            val weekOfYear = getWeekOfYear(scheduledTimeString, flights)
            if (!flightBookings.containsKey(weekOfYear)) {
                flightBookings[weekOfYear] = mutableListOf()
            }
            flightBookings[weekOfYear]?.add(
                FlightBooking(
                    id = flight.flightId.toString(),
                    departDate = flight.departure?.scheduledTime!!,
                    returnDate = flight.arrival?.scheduledTime!!
                )
            )
        }

        handleSetPosition(0)
    }

    private fun handleSetPosition(position: Int) {
        val lastIndex = flightBookings.size - 1
        val enabledColor = ContextCompat.getColor(this, R.color.primary_500)
        val disabledColor = ContextCompat.getColor(this, R.color.primary_200)

        binding.tvNext.setTextColor(if (lastIndex == position) disabledColor else enabledColor)
        binding.tvPrev.setTextColor(if (position == 0) disabledColor else enabledColor)

        this.bookingAdapter.submitList(flightBookings[position] ?: emptyList())
    }

    private fun getWeekOfYear(dateString: String?, flights: ArrayList<FlightData>): Int {
        if (dateString.isNullOrEmpty()) return -1
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val date = dateFormat.parse(dateString)
        val calendar = Calendar.getInstance()
        calendar.time = date ?: Date()

        val earliestDate = flights.minOf { flight ->
            val parsedDate = flight.departure?.scheduledTime?.let { dateFormat.parse(it) }
            parsedDate?.time ?: Long.MAX_VALUE
        }

        return ((calendar.timeInMillis - earliestDate) / (1000 * 60 * 60 * 24 * 7)).toInt()
    }

    private fun handleNavigateSearch() {
        val bundle = Bundle()
        val departDate = flightBookings[position]?.get(0)?.departDate
        bundle.putString(SearchActivity.KEY_DEPARTURE_AIRPORT, departureAirport)
        bundle.putString(SearchActivity.KEY_ARRIVAL_AIRPORT, arrivalAirport)
        bundle.putString(SearchActivity.KEY_DEPARTURE_DATE, Helper.formatDmyTime(departDate!!))

        SearchActivity.startActivity(this, bundle)
        this.finish()
    }
}