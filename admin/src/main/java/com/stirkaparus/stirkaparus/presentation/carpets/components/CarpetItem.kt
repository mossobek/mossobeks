package com.stirkaparus.stirkaparus.presentation.carpets.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stirkaparus.model.Carpet

@Composable
fun CarpetItem(
    carpet: Carpet,
    modifier: Modifier = Modifier,
    onDeleteItemMenuClick: () -> Unit,
    onClick: () -> Unit,

    ) {
     //val status = setStatus(order.status.toString(), order)
    Box(
        modifier = modifier
            .padding(4.dp)

            .border(
                width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(size = 12.dp)
            )
            .clip(shape = RoundedCornerShape(size = 12.dp))
            .background(Color.White)

    ) {

        Column(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize()
                .padding(16.dp)

        ) {

            Row(
                modifier = Modifier.fillMaxWidth().padding(4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {

                Row(
                    modifier = Modifier, verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        modifier = Modifier.padding(start = 4.dp),
                        text = "№ ",
                        maxLines = 2,
                        fontSize = 16.sp
                    )

                    Text(
                        modifier = Modifier.padding(start = 4.dp),
                        text = carpet.serial.toString(),
                        maxLines = 2,
                        fontSize = 16.sp
                    )

                }

                Row(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    IconButton(
                        modifier = Modifier
                            .size(20.dp)
                            .padding(0.dp),
                        onClick = onDeleteItemMenuClick,
                    ) {
                        Icon(

                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete order",
                            tint = MaterialTheme.colors.onSurface
                        )
                    }
                }

            }
            Spacer(
                modifier = Modifier.fillMaxWidth()
                    .background(Color.LightGray)
                    .height(2.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth().padding(4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {

                Row(
                    modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(imageVector = Icons.Default.Expand, contentDescription = "side a")

                    Text(
                        modifier = Modifier.padding(start = 4.dp),
                        text = carpet.sideA.toString(),
                        maxLines = 2,
                        fontSize = 16.sp
                    )

                }

                Row(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        modifier = Modifier.rotate(90f),
                        imageVector = Icons.Default.Expand,
                        contentDescription = "side a"
                    )
                    Text(
                        modifier = Modifier.padding(start = 4.dp),
                        text = carpet.sideB.toString(),
                        maxLines = 2,
                        fontSize = 16.sp
                    )


                }

            }
            Spacer(
                modifier = Modifier.fillMaxWidth()
                    .background(Color.LightGray)
                    .height(2.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth().padding(4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {

                Row(
                    modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(imageVector = Icons.Default.AttachMoney, contentDescription = "AttachMoney")

                    Text(
                        modifier = Modifier.padding(start = 4.dp),
                        text = carpet.sum.toString(),
                        maxLines = 2,
                        fontSize = 16.sp
                    )

                }
                Spacer(
                    modifier = Modifier
                        .background(Color.LightGray)
                        .height(2.dp)
                )
                Row(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        modifier = Modifier,
                        imageVector = Icons.Default.AspectRatio,
                        contentDescription = "side a"
                    )
                    Text(
                        modifier = Modifier.padding(start = 4.dp),
                        text = carpet.square.toString(),
                        maxLines = 2,
                        fontSize = 16.sp
                    )


                }

            }
        }


    }
}