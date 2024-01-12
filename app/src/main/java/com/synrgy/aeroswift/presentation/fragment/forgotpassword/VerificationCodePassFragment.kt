package com.synrgy.aeroswift.presentation.fragment.forgotpassword

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.synrgy.aeroswift.R
import com.synrgy.aeroswift.databinding.FragmentForgotPassBinding
import com.synrgy.aeroswift.databinding.FragmentVerificationCodePassBinding

class VerificationCodePassFragment : Fragment() {

    private lateinit var binding: FragmentVerificationCodePassBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentVerificationCodePassBinding.inflate(layoutInflater)
        val view = binding.root

        binding.vc1.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.length == 1) {
                    binding.vc2.requestFocus()
                    binding.vc1.background = resources.getDrawable(R.drawable.bg_verif_code_filled)
                    binding.vc1.setTextColor(resources.getColor(R.color.white))
                }

                if (s?.length == 0) {
                    binding.vc1.background = resources.getDrawable(R.drawable.bg_verif_code)
                    binding.vc1.setTextColor(resources.getColor(R.color.base_black))
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        binding.vc2.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.length == 1) {
                    binding.vc3.requestFocus()
                    binding.vc2.background = resources.getDrawable(R.drawable.bg_verif_code_filled)
                    binding.vc2.setTextColor(resources.getColor(R.color.white))
                }

                if (s?.length == 0) {
                    binding.vc1.requestFocus()
                    binding.vc2.background = resources.getDrawable(R.drawable.bg_verif_code)
                    binding.vc2.setTextColor(resources.getColor(R.color.base_black))
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        binding.vc3.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.length == 1) {
                    binding.vc4.requestFocus()
                    binding.vc3.background = resources.getDrawable(R.drawable.bg_verif_code_filled)
                    binding.vc3.setTextColor(resources.getColor(R.color.white))
                }

                if (s?.length == 0) {
                    binding.vc2.requestFocus()
                    binding.vc3.background = resources.getDrawable(R.drawable.bg_verif_code)
                    binding.vc3.setTextColor(resources.getColor(R.color.base_black))
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        binding.vc4.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.length == 1) {
                    binding.vc4.background = resources.getDrawable(R.drawable.bg_verif_code_filled)
                    binding.vc4.setTextColor(resources.getColor(R.color.white))
                }

                if (s?.length == 0) {
                    binding.vc3.requestFocus()
                    binding.vc4.background = resources.getDrawable(R.drawable.bg_verif_code)
                    binding.vc4.setTextColor(resources.getColor(R.color.base_black))
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        binding.btnConfirmCode.setOnClickListener { sendCode() }

        return view

    }

    private fun sendCode() {
        findNavController().navigate(R.id.action_verificationCodePassFragment_to_newPasswordFragment)
    }
}