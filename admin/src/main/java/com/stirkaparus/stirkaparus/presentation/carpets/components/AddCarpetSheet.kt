package com.stirkaparus.stirkaparus.presentation.carpets.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.stirkaparus.model.Carpet
import com.stirkaparus.stirkaparus.presentation.carpets.CarpetsViewModel
import com.stirkaparus.stirkaparus.presentation.components.SmallSpacer
import com.stirkaparus.stirkaparus.presentation.order_edit_screen.showToast
import kotlin.math.roundToInt

@Composable
fun AddCarpetSheet(
    id: String,
    viewModel: CarpetsViewModel = hiltViewModel(),
//    onClick: () -> Unit,
    price: Int = 120,

    ) {
    val context = LocalContext.current
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








    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SmallSpacer()
        Divider(
            Modifier
                .width(50.dp)
                .height(3.dp), color = Color.Black
        )
        SmallSpacer()
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = "Добавление ковра",
            style = MaterialTheme.typography.subtitle1,
        )
        SmallSpacer()
        SmallSpacer()
        SmallSpacer()
        Divider()
        SmallSpacer()

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            TextField(
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp)
                    .padding(end = 8.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal, imeAction = ImeAction.Next
                ),
                label = { Text(text = "Ширина") },
                value = sideA.value,
                singleLine = true,
                onValueChange = { v ->
                    sideA.value = v.filter { it.isDigit() }
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White
                ),
                textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
            )
            TextField(
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp)
                    .padding(end = 8.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal, imeAction = ImeAction.Next
                ),
                label = { Text(text = "Длина") },
                value = sideB.value,
                singleLine = true,
                onValueChange = { v ->
                    sideB.value = v.filter { it.isDigit() }
                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.White
                ),
                textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
            )
        }

        SmallSpacer()
        Divider()
        SmallSpacer()

        Row(Modifier.fillMaxWidth()) {


            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "сумма",

                    textAlign = TextAlign.Start
                )
                Text(text = ": ")
                Text(
                    text = sum.value,
                    textAlign = TextAlign.Start
                )

            }
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "квадратов",
                    textAlign = TextAlign.Start
                )
                Text(text = ": ")

                Text(
                    text = square.value,
                    textAlign = TextAlign.Start
                )

            }


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
                        .align(Alignment.CenterHorizontally),
                    onClick = {


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


                            viewModel.addCarpet(
                                carpet
                            )

                        }


                    },
                    colors = ButtonDefaults.buttonColors(
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



