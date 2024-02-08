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
import com.synrgy.domain.local.FlightSearch
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

    private val dateFormat = SimpleDateFormat("d MMMM", Locale.US)
    private val calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                HomeActivity.startActivity(this@SearchActivity)
                this@SearchActivity.finish()
            }
        })

        homeViewModel.getFlightSearch()
        observeViewModel()
        handleSetViewPager()

        binding.ivBack.setOnClickListener { onBackPressed() }
        binding.ivEdit.setOnClickListener { onBackPressed() }
    }

    private fun observeViewModel() {
        homeViewModel.flightSearch.observe(this, ::handleGetFlightSearch)
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

        TabLayoutMediator(binding.tlDate, binding.vpTicket) { tab, position ->
            tab.text = dateFormat.format(getDate(position))
        }.attach()

        binding.vpTicket.setCurrentItem(1, false)

        binding.tlDate.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.position?.let { binding.vpTicket.setCurrentItem(it, true) }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun getDate(position: Int): Date {
        val departureDate = intent.getStringExtra(KEY_DEPARTURE_DATE)
        calendar.time = if (departureDate != null) Helper.formatStringDate(departureDate)!! else Date()
        calendar.add(Calendar.DAY_OF_YEAR, position - 1)
        return calendar.time
    }
}