package com.stirkaparus.stirkaparus.presentation.reports.components

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.Report
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.stirkaparus.stirkaparus.presentation.components.BackIcon
import com.stirkaparus.stirkaparus.presentation.reports.ReportsViewModel
import com.stirkaparus.stirkaparus.ui.theme.Blue14
import com.stirkaparus.stirkaparus.ui.theme.Blue15
import com.stirkaparus.stirkaparus.ui.theme.Blue16

@Composable
fun ReportUserOrdersTopBar(
    viewModel: ReportsViewModel = hiltViewModel(),
    username: String,
    navBack: () -> Unit
) {
    TopAppBar(
        backgroundColor = Color.White,
        contentColor = Color.Black,
        title = {
            Text(color = Color.Black, text = "Рассчет для : $username")

        }, navigationIcon = {
            BackIcon {
                navBack()
            }
        },
        actions = {
            IconButton(onClick = {
                viewModel.openUserReportDialog()
            }) {
                Icon(
                    imageVector = Icons.Filled.Checklist,
                    contentDescription = null,
                    tint = Color.Gray
                )
            }
        })
}