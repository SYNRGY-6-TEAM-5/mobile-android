package com.synrgy.aeroswift.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.synrgy.aeroswift.R
import com.synrgy.aeroswift.databinding.ActivityAccountSetupBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccountSetupActivity : AppCompatActivity() {
    companion object {
        const val KEY_EMAIL_SETUP = "email_setup"
        const val KEY_NAME_SETUP = "name_setup"
        const val KEY_PASSWORD_SETUP = "password_setup"
        const val KEY_PHOTO_SETUP = "photo_setup"

        fun startActivity(context: Context, bundle: Bundle? = null) {
            val intent = Intent(context, AccountSetupActivity::class.java)
            if (bundle != null) intent.putExtras(bundle)

            context.startActivity(intent)
        }
    }

    private lateinit var navController: NavController
    private lateinit var binding: ActivityAccountSetupBinding
    private lateinit var mGoogleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountSetupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = findNavController(R.id.nav_container_as)

        handleSetupGso()

        mGoogleSignInClient.revokeAccess()

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val bundle = Bundle()
                bundle.putInt(AuthActivity.KEY_TAB_INDEX, 1)

                AuthActivity.startActivity(this@AccountSetupActivity, bundle)
                this@AccountSetupActivity.finish()
            }
        })
    }

    private fun handleSetupGso() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.server_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
    }
}