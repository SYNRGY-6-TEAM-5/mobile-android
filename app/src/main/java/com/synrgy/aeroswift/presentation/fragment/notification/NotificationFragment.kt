package com.synrgy.aeroswift.presentation.fragment.notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.synrgy.aeroswift.R
import com.synrgy.aeroswift.databinding.FragmentNotificationBinding
import com.synrgy.aeroswift.presentation.HomeActivity
import com.synrgy.aeroswift.presentation.adapter.NotificationAdapter
import com.synrgy.domain.local.NotificationData
import com.synrgy.presentation.constant.NotificationConstant

class NotificationFragment : Fragment() {
    private lateinit var binding: FragmentNotificationBinding

    private val adapter = NotificationAdapter(::handleClickNotification)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentNotificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvNotification.layoutManager = LinearLayoutManager(requireActivity())
        binding.rvNotification.adapter = this.adapter
        this.adapter.submitList(NotificationConstant.getData())

        binding.toolbarNotification.setNavigationOnClickListener { handleNavigate() }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                handleNavigate()
            }
        })

        handleSetSpinner()

        binding.nestedScrollView.viewTreeObserver.addOnScrollChangedListener {
            if (binding.nestedScrollView.scrollY <= 200) {
                binding.fabScrollUp.visibility = View.GONE
            } else {
                binding.fabScrollUp.visibility = View.VISIBLE
            }
        }

        binding.fabScrollUp.setOnClickListener {
            binding.nestedScrollView.fullScroll(View.FOCUS_UP)
        }
    }

    private fun handleSetSpinner() {
        val spinner = binding.spinnerMore
        val adapter = ArrayAdapter(
            requireActivity(),
            R.layout.item_dropdown_spinner,
            resources.getStringArray(R.array.notification_dropdown)
        )
        spinner.adapter = adapter
    }

    private fun handleNavigate() {
        HomeActivity.startActivity(requireActivity())
        requireActivity().finish()
    }

    private fun handleClickNotification(data: NotificationData) {
        findNavController().navigate(R.id.action_notificationFragment_to_notificationDetailFragment)
    }
}