package com.synrgy.aeroswift.presentation.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.synrgy.aeroswift.R
import com.synrgy.aeroswift.databinding.ItemDepartMealsBinding
import com.synrgy.domain.local.DepartMeals
import com.synrgy.presentation.helper.Helper

class DepartMealsViewHolder(
    private val binding: ItemDepartMealsBinding
): RecyclerView.ViewHolder(binding.root) {
    val item = binding
    fun bindData(
        data: DepartMeals,
        clickListener: (DepartMeals, isChecked: Boolean) -> Unit,
    ) {
        val context = binding.root.context

        binding.imgMeals.setImageResource(data.image)
        binding.mealsName.text = data.name
        binding.mealsPrice.text =
            context.getString(R.string.meals_price, Helper.formatPrice(data.price))

        binding.cbMeals.setOnCheckedChangeListener { _, isChecked ->
            clickListener(data, isChecked)
        }
    }
}