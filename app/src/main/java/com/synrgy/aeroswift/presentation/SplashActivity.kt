package com.synrgy.aeroswift.presentation

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.synrgy.aeroswift.databinding.ActivitySplashBinding
import com.synrgy.aeroswift.presentation.viewmodel.auth.AuthViewModel
import com.synrgy.aeroswift.presentation.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding

    private val authViewModel: AuthViewModel by viewModels()
    private val mainViewModel: MainViewModel by viewModels()

    private val coroutineScope: CoroutineScope = CoroutineScope(Job() + Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        authViewModel.getUser()
        mainViewModel.checkNewUser()
        authViewModel.checkAuth()

        authViewModel.authentication.observe(this, ::handleNavigateToActivity)
    }

    private fun handleNavigateToActivity(token: String) {
        coroutineScope.launch {
            delay(2000)

            if (token.isNotEmpty() && token.isNotBlank()) {
                mainViewModel.setNewUser(false)
                authViewModel.setToken(token)

                HomeActivity.startActivity(this@SplashActivity)
                this@SplashActivity.finish()
            } else {
                mainViewModel.newUser.observe(this@SplashActivity, ::handleIsNewUser)
            }
        }
    }

    private fun handleIsNewUser(isNewUser: Boolean) {
        if (isNewUser) {
            mainViewModel.setNewUser(true)
            MainActivity.startActivity(this)
            this.finish()
        } else {
            mainViewModel.setNewUser(false)
            AuthActivity.startActivity(this)
            this.finish()
        }
    }
}