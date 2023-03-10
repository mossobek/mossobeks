package com.stirkaparus.stirkaparus.presentation.add_order_screen.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import com.stirkaparus.stirkaparus.common.Constants.ADD_ORDER_SCREEN_TOP_BAR_TEXT

@Composable
fun AddOrderTopBar(
) {
    TopAppBar(
        backgroundColor = Color.White,
        contentColor = Color.Black,
        title = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = Color.Black, text = "Новый заказ"

            )
        }
    )
}