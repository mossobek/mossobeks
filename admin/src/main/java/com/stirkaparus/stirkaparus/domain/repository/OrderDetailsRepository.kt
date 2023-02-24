package com.stirkaparus.stirkaparus.domain.repository

import com.stirkaparus.model.Order
import com.stirkaparus.model.Response
import kotlinx.coroutines.flow.Flow


typealias OrderResponse = Response<Order>

interface OrderDetailsRepository {
    fun getOrderFromFirestore(id: String): Flow<OrderResponse>
}