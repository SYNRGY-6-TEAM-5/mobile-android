package com.synrgy.aeroswift.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.synrgy.aeroswift.databinding.ActivityBoardingPassDocBinding

class BoardingPassDocActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBoardingPassDocBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBoardingPassDocBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}