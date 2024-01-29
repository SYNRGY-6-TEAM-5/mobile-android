package com.synrgy.aeroswift.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.synrgy.aeroswift.databinding.ActivityNotifSetBinding

class NotifSetActivity : AppCompatActivity() {
    companion object {
        fun startActivity(context: Context) {
            context.startActivity(Intent(context, NotifSetActivity::class.java))
        }
    }

    private lateinit var binding: ActivityNotifSetBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotifSetBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                HomeActivity.startProfileFragment(this@NotifSetActivity)
                this@NotifSetActivity.finish()
            }
        })

        binding.toolbarNotif.setNavigationOnClickListener { onBackPressed() }
    }
}