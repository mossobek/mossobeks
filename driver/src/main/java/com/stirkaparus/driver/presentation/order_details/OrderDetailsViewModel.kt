package com.stirkaparus.driver.presentation.order_details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stirkaparus.driver.domain.repository.DOrderDetailsResponse
import com.stirkaparus.driver.domain.repository.OrderDetailsRepository
import com.stirkaparus.model.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderDetailsViewModel @Inject constructor(
    private val repo: OrderDetailsRepository,

    ) : ViewModel() {


    var orderDetailsResponse by mutableStateOf<DOrderDetailsResponse>(Response.Loading)
        private set

    fun getOrder(
        id: String
    ) = viewModelScope.launch {
        repo.getOrderFromFirestore(id).collect { response ->
            orderDetailsResponse = response
        }
    }}