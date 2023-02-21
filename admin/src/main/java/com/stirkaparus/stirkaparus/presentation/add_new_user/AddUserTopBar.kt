package com.stirkaparus.stirkaparus.presentation.add_new_user

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
fun AddUserTopBar() {
    TopAppBar(
        title = {
            Text(modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center, text = "Добавить сотрудника")
        }
    )
}