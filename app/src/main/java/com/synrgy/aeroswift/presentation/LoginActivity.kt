package com.synrgy.aeroswift.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.synrgy.aeroswift.R
import com.synrgy.aeroswift.databinding.ActivityLoginBinding
import com.synrgy.aeroswift.dialog.LoadingDialog
import com.synrgy.aeroswift.presentation.viewmodel.AuthViewModel
import com.synrgy.aeroswift.presentation.viewmodel.LoginViewModel
import com.synrgy.domain.LoginBody
import com.synrgy.presentation.helper.Helper
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginActivity : AppCompatActivity(), OnClickListener {
    companion object {
        fun startActivity(context: Context) {
            context.startActivity(Intent(context, LoginActivity::class.java))
        }
    }

    private val RC_SIGN_IN = 2
    private lateinit var binding: ActivityLoginBinding
    private lateinit var mGoogleSignInClient: GoogleSignInClient

    private val authViewModel: AuthViewModel by viewModels()
    private val loginViewModel: LoginViewModel by viewModels()

    private val loadingDialog = LoadingDialog(LoginActivity@this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        authViewModel.checkAuth()

        observeLogin()
        setupGso()
        setTextSpan()

        binding.btnSignInGoogle.setOnClickListener(this)
        binding.btnLogin.setOnClickListener { handleLogin() }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btn_sign_in_google -> signIn()
        }
    }

    private fun observeLogin() {
        loginViewModel.error.observe(this, ::handleError)
        loginViewModel.loading.observe(this, ::handleLoading)
        authViewModel.authentication.observe(this, ::handleAuthentication)
        loginViewModel.authentication.observe(this, ::handleAuthentication)
        loginViewModel.login.observe(this, ::handleSuccess)
    }

    private fun handleError(error: String) {
        val email = binding.loginTiEmail.text.toString()
        val password = binding.loginTiPassword.text.toString()

        if (email.isBlank() && email.isEmpty()) {
            binding.loginTiEmail.error = "Required"
        }

        if (password.isBlank() && password.isEmpty()) {
            binding.loginTiPassword.error = "Required"
        }

        Helper.showToast(this, this, error, isSuccess = false)
    }

    private fun handleLoading(loading: Boolean) {
        if (loading) {
            loadingDialog.startLoadingDialog()
        } else {
            loadingDialog.dismissDialog()
        }
    }

    private fun handleSuccess(message: String) {
        Helper.showToast(this, this, message, isSuccess = true)
    }

    private fun handleLogin() {
        val email = binding.loginTiEmail.text.toString()
        val password = binding.loginTiPassword.text.toString()

        loginViewModel.login(
            LoginBody(
                username = email,
                password = password
            )
        )
    }

    private fun handleAuthentication(token: String) {
        if (token.isNotEmpty() && token.isNotBlank()) {
            authViewModel.setToken(token)
            HomeActivity.startActivity(this)
            this.finish()
        }
    }

    private fun setTextSpan() {
        val loginDescText: Spannable =
            SpannableString(resources.getString(R.string.login_desc))
        val loginGoogleText: Spannable = SpannableString(resources.getString(R.string.login_google_text))

        loginDescText.setSpan(
            ForegroundColorSpan(resources.getColor(R.color.primary_500)),
            30,
            35,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        loginDescText.setSpan(
            StyleSpan(R.style.TextXS_Medium),
            30,
            35,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        loginGoogleText.setSpan(
            ForegroundColorSpan(resources.getColor(R.color.gray_300)),
            0,
            2,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        binding.loginDesc.text = loginDescText
        binding.loginGoogleText.text = loginGoogleText
    }

    private fun setupGso() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.server_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    private fun signIn() {
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            updateUI(account)
        } catch (e: ApiException) {
            Log.w("TAG", "signInResult:failed code=" + e.statusCode)
            updateUI(null)
        }
    }

    private fun updateUI(account: GoogleSignInAccount?) {
        if (account != null) {
            val idToken = account.idToken

            authViewModel.setToken(idToken!!)

            HomeActivity.startActivity(this)
        }
    }
}