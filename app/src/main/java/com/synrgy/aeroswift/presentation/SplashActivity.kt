package com.synrgy.aeroswift.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.synrgy.aeroswift.R
import com.synrgy.aeroswift.databinding.ActivityMainBinding
import com.synrgy.aeroswift.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        Handler().postDelayed({
            MainActivity.startActivity(this)
        }, 2000)
    }
}