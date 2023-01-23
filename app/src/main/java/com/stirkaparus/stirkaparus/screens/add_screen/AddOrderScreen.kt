package com.stirkaparus.stirkaparus.screens.add_screen

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.widget.Toast
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.firestore.FieldValue
import com.stirkaparus.stirkaparus.common.Constants
import com.stirkaparus.stirkaparus.model.Order
import com.stirkaparus.stirkaparus.screens.TopBarComponent
import com.stirkaparus.stirkaparus.screens.order_edit_screen.OrderEditViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddScreen(navController: NavController) {

    val viewModel = AddOrderScreenViewModel()

    val editViewModel = OrderEditViewModel()

    val context = LocalContext.current


    var buttonLoading by remember { mutableStateOf(true) }
    val phone = remember { mutableStateOf("") }
    val address = remember { mutableStateOf("") }
    val comment = remember { mutableStateOf("") }
    val count = remember { mutableStateOf("") }
    val total = remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    val options = listOf("created", "taken", "washed", "finished", "delivered")
    var selectedOptionText by remember { mutableStateOf(options[0]) }
    var take by remember { mutableStateOf(false) }









    Scaffold(modifier = Modifier) {

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopBarComponent(
                closeButton = false, title = "Новый заказ", navController = navController
            )

            Spacer(modifier = Modifier.height(20.dp))

            OrderEditCustomTextFieldDigits(
                enable = buttonLoading,
                text = phone,
                description = "Телефон",
                digits = true,
                inputType = KeyboardType.Decimal
            )
            Spacer(modifier = Modifier.height(8.dp))
            OrderEditCustomTextFieldDigits(
                enable = buttonLoading, text = address, description = "Адрес"
            )
            Spacer(modifier = Modifier.height(8.dp))
            OrderEditCustomTextFieldDigits(
                enable = buttonLoading, text = comment, description = "Коммент"
            )
            Spacer(modifier = Modifier.height(8.dp))
            OrderEditCustomTextFieldDigits(
                enable = buttonLoading,
                text = count,
                description = "Количество",
                digits = true,
                inputType = KeyboardType.Decimal
            )
            Spacer(modifier = Modifier.height(8.dp))


//
//             ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = {
//                expanded = !expanded
//            }) {
//                TextField(modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(start = 16.dp)
//                    .padding(end = 16.dp),
//
//                    readOnly = true,
//                    value = selectedOptionText,
//                    onValueChange = { },
//                    label = { Text(text = "Статус") },
//                    trailingIcon = {
//                        ExposedDropdownMenuDefaults.TrailingIcon(
//                            expanded = expanded
//                        )
//                    },
//                    colors = TextFieldDefaults.textFieldColors(
//                        cursorColor = Color.DarkGray,
//                        disabledLabelColor = Color.DarkGray,
//                        focusedLabelColor = Color.DarkGray,
//                        placeholderColor = Color.DarkGray,
//                        focusedIndicatorColor = Color.DarkGray
//
//                    )
//                )
//                DropdownMenu(expanded = expanded, onDismissRequest = {
//                    expanded = false
//                }) {
//                    options.forEach { selectionOption ->
//                        DropdownMenuItem(onClick = {
//                            selectedOptionText = selectionOption
//                            expanded = false
//                        }) {
//                            Text(text = selectionOption)
//                        }
//                    }
//                }
//            }
            Spacer(modifier = Modifier.height(18.dp))

            //button
            Button(modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp)
                .padding(end = 16.dp)
                .height(50.dp), colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.LightGray,
            ), enabled = buttonLoading, onClick = {
                if (!validate(
                        phone.value, address.value
                    )
                ) return@Button showToast(context, "Заполните обязательные поля")
                if (count.value != "" && count.value.toInt() > 0) {
                    Log.e(TAG, "AddScreen: true")
                    take = true
                }

                buttonLoading = false

                val order = Order()
                order.phone = phone.value
                order.address = address.value
                order.comment = comment.value
                if (take) {
                    order.status = Constants.TAKEN
                    order.taken_time = FieldValue.serverTimestamp()
                    order.created_time = FieldValue.serverTimestamp()
                    order.count = count.value.toInt()

                } else {
                    order.status = Constants.CREATED
                    order.created_time = FieldValue.serverTimestamp()
                    order.count = 0

                }

                viewModel.addOrder(order,
                    success = {
                        buttonLoading = true
                        showToast(context, "Заказ успешно добавлен")


                    },
                    failure = {
                        buttonLoading = true
                        showToast(context, "что то пошло не так")

                    })
            }) {
                Text(
                    text = if (buttonLoading) {
                        "Сохранить"
                    } else {
                        ""
                    }
                )

                if (!buttonLoading) {

                    CircularProgressIndicator(
                        modifier = Modifier.size(30.dp), color = Color.DarkGray, strokeWidth = 2.dp
                    )
                }
            }
        }

    }

}

fun validate(phone: String, address: String): Boolean {
    return when {
        phone == "" -> {
            false
        }
        address == "" -> {
            false
        }

        else -> {
            true
        }
    }
}

fun showToast(context: Context, text: String) {
    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()


}

@SuppressLint("SuspiciousIndentation")
@Composable
private fun OrderEditCustomTextFieldDigits(
    text: MutableState<String>,
    description: String,
    digits: Boolean = false,
    inputType: KeyboardType = KeyboardType.Text,
    enable: Boolean = true
) {

    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp)
            .padding(end = 16.dp),
        value = text.value,
        enabled = enable,
        onValueChange = { v ->
            text.value = if (digits) {
                v.filter { it.isDigit() }

            } else {
                v
            }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = inputType, imeAction = ImeAction.Next
        ),
        label = { Text(text = description) },
        colors = TextFieldDefaults.textFieldColors(
            cursorColor = Color.DarkGray,
            disabledLabelColor = Color.DarkGray,
            focusedLabelColor = Color.DarkGray,
            placeholderColor = Color.DarkGray,
            focusedIndicatorColor = Color.DarkGray

        )

    )
}







