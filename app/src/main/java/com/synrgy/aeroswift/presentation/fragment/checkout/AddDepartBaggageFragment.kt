package com.synrgy.aeroswift.presentation.fragment.checkout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.synrgy.aeroswift.R
import com.synrgy.aeroswift.databinding.FragmentAddDepartBaggageBinding
import com.synrgy.aeroswift.presentation.adapter.DepartBaggageAdapter
import com.synrgy.domain.local.DepartBaggage
import com.synrgy.presentation.constant.DepartBaggageConstant
import com.synrgy.presentation.helper.Helper


class AddDepartBaggageFragment : Fragment() {
    private lateinit var binding: FragmentAddDepartBaggageBinding
    private val adapter = DepartBaggageAdapter(::handleClickBaggage)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentAddDepartBaggageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.baggageRecycler.layoutManager = LinearLayoutManager(activity)
        binding.baggageRecycler.adapter = this.adapter
        this.adapter.submitList(DepartBaggageConstant.getData())

        binding.toolbarAddBaggage.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }

        binding.btnSave.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun handleClickBaggage(data: DepartBaggage) {
        binding.tvSubtotalPrice.text =
            getString(R.string.depart_baggage_price, Helper.formatPrice(data.price))
    }
}