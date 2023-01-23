package com.stirkaparus.stirkaparus.screens.order_edit_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.stirkaparus.stirkaparus.model.Order

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun OrderEditScreen(navController: NavController) {
    val id = navController.currentBackStackEntry?.arguments?.getString("id")
    val order: Order = Order()
    order.phone = "89285222253"
    val maxChar = 5

    Scaffold(modifier = Modifier.padding(18.dp)) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Редактировать Заказ",
                fontSize = 28.sp

            )
            Spacer(modifier = Modifier.height(20.dp))
            OrderEditCustomTextField(order.address.toString())
        }

    }


}

@SuppressLint("SuspiciousIndentation")
@Composable
private fun OrderEditCustomTextField(textString: String) {
    val text = remember { mutableStateOf("") }
    text.value = textString
        TextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = text.value,
            onValueChange = {
                text.value = it
            }

        )
}