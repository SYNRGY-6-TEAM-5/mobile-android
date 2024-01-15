package com.synrgy.aeroswift.presentation.fragment.forgotpassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.synrgy.aeroswift.R
import com.synrgy.aeroswift.databinding.FragmentNewPasswordBinding

class NewPasswordFragment : Fragment() {

    private lateinit var binding: FragmentNewPasswordBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewPasswordBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnChangePassword.setOnClickListener { changePass() }
    }

    private fun changePass() {
        findNavController().navigate(R.id.action_newPasswordFragment_to_doneResetPassFragment)
    }
}