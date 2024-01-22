package com.synrgy.aeroswift.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.synrgy.aeroswift.R
import com.synrgy.aeroswift.databinding.ActivityTermOfServicesBinding

class TermOfServicesActivity : AppCompatActivity() {
    companion object {
        fun startActivity(context: Context, bundle: Bundle? = null) {
            val intent = Intent(context, TermOfServicesActivity::class.java)
            if (bundle != null) intent.putExtras(bundle)

            context.startActivity(intent)
        }
    }

    private lateinit var binding: ActivityTermOfServicesBinding

    private lateinit var mGoogleSignInClient: GoogleSignInClient

    private lateinit var email: String
    private lateinit var password: String
    private var expiredOtp = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTermOfServicesBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        handleSetupGso()

        mGoogleSignInClient.revokeAccess()

        val intentExtras = intent.extras
        email = intentExtras?.getString(AccountSetupActivity.KEY_EMAIL_SETUP) ?: ""
        password = intentExtras?.getString(AccountSetupActivity.KEY_PASSWORD_SETUP) ?: ""
        expiredOtp = intentExtras?.getLong(AccountSetupActivity.KEY_TIMESTAMP_SETUP) ?: 0L

        binding.checkBoxAgree.setOnCheckedChangeListener { _, isChecked ->
            binding.btnAcc.isEnabled = isChecked
        }

        binding.nestedScrollView.viewTreeObserver.addOnScrollChangedListener {
            if (binding.nestedScrollView.getChildAt(0).bottom <= binding.nestedScrollView.height + binding.nestedScrollView.scrollY) {
                binding.fabScrollDown.visibility = View.GONE
            } else {
                binding.fabScrollDown.visibility = View.VISIBLE
            }
        }

        binding.fabScrollDown.setOnClickListener {
            binding.nestedScrollView.fullScroll(View.FOCUS_DOWN)
        }

        binding.toolbarTermAndServ.setNavigationOnClickListener { handleNavigateToAuth() }

        binding.btnAcc.setOnClickListener {
            if (binding.btnAcc.isEnabled) {
                if (email.isNotBlank() &&
                    email.isNotEmpty() &&
                    password.isNotBlank() &&
                    password.isNotEmpty() &&
                    expiredOtp != 0L) {

                    handleNavigateToAccount()
                } else {
                    handleNavigateToAuth()
                }
            }
        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() { handleNavigateToAuth() }
        })
    }

    private fun handleSetupGso() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.server_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    private fun handleNavigateToAuth() {
        val bundle = Bundle()
        bundle.putInt(AuthActivity.KEY_TAB_INDEX, intent.extras?.getInt(AuthActivity.KEY_TAB_INDEX) ?: 0)

        AuthActivity.startActivity(this@TermOfServicesActivity, bundle)
        this@TermOfServicesActivity.finish()
    }

    private fun handleNavigateToAccount() {
        val bundle = Bundle()
        bundle.putString(AccountSetupActivity.KEY_EMAIL_SETUP, email)
        bundle.putString(AccountSetupActivity.KEY_PASSWORD_SETUP, password)
        bundle.putLong(AccountSetupActivity.KEY_TIMESTAMP_SETUP, expiredOtp)

        AccountSetupActivity.startActivity(this@TermOfServicesActivity, bundle)
        this@TermOfServicesActivity.finish()
    }
}