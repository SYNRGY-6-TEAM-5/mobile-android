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

    private var navController: NavController? = null
    private var binding: ActivityForgotPasswordBinding? = null

    companion object {
        fun startActivity(context: Context, bundle: Bundle? = null) {
            val intent = Intent(context, ForgotPasswordActivity::class.java)
            if (bundle != null) intent.putExtras(bundle)

            context.startActivity(intent)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)

        setContentView(binding?.root)

        navController = findNavController(R.id.nav_container)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController?.navigateUp() == true || super.onSupportNavigateUp()
    }
}