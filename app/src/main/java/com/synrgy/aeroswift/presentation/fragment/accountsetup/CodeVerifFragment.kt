package com.synrgy.aeroswift.presentation.fragment.accountsetup

import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.synrgy.aeroswift.R
import com.synrgy.aeroswift.databinding.FragmentCodeVerifBinding
import com.synrgy.aeroswift.presentation.AccountSetupActivity
import com.synrgy.aeroswift.presentation.AuthActivity

class CodeVerifFragment : Fragment() {
    private lateinit var binding: FragmentCodeVerifBinding
    private lateinit var countDownTimer: CountDownTimer

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

        handleInputCode()
        handleTimer()

        binding.btnConfirmCodeReg.setOnClickListener { sendCode() }

        val bundle = requireActivity().intent.extras
        val email = bundle?.getString(AccountSetupActivity.KEY_EMAIL_SETUP) ?: "test@gmail.com"

        binding.regVerifEmail.text = "$email."

        binding.regChangeEmail.setOnClickListener { handleChangeEmail() }
    }

    private fun handleChangeEmail() {
        AuthActivity.startActivity(requireActivity())
        requireActivity().finish()
    }

    private fun handleInputCode() {
        binding.regVc1.requestFocus()

        binding.regVc1.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.length == 1) {
                    binding.regVc2.requestFocus()
                    binding.regVc1.setBackgroundResource(R.drawable.bg_verif_code_filled)
                    binding.regVc1.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                }

                if (s?.length == 0) {
                    binding.regVc1.setBackgroundResource(R.drawable.bg_verif_code)
                    binding.regVc1.setTextColor(ContextCompat.getColor(requireContext(), R.color.base_black))
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        binding.regVc2.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.length == 1) {
                    binding.regVc3.requestFocus()
                    binding.regVc2.setBackgroundResource(R.drawable.bg_verif_code_filled)
                    binding.regVc2.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                }

                if (s?.length == 0) {
                    binding.regVc1.requestFocus()
                    binding.regVc2.setBackgroundResource(R.drawable.bg_verif_code)
                    binding.regVc2.setTextColor(ContextCompat.getColor(requireContext(), R.color.base_black))
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        binding.regVc3.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.length == 1) {
                    binding.regVc4.requestFocus()
                    binding.regVc3.setBackgroundResource(R.drawable.bg_verif_code_filled)
                    binding.regVc3.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                }

                if (s?.length == 0) {
                    binding.regVc2.requestFocus()
                    binding.regVc3.setBackgroundResource(R.drawable.bg_verif_code)
                    binding.regVc3.setTextColor(ContextCompat.getColor(requireContext(), R.color.base_black))
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        binding.regVc4.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.length == 1) {
                    binding.regVc4.setBackgroundResource(R.drawable.bg_verif_code_filled)
                    binding.regVc4.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
                }

                if (s?.length == 0) {
                    binding.regVc3.requestFocus()
                    binding.regVc4.setBackgroundResource(R.drawable.bg_verif_code)
                    binding.regVc4.setTextColor(ContextCompat.getColor(requireContext(), R.color.base_black))
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

                binding.tv2TimerVcReg.text = timeString
            }

            override fun onFinish() {
                binding.tv1TimerVcReg.text = ""
                binding.tv2TimerVcReg.text = getString(R.string.resend)

                binding.tv2TimerVcReg.setOnClickListener {
                    countDownTimer.start()
                    binding.tv1TimerVcReg.text = getString(R.string.resend_code_after)
                }
            }
        }

        countDownTimer.start()
    }

    private fun sendCode() {
        findNavController().navigate(R.id.action_codeVerifFragment_to_accountDetailFragment)
    }

    override fun onDestroy() {
        super.onDestroy()
        countDownTimer.cancel()
    }
}