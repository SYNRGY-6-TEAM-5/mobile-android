package com.synrgy.aeroswift.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.synrgy.aeroswift.databinding.ActivityAirportListBinding
import com.synrgy.aeroswift.presentation.adapter.AirportListAdapter
import com.synrgy.domain.AirportList
import com.synrgy.presentation.constant.AirportListConstant
import java.util.Locale


class AirportListActivity : AppCompatActivity() {
    companion object {
        fun startActivity(context: Context) {
            context.startActivity(Intent(context, AirportListActivity::class.java))
        }
    }

    private lateinit var binding: ActivityAirportListBinding

    private val airportList = AirportListConstant.getAirportList()

    private val allAirportListAdapter = AirportListAdapter { handleNavigate() }
    private val recentAirportListAdapter = AirportListAdapter { handleNavigate() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAirportListBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.airportListRecycler.layoutManager = LinearLayoutManager(this)
        binding.recentAirportListRecycler.layoutManager = LinearLayoutManager(this)

        this.recentAirportListAdapter.submitList(AirportListConstant.getAirportList().take(3))
        binding.recentAirportListRecycler.adapter = this.recentAirportListAdapter

        this.allAirportListAdapter.submitList(airportList)
        binding.airportListRecycler.adapter = this.allAirportListAdapter

        binding.svAirportList.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                handleSearchAirport(newText)
                return false
            }
        });

        binding.tvClearAl.setOnClickListener {
            this.recentAirportListAdapter.submitList(ArrayList<AirportList>())
        }
    }

    private fun handleSearchAirport(text: String) {
        val filteredList = ArrayList<AirportList>()

        for (item in airportList) {
            if (item.title.lowercase().contains(text.lowercase(Locale.getDefault()))) {
                filteredList.add(item)
            }
        }

        this.allAirportListAdapter.submitList(filteredList)
    }

    private fun handleNavigate() {
        FlightBookingActivity.startActivity(this)
    }
}