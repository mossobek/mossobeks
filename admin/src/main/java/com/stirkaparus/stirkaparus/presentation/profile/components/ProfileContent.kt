package com.stirkaparus.stirkaparus.presentation.profile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.stirkaparus.model.Response
import com.stirkaparus.stirkaparus.domain.repository.UserItems
import com.stirkaparus.stirkaparus.domain.repository.UsersListResponse
import com.stirkaparus.stirkaparus.presentation.components.ProgressDialog
import com.stirkaparus.stirkaparus.presentation.components.SmallSpacer
import com.stirkaparus.stirkaparus.presentation.profile.ProfileViewModel

@Composable
fun ProfileContent(
    padding: PaddingValues,
    paddingBottom: PaddingValues,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val user = viewModel.user()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = paddingBottom.calculateBottomPadding())
    ) {
        LaunchedEffect(Unit) {
            viewModel.getUsersInCompany()
        }

        SmallSpacer()
        ProfileCustomText(description = "Имя", text = user.name.toString())
        SmallSpacer()
        ProfileCustomText(description = "Телефон", text = user.phone.toString())
        SmallSpacer()
        ProfileCustomText(description = "Город", text = user.city.toString())
        SmallSpacer()
        ProfileCustomText(description = "Название", text = user.company_name.toString())
        SmallSpacer()
        SmallSpacer()
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = "Ваши сотрудники:"
        )
        UsersList { users ->
            if (users.isEmpty()) {
                Text(
                    text = "EMPTY_CART_MESSAGE"
                )
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                contentPadding = PaddingValues(8.dp)
            ) {
                items(users) { user ->
                    CompanyUserItem(
                        user = user,
 )
                    SmallSpacer()

                }
            }
        }
    }

}

@Composable
fun UsersList(
    viewModel: ProfileViewModel = hiltViewModel(),
    shoppingCartContent: @Composable (items: UserItems) -> Unit
) {
    when (val usersListResponse = viewModel.usersListResponse) {
        is Response.Loading -> ProgressDialog()
        is Response.Success -> usersListResponse.data?.let { items ->
            shoppingCartContent(items)
        }
        is Response.Failure -> LaunchedEffect(Unit) {
            print(usersListResponse.e)
        }
    }

}

@Composable
fun ProfileCustomText(description: String, text: String) {
    Row(
        Modifier
            .fillMaxWidth()
            .background(Color.LightGray)
            .padding(start = 16.dp, end = 16.dp)
            .height(40.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(modifier = Modifier.weight(1f), text = description)
        Text(modifier = Modifier.weight(1f), textAlign = TextAlign.End, text = text)
    }
}