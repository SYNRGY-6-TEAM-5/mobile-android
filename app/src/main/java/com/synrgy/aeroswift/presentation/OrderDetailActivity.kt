package com.synrgy.aeroswift.presentation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import com.synrgy.aeroswift.R
import com.synrgy.aeroswift.databinding.ActivityOrderDetailBinding

class OrderDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOrderDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startTimer()

    }

    private fun startTimer() {
        // Timer for 1 hour
        val timer = object : CountDownTimer(3600000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val hour = millisUntilFinished / 3600000
                val minute = (millisUntilFinished % 3600000) / 60000
                val second = (millisUntilFinished % 60000) / 1000
                val time = "$hour:$minute:$second"
                binding.btnCompletePayment.text = "Complete the Payment in $time"
                binding.tvTimer.text = time
            }

            // When the timer is finished
            override fun onFinish() {
                binding.btnCompletePayment.text = "Complete the Payment"
            }
        }
        // Start the timer
        timer.start()
    }

    companion object {
        fun startActivity(context: Context) {
            context.startActivity(Intent(context, OrderDetailActivity::class.java))
        }
    }
}