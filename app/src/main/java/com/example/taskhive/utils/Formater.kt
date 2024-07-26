package com.example.taskhive.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Date?.getReadableDate(): String{
    if (this == null)return ""
    val simpleDateFormat = SimpleDateFormat("EEE, d MMM yyyy", Locale.ENGLISH)
    return try {
        simpleDateFormat.format(this)
    }catch (e:Exception){
        e.printStackTrace()
        ""
    }

}

fun Date?.getReadableTime(): String{
    if (this == null)return ""
    val simpleDateFormat = SimpleDateFormat("hh:mm a", Locale.ENGLISH)
    return try {
        simpleDateFormat.format(this)
    }catch (e:Exception){
        e.printStackTrace()
        ""
    }
}

fun String?.toDate(): Date?{
    if (this == null)return null
    val simpleDateFormat = SimpleDateFormat("hh:mm a", Locale.ENGLISH)
    return try {
        simpleDateFormat.parse(this)
    }catch (e:Exception){
        e.printStackTrace()
        null
    }
}

fun formatLogTime(minutes: Long): String {
    val hours = minutes / 60
    val remainingMinutes = minutes % 60
    return if (hours > 0) {
        String.format("%dh %02dm", hours, remainingMinutes)
    } else {
        String.format("%dm", remainingMinutes)
    }
}