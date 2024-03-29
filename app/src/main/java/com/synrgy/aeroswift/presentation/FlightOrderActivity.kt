package com.synrgy.aeroswift.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.synrgy.aeroswift.databinding.ActivityFlightOrderBinding

class FlightOrderActivity : AppCompatActivity() {
    companion object {
        fun startActivity(context: Context) {
            context.startActivity(Intent(context, FlightOrderActivity::class.java))
        }
    }

    private lateinit var binding: ActivityFlightOrderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFlightOrderBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}