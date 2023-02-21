package com.stirkaparus.stirkaparus.presentation.carpets.components

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.stirkaparus.model.Carpet
import com.stirkaparus.stirkaparus.presentation.carpets.CarpetsViewModel
import com.stirkaparus.stirkaparus.presentation.order_edit_screen.showToast
import kotlin.math.roundToInt

@Composable
fun AddCarpetAlertDialog(
    id: String,
    viewModel: CarpetsViewModel,
    onClick: () -> Unit,
    onDismiss: () -> Unit,
    price: Int = 120,
    context: Context

) {

    var loading by remember { mutableStateOf(false) }

    val sideA = remember { mutableStateOf("") }
    val sideB = remember { mutableStateOf("") }

    val square = remember { mutableStateOf("") }
    val sum = remember { mutableStateOf("") }

    if (sideB.value != "" && sideA.value != "") {
        val i = ((sideB.value.toDouble()) / 100 * (sideA.value.toDouble()) / 100)
        square.value = ((i * 100).roundToInt() / 100.0).toString()
        sum.value = (((i * price) * 100).roundToInt() / 100).toString()

    }


    Dialog(
        onDismissRequest = onDismiss,
    ) {
        Surface(
            modifier = Modifier,
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colors.surface,
        ) {


            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .background(Color.LightGray)
            ) {
                // TITLE
                Spacer(
                    modifier = Modifier
                        .height(2.dp)
                        .background(Color.LightGray)
                )
                Column(
                    modifier = Modifier
                        .background(Color.White)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(
                        modifier = Modifier.height(22.dp)
                    )
                    Text(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        text = "Добавление ковра",
                        style = MaterialTheme.typography.subtitle1,

                        )
                    Spacer(
                        modifier = Modifier.height(22.dp)
                    )
                    Spacer(
                        modifier = Modifier
                            .height(2.dp)
                            .background(Color.LightGray)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        TextField(keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Decimal, imeAction = ImeAction.Next
                        ),
                            label = { Text(text = "Ширина") },
                            modifier = Modifier
                                .weight(1f)
                                .height(50.dp)
                                .padding(end = 8.dp),
                            value = sideA.value,

                            onValueChange = { v ->
                                sideA.value = v.filter { it.isDigit() }
                            })
                        TextField(keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Decimal, imeAction = ImeAction.Next
                        ),
                            label = { Text(text = "Длина") },
                            modifier = Modifier
                                .weight(1f)
                                .height(50.dp)
                                .padding(start = 8.dp),
                            value = sideB.value,
                            onValueChange = { v ->
                                sideB.value = v.filter { it.isDigit() }
                            })
                    }

                    Spacer(
                        modifier = Modifier.height(22.dp)
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Сумма",
                            modifier = Modifier
                                .weight(1f)
                                .height(50.dp)
                                .padding(8.dp),
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            text = "Квадратов",
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .weight(1f)
                                .height(50.dp)
                                .padding(8.dp),
                            textAlign = TextAlign.Center
                        )


                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = sum.value,
                            modifier = Modifier
                                .weight(1f)
                                .height(50.dp)
                                .padding(8.dp),
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            text = square.value,
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                                .weight(1f)
                                .height(50.dp)
                                .padding(8.dp),
                            textAlign = TextAlign.Center
                        )


                    }



                    Column() {
                        Column(
                            modifier = Modifier
                                .background(Color.White)
                                .fillMaxWidth()
                                .padding(top = 16.dp)
                                .height(40.dp),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Button(
                                modifier = Modifier
                                    .width(200.dp)
                                    .align(Alignment.CenterHorizontally), onClick = {

                                    if (sideB.value != "" && sideB.value != "") {
                                        val a = sideA.value
                                        val b = sideB.value

                                        val carpet = Carpet()
                                        carpet.sideA = a.toInt()
                                        carpet.sideB = b.toInt()
                                        carpet.sum = sum.value.toString().toInt()
                                        carpet.orderId = id
                                        carpet.square = square.value.toDouble()

                                        loading = true


                                        viewModel.addCarpet(carpet = carpet, success = {
                                            loading = false
                                            showToast(
                                                context = context, "Ковер успешно добавлен"
                                            )
                                            onDismiss()
                                        }, failure = {
                                            loading = false
                                            showToast(
                                                context = context, "Что то пошло не так"
                                            )
                                        })

                                    }


                                }, colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Color.LightGray,
                                )
                            ) {

                                Text(
                                    text = if (!loading) {
                                        "Добавить"
                                    } else {
                                        ""
                                    }
                                )

                                if (loading) CircularProgressIndicator(
                                    modifier = Modifier.size(30.dp),
                                    color = Color.DarkGray,
                                    strokeWidth = 2.dp
                                )
                            }
                        }
                    }
                }

            }
        }
    }


}