package com.stirkaparus.stirkaparus.presentation.components

 import android.util.Log
 import androidx.compose.runtime.Composable
 import androidx.compose.runtime.LaunchedEffect
 import androidx.hilt.navigation.compose.hiltViewModel
 import com.stirkaparus.model.Response
 import com.stirkaparus.stirkaparus.MainViewModel
 import com.stirkaparus.stirkaparus.MainViewModel.Companion.TAG
 import com.stirkaparus.stirkaparus.common.Constants.VERSION

@Composable
fun VControl(
    navToUpdateScreen: () -> Unit,
    viewModel: MainViewModel = hiltViewModel()
) {
    when (val versionControlResponse = viewModel.versionControlResponse) {
        is Response.Loading -> Unit
        is Response.Success -> {
            Log.e(TAG, "VControl: ${versionControlResponse.data}")
            val version = versionControlResponse.data?.admin
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