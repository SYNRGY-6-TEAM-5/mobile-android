package com.synrgy.aeroswift.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import com.synrgy.aeroswift.databinding.ActivityOrderDetailBinding
import java.util.Locale

class OrderDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOrderDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        startTimer()
        binding.toolbarOrderDetail.setNavigationOnClickListener { onBackPressed() }
    }

    private fun startTimer() {
        val timer = object : CountDownTimer(3600000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val seconds = (millisUntilFinished / 1000).toInt()
                val minutes = seconds / 60
                val hours = minutes / 60

                val displaySeconds = seconds % 60
                val displayMinutes = minutes % 60

                val time = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, displayMinutes, displaySeconds)

                binding.btnCompletePayment.text = "Complete the Payment in $time"
                binding.tvTimer.text = time
            }

            override fun onFinish() {
                binding.btnCompletePayment.text = "Complete the Payment"
            }
        }
        timer.start()
    }

    companion object {
        fun startActivity(context: Context) {
            context.startActivity(Intent(context, OrderDetailActivity::class.java))
        }
    }
}