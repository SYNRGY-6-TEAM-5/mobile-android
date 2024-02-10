package com.synrgy.presentation.constant

import com.synrgy.domain.local.PaymentMethod
import com.synrgy.presentation.R

object PaymentMethodConstant {
    fun getData(): ArrayList<PaymentMethod> {
        return arrayListOf(
            PaymentMethod(
                id = 1,
                bankImage = R.drawable.img_bank_mandiri,
                bankName = "Mandiri Virtual Account"
            ),
            PaymentMethod(
                id = 2,
                bankImage = R.drawable.img_bank_ocbc,
                bankName = "OCB Virtual Account"
            ),
            PaymentMethod(
                id = 3,
                bankImage = R.drawable.img_bank_bca,
                bankName = "BCA Virtual Account"
            )
        )
    }
}