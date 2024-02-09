package com.synrgy.presentation.helper

import android.Manifest
import android.app.Activity
import android.app.DatePickerDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.widget.DatePicker
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.res.ResourcesCompat
import com.synrgy.domain.local.AddonData
import com.synrgy.domain.local.DocumentData
import com.synrgy.domain.local.FlightSearch
import com.synrgy.domain.local.PassengerData
import com.synrgy.presentation.R
import com.synrgy.presentation.constant.Constant
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle
import java.text.NumberFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

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
        doAfterSelected: () -> Unit = {},
        maxDate: Long? = null
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
        if (maxDate != null) datePickerDialog.datePicker.maxDate = maxDate
        datePickerDialog.show()
    }

    fun convertDateFormat(inputDate: String): String {
        val inputDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        inputDateFormat.timeZone = TimeZone.getTimeZone("UTC")

        val date = inputDateFormat.parse(inputDate) ?: Date()
        val outputDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        outputDateFormat.timeZone = TimeZone.getTimeZone("UTC")

        return outputDateFormat.format(date)
    }

    fun convertTimestampToDate(timestamp: Long): String {
        val date = Date(timestamp)
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return sdf.format(date)
    }

    fun isValidDateFormat(dateString: String): Boolean {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        dateFormat.isLenient = false

        return try {
            dateFormat.parse(dateString)
            true
        } catch (e: ParseException) {
            false
        }
    }

    fun formatPrice(number: Long): String {
        return String.format(Locale.getDefault(), "%,d", number)
    }

    fun formatPrice(amountString: String): String {
        val amount = amountString.toDoubleOrNull() ?: return "Invalid amount"
        val format = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
        return format.format(amount).replace("Rp", "IDR ").replace(",00", "").replace(".", ",")
    }

    fun copyToClipboard(
        context: Context,
        label: String,
        text: String,
    ){
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText(label, text)
        clipboard.setPrimaryClip(clip)
    }

    fun isValidFlightSearch(data: FlightSearch): Boolean {
        val isValidReturn = if (data.tripType == Constant.TripType.ROUNDTRIP.value) {
            data.retDate != null
        } else true

        return !data.id.isNullOrEmpty() &&
                !data.origin.isNullOrEmpty() &&
                !data.destination.isNullOrEmpty() &&
                !data.oCity.isNullOrEmpty() &&
                !data.dCity.isNullOrEmpty() &&
                !data.depDate.isNullOrEmpty() &&
                isValidReturn &&
                !data.tripType.isNullOrEmpty() &&
                !data.ticketClass.isNullOrEmpty() &&
                data.adultSeat != null &&
                data.childSeat != null &&
                data.totalSeat != null &&
                data.infantSeat != null
    }

    fun isValidDestination(data: FlightSearch): Boolean {
        return data.origin != data.destination &&
                data.oCity != data.dCity
    }

    fun isValidBaggage(data: AddonData): Boolean {
        return data.userName.isNotEmpty() &&
                data.userName.isNotBlank() &&
                data.category.isNotEmpty() &&
                data.category.isNotBlank() &&
                data.weight != null &&
                data.price != 0L
    }

    fun formatDateDay(dateString: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val outputFormat = SimpleDateFormat("EEE, d MMM yyyy", Locale.US)
        val date: Date = inputFormat.parse(dateString) ?: return ""
        return outputFormat.format(date)
    }

    fun formatStringDate(dateString: String): Date? {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dateFormat.parse(dateString)
    }

    fun formatScheduledTime(dateString: String): String? {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val date = dateFormat.parse(dateString)

        val outputFormat = SimpleDateFormat("dd MMM yyyy", Locale.US)
        return if (date != null) outputFormat.format(date) else null
    }

    fun formatDmyTime(dateString: String): String? {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
        val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        val date = dateFormat.parse(dateString)
        return if (date != null) outputFormat.format(date) else null
    }

    fun formatPhoneNumber(input: String): String {
        val cleanNumber = input.replace(Regex("[^\\d]"), "")
        return if (cleanNumber.isNotEmpty()) {
            when (cleanNumber.length) {
                in 5..7 -> "+${cleanNumber.substring(0, 2)} ${cleanNumber.substring(2)}"
                in 8..11 -> "+${cleanNumber.substring(0, 2)} ${cleanNumber.substring(2, 6)}-${cleanNumber.substring(6)}"
                else -> "+${cleanNumber.substring(0, 2)} ${cleanNumber.substring(2, 6)}-${cleanNumber.substring(6, 10)}-${cleanNumber.substring(10)}"
            }
        } else {
            input
        }
    }

    fun containsSpecialCharacter(sentence: String): Boolean {
        val specialCharacters = "!\"#\$%&'()*+,-./:;<=>?@[\\]^_`{|}~"
        for (ch in sentence) {
            if (specialCharacters.contains(ch)) {
                return true
            }
        }
        return false
    }

    fun containsAlphanumeric(sentence: String): Boolean {
        var containsLetter = false
        var containsNumber = false

        for (ch in sentence) {
            if (ch.isLetter()) {
                containsLetter = true
            }

            if (ch.isDigit()) {
                containsNumber = true
            }

            if (containsLetter && containsNumber) {
                break
            }
        }

        return containsLetter && containsNumber
    }

    fun containsUppercaseLetter(sentence: String): Boolean {
        for (ch in sentence) {
            if (ch.isUpperCase()) {
                return true
            }
        }
        return false
    }

    fun checkPasswordLength(password: String): Boolean {
        return password.length >= 8
    }

    fun isValidPassenger(data: PassengerData): Boolean {
        return data.id.isNotEmpty() && data.id.isNotBlank() &&
                data.nik.isNotEmpty() && data.nik.isNotBlank() &&
                data.name.isNotEmpty() && data.name.isNotBlank() &&
                data.dob.isNotEmpty() && data.dob.isNotBlank() &&
                data.category.isNotEmpty() && data.category.isNotBlank() &&
                data.surname.isNotEmpty() && data.surname.isNotBlank()
    }

    fun isValidDocument(data: List<DocumentData>): Boolean {
        return data.all {
            it.id.isNotEmpty() && it.id.isNotBlank() &&
                    it.type.isNotEmpty() && it.type.isNotBlank() &&
                    it.nationality.isNotEmpty() && it.nationality.isNotBlank() &&
                    it.docNum.isNotEmpty() && it.docNum.isNotBlank() &&
                    it.expiry.isNotEmpty() && it.expiry.isNotBlank() &&
                    it.file.isNotEmpty() && it.file.isNotBlank()
        }
    }

    fun formatDateTime(inputDateTime: String): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val date: Date = sdf.parse(inputDateTime) ?: Date()

        val outputFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
        return outputFormat.format(date)
    }

    fun calculateTimeDifference(startTime: String, endTime: String): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        sdf.timeZone = TimeZone.getTimeZone("UTC")

        val startDate: Date = sdf.parse(startTime) ?: Date()
        val endDate: Date = sdf.parse(endTime) ?: Date()

        val timeDifferenceInMillis = endDate.time - startDate.time

        val hours = (timeDifferenceInMillis / (1000 * 60 * 60))
        val minutes = (timeDifferenceInMillis / (1000 * 60)) % 60

        return "${hours}h ${minutes}m"
    }
}