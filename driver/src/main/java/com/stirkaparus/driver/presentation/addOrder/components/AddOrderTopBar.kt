package com.stirkaparus.driver.presentation.addOrder.components

import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import com.stirkaparus.driver.common.Constants.ADD_ORDER_SCREEN_TOP_BAR_TEXT

@Composable
fun AddOrderTopBar(
) {
  TopAppBar(
      title = {
          Text(text = ADD_ORDER_SCREEN_TOP_BAR_TEXT)
      }
  )
}