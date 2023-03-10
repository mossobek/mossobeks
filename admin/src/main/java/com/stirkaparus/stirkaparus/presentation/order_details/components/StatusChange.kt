package com.stirkaparus.stirkaparus.presentation.order_details.components

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lens
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.stirkaparus.model.Order
import com.stirkaparus.stirkaparus.common.Constants.CREATED
import com.stirkaparus.stirkaparus.common.Constants.CREATED_RU
import com.stirkaparus.stirkaparus.common.Constants.DELIVERED
import com.stirkaparus.stirkaparus.common.Constants.DELIVERED_RU
import com.stirkaparus.stirkaparus.common.Constants.FINISHED
import com.stirkaparus.stirkaparus.common.Constants.FINISHED_RU
import com.stirkaparus.stirkaparus.common.Constants.TAKEN
import com.stirkaparus.stirkaparus.common.Constants.TAKEN_RU
import com.stirkaparus.stirkaparus.common.Constants.WASHED
import com.stirkaparus.stirkaparus.common.Constants.WASHED_RU
import com.stirkaparus.stirkaparus.common.StatusImp
import com.stirkaparus.stirkaparus.presentation.components.SmallSpacer
import com.stirkaparus.stirkaparus.presentation.order_details.OrderDetailsViewModel
import com.stirkaparus.stirkaparus.ui.theme.*
import es.dmoral.toasty.Toasty

const val TAG = "StatusChange"

@Composable
fun StatusChange(
    order: Order,
    viewModel: OrderDetailsViewModel = hiltViewModel(),
    id: String
) {
    Column(
        modifier = Modifier.padding(16.dp)
    ) {

        Column(
            modifier = Modifier
                .background(Color.White)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = "Выберите статус",
                style = MaterialTheme.typography.subtitle1,
            )
            SmallSpacer()
            Divider()
            TextCustomStatus(
                statusColor = BlueStatusColor,
                statusText = CREATED_RU,
                onClick = {
                    viewModel.changeStatus(id = id, status = CREATED)
                })
            SmallSpacer()
            Divider()
            TextCustomStatus(
                statusColor = PurpleStatusColor,
                statusText = TAKEN_RU,
                onClick = {
                    viewModel.closeBottomSheet()
                    viewModel.openTakeDialog()
                })
            SmallSpacer()
            Divider()

            TextCustomStatus(
                statusColor = OrangeStatusColor,
                statusText = WASHED_RU,

                onClick = {
                    viewModel.changeStatus(id = id, status = WASHED)
                })
            SmallSpacer()
            Divider()
            TextCustomStatus(
                statusColor = GreenStatusColor,
                statusText = FINISHED_RU,
                //  currentSt,
                onClick = {
                    //onClick(Status.Finished)
                })

            SmallSpacer()
            Divider()
            TextCustomStatus(
                statusColor = GrayStatusColor,
                statusText = DELIVERED_RU,
                onClick = {
                         viewModel.openDeliveredDialog()

                })
            SmallSpacer()
            Divider()
        }
    }
}


