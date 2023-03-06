package com.stirkaparus.stirkaparus.presentation.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun SmallSpacer(height: Dp = 8.dp) {
    Spacer(
        modifier = Modifier.height(height)
    )
}