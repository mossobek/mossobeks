package com.stirkaparus.stirkaparus.presentation.reports.components

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.stirkaparus.model.User
import com.stirkaparus.stirkaparus.presentation.reports.ReportsViewModel


@Composable
fun ReportsContent(
    padding: PaddingValues,
    bottomPadding: PaddingValues,
    viewModel: ReportsViewModel = hiltViewModel(),
) {
    var users by remember {
        mutableStateOf<List<User>>(emptyList())
    }


    LaunchedEffect(Unit) {

        viewModel.getReportsOrders()

    }

    ReportsOrder { orders ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            viewModel.getMutableDriversList()

            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(padding)
            ) {

                val drivers by viewModel.drivers.observeAsState(initial = emptyList())

                SpecimenSpinner(drivers = drivers)



                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    contentPadding = PaddingValues(8.dp)
                ) {
                    items(orders) { order ->
                        ReportItemCard(
                            item = order
                        )

                    }
                }
            }
        }
    }

}

@Composable
fun SpecimenSpinner(drivers: List<User>, viewModel: ReportsViewModel = hiltViewModel()) {
    val TAG = "SpecimenSpinner"
    var driverText by remember { mutableStateOf("Выберите отчеты") }
    var expanded by remember { mutableStateOf(false) }
    var selectedPlant: User? = null
    var inPlantName: String = ""

    Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Row(Modifier
            .padding(24.dp)
            .clickable {
                expanded = !expanded
            }
            .padding(8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = driverText, fontSize = 18.sp, modifier = Modifier.padding(end = 8.dp))
            Icon(imageVector = Icons.Filled.ArrowDropDown, contentDescription = "")
            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                drivers.forEach { driver ->
                    DropdownMenuItem(onClick = {
                        expanded = false

                        if (driver.name == "ALL ORDERS") {
                            // we have a new driver
                            driverText = "ALL ORDERS"
                            driver.name = "ALL ORDERS"
                            selectedPlant = User(name = "ALL ORDERS")

                        } else {
                            // we have selected an existing driver.
                            driverText = driver.name.toString()
                            selectedPlant = User(name = driver.name)
                            inPlantName = driver.name!!
                        }
                        viewModel.selectedSpecimen
                        Log.e(TAG, "selectedSpecimen: $selectedPlant")
                    }) {
                        Text(text = driver.name.toString())
                    }
                }
            }
        }
    }
}