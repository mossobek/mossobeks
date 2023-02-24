package com.stirkaparus.stirkaparus.presentation.reports.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stirkaparus.model.Order


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ReportItemCard(order: Order, onClick: () -> Unit) {
    Card(
        shape = MaterialTheme.shapes.small,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 8.dp,
                end = 8.dp,
                top = 4.dp,
                bottom = 4.dp
            ),
        elevation = 3.dp,
        onClick = onClick
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    all = 12.dp
                )
        ) {
            order.phone?.let { phone ->
                Text(
                    text = phone,
                    color = Color.DarkGray,
                    fontSize = 25.sp
                )
            }
        }
    }
}
