package com.stirkaparus.stirkaparus.presentation.carpets.components

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalProvider
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.stirkaparus.model.Carpet
import com.stirkaparus.stirkaparus.common.Constants
import com.stirkaparus.stirkaparus.presentation.carpets.CarpetsViewModel
import com.stirkaparus.stirkaparus.presentation.components.SmallSpacer
import com.stirkaparus.stirkaparus.presentation.order_edit_screen.OrderEditCustomTextFieldDigits
import com.stirkaparus.stirkaparus.ui.theme.ButtonBlueColor
import com.stirkaparus.stirkaparus.ui.theme.DisableButtonColor
import com.stirkaparus.stirkaparus.ui.theme.TextLabelColor
import es.dmoral.toasty.Toasty
import kotlin.math.roundToInt

@SuppressLint("UnrememberedMutableState")
@Composable
fun CarpetItem(
    carpet: Carpet,
    modifier: Modifier = Modifier,
    onDeleteClick: (carpet: Carpet) -> Unit,
    onEditClick: (carpet: Carpet) -> Unit,
) {


    Card(
        shape = RoundedCornerShape(0.dp),
        modifier = Modifier.fillMaxWidth(),
        content = {
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(

                        start = 8.dp,
                        end = 8.dp,

                        )
            ) {

                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "№0000001", fontSize = 18.sp,
                            textAlign = TextAlign.End,
                            color = Color.Gray
                        )
                    }
                    Row(horizontalArrangement = Arrangement.End) {

                        IconButton(
                            onClick = {
                                onEditClick(carpet)
                            }

                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = null,
                                tint = Color.Gray
                            )
                        }
                        IconButton(
                            onClick = { onDeleteClick(carpet) }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = null,
                                tint = Color.Gray
                            )
                        }
                    }
                }

                Divider()

                CarpetRow(desc = "Ширина", text = carpet.sideA.toString(), descEnd = " см")
                CarpetRow(desc = "Длина", text = carpet.sideB.toString(), descEnd = " см")
                CarpetRow(desc = "Сумма", text = carpet.sum.toString(), descEnd = " руб.")
                CarpetRow(
                    desc = "Квадратов",
                    text = carpet.square.toString(),
                    descEnd = " кв.",
                    divider = false
                )


            }
        })

}



@Composable
private fun CarpetRow(desc: String, text: String, divider: Boolean = true, descEnd: String) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 2.dp)
    ) {
        Row(
            Modifier
                .weight(1f)
                .padding(vertical = 14.dp)
        ) {
            Text(fontSize = 18.sp, text = desc, color = Color.Gray)
        }
        Row(Modifier.weight(2f), horizontalArrangement = Arrangement.End) {
            Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.End) {
                Row(
                    Modifier.padding(vertical = 14.dp),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.Bottom
                ) {

                    Text(text = text, fontSize = 18.sp, textAlign = TextAlign.End)
                    Text(
                        text = descEnd,
                        fontSize = 18.sp,
                        textAlign = TextAlign.End,
                        color = Color.Gray
                    )
                }
                if (divider) {

                    Divider()
                }
            }


        }
    }

}
