package com.synrgy.aeroswift.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.synrgy.aeroswift.R
import com.synrgy.aeroswift.databinding.ActivityHomeBinding
import com.synrgy.aeroswift.presentation.fragment.FlightFragment
import com.synrgy.aeroswift.presentation.fragment.HomeFragment
import com.synrgy.aeroswift.presentation.fragment.ProfileFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    companion object {
        const val KEY_FRAGMENT_INDEX = "key_fragment_index"

        fun startActivity(context: Context, bundle: Bundle? = null) {
            val intent = Intent(context, HomeActivity::class.java)
            if (bundle != null) intent.putExtras(bundle)

            context.startActivity(intent)
        }
    }

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val bundle = intent.extras

        when (bundle?.getInt(KEY_FRAGMENT_INDEX) ?: 0) {
            0 -> {
                replaceFragment(HomeFragment())
                binding.homeBottomNavigation.selectedItemId = R.id.navigation_home
            }
            2 -> {
                replaceFragment(ProfileFragment())
                binding.homeBottomNavigation.selectedItemId = R.id.navigation_profile
            }
        }

        binding.homeBottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_home -> replaceFragment(HomeFragment())
                R.id.navigation_flight -> replaceFragment(FlightFragment())
                R.id.navigation_profile -> replaceFragment(ProfileFragment())
                else -> {
                }
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.home_frame_layout, fragment)
        fragmentTransaction.commit()
    }
}