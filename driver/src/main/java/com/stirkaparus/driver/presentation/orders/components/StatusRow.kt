package com.stirkaparus.driver.presentation.orders.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.stirkaparus.driver.common.Constants
import com.stirkaparus.driver.ui.theme.BlueStatusColor
import com.stirkaparus.driver.ui.theme.GreenStatusColor
import com.stirkaparus.driver.ui.theme.PhoneNumberBoxBackground
import com.stirkaparus.driver.ui.theme.Teal200


@Composable
fun StatusRow(status: String) {
    var color = BlueStatusColor
    var text = "Создан"
    if (status == Constants.FINISHED){
        color = GreenStatusColor
        text = "Готов"
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
                    tint = color

                )

                Text(
                    modifier = Modifier.padding(end = 10.dp),
                    text = text
                )
            }
        }
    }
}