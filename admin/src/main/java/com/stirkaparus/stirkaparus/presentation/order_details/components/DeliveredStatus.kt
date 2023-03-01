package com.stirkaparus.stirkaparus.presentation.order_details.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.stirkaparus.stirkaparus.presentation.order_details.OrderDetailsViewModel


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DeliveredStatus(
    id: String,
    viewModel: OrderDetailsViewModel = hiltViewModel(),
) {
    if (viewModel.deliveredDialog) {
        Dialog(
            onDismissRequest = {
                viewModel.closeDeliveredDialog()
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
                    .border(1.dp, color = Color.LightGray, shape = RoundedCornerShape(4.dp))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp),
                    verticalArrangement = Arrangement.spacedBy(25.dp)
                ) {

                    Text(
                        modifier = Modifier.fillMaxWidth(),

                        text = "Заказ доставлен?",
                        style = MaterialTheme.typography.h6,
                        textAlign = TextAlign.Center
                    )
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(15.dp)
                    ) {


                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        DialogOrderCustomButton(
                            onClick = {
                                viewModel.closeDeliveredDialog()
                            },
                            label = "Нет"
                        )
                        DialogOrderCustomButton(
                            enable = true,
                            onClick = {
                                viewModel.orderDeliveredStatus(id)
                                viewModel.closeDeliveredDialog()
                            },
                            label = "Заказ доставлен"
                        )
                    }

                }

            }
        }
    }
}



