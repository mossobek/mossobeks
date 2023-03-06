package com.stirkaparus.stirkaparus.presentation.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import com.stirkaparus.stirkaparus.common.Constants.EMAIL_HINT

@Composable
fun EmailField(
    email: TextFieldValue, onEmailValueChange: (newValue: TextFieldValue) -> Unit
) {
    val focusRequester = FocusRequester()

    TextField(
        value = email,
        onValueChange = { newValue ->
            onEmailValueChange(newValue)
        },
        placeholder = {
            Text(text = EMAIL_HINT)
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email
        ),
     )


}