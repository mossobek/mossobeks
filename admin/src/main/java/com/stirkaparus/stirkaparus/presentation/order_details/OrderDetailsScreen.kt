package com.stirkaparus.stirkaparus.presentation.order_details

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.stirkaparus.stirkaparus.presentation.order_details.components.*
import es.dmoral.toasty.Toasty

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun OrderDetailsScreen(
    id: String,
    navController: NavController,
    navBack: () -> Unit,
    navToCarpets: () -> Unit,
    editOOrder: () -> Unit
) {


    Scaffold(topBar = {
        OrderDetailsTopBar(
            editOrder = editOOrder,
            navBack = navBack,
            deleteOrder = {
                //TODO
            }
        )
    }, content = {
        OrderDetailsContent(
            id = id,
            navToCarpets = {
                navToCarpets()
            }
        )
    }
    )

    DeliveredStatus(id)

}


