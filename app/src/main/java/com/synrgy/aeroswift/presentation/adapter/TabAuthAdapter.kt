package com.synrgy.aeroswift.presentation.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.synrgy.aeroswift.presentation.fragment.auth.LoginFragment
import com.synrgy.aeroswift.presentation.fragment.auth.RegisterFragment

class TabAuthAdapter(tabActivity: FragmentActivity) : FragmentStateAdapter(tabActivity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> LoginFragment()
            1 -> RegisterFragment()
            else -> Fragment()
        }
    }
}