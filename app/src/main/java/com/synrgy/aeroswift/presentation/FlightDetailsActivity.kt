package com.synrgy.aeroswift.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.synrgy.aeroswift.databinding.ActivityFlightDetailsBinding

class FlightDetailsActivity : AppCompatActivity() {
    companion object {
        fun startActivity(context: Context) {
            context.startActivity(Intent(context, FlightDetailsActivity::class.java))
        }
    }

    private lateinit var binding: ActivityFlightDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFlightDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnSelectFlight.setOnClickListener {
            CheckoutActivity.startActivity(this)
        }

        binding.toolbar.setOnClickListener {
            HomeActivity.startActivity(this)
            this.finish()
        }
    }
}