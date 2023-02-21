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
import com.stirkaparus.driver.domain.repository.OrdersResponse
import com.stirkaparus.driver.useCases.UseCases
import com.stirkaparus.model.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(
    private val useCases: UseCases,
    private val prefs: SharedPreferences
) : ViewModel() {


    var ordersResponse by mutableStateOf<OrdersResponse>(Response.Loading)
        private set

    init {
        getOrders()
    }


    private fun getOrders() = viewModelScope.launch {
        val companyId = prefs.getString(COMPANY_ID, "")
        useCases.getOrders(companyId.toString()).collect { response ->
            Log.e(TAG, "getOrders: $response")
            ordersResponse = response
        }
    }

}