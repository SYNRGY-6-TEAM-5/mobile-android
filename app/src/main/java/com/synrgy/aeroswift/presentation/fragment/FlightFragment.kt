package com.synrgy.aeroswift.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.synrgy.aeroswift.databinding.FragmentFlightBinding
import com.synrgy.aeroswift.dialog.AuthDialog
import com.synrgy.aeroswift.presentation.FlightHistoryActivity
import com.synrgy.aeroswift.presentation.viewmodel.auth.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FlightFragment : Fragment() {
    private lateinit var binding: FragmentFlightBinding

    private val authViewModel: AuthViewModel by viewModels()

    private lateinit var authDialog: AuthDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFlightBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        authDialog = AuthDialog(requireActivity())

        authViewModel.getUser()
        authViewModel.checkAuth()
        observeViewModel()

        binding.tvHistory.setOnClickListener {
            FlightHistoryActivity.startActivity(requireActivity())
        }
    }

    private fun observeViewModel() {
        authViewModel.authentication.observe(viewLifecycleOwner, ::handleAuthAlert)
    }

    private fun handleAuthAlert(token: String) {
        if (token.isEmpty() || token.isBlank()) authDialog.show()
    }
}