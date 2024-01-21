package com.synrgy.aeroswift.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.synrgy.aeroswift.databinding.ActivityForgotPasswordBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgotPasswordActivity : AppCompatActivity() {
    companion object {
        const val KEY_EMAIL_RECOVERY = "email_recovery"
        const val KEY_TIMESTAMP_RECOVERY = "timestamp_recovery"

        fun startActivity(context: Context, bundle: Bundle? = null) {
            val intent = Intent(context, ForgotPasswordActivity::class.java)
            if (bundle != null) intent.putExtras(bundle)

            context.startActivity(intent)
        }
    }

    private lateinit var binding: ActivityForgotPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                AuthActivity.startActivity(this@ForgotPasswordActivity)
                this@ForgotPasswordActivity.finish()
            }
        })
    }
}