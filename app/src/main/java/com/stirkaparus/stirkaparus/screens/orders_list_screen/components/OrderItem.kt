package com.stirkaparus.stirkaparus.screens.orders_list_screen.components

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.Timestamp
import com.stirkaparus.stirkaparus.model.Order
import com.stirkaparus.stirkaparus.screens.orders_list_screen.OrdersViewModel
import com.stirkaparus.stirkaparus.ui.theme.*
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun OrderItem(
    order: Order, modifier: Modifier = Modifier, onItemMenuClick: () -> Unit
) {

    val viewModel: OrdersViewModel = OrdersViewModel()


    Box(
        modifier = modifier
            .border(width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(size = 12.dp))

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
                    modifier = Modifier.weight(5f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        modifier = Modifier.size(20.dp),
                        onClick = onItemMenuClick,


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
                        modifier = Modifier.size(20.dp),
                        onClick = onItemMenuClick,
                    ) {
                        Icon(

                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete order",
                            tint = MaterialTheme.colors.onSurface
                        )
                    }
                }

            }
            Spacer(modifier = Modifier.height(6.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(20.dp),
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
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier

                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,


                ) {
                Row(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Icon(
                        modifier = Modifier.size(20.dp),
                        imageVector = Icons.Default.Timer,
                        contentDescription = "Time Ago Icon",
                        tint = Color.LightGray,

                        )
                    Text(text = viewModel.getAgoTime(order.created_time as Timestamp))

                    Log.e(TAG, "OrderItem: ${order.created_time}")
                }
                Row(
                    modifier = Modifier
                        .weight(1f),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier
                            .height(15.dp)
                            .padding(end = 8.dp),
                        text = statusToTime(order),


                        )
                }
            }
            Spacer(modifier = Modifier.height(6.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {

                Row() {
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
                                tint = statusColor(order)

                            )

                            Text(
                                modifier = Modifier.padding(end = 10.dp),
                                text = statusText(order)
                            )
                        }
                    }
                }


            }


        }


    }
}


private fun statusText(order: Order): String {
    return when (order.status.toString()) {
        "created" -> {
            "Создан"
        }
        "taken" -> {
            "Забран"
        }

        else -> {
            "Статус"
        }
    }

}

private fun statusColor(order: Order): Color {
    return when (order.status.toString()) {
        "created" -> {
            BlueStatusColor
        }
        "taken" -> {
            PurpleStatusColor
        }

        else -> {
            Teal200
        }
    }

}

private fun statusToTime(order: Order): String {
    return when (order.status) {
        "created" -> {
            formatDate((order.created_time as Timestamp?)!!.toDate())
        }
        "taken" -> {
            formatDate((order.taken_time as Timestamp?)!!.toDate())
        }

        else -> {
            "-:-"
        }
    }
}


@SuppressLint("SimpleDateFormat")
fun formatDate(date: Date): String {
    return SimpleDateFormat("dd. MM. yy").format(date)
}

@Preview
@Composable
fun Row() {
    val order: Order = Order()
    order.phone = "8938436453"
    order.address = "Adress"
    order.comment = "Comment"
    OrderItem(order = order) {

    }
}