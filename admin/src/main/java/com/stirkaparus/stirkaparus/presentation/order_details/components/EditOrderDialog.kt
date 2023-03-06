package com.stirkaparus.stirkaparus.presentation.order_details.components

import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.stirkaparus.model.Order
import com.stirkaparus.stirkaparus.common.Constants
import com.stirkaparus.stirkaparus.presentation.components.SmallSpacer
import com.stirkaparus.stirkaparus.presentation.order_details.OrderDetailsViewModel
import com.stirkaparus.stirkaparus.presentation.order_edit_screen.OrderEditCustomTextFieldDigits
import es.dmoral.toasty.Toasty

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EditOrderDialog(
    order: Order, openDialog: Boolean, viewModel: OrderDetailsViewModel = hiltViewModel()
) {

    val phone = remember { mutableStateOf("") }
    phone.value = order.phone.toString()
    val address = remember { mutableStateOf("") }
    address.value = order.address.toString()
    val comment = remember { mutableStateOf("") }
    comment.value = order.comment.toString()
    val count = remember { mutableStateOf("") }
    count.value = order.count.toString()
    val total = remember { mutableStateOf("") }
    total.value = order.total.toString()
    val context = LocalContext.current

    if (openDialog) {

        Dialog(
            onDismissRequest = { viewModel.closeEditOrderDialog() }, properties = DialogProperties(
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
                    .border(2.dp, color = Color.LightGray, shape = RoundedCornerShape(5.dp))

            ) {


                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 30.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    SmallSpacer(20.dp)
                    Text(text = "Редактировать ЗАКАЗ")
                    SmallSpacer(20.dp)

                    OrderEditCustomTextFieldDigits(
                        phone,
                        description = Constants.PHONE_RU,
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
                        description = Constants.COUNT_RU,
                        true,
                        inputType = KeyboardType.Decimal
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OrderEditCustomTextFieldDigits(
                        total,
                        description = Constants.TOTAL_RU,
                        true,
                        inputType = KeyboardType.Decimal
                    )

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
                            if (phone.value.isNotBlank() &&
                                phone.value.length > 5 &&
                                address.value.isNotBlank()
                            ) {
                                val totalOfNull =
                                    if (total.value.isBlank()) 0 else total.value.toInt()
                                val countOrNull =
                                    if (count.value.isBlank()) 0 else count.value.toInt()
                                if (countOrNull > 0) {
                                    viewModel.closeEditOrderDialog()
                                    viewModel.editOrder(
                                        id = order.id.toString(),
                                        phone = phone.value,
                                        address = address.value,
                                        comment = comment.value,
                                        count = countOrNull,
                                        total = totalOfNull
                                    )
                                }
                            } else {
                                Toasty.error(
                                    context,
                                    "Заполните все поля",
                                    Toast.LENGTH_SHORT,
                                    true
                                ).show()
                            }

                        }) {
                        Text(
                            text = "Сохранить"

                        )

                    }
                }

            }

        }
    }
}