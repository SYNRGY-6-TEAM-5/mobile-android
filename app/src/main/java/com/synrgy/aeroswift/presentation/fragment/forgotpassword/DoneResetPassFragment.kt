package com.synrgy.aeroswift.presentation.fragment.forgotpassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.synrgy.aeroswift.databinding.FragmentDoneResetPassBinding
import com.synrgy.aeroswift.presentation.AuthActivity

class DoneResetPassFragment: Fragment() {
    private lateinit var binding: FragmentDoneResetPassBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDoneResetPassBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnDoneLogin.setOnClickListener {
            AuthActivity.startActivity(requireActivity())
            requireActivity().finish()
        }
    }
}