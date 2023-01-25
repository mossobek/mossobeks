package com.stirkaparus.stirkaparus.screens.order_details_screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stirkaparus.stirkaparus.ui.theme.OrderDetailsDescriptionColor


@Composable
fun CustomTextFieldRow(
    text: String = "Text",
    description: String = "description",
    textColor: Color = Color.Black,
    icon: ImageVector? = null,
    secondIcon: ImageVector? = null,
    iconDesc: String = "",
    secondIconDesc: String = "",
    paddingStart: Dp = 6.dp,
    onClick: () -> Unit? = {},
    clickable:Boolean = false
) {
    Row(
        modifier = Modifier
            .clickable(
                enabled = clickable,
                onClick = { onClick() }
            )

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
                        contentDescription = secondIconDesc
                    )
                }
            }
        }


    }
}
