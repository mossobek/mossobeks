package com.stirkaparus.driver.presentation.orderDetails.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.stirkaparus.driver.presentation.createdOrderDetails.components.LoadingButton


@Composable
fun CancelOrderDialogAlert(
    onDismiss: () -> Unit,
    cancelOrder: () -> Unit,
    loadingState: MutableState<Boolean>
) {
    val text = remember { mutableStateOf("") }


    Dialog(
        onDismissRequest = onDismiss,

        ) {
        Surface(
            modifier = Modifier.padding(16.dp),
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colors.surface,

            ) {
            Column(
                Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    modifier = Modifier.padding(20.dp),
                    fontSize = 20.sp,
                    text = "Введите причину отмены"
                )
                TextField(modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
                    value = text.value,
                    maxLines = 2,
                    label = { Text(text = "Причина отмены") },
                    onValueChange = { text.value = it })
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    LoadingButton(
                        text = "Я передумал",
                        modifier = Modifier.padding(bottom = 16.dp),
                        loading = loadingState
                    ) {
                        onDismiss()

                    }
                    LoadingButton(
                        text = "Отменить",
                        modifier = Modifier.padding(bottom = 16.dp),
                    ) {
                        cancelOrder()

                    }
                }


            }
        }
    }
}



