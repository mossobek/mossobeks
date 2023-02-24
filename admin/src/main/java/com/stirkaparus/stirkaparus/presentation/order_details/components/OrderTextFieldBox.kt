package com.stirkaparus.stirkaparus.presentation.order_details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.stirkaparus.model.Order
import com.stirkaparus.stirkaparus.common.Constants
import com.stirkaparus.stirkaparus.common.formatDate
import com.stirkaparus.stirkaparus.presentation.orders_list_screen.components.setStatus


@Composable
fun OrderTextFieldBox(
    order: Order, onStatusClick: () -> Unit,
    navToCarpets: () -> Unit,
    onAddCarpetClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
    ) {

        Spacer(modifier = Modifier.height(16.dp))
        CustomTextFieldRow(
            description = Constants.PHONE_RU,
            text = order.phone.toString(),
            icon = Icons.Default.Phone
        )
        Spacer(modifier = Modifier.height(2.dp))
        CustomTextFieldRow(
            description = Constants.ADDRESS_RU,
            text = order.address.toString(),
            icon = Icons.Default.LocationOn
        )
        Spacer(modifier = Modifier.height(2.dp))
        CustomTextFieldRow(
            description = Constants.COMMENT_RU,
            text = order.comment.toString(),
            icon = Icons.Default.Comment
        )
        Spacer(modifier = Modifier.height(2.dp))
        OrderDetailsStatusComponent(status = setStatus(order.status.toString(), order),
            description = Constants.STATUS_RU,
            text = order.status.toString(),
            icon = Icons.Default.CheckCircle,
            onClock = {
                onStatusClick()
            })
        Spacer(modifier = Modifier.height(2.dp))
        CustomTextFieldRow(visibleAddButton = order.status == Constants.TAKEN,
            description = Constants.COUNT_RU,
            text = order.count.toString(),
            icon = Icons.Default.AutoAwesomeMotion,
            secondIcon = if (order.status == Constants.TAKEN) {
                Icons.Default.AddBox
            } else {
                null
            },
            onClick = {
                navToCarpets()
            },
            clickable = true,
            secondIconOnClick = {
                onAddCarpetClick()

            })
        Spacer(modifier = Modifier.height(2.dp))
        CustomTextFieldRow(
            description = Constants.OF_THEM_WASHED,
            text = order.washed_count.toString(),
            icon = Icons.Default.AutoAwesomeMotion,
            clickable = false
        )
        Spacer(modifier = Modifier.height(2.dp))

        CustomTextFieldRow(
            description = Constants.TOTAL_RU,
            text = order.total.toString(),
            icon = Icons.Default.Money
        )
        Spacer(modifier = Modifier.height(20.dp))
        CustomTextFieldRow(
            description = "Создан", text = formatDate(order.created_time), paddingStart = 16.dp
        )
        Spacer(modifier = Modifier.height(2.dp))
        CustomTextFieldRow(
            description = "Забран", text = formatDate(order.taken_time), paddingStart = 16.dp
        )
        Spacer(modifier = Modifier.height(2.dp))
        CustomTextFieldRow(
            description = "Постиран", text = formatDate(order.washed_time), paddingStart = 16.dp
        )
        Spacer(modifier = Modifier.height(2.dp))
        CustomTextFieldRow(
            description = "Доставлен", text = formatDate(order.delivered_time), paddingStart = 16.dp
        )
        Spacer(modifier = Modifier.height(2.dp))
    }
}
