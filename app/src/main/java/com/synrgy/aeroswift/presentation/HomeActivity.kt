package com.synrgy.aeroswift.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.synrgy.aeroswift.R
import com.synrgy.aeroswift.databinding.ActivityHomeBinding
import com.synrgy.aeroswift.dialog.AuthDialog
import com.synrgy.aeroswift.presentation.fragment.FlightFragment
import com.synrgy.aeroswift.presentation.fragment.HomeFragment
import com.synrgy.aeroswift.presentation.fragment.ProfileFragment
import com.synrgy.aeroswift.presentation.viewmodel.auth.AuthViewModel
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

        fun startProfileFragment(context: Context) {
            val bundle = Bundle()
            bundle.putInt(KEY_FRAGMENT_INDEX, 2)

            val intent = Intent(context, HomeActivity::class.java)
            intent.putExtras(bundle)

            context.startActivity(intent)
        }
    }

    private lateinit var binding: ActivityHomeBinding

    private val authViewModel: AuthViewModel by viewModels()

    private lateinit var authDialog: AuthDialog

    private lateinit var token: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        authDialog = AuthDialog(this)

        authViewModel.getUser()
        authViewModel.checkAuth()
        observeViewModel()

        val bundle = intent.extras

        when (bundle?.getInt(KEY_FRAGMENT_INDEX) ?: 0) {
            0 -> {
                replaceFragment(HomeFragment())
                binding.homeBottomNavigation.selectedItemId = R.id.navigation_home
            }
            1 -> {
                replaceFragment(FlightFragment())
                binding.homeBottomNavigation.selectedItemId = R.id.navigation_flight
            }
            2 -> {
                replaceFragment(ProfileFragment())
                binding.homeBottomNavigation.selectedItemId = R.id.navigation_profile
            }
        }

        binding.homeBottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_home -> replaceFragment(HomeFragment())
                R.id.navigation_flight -> {
//                    replaceFragment(FlightFragment())
                    if (token.isNotEmpty() && token.isNotBlank()) {
                        replaceFragment(FlightFragment())
                    } else {
                        authDialog.show()
                    }
                }
                R.id.navigation_profile -> {
//                    replaceFragment(ProfileFragment())
                    if (token.isNotEmpty() && token.isNotBlank()) {
                        replaceFragment(ProfileFragment())
                    } else {
                        authDialog.show()
                    }
                }
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

    private fun observeViewModel() {
        authViewModel.authentication.observe(this) { token = it }
    }
}