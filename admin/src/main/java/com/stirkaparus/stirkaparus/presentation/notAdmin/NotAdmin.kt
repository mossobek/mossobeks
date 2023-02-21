package com.stirkaparus.stirkaparus.presentation.notAdmin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth

@Composable
fun NotAdminScreen(

    navController: NavController
) {
    Column(Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "У вас не достаточно прав")
        Text(text = "Зайдите под учетной записью админа")
        Button(onClick = {
            FirebaseAuth.getInstance().signOut()

        }) {
            Text(text = "Выйти")
        }
    }
}