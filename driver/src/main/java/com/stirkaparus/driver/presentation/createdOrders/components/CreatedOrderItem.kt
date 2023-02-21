package com.stirkaparus.driver.presentation.createdOrders.components

import android.annotation.SuppressLint
import android.content.ContentValues
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.Timestamp
import com.stirkaparus.driver.ui.theme.BlueStatusColor
import com.stirkaparus.driver.ui.theme.PhoneColor
import com.stirkaparus.driver.ui.theme.PhoneNumberBoxBackground
import com.stirkaparus.driver.ui.theme.Teal200
import com.stirkaparus.model.Order
import java.text.SimpleDateFormat
import java.util.*


@Composable
fun CreatedOrderItem(
    order: Order,
    modifier: Modifier = Modifier,
    onPhoneClick: () -> Unit,

    ) {
    Box(
        modifier = modifier
            .border(
                width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(size = 12.dp)
            )
            .clip(shape = RoundedCornerShape(size = 12.dp))
            .background(Color.White)

    ) {
        if (order.created_time == null) {
            Log.e(ContentValues.TAG, "OrderItem: ${order.id}")
        }

        Column(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize()
                .padding(16.dp)

        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {

                Row(
                    modifier = Modifier.weight(5f), verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(modifier = Modifier
                        .size(20.dp)
                        .padding(0.dp), onClick = {}


                    ) {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = "Address icon",
                            tint = Color.LightGray,


                            )
                    }
                    Text(
                        modifier = Modifier.padding(start = 4.dp),
                        text = order.address.toString(),
                        maxLines = 2,
                        fontSize = 16.sp
                    )

                }



            }
            Spacer(modifier = Modifier.height(2.dp))
            Row(
                modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier
                        .size(20.dp)
                        .padding(0.dp),
                    imageVector = Icons.Default.Comment,
                    contentDescription = "Comment Icon",
                    tint = Color.LightGray,

                    )
                Text(
                    modifier = Modifier.padding(start = 4.dp),
                    text = if (order.comment.toString() == "") {
                        "комментарий"
                    } else order.comment.toString(),
                    color = if (order.comment.toString() == "") {
                        Color.LightGray
                    } else {
                        Color.Black
                    }

                )
            }
            Spacer(modifier = Modifier.height(2.dp))
            Row(
                modifier = Modifier

                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,


                ) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically,

                    ) {
                    Icon(
                        modifier = Modifier
                            .size(20.dp)
                            .padding(0.dp),
                        imageVector = Icons.Default.Timer,
                        contentDescription = "Time Ago Icon",
                        tint = Color.LightGray,

                        )
                    Text(
                        modifier = Modifier.padding(start = 4.dp),
                        text = getAgoTime(order.created_time as Timestamp),

                        color = Color.DarkGray

                    )

                    Log.e(ContentValues.TAG, "OrderItem: ${order.created_time}")
                }
                Row(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier
                            .height(15.dp)
                            .padding(end = 8.dp),

                        text = formatDate(order.created_time),
                        color = Color.DarkGray


                    )
                }
            }
            Spacer(modifier = Modifier.height(2.dp))
            Row(
                modifier = Modifier

                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,


                ) {
                Row(
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        modifier = Modifier
                            .size(20.dp)
                            .padding(0.dp),
                        imageVector = Icons.Default.Money,
                        contentDescription = "cash icon",
                        tint = Color.LightGray,

                        )
                    Text(
                        modifier = Modifier.padding(start = 4.dp),
                        text = "Сумма заказа",
                        color = Color.DarkGray
                    )

                }
                Row(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier
                            .height(20.dp)
                            .padding(end = 8.dp),
                        text = order.total.toString(),

                        )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        modifier = Modifier
                            .height(20.dp)
                            .padding(end = 8.dp),
                        text = "Руб.",


                        )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,

                ) {

                Row() {
                    Box(
                        modifier = Modifier
                            .clickable(onClick = {
                                onPhoneClick()
                            })
                            .background(PhoneNumberBoxBackground)
                            .border(
                                1.dp, Teal200, shape = RoundedCornerShape(size = 5.dp)
                            )
                            .clip(shape = RoundedCornerShape(size = 5.dp))
                            .height(35.dp),
                        Alignment.CenterStart,

                        ) {
                        Row(
                            modifier = Modifier
                                .padding(start = 4.dp)
                                .padding(end = 4.dp),
                            verticalAlignment = Alignment.CenterVertically,

                            ) {

                            Icon(
                                modifier = Modifier
                                    .padding(start = 6.dp)
                                    .padding(end = 6.dp)
                                    .size(15.dp),
                                imageVector = Icons.Default.Phone,
                                contentDescription = "Phone",
                                tint = PhoneColor

                            )
                            Text(

                                modifier = Modifier.padding(end = 6.dp),
                                text = order.phone.toString()

                            )
                        }
                    }
                }


                Row(
                    modifier = Modifier
                        .padding(start = 4.dp)
                        .padding(end = 4.dp)
                ) {

                    Box(
                        modifier = Modifier.clickable { return@clickable }
                            .background(PhoneNumberBoxBackground)
                            .border(
                                1.dp, Teal200, shape = RoundedCornerShape(size = 5.dp)
                            )
                            .clip(shape = RoundedCornerShape(size = 5.dp))
                            .height(35.dp),
                        Alignment.CenterStart
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                        ) {

                            Icon(
                                modifier = Modifier.clickable { return@clickable }
                                    .padding(start = 10.dp)
                                    .padding(end = 6.dp)
                                    .size(15.dp),
                                imageVector = Icons.Default.Circle,
                                contentDescription = "status circle",
                                tint = BlueStatusColor

                            )

                            Text(
                                modifier = Modifier.padding(end = 10.dp),
                                text = "Создан"
                            )
                        }
                    }
                }


            }


        }


    }
}

fun getAgoTime(ts: Timestamp?): String {
    val time = (Timestamp.now().seconds).minus(ts!!.seconds)

    return getShortDateAgo(time)

}

private fun getShortDateAgo(ts: Long?): String {
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

@SuppressLint("SimpleDateFormat")
fun formatDate(time: Any?): String {
    return if (time != null) {

        val data: Date = (time as Timestamp).toDate()

        SimpleDateFormat("dd. MM. yy '-' HH:mm:ss").format(data)
    } else "--:--"
}