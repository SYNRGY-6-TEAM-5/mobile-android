package com.synrgy.aeroswift.presentation.adapter

import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.synrgy.aeroswift.databinding.ItemActiveOrderBinding
import com.synrgy.aeroswift.presentation.OrderDetailActivity
import java.util.Locale


class ActiveOrderAdapter : RecyclerView.Adapter<ActiveOrderAdapter.ActiveOrderViewHolder>() {

    inner class ActiveOrderViewHolder(val binding: ItemActiveOrderBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActiveOrderViewHolder {
        val binding = ItemActiveOrderBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ActiveOrderViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return 3
    }

    override fun onBindViewHolder(holder: ActiveOrderViewHolder, position: Int) {
        // Timer for 1 hour
        val timer = object : CountDownTimer(3600000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val seconds = (millisUntilFinished / 1000).toInt()
                val minutes = seconds / 60
                val hours = minutes / 60

                val displaySeconds = seconds % 60
                val displayMinutes = minutes % 60

                val time = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, displayMinutes, displaySeconds)

                holder.binding.btnCompletePayment.text = "Complete the Payment in $time"
            }

            // When the timer is finished
            override fun onFinish() {
                holder.binding.btnCompletePayment.text = "Complete the Payment"
            }
        }
        // Start the timer
        timer.start()
        // When the button is clicked
        holder.binding.btnCompletePayment.setOnClickListener {
            getItemId(position)
            OrderDetailActivity.startActivity(holder.itemView.context)
        }


    }
}
