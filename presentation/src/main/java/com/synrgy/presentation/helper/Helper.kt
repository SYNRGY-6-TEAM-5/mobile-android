package com.synrgy.presentation.helper

import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.widget.DatePicker
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.res.ResourcesCompat
import com.synrgy.presentation.R
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle
import java.util.Calendar

object Helper {
    private const val REQUEST_CODE_PERMISSION = 0

    private val permissions = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private val permissionsAndroid13 = arrayOf(
        Manifest.permission.CAMERA,
        Manifest.permission.READ_MEDIA_IMAGES,
    )

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

    fun requestPermissions(
        activity: Activity,
        doIfGranted: () -> Unit = {},
    ) {
        val deniedPermissions = mutableListOf<String>()
        val permissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissionsAndroid13
        } else {
            permissions
        }

        permissions.forEach { permission ->
            if (!isGranted(activity, permission)) {
                deniedPermissions.add(permission)
            }
        }
        if (deniedPermissions.isNotEmpty()) {
            ActivityCompat.requestPermissions(activity, deniedPermissions.toTypedArray(), REQUEST_CODE_PERMISSION)
        } else {
            doIfGranted()
        }
    }

    private fun isGranted(activity: Activity, permission: String): Boolean {
        return ActivityCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED
    }

    fun showDatePicker(
        context: Context,
        selectedDate: Calendar,
        doAfterSelected: () -> Unit = {}
    ) {
        val datePickerDialog = DatePickerDialog(
            context,
            { _: DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                selectedDate.set(Calendar.YEAR, year)
                selectedDate.set(Calendar.MONTH, monthOfYear)
                selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                doAfterSelected()
            },
            selectedDate.get(Calendar.YEAR),
            selectedDate.get(Calendar.MONTH),
            selectedDate.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }
}