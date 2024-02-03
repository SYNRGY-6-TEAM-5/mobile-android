package com.synrgy.aeroswift.presentation.fragment.flightorder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.synrgy.aeroswift.databinding.FragmentPaymentHistoryBinding
import com.synrgy.aeroswift.presentation.adapter.FlightHistoryAdapter
import com.synrgy.presentation.constant.FlightHistoryConstant


class PaymentHistoryFragment : Fragment() {
    companion object {
        const val KEY_PAYMENT_CATEGORY = "payment_category"
    }

    private lateinit var binding: FragmentPaymentHistoryBinding

    private val adapter = FlightHistoryAdapter()

    private lateinit var category: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentPaymentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        category = arguments?.getString(KEY_PAYMENT_CATEGORY)!!

        handleSetAdapter()

        binding.tvToolbar.text = category
        binding.toolbarPayment.setNavigationOnClickListener { requireActivity().onBackPressed() }
    }

    private fun handleSetAdapter() {
        binding.rvPayment.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvPayment.adapter = this.adapter
        this.adapter.submitList(FlightHistoryConstant.getData().filter {
            it.category == category
        })
    }
}