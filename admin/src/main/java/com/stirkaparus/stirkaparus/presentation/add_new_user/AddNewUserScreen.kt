package com.stirkaparus.stirkaparus.presentation.add_new_user

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.stirkaparus.stirkaparus.common.Constants.TAG
import com.stirkaparus.stirkaparus.presentation.add_new_user.components.AddNewUserContent
import com.stirkaparus.stirkaparus.presentation.add_new_user.components.AddUser
import es.dmoral.toasty.Toasty

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddNewUserScreen(
    navBack: () -> Unit,
    navController: NavController,
    viewModel: AddNewUsrViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    Scaffold(topBar = {
        AddUserTopBar()
    }, content = {
        AddNewUserContent(addUser = { name, phone, id, selected ->
            Log.e(TAG, "AddNewUserScreen: $name")
            viewModel.addNewUser(
                name = name, phone = phone, id = id, selected = selected

            )
        })
    })

    AddUser(navBack = navBack, showErrorMessage = { errorMessage ->
        Toasty.error(context, errorMessage.toString(), Toast.LENGTH_SHORT, true).show();
    })

}

