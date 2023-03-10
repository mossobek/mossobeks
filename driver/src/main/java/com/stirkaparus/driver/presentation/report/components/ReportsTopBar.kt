package com.stirkaparus.driver.presentation.report.components

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.stirkaparus.driver.common.Constants


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReportsTopBar(
    navigateToProfileScreen: () -> Unit
) {
    CenterAlignedTopAppBar(
        title = { Text(text = Constants.REPORT_SCREEN_TOP_BAR_TEXT) },
        actions = {
            IconButton(onClick = {

            }) {

                Icon(imageVector = Icons.Outlined.Search, contentDescription = null)

            }
            IconButton(onClick = {
                navigateToProfileScreen ()

            }) {
                Icon(imageVector = Icons.Outlined.Person, contentDescription = null)

            }
        }, colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.White,
//            scrolledContainerColor: Color,
//            navigationIconContentColor: Color,
//            titleContentColor: Color,
//            actionIconContentColor: Color
        )
    )

}

