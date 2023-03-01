package com.stirkaparus.stirkaparus.common

import androidx.compose.ui.graphics.Color
import com.stirkaparus.model.Order
import com.stirkaparus.stirkaparus.common.Constants.CANCELED
import com.stirkaparus.stirkaparus.common.Constants.CANCELED_TIME
import com.stirkaparus.stirkaparus.common.Constants.CREATED
import com.stirkaparus.stirkaparus.common.Constants.CREATED_RU
import com.stirkaparus.stirkaparus.common.Constants.CREATED_TIME
import com.stirkaparus.stirkaparus.common.Constants.DELIVERED
import com.stirkaparus.stirkaparus.common.Constants.DELIVERED_RU
import com.stirkaparus.stirkaparus.common.Constants.DELIVERED_TIME
import com.stirkaparus.stirkaparus.common.Constants.FINISHED
import com.stirkaparus.stirkaparus.common.Constants.FINISHED_RU
import com.stirkaparus.stirkaparus.common.Constants.FINISHED_TIME
import com.stirkaparus.stirkaparus.common.Constants.TAKEN
import com.stirkaparus.stirkaparus.common.Constants.TAKEN_RU
import com.stirkaparus.stirkaparus.common.Constants.TAKEN_TIME
import com.stirkaparus.stirkaparus.common.Constants.WASHED
import com.stirkaparus.stirkaparus.common.Constants.WASHED_RU
import com.stirkaparus.stirkaparus.common.Constants.WASHED_TIME
import com.stirkaparus.stirkaparus.ui.theme.*


class StatusImp() {
    fun colorStatus(order: Order): Color {
        return when (order.status) {
            CREATED -> BlueStatusColor
            TAKEN -> PurpleStatusColor
            WASHED -> OrangeStatusColor
            FINISHED -> GreenStatusColor
            DELIVERED -> GrayStatusColor
            CANCELED -> RedStatusColor
            else -> {
                BlueStatusColor
            }
        }
    }

    fun textStatus(order: Order): String {
        return when (order.status) {
            CREATED -> CREATED_RU
            TAKEN -> TAKEN_RU
            WASHED -> WASHED_RU
            FINISHED -> FINISHED_RU
            DELIVERED -> DELIVERED_RU
            CANCELED -> CREATED_RU
            else -> {
                "Статус"
            }
        }
    }

    fun textStatusTime(order: Order): String {
        val currentStatusTime = when (order.status) {
            CREATED -> order.created_time
            TAKEN -> order.taken_time
            WASHED -> order.washed_time
            FINISHED -> order.finished_time
            DELIVERED -> order.delivered_time
            CANCELED -> order.canceled_time
            else -> {
                order.created_time
            }
        }

        return if (currentStatusTime != null) formatDate(currentStatusTime) else "--:--"
    }

}