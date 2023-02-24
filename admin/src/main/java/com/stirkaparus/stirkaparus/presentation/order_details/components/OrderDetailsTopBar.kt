package com.stirkaparus.stirkaparus.presentation.order_details.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.stirkaparus.stirkaparus.common.Constants.EDIT_ORDER_TOP_BAR_TEXT_RU
import com.stirkaparus.stirkaparus.presentation.components.BackIcon


@Composable
fun OrderDetailsTopBar(
    navBack: () -> Unit,
    editOrder: () -> Unit,
    deleteOrder: () -> Unit
) {
    TopAppBar(title = {
        Text(text = EDIT_ORDER_TOP_BAR_TEXT_RU)
    }, actions = {
        IconButton(
            modifier = Modifier.padding(end = 0.dp),
            onClick = {
                editOrder()
            }) {
            Icon(
                imageVector = Icons.Default.Edit, contentDescription = "Edit icon button"
            )
        }
        IconButton(modifier = Modifier.padding(end = 8.dp), onClick = {
            deleteOrder()
        }) {
            Icon(
                imageVector = Icons.Default.Delete, contentDescription = "Delete icon button"
            )
        }
    }, navigationIcon = {
        BackIcon {
            navBack()
        }


    })

}




