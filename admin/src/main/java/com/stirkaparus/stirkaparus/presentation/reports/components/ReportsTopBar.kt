package com.stirkaparus.stirkaparus.presentation.reports.components

import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import com.stirkaparus.stirkaparus.common.Constants

@Composable
fun ReportsTopBar() {

    TopAppBar(
        title = {
            Text(text = Constants.REPORTS_SCREEN)
        }
    )

}