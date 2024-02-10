package com.synrgy.presentation.constant

import com.synrgy.domain.local.PaymentInstruction

object PaymentInstructionConstant {
    fun getData(): ArrayList<PaymentInstruction> {
        return arrayListOf(
            PaymentInstruction(
                id = 1,
                title = "Transfer via ATM",
                description = "Transfer funds conveniently using Automated Teller Machines (ATMs) available at various locations. Simply insert your card, follow the on-screen instructions, and complete the transfer securely."
            ),
            PaymentInstruction(
                id = 2,
                title = "Transfer via Internet Banking",
                description = "Seamlessly transfer money through your bank's online banking platform. Log in to your account, navigate to the transfer section, input the recipient's details and amount, and authorize the transaction. Enjoy the ease and efficiency of managing your finances online."
            ),
            PaymentInstruction(
                id = 3,
                title = "Transfer via Mobile Banking",
                description = "Transfer funds swiftly using your mobile device with your bank's dedicated mobile banking application. Access your account anytime, anywhere, and initiate transfers with just a few taps. Experience the convenience of banking on the go, right from the palm of your hand."
            )
        )
    }
}