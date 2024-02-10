package com.synrgy.aeroswift.presentation.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.synrgy.aeroswift.presentation.fragment.OneWayFragment
import com.synrgy.aeroswift.presentation.viewmodel.HomeViewModel

class TabTripAdapter(
    tabActivity: FragmentActivity,
    private val homeViewModel: HomeViewModel
) : FragmentStateAdapter(tabActivity) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return OneWayFragment(homeViewModel)
    }
}