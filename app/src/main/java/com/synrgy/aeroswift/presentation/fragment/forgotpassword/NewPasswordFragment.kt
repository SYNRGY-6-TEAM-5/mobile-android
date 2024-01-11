package com.synrgy.aeroswift.presentation.fragment.forgotpassword

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.synrgy.aeroswift.R
import com.synrgy.aeroswift.databinding.FragmentForgotPassBinding
import com.synrgy.aeroswift.databinding.FragmentNewPasswordBinding

class NewPasswordFragment : Fragment() {

    private lateinit var binding: FragmentNewPasswordBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentNewPasswordBinding.inflate(layoutInflater)
        val view = binding.root

        binding.btnChangePassword.setOnClickListener { changePass() }

        return view

    }

    private fun changePass() {
        findNavController().navigate(R.id.action_newPasswordFragment_to_doneResetPassFragment)
    }
}