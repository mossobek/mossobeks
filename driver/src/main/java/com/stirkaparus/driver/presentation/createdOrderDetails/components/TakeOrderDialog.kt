package com.stirkaparus.driver.presentation.createdOrderDetails.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog


@Composable
fun TakeOrderDialog(
    onDismiss: () -> Unit,
    addCount: (Int) -> Unit,
    loadingState: MutableState<Boolean>
) {
    val count = remember { mutableStateOf("") }


    Dialog(
        onDismissRequest = onDismiss,

        ) {
        Surface(
            modifier = Modifier,
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
                    text = "Введите количество ковров"
                )
                TextField(modifier = Modifier
                    .width(165.dp)
                    .padding(bottom = 16.dp),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Decimal,
                        imeAction = ImeAction.Next
                    ),
                    textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
                    value = count.value,
                    maxLines = 1,
                    label = { Text(text = "Количество") },
                    onValueChange = { v ->
                        count.value = v.filter { it.isDigit() }
                    })

                LoadingButton(
                    modifier = Modifier.padding(bottom = 16.dp),
                    loading = loadingState
                ) {
                    if (count.value == "") return@LoadingButton
                    if (count.value.toInt() == 0) return@LoadingButton
                    else addCount(count.value.toInt())
                }


            }
        }
    }
}


@Composable
fun DeliveryOrderDialog(
    onDismiss: () -> Unit,
    updateOrder: () -> Unit,
    loadingState: MutableState<Boolean>
) {
    val count = remember { mutableStateOf("") }


    Dialog(
        onDismissRequest = onDismiss,

        ) {
        Surface(
            modifier = Modifier,
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
                    text = "Заказ Доставлен?"
                )


                LoadingButton(
                    text = "Доставлен",
                    modifier = Modifier.padding(bottom = 16.dp),
                    loading = loadingState
                ) {
                    updateOrder()
                }


            }
        }
    }
}

@Composable
fun LoadingButton(
    text: String = "Добавить",
    modifier: Modifier,
    loading: MutableState<Boolean> = mutableStateOf(false),
    onClick: () -> Unit
) {


    Button(
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.LightGray,
        ),
        enabled = !loading.value,
        onClick = {
            onClick()
        }) {

        Text(
            text = if (!loading.value) {
                text
            } else {
                ""
            }
        )
        if (loading.value) {

            CircularProgressIndicator(
                modifier = Modifier.size(30.dp), color = Color.DarkGray, strokeWidth = 2.dp
            )
        }
    }
}


