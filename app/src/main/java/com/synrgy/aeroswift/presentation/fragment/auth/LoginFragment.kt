package com.synrgy.aeroswift.presentation.fragment.auth

import android.content.Intent
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
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
import com.synrgy.aeroswift.presentation.ForgotPasswordActivity
import com.synrgy.aeroswift.presentation.TermOfServicesActivity
import com.synrgy.aeroswift.presentation.viewmodel.auth.AuthViewModel
import com.synrgy.aeroswift.presentation.viewmodel.auth.LoginViewModel
import com.synrgy.aeroswift.presentation.viewmodel.forgotpassword.ForgotPasswordViewModel
import com.synrgy.domain.body.auth.LoginBody
import com.synrgy.domain.response.auth.LoginResponse
import com.synrgy.domain.response.error.ErrorItem
import com.synrgy.domain.response.forgotpassword.ForgotPasswordResponse
import com.synrgy.presentation.helper.Helper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment: Fragment() {
    private lateinit var binding: FragmentLoginBinding

    private lateinit var mGoogleSignInClient: GoogleSignInClient

    private val authViewModel: AuthViewModel by viewModels()
    private val loginViewModel: LoginViewModel by viewModels()
    private val forgotPassViewModel: ForgotPasswordViewModel by viewModels()

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
        forgotPassDialog = ForgotPassDialog(
            requireActivity(),
            forgotPassViewModel,
            viewLifecycleOwner
        )

        observeViewModel()
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

    private fun observeViewModel() {
        loginViewModel.errors.observe(viewLifecycleOwner, ::handleErrors)
        loginViewModel.error.observe(viewLifecycleOwner, ::handleError)
        loginViewModel.loading.observe(viewLifecycleOwner, ::handleLoading)
        loginViewModel.login.observe(viewLifecycleOwner, ::handleSuccess)

        forgotPassViewModel.forgotPassword.observe(viewLifecycleOwner, ::handleForgotPassword)
        forgotPassViewModel.error.observe(viewLifecycleOwner, ::handleError)
        forgotPassViewModel.loading.observe(viewLifecycleOwner, ::handleLoading)
    }

    private fun handleError(error: String) {
        if (error.isNotBlank() && error.isNotEmpty()) {
            Helper.showToast(requireActivity(), requireContext(), error, isSuccess = false)
        }
    }

    private fun handleErrors(errors: List<ErrorItem?>?) {
        if (!errors.isNullOrEmpty()) {
            var emailMessage = ""
            var passwordMessage = ""

            for (error in errors) {
                when (error?.field) {
                    "emailAddress" -> emailMessage += error.defaultMessage + "\n"
                    "password" -> passwordMessage += error.defaultMessage + "\n"
                }
            }

            binding.loginTilEmail.error = emailMessage.replace(",", "\n")
            binding.loginTilPassword.error = passwordMessage.replace(",", "\n")
            binding.loginTilPassword.errorIconDrawable = null
        }
    }

    private fun handleLoading(loading: Boolean) {
        if (loading) {
            loadingDialog.startLoadingDialog()
        } else {
            loadingDialog.dismissDialog()
        }
    }

    private fun handleSuccess(response: LoginResponse) {
        if (!response.message.isNullOrEmpty() &&
            !response.message.isNullOrBlank() &&
            response.token.isNotEmpty() &&
            response.token.isNotBlank()) {

            Helper.showToast(requireActivity(), requireContext(), response.message!!, isSuccess = true)
            handleAuthentication(response.token)
        }
    }

    private fun handleAuthentication(token: String) {
        authViewModel.setToken(token)
        handleNavigateToHome()
    }

    private fun handleForgotPassword(response: ForgotPasswordResponse) {
        if (!response.otp.isNullOrEmpty() &&
            !response.otp.isNullOrBlank() &&
            response.expiredOTP != 0L &&
            !response.email.isNullOrEmpty() &&
            !response.email.isNullOrBlank() &&
            response.success == true) {

            val bundle = Bundle()
            bundle.putString(ForgotPasswordActivity.KEY_EMAIL_RECOVERY, response.email)
            bundle.putLong(ForgotPasswordActivity.KEY_TIMESTAMP_RECOVERY, response.expiredOTP!!)

            ForgotPasswordActivity.startActivity(requireActivity(), bundle)
            requireActivity().finish()
        }
    }

    private fun handleLogin() {
        val email = binding.loginTiEmail.text.toString()
        val password = binding.loginTiPassword.text.toString()

        binding.loginTilEmail.error = null
        binding.loginTilPassword.error = null

        loginViewModel.login(
            LoginBody(email, password)
        )
    }

    private fun handleNavigateToHome() {
        HomeActivity.startActivity(requireActivity())
        requireActivity().finish()
    }

    private fun setTextSpan() {
        val loginDescText: Spannable =
            SpannableString(resources.getString(R.string.t_and_c))
        val loginGoogleText: Spannable = SpannableString(resources.getString(R.string.login_google_text))

        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                TermOfServicesActivity.startActivity(requireActivity())
                requireActivity().finish()
            }
        }

        loginDescText.setSpan(
            clickableSpan,
            31,
            35,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        loginDescText.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.primary_500)),
            31,
            35,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        loginDescText.setSpan(
            StyleSpan(R.style.TextXS_Medium),
            31,
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
        binding.loginDesc.movementMethod = LinkMovementMethod.getInstance()
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