package com.stirkaparus.stirkaparus.presentation.order_details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lens
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TextCustomStatus(
    statusColor: Color,
    statusText: String,
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
                tint = statusColor,
                contentDescription = null
            )
            Text(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .padding(end = 16.dp),
                text = statusText,
                color = Color.Black,
                fontSize = 18.sp
            )
        }


    }
}