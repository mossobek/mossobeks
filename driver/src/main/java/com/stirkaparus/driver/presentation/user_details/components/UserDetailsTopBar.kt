package com.stirkaparus.driver.presentation.user_details.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Output
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
fun UserDetailsTopBar(signOut: () -> Unit) {
    TopAppBar(
        title = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                text = "Активация профиля"
            )
        }, actions = {
            IconButton(onClick = {
                signOut()
            }) {
                Icon(imageVector = Icons.Outlined.Output, contentDescription = null)
            }
        }
    )
}