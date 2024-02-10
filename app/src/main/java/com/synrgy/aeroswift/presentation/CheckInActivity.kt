package com.synrgy.aeroswift.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.synrgy.aeroswift.databinding.ActivityCheckInBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CheckInActivity : AppCompatActivity() {
    companion object {
        fun startActivity(context: Context) {
            context.startActivity(Intent(context, CheckInActivity::class.java))
        }
    }

    private lateinit var binding: ActivityCheckInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckInBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}