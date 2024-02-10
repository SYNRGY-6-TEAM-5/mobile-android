package com.synrgy.aeroswift.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.synrgy.aeroswift.databinding.ActivityCheckoutBinding
import com.synrgy.aeroswift.dialog.AuthDialog
import com.synrgy.aeroswift.presentation.viewmodel.auth.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CheckoutActivity : AppCompatActivity() {
    companion object {
        fun startActivity(context: Context) {
            context.startActivity(Intent(context, CheckoutActivity::class.java))
        }
    }

    private lateinit var binding: ActivityCheckoutBinding

    private val authViewModel: AuthViewModel by viewModels()

    private lateinit var authDialog: AuthDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckoutBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        authDialog = AuthDialog(this)

        authViewModel.getUser()
        authViewModel.checkAuth()
        observeViewModel()
    }

    private fun observeViewModel() {
        authViewModel.authentication.observe(this, ::handleAuthentication)
    }

    private fun handleAuthentication(token: String) {
        if (token.isEmpty() || token.isBlank()) authDialog.show()
    }
}