package com.synrgy.aeroswift.presentation.fragment.forgotpassword

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.synrgy.aeroswift.R
import com.synrgy.aeroswift.databinding.FragmentForgotPassBinding


class ForgotPassFragment : Fragment() {

    private lateinit var binding: FragmentForgotPassBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentForgotPassBinding.inflate(layoutInflater)
        val view = binding.root

        binding.btnSendCode.setOnClickListener { sendCode() }

        return view

    }

    private fun sendCode() {
        findNavController().navigate(R.id.action_forgotPassFragment_to_verificationCodePassFragment)
    }
}