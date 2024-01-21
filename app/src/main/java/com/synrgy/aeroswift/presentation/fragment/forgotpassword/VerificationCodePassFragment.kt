package com.synrgy.aeroswift.presentation.fragment.forgotpassword

import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.Html
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.synrgy.aeroswift.R
import com.synrgy.aeroswift.databinding.FragmentVerificationCodePassBinding
import com.synrgy.aeroswift.dialog.LoadingDialog
import com.synrgy.aeroswift.presentation.ForgotPasswordActivity
import com.synrgy.aeroswift.presentation.viewmodel.auth.AuthViewModel
import com.synrgy.aeroswift.presentation.viewmodel.forgotpassword.ForgotPasswordViewModel
import com.synrgy.aeroswift.presentation.viewmodel.forgotpassword.ValidateOtpFpViewModel
import com.synrgy.domain.body.forgotpassword.ForgotPasswordBody
import com.synrgy.domain.body.forgotpassword.ValidateOtpFpBody
import com.synrgy.domain.response.forgotpassword.ForgotPasswordResponse
import com.synrgy.domain.response.forgotpassword.ValidateOtpFpResponse
import com.synrgy.presentation.helper.Helper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VerificationCodePassFragment : Fragment() {

    private lateinit var binding: FragmentVerificationCodePassBinding
    private lateinit var countDownTimer: CountDownTimer

    private lateinit var loadingDialog: LoadingDialog

    private val viewModel: ValidateOtpFpViewModel by viewModels()
    private val authViewModel: AuthViewModel by viewModels()
    private val forgotPassViewModel: ForgotPasswordViewModel by viewModels()

    private lateinit var email: String
    private var expiredOtp = 0L

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentVerificationCodePassBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadingDialog = LoadingDialog(requireActivity())

        observeViewModel()
        handleInputCode()

        binding.btnConfirmCode.setOnClickListener { sendCode() }

        val bundle = requireActivity().intent.extras
        email = bundle?.getString(ForgotPasswordActivity.KEY_EMAIL_RECOVERY)!!
        expiredOtp = bundle.getLong(ForgotPasswordActivity.KEY_TIMESTAMP_RECOVERY)

        handleTimer()

        binding.verifDesc.text = Html.fromHtml(getString(
            R.string.verification_code_desc,
            "<b>${email}.</b>"
        ))
    }

    private fun handleInputCode() {
        binding.vc1.requestFocus()

        binding.vc1.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.length == 1) {
                    binding.vc2.requestFocus()
                    binding.vc1.setBackgroundResource(R.drawable.bg_verif_code_filled)
                    binding.vc1.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                }

                if (s?.length == 0) {
                    binding.vc1.setBackgroundResource(R.drawable.bg_verif_code)
                    binding.vc1.setTextColor(ContextCompat.getColor(requireContext(), R.color.base_black))
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        binding.vc2.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.length == 1) {
                    binding.vc3.requestFocus()
                    binding.vc2.setBackgroundResource(R.drawable.bg_verif_code_filled)
                    binding.vc2.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                }

                if (s?.length == 0) {
                    binding.vc1.requestFocus()
                    binding.vc2.setBackgroundResource(R.drawable.bg_verif_code)
                    binding.vc2.setTextColor(ContextCompat.getColor(requireContext(), R.color.base_black))
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        binding.vc3.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.length == 1) {
                    binding.vc4.requestFocus()
                    binding.vc3.setBackgroundResource(R.drawable.bg_verif_code_filled)
                    binding.vc3.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                }

                if (s?.length == 0) {
                    binding.vc2.requestFocus()
                    binding.vc3.setBackgroundResource(R.drawable.bg_verif_code)
                    binding.vc3.setTextColor(ContextCompat.getColor(requireContext(), R.color.base_black))
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        binding.vc4.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.length == 1) {
                    binding.vc4.setBackgroundResource(R.drawable.bg_verif_code_filled)
                    binding.vc4.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                }

                if (s?.length == 0) {
                    binding.vc3.requestFocus()
                    binding.vc4.setBackgroundResource(R.drawable.bg_verif_code)
                    binding.vc4.setTextColor(ContextCompat.getColor(requireContext(), R.color.base_black))
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun handleTimer() {
        countDownTimer = object : CountDownTimer(expiredOtp - System.currentTimeMillis(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val minutes = millisUntilFinished / 1000 / 60
                val seconds = (millisUntilFinished / 1000) % 60
                val timeString = String.format("%d:%02d", minutes, seconds)

                binding.tv2TimerVc.text = timeString
            }

            override fun onFinish() {
                binding.tv1TimerVc.text = ""
                binding.tv2TimerVc.text = getString(R.string.resend)

                binding.tv2TimerVc.setOnClickListener {
                    resendCode()
                    forgotPassViewModel.forgotPassword.observe(viewLifecycleOwner, ::handleForgotPassword)
                }
            }
        }

        countDownTimer.start()
    }

    private fun observeViewModel() {
        viewModel.error.observe(viewLifecycleOwner, ::handleError)
        viewModel.loading.observe(viewLifecycleOwner, ::handleLoading)
        viewModel.validateOtp.observe(viewLifecycleOwner, ::handleSuccess)

        forgotPassViewModel.error.observe(viewLifecycleOwner, ::handleError)
        forgotPassViewModel.loading.observe(viewLifecycleOwner, ::handleLoading)
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

    private fun handleSuccess(response: ValidateOtpFpResponse) {
        if (!response.token.isNullOrEmpty() &&
            !response.token.isNullOrBlank() &&
            !response.message.isNullOrEmpty() &&
            !response.message.isNullOrBlank() &&
            response.status == true
        ) {

            Helper.showToast(requireActivity(), requireContext(), response.message!!, isSuccess = true)
            authViewModel.setRegToken(response.token!!)
            findNavController().navigate(R.id.action_verificationCodePassFragment_to_newPasswordFragment)
        }
    }

    private fun handleForgotPassword(response: ForgotPasswordResponse) {
        if (!response.otp.isNullOrEmpty() &&
            !response.otp.isNullOrBlank() &&
            response.expiredOTP != 0L &&
            !response.email.isNullOrEmpty() &&
            !response.email.isNullOrBlank() &&
            response.success == true) {

            Helper.showToast(requireActivity(), requireContext(), "Resend code success!", isSuccess = true)
            expiredOtp = response.expiredOTP!!
            countDownTimer.start()
            binding.tv1TimerVc.text = getString(R.string.resend_code_after)
        }
    }

    private fun sendCode() {
        val code1 = binding.vc1.text
        val code2 = binding.vc2.text
        val code3 = binding.vc3.text
        val code4 = binding.vc4.text
        val otp = "${code1}${code2}${code3}${code4}"

        viewModel.validateOtp(
            ValidateOtpFpBody(email, otp)
        )
    }

    private fun resendCode() {
        forgotPassViewModel.forgotPassword(
            ForgotPasswordBody(email)
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer.cancel()
    }
}