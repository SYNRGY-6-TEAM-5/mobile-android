package com.synrgy.presentation.constant

import com.synrgy.domain.local.DepartMeals
import com.synrgy.presentation.R

object DepartMealsConstant {
    fun getData(): ArrayList<DepartMeals> {
        return arrayListOf(
            DepartMeals(
                image = R.drawable.img_fried_noodle,
                name = "Fried Noodle",
                price = 99000
            ),
            DepartMeals(
                image = R.drawable.img_fried_rice,
                name = "Fried Rice",
                price = 49000
            ),
            DepartMeals(
                image = R.drawable.img_omellete,
                name = "Omellete",
                price = 19000
            ),
            DepartMeals(
                image = R.drawable.img_rendang_padang,
                name = "Rendang Padang",
                price = 23000
            )
        )
    }
}