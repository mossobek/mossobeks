package com.stirkaparus.driver.presentation.report

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.stirkaparus.driver.common.Constants.REPORT_SCREEN_TOP_BAR_TEXT

const val TAG = "ReportsScreen"

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ReportsScreen(
    navController: NavHostController,
    paddingValue: PaddingValues
) {
    Scaffold(
        topBar = {
            ReportsTopBar()
        },
        content = {

            Text(text = REPORT_SCREEN_TOP_BAR_TEXT)

        }
    )
}

@Composable
fun ReportsTopBar() {

    val context = LocalContext.current

    TopAppBar(title = {
        Text(text = REPORT_SCREEN_TOP_BAR_TEXT)
    },
        actions = {
            IconButton(onClick = {
                Log.e(TAG, "ReportsTopBar: setting button clicked")
                FirebaseAuth.getInstance().signOut()
                //SharedPrefsRepository(context).clear()
            }) {
                Icon(imageVector = Icons.Outlined.Settings, contentDescription = null)

            }
        }
    )
}
