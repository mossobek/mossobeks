package com.stirkaparus.stirkaparus.presentation.reports.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.Discount
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.Timer
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stirkaparus.model.Order
import com.stirkaparus.stirkaparus.common.Constants
import com.stirkaparus.stirkaparus.common.StatusImp
import com.stirkaparus.stirkaparus.common.getAgoTime
import com.stirkaparus.stirkaparus.ui.theme.*


@Preview(
    showBackground = true
)

@Composable
fun ReportUserOrderItemCardPrev() {

    ReportUserOrderItemCard(
        Order(
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
            modifier = Modifier
                .fillMaxWidth()
                .background(StatusImp().colorStatus(order))
                .padding(start = 10.dp)


        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
            ) {


                Column(
                    Modifier

                        .padding(horizontal = 16.dp)
                        .padding(top = 6.dp)
                ) {
                    CardRowOrder(desc = Constants.PHONE_RU, text = order.phone.toString())
                    Divider()
                    CardRowOrder(desc = Constants.ADDRESS_RU, text = order.address.toString())
                    Divider()
                    CardRowOrder(desc = Constants.COMMENT_RU, text = order.comment.toString())
                    Divider()
                    CardRowOrder(desc = Constants.PHONE_RU, text = order.phone.toString())
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

