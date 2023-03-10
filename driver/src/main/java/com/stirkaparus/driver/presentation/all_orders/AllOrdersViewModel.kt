package com.stirkaparus.driver.presentation.all_orders

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stirkaparus.driver.domain.repository.AllOrdersResponse
import com.stirkaparus.driver.domain.repository.OrdersRepository
import com.stirkaparus.model.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AllOrdersViewModel @Inject constructor(
    private val ordersRepository: OrdersRepository
) : ViewModel() {

    init {
        getOrders()
    }

    var orderListResponse by mutableStateOf<AllOrdersResponse>(Response.Loading)
        private set

    private   fun getOrders() = viewModelScope.launch {
        ordersRepository.getOrdersFromFireStore().collect { response ->
            orderListResponse = response
        }
    }


}