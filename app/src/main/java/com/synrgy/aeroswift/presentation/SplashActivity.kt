package com.synrgy.aeroswift.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.activity.viewModels
import com.synrgy.aeroswift.R
import com.synrgy.aeroswift.databinding.ActivityMainBinding
import com.synrgy.aeroswift.databinding.ActivitySplashBinding
import com.synrgy.aeroswift.presentation.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        authViewModel.checkAuth()

        authViewModel.authentication.observe(this, ::handleNavigateToActivity)
    }

    private fun handleNavigateToActivity(token: String) {
        Handler().postDelayed({
            if (token.isNotEmpty() && token.isNotBlank()) {
                authViewModel.setToken(token)
                HomeActivity.startActivity(this)
                this.finish()
            } else {
                MainActivity.startActivity(this)
                this.finish()
            }
        }, 2000)
    }
}