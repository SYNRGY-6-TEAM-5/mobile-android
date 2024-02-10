package com.synrgy.aeroswift.presentation.fragment.passenger

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.synrgy.aeroswift.R
import com.synrgy.aeroswift.databinding.FragmentPassengerListBinding
import com.synrgy.aeroswift.presentation.HomeActivity
import com.synrgy.aeroswift.presentation.adapter.PassengerAdapter
import com.synrgy.aeroswift.presentation.viewmodel.auth.AuthViewModel
import com.synrgy.aeroswift.presentation.viewmodel.passenger.PassengerDetailsViewModel
import com.synrgy.domain.local.PassengerData
import dagger.hilt.android.AndroidEntryPoint
import koleton.api.hideSkeleton
import koleton.api.loadSkeleton

@AndroidEntryPoint
class PassengerListFragment : Fragment() {
    private lateinit var binding: FragmentPassengerListBinding

    private val authViewModel: AuthViewModel by viewModels()
    private val passengerViewModel: PassengerDetailsViewModel by viewModels()

    private val adapter = PassengerAdapter(::handleClickPassenger)

    private var userId: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentPassengerListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                HomeActivity.startProfileFragment(requireActivity())
                requireActivity().finish()
            }
        })

        authViewModel.checkAuth()
        authViewModel.getUser()
        passengerViewModel.getPassengers()

        observeViewModel()

        binding.cardOwner.setOnClickListener { handleNavigate(userId, isOwner = true) }
        binding.btnAdd.setOnClickListener { handleNavigate() }
        binding.toolbarPassenger.setNavigationOnClickListener { requireActivity().onBackPressed() }
    }

    private fun observeViewModel() {
        authViewModel.loading.observe(viewLifecycleOwner, ::handleLoading)
        authViewModel.name.observe(viewLifecycleOwner, ::handleGetName)
        authViewModel.userId.observe(viewLifecycleOwner) { userId = it }
        passengerViewModel.passengers.observe(viewLifecycleOwner, ::handleSetAdapter)
    }

    private fun handleLoading(loading: Boolean) {
        if (loading) {
            binding.tvUserName.loadSkeleton()
        } else {
            binding.tvUserName.hideSkeleton()
        }
    }

    private fun handleGetName(name: String) {
        authViewModel.setName(name)
        binding.tvUserName.text = name
    }

    private fun handleNavigate(id: String? = null, isOwner: Boolean = false) {
        val bundle = Bundle()
        bundle.putString(PassengerDetailsFragment.KEY_PASSENGER_DETAIL_ID, id)
        bundle.putBoolean(PassengerDetailsFragment.KEY_IS_PASSENGER_OWNER, isOwner)

        findNavController().navigate(R.id.action_passengerListFragment_to_passengerDetailsFragment, bundle)
    }

    private fun handleSetAdapter(data: List<PassengerData>) {
        binding.rvPassenger.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvPassenger.adapter = this.adapter
        this.adapter.submitList(data.filter { it.id != userId })
    }

    private fun handleClickPassenger(data: PassengerData) {
        handleNavigate(data.id)
    }
}