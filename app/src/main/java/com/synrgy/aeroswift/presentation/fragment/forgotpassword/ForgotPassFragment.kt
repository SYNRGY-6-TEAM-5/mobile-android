package com.synrgy.aeroswift.presentation.fragment.forgotpassword

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.synrgy.aeroswift.R
import com.synrgy.aeroswift.databinding.FragmentForgotPassBinding
import com.synrgy.aeroswift.dialog.LoadingDialog


class ForgotPassFragment : Fragment() {

    private lateinit var binding: FragmentForgotPassBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentForgotPassBinding.inflate(layoutInflater)
        val view = binding.root

        val loadingDialog = LoadingDialog(requireActivity())

        binding.btnSendCode.setOnClickListener {
            loadingDialog.startLoadingDialog()

            Handler().postDelayed({
                loadingDialog.dismissDialog()
                sendCode()
            }, 1000)
        }

        return view

    }

    private fun sendCode() {
        findNavController().navigate(R.id.action_forgotPassFragment_to_verificationCodePassFragment)
    }
}