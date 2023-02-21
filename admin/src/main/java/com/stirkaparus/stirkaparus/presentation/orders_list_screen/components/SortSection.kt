package com.stirkaparus.stirkaparus.presentation.orders_list_screen.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.stirkaparus.stirkaparus.presentation.orders_list_screen.Filter

@Composable
fun SortSection(
    modifier: Modifier = Modifier,
    orderSort: String,
    onFilterChange: MutableState<String>
) {
    Row(
        modifier = modifier.fillMaxWidth()
    ) {

        Column(
         ) {
            Text(text = "Фильтр")
            DefaultRadioButton(
                text = "Все",
                selected = orderSort == Filter.all,
                onSelect = { onFilterChange.value = Filter.all }
            )
            DefaultRadioButton(
                text = "Создан",
                selected = orderSort == Filter.created,
                onSelect = { onFilterChange.value = Filter.created }
            )
             DefaultRadioButton(
                text = "Забран",
                selected = orderSort == Filter.taken,
                onSelect = { onFilterChange.value = Filter.taken }
            )
            Spacer(modifier = Modifier.width(2.dp))
            DefaultRadioButton(
                text = "Постиран",
                selected = orderSort == Filter.washed,
                onSelect = { onFilterChange.value = Filter.washed}

            )


            Spacer(modifier = Modifier.width(2.dp))
            DefaultRadioButton(
                text = "Зафасован",
                selected = orderSort == Filter.finished,
                onSelect = { onFilterChange.value = Filter.finished}
            )
            Spacer(modifier = Modifier.width(2.dp))
            DefaultRadioButton(
                text = "Достален",
                selected = orderSort == Filter.delivered,
                onSelect = { onFilterChange.value = Filter.delivered}
            )
        }
        Column(
         ) {
            Text(text = "Сортировка")

            DefaultRadioButton(
                text = "Ascending",
                selected = orderSort == "ascending",
                onSelect = {
                    onFilterChange.value = "created"
                }
            )
            Spacer(modifier = Modifier.width(8.dp))
            DefaultRadioButton(
                text = "Descending",
                selected = orderSort == "descending",
                onSelect = {
                    onFilterChange.value = "created"
                }
            )
        }
    }
}

sealed class OrderFilter(val filterType: SortType) {
    class Created(filterType: SortType) : OrderFilter(filterType)
    class Taken(filterType: SortType) : OrderFilter(filterType)


    fun copy(orderType: SortType): OrderFilter {
        return when (this) {
            is Created -> Created(orderType)
            is Taken -> Taken(orderType)
        }
    }
}

sealed class SortType {
    object Ascending : SortType()
    object Descending : SortType()
}