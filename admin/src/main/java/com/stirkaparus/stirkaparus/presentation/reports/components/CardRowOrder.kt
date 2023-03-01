package com.stirkaparus.stirkaparus.presentation.reports.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stirkaparus.stirkaparus.ui.theme.CardDescColor

@Composable
fun CardRowOrder(desc: String, text: String) {
    Row(
        modifier = Modifier
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(Modifier.weight(1f)) {
            Text(
                text = desc, color = CardDescColor, fontSize = 14.sp
            )

        }
        Row(
            Modifier.weight(2f), horizontalArrangement = Arrangement.End
        ) {
            Column(horizontalAlignment = Alignment.End) {


                Text(

                    //modifier = Modifier.padding(vertical = 14.dp),
                    text = text,
                    color = Color.Black,
                    fontSize = 16.sp,
                    maxLines = 1,
                    textAlign = TextAlign.End,
                    overflow = TextOverflow.Ellipsis
                )


            }

        }
    }
}