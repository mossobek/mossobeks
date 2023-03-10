package com.stirkaparus.stirkaparus.presentation.allReportsList.components

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.stirkaparus.model.Report
import com.stirkaparus.stirkaparus.common.formatDate
import com.stirkaparus.stirkaparus.presentation.reports.components.TAG


@Composable
fun AllReportItemCard(
    report: Report,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(0.dp),
        modifier = Modifier.fillMaxWidth(),
        content = {
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(
                        start = 8.dp,
                        end = 8.dp,
                    )
            ) {

                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "№0000001", fontSize = 18.sp,
                            textAlign = TextAlign.End,
                            color = Color.Gray
                        )
                    }
                }
                Divider()
                CarpetRow(desc = "Создан", text = formatDate(report.reported_time))
                CarpetRow(desc = "Cумма", text = report.total.toString(), descEnd = " руб")
                CarpetRow(desc = "Количество", text = report.count.toString())
            }
        }
    )
}

@Composable
private fun CarpetRow(desc: String, text: String, divider: Boolean = true, descEnd: String = "") {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 2.dp)
    ) {
        Row(
            Modifier
                .weight(1f)
                .padding(vertical = 14.dp)
        ) {
            Text(fontSize = 18.sp, text = desc, color = Color.Gray)
        }
        Row(Modifier.weight(2f), horizontalArrangement = Arrangement.End) {
            Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.End) {
                Row(
                    Modifier.padding(vertical = 14.dp),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.Bottom
                ) {

                    Text(text = text, fontSize = 18.sp, textAlign = TextAlign.End)
                    Text(
                        text = descEnd,
                        fontSize = 18.sp,
                        textAlign = TextAlign.End,
                        color = Color.Gray
                    )
                }
                if (divider) {

                    Divider()
                }
            }


        }
    }

}

@Preview
@Composable
fun AllReportItemCard() {
    val report = Report()
    report.total = 6250
    report.count = 5
    report.reported_time = "24.Март 23"
    AllReportItemCard(report = report) {
        Log.e(TAG, "AllReportItemCard:${report.id} ")
    }
}