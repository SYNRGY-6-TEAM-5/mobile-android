package com.synrgy.aeroswift.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.synrgy.aeroswift.R
import com.synrgy.aeroswift.databinding.ActivityHomeBinding
import com.synrgy.aeroswift.dialog.LoadingDialog
import com.synrgy.aeroswift.presentation.fragment.FlightFragment
import com.synrgy.aeroswift.presentation.fragment.HomeFragment
import com.synrgy.aeroswift.presentation.fragment.ProfileFragment
import com.synrgy.aeroswift.presentation.viewmodel.AuthViewModel
import com.synrgy.presentation.helper.Helper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    companion object {
        fun startActivity(context: Context) {
            context.startActivity(Intent(context, HomeActivity::class.java))
        }
    }

    private lateinit var binding: ActivityHomeBinding
    private lateinit var mGoogleSignInClient: GoogleSignInClient

    private val authViewModel: AuthViewModel by viewModels()

    private val loadingDialog = LoadingDialog(HomeActivity@ this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        authViewModel.checkAuth()

        setupGso()
        observeHome()
//
//        binding.homeBtnLogout.setOnClickListener { authViewModel.logout() }


        replaceFragment(HomeFragment())

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
// =======
//         binding.homeBtnLogout.setOnClickListener { authViewModel.logout() }
//         binding.homeBtnAirport.setOnClickListener { AirportListActivity.startActivity(this) }
// >>>>>>> develop
    }

    private fun setupGso() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.server_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    private fun observeHome() {
        authViewModel.logoutLoading.observe(this, ::handleLogout)
        authViewModel.name.observe(this, ::handleGetUserProfile)
    }

    private fun handleLogout(loading: Boolean) {
        if (loading) {
            loadingDialog.startLoadingDialog()
        } else {
            loadingDialog.dismissDialog()

            mGoogleSignInClient.revokeAccess().addOnCompleteListener {
                Helper.showToast(this, this, getString(R.string.message_logout), isSuccess = true)
                LoginActivity.startActivity(this)
                this.finish()
            }
        }
    }

    private fun handleGetUserProfile(name: String) {
//        binding.homeDisplayName.text = name
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.home_frame_layout, fragment)
        fragmentTransaction.commit()
    }
}