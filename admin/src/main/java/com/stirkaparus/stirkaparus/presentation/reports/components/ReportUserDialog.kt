package com.stirkaparus.stirkaparus.presentation.reports.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.stirkaparus.stirkaparus.presentation.components.SmallSpacer


@Composable
fun ReportUserDialog(
    openDialog: Boolean,
    closeDialog: () -> Unit,
    report: () -> Unit,
    count: Int,
    total: Int,

    ) {


    val buttonEnable = count > 0

    if (openDialog) {


        AlertDialog(
            onDismissRequest = closeDialog,
            title = {
                Text(modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center,
                    text = "Сделать рассчёт"
                )
            },
            text = {
                Column(Modifier.padding(16.dp)) {
                    SmallSpacer()


                    Row(Modifier.fillMaxWidth()) {
                        Text(text = "Сумма : ")
                        Text(text = total.toString())
                    }
                    Row(Modifier.fillMaxWidth()) {
                        Text(text = "Количество : ")
                        Text(text = count.toString())
                    }

                }
            },
            confirmButton = {
                Button(
                    enabled = buttonEnable,
                    onClick = {
                        report()
                        closeDialog()

                    }
                ) {
                    Text(
                        text = "Рассчет"
                    )
                }
            },
            dismissButton = {
                Button(
                    onClick = closeDialog
                ) {
                    Text(
                        text = "Зкрыть"
                    )
                }
            }
        )
    }
}
