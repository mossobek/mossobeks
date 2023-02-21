package com.stirkaparus.stirkaparus.presentation.order_details_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Lens
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.stirkaparus.stirkaparus.presentation.orders_list_screen.components.Status


@Composable
fun StatusChange(
    currentSt: String? = "Created",
    onClick: (Status) -> Unit,
    onDismiss: () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismiss,
    ) {
        Surface(
            modifier = Modifier,
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colors.surface,
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .background(Color.LightGray)
            ) {
                // TITLE
                Spacer(
                    modifier = Modifier
                        .height(2.dp)
                        .background(Color.LightGray)
                )
                Column(
                    modifier = Modifier
                        .background(Color.White)
                        .fillMaxWidth()
                        .height(40.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        text = "Статус",
                        style = MaterialTheme.typography.subtitle1,

                        )
                }


                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                        .weight(weight = 1f, fill = false)
                        .padding(vertical = 16.dp)
                ) {

                    TextCustomStatus(
                        status = Status.Created, currentSt, onClick = { onClick(Status.Created) }
                    )
                    Spacer(
                        modifier = Modifier
                            .height(2.dp)
                            .background(Color.LightGray)
                    )

                    TextCustomStatus(
                        status = Status.Taken, currentSt, onClick = { onClick(Status.Taken) }
                    )
                    Spacer(
                        modifier = Modifier
                            .height(2.dp)
                            .background(Color.LightGray)
                    )

                    TextCustomStatus(
                        status = Status.Washed, currentSt, onClick = { onClick(Status.Washed) }
                    )
                    Spacer(
                        modifier = Modifier
                            .height(2.dp)
                            .background(Color.LightGray)
                    )
                    TextCustomStatus(
                        status = Status.Delivered, currentSt, onClick = { onClick(Status.Delivered) }
                    )
                    Spacer(
                        modifier = Modifier
                            .height(2.dp)
                            .background(Color.LightGray)
                    )
                    TextCustomStatus(
                        status = Status.Finished, currentSt, onClick = { onClick(Status.Finished) }
                    )
                }
                Column() {
                    Column(
                        modifier = Modifier
                            .background(Color.White)
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                            .height(40.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Button(
                            modifier = Modifier
                                .width(200.dp)
                                .align(Alignment.CenterHorizontally),
                            onClick = { onDismiss() },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color.LightGray,
                            )
                        ) {

                            Text(text = "Закрыть")

                        }
                    }
                }

            }
        }
    }
}

@Composable
fun TextCustomStatus(
    status: Status,
    currentStatus: String?,
    onClick: () -> Unit
) {
    var check by remember { mutableStateOf(false) }
    if (status.status == currentStatus) check = true
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .height(35.dp)
            .background(Color.White),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(26.dp),
                imageVector = Icons.Default.Lens,
                tint = status.color!!,
                contentDescription = status.trans
            )
            Text(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .padding(end = 16.dp),
                text = status.trans,
                color = Color.Black,
                fontSize = 18.sp
            )
        }

        Row(
            horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.CenterVertically
        ) {

            if (check) {

                Icon(
                    modifier = Modifier.size(26.dp),
                    imageVector = Icons.Default.Check,
                    tint = Color.Black,
                    contentDescription = "desc"
                )

            }
        }

    }
}