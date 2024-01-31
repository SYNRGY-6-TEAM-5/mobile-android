package com.synrgy.aeroswift.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.synrgy.aeroswift.databinding.ActivityPassengerBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PassengerActivity : AppCompatActivity() {
    companion object {
        fun startActivity(context: Context) {
            context.startActivity(Intent(context, PassengerActivity::class.java))
        }
    }

    private lateinit var binding: ActivityPassengerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPassengerBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}