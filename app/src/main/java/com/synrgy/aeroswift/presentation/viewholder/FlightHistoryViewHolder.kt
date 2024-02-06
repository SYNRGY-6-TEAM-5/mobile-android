package com.synrgy.aeroswift.presentation.viewholder

import android.os.CountDownTimer
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.synrgy.aeroswift.R
import com.synrgy.aeroswift.databinding.ItemFlightHistoryBinding
import com.synrgy.domain.local.FlightHistory
import com.synrgy.presentation.constant.Constant
import com.synrgy.presentation.helper.Helper
import java.util.Date

class FlightHistoryViewHolder(
    private val binding: ItemFlightHistoryBinding
): RecyclerView.ViewHolder(binding.root) {
    private lateinit var timer: CountDownTimer
    fun bindData(data: FlightHistory) {
        val context = binding.root.context

        if (data.category != Constant.FlightHistoryCategory.AWAITING_PAYMENT.value) {
            binding.tvStatus.visibility = View.VISIBLE
            binding.tvStatus.text = data.category
        }

        if (data.category == Constant.FlightHistoryCategory.AWAITING_PAYMENT.value &&
            data.total != null) {
            binding.layoutTotalPrice.visibility = View.VISIBLE
            binding.btnComplete.visibility = View.VISIBLE

            binding.tvTotalPrice.text = context.getString(R.string.depart_baggage_price, Helper.formatPrice(data.total!!))
            if (data.time != null) startTimer(data.time!!)
        }
    }

    private fun startTimer(totalTimeInMillis: Long) {
        timer = object : CountDownTimer(totalTimeInMillis - Date().time, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                updateTimerUI(millisUntilFinished)
            }

            override fun onFinish() {
                updateTimerUI(0)
            }
        }
        timer.start()
    }

    private fun updateTimerUI(timeInMillis: Long) {
        val context = binding.root.context

        val hours = timeInMillis / (1000 * 60 * 60) % 24
        val minutes = timeInMillis / (1000 * 60) % 60
        val seconds = timeInMillis / 1000 % 60
        val timerText = String.format("%02d:%02d:%02d", hours, minutes, seconds)

        binding.btnComplete.text =
            context.getString(R.string.complete_the_payment_in, timerText)
    }
}