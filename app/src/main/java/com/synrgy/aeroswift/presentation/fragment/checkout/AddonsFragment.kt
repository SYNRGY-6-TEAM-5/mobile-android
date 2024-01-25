package com.synrgy.aeroswift.presentation.fragment.checkout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.synrgy.aeroswift.databinding.FragmentAddonsBinding
import com.synrgy.aeroswift.dialog.TripConfirmationDialog


class AddonsFragment : Fragment() {
    private lateinit var binding: FragmentAddonsBinding
    private lateinit var confirmationDialog: TripConfirmationDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentAddonsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        confirmationDialog = TripConfirmationDialog(requireActivity())

        binding.btnContinue.setOnClickListener { confirmationDialog.show() }
    }
}