package com.example.jetsurveyme.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

fun formatCurrentDate():String{
    val timeStamp = System.currentTimeMillis()
    val sdf = SimpleDateFormat("EEE, MMM d")
    val date = Date(timeStamp.toLong() )
    return sdf.format(date)
}

/**
 * Returns the start of today in milliseconds
 */
fun getDefaultDateInMillis(): Long {
    val cal = Calendar.getInstance()
    val year = cal.get(Calendar.YEAR)
    val month = cal.get(Calendar.MONTH)
    val date = cal.get(Calendar.DATE)
    cal.clear()
    cal.set(year, month, date)
    return cal.timeInMillis
}

fun formatDate(timestamp:Long?):String{
    val sdf = SimpleDateFormat("EEE, MMM d")
    val date = timestamp?.let { Date(it) }
    return sdf.format(date)
}

fun formatDateFromCalendar(mDayOfWeek:Int,mMonth:Int,mDayOfMonth:Int):String{
    val monthAfterFormat = when(mMonth){
        1 -> "Jan"
        2 -> "Feb"
        3 -> "Mar"
        4 -> "April"
        5 -> "May"
        6 -> "June"
        7 -> "July"
        8 -> "Aug"
        9 -> "Sept"
        10 -> "Oct"
        11 -> "Nov"
        12 -> "Dec"
        else -> {""}
    }

    val dayOfWeekFormatted = when(mDayOfWeek){
        1 -> "Sun"
        2 -> "Mon"
        3 -> "Tue"
        4 -> "Wed"
        5 -> "Thur"
        6 -> "Fri"
        7 -> "Sat"
        else -> {""}
    }

    return dayOfWeekFormatted +", "+monthAfterFormat+" "+mDayOfMonth.toString()
}