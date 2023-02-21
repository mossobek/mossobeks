package com.stirkaparus.stirkaparus.presentation.user_details.components

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import com.stirkaparus.stirkaparus.presentation.components.SmallSpacer
import com.stirkaparus.stirkaparus.presentation.user_details.UserDetailsViewModel
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