package com.stirkaparus.stirkaparus.screens.order_details_screen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.stirkaparus.stirkaparus.model.Order
import com.stirkaparus.stirkaparus.screens.orders_list_screen.OrderDetailsScreen1
import com.stirkaparus.stirkaparus.ui.theme.OrderDetailsDescriptionColor

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun OrderDetailsScreen(navController: NavController) {
    val id = navController.currentBackStackEntry?.arguments?.getString("id")
    val viewModel: OrderDetailsScreenViewModel = OrderDetailsScreenViewModel()
    val orderResource by viewModel.getOrder(id.toString())
        .collectAsState(initial = Resource.loading(null))
    val order = orderResource.data ?: Order()
    Scaffold(
        modifier = Modifier.fillMaxSize(), backgroundColor = Color.LightGray
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            OrderDetailsTopBarComponent(
                navController,id.toString()
            )

            Spacer(modifier = Modifier.height(16.dp))
            OrderDetailsTextComponent(
                description = "Телефон",
                text = order.phone.toString(),
                icon = Icons.Default.Phone
            )
            Spacer(modifier = Modifier.height(2.dp))
            OrderDetailsTextComponent(
                description = "Адрес",
                text = order.address.toString(),
                icon = Icons.Default.LocationOn
            )
            Spacer(modifier = Modifier.height(2.dp))
            OrderDetailsTextComponent(
                description = "Комментарий",
                text = order.comment.toString(),
                icon = Icons.Default.Comment
            )
            Spacer(modifier = Modifier.height(2.dp))
            OrderDetailsTextComponent(
                description = "Статус",
                text = order.status.toString(),
                icon = Icons.Default.CheckCircle
            )
            Spacer(modifier = Modifier.height(2.dp))
            OrderDetailsTextComponent(
                description = "Количество",
                text = order.count.toString(),
                icon = Icons.Default.AutoAwesomeMotion,
                secondIcon = Icons.Default.AddBox
            )
            Spacer(modifier = Modifier.height(2.dp))
            OrderDetailsTextComponent(
                description = "Сумма",
                text = order.total.toString(),
                icon = Icons.Default.Money
            )
            Spacer(modifier = Modifier.height(20.dp))
            OrderDetailsTextComponent(
                description = "Создан",
                text = "12.1.2023",
                paddingStart = 16.dp
            )
            Spacer(modifier = Modifier.height(2.dp))
            OrderDetailsTextComponent(
                description = "Забран",
                text = "--:--",
                paddingStart = 16.dp
            )
            Spacer(modifier = Modifier.height(2.dp))
            OrderDetailsTextComponent(
                description = "Постиран",
                text = "--:--",
                paddingStart = 16.dp
            )
            Spacer(modifier = Modifier.height(2.dp))
            OrderDetailsTextComponent(
                description = "Доставлен",
                text = "--:--",
                paddingStart = 16.dp
            )
            Spacer(modifier = Modifier.height(2.dp))
        }
    }
    //SetDetails(order = orderDetails)

}


@Composable
fun OrderDetailsTopBarComponent(navController: NavController,id:String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(Color.White),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier

        ) {
            IconButton(
                onClick = { navController.popBackStack() }
            ) {
                Icon(
                    imageVector = Icons.Default.Close, contentDescription = "Close icon button"
                )
            }
        }
        Row(
            modifier = Modifier
        ) {
            Text(
                modifier = Modifier, style = MaterialTheme.typography.h6, text = "Детали заказа"
            )
        }
        Row(
            modifier = Modifier
        ) {
            IconButton(
                modifier = Modifier
                    .padding(end = 0.dp),
                onClick = {
                    navController.navigate(OrderDetailsScreen1.Edit1.route+"/${id}")
                }) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Delete icon button"
                )
            }
            IconButton(modifier = Modifier.padding(end = 8.dp), onClick = { /*TODO*/ }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete icon button"
                )
            }
        }
    }
}

@Preview
@Composable
fun OrderDetailsTextComponent(
    text: String = "Text",
    description: String = "description",
    textColor: Color = Color.Black,
    divider: Boolean = false,
    icon: ImageVector? = null,
    secondIcon: ImageVector? = null,
    iconDescr: String = "",
    secondIconDescr: String = "",
    paddingStart: Dp = 6.dp
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(35.dp)
            .background(Color.White),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (icon != null) {
                Icon(
                    modifier = Modifier
                        .size(20.dp)
                        .padding(start = 6.dp),
                    imageVector = icon,
                    tint = Color.DarkGray,
                    contentDescription = iconDescr
                )
            }
            Text(
                modifier = Modifier
                    .padding(start = paddingStart)
                    .padding(end = 16.dp),
                text = description,
                color = OrderDetailsDescriptionColor,
                fontSize = 18.sp
            )
        }

        Row(
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.padding(end = 16.dp),
                text = text,
                color = textColor,
                fontSize = 18.sp,
                maxLines = 1,
                textAlign = TextAlign.End,
                overflow = TextOverflow.Ellipsis

            )
            if (secondIcon != null) {
                IconButton(modifier = Modifier
                    //.padding(start = 10.dp)
                    .padding(end = 10.dp),
                    onClick = { /*TODO*/ }) {

                    Icon(
                        modifier = Modifier
                            .size(26.dp),
                        imageVector = secondIcon,
                        tint = Color.Black,
                        contentDescription = secondIconDescr
                    )
                }
            }
        }


    }
}




