package com.stirkaparus.driver.presentation.user_details.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.stirkaparus.driver.presentation.components.SmallSpacer

@SuppressLint("UnrememberedMutableState")
@Composable
fun UserDetailsContent(
    padding: PaddingValues,
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "ПРОФИЛЬ НЕ АКТИВИРОВАН")
        Text(text = "обратитесь к администратору вашей компании")
        SmallSpacer()
        SmallSpacer()
        SmallSpacer()
        Button(onClick = { /*TODO*/ }) {
            Text(text = "ПРОВЕРИТЬ")

        }
    }


}