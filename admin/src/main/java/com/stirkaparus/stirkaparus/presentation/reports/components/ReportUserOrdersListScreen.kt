package com.stirkaparus.stirkaparus.presentation.reports.components

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment.Companion.Rectangle
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.stirkaparus.model.Order
import com.stirkaparus.model.Response
import com.stirkaparus.stirkaparus.common.Constants.ADDRESS_RU
import com.stirkaparus.stirkaparus.common.Constants.COMMENT_RU
import com.stirkaparus.stirkaparus.common.Constants.PHONE_RU
import com.stirkaparus.stirkaparus.common.StatusImp
import com.stirkaparus.stirkaparus.common.getAgoTime
import com.stirkaparus.stirkaparus.presentation.ProgressDialog
import com.stirkaparus.stirkaparus.presentation.reports.ReportsViewModel
import com.stirkaparus.stirkaparus.ui.theme.*
import es.dmoral.toasty.Toasty

@Composable
fun ReportUserOrdersListScreen(
    viewModel: ReportsViewModel = hiltViewModel(),
    id: String,
    navBack: () -> Unit,
    navigateToOrderScreen: (orderId: String) -> Unit,
) {

    Scaffold(topBar = {
        ReportUserOrdersTopBar(navBack)
    }, content = { padding ->
        viewModel.reportUserOrdersList(id)
        when (val reportUserOrdersListResponse = viewModel.reportUserOrdersListResponse) {
            is Response.Loading -> ProgressDialog()
            is Response.Success -> {
                ReportUserOrdersListContent(id = id,
                    padding = padding,
                    userOrderList = reportUserOrdersListResponse.data!!,
                    navigateToOrderScreen = { orderid ->
                        navigateToOrderScreen(orderid)
                    })
            }
            is Response.Failure -> Toasty.error(
                LocalContext.current,
                reportUserOrdersListResponse.e?.message.toString(),
                Toast.LENGTH_SHORT,
                true
            ).show()
        }
    })
}

@Composable
fun ReportUserOrdersListContent(
    padding: PaddingValues,
    id: String,
    userOrderList: List<Order>,
    navigateToOrderScreen: (orderId: String) -> Unit
) {

    Column(
        Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(8.dp)
    ) {
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
                items = userOrderList
            ) { order ->
                ReportUserOrderItemCard(order = order, onClick = {
                    //navigateToUserOrdersScreen(user.id.toString())
                })
            }
        }
    }
}

@Preview(
    showBackground = true
)

@Composable
fun ReportUserOrderItemCardPrev() {

    ReportUserOrderItemCard(Order(
        phone = "89285222253",
        address = "Хасавюрт Хизроева 58. Вторые ворота",
        comment = "Надо срочно постирать"
    ), onClick = {})
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ReportUserOrderItemCard(
    order: Order, onClick: () -> Unit
) {

    Card(
        shape = RoundedCornerShape(6.dp), modifier = Modifier
            .fillMaxWidth()
            .padding(
                bottom = 4.dp
            )
            .border(
                width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(size = 6.dp)
            ), elevation = 3.dp, onClick = onClick
    ) {
        Box(
            modifier = Modifier.fillMaxWidth()

        ) {


                Column(
                    Modifier

                        .padding(horizontal = 16.dp)
                        .padding(top = 6.dp)
                ) {
                    CardRowOrder(desc = PHONE_RU, text = order.phone.toString())
                    Divider()
                    CardRowOrder(desc = ADDRESS_RU, text = order.address.toString())
                    Divider()
                    CardRowOrder(desc = COMMENT_RU, text = order.comment.toString())
                    Divider()
                    CardRowOrder(desc = PHONE_RU, text = order.phone.toString())
                    Divider()
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        //status
                        RowWithIconStatus(
                            order = order,
                        )
//                    //Time
//                    RowWithIcon(
//                        textColor = Blue10,
//                        text = StatusImp().textStatusTime(order),
//                        icon = Icons.Filled.AccessTime,
//                        iconColor = Blue9,
//                        boxColor = Blue8
//                    )
                        //time ago
                        RowWithIcon(
                            textColor = Blue3,
                            text = getAgoTime(order.created_time),
                            icon = Icons.Filled.Timer,
                            iconColor = Blue4,
                            boxColor = Blue2
                        )

                        //total
                        RowWithIcon(
                            textColor = Blue5,
                            text = order.total.toString(),
                            icon = Icons.Filled.MonetizationOn,
                            iconColor = Blue5,
                            boxColor = Blue6
                        )
                        //count
                        RowWithIcon(
                            textColor = Blue11,
                            text = order.count.toString(),
                            icon = Icons.Filled.Discount,
                            iconColor = Blue12,
                            boxColor = Blue13
                        )

                    }
                }

            }
        }

    }


@Composable
fun RowWithIcon(
    modifier: Modifier = Modifier,
    text: String = "",
    textColor: Color = Color.White,
    icon: ImageVector,
    iconColor: Color,
    boxColor: Color,
    border: Dp = 0.dp
) {

    Box(
        modifier = modifier
            .padding(2.dp)
            .clip(shape = RoundedCornerShape(2.dp))
            .background(color = boxColor)
    ) {
        Row(
            Modifier
                .padding(2.dp)
                .padding(end = 2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {


            Icon(
                modifier = Modifier.size(16.dp),
                tint = iconColor,
                imageVector = icon,
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(3.dp))
            Text(text = text, color = textColor)
        }
    }

}

@Composable
fun RowWithIconStatus(
    order: Order,

    ) {
    val status = StatusImp()
    Box(
        modifier = Modifier
            .border(
                width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(2.dp)
            )
            .padding(2.dp)
            .clip(shape = RoundedCornerShape(2.dp))
            .background(color = Color.White)
    ) {
        Row(
            Modifier
                .padding(2.dp)
                .padding(end = 2.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {


            Icon(
                modifier = Modifier.size(14.dp),
                tint = status.colorStatus(order = order),
                imageVector = Icons.Default.Circle,
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(3.dp))
            Text(fontSize = 12.sp, color = Color.Gray, text = status.textStatus(order))
        }
    }
}


