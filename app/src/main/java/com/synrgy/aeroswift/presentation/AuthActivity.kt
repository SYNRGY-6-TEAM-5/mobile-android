package com.synrgy.aeroswift.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.synrgy.aeroswift.databinding.ActivityAuthBinding
import com.synrgy.aeroswift.presentation.adapter.TabAuthAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {
    companion object {
        const val RC_SIGN_IN = 2
        const val KEY_TAB_INDEX = "tab_index"

        fun startActivity(context: Context, bundle: Bundle? = null) {
            val intent = Intent(context, AuthActivity::class.java)
            if (bundle != null) intent.putExtras(bundle)

            context.startActivity(intent)
        }
    }

    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        handleSetAdapter()
        handleSetTab()
    }

    private fun handleSetAdapter() {
        val tabAdapter = TabAuthAdapter(this)
        binding.viewPagerAuth.adapter = tabAdapter
    }

    private fun handleSetTab() {
        binding.viewPagerAuth.isUserInputEnabled = false

        binding.tabLayoutAuth.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.position?.let { binding.viewPagerAuth.setCurrentItem(it, false) }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })
        TabLayoutMediator(binding.tabLayoutAuth, binding.viewPagerAuth) { tab, position ->
            when (position) {
                0 -> tab.text = "Sign in"
                else -> tab.text = "Sign up"
            }
        }.attach()

        val tabIndex = intent.extras?.getInt(KEY_TAB_INDEX) ?: 0
        binding.viewPagerAuth.currentItem = tabIndex
    }
}