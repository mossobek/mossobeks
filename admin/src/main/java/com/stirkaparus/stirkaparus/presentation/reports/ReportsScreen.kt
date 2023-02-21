package com.stirkaparus.stirkaparus.presentation.reports

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.stirkaparus.stirkaparus.presentation.reports.components.ReportsContent
import com.stirkaparus.stirkaparus.presentation.reports.components.ReportsTopBar

const val TAG ="ReportsScreen"

@Composable
fun ReportsScreen(
    navController: NavController,
    bottomPadding: PaddingValues,
) {

     Scaffold(
        topBar = {
            ReportsTopBar()
        },
        content = { padding ->
            ReportsContent(
                padding, bottomPadding
            )

        }
    )
}
