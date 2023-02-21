package com.stirkaparus.driver.presentation.finishedOrders

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stirkaparus.driver.domain.repository.OrdersResponse
 import com.stirkaparus.driver.useCases.UseCases
import com.stirkaparus.model.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FinishedOrdersViewModel @Inject constructor(
    private val useCases: UseCases
) : ViewModel() {

    var ordersResponse by mutableStateOf<OrdersResponse>(Response.Loading)
        private set



    private fun getOrders(companyId: String) = viewModelScope.launch {
        useCases.getOrders(companyId).collect { response ->
            ordersResponse = response
        }
    }
}