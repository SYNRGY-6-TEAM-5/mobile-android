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
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        binding.vc2.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.length == 1) {
                    binding.vc3.requestFocus()
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        binding.vc3.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.length == 1) {
                    binding.vc4.requestFocus()
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