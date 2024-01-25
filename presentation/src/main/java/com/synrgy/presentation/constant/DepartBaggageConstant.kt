package com.synrgy.presentation.constant

import com.synrgy.domain.DepartBaggage

object DepartBaggageConstant {
    fun getData(): ArrayList<DepartBaggage> {
        return arrayListOf(
            DepartBaggage(
                weight = 10,
                price = 99000
            ),
            DepartBaggage(
                weight = 16,
                price = 199000
            ),
            DepartBaggage(
                weight = 24,
                price = 299000
            ),
            DepartBaggage(
                weight = 32,
                price = 399000
            )
        )
    }
}