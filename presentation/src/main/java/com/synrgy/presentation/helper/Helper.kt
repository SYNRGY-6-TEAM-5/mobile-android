package com.synrgy.presentation.helper

import android.app.Activity
import android.content.Context
import androidx.core.content.res.ResourcesCompat
import com.synrgy.presentation.R
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle

object Helper {
    fun showToast(activity: Activity, context: Context, message: String, isSuccess: Boolean) {
        MotionToast.createColorToast(activity,
            if (isSuccess) context.getString(R.string.message_success) else context.getString(R.string.message_error),
            message,
            if (isSuccess) MotionToastStyle.SUCCESS else MotionToastStyle.ERROR,
            MotionToast.GRAVITY_BOTTOM,
            MotionToast.LONG_DURATION,
            ResourcesCompat.getFont(context, R.font.inter))
    }

    fun maskEmail(email: String): String {
        val parts = email.split('@')

        if (parts.size == 2) {
            val username = parts[0]
            val domain = parts[1]

            val maskedUsername = maskUsername(username)
            val maskedDomain = maskDomain(domain)

            return "$maskedUsername@$maskedDomain"
        }

        return email
    }

    private fun maskUsername(input: String): String {
        val visibleCharacters = 2

        return if (input.length > visibleCharacters) {
            val maskedPart = "*".repeat(input.length - visibleCharacters)
            input.substring(0, visibleCharacters) + maskedPart
        } else {
            input
        }
    }

    private fun maskDomain(input: String): String {
        val visibleCharacters = input.indexOf(".")

        return if (input.length > visibleCharacters) {
            val maskedPart = "*".repeat(input.length - visibleCharacters)
            maskedPart + input.substring(input.indexOf("."), input.length)
        } else {
            input
        }
    }

    fun isValidEmail(email: String): Boolean {
        val emailRegex = Regex("^\\S+@\\S+\\.\\S+$")
        return emailRegex.matches(email)
    }
}