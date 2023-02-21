package com.stirkaparus.driver.presentation.createdOrderDetails.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.unit.dp


@Composable
fun ConnectDialog(
    onDismiss: () -> Unit,
    onMapClick: () -> Unit,
    onWhatsappClick: () -> Unit,
    ) {


    AlertDialog(
        onDismissRequest = onDismiss,
        {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium,
                color = MaterialTheme.colors.surface,
            ) {
                Column(Modifier.padding(16.dp)) {

                    ButtonInAlertDialog(text = "WhatsApp") {

                    }
                    ButtonInAlertDialog(text = "Марштрут") {

                    }
                    ButtonInAlertDialog(text = "Скрыть") {
                        onDismiss()
                    }
                }

            }
        },
        modifier = Modifier.customDialogModifier(),
    )
}

fun Modifier.customDialogModifier() = layout { measurable, constraints ->

    val placeable = measurable.measure(constraints);
    layout(constraints.maxWidth, constraints.maxHeight) {
        placeable.place(0, constraints.maxHeight - placeable.height, 10f)
    }

}

@Composable
fun ButtonInAlertDialog(text: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp)
            .height(35.dp)
            .background(Color.White),

        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.Center
    ) {


        Button(modifier = Modifier.width(150.dp), colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.LightGray,
        ),
            onClick = { onClick() }
        ) {
            Text(text = text)

        }

    }
}


