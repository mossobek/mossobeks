package com.stirkaparus.driver.presentation.orders

import android.content.ContentValues.TAG
import android.content.SharedPreferences
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stirkaparus.driver.common.Constants.COMPANY_ID
import com.stirkaparus.driver.domain.repository.OrdersRepository
import com.stirkaparus.driver.domain.repository.OrdersResponse
import com.stirkaparus.model.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(
    private val prefs: SharedPreferences,
    private val repo: OrdersRepository
) : ViewModel() {


    var ordersResponse by mutableStateOf<OrdersResponse>(Response.Loading)
        private set

    init {
        getOrders()
    }


    private fun getOrders() = viewModelScope.launch {
         repo.getOrdersFromFireStore().collect { response ->
            Log.e(TAG, "getOrders: $response")
            ordersResponse = response
        }
    }

}