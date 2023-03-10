package com.stirkaparus.stirkaparus.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import com.stirkaparus.stirkaparus.ui.theme.LightGray

@Composable
fun ProgressDialog() {

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .clickable(enabled = false) { }
            .fillMaxSize()
            .background(LightGray.copy(alpha = 0.2f))
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .border(1.dp, Color.White, shape = RoundedCornerShape(8.dp))
                .size(100.dp)
                .background(Color.White, shape = RoundedCornerShape(8.dp))
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(modifier =
                Modifier
                    .clickable {
                        return@clickable
                    }
                )
                Text(text = "Подождите ...", Modifier.padding(0.dp, 8.dp, 0.dp, 0.dp))

            }

        }
    }
}
