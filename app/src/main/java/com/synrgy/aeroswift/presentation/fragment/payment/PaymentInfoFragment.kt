package com.synrgy.aeroswift.presentation.fragment.payment

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.synrgy.aeroswift.databinding.FragmentPaymentInfoBinding
import com.synrgy.aeroswift.presentation.adapter.PaymentInstructionAdapter
import com.synrgy.presentation.constant.PaymentInstructionConstant
import com.synrgy.presentation.constant.PaymentMethodConstant
import com.synrgy.presentation.helper.Helper
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale


@AndroidEntryPoint
class PaymentInfoFragment : Fragment() {
    companion object {
        const val KEY_REMAINING_TIME = "key_remaining_time"
        const val KEY_BANK_ID = "key_bank_id"
    }

    private lateinit var binding: FragmentPaymentInfoBinding

    private val adapter = PaymentInstructionAdapter()

    private var remainingTime: Long = 3600000

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentPaymentInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bankId = arguments?.getInt(KEY_BANK_ID, 1)
        val bankData = PaymentMethodConstant.getData().find { it.id == bankId }

        remainingTime = arguments?.getLong(KEY_REMAINING_TIME) ?: 3600000
        startTimer()
        handleSetAdapter()

        binding.ivCopy.setOnClickListener {
            val text = binding.tvPaymentCode.text.toString()
            Helper.copyToClipboard(requireContext(), "payment_code", text)
            Toast.makeText(requireContext(), "Code copied!", Toast.LENGTH_LONG).show()
        }

        binding.toolbarPaymentInfo.setNavigationOnClickListener { requireActivity().onBackPressed() }
        binding.btnOrderList.setOnClickListener { requireActivity().onBackPressed() }

        binding.tvBankName.text = bankData?.bankName
        binding.ivBank.setImageResource(bankData?.bankImage!!)
    }

    private fun handleSetAdapter() {
        binding.rvHowToPay.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvHowToPay.adapter = this.adapter
        this.adapter.submitList(PaymentInstructionConstant.getData())
    }

    private fun startTimer() {
        val timer = object : CountDownTimer(remainingTime, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val seconds = (millisUntilFinished / 1000).toInt()
                val minutes = seconds / 60
                val hours = minutes / 60

                val displaySeconds = seconds % 60
                val displayMinutes = minutes % 60

                val time = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, displayMinutes, displaySeconds)
                binding.tvTimer.text = time
            }

            override fun onFinish() {
                binding.tvTimer.text = "Complete the Payment"
            }
        }
        timer.start()
    }
}