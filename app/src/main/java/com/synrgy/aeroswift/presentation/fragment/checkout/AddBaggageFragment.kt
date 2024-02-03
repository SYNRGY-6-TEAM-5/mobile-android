package com.synrgy.aeroswift.presentation.fragment.checkout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.synrgy.aeroswift.R
import com.synrgy.aeroswift.databinding.FragmentAddBaggageBinding
import com.synrgy.aeroswift.models.AddonModels
import com.synrgy.aeroswift.presentation.adapter.AddonAdapter
import com.synrgy.aeroswift.presentation.viewmodel.checkout.AddonViewModel
import com.synrgy.domain.local.AddonData
import com.synrgy.presentation.constant.Constant
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AddBaggageFragment : Fragment() {
    private lateinit var binding: FragmentAddBaggageBinding

    private val addonViewModel: AddonViewModel by viewModels()

    private val adapter = AddonAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentAddBaggageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        addonViewModel.getAddons()
        observeViewModel()

        binding.rvBaggage.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvBaggage.adapter = this.adapter

        binding.toolbarAddBaggage.setNavigationOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        binding.btnDone.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        binding.tvDepartSelect.setOnClickListener {
            findNavController().navigate(R.id.action_addBaggageFragment_to_addDepartBaggageFragment)
        }
    }

    private fun observeViewModel() {
        addonViewModel.addons.observe(viewLifecycleOwner, ::handleGetAddons)
    }

    private fun handleGetAddons(addons: List<AddonData>) {
        if (addons.isNotEmpty()) {
            binding.tvDepartSelect.text = "Change"

            val filteredAddons = addons
                .filter { it.category == Constant.AddonType.BAGGAGE.value }
                .groupBy { it.userId }

            val list = mutableListOf<AddonModels>()

            filteredAddons.forEach { (_, value) ->
                value.forEach {
                    list.add(
                        AddonModels(
                            id = it.userId,
                            name = it.userName,
                            count = "${it.weight} KG"
                        )
                    )
                }
            }

            this.adapter.submitList(list)
        } else {
            this.adapter.submitList(emptyList())
        }
    }
}