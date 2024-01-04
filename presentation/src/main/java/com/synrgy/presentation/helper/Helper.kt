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
}