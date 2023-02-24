package com.stirkaparus.stirkaparus.presentation.order_details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Lens
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stirkaparus.stirkaparus.presentation.components.SmallSpacer
import com.stirkaparus.stirkaparus.presentation.orders_list_screen.components.Status


@Composable
fun StatusChange(
    currentSt: String? = "created",
    onClick: (Status) -> Unit,
) {

    Column(
        modifier = Modifier
            .padding(16.dp)
    ) {

        Column(
            modifier = Modifier
                .background(Color.White)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = "Выберите статус",
                style = MaterialTheme.typography.subtitle1,
            )
            SmallSpacer()
            Divider()
            TextCustomStatus(
                status = Status.Created, currentSt, onClick = { onClick(Status.Created) }
            )
            SmallSpacer()
            Divider()
            TextCustomStatus(
                status = Status.Taken, currentSt, onClick = { onClick(Status.Taken) }
            )
            SmallSpacer()
            Divider()

            TextCustomStatus(
                status = Status.Washed, currentSt, onClick = { onClick(Status.Washed) }
            )
            SmallSpacer()
            Divider()
            TextCustomStatus(
                status = Status.Delivered, currentSt, onClick = { onClick(Status.Delivered) }
            )
            SmallSpacer()
            Divider()
            TextCustomStatus(
                status = Status.Finished, currentSt, onClick = { onClick(Status.Finished) }
            )
            SmallSpacer()
            Divider()
        }
    }
}


@Composable
fun TextCustomStatus(
    status: Status,
    currentStatus: String?,
    onClick: () -> Unit
) {
    var check by remember { mutableStateOf(false) }
    if (status.status == currentStatus) check = true
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .height(35.dp)
            .background(Color.White),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(26.dp),
                imageVector = Icons.Default.Lens,
                tint = status.color!!,
                contentDescription = status.trans
            )
            Text(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .padding(end = 16.dp),
                text = status.trans,
                color = Color.Black,
                fontSize = 18.sp
            )
        }

        Row(
            horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.CenterVertically
        ) {

            if (check) {

                Icon(
                    modifier = Modifier.size(26.dp),
                    imageVector = Icons.Default.Check,
                    tint = Color.Black,
                    contentDescription = "desc"
                )

            }
        }

    }
}