package com.stirkaparus.stirkaparus.presentation.allReportsList

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.stirkaparus.model.Report
import com.stirkaparus.model.Response
import com.stirkaparus.stirkaparus.presentation.ProgressDialog
import com.stirkaparus.stirkaparus.presentation.allReportsList.components.AllReportItemCard
import com.stirkaparus.stirkaparus.presentation.components.BackIcon
import es.dmoral.toasty.Toasty

@Composable
fun AllReportsListScreen(
    viewModel: AllReportsListViewModel = hiltViewModel(),
    navBack: () -> Unit,
) {
    Scaffold(
        topBar = {
            AllReportsTopBar(navBack = navBack)
        },
        content = { paddingValues ->

            viewModel.getAllReportsList()
            when (val allReportsListResponse = viewModel.allReportsListResponse) {
                is Response.Loading -> ProgressDialog()
                is Response.Success -> {
                    AllReportsListContent(
                        padding = paddingValues,
                        reportsList = allReportsListResponse.data!!
//                        navigateToUserOrdersScreen = { userId, username ->
//                            navigateToUserOrdersScreen(userId, username)
//                        }
                    )
                }
                is Response.Failure -> Toasty.error(
                    LocalContext.current,
                    allReportsListResponse.e?.message.toString(),
                    Toast.LENGTH_SHORT,
                    true
                ).show()
            }


        }
    )
}

@Composable
fun AllReportsTopBar(navBack: () -> Unit) {
    TopAppBar(
        title = {
            Text(text = "Все отчеты")
        },
        navigationIcon = {
            IconButton(onClick = { /*TODO*/ }) {
                BackIcon {
                    navBack()
                }
            }
        }
    )
}

@Composable
fun AllReportsListContent(
    padding: PaddingValues,
    reportsList: List<Report>,
) {

    Column(Modifier.fillMaxSize()) {

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            items(
                items = reportsList
            ) { report ->
                AllReportItemCard(
                    report = report,
                    onClick = {
                        //navigateToUserOrdersScreen(user.id.toString(),user.name.toString())
                    }
                )
                Spacer(modifier = Modifier.height(11.dp))
            }
        }
    }

}