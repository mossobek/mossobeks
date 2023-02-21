package com.stirkaparus.driver.presentation.addOrder.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import com.stirkaparus.driver.presentation.components.SmallSpacer
import com.stirkaparus.model.Order

@Composable
fun AddOrderContent(
    padding: PaddingValues,
    addOrder: (order: Order) -> Unit
) {
    val phone = remember { mutableStateOf("") }
    val address = remember { mutableStateOf("") }
    val comment = remember { mutableStateOf("") }
    val count = remember { mutableStateOf("") }

    val order = Order()


    Column(
        Modifier
            .fillMaxSize()
            .padding(padding)
    ) {
        SmallSpacer()
        AddOrderTextField(
            text = phone,
            description = "Телефон",
            digits = true,
            inputType = KeyboardType.Decimal
        )
        SmallSpacer()
        AddOrderTextField(
            text = address, description = "Адрес"
        )
        SmallSpacer()
        AddOrderTextField(
            text = comment, description = "Комментарий"
        )
        SmallSpacer()
        AddOrderTextField(
            text = count,
            description = "Количество",
            digits = true,
            inputType = KeyboardType.Decimal
        )
        SmallSpacer()
        Button(onClick = {
            addOrder(order)
        }) {

        }

    }

}