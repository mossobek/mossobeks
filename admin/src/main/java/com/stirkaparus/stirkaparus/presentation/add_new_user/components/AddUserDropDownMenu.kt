package com.stirkaparus.stirkaparus.presentation.add_new_user.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddUserDropDownMenu(selectedText: MutableState<String>) {
    val options = mapOf("driver" to "Водитель", "washer" to "Мойщик")
    var expanded by remember { mutableStateOf(false) }
    var selectedOptionText by remember { mutableStateOf(options["driver"]) }
    ExposedDropdownMenuBox(modifier = Modifier
        .fillMaxWidth()
        .padding(start = 16.dp)
        .padding(end = 16.dp),
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            readOnly = true,
            value = selectedOptionText!!,
            onValueChange = { },
            label = { Text("Роль") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors()
        )
        ExposedDropdownMenu(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp)
            .padding(end = 16.dp),
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            }) {
            options.forEach { selectionOption ->
                DropdownMenuItem(modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp)
                    .padding(end = 16.dp),
                    onClick = {
                        selectedOptionText = selectionOption.value
                        expanded = false
                        selectedText.value = selectionOption.key

                    }) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp)
                            .padding(end = 16.dp), text = selectionOption.value
                    )
                }
            }
        }
    }

}
