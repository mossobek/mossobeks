package com.stirkaparus.driver.presentation.report

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.stirkaparus.driver.common.Constants.REPORT_SCREEN_TOP_BAR_TEXT
import com.stirkaparus.driver.presentation.report.components.ReportsContent
import com.stirkaparus.driver.presentation.report.components.ReportsTopBar
import com.stirkaparus.driver.ui.theme.BackgroundColor
import es.dmoral.toasty.Toasty

const val TAG = "ReportsScreen"

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ReportsScreen(
    navigateToProfileScreen: () -> Unit,
    navController: NavHostController,
    paddingValue: PaddingValues
) {
    val context = LocalContext.current
    Scaffold(
        topBar = {
            ReportsTopBar(
                navigateToProfileScreen = navigateToProfileScreen
            )
        },
        content = {
            ReportsContent(
                errorMessage = {
                    Toasty.error(context, it, Toast.LENGTH_SHORT, true).show()
                }
            )
        })
}


