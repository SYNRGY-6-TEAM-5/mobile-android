package com.synrgy.aeroswift.presentation.fragment.auth

import android.content.Intent
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.synrgy.aeroswift.R
import com.synrgy.aeroswift.databinding.FragmentLoginBinding
import com.synrgy.aeroswift.dialog.ForgotPassDialog
import com.synrgy.aeroswift.dialog.LoadingDialog
import com.synrgy.aeroswift.presentation.HomeActivity
import com.synrgy.aeroswift.presentation.AuthActivity
import com.synrgy.aeroswift.presentation.viewmodel.auth.AuthViewModel
import com.synrgy.aeroswift.presentation.viewmodel.auth.LoginViewModel
import com.synrgy.domain.body.LoginBody
import com.synrgy.presentation.helper.Helper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment: Fragment() {
    private lateinit var binding: FragmentLoginBinding

    private lateinit var mGoogleSignInClient: GoogleSignInClient

    private val authViewModel: AuthViewModel by viewModels()
    private val loginViewModel: LoginViewModel by viewModels()

    private lateinit var loadingDialog: LoadingDialog
    private lateinit var forgotPassDialog: ForgotPassDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadingDialog = LoadingDialog(requireActivity())
        forgotPassDialog = ForgotPassDialog(requireActivity())

        authViewModel.checkAuth()

        observeLogin()
        setupGso()
        setTextSpan()

        binding.btnSignInGoogle.setOnClickListener { signIn() }
        binding.btnLogin.setOnClickListener { handleLogin() }

        binding.txtForgotPass.setOnClickListener{ forgotPassDialog.show() }
        binding.txtLoginAsGuest.setOnClickListener {
            authViewModel.setName("Guest")
            handleNavigateToHome()
        }
    }

    private fun observeLogin() {
        loginViewModel.error.observe(viewLifecycleOwner, ::handleError)
        loginViewModel.loading.observe(viewLifecycleOwner, ::handleLoading)
        loginViewModel.authentication.observe(viewLifecycleOwner, ::handleAuthentication)
        loginViewModel.login.observe(viewLifecycleOwner, ::handleSuccess)

        authViewModel.authentication.observe(viewLifecycleOwner, ::handleAuthentication)
    }

    private fun handleError(error: String) {
        if (error.isNotBlank() && error.isNotEmpty()) {
            val email = binding.loginTiEmail.text.toString()
            val password = binding.loginTiPassword.text.toString()

            if (email.isBlank() && email.isEmpty()) {
                binding.loginTilEmail.error = "Required"
            }

            if (password.isBlank() && password.isEmpty()) {
                binding.loginTilPassword.error = "Required"
                binding.loginTilPassword.errorIconDrawable = null
            }

            Helper.showToast(requireActivity(), requireContext(), error, isSuccess = false)
        }
    }

    private fun handleLoading(loading: Boolean) {
        if (loading) {
            loadingDialog.startLoadingDialog()
        } else {
            loadingDialog.dismissDialog()
        }
    }

    private fun handleSuccess(message: String) {
        if (message.isNotEmpty() && message.isNotBlank()) {
            Helper.showToast(requireActivity(), requireContext(), message, isSuccess = true)
        }
    }

    private fun handleLogin() {
        val email = binding.loginTiEmail.text.toString()
        val password = binding.loginTiPassword.text.toString()

        loginViewModel.login(
            LoginBody(email, password)
        )

        authViewModel.setName("zachriek")
    }

    private fun handleAuthentication(token: String) {
        if (token.isNotEmpty() && token.isNotBlank()) {
            authViewModel.setToken(token)
            handleNavigateToHome()
        }
    }

    private fun handleNavigateToHome() {
        HomeActivity.startActivity(requireActivity())
        requireActivity().finish()
    }

    private fun setTextSpan() {
        val loginDescText: Spannable =
            SpannableString(resources.getString(R.string.t_and_c))
        val loginGoogleText: Spannable = SpannableString(resources.getString(R.string.login_google_text))

        loginDescText.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.primary_500)),
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
            ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.gray_300)),
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
        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
    }

    private fun signIn() {
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, AuthActivity.RC_SIGN_IN)
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == AuthActivity.RC_SIGN_IN) {
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
            val idToken = account.idToken.toString()
            val displayName = account.displayName.toString()
            val photoUrl = account.photoUrl.toString()

            authViewModel.setToken(idToken)
            authViewModel.setName(displayName)
            authViewModel.setPhoto(photoUrl)

            handleNavigateToHome()
        }
    }
}