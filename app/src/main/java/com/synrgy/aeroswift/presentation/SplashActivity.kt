package com.synrgy.aeroswift.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.activity.viewModels
import com.synrgy.aeroswift.R
import com.synrgy.aeroswift.databinding.ActivityMainBinding
import com.synrgy.aeroswift.databinding.ActivitySplashBinding
import com.synrgy.aeroswift.presentation.viewmodel.AuthViewModel
import com.synrgy.aeroswift.presentation.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    private val authViewModel: AuthViewModel by viewModels()
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        mainViewModel.checkNewUser()
        authViewModel.checkAuth()

        authViewModel.authentication.observe(this, ::handleNavigateToActivity)
    }

    private fun handleNavigateToActivity(token: String) {
        Handler().postDelayed({
            if (token.isNotEmpty() && token.isNotBlank()) {
                mainViewModel.setNewUser(false)
                authViewModel.setToken(token)

                HomeActivity.startActivity(this)
                this.finish()
            } else {
                mainViewModel.newUser.observe(this, ::handleIsNewUser)
            }
        }, 2000)
    }

    private fun handleIsNewUser(isNewUser: Boolean) {
        if (isNewUser) {
            mainViewModel.setNewUser(true)
            MainActivity.startActivity(this)
            this.finish()
        } else {
            mainViewModel.setNewUser(false)
            LoginActivity.startActivity(this)
            this.finish()
        }
    }
}