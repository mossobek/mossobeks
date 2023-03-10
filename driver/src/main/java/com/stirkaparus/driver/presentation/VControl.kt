package com.stirkaparus.driver.presentation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.stirkaparus.driver.common.Constants.VERSION
import com.stirkaparus.model.Response

@Composable
fun VControl(
    navToUpdateScreen: () -> Unit,
    viewModel: MainViewModel = hiltViewModel()
) {
    when (val versionControlResponse = viewModel.versionControlResponse) {
        is Response.Loading -> Unit
        is Response.Success -> {

            Log.e(TAG, "VControl: ${versionControlResponse.data}")

            val version = versionControlResponse.data?.driver
            if (version != null) {
                if (version!=VERSION){
                    LaunchedEffect(Unit){
                        navToUpdateScreen()
                    }
                }
            }
        }
        is Response.Failure -> Unit
    }
}