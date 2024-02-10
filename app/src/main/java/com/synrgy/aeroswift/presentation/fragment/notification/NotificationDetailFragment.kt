package com.synrgy.aeroswift.presentation.fragment.notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.synrgy.aeroswift.databinding.FragmentNotificationDetailBinding
import com.synrgy.presentation.helper.Helper

class NotificationDetailFragment : Fragment() {
    private lateinit var binding: FragmentNotificationDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentNotificationDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbarNotification.setNavigationOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        binding.ivCopy.setOnClickListener {
            val text = binding.tvPromoCode.text.toString()
            Helper.copyToClipboard(requireContext(), "promo_code", text)
            Toast.makeText(requireContext(), "Code copied!", Toast.LENGTH_LONG).show()
        }
    }
}