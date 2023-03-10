package com.stirkaparus.stirkaparus.presentation.reports.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.stirkaparus.stirkaparus.presentation.components.BackIcon

@Composable
fun ReportsTopBar(
    navBack: () -> Unit,
    navToAllReports: () -> Unit
) {
    TopAppBar(
        backgroundColor = Color.White,
        contentColor = Color.Black,
        title = {
            Text(color = Color.Black, text = "Отчеты")

        }, navigationIcon = {
            BackIcon {
                navBack()
            }
        },
        actions = {
            IconButton(onClick = {
navToAllReports()
            }) {
                Icon(
                    imageVector = Icons.Filled.List,
                    contentDescription = null,
                    tint = Color.Gray
                )
            }
        })
}