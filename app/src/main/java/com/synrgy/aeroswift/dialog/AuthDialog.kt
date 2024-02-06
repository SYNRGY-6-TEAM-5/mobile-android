package com.synrgy.aeroswift.dialog

import android.app.Activity
import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import androidx.core.content.ContextCompat.getString
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.synrgy.aeroswift.R
import com.synrgy.aeroswift.databinding.DialogAuthBinding
import com.synrgy.aeroswift.presentation.AuthActivity

class AuthDialog(
    private val activity: Activity
) {
    private lateinit var dialog: AlertDialog
    private lateinit var binding: DialogAuthBinding
    private lateinit var mGoogleSignInClient: GoogleSignInClient

    fun show() {
        val builder = AlertDialog.Builder(activity)
        binding = DialogAuthBinding.inflate(activity.layoutInflater)
        val view = binding.root

        builder.setView(view)
        builder.setCancelable(false)

        val back = ColorDrawable(Color.TRANSPARENT)
        val margin = 16
        val inset = InsetDrawable(back, margin)

        dialog = builder.create()
        dialog.window?.setBackgroundDrawable(inset)
        dialog.show()

        setupGso()

        binding.btnSignIn.setOnClickListener {
            mGoogleSignInClient.revokeAccess().addOnCompleteListener {
                AuthActivity.startActivity(activity)
                activity.finish()
            }
        }
    }

    fun dismiss() {
        dialog.dismiss()
    }

    private fun setupGso() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(activity, R.string.server_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(activity, gso)
    }
}