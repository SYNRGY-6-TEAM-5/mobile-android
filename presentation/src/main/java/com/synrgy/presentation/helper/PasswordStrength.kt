package com.synrgy.presentation.helper

import com.synrgy.presentation.R


enum class PasswordStrength(
    var message: Int,
    var color: Int
) {
    WEAK(R.string.weak, R.color.password_weak),
    OKAY(R.string.okay, R.color.password_okay),
    STRONG(R.string.strong, R.color.password_strong);

    companion object {
        private const val MIN_LENGTH = 8
        private const val MAX_LENGTH = 15

        fun calculate(password: String): PasswordStrength {
            var score = 0
            var upper = false
            var lower = false
            var digit = false
            var specialChar = false

            for (element in password) {
                if (!specialChar && !Character.isLetterOrDigit(element)) {
                    score++
                    specialChar = true
                } else {
                    if (!digit && Character.isDigit(element)) {
                        score++
                        digit = true
                    } else {
                        if (!upper || !lower) {
                            if (Character.isUpperCase(element)) {
                                upper = true
                            } else {
                                lower = true
                            }
                            if (upper && lower) {
                                score++
                            }
                        }
                    }
                }
            }

            val length = password.length
            if (length > MAX_LENGTH) {
                if (score == 3) {
                    score++
                }
            } else if (length < MIN_LENGTH) {
                score = 0
            }

            return when (score) {
                in 0..2 -> WEAK
                3 -> OKAY
                else -> STRONG
            }
        }
    }
}