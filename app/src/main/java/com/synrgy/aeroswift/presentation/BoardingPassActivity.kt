package com.synrgy.aeroswift.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.synrgy.aeroswift.R
import com.synrgy.aeroswift.databinding.ActivityBoardingPassBinding
import com.synrgy.aeroswift.presentation.viewmodel.passenger.PassengerDetailsViewModel
import com.synrgy.domain.local.PassengerData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BoardingPassActivity : AppCompatActivity() {
    companion object {
        fun startActivity(context: Context) {
            context.startActivity(Intent(context, BoardingPassActivity::class.java))
        }
    }

    private lateinit var binding: ActivityBoardingPassBinding

    private val passengerViewModel: PassengerDetailsViewModel by viewModels()

    private var passengerList = mutableListOf<PassengerData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBoardingPassBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                handleNavigateToHome()
            }
        })

        passengerViewModel.getPassengers()
        observeViewModel()

        binding.toolbarBoardingPass.bringToFront()
        binding.btnHome.setOnClickListener { onBackPressed() }
    }

    private fun observeViewModel() {
        passengerViewModel.passengers.observe(this, ::handleSetSpinner)
    }

    private fun handleSetSpinner(data: List<PassengerData>) {
        if (data.isNotEmpty()) {
            passengerList = data.toMutableList()
            val spinner = binding.tvPassengers
            val adapter = ArrayAdapter(
                this,
                R.layout.item_dropdown_spinner,
                data.map { it.name }
            )
            spinner.adapter = adapter
        }
    }

    private fun handleNavigateToHome() {
        HomeActivity.startActivity(this)
        this.finish()
    }
}