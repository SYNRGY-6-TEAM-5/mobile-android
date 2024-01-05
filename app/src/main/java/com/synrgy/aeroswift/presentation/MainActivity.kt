package com.synrgy.aeroswift.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.tabs.TabLayoutMediator
import com.synrgy.aeroswift.R
import com.synrgy.aeroswift.databinding.ActivityMainBinding
import com.synrgy.aeroswift.dialog.PermissionNotificationDialog
import com.synrgy.aeroswift.presentation.adapter.ScreenSlidePagerAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    companion object {
        fun startActivity(context: Context) {
            context.startActivity(Intent(context, MainActivity::class.java))
        }
    }

    private lateinit var binding: ActivityMainBinding
    private val notifDialog = PermissionNotificationDialog(MainActivity@this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val adapter = ScreenSlidePagerAdapter(this)
        binding.viewPagerOnboarding.adapter = adapter
        TabLayoutMediator(binding.tabLayoutOnboarding, binding.viewPagerOnboarding) { _, _ -> }.attach()

        binding.textSkipOnboarding.setOnClickListener {
            notifDialog.show()
        }

        binding.textNextOnboarding.setOnClickListener {
            val currPos = binding.viewPagerOnboarding.currentItem

            if ((currPos + 1) < adapter.itemCount ?: 0) {
                handleNextOnBoarding(currPos)
            } else {
                notifDialog.show()
            }
        }
    }

    private fun handleNextOnBoarding(currPos: Int) {
        binding.viewPagerOnboarding.currentItem = currPos + 1
    }
}