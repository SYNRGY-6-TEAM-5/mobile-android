package com.synrgy.aeroswift.presentation.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.synrgy.aeroswift.presentation.fragment.OneWayFragment

class TabTripAdapter(tabActivity: FragmentActivity) : FragmentStateAdapter(tabActivity) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> OneWayFragment()
            1 -> OneWayFragment()
            else -> Fragment()
        }
    }
}