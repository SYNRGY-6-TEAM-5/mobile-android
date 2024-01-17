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
import com.synrgy.aeroswift.databinding.FragmentRegisterBinding
import com.synrgy.aeroswift.dialog.ForgotPassDialog
import com.synrgy.aeroswift.dialog.LoadingDialog
import com.synrgy.aeroswift.presentation.AccountSetupActivity
import com.synrgy.aeroswift.presentation.AuthActivity
import com.synrgy.aeroswift.presentation.HomeActivity
import com.synrgy.aeroswift.presentation.viewmodel.AuthViewModel
import com.synrgy.aeroswift.presentation.viewmodel.RegisterViewModel
import com.synrgy.domain.RegisterBody
import com.synrgy.presentation.helper.Helper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment: Fragment() {
    private lateinit var binding: FragmentRegisterBinding

    private lateinit var mGoogleSignInClient: GoogleSignInClient

    private val authViewModel: AuthViewModel by viewModels()
    private val registerViewModel: RegisterViewModel by viewModels()

    private lateinit var loadingDialog: LoadingDialog
    private lateinit var forgotPassDialog: ForgotPassDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
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

        binding.btnSignInGoogle.setOnClickListener { signUp() }
        binding.btnRegister.setOnClickListener { handleRegister() }
    }

    private fun observeLogin() {
        registerViewModel.error.observe(viewLifecycleOwner, ::handleError)
        registerViewModel.loading.observe(viewLifecycleOwner, ::handleLoading)
        registerViewModel.register.observe(viewLifecycleOwner, ::handleSuccess)

        authViewModel.authentication.observe(viewLifecycleOwner, ::handleAuthentication)
    }

    private fun handleError(error: String) {
        if (error.isNotBlank() && error.isNotEmpty()) {
            val email = binding.registerTiEmail.text.toString()
            val password = binding.registerTiPassword.text.toString()

            if (email.isBlank() && email.isEmpty()) {
                binding.registerTilEmail.error = "Required"
            }

            if (password.isBlank() && password.isEmpty()) {
                binding.registerTilPassword.error = "Required"
                binding.registerTilPassword.errorIconDrawable = null
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
            val email = binding.registerTiEmail.text.toString().takeIf { it.isNotEmpty() }
                ?: "test@gmail.com"

            val bundle = Bundle()
            bundle.putString(AccountSetupActivity.KEY_EMAIL_SETUP, email)

            handleNavigateToAccountSetup(bundle)
        }
    }

    private fun handleRegister() {
        val email = binding.registerTiEmail.text.toString()
        val password = binding.registerTiPassword.text.toString()

        registerViewModel.register(
            RegisterBody(email, password)
        )
    }

    private fun handleAuthentication(token: String) {
        if (token.isNotEmpty() && token.isNotBlank()) {
            authViewModel.setToken(token)
            handleNavigateToHome()
        }
    }

    private fun handleNavigateToAccountSetup(bundle: Bundle) {
        AccountSetupActivity.startActivity(requireActivity(), bundle)
        requireActivity().finish()
    }

    private fun handleNavigateToHome() {
        HomeActivity.startActivity(requireActivity())
        requireActivity().finish()
    }

    private fun setTextSpan() {
        val registerDescText: Spannable =
            SpannableString(resources.getString(R.string.t_and_c))
        val registerGoogleText: Spannable = SpannableString(resources.getString(R.string.register_google_text))

        registerDescText.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.primary_500)),
            30,
            35,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        registerDescText.setSpan(
            StyleSpan(R.style.TextXS_Medium),
            30,
            35,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        registerGoogleText.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.gray_300)),
            0,
            2,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        binding.registerDesc.text = registerDescText
        binding.registerGoogleText.text = registerGoogleText
    }

    private fun setupGso() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.server_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
    }

    private fun signUp() {
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
            handleSignUpResult(task)
        }
    }

    private fun handleSignUpResult(completedTask: Task<GoogleSignInAccount>) {
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
            val displayName = account.displayName.toString()
            val photoUrl = account.photoUrl.toString()
            val email = account.email

            val bundle = Bundle()
            bundle.putString(AccountSetupActivity.KEY_EMAIL_SETUP, email)
            bundle.putString(AccountSetupActivity.KEY_NAME_SETUP, displayName)
            bundle.putString(AccountSetupActivity.KEY_PHOTO_SETUP, photoUrl)

            handleNavigateToAccountSetup(bundle)
        }
    }
}