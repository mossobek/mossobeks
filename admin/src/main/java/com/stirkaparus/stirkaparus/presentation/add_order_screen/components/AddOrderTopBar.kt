package com.stirkaparus.stirkaparus.presentation.add_order_screen.components

import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import com.stirkaparus.stirkaparus.common.Constants.ADD_ORDER_SCREEN_TOP_BAR_TEXT

@Composable
fun AddOrderTopBar(
) {
  TopAppBar(
      title = {
          Text(text = ADD_ORDER_SCREEN_TOP_BAR_TEXT)
      }
  )
}