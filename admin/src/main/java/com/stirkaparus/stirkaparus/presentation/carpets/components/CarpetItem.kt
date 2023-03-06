package com.stirkaparus.stirkaparus.presentation.carpets.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.stirkaparus.model.Carpet

@Composable
fun CarpetItem(
    carpet: Carpet,
    modifier: Modifier = Modifier ,
    onDeleteItemMenuClick: () -> Unit,
    onClick: () -> Unit,

    ) {

    Card(
        modifier = Modifier.fillMaxWidth() ,
        content = {
            Column(Modifier.fillMaxSize()) {
                Divider()


                CarpetRow(desc = "Ширина", text = carpet.sideA.toString())


                Divider()

            }
        })

}

@Composable
private fun CarpetRow(desc: String, text: String) {

    Row(modifier = Modifier.fillMaxWidth()) {
        Row(Modifier.weight(1f)) {
            Text(text = desc)
        }
        Row(Modifier.weight(1f)) {
            Text(text = text)
        }
    }

}
//    Box(
//        modifier = modifier
//            .padding(4.dp)
//            .border(
//                width = 1.dp,
//                color = Color.LightGray,
//                shape = RoundedCornerShape(size = 5.dp)
//            )
//            .clip(shape = RoundedCornerShape(size = 5.dp))
//            .background(Color.White),
//        content = {
//
//            Column(
//                modifier = Modifier
//                    .background(Color.White)
//                    .fillMaxSize()
//                    .padding(16.dp)
//
//            ) {
//
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(4.dp),
//                    horizontalArrangement = Arrangement.SpaceBetween,
//                    verticalAlignment = Alignment.Top
//                ) {
//
//                    Row(
//                        modifier = Modifier, verticalAlignment = Alignment.CenterVertically
//                    ) {
//
//                        Text(
//                            modifier = Modifier.padding(start = 4.dp),
//                            text = "№ ",
//                            maxLines = 2,
//                            fontSize = 16.sp
//                        )
//
//                        Text(
//                            modifier = Modifier.padding(start = 4.dp),
//                            text = carpet.serial.toString(),
//                            maxLines = 2,
//                            fontSize = 16.sp
//                        )
//
//                    }
//
//                    Row(
//                        modifier = Modifier.weight(1f),
//                        horizontalArrangement = Arrangement.End,
//                        verticalAlignment = Alignment.CenterVertically,
//                    ) {
//                        IconButton(
//                            modifier = Modifier
//                                .size(20.dp)
//                                .padding(0.dp),
//                            onClick = onDeleteItemMenuClick,
//                        ) {
//                            Icon(
//
//                                imageVector = Icons.Default.Delete,
//                                contentDescription = "Delete order",
//                                tint = MaterialTheme.colors.onSurface
//                            )
//                        }
//                    }
//
//                }
//                Spacer(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .background(Color.LightGray)
//                        .height(2.dp)
//                )
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(4.dp),
//                    horizontalArrangement = Arrangement.SpaceBetween,
//                    verticalAlignment = Alignment.Top
//                ) {
//
//                    Row(
//                        modifier = Modifier.weight(1f),
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//
//                        Icon(imageVector = Icons.Default.Expand, contentDescription = "side a")
//
//                        Text(
//                            modifier = Modifier.padding(start = 4.dp),
//                            text = carpet.sideA.toString(),
//                            maxLines = 2,
//                            fontSize = 16.sp
//                        )
//
//                    }
//
//                    Row(
//                        modifier = Modifier.weight(1f),
//                        horizontalArrangement = Arrangement.Start,
//                        verticalAlignment = Alignment.CenterVertically,
//                    ) {
//                        Icon(
//                            modifier = Modifier.rotate(90f),
//                            imageVector = Icons.Default.Expand,
//                            contentDescription = "side a"
//                        )
//                        Text(
//                            modifier = Modifier.padding(start = 4.dp),
//                            text = carpet.sideB.toString(),
//                            maxLines = 2,
//                            fontSize = 16.sp
//                        )
//
//
//                    }
//
//                }
//                Spacer(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .background(Color.LightGray)
//                        .height(2.dp)
//                )
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(4.dp),
//                    horizontalArrangement = Arrangement.SpaceBetween,
//                    verticalAlignment = Alignment.Top
//                ) {
//
//                    Row(
//                        modifier = Modifier.weight(1f),
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//
//                        Icon(
//                            imageVector = Icons.Default.AttachMoney,
//                            contentDescription = "AttachMoney"
//                        )
//
//                        Text(
//                            modifier = Modifier.padding(start = 4.dp),
//                            text = carpet.sum.toString(),
//                            maxLines = 2,
//                            fontSize = 16.sp
//                        )
//
//                    }
//                    Spacer(
//                        modifier = Modifier
//                            .background(Color.LightGray)
//                            .height(2.dp)
//                    )
//                    Row(
//                        modifier = Modifier.weight(1f),
//                        horizontalArrangement = Arrangement.Start,
//                        verticalAlignment = Alignment.CenterVertically,
//                    ) {
//                        Icon(
//                            modifier = Modifier,
//                            imageVector = Icons.Default.AspectRatio,
//                            contentDescription = "side a"
//                        )
//                        Text(
//                            modifier = Modifier.padding(start = 4.dp),
//                            text = carpet.square.toString(),
//                            maxLines = 2,
//                            fontSize = 16.sp
//                        )
//
//
//                    }
//
//                }
//            }
//
//
//        }
//    )
//}