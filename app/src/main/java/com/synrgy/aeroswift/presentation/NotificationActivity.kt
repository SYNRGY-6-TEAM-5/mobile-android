package com.synrgy.aeroswift.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.synrgy.aeroswift.R
import com.synrgy.aeroswift.databinding.ActivityNotificationBinding
import com.synrgy.aeroswift.presentation.adapter.NotificationAdapter
import com.synrgy.domain.NotificationData
import com.synrgy.presentation.constant.NotificationConstant

class NotificationActivity : AppCompatActivity() {
    companion object {
        fun startActivity(context: Context) {
            context.startActivity(Intent(context, NotificationActivity::class.java))
        }
    }

    private lateinit var binding: ActivityNotificationBinding

    private val adapter = NotificationAdapter(::handleClickNotification)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.rvNotification.layoutManager = LinearLayoutManager(this)
        binding.rvNotification.adapter = this.adapter
        adapter.submitList(NotificationConstant.getData())

        binding.toolbarNotification.setNavigationOnClickListener { handleNavigate() }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                handleNavigate()
            }
        })

        handleSetSpinner()
    }

    private fun handleSetSpinner() {
        val spinner = binding.spinnerMore
        val adapter = ArrayAdapter(
            this,
            R.layout.item_dropdown_spinner,
            resources.getStringArray(R.array.notification_dropdown)
        )
        spinner.adapter = adapter
    }

    private fun handleNavigate() {
        HomeActivity.startActivity(this)
        this.finish()
    }

    private fun handleClickNotification(data: NotificationData) {

    }
}