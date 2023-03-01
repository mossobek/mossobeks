package com.stirkaparus.stirkaparus.presentation.reports.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.stirkaparus.model.User
import com.stirkaparus.stirkaparus.presentation.reports.ReportsViewModel

const val TAG = "ReportsOrderListContent"

@Composable
fun ReportsUserListContent(
    padding: PaddingValues,
    userList: List<User>,
    navigateToUserOrdersScreen: (user: String) -> Unit,
) {

    Column(Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.Center
        ) {

            Text(textAlign = TextAlign.Center, fontSize = 20.sp, text = "Выберите отчет:")
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            items(
                items = userList
            ) { user ->
                ReportItemCard(
                    user = user,
                    onClick = {
                        navigateToUserOrdersScreen(user.id.toString())
                    }
                )
            }
        }
    }
}