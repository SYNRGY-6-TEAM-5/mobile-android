package com.synrgy.aeroswift.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.synrgy.aeroswift.databinding.ActivityFlightBookingBinding
import com.synrgy.aeroswift.presentation.adapter.DepartDateAdapter

class FlightBookingActivity : AppCompatActivity() {
    companion object {
        fun startActivity(context: Context) {
            context.startActivity(Intent(context, FlightBookingActivity::class.java))
        }
    }

    private lateinit var binding: ActivityFlightBookingBinding

    private val departDateAdapter = DepartDateAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFlightBookingBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.departDateRecycler.layoutManager = LinearLayoutManager(this)
        binding.departDateRecycler.adapter = this.departDateAdapter

        binding.btnSearchSchedule.setOnClickListener {
            Intent(this, FlightDetailsActivity::class.java).apply {
                startActivity(this)
            }
        }
    }
}