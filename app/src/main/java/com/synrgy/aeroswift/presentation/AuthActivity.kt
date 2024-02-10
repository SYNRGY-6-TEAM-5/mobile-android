package com.synrgy.aeroswift.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.synrgy.aeroswift.databinding.ActivityAuthBinding
import com.synrgy.aeroswift.presentation.adapter.TabAuthAdapter
import com.synrgy.aeroswift.presentation.viewmodel.auth.AuthViewModel
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

    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        authViewModel.checkAuth()
        observeViewModel()

        handleSetAdapter()
        handleSetTab()
    }

    private fun observeViewModel() {
        authViewModel.authentication.observe(this, ::handleAuthentication)
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

    private fun handleAuthentication(token: String) {
        if (token.isNotEmpty() && token.isNotBlank()) {
            authViewModel.setToken(token)
            handleNavigateToHome()
        }
    }

    private fun handleNavigateToHome() {
        HomeActivity.startActivity(this)
        this.finish()
    }
}