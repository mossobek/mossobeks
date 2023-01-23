package com.stirkaparus.stirkaparus.screens.add_screen

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.stirkaparus.stirkaparus.screens.order_edit_screen.OrderEditViewModel
import com.stirkaparus.stirkaparus.screens.orders_list_screen.components.Status

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddScreen(navController: NavController) {

    val viewModel = AddOrderScreenViewModel()

    val editViewModel = OrderEditViewModel()

    val context = LocalContext.current


    var buttonLoading by remember { mutableStateOf(false) }

    val phone = remember { mutableStateOf("") }
    val address = remember { mutableStateOf("") }
    val comment = remember { mutableStateOf("") }
    val count = remember { mutableStateOf("") }
    val total = remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    val options = listOf("created", "taken", "washed", "finished", "delivered")
    var selectedOptionText by remember { mutableStateOf(options[0]) }

    Scaffold(modifier = Modifier) {

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OrderEditTopBarComponent(navController = navController)

            Spacer(modifier = Modifier.height(20.dp))

            OrderEditCustomTextFieldDigits(
                phone,
                description = "Телефон",
                digits = true,
                inputType = KeyboardType.Decimal
            )
            Spacer(modifier = Modifier.height(8.dp))
            OrderEditCustomTextFieldDigits(address, description = "Адрес")
            Spacer(modifier = Modifier.height(8.dp))
            OrderEditCustomTextFieldDigits(comment, description = "Коммент")
            Spacer(modifier = Modifier.height(8.dp))
            OrderEditCustomTextFieldDigits(
                count,
                description = "Количество",
                true,
                inputType = KeyboardType.Decimal
            )
            Spacer(modifier = Modifier.height(8.dp))
            OrderEditCustomTextFieldDigits(
                total,
                description = "Сумма",
                true,
                inputType = KeyboardType.Decimal
            )
            Spacer(modifier = Modifier.height(8.dp))



            ExposedDropdownMenuBox(expanded = expanded,
                onExpandedChange = {
                    expanded = !expanded
                }
            ) {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp)
                        .padding(end = 16.dp),

                    readOnly = true,
                    value = selectedOptionText,
                    onValueChange = { },
                    label = { Text(text = "Статус") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = expanded
                        )
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        cursorColor = Color.DarkGray,
                        disabledLabelColor = Color.DarkGray,
                        focusedLabelColor = Color.DarkGray,
                        placeholderColor = Color.DarkGray,
                        focusedIndicatorColor = Color.DarkGray

                    )
                )
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = {
                        expanded = false
                    }
                ) {
                    options.forEach { selectionOption ->
                        DropdownMenuItem(
                            onClick = {
                                selectedOptionText = selectionOption
                                expanded = false
                            }
                        ) {
                            Text(text = selectionOption)
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(18.dp))

            //button
            Button(modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp)
                .padding(end = 16.dp)
                .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.LightGray
                ),
                onClick = {
                    buttonLoading = true
                    viewModel.addOrder(
                        data = hashMapOf(
                            "phone" to phone.value.toString(),
                            "address" to address.value.toString(),
                            "comment" to comment.value.toString(),
                            "count" to count.value.toString().toInt(),
                            "total" to total.value.toString().toInt(),
                            "status" to selectedOptionText.toString(),

                            ),
                        success = {
                            buttonLoading = false

                            showToast(context, "изменения сохранены")


                            navController.popBackStack()
                        },
                        failure = { buttonLoading = false }
                    )
                }
            ) {
                Text(
                    text = if (!buttonLoading) {
                        "Сохранить"
                    } else {
                        ""
                    }
                )

                if (buttonLoading) CircularProgressIndicator(
                    modifier = Modifier.size(30.dp),
                    color = Color.DarkGray,
                    strokeWidth = 2.dp
                )
            }
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
    inputType: KeyboardType = KeyboardType.Text
) {

    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp)
            .padding(end = 16.dp),
        value = text.value,
        onValueChange = { v ->
            text.value = if (digits) {
                v.filter { it.isDigit() }

            } else {
                v
            }
        }, keyboardOptions = KeyboardOptions(
            keyboardType = inputType,
            imeAction = ImeAction.Next
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

@Composable
fun OrderEditTopBarComponent(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(Color.White),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier

        ) {
            IconButton(
                onClick = { navController.popBackStack() }
            ) {
                Icon(
                    imageVector = Icons.Default.Close, contentDescription = "Close icon button"
                )
            }
        }
        Row(
            modifier = Modifier
        ) {
            Text(
                modifier = Modifier,
                style = MaterialTheme.typography.h6,
                text = "Редактировать Заказ"
            )
        }
        Row(
            modifier = Modifier
        ) {

        }
    }
}



