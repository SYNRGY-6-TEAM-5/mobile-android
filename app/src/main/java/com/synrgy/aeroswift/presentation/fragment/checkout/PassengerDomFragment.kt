package com.synrgy.aeroswift.presentation.fragment.checkout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.synrgy.aeroswift.databinding.FragmentPassengerDomBinding

class PassengerDomFragment : Fragment() {
    private lateinit var binding: FragmentPassengerDomBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentPassengerDomBinding.inflate(inflater, container, false)
        return binding.root
    }
}