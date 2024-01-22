package com.synrgy.aeroswift.presentation.fragment.accountsetup

import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.synrgy.aeroswift.R
import com.synrgy.aeroswift.databinding.FragmentCodeVerifBinding
import com.synrgy.aeroswift.dialog.LoadingDialog
import com.synrgy.aeroswift.presentation.AccountSetupActivity
import com.synrgy.aeroswift.presentation.AuthActivity
import com.synrgy.aeroswift.presentation.viewmodel.auth.AuthViewModel
import com.synrgy.aeroswift.presentation.viewmodel.auth.CodeVerifViewModel
import com.synrgy.aeroswift.presentation.viewmodel.auth.RegisterViewModel
import com.synrgy.domain.body.auth.RegisterBody
import com.synrgy.domain.body.auth.ValidateOtpBody
import com.synrgy.domain.response.auth.ValidateOtpResponse
import com.synrgy.presentation.helper.Helper
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CodeVerifFragment : Fragment() {
    private lateinit var binding: FragmentCodeVerifBinding
    private lateinit var countDownTimer: CountDownTimer

    private val authViewModel: AuthViewModel by viewModels()
    private val registerViewModel: RegisterViewModel by viewModels()
    private val viewModel: CodeVerifViewModel by viewModels()

    private lateinit var loadingDialog: LoadingDialog

    private lateinit var email: String
    private lateinit var password: String
    private var expiredOtp = 0L

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentCodeVerifBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadingDialog = LoadingDialog(requireActivity())

        observeViewModel()
        handleInputCode()

        val bundle = requireActivity().intent.extras
        email = bundle?.getString(AccountSetupActivity.KEY_EMAIL_SETUP)!!
        password = bundle.getString(AccountSetupActivity.KEY_PASSWORD_SETUP)!!
        expiredOtp = bundle.getLong(AccountSetupActivity.KEY_TIMESTAMP_SETUP)

        handleTimer()
        binding.regVerifEmail.text = "$email."
        binding.btnConfirmCodeReg.setOnClickListener { sendCode() }
        binding.regChangeEmail.setOnClickListener { handleChangeEmail() }
    }

    private fun handleChangeEmail() {
        val bundle = Bundle()
        bundle.putInt(AuthActivity.KEY_TAB_INDEX, 1)

        AuthActivity.startActivity(requireActivity(), bundle)
        requireActivity().finish()
    }

    private fun handleInputCode() {
        val vcInputs = listOf(
            binding.regVc1,
            binding.regVc2,
            binding.regVc3,
            binding.regVc4
        )
        val vcLength = vcInputs.size

        vcInputs[0].requestFocus()

        for (index in 0 until vcLength) {
            vcInputs[index].addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (count >= 1) {
                        if (index != vcLength - 1) {
                            vcInputs[index + 1].requestFocus()
                        }
                        vcInputs[index].setBackgroundResource(R.drawable.bg_verif_code_filled)
                        vcInputs[index].setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                    }

                    if (count == 0) {
                        if (index != 0) {
                            vcInputs[index - 1].requestFocus()
                        }
                        vcInputs[index].setBackgroundResource(R.drawable.bg_verif_code)
                        vcInputs[index].setTextColor(ContextCompat.getColor(requireContext(), R.color.base_black))
                    }
                }
                override fun afterTextChanged(s: Editable?) {}
            })

            vcInputs[index].setOnKeyListener(View.OnKeyListener { _, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_DEL
                    && index > 0 && vcInputs[index].text.toString().isEmpty()) {
                    vcInputs[index - 1].requestFocus()
                    return@OnKeyListener true
                }
                false
            })
        }
    }

    private fun handleTimer() {
        countDownTimer = object : CountDownTimer(expiredOtp - System.currentTimeMillis(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val minutes = millisUntilFinished / 1000 / 60
                val seconds = (millisUntilFinished / 1000) % 60
                val timeString = String.format("%d:%02d", minutes, seconds)

                binding.tv2TimerVcReg.text = timeString
            }

            override fun onFinish() {
                binding.tv1TimerVcReg.text = ""
                binding.tv2TimerVcReg.text = getString(R.string.resend)

                binding.tv2TimerVcReg.setOnClickListener {
                    resendCode()

                    registerViewModel.register.observe(viewLifecycleOwner) {
                        if (it.expiredOTP != 0L && it.success) {
                            Helper.showToast(requireActivity(), requireContext(), "Resend code success!", isSuccess = true)
                            expiredOtp = it.expiredOTP
                            countDownTimer.start()
                            binding.tv1TimerVcReg.text = getString(R.string.resend_code_after)
                        }
                    }
                }
            }
        }

        countDownTimer.start()
    }

    private fun observeViewModel() {
        viewModel.error.observe(viewLifecycleOwner, ::handleError)
        viewModel.loading.observe(viewLifecycleOwner, ::handleLoading)
        viewModel.validateOtp.observe(viewLifecycleOwner, ::handleSuccess)

        registerViewModel.error.observe(viewLifecycleOwner, ::handleError)
        registerViewModel.loading.observe(viewLifecycleOwner, ::handleLoading)
    }

    private fun handleError(error: String) {
        if (error.isNotEmpty() && error.isNotBlank()) {
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

    private fun handleSuccess(response: ValidateOtpResponse) {
        if (response.token.isNotBlank() &&
            response.token.isNotEmpty() &&
            response.message.isNotBlank() &&
            response.message.isNotEmpty() &&
            response.success) {

            Helper.showToast(requireActivity(), requireContext(), response.message, isSuccess = true)
            authViewModel.setToken(response.token)
            findNavController().navigate(R.id.action_codeVerifFragment_to_accountDetailFragment)
        }
    }

    private fun sendCode() {
        val code1 = binding.regVc1.text
        val code2 = binding.regVc2.text
        val code3 = binding.regVc3.text
        val code4 = binding.regVc4.text
        val otp = "${code1}${code2}${code3}${code4}"

        viewModel.validateOtp(
            ValidateOtpBody(
                email = email,
                otp = otp,
                password = password
            )
        )
    }

    private fun resendCode() {
        registerViewModel.register(
            RegisterBody(email, password)
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer.cancel()
    }
}