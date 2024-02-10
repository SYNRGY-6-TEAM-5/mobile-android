package com.synrgy.aeroswift.presentation.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.synrgy.aeroswift.presentation.fragment.TicketFragment

class DatePagerAdapter(tabActivity: FragmentActivity) : FragmentStateAdapter(tabActivity) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return TicketFragment.newInstance(position)
    }
}