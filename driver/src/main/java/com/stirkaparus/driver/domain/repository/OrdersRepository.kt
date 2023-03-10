package com.stirkaparus.driver.domain.repository

import com.stirkaparus.model.Order
import com.stirkaparus.model.Response
import kotlinx.coroutines.flow.Flow

typealias Orders = List<Order>
typealias AllOrders = List<Order>
typealias OrdersResponse = Response<Orders>
typealias AllOrdersResponse = Response<AllOrders>
typealias AddOrderInFirestoreResponse = Response<Boolean>

interface OrdersRepository {

    fun getOrdersFromFireStore(): Flow<OrdersResponse>

    suspend fun addOrderInFirestore(
        order: Order
    ): AddOrderInFirestoreResponse
}


