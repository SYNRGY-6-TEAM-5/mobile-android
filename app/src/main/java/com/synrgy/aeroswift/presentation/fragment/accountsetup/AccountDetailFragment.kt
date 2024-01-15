package com.synrgy.aeroswift.presentation.fragment.accountsetup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.synrgy.aeroswift.R
import com.synrgy.aeroswift.databinding.FragmentAccountDetailBinding

class AccountDetailFragment : Fragment() {
    private lateinit var binding: FragmentAccountDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentAccountDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvSkipAccountDetail.setOnClickListener { handleNavigate() }
    }

    private fun handleNavigate() {
        findNavController().navigate(R.id.action_accountDetailFragment_to_setupEmailSuccessFragment)
    }
}