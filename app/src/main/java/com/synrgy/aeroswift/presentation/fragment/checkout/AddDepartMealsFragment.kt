package com.synrgy.aeroswift.presentation.fragment.checkout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.synrgy.aeroswift.R
import com.synrgy.aeroswift.databinding.FragmentAddDepartMealsBinding
import com.synrgy.aeroswift.presentation.adapter.DepartMealsAdapter
import com.synrgy.domain.DepartMeals
import com.synrgy.presentation.constant.DepartMealsConstant
import com.synrgy.presentation.helper.Helper

class AddDepartMealsFragment : Fragment() {
    private lateinit var binding: FragmentAddDepartMealsBinding
    private val adapter = DepartMealsAdapter(::handleCheckMeals)

    private var price = 0L

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentAddDepartMealsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.mealsRecycler.layoutManager = LinearLayoutManager(activity)
        binding.mealsRecycler.adapter = this.adapter
        this.adapter.submitList(DepartMealsConstant.getData())

        binding.toolbarAddMeals.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }

        binding.btnSave.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }

    private fun handleCheckMeals(data: DepartMeals, isChecked: Boolean) {
        if (isChecked) price += data.price else price -= data.price
        binding.tvSubtotalPrice.text =
            getString(R.string.depart_baggage_price, Helper.formatPrice(price))
    }
}