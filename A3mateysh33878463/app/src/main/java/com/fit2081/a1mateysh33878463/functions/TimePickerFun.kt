package com.fit2081.a1mateysh33878463.functions

import android.app.TimePickerDialog
import android.icu.util.Calendar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.platform.LocalContext

// Time picker function obtained from course materials
// https://edstem.org/au/courses/20813/lessons/77916/slides/527595
@Composable
fun TimePickerFun(mTime: MutableState<String>): TimePickerDialog {
    // Get the current context
    val mContext = LocalContext.current
    // create instance of calendar to access time info
    val mCalendar = Calendar.getInstance()

    // Get the current hour and minute
    val mHour = mCalendar.get(Calendar.HOUR_OF_DAY)
    val mMinute = mCalendar.get(Calendar.MINUTE)

    // Set the calendar's time to the current time
    mCalendar.time = Calendar.getInstance().time
    // Return a TimePickerDialog
    return TimePickerDialog(
        // Context
        // Listener to be invoked when the time is set, sets the selected time into the MutableState
        // Initial hour and minute
        // Uses 12 hour format (false)

        mContext,
        { _, mHour: Int, mMinute: Int -> mTime.value = "$mHour: $mMinute" },
        mHour,
        mMinute,
        false
    )
}