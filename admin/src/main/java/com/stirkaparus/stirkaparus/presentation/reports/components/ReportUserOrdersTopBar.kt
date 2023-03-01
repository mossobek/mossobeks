package com.stirkaparus.stirkaparus.presentation.reports.components

import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import com.stirkaparus.stirkaparus.presentation.components.BackIcon

@Composable
fun ReportUserOrdersTopBar(
    navBack: () -> Unit
) {
    TopAppBar(title = {
        Text(text = "Сделать отчеты")

    }, navigationIcon = {
        BackIcon {
            navBack()
        }
    })
}