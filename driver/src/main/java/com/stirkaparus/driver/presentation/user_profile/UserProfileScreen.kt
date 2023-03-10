package com.stirkaparus.driver.presentation.user_profile

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.stirkaparus.driver.presentation.components.BackIcon
import com.stirkaparus.driver.presentation.components.SmallSpacer
import com.stirkaparus.driver.presentation.user_details.UserDetailsViewModel
import com.stirkaparus.driver.ui.theme.BackgroundColor
import com.stirkaparus.model.User

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun UserProfileScreen(navBack: () -> Unit, viewModel: UserDetailsViewModel = hiltViewModel()) {
    Scaffold(
        topBar = {
            ProfileTopBar(navBack = navBack)
        },
        content = {
            UserProfileContent(viewModel.getUserData())
        }
    )
}

@Composable
fun UserProfileContent(user: User) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)

    ) {
        Divider()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BackgroundColor)
                .padding(16.dp)
        ) {
            TextRow(desc = "имя", user.name.toString())
            SmallSpacer()
            TextRow(desc = "город", user.city.toString())
            SmallSpacer()
            TextRow(desc = "компания", user.company_name.toString())
            SmallSpacer()
            TextRow(desc = "телефон", user.phone.toString())
            SmallSpacer()
            TextRow(desc = "email", user.email.toString())
            SmallSpacer()
        }


    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileTopBar(navBack: () -> Unit) {
    CenterAlignedTopAppBar(
        title = { Text(text = "Профиль") },
        actions = {


        }, colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.White,
//            scrolledContainerColor: Color,
//            navigationIconContentColor: Color,
//            titleContentColor: Color,
//            actionIconContentColor: Color
        ),
        navigationIcon = {
            BackIcon {
                navBack()
            }
        }
    )

}


@Composable
private fun TextRow(desc: String, text: String) {
    Row(Modifier.fillMaxWidth()) {

        Column() {


            Text(modifier = Modifier.padding(start = 6.dp), text = desc, color = Color.Gray)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(35.dp)
                    .clip(
                        shape = RoundedCornerShape(3.dp)
                    )
                    .background(Color.LightGray),


                ) {
                Row(
                    Modifier
                        .fillMaxSize()
                        .padding(start = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Text(text = text, fontSize = 20.sp)

                }
            }
        }
    }
}