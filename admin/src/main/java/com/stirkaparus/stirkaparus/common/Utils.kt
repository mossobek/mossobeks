package com.stirkaparus.stirkaparus.common

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.firebase.Timestamp
import com.stirkaparus.stirkaparus.common.Constants.TAG
import java.text.SimpleDateFormat
import java.util.*

class Utils() {
    companion object {
        fun print(e: Exception) = Log.e(ContentValues.TAG, e.stackTraceToString())
        fun showMessage(
            context: Context,
            message: String?
        ) = Toast.makeText(context, message, Toast.LENGTH_LONG).show()
    }
    fun getRandomString(length: Int): String {
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }
}

@SuppressLint("SimpleDateFormat")
fun formatDate(time: Any?): String {
    return if (time != null && time != "") {

        Log.e(ContentValues.TAG, "formatDate: $time")

        val data: Date = (time as Timestamp).toDate()

        SimpleDateFormat("dd. MMM ' ' HH:mm").format(data)
    } else "--:--"
}

fun getAgoTime(ts: Any?): String {
    return if (ts != null && ts != "") {
        val data = ts as Timestamp
        Log.e(TAG, "getAgoTime: $data", )
        val time = (Timestamp.now().seconds).minus(ts.seconds)
        getShortDateAgo(time)
    } else "--:--"

}

fun getShortDateAgo(ts: Long?): String {
    if (ts == null) return ""

    return when {

        ts < 60 -> {
            (ts / 60).toString() + " с."

        }
        ts in 61..3599 -> {
            (ts / 60).toString() + " м."
        }
        ts in 3600..86400 -> {
            (ts / 3600).toString() + " ч."
        }
        ts > 86401 -> {
            (ts / 86400).toString() + " д."
        }

        else -> ""
    }


}