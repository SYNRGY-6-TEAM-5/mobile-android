package com.synrgy.aeroswift.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.synrgy.aeroswift.databinding.ActivityCheckInSuccessBinding
import com.synrgy.aeroswift.presentation.adapter.BulletListAdapter
import com.synrgy.presentation.constant.CheckInSuccessConstant

class CheckInSuccessActivity : AppCompatActivity() {
    companion object {
        fun startActivity(context: Context) {
            context.startActivity(Intent(context, CheckInSuccessActivity::class.java))
        }
    }

    private lateinit var binding: ActivityCheckInSuccessBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckInSuccessBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                handleNavigateToHome()
            }
        })

        handleSetAdapter()

        binding.btnHome.setOnClickListener { onBackPressed() }
        binding.btnBoardingPass.setOnClickListener {
            BoardingPassActivity.startActivity(this)
            this.finish()
        }
    }

    private fun handleNavigateToHome() {
        HomeActivity.startActivity(this)
        this.finish()
    }

    private fun handleSetAdapter() {
        val adapter = BulletListAdapter()
        binding.rvCheckInSuccess.layoutManager = LinearLayoutManager(this)
        binding.rvCheckInSuccess.adapter = adapter
        adapter.submitList(CheckInSuccessConstant.getData())
    }
}