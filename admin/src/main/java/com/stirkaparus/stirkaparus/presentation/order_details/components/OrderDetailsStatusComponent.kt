package com.stirkaparus.stirkaparus.presentation.order_details.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stirkaparus.stirkaparus.presentation.orders_list_screen.components.StatusData
import com.stirkaparus.stirkaparus.ui.theme.OrderDetailsDescriptionColor
import com.stirkaparus.stirkaparus.ui.theme.PhoneNumberBoxBackground
import com.stirkaparus.stirkaparus.ui.theme.Teal200

@Composable
fun OrderDetailsStatusComponent(
    status: StatusData,
    text: String = "Text",
    description: String = "description",
    textColor: Color = Color.Black,
    divider: Boolean = false,
    icon: ImageVector? = null,
    iconDesc: String = "",
    secondIconDesc: String = "",
    paddingStart: Dp = 6.dp,
    onClock:()->Unit

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
                    contentDescription = iconDesc
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
            modifier = Modifier
                .padding(start = 4.dp)
                .padding(end = 4.dp)
                .clickable { onClock() }
        ) {

            Box(
                modifier = Modifier
                    .background(PhoneNumberBoxBackground)

                    .border(
                        1.dp, Teal200, shape = RoundedCornerShape(size = 5.dp)
                    )
                    .clip(shape = RoundedCornerShape(size = 5.dp))
                    .height(25.dp),
                Alignment.CenterStart
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {

                    Icon(
                        modifier = Modifier
                            .padding(start = 10.dp)
                            .padding(end = 6.dp)
                            .size(15.dp),
                        imageVector = Icons.Default.Circle,
                        contentDescription = "status circle",
                        tint = status.color!!

                    )

                    Text(
                        modifier = Modifier.padding(end = 10.dp),
                        text = status.text.toString()
                    )
                }
            }
        }


    }
}



