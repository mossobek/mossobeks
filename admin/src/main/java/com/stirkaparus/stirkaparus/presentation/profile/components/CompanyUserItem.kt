package com.stirkaparus.stirkaparus.presentation.profile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.stirkaparus.model.User
import com.stirkaparus.stirkaparus.common.Constants.DRIVER
import com.stirkaparus.stirkaparus.common.Constants.WASHER

@Composable
fun CompanyUserItem(
    user: User
) {

    var isUserDialogClicked = remember { mutableStateOf(false) }
    if (isUserDialogClicked.value) {
        UserInCompanyDialog(user = user,isUserDialogClicked = isUserDialogClicked)
    }


    Box(
        modifier = Modifier
            .clickable(
                onClick = {
                    isUserDialogClicked.value = true
                }
            )

            .border(
                width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(size = 12.dp)
            )
            .clip(shape = RoundedCornerShape(size = 12.dp))
            .background(Color.White)
            .padding(8.dp)

    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(modifier = Modifier.weight(1f), text = "Имя")
                Text(
                    modifier = Modifier.weight(1f),
                    text = user.name.toString(),
                    textAlign = TextAlign.End
                )
            }
            Divider()
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(modifier = Modifier.weight(1f), text = "Телефон")
                Text(
                    modifier = Modifier.weight(1f),
                    text = user.phone.toString(),
                    textAlign = TextAlign.End
                )
            }
            Divider()

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                val text =
                    when (user.role) {
                        DRIVER -> "ВОДИТЕЛЬ"
                        WASHER -> "МОЙЩИК"
                        else -> "РАБОТНИК"
                    }
                Text(modifier = Modifier.weight(1f), text = "Должность")
                Text(
                    modifier = Modifier.weight(1f), text = text, textAlign = TextAlign.End
                )
            }
        }
    }
}