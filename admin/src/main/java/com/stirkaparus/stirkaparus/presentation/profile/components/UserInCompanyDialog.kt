package com.stirkaparus.stirkaparus.presentation.profile.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.stirkaparus.model.User
import com.stirkaparus.stirkaparus.presentation.profile.ProfileViewModel

@Composable
fun UserInCompanyDialog(
    user: User,
    isUserDialogClicked: MutableState<Boolean>,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    AlertDialog(
        onDismissRequest = {
            isUserDialogClicked.value = false
        },
        title = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(text = "Удлить рботника?")
                Text(text = user.name.toString(), fontSize = 18.sp)
            }
        },
        buttons = {
            Row(
                modifier = Modifier
                    .padding(all = 8.dp)
                    .fillMaxWidth(),


                ) {
                Button(
                    modifier = Modifier
                        .weight(1f)
                        .padding(16.dp),
                    onClick = {
                        viewModel.deleteUser(user.id.toString())
                        isUserDialogClicked.value = false
                    }) {
                    Text("Да")
                }
                Button(
                    modifier = Modifier
                        .weight(1f)
                        .padding(16.dp),
                    onClick = { isUserDialogClicked.value = false }) {
                    Text("Нет")
                }
            }
        }

    )
}