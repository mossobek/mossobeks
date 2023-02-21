package com.stirkaparus.stirkaparus.presentation.profile.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PersonAdd
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
fun ProfileTopBar(navToAddUserScreen: () -> Unit) {

    TopAppBar(
        title = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = "Профиль"
            )
        },
        actions = {
            IconButton(
                onClick = {
                    navToAddUserScreen()
                }) {
                Icon(imageVector = Icons.Outlined.PersonAdd, contentDescription = null)
            }
        }
    )
}

@Composable
fun AddUserDialog() {
    TODO("Not yet implemented")
}
