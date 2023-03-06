package com.stirkaparus.stirkaparus.presentation.carpets.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.stirkaparus.model.Carpet
import com.stirkaparus.stirkaparus.presentation.carpets.CarpetsViewModel
import com.stirkaparus.stirkaparus.presentation.components.SmallSpacer
import com.stirkaparus.stirkaparus.ui.theme.ButtonBlueColor
import com.stirkaparus.stirkaparus.ui.theme.DisableButtonColor
import com.stirkaparus.stirkaparus.ui.theme.TextLabelColor
import kotlin.math.roundToInt

@Composable
fun AddCarpetSheet(
    id: String,
    viewModel: CarpetsViewModel = hiltViewModel(),
    price: Int = 120,
    onDismiss: () -> Unit,

    ) {

    var buttonEnable by remember { mutableStateOf(false) }

    val sideA = remember { mutableStateOf("") }
    val sideB = remember { mutableStateOf("") }

    val square = remember { mutableStateOf("0") }
    val sum = remember { mutableStateOf("0") }

    if (sideB.value != "" && sideA.value != "") {
        val i = ((sideB.value.toDouble()) / 100 * (sideA.value.toDouble()) / 100)
        square.value = ((i * 100).roundToInt() / 100.0).toString()
        sum.value = (((i * price) * 100).roundToInt() / 100).toString()
        buttonEnable = true

    } else {
        buttonEnable = false
    }








    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
            .padding(horizontal = 8.dp),
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
        SmallSpacer()
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Left,
            text = "Добавление ковра",
            fontSize = 24.sp,
        )
        SmallSpacer(12.dp)
        Row(Modifier.fillMaxWidth()) {
            Row(
                Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            ) {
                Column() {
                    Text(
                        modifier = Modifier.padding(12.dp),
                        text = "Ширина",
                        color = TextLabelColor,
                    )
                    OutlinedTextField(
                        textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
                        value = sideA.value,
                        onValueChange = { v ->
                            sideA.value = v.filter { it.isDigit() }

                        },
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.White
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Decimal, imeAction = ImeAction.Next
                        ),
                    )
                }
            }
            Row(
                Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
            ) {
                Column() {
                    Text(
                        modifier = Modifier.padding(12.dp), text = "Длина", color = TextLabelColor
                    )
                    OutlinedTextField(

                        textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
                        value = sideB.value,
                        onValueChange = { v ->
                            sideB.value = v.filter { it.isDigit() }

                        },
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.White
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Decimal, imeAction = ImeAction.Next
                        ),
                    )
                }
            }
        }

        SmallSpacer(16.dp)
        Divider()
        SmallSpacer(19.dp)
        Column(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        ) {
            CarpetRow(key = "Квадратов", value = square.value)
            CarpetRow(key = "Стоимость", value = sum.value)
            CarpetRow(key = "Цена за квадрат", value = price.toString())

        }
        SmallSpacer(20.dp)
        Divider()
        SmallSpacer(20.dp)
        Button(
            modifier = Modifier
                .height(45.dp)
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            enabled = buttonEnable,
            onClick = {

                if (sideB.value != "" && sideB.value != "") {
                    val carpet = Carpet()
                    carpet.sideA = sideA.value.toInt()
                    carpet.sideB = sideB.value.toInt()
                    carpet.square = square.value.toDouble()
                    carpet.sum = sum.value.toInt()
                    carpet.orderId = id
                    viewModel.addCarpet(carpet = carpet).also {
                        sideA.value = "0"
                        sideB.value = "0"
                    }

                    onDismiss()
                }


            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = ButtonBlueColor,
                contentColor = Color.White,
                disabledBackgroundColor = DisableButtonColor,
                disabledContentColor = Color.Gray,
            )
        ) {
            Text(text = "Добавить ковер")
        }
    }
}


@Composable
private fun CarpetRow(key: String, value: String) {


    Row(
        Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = key, fontSize = 18.sp)
        Text(text = value, fontWeight = Bold, fontSize = 18.sp)
    }

}

