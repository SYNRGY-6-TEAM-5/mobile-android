package com.synrgy.aeroswift.presentation.fragment.flightorder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.synrgy.aeroswift.databinding.FragmentPaymentHistoryBinding
import com.synrgy.aeroswift.presentation.CheckInActivity
import com.synrgy.aeroswift.presentation.OrderDetailActivity
import com.synrgy.aeroswift.presentation.adapter.FlightHistoryAdapter
import com.synrgy.presentation.constant.Constant
import com.synrgy.presentation.constant.FlightHistoryConstant


class PaymentHistoryFragment : Fragment() {
    companion object {
        const val KEY_PAYMENT_CATEGORY = "payment_category"
    }

    private lateinit var binding: FragmentPaymentHistoryBinding

    private val adapter = FlightHistoryAdapter(::handleClickHistory)

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
        binding.toolbarPayment.setNavigationOnClickListener { requireActivity().supportFragmentManager.popBackStack() }

        binding.nestedScrollView.viewTreeObserver.addOnScrollChangedListener {
            if (binding.nestedScrollView.scrollY <= 200) {
                binding.fabScrollUp.visibility = View.GONE
            } else {
                binding.fabScrollUp.visibility = View.VISIBLE
            }
        }

        binding.fabScrollUp.setOnClickListener {
            binding.nestedScrollView.fullScroll(View.FOCUS_UP)
        }
    }

    private fun handleSetAdapter() {
        val data = FlightHistoryConstant.getData().filter { it.category == category }
        binding.rvPayment.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvPayment.adapter = this.adapter
        this.adapter.submitList(data)
    }

    private fun handleClickHistory() {
        if (category == Constant.FlightHistoryCategory.PROCESSING.value) {
            CheckInActivity.startActivity(requireActivity())
            requireActivity().finish()
        }
        if (category == Constant.FlightHistoryCategory.AWAITING_PAYMENT.value) {
            OrderDetailActivity.startActivity(requireActivity())
        }
    }
}