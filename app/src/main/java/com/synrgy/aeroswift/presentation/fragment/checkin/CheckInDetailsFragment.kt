package com.synrgy.aeroswift.presentation.fragment.checkin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.synrgy.aeroswift.databinding.FragmentCheckInDetailsBinding


class CheckInDetailsFragment : Fragment() {
    private lateinit var binding: FragmentCheckInDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentCheckInDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }
}