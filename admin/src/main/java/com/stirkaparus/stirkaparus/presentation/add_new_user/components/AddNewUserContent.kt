package com.stirkaparus.stirkaparus.presentation.add_new_user.components

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import com.stirkaparus.stirkaparus.common.Constants.DRIVER
import com.stirkaparus.stirkaparus.common.Constants.TAG
import com.stirkaparus.stirkaparus.common.Constants.WASHER
import com.stirkaparus.stirkaparus.presentation.add_order_screen.components.AddOrderTextField
import com.stirkaparus.stirkaparus.presentation.components.SmallSpacer
import es.dmoral.toasty.Toasty

@Composable
fun AddNewUserContent(
    addUser: (name: String, phone: String, id: String, selected: String) -> Unit
) {
    val name = remember { mutableStateOf("") }
    val phone = remember { mutableStateOf("") }
    val id = remember { mutableStateOf("") }
    val selected = remember { mutableStateOf(DRIVER) }
    val context = LocalContext.current

    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        SmallSpacer()
        AddOrderTextField(text = id, description = "ID")
        SmallSpacer()
        AddUserTextFiled(
            text = name, description = "Имя"
        )
        SmallSpacer()
        AddUserTextFiled(
            text = phone, description = "Телефон", digits = true, inputType = KeyboardType.Decimal
        )
        SmallSpacer()
        AddUserDropDownMenu(selected)
        SmallSpacer()
        SmallSpacer()
        SmallSpacer()
        Button(
            onClick = {
                Log.e(TAG, "AddNewUserContent:   onClick ${selected.value} ")

                if (name.value.isNotBlank() && phone.value.isNotBlank() && id.value.length > 19) {
                    if (selected.value == DRIVER || selected.value == WASHER) {
                        addUser(
                            name.value,
                            phone.value,
                            id.value,
                            selected.value
                        )

                    }
                } else {
                    Toasty.error(context, "Заполните все данные", Toast.LENGTH_SHORT, true).show();
                }
            }
        ) {
            Text(text = "Добавить")
        }
    }
}
