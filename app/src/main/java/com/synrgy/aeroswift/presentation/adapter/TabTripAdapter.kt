package com.synrgy.aeroswift.presentation.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.synrgy.aeroswift.presentation.fragment.tabTrip.MultiFragment
import com.synrgy.aeroswift.presentation.fragment.tabTrip.OneWayFragment
import com.synrgy.aeroswift.presentation.fragment.tabTrip.RoundTripFragment

class TabTripAdapter(tabActivity: FragmentActivity) : FragmentStateAdapter(tabActivity) {

    private fun geTabCount(): Int = 2
    override fun getItemCount(): Int {
        return geTabCount()
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> OneWayFragment()
            else -> RoundTripFragment()
        }
    }
}