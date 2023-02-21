package com.stirkaparus.driver.presentation.login.components

import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import com.stirkaparus.driver.common.Constants.LOGIN_SCREEN_TOP_BAR_TEXT

@Composable
fun LoginTopBar(
 ) {
    TopAppBar (
        title = {
            Text(
                text = LOGIN_SCREEN_TOP_BAR_TEXT
            )
        },

    )
}