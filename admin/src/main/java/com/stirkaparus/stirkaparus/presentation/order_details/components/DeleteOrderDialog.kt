package com.stirkaparus.stirkaparus.presentation.order_details.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.stirkaparus.stirkaparus.presentation.components.SmallSpacer
import com.stirkaparus.stirkaparus.presentation.order_details.OrderDetailsViewModel


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DeleteOrderDialog(
    navBack:()->Unit,
    id: String,
    openDialog: Boolean,
    viewModel: OrderDetailsViewModel = hiltViewModel()
) {
    if (openDialog) {

        Dialog(
            onDismissRequest = {
                viewModel.closeDeleteOrderDialog()
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
                    Text(text = "УДАЛЕНИЕ ЗАКАЗ")
                    SmallSpacer(20.dp)
                    Row(Modifier.fillMaxWidth()) {


                        //button
                        Button(
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 16.dp)
                                .padding(end = 16.dp)
                                .height(50.dp),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color.LightGray
                            ),
                            onClick = {
                                viewModel.closeDeleteOrderDialog()
                            }

                        ) {
                            Text(
                                text = "Нет"

                            )

                        }
                        Button(
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 16.dp)
                                .padding(end = 16.dp)
                                .height(50.dp),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color.LightGray
                            ),
                            onClick = {
                                navBack()
                                viewModel.deleteOrder(id)
                                viewModel.closeDeleteOrderDialog()
                            }

                        ) {
                            Text(
                                text = "Удалить"

                            )

                        }
                    }

                }
            }

        }
    }
}


