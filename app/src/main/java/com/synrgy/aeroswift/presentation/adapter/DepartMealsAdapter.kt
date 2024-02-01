package com.synrgy.aeroswift.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.synrgy.aeroswift.databinding.ItemDepartMealsBinding
import com.synrgy.aeroswift.presentation.diffutil.DepartMealsDiffUtil
import com.synrgy.aeroswift.presentation.viewholder.DepartMealsViewHolder
import com.synrgy.domain.local.DepartMeals

class DepartMealsAdapter(
    private val clickListener: (DepartMeals, isChecked: Boolean) -> Unit
): ListAdapter<DepartMeals, DepartMealsViewHolder>(
    DepartMealsDiffUtil()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DepartMealsViewHolder {
        return DepartMealsViewHolder(
            ItemDepartMealsBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: DepartMealsViewHolder, position: Int) {
        holder.bindData(getItem(position), clickListener)
    }
}