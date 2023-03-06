package com.stirkaparus.stirkaparus.presentation.order_details.components

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.stirkaparus.stirkaparus.presentation.order_details.OrderDetailsViewModel
import com.stirkaparus.stirkaparus.ui.theme.ButtonBlueColor
import es.dmoral.toasty.Toasty

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun OrderTakeDialog(
    openDialog: Boolean,
    id: String,
    viewModel: OrderDetailsViewModel = hiltViewModel(),
) {

    val keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current
    var count by remember {
        mutableStateOf("")
    }
    if (openDialog) {
        Dialog(
            onDismissRequest = {
                viewModel.closeTakeDialog()
            },
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true,
                usePlatformDefaultWidth = false
            )
        ) {
            Card(
                elevation = 5.dp,
                shape = RoundedCornerShape(5.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .border(2.dp, color = Blue, shape = RoundedCornerShape(2.dp))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp),
                    verticalArrangement = Arrangement.spacedBy(25.dp)
                ) {

                    Text(
                        modifier = Modifier.fillMaxWidth(),

                        text = "Забрали заказ у клиента?",
                        style = MaterialTheme.typography.h6,
                        textAlign = TextAlign.Center
                    )
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(15.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(fontSize = 22.sp, text = "количество: ")

                            TextField(
                                textStyle = TextStyle.Default.copy(fontSize = 18.sp),
                                //label = { Text(text = "Кол") },
                                modifier = Modifier
                                    .scale(scaleY = 0.9F, scaleX = 1F)
                                    .width(65.dp),
                                value = count,
                                onValueChange = { it ->
                                    count = it.filter { it.isDigit() }
                                },
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Decimal
                                ),
                                colors = TextFieldDefaults.textFieldColors(
                                    cursorColor = Color.DarkGray,
                                    disabledLabelColor = Color.DarkGray,
                                    focusedLabelColor = Color.DarkGray,
                                    placeholderColor = Color.DarkGray,
                                    focusedIndicatorColor = Color.DarkGray

                                )
                            )
                        }


                    }

                    Divider()
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        DialogOrderCustomButton(
                            onClick = {
                                viewModel.closeTakeDialog()
                            },
                            label = "Нет"
                        )
                        DialogOrderCustomButton(
                            enable = count.isNotEmpty(),
                            onClick = {
                                keyboardController?.hide()
                                if (count.isNotEmpty()) {
                                    if (count.toInt() > 0) {
                                        viewModel.orderTaken(count.toInt(), id)
                                        viewModel.closeTakeDialog()


                                        Log.e(TAG, "OrderTakeDialog: >0")
                                    } else {
                                        Toasty.error(
                                            context,
                                            "Введите количество ковров",
                                            Toast.LENGTH_SHORT,
                                            true
                                        ).show()
                                    }
                                }
                            },
                            label = "Заказ получен"
                        )
                    }

                }

            }
        }


    }

}

@Composable
fun DialogOrderCustomButton(
    onClick: () -> Unit,
    label: String,
    enable: Boolean = true
) {
    Button(
        enabled = enable,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            contentColor = White,
            backgroundColor = ButtonBlueColor
        )
    ) {

        Text(text = label)

    }
}