package com.stirkaparus.stirkaparus.screens.orders_list_screen.components

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
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
import com.stirkaparus.stirkaparus.common.Constants
import com.stirkaparus.stirkaparus.model.Order
import com.stirkaparus.stirkaparus.screens.orders_list_screen.OrdersViewModel
import com.stirkaparus.stirkaparus.ui.theme.*
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun OrderItem(
    order: Order,
    modifier: Modifier = Modifier,
    onDeleteItemMenuClick: () -> Unit,
    onPhoneClick: () -> Unit,

    ) {
    val viewModel: OrdersViewModel = OrdersViewModel()
    val status = setStatus(order.status.toString(), order)
    Box(
        modifier = modifier
            .border(
                width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(size = 12.dp)
            )
            .clip(shape = RoundedCornerShape(size = 12.dp))
            .background(Color.White)

    ) {


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

                Row(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    IconButton(
                        modifier = Modifier
                            .size(20.dp)
                            .padding(0.dp),
                        onClick = onDeleteItemMenuClick,
                    ) {
                        Icon(

                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete order",
                            tint = MaterialTheme.colors.onSurface
                        )
                    }
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
                        text = viewModel.getAgoTime(order.created_time as Timestamp),
                        color = Color.DarkGray

                    )

                    Log.e(TAG, "OrderItem: ${order.created_time}")
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

                        text = formatDate(status.timestamp),
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
                        modifier = Modifier
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
                                modifier = Modifier
                                    .padding(start = 10.dp)
                                    .padding(end = 6.dp)
                                    .size(15.dp),
                                imageVector = Icons.Default.Circle,
                                contentDescription = "status circle",
                                tint = status.color!!

                            )

                            Text(
                                modifier = Modifier.padding(end = 10.dp),
                                text = status.text.toString()
                            )
                        }
                    }
                }


            }


        }


    }
}

fun setStatus(status: String, order: Order): StatusData {
    val statusData = StatusData()
    return when (status) {
        Status.Created.status -> {
            statusData.also {
                statusData.text = Constants.CREATED_RU
                statusData.color = BlueStatusColor
                statusData.timestamp = order.created_time
            }
        }
        Status.Taken.status -> {
            statusData.also {
                statusData.text = Constants.TAKEN_RU
                statusData.color = PurpleStatusColor
                statusData.timestamp = order.taken_time
            }
        }
        Status.Washed.status -> {
            statusData.also {
                statusData.text = Constants.WASHED_RU
                statusData.color = OrangeStatusColor
                statusData.timestamp = order.washed_time
            }
        }
        Status.Finished.status -> {
            statusData.also {
                statusData.text = Constants.FINISHED_RU
                statusData.color = GreenStatusColor
                statusData.timestamp = order.finished_time
            }
        }
        Status.Delivered.status -> {
            statusData.also {
                statusData.text = Constants.DELIVERED_RU
                statusData.color = GrayStatusColor
                statusData.timestamp = order.delivered_time
            }
        }
        Status.Canceled.status -> {
            statusData.also {
                statusData.text = Constants.CANCELED_RU
                statusData.color = RedStatusColor
                statusData.timestamp = order.delete_time
            }
        }
        Status.Deleted.status -> {
            statusData.also {
                statusData.text = Constants.DELETED_RU
                statusData.color = RedStatusColor
                statusData.timestamp = order.delete_time
            }
        }
        else -> {
            StatusData()
        }
    }

}

data class StatusData(
    var color: Color? = Teal200,
    var text: String? = "",
    var timestamp: Any? = "",

    )

sealed class Status(val status: String, val trans: String, val color: Color?) {
    object Created :
        Status(status = Constants.CREATED, trans = Constants.CREATED_RU, color = BlueStatusColor)

    object Taken :
        Status(status = Constants.TAKEN, trans = Constants.TAKEN_RU, color = PurpleStatusColor)

    object Washed :
        Status(status = Constants.WASHED, trans = Constants.WASHED_RU, color = OrangeStatusColor)

    object Finished :
        Status(status = Constants.FINISHED, trans = Constants.FINISHED_RU, color = GreenStatusColor)

    object Delivered : Status(
        status = Constants.DELIVERED,
        trans = Constants.DELIVERED_RU,
        color = GrayStatusColor
    )

    object Deleted :
        Status(status = Constants.DELETED, trans = Constants.DELETED_RU, color = RedStatusColor)

    object Canceled :
        Status(status = Constants.CANCELED, trans = Constants.CANCELED_RU, color = RedStatusColor)
}


@SuppressLint("SimpleDateFormat")
fun formatDate(time: Any?): String {
    return if (time != null) {

        val data: Date = (time as Timestamp).toDate()

        SimpleDateFormat("dd. MM. yy '-' HH:mm:ss").format(data)
    } else "--:--"
}

