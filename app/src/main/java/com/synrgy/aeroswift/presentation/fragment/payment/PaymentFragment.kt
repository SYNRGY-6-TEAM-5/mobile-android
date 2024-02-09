package com.synrgy.aeroswift.presentation.fragment.payment

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.synrgy.aeroswift.R
import com.synrgy.aeroswift.databinding.FragmentPaymentBinding
import com.synrgy.aeroswift.dialog.LoadingDialog
import com.synrgy.aeroswift.presentation.adapter.PaymentMethodAdapter
import com.synrgy.aeroswift.presentation.viewmodel.payment.PaymentViewModel
import com.synrgy.domain.local.PaymentMethod
import com.synrgy.presentation.constant.PaymentMethodConstant
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale


@AndroidEntryPoint
class PaymentFragment : Fragment() {
    private lateinit var binding: FragmentPaymentBinding

    private val paymentViewModel: PaymentViewModel by viewModels()

    private val paymentMethodAdapter = PaymentMethodAdapter(::handleClickPayment)

    private lateinit var loadingDialog: LoadingDialog

    private var remainingTime: Long = 3600000

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentPaymentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadingDialog = LoadingDialog(requireActivity())

        startTimer()
        handleSetAdapter()
        observeViewModel()

        binding.btnPay.setOnClickListener { paymentViewModel.processPayment() }
        binding.toolbarPayment.setNavigationOnClickListener { requireActivity().onBackPressed() }
    }

    private fun handleSetAdapter() {
        binding.rvPayment.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvPayment.adapter = this.paymentMethodAdapter
        this.paymentMethodAdapter.submitList(PaymentMethodConstant.getData())
    }

    private fun observeViewModel() {
        paymentViewModel.loading.observe(viewLifecycleOwner, ::handleLoading)
    }

    private fun handleLoading(loading: Boolean) {
        if (loading) {
            loadingDialog.startLoadingDialog()
        } else {
            loadingDialog.dismissDialog()
            handleNavigate()
        }
    }

    private fun handleNavigate() {
        val bundle = Bundle()
        bundle.putLong(PaymentInfoFragment.KEY_REMAINING_TIME, remainingTime)
        findNavController().navigate(R.id.action_paymentFragment_to_paymentInfoFragment, bundle)
    }

    private fun handleClickPayment(data: PaymentMethod, isChecked: Boolean) {}

    private fun startTimer() {
        val timer = object : CountDownTimer(3600000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val seconds = (millisUntilFinished / 1000).toInt()
                val minutes = seconds / 60
                val hours = minutes / 60

                val displaySeconds = seconds % 60
                val displayMinutes = minutes % 60

                val time = String.format(Locale.getDefault(), "%02d:%02d:%02d", hours, displayMinutes, displaySeconds)
                binding.tvTimer.text = time
                remainingTime = millisUntilFinished
            }

            override fun onFinish() {
                binding.tvTimer.text = "Complete the Payment"
            }
        }
        timer.start()
    }
}