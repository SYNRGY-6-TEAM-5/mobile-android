package com.synrgy.aeroswift.presentation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.synrgy.aeroswift.R
import com.synrgy.aeroswift.databinding.ActivityForgotPasswordBinding

class ForgotPasswordActivity : AppCompatActivity() {
    companion object {
        fun startActivity(context: Context, bundle: Bundle? = null) {
            val intent = Intent(context, ForgotPasswordActivity::class.java)
            if (bundle != null) intent.putExtras(bundle)

            context.startActivity(intent)
        }
    }

    private lateinit var navController: NavController
    private lateinit var binding: ActivityForgotPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = findNavController(R.id.nav_container)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}