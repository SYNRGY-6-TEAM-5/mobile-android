package com.synrgy.aeroswift.presentation.fragment.auth

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.Spannable
import android.text.SpannableString
import android.text.TextWatcher
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.util.Log
import android.util.Patterns
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
import com.synrgy.aeroswift.dialog.LoadingDialog
import com.synrgy.aeroswift.presentation.AccountSetupActivity
import com.synrgy.aeroswift.presentation.AuthActivity
import com.synrgy.aeroswift.presentation.TermOfServicesActivity
import com.synrgy.aeroswift.presentation.viewmodel.auth.RegisterViewModel
import com.synrgy.domain.body.auth.RegisterBody
import com.synrgy.domain.response.auth.RegisterResponse
import com.synrgy.domain.response.error.ErrorItem
import com.synrgy.presentation.helper.Helper
import com.synrgy.presentation.helper.PasswordStrength
import dagger.hilt.android.AndroidEntryPoint
import com.synrgy.data.helper.Helper as HelperData

@AndroidEntryPoint
class RegisterFragment: Fragment() {
    private lateinit var binding: FragmentRegisterBinding

    private lateinit var mGoogleSignInClient: GoogleSignInClient

    private val registerViewModel: RegisterViewModel by viewModels()

    private lateinit var loadingDialog: LoadingDialog

    private var emailMessage = ""
    private var passwordMessage = ""

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

        observeViewModel()
        setupGso()
        setTextSpan()

        binding.btnSignInGoogle.setOnClickListener { signUp() }
        binding.btnRegister.setOnClickListener { handleRegister() }

        handleInputChange()
    }

    private fun observeViewModel() {
        registerViewModel.error.observe(viewLifecycleOwner, ::handleError)
        registerViewModel.errors.observe(viewLifecycleOwner, ::handleErrors)
        registerViewModel.loading.observe(viewLifecycleOwner, ::handleLoading)
        registerViewModel.register.observe(viewLifecycleOwner, ::handleSuccess)
        registerViewModel.localError.observe(viewLifecycleOwner, ::handleLocalError)
    }

    private fun handleError(error: String) {
        if (error.isNotEmpty() && error.isNotBlank()) {
            Helper.showToast(requireActivity(), requireContext(), error, isSuccess = false)
        }
    }

    private fun handleErrors(errors: List<ErrorItem?>?) {
        if (!errors.isNullOrEmpty()) {
            for (error in errors) {
                when (error?.field) {
                    "email" -> {
                        emailMessage += if (error.defaultMessage?.last() != '.') {
                            "${error.defaultMessage}.\n"
                        } else {
                            "${error.defaultMessage}\n"
                        }
                    }
                    "password" -> {
                        passwordMessage += if (error.defaultMessage?.last() != '.') {
                            "${error.defaultMessage}.\n"
                        } else {
                            "${error.defaultMessage}\n"
                        }
                    }
                }
            }
        }

        handleSetInputMessage()
    }

    private fun handleLoading(loading: Boolean) {
        if (loading) {
            loadingDialog.startLoadingDialog()
        } else {
            loadingDialog.dismissDialog()
        }
    }

    private fun handleSuccess(response: RegisterResponse) {
        if (response.otp.isNotBlank() &&
            response.otp.isNotEmpty() &&
            response.expiredOTP != 0L &&
            response.success) {

            Log.d("OTP", response.otp)

            val email = binding.registerTiEmail.text.toString()
            val password = binding.registerTiPassword.text.toString()

            val bundle = Bundle()
            bundle.putString(AccountSetupActivity.KEY_EMAIL_SETUP, email)
            bundle.putString(AccountSetupActivity.KEY_PASSWORD_SETUP, password)
            bundle.putLong(AccountSetupActivity.KEY_TIMESTAMP_SETUP, response.expiredOTP)
            bundle.putInt(AuthActivity.KEY_TAB_INDEX, 1)

            handleNavigateTOS(bundle)
        }
    }

    private fun handleRegister() {
        emailMessage = ""
        passwordMessage = ""

        val email = binding.registerTiEmail.text.toString()
        val password = binding.registerTiPassword.text.toString()

        binding.registerTilEmail.error = null
        binding.registerTilPassword.error = null

        registerViewModel.register(
            RegisterBody(email, password)
        )
    }

    private fun handleLocalError(error: Boolean) {
        if (error) {
            val email = binding.registerTiEmail.text.toString()
            val password = binding.registerTiPassword.text.toString()

            validateEmail(email)
            validatePassword(password)
            handleSetInputMessage()
        }
    }

    private fun handleNavigateTOS(bundle: Bundle) {
        TermOfServicesActivity.startActivity(requireActivity(), bundle)
        requireActivity().finish()
    }

    private fun setTextSpan() {
        val registerDescText: Spannable =
            SpannableString(resources.getString(R.string.t_and_c))
        val registerGoogleText: Spannable = SpannableString(resources.getString(R.string.register_google_text))

        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                val bundle = Bundle()
                bundle.putInt(AuthActivity.KEY_TAB_INDEX, 1)

                handleNavigateTOS(bundle)
            }
        }

        registerDescText.setSpan(
            clickableSpan,
            31,
            35,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        registerDescText.setSpan(
            ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.primary_500)),
            31,
            35,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        registerDescText.setSpan(
            StyleSpan(R.style.TextXS_Medium),
            31,
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
        binding.registerDesc.movementMethod = LinkMovementMethod.getInstance()
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
            bundle.putString(AccountSetupActivity.KEY_PASSWORD_SETUP, "password")
            bundle.putString(AccountSetupActivity.KEY_PHOTO_SETUP, photoUrl)
            bundle.putLong(AccountSetupActivity.KEY_TIMESTAMP_SETUP,
                (System.currentTimeMillis() + 300000)
            )
            bundle.putInt(AuthActivity.KEY_TAB_INDEX, 1)

            handleNavigateTOS(bundle)
        }
    }

    private fun handleInputChange() {
        binding.registerTiEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                emailMessage = ""
                validateEmail(p0.toString())
            }

            override fun afterTextChanged(p0: Editable?) {
                binding.registerTilEmail.error = emailMessage
            }
        })

        binding.registerTiPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                passwordMessage = ""
                calculatePasswordStrength(p0.toString())
                validatePassword(p0.toString())
            }

            override fun afterTextChanged(p0: Editable?) {
                binding.registerTilPassword.error = passwordMessage
                binding.registerTilPassword.errorIconDrawable = null
            }
        })
    }

    private fun calculatePasswordStrength(password: String) {
        val passwordStrength = PasswordStrength.calculate(password)
        binding.tvStrength.text = getString(passwordStrength.message)
        binding.tvStrength.setTextColor(ContextCompat.getColor(requireContext(), passwordStrength.color))
    }

    private fun validateEmail(email: String) {
        if (email.isEmpty() && email.isBlank()) {
            emailMessage += "Email is required.\n"
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailMessage += "Email is not valid.\n"
        }
    }

    private fun validatePassword(password: String) {
        if (password.isEmpty() && password.isBlank()) {
            passwordMessage += "Password is required.\n"
        }
        if (!HelperData.checkPasswordLength(password)) {
            passwordMessage += "Password length min 8 character.\n"
        }
        if (!HelperData.containsSpecialCharacter(password)) {
            passwordMessage += "Password should contain a special character.\n"
        }
        if (!HelperData.containsAlphanumeric(password)) {
            passwordMessage += "Password should contain both letters and numbers.\n"
        }
        if (!HelperData.containsUppercaseLetter(password)) {
            passwordMessage += "Password should contain at least one uppercase letter.\n"
        }
    }

    private fun handleSetInputMessage() {
        if ((emailMessage.isNotEmpty() && emailMessage.isNotBlank()) ||
            (passwordMessage.isNotEmpty() && passwordMessage.isNotBlank())) {

            binding.registerTilEmail.error = emailMessage.replace(",", "\n")
            binding.registerTilPassword.error = passwordMessage.replace(",", "\n")
            binding.registerTilPassword.errorIconDrawable = null
        }
    }
}