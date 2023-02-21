package com.stirkaparus.stirkaparus.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.unit.dp
import com.stirkaparus.stirkaparus.ui.theme.LightGray

@Composable
fun ProgressDialog() {

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(LightGray.copy(alpha = 0.2f) )
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .border(1.dp, Color.Black, shape = RoundedCornerShape(8.dp))
                .size(100.dp)
                .background(White, shape = RoundedCornerShape(8.dp))
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
                Text(text = "Подождите ...", Modifier.padding(0.dp, 8.dp, 0.dp, 0.dp))

            }

        }
    }
}