package com.synrgy.aeroswift.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.synrgy.aeroswift.databinding.ActivityFlightHistoryBinding
import com.synrgy.aeroswift.presentation.adapter.FlightHistoryAdapter
import com.synrgy.presentation.constant.Constant
import com.synrgy.presentation.constant.FlightHistoryConstant

class FlightHistoryActivity : AppCompatActivity() {
    companion object {
        fun startActivity(context: Context) {
            context.startActivity(Intent(context, FlightHistoryActivity::class.java))
        }
    }

    private lateinit var binding: ActivityFlightHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFlightHistoryBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setAdapter()

        binding.toolbarHistory.setNavigationOnClickListener { onBackPressed() }

        binding.nestedScrollView.viewTreeObserver.addOnScrollChangedListener {
            if (binding.nestedScrollView.scrollY <= 200) {
                binding.fabScrollUp.visibility = View.GONE
            } else {
                binding.fabScrollUp.visibility = View.VISIBLE
            }
        }

        binding.fabScrollUp.setOnClickListener {
            binding.nestedScrollView.fullScroll(View.FOCUS_UP)
        }
    }

    private fun setAdapter() {
        val adapter = FlightHistoryAdapter()
        binding.flightHistoryRecycler.layoutManager = LinearLayoutManager(this)
        binding.flightHistoryRecycler.adapter = adapter
        adapter.submitList(FlightHistoryConstant.getData().filter {
            it.category == Constant.FlightHistoryCategory.COMPLETED.value
        })
    }
}