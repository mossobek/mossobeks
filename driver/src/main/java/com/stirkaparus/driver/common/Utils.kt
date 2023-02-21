package com.stirkaparus.driver.common

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import com.google.firebase.Timestamp
import java.text.SimpleDateFormat
import java.util.*

class Utils {
    companion object {
        fun print(e: Exception) = Log.e(TAG, e.stackTraceToString())

    }
}
fun showMessage(
    context: Context,
    message: String?
) = Toast.makeText(context, message, Toast.LENGTH_LONG).show()
fun getRandomString(length: Int): String {
    val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
    return (1..length).map { allowedChars.random() }.joinToString("")
}


@SuppressLint("SimpleDateFormat")
fun formatDate(time: Any?): String {
    return if (time != null && time != "") {

        Log.e(ContentValues.TAG, "formatDate: $time")

        val data: Date = (time as Timestamp).toDate()

        SimpleDateFormat("dd. MM. yy '-' HH:mm:ss").format(data)
    } else "--:--"
}

fun getAgoTime(ts: Any?): String {

    return if (ts != null && ts != "") {

        val data = ts as Timestamp

        val time = (data.seconds).minus(ts.seconds)

        getShortDateAgo(time)

    } else "--:--"

}

fun gotoWhatsapp(context: Context, number: String) {


    if (number == "") return

    val t1 = number[0]
    val ext = if (t1.toString() == "8") {
        number.replaceFirst("8", "+7")
    } else {
        number
    }


    val url = "https://api.whatsapp.com/send?phone=$ext"

    val intent = Intent(Intent.ACTION_VIEW)
    intent.data = Uri.parse(url)
    context.startActivity(intent)
}

fun gotoMap(context: Context, address: String) {
    val uri = java.lang.String.format(Locale.ENGLISH, "geo:0,0?q=${address}")
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
    context.startActivity(intent)
}
fun showToast(context: Context, text: String) {
    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()

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