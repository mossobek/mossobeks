package com.stirkaparus.stirkaparus.presentation.order_details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lens
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.stirkaparus.stirkaparus.common.Constants.CREATED
import com.stirkaparus.stirkaparus.common.Constants.WASHED
import com.stirkaparus.stirkaparus.presentation.components.SmallSpacer
import com.stirkaparus.stirkaparus.presentation.order_details.OrderDetailsViewModel
import com.stirkaparus.stirkaparus.presentation.orders_list_screen.components.Status

const val TAG = "StatusChange"

@Composable
fun StatusChange(
    viewModel: OrderDetailsViewModel = hiltViewModel(),
    id: String,
    onOrderTakeClicked: () -> Unit
) {

    Column(
        modifier = Modifier.padding(16.dp)
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
                status = Status.Created,
                onClick = {
                    viewModel.changeStatus(id = id, status = CREATED)
                })
            SmallSpacer()
            Divider()
            TextCustomStatus(
                status = Status.Taken,
                onClick = {
                    onOrderTakeClicked()
                })
            SmallSpacer()
            Divider()

            TextCustomStatus(
                status = Status.Washed,

                onClick = {
                    viewModel.changeStatus(id = id, status = WASHED)
                })
            SmallSpacer()
            Divider()
            TextCustomStatus(
                status = Status.Delivered,

                onClick = {
                    viewModel.openDeliveredDialog()
                })
            SmallSpacer()
            Divider()
            TextCustomStatus(
                status = Status.Finished,
                //  currentSt,
                onClick = {
                    //onClick(Status.Finished)
                })
            SmallSpacer()
            Divider()
        }
    }
}


@Composable
fun TextCustomStatus(
    status: Status,
    //currentStatus: MutableState<String>,
    onClick: () -> Unit
) {

    Row(modifier = Modifier
        .fillMaxWidth()
        .clickable { onClick() }
        .height(35.dp)
        .background(Color.White),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween) {
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


    }
}