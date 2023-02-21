package com.stirkaparus.driver.presentation.orders.components

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.stirkaparus.driver.presentation.orderDetails.StatusDetails
import com.stirkaparus.model.Order


@Composable
fun ButtonsRow(
    order: Order,
    context: Context,
    statusDetails: StatusDetails,
    weight: Float = 1f,
    buttonPadding: Dp = 8.dp,
    takenClick: ()->Unit,
    deliveredClick: ()->Unit,
) {
    Row(Modifier.fillMaxWidth()) {


        Button(modifier = Modifier
            .weight(weight)
            .padding(buttonPadding)
            .padding(start = 20.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.White,
            ), onClick = {
                val callPhone = order.phone
                val callIntent = Intent(Intent.ACTION_DIAL)
                callIntent.data = Uri.parse("tel:$callPhone")
                ContextCompat.startActivity(context, callIntent, null)
            }) {
            Text(text = "ПОЗВОНИТЬ")
        }

        when (statusDetails) {
            is StatusDetails.Created -> {
                Button(modifier = Modifier
                    .weight(weight)
                    .padding(buttonPadding)
                    .padding(end = 20.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.White,
                    ), onClick = {
                        takenClick()
                    }) {
                    Text(text = "Забрал")
                }

            }
            is StatusDetails.Finished -> {
                Button(modifier = Modifier
                    .weight(weight)
                    .padding(buttonPadding)
                    .padding(end = 20.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.White,
                    ), onClick = {
                        deliveredClick()
                    }) {
                    Text(text = "Доставил")
                }

            }
        }
    }
}
