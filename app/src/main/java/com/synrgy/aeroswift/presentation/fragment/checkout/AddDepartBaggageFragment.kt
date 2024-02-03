package com.synrgy.aeroswift.presentation.fragment.checkout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.synrgy.aeroswift.R
import com.synrgy.aeroswift.databinding.FragmentAddDepartBaggageBinding
import com.synrgy.aeroswift.presentation.adapter.DepartBaggageAdapter
import com.synrgy.aeroswift.presentation.viewmodel.auth.AuthViewModel
import com.synrgy.aeroswift.presentation.viewmodel.checkout.AddonViewModel
import com.synrgy.domain.local.AddonData
import com.synrgy.domain.local.DepartBaggage
import com.synrgy.presentation.constant.Constant
import com.synrgy.presentation.constant.DepartBaggageConstant
import com.synrgy.presentation.helper.Helper
import dagger.hilt.android.AndroidEntryPoint
import koleton.api.hideSkeleton
import koleton.api.loadSkeleton


@AndroidEntryPoint
class AddDepartBaggageFragment : Fragment() {
    private lateinit var binding: FragmentAddDepartBaggageBinding
    private lateinit var adapter: DepartBaggageAdapter

    private val addonViewModel: AddonViewModel by viewModels()
    private val authViewModel: AuthViewModel by viewModels()

    private var userName: String? = null
    private var userId: String? = null
    private var addon: AddonData? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentAddDepartBaggageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        authViewModel.getUser()
        authViewModel.checkAuth()
        addonViewModel.getAddons()
        observeViewModel()

        binding.toolbarAddBaggage.setNavigationOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        binding.btnSave.setOnClickListener { handleSaveAddon() }
    }

    private fun observeViewModel() {
        authViewModel.name.observe(viewLifecycleOwner) {
            userName = it
            binding.tvName.text = it
        }
        authViewModel.userId.observe(viewLifecycleOwner) { userId = it }
        addonViewModel.addons.observe(viewLifecycleOwner, ::handleAddons)
        addonViewModel.loading.observe(viewLifecycleOwner, ::handleSetAdapter)
    }

    private fun handleClickBaggage(data: DepartBaggage) {
        handleSetPrice(data.price)
        addon = AddonData(
            userName = userName!!,
            category = Constant.AddonType.BAGGAGE.value,
            weight = data.weight,
            price = data.price
        )
    }

    private fun handleSaveAddon() {
        if (addon != null && Helper.isValidBaggage(addon!!)) {
            addonViewModel.saveAddons(addon!!)
        } else {
            addonViewModel.deleteAddons(Constant.AddonType.BAGGAGE.value)
        }

        requireActivity().supportFragmentManager.popBackStack()
    }

    private fun handleAddons(data: List<AddonData>) {
        if (data.isNotEmpty()) {
            addon = data.filter { it.category == Constant.AddonType.BAGGAGE.value }
                .find { it.userId == userId }
        }
    }

    private fun handleSetAdapter(loading: Boolean) {
        if (loading) {
            binding.baggageRecycler.loadSkeleton()
        } else {
            binding.baggageRecycler.hideSkeleton()

            val data = DepartBaggageConstant.getData()
            val selectedPosition = data.indexOfFirst { it.weight == addon?.weight }

            this.adapter = DepartBaggageAdapter(selectedPosition, ::handleClickBaggage)
            binding.baggageRecycler.layoutManager = LinearLayoutManager(activity)
            binding.baggageRecycler.adapter = this.adapter
            this.adapter.submitList(DepartBaggageConstant.getData())

            handleSetPrice(if (addon != null) addon!!.price else 0L)
        }
    }

    private fun handleSetPrice(price: Long) {
        binding.tvSubtotalPrice.text =
            getString(R.string.depart_baggage_price, Helper.formatPrice(price))
        binding.tvMealPrice.text =
            getString(R.string.depart_baggage_price, Helper.formatPrice(price))
    }
}