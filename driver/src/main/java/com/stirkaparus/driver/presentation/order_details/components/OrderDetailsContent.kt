package com.stirkaparus.driver.presentation.order_details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stirkaparus.driver.ui.theme.BackgroundColor
import com.stirkaparus.model.Order


@Composable
fun OrderDetailsContent(order: Order?) {
    Column(Modifier.fillMaxSize()) {
        Divider()
        Column(
            Modifier
                .fillMaxSize()
                .background(BackgroundColor)
        ) {
            Box(
                Modifier
                    .padding(8.dp)
                    .clip(shape = RoundedCornerShape(5.dp))
                    .background(Color.White)
                    .fillMaxWidth()
            ) {
                Column() {

                    TextRow(desc = "Телефон", text = order?.phone.toString())
                    Divider()
                    TextRow(desc = "Адресс", text = order?.address.toString())
                    Divider()
                    TextRow(desc = "Комментарий", text = order?.comment.toString())
                    Divider()
                    TextRow(desc = "Количество", text = order?.count.toString())


                }
            }
        }
    }
}


@Preview
@Composable
fun OrderDetailsContentP() {

    val order = Order()
    order.phone = "89285222253"
    order.address = "Хизроева 58"
    order.comment = "Какой то коммент"
    order.count  = 3
    OrderDetailsContent(order = order)
}

@Composable
private fun TextRow(desc: String, text: String) {

    Row(
        Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(Modifier.fillMaxWidth()) {

            Text(text = desc, color = Color.DarkGray, fontSize = 14.sp)

            Text(text = text, color = Color.Black, fontSize = 20.sp)

        }
    }
}