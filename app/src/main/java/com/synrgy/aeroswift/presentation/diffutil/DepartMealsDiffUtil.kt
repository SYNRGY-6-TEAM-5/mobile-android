package com.synrgy.aeroswift.presentation.diffutil

import androidx.recyclerview.widget.DiffUtil
import com.synrgy.domain.local.DepartMeals

class DepartMealsDiffUtil: DiffUtil.ItemCallback<DepartMeals>() {
    override fun areItemsTheSame(oldItem: DepartMeals, newItem: DepartMeals): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: DepartMeals, newItem: DepartMeals): Boolean {
        return oldItem.name == newItem.name
    }
}