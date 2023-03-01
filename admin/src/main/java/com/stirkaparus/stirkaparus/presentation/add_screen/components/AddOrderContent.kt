package com.stirkaparus.stirkaparus.presentation.add_screen.components

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.stirkaparus.model.Order
import com.stirkaparus.model.Response
import com.stirkaparus.stirkaparus.presentation.add_screen.AddOrderViewModel
import com.stirkaparus.stirkaparus.presentation.components.SmallSpacer
import com.stirkaparus.stirkaparus.presentation.order_edit_screen.showToast
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

const val TAG = "AddOrderContent"

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AddOrderContent(
    viewModel: AddOrderViewModel = hiltViewModel(),
    padding: PaddingValues,
    addOrder: (order: Order) -> Unit
) {

    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val phone = remember { mutableStateOf("") }
    val address = remember { mutableStateOf("") }
    val comment = remember { mutableStateOf("") }
    val count = remember { mutableStateOf("") }
    var success by remember { mutableStateOf(false) }
    success = viewModel.addOrderInFirestoreResponse == Response.Success(true)

    Log.e(TAG, "AddOrderContent: ${success}")


    val order = Order()
    val keyboard = LocalSoftwareKeyboardController.current
    if (success) {
        viewModel.addOrderInFirestoreResponse = Response.Success(false)
        phone.value = ""
        address.value = ""
        comment.value = ""
        count.value = ""
        Toasty.success(context, "Заказ добавлен", Toast.LENGTH_SHORT, true).show();
    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(padding), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SmallSpacer()
        AddOrderTextField(
            text = phone, description = "Телефон", digits = true, inputType = KeyboardType.Decimal
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
        Button(

            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp)
                .padding(end = 16.dp)
                .height(50.dp), colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.LightGray
            ), onClick = {
                keyboard?.hide()
                if (phone.value.isNotEmpty() || address.value.isNotEmpty()) {
                    order.phone = phone.value
                    order.address = address.value
                    order.count = if (count.value.isBlank()) 0 else count.value.toInt()
                    order.comment = comment.value
                    addOrder(order)
                } else {
                    showToast(context, "что то пошло не так")
                }
            }) {
            Text(text = "Добавить")
        }

    }

}