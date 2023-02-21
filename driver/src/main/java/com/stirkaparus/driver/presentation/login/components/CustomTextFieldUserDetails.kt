package com.stirkaparus.driver.presentation.login.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@SuppressLint("SuspiciousIndentation")
@Composable
  fun CustomTextFieldUserDetails(
    text: MutableState<String>,
    description: String,
    digits: Boolean = false,
    inputType: KeyboardType = KeyboardType.Text,
    enable: Boolean = true
) {

    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp)
            .padding(end = 16.dp),
        value = text.value,
        enabled = enable,
        onValueChange = { v ->
            text.value = if (digits) {
                v.filter { it.isDigit() }

            } else {
                v
            }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = inputType, imeAction = ImeAction.Next
        ),
        label = { Text(text = description) },
        colors = TextFieldDefaults.textFieldColors(
            cursorColor = Color.DarkGray,
            disabledLabelColor = Color.DarkGray,
            focusedLabelColor = Color.DarkGray,
            placeholderColor = Color.DarkGray,
            focusedIndicatorColor = Color.DarkGray

        )

    )
}