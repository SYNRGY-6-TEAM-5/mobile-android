package com.synrgy.aeroswift.presentation.fragment.checkin

import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.synrgy.aeroswift.R
import com.synrgy.aeroswift.databinding.FragmentCheckInPolicyBinding
import com.synrgy.aeroswift.presentation.adapter.BulletListAdapter
import com.synrgy.aeroswift.presentation.adapter.PassengerReqAdapter
import com.synrgy.presentation.constant.DangerousGoodsConstant
import com.synrgy.presentation.constant.ImportantInformationConstant
import com.synrgy.presentation.constant.PassengerReqConstant


class CheckInPolicyFragment : Fragment() {
    private lateinit var binding: FragmentCheckInPolicyBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentCheckInPolicyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        handleSetAdapter()
        handleSetTextSpan()

        binding.toolbarCheckInPolicy.setNavigationOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        binding.cbAgree.setOnCheckedChangeListener { _, checked -> binding.btnContinue.isEnabled = checked }
        binding.btnContinue.setOnClickListener {
            findNavController().navigate(R.id.action_checkInPolicyFragment_to_checkInDataFragment)
        }
    }

    private fun handleSetAdapter() {
        val passengerReqAdapter = PassengerReqAdapter()
        binding.rvPassengerReq.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvPassengerReq.adapter = passengerReqAdapter
        passengerReqAdapter.submitList(PassengerReqConstant.getData())

        val informationAdapter = BulletListAdapter()
        binding.rvImportantInformation.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvImportantInformation.adapter = informationAdapter
        informationAdapter.submitList(ImportantInformationConstant.getData())

        val dangerousAdapter = BulletListAdapter()
        binding.rvDangerousGoods.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvDangerousGoods.adapter = dangerousAdapter
        dangerousAdapter.submitList(DangerousGoodsConstant.getData())
    }

    private fun handleSetTextSpan() {
        val policyText =
            SpannableString(resources.getString(R.string.i_agree_to_the_check_in_policy))
        val startIndex = policyText.indexOf("check-in policy")
        val endIndex = startIndex + "check-in policy".length

        policyText.setSpan(
            StyleSpan(Typeface.BOLD),
            startIndex,
            endIndex,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        binding.cbAgree.text = policyText
    }
}