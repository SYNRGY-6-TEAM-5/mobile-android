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
import androidx.navigation.fragment.findNavController
import com.synrgy.aeroswift.R
import com.synrgy.aeroswift.databinding.FragmentVerificationCodePassBinding
import com.synrgy.aeroswift.dialog.ForgotPassDialog

class VerificationCodePassFragment : Fragment() {

    private lateinit var binding: FragmentVerificationCodePassBinding

    private lateinit var countDownTimer: CountDownTimer

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentVerificationCodePassBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handleInputCode()
        handleTimer()

        binding.btnConfirmCode.setOnClickListener { sendCode() }

        val bundle = requireActivity().intent.extras

        binding.verifDesc.text = Html.fromHtml(getString(
            R.string.verification_code_desc,
            "<b>${bundle?.getString(ForgotPassDialog.KEY_EMAIL_RECOVERY)
                ?: "test@gmail.com"}.</b>"
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
        val totalTimeInMillis: Long = 97 * 1000

        countDownTimer = object : CountDownTimer(totalTimeInMillis, 1000) {
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
                    countDownTimer.start()
                    binding.tv1TimerVc.text = getString(R.string.resend_code_after)
                }
            }
        }

        countDownTimer.start()
    }

    private fun sendCode() {
        findNavController().navigate(R.id.action_verificationCodePassFragment_to_newPasswordFragment)
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer.cancel()
    }
}