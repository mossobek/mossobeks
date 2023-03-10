package com.stirkaparus.driver.common

import androidx.compose.ui.graphics.Color
import com.stirkaparus.driver.common.Constants.FINISHED
import com.stirkaparus.model.Order
import com.stirkaparus.driver.common.Constants.CANCELED
import com.stirkaparus.driver.common.Constants.CREATED
import com.stirkaparus.driver.common.Constants.CREATED_RU
import com.stirkaparus.driver.common.Constants.DELIVERED
import com.stirkaparus.driver.common.Constants.DELIVERED_RU
import com.stirkaparus.driver.common.Constants.FINISHED_RU
import com.stirkaparus.driver.common.Constants.TAKEN
import com.stirkaparus.driver.common.Constants.TAKEN_RU
import com.stirkaparus.driver.common.Constants.WASHED
import com.stirkaparus.driver.common.Constants.WASHED_RU
import com.stirkaparus.driver.ui.theme.*


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